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
package com.b2international.snowowl.snomed.core.domain;

import java.util.Map;

import com.b2international.snowowl.core.domain.TransactionContext;
import com.b2international.snowowl.core.events.Request;
import com.b2international.snowowl.snomed.datastore.request.SnomedRequests;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Multimap;

/**
 * Represents a SNOMED CT Description.
 * <p>
 * Information about the inactivation reason can also be retrieved from this object if applicable.
 */
public final class SnomedDescription extends SnomedCoreComponent {

	private String term;
	private String languageCode;
	private CaseSignificance caseSignificance;
	private DescriptionInactivationIndicator descriptionInactivationIndicator;
	private Map<String, Acceptability> acceptabilityMap;
	private Multimap<AssociationType, String> associationTargets;
	private SnomedConcept concept;
	private SnomedConcept type;

	public SnomedDescription() {
	}
	
	public SnomedDescription(String id) {
		setId(id);
	}

	/**
	 * Returns the associated concept's identifier, eg. "{@code 363698007}".
	 * 
	 * @return the concept identifier
	 */
	public String getConceptId() {
		return getConcept().getId();
	}

	/**
	 * Returns the description type identifier, eg. "{@code 900000000000013009}".
	 * 
	 * @return the type identifier
	 */
	public String getTypeId() {
		return getType().getId();
	}

	/**
	 * Returns the container concept of the description.
	 *  
	 * @return the container concept
	 */
	public SnomedConcept getConcept() {
		return concept;
	}

	/**
	 * Returns the type concept of the description.
	 *  
	 * @return the type concept
	 */
	public SnomedConcept getType() {
		return type;
	}

	/**
	 * Returns the description term, eg. "{@code Finding site}".
	 * 
	 * @return the description term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * Returns the description's language code, not including any dialects or variations, eg. "{@code en}".
	 * 
	 * @return the language code of this description
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Returns the description's case significance attribute, indicating whether character case within the term should
	 * be preserved or is interchangeable.
	 * 
	 * @return the case significance of this description
	 */
	public CaseSignificance getCaseSignificance() {
		return caseSignificance;
	}

	/**
	 * Returns language reference set member acceptability values for this description, keyed by language reference set identifier.
	 * 
	 * @return the acceptability map for this description
	 */
	public Map<String, Acceptability> getAcceptabilityMap() {
		return acceptabilityMap;
	}

	/**
	 * Returns the inactivation indicator (if any) of the description that can be used to identify the reason why the
	 * current description has been deactivated.
	 * 
	 * @return the inactivation reason for this description, or {@code null} if the description is still active, or no
	 * reason has been given
	 */
	public DescriptionInactivationIndicator getInactivationIndicator() {
		return descriptionInactivationIndicator;
	}

	/**
	 * Returns association reference set member targets keyed by the association type.
	 * 
	 * @return related association targets, or {@code null} if the description is still active
	 */
	public Multimap<AssociationType, String> getAssociationTargets() {
		return associationTargets;
	}

	public void setConceptId(final String conceptId) {
		setConcept(new SnomedConcept(conceptId));
	}

	public void setTypeId(final String typeId) {
		setType(new SnomedConcept(typeId));
	}
	
	@JsonIgnore
	public void setConcept(SnomedConcept concept) {
		this.concept = concept;
	}
	
	@JsonIgnore	
	public void setType(SnomedConcept type) {
		this.type = type;
	}

	public void setTerm(final String term) {
		this.term = term;
	}

	public void setLanguageCode(final String languageCode) {
		this.languageCode = languageCode;
	}

	public void setCaseSignificance(final CaseSignificance caseSignificance) {
		this.caseSignificance = caseSignificance;
	}

	@JsonProperty("acceptability")
	public void setAcceptabilityMap(final Map<String, Acceptability> acceptabilityMap) {
		this.acceptabilityMap = acceptabilityMap;
	}
	
	public void setDescriptionInactivationIndicator(final DescriptionInactivationIndicator descriptionInactivationIndicator) {
		this.descriptionInactivationIndicator = descriptionInactivationIndicator;
	}
	
	public void setAssociationTargets(Multimap<AssociationType, String> associationTargets) {
		this.associationTargets = associationTargets;
	}
	
	@Override
	public Request<TransactionContext, Boolean> toUpdateRequest() {
		return SnomedRequests.prepareUpdateDescription(getId())
			.setAcceptability(getAcceptabilityMap())
			.setActive(isActive())
			.setAssociationTargets(getAssociationTargets())
			.setCaseSignificance(getCaseSignificance())
			.setInactivationIndicator(getInactivationIndicator())
			.setModuleId(getModuleId())
			.build();
	}

	@Override
	public Request<TransactionContext, String> toCreateRequest(final String conceptId) {
		return SnomedRequests.prepareNewDescription()
			.setAcceptability(getAcceptabilityMap())
			.setCaseSignificance(getCaseSignificance())
			// ensure that the description's conceptId property is the right one
			.setConceptId(conceptId)
			// XXX assuming that the ID is always set in this case
			.setId(getId())
			.setLanguageCode(getLanguageCode())
			.setModuleId(getModuleId())
			.setTerm(getTerm())
			.setTypeId(getTypeId())
			.build();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SnomedDescription [getId()=");
		builder.append(getId());
		builder.append(", isReleased()=");
		builder.append(isReleased());
		builder.append(", isActive()=");
		builder.append(isActive());
		builder.append(", getEffectiveTime()=");
		builder.append(getEffectiveTime());
		builder.append(", getModuleId()=");
		builder.append(getModuleId());
		builder.append(", getTypeId()=");
		builder.append(getTypeId());
		builder.append(", getTerm()=");
		builder.append(getTerm());
		builder.append(", getLanguageCode()=");
		builder.append(getLanguageCode());
		builder.append(", getCaseSignificance()=");
		builder.append(getCaseSignificance());
		builder.append(", getAcceptabilityMap()=");
		builder.append(getAcceptabilityMap());
		if (null != descriptionInactivationIndicator) {
			builder.append(", getDescriptionInactivationIndicator()=")
				.append(descriptionInactivationIndicator);
			
		}
		builder.append(", getAssociationTargets()=");
		builder.append(getAssociationTargets());
		builder.append("]");
		return builder.toString();
	}
}