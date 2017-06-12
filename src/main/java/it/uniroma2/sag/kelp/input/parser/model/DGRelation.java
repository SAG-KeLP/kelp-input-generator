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

import java.util.List;
import java.util.Map;

public class DGRelation {
	private DGNode source;
	private DGNode target;
	private Map<String, Object> properties;

	public DGNode getSource() {
		return source;
	}

	public void setSource(DGNode source) {
		this.source = source;
	}

	public DGNode getTarget() {
		return target;
	}

	public void setTarget(DGNode target) {
		this.target = target;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}