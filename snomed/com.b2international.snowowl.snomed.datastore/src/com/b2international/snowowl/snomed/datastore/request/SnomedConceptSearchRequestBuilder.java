/*
 * Copyright 2011-2017 B2i Healthcare Pte Ltd, http://b2i.sg
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
package com.b2international.snowowl.snomed.datastore.request;

import java.util.Collection;
import java.util.Set;

import com.b2international.snowowl.core.domain.BranchContext;
import com.b2international.snowowl.datastore.request.SearchResourceRequest;
import com.b2international.snowowl.snomed.core.domain.CharacteristicType;
import com.b2international.snowowl.snomed.core.domain.DefinitionStatus;
import com.b2international.snowowl.snomed.core.domain.SnomedConcepts;

/**
 * <i>Builder</i> class to build requests responsible for searching SNOMED CT concepts.
 * This class should be instantiated from the corresponding static method on the central {@link SnomedRequests} class.
 * Filter methods restrict the results set returned from the search requests; 
 * what passes the filters will be returned as part of the pageable resultset.
 * 
 * @since 4.5
 */
public final class SnomedConceptSearchRequestBuilder extends SnomedComponentSearchRequestBuilder<SnomedConceptSearchRequestBuilder, SnomedConcepts> {

	/**
	 * Protected constructor.
	 * This class should be instantiated using the central {@link SnomedRequests} class.
	 */
	SnomedConceptSearchRequestBuilder() {
	}

	public final SnomedConceptSearchRequestBuilder withDoi() {
		return addOption(SnomedConceptSearchRequest.OptionKey.USE_DOI, true);
	}

	public final SnomedConceptSearchRequestBuilder withSearchProfile(final String userId) {
		return addOption(SnomedConceptSearchRequest.OptionKey.SEARCH_PROFILE, userId);
	}

	public final SnomedConceptSearchRequestBuilder withFuzzySearch() {
		return addOption(SnomedConceptSearchRequest.OptionKey.USE_FUZZY, true);
	}

	public final SnomedConceptSearchRequestBuilder withParsedTerm() {
		return addOption(SnomedConceptSearchRequest.OptionKey.PARSED_TERM, true);
	}

	public final SnomedConceptSearchRequestBuilder filterByTerm(String term) {
		return addOption(SnomedConceptSearchRequest.OptionKey.TERM, term);
	}

	/**
	 * Filters the concepts based on the type of its descriptions where the description type is specified by concept ID 
	 * representing the type. E.g.: "900000000000003001" for <i>Fully Specified Name</i>.
	 * @param description type represented by its concept ID
	 * @return SnomedConceptSearchRequestBuilder
	 * 
	 * @see SnomedConcepts
	 */
	public final SnomedConceptSearchRequestBuilder filterByDescriptionType(String type) {
		return addOption(SnomedConceptSearchRequest.OptionKey.DESCRIPTION_TYPE, type);
	}

	/**
	 * Filter matches by the specified ESCG expression.
	 * @param ESCG expression
	 * @return SnomedConceptSearchRequestBuilder
	 * @deprecated - as of 5.4, use {@link #filterByEcl(String)}
	 */
	public final SnomedConceptSearchRequestBuilder filterByEscg(String expression) {
		return addOption(SnomedConceptSearchRequest.OptionKey.ESCG, expression);
	}

	/**
	 * Filter matches by the specified Expression Constraint Language (ECL) expression. 
	 * The currently supported ECL version is v1.1. See <a href="http://snomed.org/ecl">ECL Specification and Guide</a> or
	 * <a href="http://www.snomed.org/news-articles/expression-constraint-language">About ECL</a> for more information.
	 * 
	 * @param expression ECL expression
	 * @return SnomedConceptSearchRequestBuilder
	 */
	public final SnomedConceptSearchRequestBuilder filterByEcl(String expression) {
		return addOption(SnomedConceptSearchRequest.OptionKey.ECL, expression);
	}

	/**
	 * Filter that matches the specified parent identifier amongst the <b>direct</b> inferred super types.
	 * E.g.: a filter that returns the direct <i>inferred</i> children of the specified parent.
	 * 
	 * @param parentId the SNOMED CT concept ID of the parent concept
	 * @return SnomedConceptSearchRequestBuilder
	 * @see CharacteristicType
	 */
	public final SnomedConceptSearchRequestBuilder filterByParent(String parentId) {
		return addOption(SnomedConceptSearchRequest.OptionKey.PARENT, parentId);
	}

	/**
	 * Filter matches to have any of the specified parent identifiers amongst the direct inferred super types.
	 * E.g.:a filter that returns the direct <i>inferred</i> children of the specified parents.
	 * 
	 * @param parentIds set of parent ids
 	 * @return SnomedConceptSearchRequestBuilder
 	 * @see CharacteristicType
	 */
	public final SnomedConceptSearchRequestBuilder filterByParents(Set<String> parentIds) {
		return addOption(SnomedConceptSearchRequest.OptionKey.PARENT, parentIds);
	}

	/**
	 * Filter matches to have the specified parent identifier amongst the direct stated super types.
	 * E.g.: a filter that returns the direct <i>stated</i> children of the specified parent.
	 * 
	 * @param parentId
	 * @return SnomedConceptSearchRequestBuilder
	 * @see CharacteristicType
	 */
	public final SnomedConceptSearchRequestBuilder filterByStatedParent(String parentId) {
		return addOption(SnomedConceptSearchRequest.OptionKey.STATED_PARENT, parentId);
	}

	/**
	 * Filter matches to have the specified parent identifier amongst the direct stated super types.
	 * E.g.:a filter that returns the direct <i>stated</i> children of the specified parents.
	 * 
	 * @param parentIds set of parent ids
	 * @return SnomedConceptSearchRequestBuilder
	 * @see CharacteristicType
	 */
	public final SnomedConceptSearchRequestBuilder filterByStatedParents(Set<String> parentIds) {
		return addOption(SnomedConceptSearchRequest.OptionKey.STATED_PARENT, parentIds);
	}

	/**
	 * Filter matches to have the specified ancestor identifier amongst the inferred super types (including direct as well).
	 * E.g.:a filter that returns all of the <i>inferred</i> (direct and non-direct) children of the specified parent.
	 * 
	 * @param ancestorId
	 * @return SnomedConceptSearchRequestBuilder
	 * @see CharacteristicType
	 */
	public final SnomedConceptSearchRequestBuilder filterByAncestor(String ancestorId) {
		return addOption(SnomedConceptSearchRequest.OptionKey.ANCESTOR, ancestorId);
	}

	/**
	 * Filter matches to have any of the specified ancestor identifier amongst the inferred super types (including direct as well).
	 * E.g.:a filter that returns all of the <i>inferred</i> (direct and non-direct) children of the specified parents
	 * 
	 * @param ancestorIds collection of ancestor IDs
	 * @return SnomedConceptSearchRequestBuilder
	 * @see CharacteristicType
	 */
	public final SnomedConceptSearchRequestBuilder filterByAncestors(Collection<String> ancestorIds) {
		return addOption(SnomedConceptSearchRequest.OptionKey.ANCESTOR, ancestorIds);
	}

	/**
	 * Filter matches to have the specified ancestor identifier amongst the stated super types (including direct as well).
	 * E.g.:a filter that returns all of the <i>stated</i> (direct and non-direct) children of the specified parent.
	 * 
	 * @param ancestorId
	 * @return SnomedConceptSearchRequestBuilder
	 * @see CharacteristicType
	 */
	public final SnomedConceptSearchRequestBuilder filterByStatedAncestor(String ancestorId) {
		return addOption(SnomedConceptSearchRequest.OptionKey.STATED_ANCESTOR, ancestorId);
	}

	/**
	 * Filter matches to have any of the specified ancestor identifier amongst the stated super types (including direct as well).
	 * E.g.:a filter that returns all of the <i>stated</i> (direct and non-direct) children of the specified parents.
	 * 
	 * @param ancestorId
	 * @return SnomedConceptSearchRequestBuilder
	 * @see CharacteristicType
	 */
	public final SnomedConceptSearchRequestBuilder filterByStatedAncestors(Set<String> ancestorIds) {
		return addOption(SnomedConceptSearchRequest.OptionKey.STATED_ANCESTOR, ancestorIds);
	}

	/**
	 * Filter matches to have the specified definition status.
	 * 
	 * @param definitionStatusId id of the definition status {@link DefinitionStatus}
	 * @return SnomedConceptSearchRequestBuilder
	 * @see DefinitionStatus
	 */
	public final SnomedConceptSearchRequestBuilder filterByDefinitionStatus(String definitionStatusId) {
		return addOption(SnomedConceptSearchRequest.OptionKey.DEFINITION_STATUS, definitionStatusId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.b2international.snowowl.datastore.request.RevisionSearchRequestBuilder#createSearch()
	 */
	@Override
	protected SearchResourceRequest<BranchContext, SnomedConcepts> createSearch() {
		return new SnomedConceptSearchRequest();
	}
}