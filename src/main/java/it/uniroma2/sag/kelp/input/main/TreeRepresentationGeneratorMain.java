/*
 * Copyright 2018 Simone Filice and Giuseppe Castellucci and Danilo Croce and Roberto Basili
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

package it.uniroma2.sag.kelp.input.main;

import java.util.HashSet;
import java.util.List;

import edu.stanford.nlp.ie.machinereading.RelationFeatureFactory.DEPENDENCY_TYPE;
import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.example.SimpleExample;
import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.data.label.StringLabel;
import it.uniroma2.sag.kelp.data.representation.tree.TreeRepresentation;
import it.uniroma2.sag.kelp.input.parser.DependencyParser;
import it.uniroma2.sag.kelp.input.parser.impl.StanfordParserWrapper;
import it.uniroma2.sag.kelp.input.parser.model.DGNode;
import it.uniroma2.sag.kelp.input.parser.model.DGRelation;
import it.uniroma2.sag.kelp.input.parser.model.DependencyGraph;
import it.uniroma2.sag.kelp.input.tree.TreeRepresentationGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.LemmaCompactPOSLabelGeneratorLowerCase;
import it.uniroma2.sag.kelp.input.tree.generators.LexicalElementLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.OriginalPOSLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.PosElementLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.RelationNameLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.SyntElementLabelGenerator;

/**
 * Main class showing how to generate GRCT, LCT, CGRCT and CLCT representations
 * derived from a dependency graph produced by the Stanford Parser
 * 
 * More info about these representations can be found in:
 * 
 * - [Croce et al(2011)] Croce D., Moschitti A., Basili R. (2011) Structured
 * lexical similarity via convolution kernels on dependency trees. In:
 * Proceedings of EMNLP, Edinburgh, Scotland. <br>
 * <br>
 * - [Annesi et al(2014)] Paolo Annesi, Danilo Croce, and Roberto Basili. 2014.
 * Semantic compositionality in tree kernels. In Proc. of CIKM 2014, pages
 * 1029–1038, New York, NY, USA. ACM
 * 
 * @author Danilo Croce
 * 
 */

public class TreeRepresentationGeneratorMain {

	public static void main(String[] args) {

		String sentence = "The dog jumped the wall behind the house.";

		// Instantiate a new DependencyParser, i.e., a StanfordParser
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED);
		parser.initialize();
		// Initialize the label generators for: syntactic nodes, lexical nodes
		// and part-of-speech nodes
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();

		// Parse a sentence
		DependencyGraph parse = parser.parse(sentence);

		// Print the sentence
		List<DGNode> nodes = parse.getNodes();
		List<DGRelation> relations = parse.getRelations();

		System.out.println("================================");
		System.out.println("      Dependency graph");
		System.out.println("================================");
		for (DGNode node : nodes) {
			int id = new Integer(node.getProperties().get("id").toString());
			String surface = node.getProperties().get("surface").toString();
			String lemma = node.getProperties().get("lemma").toString();
			String pos = node.getProperties().get("pos").toString();

			String srcId = "0";
			String relType = "root";
			DGRelation relation = relations.get(id - 1);
			DGNode source = relation.getSource();
			if (source != null) {
				relType = relation.getProperties().get("type").toString();
				srcId = source.getProperties().get("id").toString();
			}
			System.out.println(id + "\t" + surface + "\t" + lemma + "\t" + pos + "\t" + srcId + "\t" + relType);
		}

		System.out.println();

		System.out.println("================================");
		System.out.println("      Tree Representations");
		System.out.println("================================");

		// Generating a Grammatical relation-centered Tree (GRCT).
		TreeRepresentation grctTreeRepresentation = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		print("GRCT", grctTreeRepresentation);

		// Generating a Lexcial centered Tree (LCT).
		TreeRepresentation lctTreeRepresentation = TreeRepresentationGenerator.lctGenerator(parse, rg, ng, ig);
		print("LCT", lctTreeRepresentation);

		// Generating a Compositionally Grammatical relation-centered Tree
		// (GRCT).
		TreeRepresentation cgrctTreeRepresentation = TreeRepresentationGenerator.cgrctGenerator(parse, rg, ng, ig);
		print("CGRCT", cgrctTreeRepresentation);

		// Generating a Compositionally Lexical centered Tree (CLCT).
		TreeRepresentation clctTreeRepresentation = TreeRepresentationGenerator.clctGenerator(parse, rg, ng, ig);
		print("CLCT", clctTreeRepresentation);

		System.out.println();

		System.out.println("================================");
		System.out.println("      Example");
		System.out.println("================================");

		// Creating an example to be used within the algorithms
		Example e = new SimpleExample();
		HashSet<Label> exampleLabels = new HashSet<Label>();
		// Adding the classes
		exampleLabels.add(new StringLabel("classA"));
		exampleLabels.add(new StringLabel("classB"));
		e.setClassificationLabels(exampleLabels);
		// Adding the representations
		e.addRepresentation("grct", grctTreeRepresentation);
		e.addRepresentation("lct", lctTreeRepresentation);
		e.addRepresentation("cgrct", cgrctTreeRepresentation);
		e.addRepresentation("clct", clctTreeRepresentation);

		System.out.println(e);
	}

	private static void print(String repType, TreeRepresentation treeRepresentation) {
		String repString = treeRepresentation.getTextFromData();
		System.out.println(repType + "\t" + repString.replaceAll("\\(", "[").replaceAll("\\)", "]"));
		// System.out.println(repType +"\t"+ repString);
	}

}
