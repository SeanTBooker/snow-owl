/*
 * Copyright 2011-2020 B2i Healthcare Pte Ltd, http://b2i.sg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.b2international.snowowl.core.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import com.b2international.snowowl.core.branch.Branch;

/**
 * @since 4.5
 */
public final class RepositoryBranchContext extends DelegatingRepositoryContext implements BranchContext {

	private final String path;
	private final Branch branch;

	public RepositoryBranchContext(RepositoryContext context, String path, Branch branch) {
		super(context);
		this.path = path;
		this.branch = checkNotNull(branch, "branch");
	}
	
	@Override
	public final Branch branch() {
		return branch;
	}
	
	@Override
	public String path() {
		// XXX make sure we return the original path expression requested by the client 
		return path;
	}

}
