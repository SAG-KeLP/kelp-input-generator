/*
 * Copyright 2017 Simone Filice and Giuseppe Castellucci and Danilo Croce and Roberto Basili
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.uniroma2.sag.kelp.input.parser.model;

import java.util.Map;

/**
 * This class models a node in a dependency graph. It is made of a Map<String, Object> that is meant to contain
 * all the information associated to this node (e.g, surface, lemma, part of speech).
 */
public class DGNode {
	private Map<String, Object> properties;

	/**
	 * @return the properties associated to this node.
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Set the properties associated to this node.
	 * @param properties
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}
