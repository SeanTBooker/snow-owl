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
package com.b2international.snowowl.core.ft;

import com.b2international.snowowl.core.domain.BranchContext;

public final class Features {
	
	/**
	 * Creates a feature toggle key for reindex requests
	 * 
	 * @param repositoryId - the id of the repository being reindexed
	 * @return a unique identifier for the reindex request
	 */
	public static String getReindexFeatureToggle(String repositoryId) {
		return String.format("reindex.%s", repositoryId);
	}

	/**
	 * Creates a feature toggle key for an import process 
	 * 
	 * @param context
	 * @return
	 */
	public static String getImportFeatureToggle(BranchContext context) {
		return getImportFeatureToggle(context.id(), context.branchPath());
	}
	
	/**
	 * Creates a feature toggle key for SNOMED CT import
	 * 
	 * @param branchPath - the branch where the import is executed
	 * @return a unique identifier for the import feature
	 */
	public static String getImportFeatureToggle(String repositoryId, String branchPath) {
		return String.format("import.%s-%s", repositoryId, branchPath);
	}
	
	/**
	 * Creates a feature toggle key for SNOMED CT classification
	 * 
	 * @return a unique identifier for the classification feature 
	 */
	public static String getClassifyFeatureToggle(String repositoryId, String branchPath) {
		return String.format("classify.%s-%s", repositoryId, branchPath);
	}
	
	private Features() { /* prevent instantiation */ }
}
