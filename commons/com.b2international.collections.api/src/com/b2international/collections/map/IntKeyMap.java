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
package com.b2international.collections.map;

import java.util.Collection;

import com.b2international.collections.set.IntSet;

/**
 * @since 4.6
 * 
 * @param <V> 
 */
public interface IntKeyMap<V> extends PrimitiveKeyMap {

	boolean containsKey(int key);
	
	@Override
	IntKeyMap<V> dup();

	V get(int key);

	@Override
	IntSet keySet();
	
	@Override
	IntKeyMapIterator<V> mapIterator();
	
	V put(int key, V value);

	V remove(int key);
	
	Collection<V> values();
}
