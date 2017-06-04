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

public class DependencyGraph {
	private String sentence;
	private String parserName;
	private String parserVersion;
	private List<DGRelation> relations;
	private DGRelation root;
	private List<DGNode> nodes;

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getParserName() {
		return parserName;
	}

	public void setParserName(String parserName) {
		this.parserName = parserName;
	}

	public String getParserVersion() {
		return parserVersion;
	}

	public void setParserVersion(String parserVersion) {
		this.parserVersion = parserVersion;
	}

	public List<DGRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<DGRelation> relations) {
		this.relations = relations;
	}

	public DGRelation getRoot() {
		return root;
	}

	public void setRoot(DGRelation root) {
		this.root = root;
	}

	public List<DGNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<DGNode> nodes) {
		this.nodes = nodes;
	}

	public DGNode getDGNodeById(int index) {
		for (DGNode dgNode : nodes) {
			if ((Integer) dgNode.getProperties().get("id") == index)
				return dgNode;
		}
		return null;
	}
}
