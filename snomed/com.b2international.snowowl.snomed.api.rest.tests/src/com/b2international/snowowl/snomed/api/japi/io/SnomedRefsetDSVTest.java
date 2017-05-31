/*
 * Copyright 2017 B2i Healthcare Pte Ltd, http://b2i.sg
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
package com.b2international.snowowl.snomed.api.japi.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.b2international.commons.FileUtils;
import com.b2international.commons.http.ExtendedLocale;
import com.b2international.snowowl.core.ApplicationContext;
import com.b2international.snowowl.core.branch.Branch;
import com.b2international.snowowl.core.domain.TransactionContext;
import com.b2international.snowowl.core.events.Request;
import com.b2international.snowowl.core.events.bulk.BulkRequest;
import com.b2international.snowowl.core.events.bulk.BulkRequestBuilder;
import com.b2international.snowowl.core.events.bulk.BulkResponse;
import com.b2international.snowowl.core.terminology.ComponentCategory;
import com.b2international.snowowl.datastore.file.FileRegistry;
import com.b2international.snowowl.datastore.request.CommitResult;
import com.b2international.snowowl.datastore.request.TransactionalRequestBuilder;
import com.b2international.snowowl.eventbus.IEventBus;
import com.b2international.snowowl.snomed.SnomedConstants.Concepts;
import com.b2international.snowowl.snomed.common.SnomedTerminologyComponentConstants;
import com.b2international.snowowl.snomed.core.domain.CharacteristicType;
import com.b2international.snowowl.snomed.core.domain.constraint.SnomedConcreteDomainConstraint;
import com.b2international.snowowl.snomed.core.domain.constraint.SnomedConstraint;
import com.b2international.snowowl.snomed.core.domain.constraint.SnomedDescriptionConstraint;
import com.b2international.snowowl.snomed.core.domain.constraint.SnomedRelationshipConstraint;
import com.b2international.snowowl.snomed.core.domain.refset.SnomedReferenceSetMembers;
import com.b2international.snowowl.snomed.core.lang.LanguageSetting;
import com.b2international.snowowl.snomed.datastore.SnomedDatastoreActivator;
import com.b2international.snowowl.snomed.datastore.id.SnomedIdentifiers;
import com.b2international.snowowl.snomed.datastore.internal.rf2.AbstractSnomedDsvExportItem;
import com.b2international.snowowl.snomed.datastore.internal.rf2.ComponentIdSnomedDsvExportItem;
import com.b2international.snowowl.snomed.datastore.internal.rf2.DatatypeSnomedDsvExportItem;
import com.b2international.snowowl.snomed.datastore.internal.rf2.SimpleSnomedDsvExportItem;
import com.b2international.snowowl.snomed.datastore.internal.rf2.SnomedDsvExportItemType;
import com.b2international.snowowl.snomed.datastore.request.SnomedConceptCreateRequestBuilder;
import com.b2international.snowowl.snomed.datastore.request.SnomedDescriptionCreateRequestBuilder;
import com.b2international.snowowl.snomed.datastore.request.SnomedRefSetCreateRequestBuilder;
import com.b2international.snowowl.snomed.datastore.request.SnomedRefSetMemberCreateRequestBuilder;
import com.b2international.snowowl.snomed.datastore.request.SnomedRelationshipCreateRequestBuilder;
import com.b2international.snowowl.snomed.datastore.request.SnomedRequests;
import com.b2international.snowowl.snomed.snomedrefset.DataType;
import com.b2international.snowowl.snomed.snomedrefset.SnomedRefSetType;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * @since 5.11
 */
public class SnomedRefsetDSVTest  {

	private static final String REPOSITORY_ID = SnomedDatastoreActivator.REPOSITORY_UUID;
	
	private static final String MAIN_BRANCH = Branch.MAIN_PATH;

	private static final String DELIMITER = "|";
	
	private IEventBus bus;
	
	private FileRegistry fileRegistry;
	
	@Before
	public void setup() {
		bus = ApplicationContext.getInstance().getService(IEventBus.class);
		fileRegistry = ApplicationContext.getInstance().getService(FileRegistry.class);
	}
	
	@Test
	public void export() throws Exception {

		String refsetId = createRefset();
		addMembers(refsetId, Concepts.SUBSTANCE, Concepts.FINDING_SITE);
		
		UUID fileId = 
			SnomedRequests.dsv()
				.prepareExport()
				.setLocales(locales())
				.setDelimiter(DELIMITER)
				.setDescriptionIdExpected(true)
				.setRelationshipTargetExpected(true)
				.setRefSetId(refsetId)
				.setRefSetType(SnomedRefSetType.SIMPLE)
				.setExportItems(createExportItems(refsetId))
				.build(REPOSITORY_ID, MAIN_BRANCH).execute(bus)
				.getSync();
	
		
		File tempDir = Files.createTempDir();
		tempDir.deleteOnExit();

		File dsvExportZipFile = new File(tempDir, String.format("dsv-export-%s.zip", fileId.toString()));
		OutputStream outputStream = new FileOutputStream(dsvExportZipFile);
		fileRegistry.download(fileId, outputStream);
		Assert.assertTrue("Export archive must exist!", dsvExportZipFile.exists());

		FileUtils.decompressZipArchive(dsvExportZipFile, tempDir);
		File decompressedDsvFile = new File(tempDir,"dsv-export-file.txt");
		Assert.assertTrue("Uncompressed file must exist.", decompressedDsvFile.exists());

		List<String> dsvExportLines = Files.readLines(decompressedDsvFile, Charsets.UTF_8);
		Assert.assertTrue(MessageFormat.format("DSV export file must contain at least {0} lines besides header row(s)", dsvExportLines.size()), dsvExportLines.size() > 2);
	}

	private List<ExtendedLocale> locales() {
		return ApplicationContext.getInstance().getService(LanguageSetting.class).getLanguagePreference();
	}

	private List<AbstractSnomedDsvExportItem> createExportItems(String refsetId) {
		
		Set<String> referencedComponentIds = SnomedRequests.prepareSearchMember()
				.all()
				.filterByRefSet(refsetId)
				.build(REPOSITORY_ID, MAIN_BRANCH)
				.execute(bus)
				.then(SnomedReferenceSetMembers.GET_REFERENCED_COMPONENT_IDS)
				.getSync();
		
		return transformToExportItems(getConstraints(referencedComponentIds));
	}

	private Iterable<SnomedConstraint> getConstraints(Set<String> referencedComponents) {
		Set<String> conceptIds = referencedComponents.stream().filter(id -> SnomedIdentifiers.getComponentCategory(id) == ComponentCategory.CONCEPT).collect(Collectors.toSet());
		return SnomedRequests
					.prepareGetApplicablePredicates(MAIN_BRANCH, conceptIds, conceptIds, Collections.emptySet())
					.getSync();
	}

	private void addMembers(String refsetId, String... refComponentIds) {
		BulkRequestBuilder<TransactionContext> bulkRequestBuilder = BulkRequest.create();

		for (String refComponentId : refComponentIds) {
			bulkRequestBuilder.add(memberBuilder(refsetId, refComponentId));
		}
		new TransactionalRequestBuilder<BulkResponse>() {
			@Override
			public Request<TransactionContext, BulkResponse> build() {
				return bulkRequestBuilder.build();
			}
		}.build(REPOSITORY_ID, MAIN_BRANCH, "test", "test")
			.execute(bus)
			.getSync();
	}

	private String createRefset() {
		SnomedDescriptionCreateRequestBuilder fsn = descriptionBuilder(Concepts.FULLY_SPECIFIED_NAME, "term-test");
		SnomedDescriptionCreateRequestBuilder pt = descriptionBuilder(Concepts.SYNONYM, "test");

		SnomedRelationshipCreateRequestBuilder statedIsA = relationshipBuilder(Concepts.IS_A, CharacteristicType.STATED_RELATIONSHIP, Concepts.REFSET_SIMPLE_TYPE);
		SnomedRelationshipCreateRequestBuilder inferredIsA = relationshipBuilder(Concepts.IS_A, CharacteristicType.INFERRED_RELATIONSHIP, Concepts.REFSET_SIMPLE_TYPE);

		SnomedRefSetCreateRequestBuilder refSet = createRefsetBuilder();
		
		String conceptId = generateId();
		createConcept(fsn, pt, statedIsA, inferredIsA, refSet, conceptId);
		
		return conceptId;
	}

	private SnomedRefSetCreateRequestBuilder createRefsetBuilder() {
		return SnomedRequests.prepareNewRefSet()
				.setType(SnomedRefSetType.SIMPLE)
				.setReferencedComponentType(SnomedTerminologyComponentConstants.CONCEPT);
	}

	private CommitResult createConcept(
			SnomedDescriptionCreateRequestBuilder fsn,
			SnomedDescriptionCreateRequestBuilder pt, 
			SnomedRelationshipCreateRequestBuilder statedIsA,
			SnomedRelationshipCreateRequestBuilder inferredIsA, 
			SnomedRefSetCreateRequestBuilder refSet,
			String conceptId) {
		
		SnomedConceptCreateRequestBuilder builder = 
				SnomedRequests.prepareNewConcept()
					.setId(conceptId)
					.setModuleId(Concepts.MODULE_SCT_CORE)
					.addDescription(fsn)
					.addDescription(pt)
					.addRelationship(statedIsA)
					.addRelationship(inferredIsA);
		if (refSet != null)
			builder.setRefSet(refSet);
		
		return builder
					.build(REPOSITORY_ID, MAIN_BRANCH, "test", "test")
					.execute(bus)
					.getSync();
	}

	private String generateId() {
		return SnomedRequests.identifiers()
					.prepareGenerate()
					.setNamespace(Concepts.B2I_NAMESPACE)
					.setCategory(ComponentCategory.CONCEPT)
					.setQuantity(1)
					.build(REPOSITORY_ID)
					.execute(bus)
				.getSync().first().orElseThrow(() -> new IllegalStateException("Couldn't generate identifier concept ID"));
	}

	private SnomedRelationshipCreateRequestBuilder relationshipBuilder(String typeId, CharacteristicType characteristicType, String desctinationId) {
		return SnomedRequests.prepareNewRelationship()
				.setIdFromNamespace(Concepts.B2I_NAMESPACE)
				.setModuleId(Concepts.MODULE_SCT_CORE)
				.setDestinationId(desctinationId)
				.setTypeId(typeId)
				.setCharacteristicType(characteristicType);
	}

	private SnomedDescriptionCreateRequestBuilder descriptionBuilder(String typeId, String term) {
		return SnomedRequests.prepareNewDescription()
				.setIdFromNamespace(Concepts.B2I_NAMESPACE)
				.setModuleId(Concepts.MODULE_SCT_CORE)
				.setTerm(term)
				.setTypeId(typeId)
				.preferredIn(Concepts.REFSET_LANGUAGE_TYPE_UK);
	}
	
	private SnomedRefSetMemberCreateRequestBuilder memberBuilder(String refsetId, String targetConcept) {
		return SnomedRequests.prepareNewMember()
				.setModuleId(Concepts.MODULE_SCT_CORE)
				.setActive(true)
				.setReferenceSetId(refsetId)
				.setReferencedComponentId(targetConcept)
				.setId(UUID.randomUUID().toString());
	}
	
	private List<AbstractSnomedDsvExportItem> transformToExportItems(final Iterable<SnomedConstraint> constraints) {

		List<AbstractSnomedDsvExportItem> results = Lists.newArrayList();
		

		for (final SnomedConstraint constraint : constraints) {
			if (constraint instanceof SnomedDescriptionConstraint) {
				final String descriptionTypeId = ((SnomedDescriptionConstraint) constraint).getTypeId();
				final ComponentIdSnomedDsvExportItem descriptionExportItem = new ComponentIdSnomedDsvExportItem(SnomedDsvExportItemType.DESCRIPTION, descriptionTypeId);
				results.add(descriptionExportItem);
			} else if (constraint instanceof SnomedRelationshipConstraint) {
				final SnomedRelationshipConstraint relationshipConstraint = (SnomedRelationshipConstraint) constraint;
				final String matchingType = relationshipConstraint.getType();
				final ComponentIdSnomedDsvExportItem relationshipExportItem = new ComponentIdSnomedDsvExportItem(SnomedDsvExportItemType.RELATIONSHIP, matchingType);
				results.add(relationshipExportItem);
			} else if (constraint instanceof SnomedConcreteDomainConstraint) {
				final SnomedConcreteDomainConstraint concreteDomainConstraint = (SnomedConcreteDomainConstraint) constraint;
				final String dataTypeName = concreteDomainConstraint.getValueType().getName();
				final boolean dataTypeBoolean = DataType.BOOLEAN.equals(concreteDomainConstraint.getValueType());
				
				final DatatypeSnomedDsvExportItem datatypeExportItem = new DatatypeSnomedDsvExportItem(SnomedDsvExportItemType.DATAYPE, dataTypeName, dataTypeBoolean);
				results.add(datatypeExportItem);
			}
		}
		
		results.add(new SimpleSnomedDsvExportItem(SnomedDsvExportItemType.PREFERRED_TERM));
		results.add(new SimpleSnomedDsvExportItem(SnomedDsvExportItemType.MODULE));
		results.add(new SimpleSnomedDsvExportItem(SnomedDsvExportItemType.EFFECTIVE_TIME));
		results.add(new SimpleSnomedDsvExportItem(SnomedDsvExportItemType.STATUS_LABEL));
		results.add(new SimpleSnomedDsvExportItem(SnomedDsvExportItemType.DEFINITION_STATUS));
		
		return results;
	}
}
