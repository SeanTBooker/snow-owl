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
package com.b2international.snowowl.snomed.exporter.server.sandbox;

import com.b2international.index.query.Expression;
import com.b2international.index.query.Expressions;
import com.b2international.index.revision.RevisionSearcher;
import com.b2international.snowowl.core.date.EffectiveTimes;
import com.b2international.snowowl.snomed.SnomedConstants.Concepts;
import com.b2international.snowowl.snomed.datastore.index.entry.SnomedDocument;
import com.b2international.snowowl.snomed.datastore.index.entry.SnomedRelationshipIndexEntry;
import com.b2international.snowowl.snomed.exporter.server.ComponentExportType;

/**
 * RF2 exporter for all SNOMED CT relationships except stated ones.
 */
public class SnomedInferredRelationshipExporter extends AbstractSnomedRelationshipExporter {

	public SnomedInferredRelationshipExporter(final SnomedExportContext configuration, final RevisionSearcher revisionSearcher, final boolean unpublished) {
		super(configuration, revisionSearcher, unpublished);
	}
	
	@Override
	public ComponentExportType getType() {
		return ComponentExportType.RELATIONSHIP;
	}
	
	@Override
	protected Expression getQueryExpression() {
		if (isUnpublished()) {
			return Expressions.builder()
					.mustNot(SnomedRelationshipIndexEntry.Expressions.characteristicTypeId(Concepts.STATED_RELATIONSHIP))
					.must(SnomedDocument.Expressions.effectiveTime(EffectiveTimes.UNSET_EFFECTIVE_TIME))
					.build();
		} else {
			return Expressions.builder().mustNot(SnomedRelationshipIndexEntry.Expressions.characteristicTypeId(Concepts.STATED_RELATIONSHIP)).build();
		}
	}
	
}
