/*
 * Copyright 2011-2015 B2i Healthcare Pte Ltd, http://b2i.sg
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
package com.b2international.snowowl.datastore.internal.branch;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import com.b2international.snowowl.datastore.branch.Branch;
import com.b2international.snowowl.datastore.branch.BranchMergeException;

/**
 * TODO: branch description
 * TODO: metadata
 * @since 4.1
 */
public class BranchImpl implements Branch {

    public enum BranchState {
        UP_TO_DATE,
        FORWARD, 
        BEHIND, 
        DIVERGED, 
        STALE
    }

    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9_-]{1,50}");

	private static void checkName(String name) {
		checkArgument(VALID_NAME_PATTERN.matcher(name).matches(), "Name '%s' has invalid characters.", name);
	}

    protected final BranchManagerImpl branchManager;
    
    private final String name;
    private final String parentPath;
    private final long baseTimestamp;
    private final long headTimestamp;
    private final boolean deleted;
    
    public BranchImpl(BranchManagerImpl branchManager, String name, String parentPath, long baseTimestamp) {
    	this(branchManager, name, parentPath, baseTimestamp, baseTimestamp);
    }
    
    public BranchImpl(BranchManagerImpl branchManager, String name, String parentPath, long baseTimestamp, long headTimestamp) {
    	this(branchManager, name, parentPath, baseTimestamp, headTimestamp, false);
    }
    
	public BranchImpl(BranchManagerImpl branchManager, String name, String parentPath, long baseTimestamp, long headTimestamp, boolean deleted) {
        checkName(name);
        checkArgument(baseTimestamp >= 0L, "Base timestamp may not be negative.");
        checkArgument(headTimestamp >= baseTimestamp, "Head timestamp may not be smaller than base timestamp.");
        checkNotNull(branchManager, "Branch manager may not be null.");
        
		this.branchManager = branchManager;
		this.name = name;
		this.parentPath = parentPath;
		this.baseTimestamp = baseTimestamp;
		this.headTimestamp = headTimestamp;
		this.deleted = deleted;
	}
	
	BranchImpl withDeleted() {
		return new BranchImpl(branchManager, name, parentPath, baseTimestamp, headTimestamp, true);
	}
	
	BranchImpl withBaseTimestamp(long newBaseTimestamp) {
        checkArgument(newBaseTimestamp > baseTimestamp, "New base timestamp may not be smaller or equal than old base timestamp.");
		return new BranchImpl(branchManager, name, parentPath, newBaseTimestamp, newBaseTimestamp, deleted);
	}
	
	BranchImpl withHeadTimestamp(long newHeadTimestamp) {
		checkArgument(newHeadTimestamp > headTimestamp, "New head timestamp may not be smaller or equal than old head timestamp.");
		return new BranchImpl(branchManager, name, parentPath, baseTimestamp, newHeadTimestamp, deleted);
	}
	
	@Override
	public Branch delete() {
		return branchManager.delete(this);
	}

	@Override
	public Branch rebase() {
		return rebase(parent());
	}

	@Override
	public Branch rebase(Branch target) {
		final BranchState state = state();
		if (state == BranchState.BEHIND || state == BranchState.DIVERGED || state == BranchState.STALE) {
			return branchManager.rebase(this, (BranchImpl) target);
		} else {
			return this;
		}
	}

	@Override
	public Branch merge(Branch source) throws BranchMergeException {
		checkArgument(!source.equals(this), "Can't merge branch onto itself.");
		if (source.state() != BranchState.FORWARD) {
			throw new BranchMergeException("Only source in the FORWARD state can merged.");
		} else {
			return branchManager.merge(this, (BranchImpl) source);
		}
	}

	@Override
	public Branch createChild(String name) {
		checkName(name);
		return branchManager.createBranch(this, name);
	}

	@Override
	public String name() {
		return name;
	}
	
	@Override
	public Branch parent() {
		return branchManager.getBranch(parentPath);
	}
	
    @Override
	public long baseTimestamp() {
        return baseTimestamp;
    }

    @Override
	public long headTimestamp() {
        return headTimestamp;
    }

    @Override
    public boolean isDeleted() {
    	return deleted;
    }

	@Override
	public String path() {
        return parentPath + SEPARATOR + name;
    }

    @Override
	public BranchState state() {
		return state(parent());
	}
    
    @Override
	public BranchState state(Branch target) {
		if (baseTimestamp() < target.baseTimestamp()) {
        	return BranchState.STALE;
        } else if (headTimestamp > baseTimestamp && target.headTimestamp() < baseTimestamp) {
        	return BranchState.FORWARD;
        } else if (headTimestamp == baseTimestamp && target.headTimestamp() > baseTimestamp) {
        	return BranchState.BEHIND;
        } else if (headTimestamp > baseTimestamp && target.headTimestamp() > baseTimestamp) {
        	return BranchState.DIVERGED;
        } else {
    	    return BranchState.UP_TO_DATE;
        }
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (baseTimestamp ^ (baseTimestamp >>> 32));
		result = prime * result + ((branchManager == null) ? 0 : branchManager.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + (int) (headTimestamp ^ (headTimestamp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentPath == null) ? 0 : parentPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		
		if (!(obj instanceof BranchImpl)) {
			return false;
		}
		
		BranchImpl other = (BranchImpl) obj;
		
		if (baseTimestamp != other.baseTimestamp) { return false; }
		if (!branchManager.equals(other.branchManager)) { return false; }
		if (deleted != other.deleted) { return false; }
		if (headTimestamp != other.headTimestamp) { return false; }
		if (!name.equals(other.name)) { return false; }
		if (!parentPath.equals(other.parentPath)) { return false; }
		
		return true;
	}
}
