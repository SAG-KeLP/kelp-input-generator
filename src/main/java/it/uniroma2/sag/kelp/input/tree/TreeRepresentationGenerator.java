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

package it.uniroma2.sag.kelp.input.tree;

import java.util.ArrayList;

import it.uniroma2.sag.kelp.data.representation.structure.LexicalStructureElement;
import it.uniroma2.sag.kelp.data.representation.structure.PosStructureElement;
import it.uniroma2.sag.kelp.data.representation.structure.SyntacticStructureElement;
import it.uniroma2.sag.kelp.data.representation.tree.TreeRepresentation;
import it.uniroma2.sag.kelp.data.representation.tree.node.TreeNode;
import it.uniroma2.sag.kelp.input.parser.model.DGNode;
import it.uniroma2.sag.kelp.input.parser.model.DGRelation;
import it.uniroma2.sag.kelp.input.parser.model.DependencyGraph;
import it.uniroma2.sag.kelp.input.tree.generators.LexicalElementLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.PosElementLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.SyntElementLabelGenerator;

public class TreeRepresentationGenerator {
	public static TreeRepresentation grctGenerator(DependencyGraph g, SyntElementLabelGenerator rg,
			LexicalElementLabelGenerator ng, PosElementLabelGenerator ig) {
		DGNode target = g.getRoot().getTarget();
		int id = 1;
		TreeNode targetKelp = getKelpNode(id, target, null, rg, ng, g);
		TreeNode res = generateGrctRepresentation(target, targetKelp, g.getRoot(), g, rg, ng, ig, id++);
		TreeRepresentation representation = new TreeRepresentation(res);
		return representation;
	}

	private static TreeNode generateGrctRepresentation(DGNode target, TreeNode targetKelpNode, DGRelation r,
			DependencyGraph g, SyntElementLabelGenerator rg, LexicalElementLabelGenerator ng,
			PosElementLabelGenerator ig, int id) {
		ArrayList<TreeNode> rootChildren = new ArrayList<TreeNode>();
		if (g.getRelationsWithSource(target).isEmpty()) {
			ArrayList<TreeNode> posChildren = new ArrayList<TreeNode>();
			posChildren.add(new TreeNode(id++, new LexicalStructureElement(ng.getLemmaLabelOf(target, g),
					ng.getPosLabelOf(target, g).substring(0, 1)), targetKelpNode));

			TreeNode tmp = new TreeNode(id++, new PosStructureElement(ig.getPosLabelOf(target, g)), targetKelpNode);
			tmp.setChildren(posChildren);
			rootChildren.add(tmp);
		} else {
			boolean printedRootNode = false;

			for (DGRelation relation : g.getRelationsWithSource(target)) {
				DGNode childNode = relation.getTarget();

				if (!printedRootNode && (Integer) childNode.getProperties().get("start") > (Integer) target
						.getProperties().get("start")) {
					ArrayList<TreeNode> posChildren = new ArrayList<TreeNode>();
					posChildren.add(new TreeNode(id++,
							new LexicalStructureElement(ng.getLemmaLabelOf(target, g), ng.getPosLabelOf(target, g)),
							targetKelpNode));
					TreeNode tmp = new TreeNode(id++, new PosStructureElement(ig.getPosLabelOf(target, g)),
							targetKelpNode);
					tmp.setChildren(posChildren);
					rootChildren.add(tmp);

					printedRootNode = true;

				}
				if (childNode.equals(target)) {
					return null;
				}

				TreeNode childNoneKelp = getKelpNode(id++, childNode, targetKelpNode, rg, ng, g);
				rootChildren.add(generateGrctRepresentation(childNode, childNoneKelp, relation, g, rg, ng, ig, id));
			}
			if (!printedRootNode) {
				// Print pos-lexical info
				ArrayList<TreeNode> posChildren = new ArrayList<TreeNode>();
				posChildren.add(new TreeNode(id++,
						new LexicalStructureElement(ng.getLemmaLabelOf(target, g), ng.getPosLabelOf(target, g)),
						targetKelpNode));
				TreeNode tmp = new TreeNode(id++, new PosStructureElement(ig.getPosLabelOf(target, g)), targetKelpNode);
				tmp.setChildren(posChildren);
				rootChildren.add(tmp);
			}
		}

		TreeNode root = new TreeNode(id++, new SyntacticStructureElement(rg.getLabelOf(r, g)), targetKelpNode);
		root.setChildren(rootChildren);
		return root;
	}

	public static TreeRepresentation loctGenerator(DependencyGraph g, SyntElementLabelGenerator rg,
			LexicalElementLabelGenerator ng, PosElementLabelGenerator ig) {
		DGNode target = g.getRoot().getTarget();
		int id = 1;
		TreeNode targetKelp = getKelpNode(id, target, null, rg, ng, g);
		TreeNode res = generateLoctRepresentation(target, targetKelp, g.getRoot(), g, rg, ng, ig, id++);
		TreeRepresentation representation = new TreeRepresentation(res);
		return representation;
	}

	private static TreeNode generateLoctRepresentation(DGNode target, TreeNode targetKelp, DGRelation root,
			DependencyGraph g, SyntElementLabelGenerator rg, LexicalElementLabelGenerator ng,
			PosElementLabelGenerator ig, int id) {
		ArrayList<TreeNode> children = new ArrayList<TreeNode>();
		for (DGRelation relation : g.getRelationsWithSource(target)) {
			DGNode child = relation.getTarget();
			TreeNode childKelp = getKelpNode(id++, child, targetKelp, rg, ng, g);
			children.add(generateLoctRepresentation(child, childKelp, relation, g, rg, ng, ig, id));
		}

		TreeNode rootNode = new TreeNode(id++,
				new LexicalStructureElement(ng.getLemmaLabelOf(target, g), ng.getPosLabelOf(target, g)), targetKelp);
		rootNode.setChildren(children);

		return rootNode;
	}

	public static TreeRepresentation lctGenerator(DependencyGraph g, SyntElementLabelGenerator rg,
			LexicalElementLabelGenerator ng, PosElementLabelGenerator ig) {
		DGNode target = g.getRoot().getTarget();
		int id = 1;
		TreeNode targetKelp = getKelpNode(id, target, null, rg, ng, g);
		TreeNode res = generateLctRepresentation(target, targetKelp, g.getRoot(), g, rg, ng, ig, id++);
		TreeRepresentation representation = new TreeRepresentation(res);
		return representation;
	}

	private static TreeNode generateLctRepresentation(DGNode target, TreeNode targetKelp, DGRelation root,
			DependencyGraph g, SyntElementLabelGenerator rg, LexicalElementLabelGenerator ng,
			PosElementLabelGenerator ig, int id) {
		ArrayList<TreeNode> children = new ArrayList<TreeNode>();
		for (DGRelation relation : g.getRelationsWithSource(target)) {
			DGNode child = relation.getTarget();
			TreeNode childKelp = getKelpNode(id++, child, targetKelp, rg, ng, g);
			children.add(generateLctRepresentation(child, childKelp, relation, g, rg, ng, ig, id));
		}
		children.add(new TreeNode(id++, new PosStructureElement(ig.getPosLabelOf(target, g)), targetKelp));
		children.add(new TreeNode(id++, new SyntacticStructureElement(rg.getLabelOf(root, g)), targetKelp));
		TreeNode rootNode = new TreeNode(id++,
				new LexicalStructureElement(ng.getLemmaLabelOf(target, g), ng.getPosLabelOf(target, g)), targetKelp);
		rootNode.setChildren(children);
		return rootNode;
	}

	private static TreeNode getKelpNode(int id, DGNode target, TreeNode father, SyntElementLabelGenerator rg,
			LexicalElementLabelGenerator ng, DependencyGraph g) {
		return new TreeNode(id, new LexicalStructureElement(ng.getLemmaLabelOf(target, g), ng.getPosLabelOf(target, g)),
				father);
	}
}
