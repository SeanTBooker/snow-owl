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
package com.b2international.snowowl.datastore.index.mapping;

import org.apache.lucene.document.Document;

import bak.pcj.list.LongList;
import bak.pcj.set.LongSet;

/**
 * @since 4.3
 */
public interface LongCollectionIndexField extends IndexField<Long> {

	LongSet getValueAsLongSet(Document doc);
	
	LongList getValueAsLongList(Document doc);
}