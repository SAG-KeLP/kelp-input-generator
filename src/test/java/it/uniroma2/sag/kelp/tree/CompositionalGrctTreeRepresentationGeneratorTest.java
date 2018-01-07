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

public class CompositionalGrctTreeRepresentationGeneratorTest {
	private String testSentence1 = "The cat runs over the grass.";
	private String testSentence2 = "Yesterday, I was at the sea looking for a shell.";
	
	@Test
	public void testCGRCTExtractionBasic() {
		DependencyParser parser = new StanfordParserWrapper();
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.cgrctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(COMP##root<*::*,run::v>(COMP##nsubj<run::v,cat::n>(COMP##det<cat::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##cat::n)))(POS##VBZ(LEX##run::v))(COMP##nmod<run::v,grass::n>(COMP##case<grass::n,over::i>(POS##IN(LEX##over::i)))(COMP##det<grass::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##grass::n)))(COMP##punct<run::v,.::.>(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.cgrctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(COMP##root<*::*,look::v>(COMP##nmod:tmod<look::v,yesterday::n>(POS##NN(LEX##yesterday::n)))(COMP##punct<look::v,_comma_::_comma_>(POS##,(LEX##,::,)))(COMP##nsubj<look::v,i::p>(POS##PRP(LEX##i::p)))(COMP##aux<look::v,be::v>(POS##VBD(LEX##be::v)))(COMP##advmod<look::v,sea::n>(COMP##case<sea::n,at::i>(POS##IN(LEX##at::i)))(COMP##det<sea::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##sea::n)))(POS##VBG(LEX##look::v))(COMP##nmod<look::v,shell::n>(COMP##case<shell::n,for::i>(POS##IN(LEX##for::i)))(COMP##det<shell::n,a::d>(POS##DT(LEX##a::d)))(POS##NN(LEX##shell::n)))(COMP##punct<look::v,.::.>(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected2, grct2);
	}
	
	@Test
	public void testCGRCTExtractionCollapsed() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED);
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.cgrctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(COMP##root<*::*,run::v>(COMP##nsubj<run::v,cat::n>(COMP##det<cat::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##cat::n)))(POS##VBZ(LEX##run::v))(COMP##nmod:over<run::v,grass::n>(COMP##case<grass::n,over::i>(POS##IN(LEX##over::i)))(COMP##det<grass::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##grass::n)))(COMP##punct<run::v,.::.>(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.cgrctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(COMP##root<*::*,look::v>(COMP##nmod:tmod<look::v,yesterday::n>(POS##NN(LEX##yesterday::n)))(COMP##punct<look::v,_comma_::_comma_>(POS##,(LEX##,::,)))(COMP##nsubj<look::v,i::p>(POS##PRP(LEX##i::p)))(COMP##aux<look::v,be::v>(POS##VBD(LEX##be::v)))(COMP##advmod<look::v,sea::n>(COMP##case<sea::n,at::i>(POS##IN(LEX##at::i)))(COMP##det<sea::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##sea::n)))(POS##VBG(LEX##look::v))(COMP##nmod:for<look::v,shell::n>(COMP##case<shell::n,for::i>(POS##IN(LEX##for::i)))(COMP##det<shell::n,a::d>(POS##DT(LEX##a::d)))(POS##NN(LEX##shell::n)))(COMP##punct<look::v,.::.>(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected2, grct2);
	}
	
	@Test
	public void testCGRCTExtractionCollapsedCC() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED_CCPROCESSED);
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.cgrctGenerator(parse, rg, ng, ig);
		String grct1 = grctGenerator1.getTextFromData();
		System.out.println(grct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(COMP##root<*::*,run::v>(COMP##nsubj<run::v,cat::n>(COMP##det<cat::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##cat::n)))(POS##VBZ(LEX##run::v))(COMP##nmod:over<run::v,grass::n>(COMP##case<grass::n,over::i>(POS##IN(LEX##over::i)))(COMP##det<grass::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##grass::n)))(COMP##punct<run::v,.::.>(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected1, grct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation grctGenerator2 = TreeRepresentationGenerator.cgrctGenerator(parse, rg, ng, ig);
		String grct2 = grctGenerator2.getTextFromData();
		System.out.println(grct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(COMP##root<*::*,look::v>(COMP##nmod:tmod<look::v,yesterday::n>(POS##NN(LEX##yesterday::n)))(COMP##punct<look::v,_comma_::_comma_>(POS##,(LEX##,::,)))(COMP##nsubj<look::v,i::p>(POS##PRP(LEX##i::p)))(COMP##aux<look::v,be::v>(POS##VBD(LEX##be::v)))(COMP##advmod<look::v,sea::n>(COMP##case<sea::n,at::i>(POS##IN(LEX##at::i)))(COMP##det<sea::n,the::d>(POS##DT(LEX##the::d)))(POS##NN(LEX##sea::n)))(POS##VBG(LEX##look::v))(COMP##nmod:for<look::v,shell::n>(COMP##case<shell::n,for::i>(POS##IN(LEX##for::i)))(COMP##det<shell::n,a::d>(POS##DT(LEX##a::d)))(POS##NN(LEX##shell::n)))(COMP##punct<look::v,.::.>(POS##.(LEX##.::.))))";
		Assert.assertEquals(expected2, grct2);
	}
}
