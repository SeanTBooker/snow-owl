/*
 * Copyright 2011-2016 B2i Healthcare Pte Ltd, http://b2i.sg
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
package com.b2international.index.lucene.tests;

import static org.junit.Assert.*;

import java.util.Objects;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.b2international.index.FSIndexAdmin;
import com.b2international.index.IndexClient;
import com.b2international.index.LuceneClient;
import com.b2international.index.read.Searcher;
import com.b2international.index.write.Writer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @since 4.7
 */
public class LuceneClientTest {

	private static final String TYPE = "type";
	private static final String KEY = "key";

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	private IndexClient client;

	@Before
	public void givenClient() {
		final ObjectMapper mapper = new ObjectMapper();
		client = new LuceneClient(new FSIndexAdmin(folder.getRoot(), UUID.randomUUID().toString()), mapper);
		client.admin().create();
	}
	
	@Test
	public void addDocument() throws Exception {
		final Data data = new Data();
		try (final Writer writer = client.writer()) {
			writer.put(TYPE, KEY, data);
			writer.commit();
		}
		try (Searcher searcher = client.searcher()) {
			final Data actual = searcher.get(Data.class, KEY);
			assertEquals(data, actual);
		}
	}
	
	@Test
	public void deleteDocument() throws Exception {
		addDocument();
		try (final Writer writer = client.writer()) {
			writer.remove(TYPE, KEY);
			writer.commit();
		}
		try (Searcher searcher = client.searcher()) {
			assertNull(searcher.get(Data.class, KEY));
		}
	}
	
	@After
	public void after() {
		client.close();
	}
	
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	static class Data {
		String field1 = "field1";
		String field2 = "field2";

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Data other = (Data) obj;
			return Objects.equals(field1, other.field1) && Objects.equals(field2, other.field2); 
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(field1, field2);
		}
		
	}
	
}
