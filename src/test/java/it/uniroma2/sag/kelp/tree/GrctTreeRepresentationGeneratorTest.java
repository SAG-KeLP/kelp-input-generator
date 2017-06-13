package it.uniroma2.sag.kelp.tree;

import org.junit.Assert;
import org.junit.Test;

import edu.stanford.nlp.ie.machinereading.RelationFeatureFactory.DEPENDENCY_TYPE;
import it.uniroma2.sag.kelp.data.representation.tree.TreeRepresentation;
import it.uniroma2.sag.kelp.input.parser.DependencyParser;
import it.uniroma2.sag.kelp.input.parser.impl.StanfordParserWrapper;
import it.uniroma2.sag.kelp.input.parser.model.DependencyGraph;
import it.uniroma2.sag.kelp.input.tree.TreeRepresentationGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.OriginalPOSLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.PosElementLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.LemmaCompactPOSLabelGeneratorLowerCase;
import it.uniroma2.sag.kelp.input.tree.generators.LexicalElementLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.SyntElementLabelGenerator;
import it.uniroma2.sag.kelp.input.tree.generators.RelationNameLabelGenerator;

public class GrctTreeRepresentationGeneratorTest {
	private String testSentence1 = "The cat runs over the grass.";
	private String testSentence2 = "Yesterday, I was at the sea looking for a shell.";
	
	@Test
	public void testGRCTExtractionBasic() {
		DependencyParser parser = new StanfordParserWrapper();
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(SYNT##root(SYNT##nsubj(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##cat::n)))(POS##VBZ(LEX##run::v))(SYNT##nmod(SYNT##case(POS##IN(LEX##over::i)))(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##grass::n)))(SYNT##punct(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(SYNT##root(SYNT##nmod:tmod(POS##NN(LEX##yesterday::n)))(SYNT##punct(POS##,(LEX##,::,)))(SYNT##nsubj(POS##PRP(LEX##i::p)))(SYNT##aux(POS##VBD(LEX##be::v)))(SYNT##advmod(SYNT##case(POS##IN(LEX##at::i)))(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##sea::n)))(POS##VBG(LEX##look::v))(SYNT##nmod(SYNT##case(POS##IN(LEX##for::i)))(SYNT##det(POS##DT(LEX##a::d)))(POS##NN(LEX##shell::n)))(SYNT##punct(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected2, grct2);
	}
	
	@Test
	public void testGRCTExtractionCollapsed() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED);
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(SYNT##root(SYNT##nsubj(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##cat::n)))(POS##VBZ(LEX##run::v))(SYNT##nmod:over(SYNT##case(POS##IN(LEX##over::i)))(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##grass::n)))(SYNT##punct(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(SYNT##root(SYNT##nmod:tmod(POS##NN(LEX##yesterday::n)))(SYNT##punct(POS##,(LEX##,::,)))(SYNT##nsubj(POS##PRP(LEX##i::p)))(SYNT##aux(POS##VBD(LEX##be::v)))(SYNT##advmod(SYNT##case(POS##IN(LEX##at::i)))(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##sea::n)))(POS##VBG(LEX##look::v))(SYNT##nmod:for(SYNT##case(POS##IN(LEX##for::i)))(SYNT##det(POS##DT(LEX##a::d)))(POS##NN(LEX##shell::n)))(SYNT##punct(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected2, grct2);
	}
	
	@Test
	public void testGRCTExtractionCollapsedCC() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED_CCPROCESSED);
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(SYNT##root(SYNT##nsubj(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##cat::n)))(POS##VBZ(LEX##run::v))(SYNT##nmod:over(SYNT##case(POS##IN(LEX##over::i)))(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##grass::n)))(SYNT##punct(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(SYNT##root(SYNT##nmod:tmod(POS##NN(LEX##yesterday::n)))(SYNT##punct(POS##,(LEX##,::,)))(SYNT##nsubj(POS##PRP(LEX##i::p)))(SYNT##aux(POS##VBD(LEX##be::v)))(SYNT##advmod(SYNT##case(POS##IN(LEX##at::i)))(SYNT##det(POS##DT(LEX##the::d)))(POS##NN(LEX##sea::n)))(POS##VBG(LEX##look::v))(SYNT##nmod:for(SYNT##case(POS##IN(LEX##for::i)))(SYNT##det(POS##DT(LEX##a::d)))(POS##NN(LEX##shell::n)))(SYNT##punct(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected2, grct2);
	}
}
