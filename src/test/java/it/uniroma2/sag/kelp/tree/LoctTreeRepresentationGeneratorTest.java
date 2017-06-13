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

public class LoctTreeRepresentationGeneratorTest {
	private String testSentence1 = "The cat runs over the grass.";
	private String testSentence2 = "Yesterday, I was at the sea looking for a shell.";
	
	@Test
	public void testLOCTExtractionBasic() {
		DependencyParser parser = new StanfordParserWrapper();
		parser.initialize();

		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.loctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(LEX##run::v(LEX##cat::n(LEX##the::d))(LEX##grass::n(LEX##over::i)(LEX##the::d))(LEX##.::.))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.loctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(LEX##look::v(LEX##yesterday::n)(LEX##,::,)(LEX##i::p)(LEX##be::v)(LEX##sea::n(LEX##at::i)(LEX##the::d))(LEX##shell::n(LEX##for::i)(LEX##a::d))(LEX##.::.))";
		Assert.assertEquals(expected2, grct2);
	}
	
	@Test
	public void testLOCTExtractionCollapsed() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED);
		parser.initialize();

		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.loctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(LEX##run::v(LEX##cat::n(LEX##the::d))(LEX##grass::n(LEX##over::i)(LEX##the::d))(LEX##.::.))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.loctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(LEX##look::v(LEX##yesterday::n)(LEX##,::,)(LEX##i::p)(LEX##be::v)(LEX##sea::n(LEX##at::i)(LEX##the::d))(LEX##shell::n(LEX##for::i)(LEX##a::d))(LEX##.::.))";
		Assert.assertEquals(expected2, grct2);
	}
	
	@Test
	public void testLOCTExtractionCollapsedCC() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED_CCPROCESSED);
		parser.initialize();

		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.loctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(LEX##run::v(LEX##cat::n(LEX##the::d))(LEX##grass::n(LEX##over::i)(LEX##the::d))(LEX##.::.))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.loctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(LEX##look::v(LEX##yesterday::n)(LEX##,::,)(LEX##i::p)(LEX##be::v)(LEX##sea::n(LEX##at::i)(LEX##the::d))(LEX##shell::n(LEX##for::i)(LEX##a::d))(LEX##.::.))";
		Assert.assertEquals(expected2, grct2);
	}
}
