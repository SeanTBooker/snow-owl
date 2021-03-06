# Backup and Restore with Curator

## Prerequisites

Please refer to the official Curator [install guide](https://www.elastic.co/guide/en/elasticsearch/client/curator/5.8/installation.html) on how to install it on various operating systems.

## Configure Snapshot repository

In order to create backups for Snow Owl, you need a repository in your Elasticsearch cluster.

To create a repository (assuming shared file system repository, `fs`), execute the following command:

```
$ curl -XPUT localhost:9200/_snapshot/snowowl-snapshots -d
{
  "type": "fs",
  "settings": {
    "location": "/path/to/shared/mount",
    "compress": true
  }
}
```

Elasticsearch requires that the specified `/path/to/shared/mount` is whitelisted in the `path.repo` configuration setting in the `elasticsearch.yml` configuration file. See section
[Shared file system repository](https://www.elastic.co/guide/en/elasticsearch/reference/7.7/snapshots-register-repository.html#snapshots-filesystem-repository) of
the Elasticsearch reference for details.

## Curator configuration file

Curator requires a single configuration file to be specified when running it.
If you are using a default Elasticsearch cluster with default configurations then the default Curator recommended file should be sufficient. 
Any configuration changes you have made to your Elasticsearch cluster needs to be changed here as well in this config file so Curator can access your cluster without any issues. 

Example `curator.yml`:

```yml
client:
  hosts:
    - 127.0.0.1
  port: 9200
  url_prefix:
  use_ssl: False
  certificate:
  client_cert:
  client_key:
  ssl_no_validate: False
  http_auth:
  timeout: 30
  master_only: False

logging:
  loglevel: INFO
  logfile:
  logformat: default
  blacklist: ['elasticsearch', 'urllib3']
```

## Snapshot Action

Curator is using action YML files to perform a set of actions sequentially.
See the available steps here: 
https://www.elastic.co/guide/en/elasticsearch/client/curator/5.8/actions.html

A Snapshot Action that can be used to backup the content from a Snow Owl Terminology Server.

Example `snowowl_snapshot.yml` file:

```yml
actions:
  1:
    action: snapshot
    description: >-
      Snapshot all indices. Wait for the snapshot to complete. Do not skip
      the repository filesystem access check.
    options:
      repository: snowowl-snapshots
      name:
      ignore_unavailable: False
      include_global_state: True
      partial: False
      wait_for_completion: True
      skip_repo_fs_check: False
      disable_action: False
    filters:
    - filtertype: none
  2:
    action: delete_snapshots
    description: >-
      Keep 10 most recent snapshots in the selected repository
      (based on creation_date), for 'curator-' prefixed snapshots. Ordering
      is age-based and reversed by default for the 'count' filter.
    options:
      repository: snowowl-snapshots
      disable_action: False
      ignore_empty_list: True
    filters:
    - filtertype: pattern
      kind: prefix
      value: curator-
      exclude:
    - filtertype: count
      count: 10
      use_age: True
      source: creation_date
```

To execute a Snapshot action manually, you can use the following command:

```
$ curator --config curator.yml snowowl_snapshot.yml
```

## Restore Action

A Restore Action that can be used to restore the latest snapshot (aka backup) to the Snow Owl Terminology Server.

Example `snowowl_restore.yml` file:

```yml
actions:
  1:
    action: restore
    description: >-
      Restore all indices in the most recent curator-* snapshot with state SUCCESS. Wait
      for the restore to complete before continuing. Do not skip the repository
      filesystem access check.
    options:
      repository: snowowl-snapshots
      # If name is blank, the most recent snapshot by age will be selected
      name:
      # If indices is blank, all indices in the snapshot will be restored
      indices:
      include_aliases: False
      ignore_unavailable: False
      include_global_state: False
      partial: False
      rename_pattern:
      rename_replacement:
      extra_settings:
      wait_for_completion: True
      skip_repo_fs_check: False
      disable_action: False
    filters:
    - filtertype: pattern
      kind: prefix
      value: curator-
    - filtertype: state
      state: SUCCESS
```

To execute a Restore action manually, you can use the following command:

```
$ curator --config curator.yml snowowl_restore.yml
```

## Taking scheduled backups

To schedule automated backups, you can use [Cron](https://en.wikipedia.org/wiki/Cron) on Unix-style operating systems to automate the job.
The back up interval depends on your use case and how you are accessing the data. If you have a write-heavy scenario, we recommend a hourly backup interval, otherwise some value between hourly - daily is preferable.

An example crontab entry that initiates a daily backup at 03:00, and captures Curator's output to `/var/log/backup.log` (both standard output and standard error) would look like this:

```
0 3 * * * /path/to/curator --config /path/to/snowowl/configs/curator.yml /path/to/snowowl/configs/curator/snowowl_snapshot.yml > /var/log/backup.log 2>&1
```
