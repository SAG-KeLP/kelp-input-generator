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

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a dependency graph of a sentence.
 */
public class DependencyGraph {
	private String sentence;
	private String parserName;
	private String parserVersion;
	private List<DGRelation> relations;
	private DGRelation root;
	private List<DGNode> nodes;

	/**
	 * @return the sentence string associated to this graph.
	 */
	public String getSentence() {
		return sentence;
	}

	/**
	 * Set the sentence associated to this graph.
	 * 
	 * @param sentence
	 */
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	/**
	 * @return the parser name (if any) that produced this graph.
	 */
	public String getParserName() {
		return parserName;
	}

	/**
	 * Set the parser name (if any) that produced this graph.
	 * 
	 * @param parserName
	 */
	public void setParserName(String parserName) {
		this.parserName = parserName;
	}

	/**
	 * 
	 * @return the parser version (if any) that produced this graph.
	 */
	public String getParserVersion() {
		return parserVersion;
	}

	/**
	 * Set the parser version (if any) that produced this graph.
	 * 
	 * @param parserVersion
	 */
	public void setParserVersion(String parserVersion) {
		this.parserVersion = parserVersion;
	}

	/**
	 * @return all the relations of this graph.
	 */
	public List<DGRelation> getRelations() {
		return relations;
	}

	/**
	 * Set the list of relations associated to this graph.
	 * 
	 * @param relations
	 */
	public void setRelations(List<DGRelation> relations) {
		this.relations = relations;
	}

	/**
	 * @return the root relation.
	 */
	public DGRelation getRoot() {
		return root;
	}

	/**
	 * Set the root relation.
	 * 
	 * @param root
	 */
	public void setRoot(DGRelation root) {
		this.root = root;
	}

	/**
	 * 
	 * @return the list of nodes associated to this graph.
	 */
	public List<DGNode> getNodes() {
		return nodes;
	}

	/**
	 * Set the list of nodes associated to this graph.
	 * 
	 * @param nodes
	 */
	public void setNodes(List<DGNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Utility method to retrieve a node by its id.
	 * 
	 * @param index
	 *            the id to search for in the node list.
	 * @return
	 */
	public DGNode getDGNodeById(int index) {
		for (DGNode dgNode : nodes) {
			if ((Integer) dgNode.getProperties().get("id") == index)
				return dgNode;
		}
		return null;
	}

	/**
	 * Utility method to find all the relations with a specific source node.
	 * 
	 * @param node
	 *            the source node.
	 * @return
	 */
	public List<DGRelation> getRelationsWithSource(DGNode node) {
		List<DGRelation> ret = new ArrayList<DGRelation>();
		for (DGRelation dgRelation : relations) {
			if (dgRelation.getSource() != null
					&& dgRelation.getSource().equals(node))
				ret.add(dgRelation);
		}
		return ret;
	}
}
