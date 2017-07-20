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
package it.uniroma2.sag.kelp.input.parser.impl;

import it.uniroma2.sag.kelp.input.parser.DependencyParser;
import it.uniroma2.sag.kelp.input.parser.model.DGNode;
import it.uniroma2.sag.kelp.input.parser.model.DGRelation;
import it.uniroma2.sag.kelp.input.parser.model.DependencyGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ie.machinereading.RelationFeatureFactory.DEPENDENCY_TYPE;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

public class StanfordParserWrapper implements DependencyParser {
	protected StanfordCoreNLP pipeline;
	private DEPENDENCY_TYPE dependencyType;

	public StanfordParserWrapper() {
		this.dependencyType = DEPENDENCY_TYPE.BASIC;
	}

	public StanfordParserWrapper(DEPENDENCY_TYPE type) {
		this.dependencyType = type;
	}

	@Override
	public void initialize() {
		Properties props = new Properties();
		props.put("ssplit.isOneSentence", true);
		props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
		pipeline = new StanfordCoreNLP(props);
	}

	@Override
	public void finalize() throws Throwable {
		pipeline = null;
	}

	@Override
	public DependencyGraph parse(String sentenceString) {
		Annotation document = new Annotation(sentenceString);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		CoreMap sentence = sentences.get(0);
		DependencyGraph graph = new DependencyGraph();
		graph.setSentence(sentenceString);
		graph.setParserName("StanfordParser");
		graph.setParserVersion("3.6.0");
		graph.setNodes(new ArrayList<DGNode>());
		int nId = 1;
		for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
			DGNode node = new DGNode();
			Map<String, Object> nodeProps = new HashMap<String, Object>();
			nodeProps.put("surface", token.originalText());
			nodeProps.put("lemma", token.lemma());
			nodeProps.put("pos", token.tag());
			nodeProps.put("start", token.beginPosition());
			nodeProps.put("end", token.endPosition());
			nodeProps.put("id", nId);
			nId++;
			graph.getNodes().add(node);
			node.setProperties(nodeProps);
		}

		SemanticGraph dependencies = null;

		switch (dependencyType) {
		case BASIC:
			dependencies = sentence.get(BasicDependenciesAnnotation.class);
			break;
		case COLLAPSED:
			dependencies = sentence.get(CollapsedDependenciesAnnotation.class);
			break;
		case COLLAPSED_CCPROCESSED:
			dependencies = sentence
					.get(CollapsedCCProcessedDependenciesAnnotation.class);
			break;
		default:
			dependencies = sentence.get(BasicDependenciesAnnotation.class);
			break;
		}
		dependencies.edgeListSorted();
		List<DGRelation> relations = new ArrayList<DGRelation>();
		for (IndexedWord node : dependencies.vertexListSorted()) {
			DGRelation relation = new DGRelation();
			relation.setProperties(new HashMap<String, Object>());
			DGNode child = graph.getDGNodeById(node.index());
			relation.setTarget(child);

			Collection<IndexedWord> parentsTmp = dependencies.getParents(node);
			ArrayList<IndexedWord> parents = new ArrayList<IndexedWord>();
			for (IndexedWord par : parentsTmp) {
				SemanticGraphEdge edge = dependencies.getEdge(par, node);
				DGNode parent = graph.getDGNodeById(edge.getGovernor().index());
				if (parent.getProperties().get("id") != child.getProperties()
						.get("id"))
					parents.add(par);
			}

			if (parents.isEmpty()) {
				relation.getProperties().put("type", "root");
				relation.getProperties().put("fromId", new Integer(0));
				relation.setSource(null);
				graph.setRoot(relation);
			} else {
				Iterator<IndexedWord> it = parents.iterator();
				while (it.hasNext()) {
					IndexedWord par = it.next();
					SemanticGraphEdge edge = dependencies.getEdge(par, node);
					DGNode parent = graph.getDGNodeById(edge.getGovernor()
							.index());

					relation.setSource(parent);
					relation.getProperties().put("fromId",
							parent.getProperties().get("id"));
					relation.getProperties().put("type",
							edge.getRelation().toString());
				}
			}
			relations.add(relation);
		}

		graph.setRelations(relations);
		return graph;
	}
}
