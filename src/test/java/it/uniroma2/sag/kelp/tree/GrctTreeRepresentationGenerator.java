package it.uniroma2.sag.kelp.tree;

import org.junit.Assert;
import org.junit.Test;

import it.uniroma2.sag.kelp.data.representation.tree.TreeRepresentation;
import it.uniroma2.sag.kelp.input.parser.DependencyParser;
import it.uniroma2.sag.kelp.input.parser.impl.StanfordParserWrapper;
import it.uniroma2.sag.kelp.input.parser.model.DependencyGraph;
import it.uniroma2.sag.kelp.input.tree.TreeRepresentationGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.IntermediateLemmaPOSLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.IntermediateNodeLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.LemmaCompactPOSLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.LexicalLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.SyntLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.RelationNameLabelGenerator;

public class GrctTreeRepresentationGenerator {

	@Test
	public void testGRCTExtraction1() {
		DependencyParser parser = new StanfordParserWrapper();
		parser.initialize();

		DependencyGraph parse = parser.parse("The cat runs over the grass.");
		SyntLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalLabelGenerator ng = new LemmaCompactPOSLabelGenerator();
		IntermediateNodeLabelGenerator ig = new IntermediateLemmaPOSLabelGenerator();
		TreeRepresentation grctGenerator = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		String grct = grctGenerator.getTextFromData().replace("(", "[").replace(")", "]");
		System.out.println(grct);
		String expected = "[SYNT##root[SYNT##nsubj[SYNT##det[POS##DT[LEX##the::d]]][POS##NN[LEX##cat::n]]][POS##VBZ[LEX##run::v]][SYNT##nmod[SYNT##case[POS##IN[LEX##over::i]]][SYNT##det[POS##DT[LEX##the::d]]][POS##NN[LEX##grass::n]]][SYNT##punct[POS##.[LEX##.::.]]]]";
		Assert.assertEquals(expected, grct);
	}
	
	@Test
	public void testGRCTExtraction2() {
		DependencyParser parser = new StanfordParserWrapper();
		parser.initialize();

		DependencyGraph parse = parser.parse("Yesterday, I was at the sea looking for a shell.");
		SyntLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalLabelGenerator ng = new LemmaCompactPOSLabelGenerator();
		IntermediateNodeLabelGenerator ig = new IntermediateLemmaPOSLabelGenerator();
		TreeRepresentation grctGenerator = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		String grct = grctGenerator.getTextFromData().replace("(", "[").replace(")", "]");
		System.out.println(grct);
		String expected = "[SYNT##root[SYNT##nmod:tmod[POS##NN[LEX##yesterday::n]]][SYNT##punct[POS##,[LEX##,::,]]][SYNT##nsubj[POS##PRP[LEX##i::p]]][SYNT##aux[POS##VBD[LEX##be::v]]][SYNT##advmod[SYNT##case[POS##IN[LEX##at::i]]][SYNT##det[POS##DT[LEX##the::d]]][POS##NN[LEX##sea::n]]][POS##VBG[LEX##look::v]][SYNT##nmod[SYNT##case[POS##IN[LEX##for::i]]][SYNT##det[POS##DT[LEX##a::d]]][POS##NN[LEX##shell::n]]][SYNT##punct[POS##.[LEX##.::.]]]]";
		Assert.assertEquals(expected, grct);
	}
}
