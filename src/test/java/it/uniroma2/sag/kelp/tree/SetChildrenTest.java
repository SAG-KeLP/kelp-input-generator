package it.uniroma2.sag.kelp.tree;

import java.util.ArrayList;

import org.junit.Test;

import it.uniroma2.sag.kelp.data.representation.structure.LexicalStructureElement;
import it.uniroma2.sag.kelp.data.representation.tree.TreeRepresentation;
import it.uniroma2.sag.kelp.data.representation.tree.node.TreeNode;
import it.uniroma2.sag.kelp.kernel.tree.PartialTreeKernel;

public class SetChildrenTest {

	@Test
	public void testSetChildren() throws Exception {
		TreeRepresentation tree = new TreeRepresentation();
		tree.setDataFromText("(ROOT (develop::v (how::r (GR-advmod) (POS-ADV)) (do::a (GR-aux) (POS-AUX)) (serfdom::r (GR-advmod) (POS-ADV)) (in::a (GR-compound:prt) (POS-ADP)) (leave::v (and::c (GR-cc) (POS-CCONJ)) (then::r (GR-advmod) (POS-ADV)) (russia::p (GR-obj) (POS-PROPN)) (GR-conj) (POS-VERB)) (?::p (GR-punct) (POS-PUNCT)) (ROOT) (POS-VERB)))");
		PartialTreeKernel ptk = new PartialTreeKernel(0.4f, 0.4f, 1.0f, "");
		float initialPtk = ptk.kernelComputation(tree, tree);
		System.out.println("INITIAL PTK: " + initialPtk);
		
		TreeNode leaf = tree.getLeaves().get(0);
		int numberOfNodes = tree.getNumberOfNodes();
		TreeNode node1 = new TreeNode(numberOfNodes+1, new LexicalStructureElement("prova", "posProva"), leaf);
		TreeNode node2 = new TreeNode(numberOfNodes+2, new LexicalStructureElement("prova2", "posProva2"), leaf);
		ArrayList<TreeNode> newNodes = new ArrayList<TreeNode>();
		newNodes.add(node1);
		newNodes.add(node2);
		leaf.setChildren(newNodes);
		
		float finalPtk = ptk.kernelComputation(tree, tree);
		System.out.println("FINAL PTK: " + finalPtk);
		
		TreeRepresentation treeCopy = new TreeRepresentation();
		treeCopy.setDataFromText(tree.getTextFromData());
		float copyPtk = ptk.kernelComputation(treeCopy, treeCopy);
		System.out.println("COPY PTK: " + copyPtk);
		
		tree.updateTree();
		finalPtk = ptk.kernelComputation(tree, tree);
		System.out.println("UPDATED PTK: " + finalPtk);
		
		
	}
	
}
