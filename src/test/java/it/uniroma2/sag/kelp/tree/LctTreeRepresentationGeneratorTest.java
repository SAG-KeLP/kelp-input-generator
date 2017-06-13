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

public class LctTreeRepresentationGeneratorTest {
	private String testSentence1 = "The cat runs over the grass.";
	private String testSentence2 = "Yesterday, I was at the sea looking for a shell.";
	
	@Test
	public void testLCTExtractionBasic() {
		DependencyParser parser = new StanfordParserWrapper();
		parser.initialize();

		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.lctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(LEX##run::v(LEX##cat::n(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nsubj))(LEX##grass::n(LEX##over::i(POS##IN)(SYNT##case))(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nmod))(LEX##.::.(POS##.)(SYNT##punct))(POS##VBZ)(SYNT##root))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.lctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(LEX##look::v(LEX##yesterday::n(POS##NN)(SYNT##nmod:tmod))(LEX##,::,(POS##,)(SYNT##punct))(LEX##i::p(POS##PRP)(SYNT##nsubj))(LEX##be::v(POS##VBD)(SYNT##aux))(LEX##sea::n(LEX##at::i(POS##IN)(SYNT##case))(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##advmod))(LEX##shell::n(LEX##for::i(POS##IN)(SYNT##case))(LEX##a::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nmod))(LEX##.::.(POS##.)(SYNT##punct))(POS##VBG)(SYNT##root))";
		Assert.assertEquals(expected2, grct2);
	}
	
	@Test
	public void testLCTExtractionCollapsed() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED);
		parser.initialize();

		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.lctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(LEX##run::v(LEX##cat::n(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nsubj))(LEX##grass::n(LEX##over::i(POS##IN)(SYNT##case))(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nmod:over))(LEX##.::.(POS##.)(SYNT##punct))(POS##VBZ)(SYNT##root))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.lctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(LEX##look::v(LEX##yesterday::n(POS##NN)(SYNT##nmod:tmod))(LEX##,::,(POS##,)(SYNT##punct))(LEX##i::p(POS##PRP)(SYNT##nsubj))(LEX##be::v(POS##VBD)(SYNT##aux))(LEX##sea::n(LEX##at::i(POS##IN)(SYNT##case))(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##advmod))(LEX##shell::n(LEX##for::i(POS##IN)(SYNT##case))(LEX##a::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nmod:for))(LEX##.::.(POS##.)(SYNT##punct))(POS##VBG)(SYNT##root))";
		Assert.assertEquals(expected2, grct2);
	}
	
	@Test
	public void testLCTExtractionCollapsedCC() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED_CCPROCESSED);
		parser.initialize();

		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.lctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(LEX##run::v(LEX##cat::n(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nsubj))(LEX##grass::n(LEX##over::i(POS##IN)(SYNT##case))(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nmod:over))(LEX##.::.(POS##.)(SYNT##punct))(POS##VBZ)(SYNT##root))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.lctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(LEX##look::v(LEX##yesterday::n(POS##NN)(SYNT##nmod:tmod))(LEX##,::,(POS##,)(SYNT##punct))(LEX##i::p(POS##PRP)(SYNT##nsubj))(LEX##be::v(POS##VBD)(SYNT##aux))(LEX##sea::n(LEX##at::i(POS##IN)(SYNT##case))(LEX##the::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##advmod))(LEX##shell::n(LEX##for::i(POS##IN)(SYNT##case))(LEX##a::d(POS##DT)(SYNT##det))(POS##NN)(SYNT##nmod:for))(LEX##.::.(POS##.)(SYNT##punct))(POS##VBG)(SYNT##root))";
		Assert.assertEquals(expected2, grct2);
	}
}
