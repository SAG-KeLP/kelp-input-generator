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

public class CompositionalLctTreeRepresentationGeneratorTest {
	private String testSentence1 = "The cat runs over the grass.";
	private String testSentence2 = "Yesterday, I was at the sea looking for a shell.";
	
	@Test
	public void testCLCTExtractionBasic() {
		DependencyParser parser = new StanfordParserWrapper();
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation lctGenerator1 = TreeRepresentationGenerator.clctGenerator(parse, rg, ng, ig);
		String lct1 = lctGenerator1.getTextFromData();
		System.out.println(lct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(COMP##root<*::*,run::v>(COMP##nsubj<run::v,cat::n>(COMP##det<cat::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##nsubj)(LEX##cat::n))(COMP##nmod<run::v,grass::n>(COMP##case<grass::n,over::i>(POS##IN)(SYNT##case)(LEX##over::i))(COMP##det<grass::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##nmod)(LEX##grass::n))(COMP##punct<run::v,.::.>(POS##.)(SYNT##punct)(LEX##.::.))(POS##VBZ)(SYNT##root)(LEX##run::v))";
		Assert.assertEquals(expected1, lct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation lctGenerator2 = TreeRepresentationGenerator.clctGenerator(parse, rg, ng, ig);
		String lct2 = lctGenerator2.getTextFromData();
		System.out.println(lct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(COMP##root<*::*,look::v>(COMP##nmod:tmod<look::v,yesterday::n>(POS##NN)(SYNT##nmod:tmod)(LEX##yesterday::n))(COMP##punct<look::v,_comma_::_comma_>(POS##,)(SYNT##punct)(LEX##,::,))(COMP##nsubj<look::v,i::p>(POS##PRP)(SYNT##nsubj)(LEX##i::p))(COMP##aux<look::v,be::v>(POS##VBD)(SYNT##aux)(LEX##be::v))(COMP##advmod<look::v,sea::n>(COMP##case<sea::n,at::i>(POS##IN)(SYNT##case)(LEX##at::i))(COMP##det<sea::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##advmod)(LEX##sea::n))(COMP##nmod<look::v,shell::n>(COMP##case<shell::n,for::i>(POS##IN)(SYNT##case)(LEX##for::i))(COMP##det<shell::n,a::d>(POS##DT)(SYNT##det)(LEX##a::d))(POS##NN)(SYNT##nmod)(LEX##shell::n))(COMP##punct<look::v,.::.>(POS##.)(SYNT##punct)(LEX##.::.))(POS##VBG)(SYNT##root)(LEX##look::v))";
		Assert.assertEquals(expected2, lct2);
	}
	
	@Test
	public void testCLCTExtractionCollapsed() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED);
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation lctGenerator1 = TreeRepresentationGenerator.clctGenerator(parse, rg, ng, ig);
		String lct1 = lctGenerator1.getTextFromData();
		System.out.println(lct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(COMP##root<*::*,run::v>(COMP##nsubj<run::v,cat::n>(COMP##det<cat::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##nsubj)(LEX##cat::n))(COMP##nmod:over<run::v,grass::n>(COMP##case<grass::n,over::i>(POS##IN)(SYNT##case)(LEX##over::i))(COMP##det<grass::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##nmod:over)(LEX##grass::n))(COMP##punct<run::v,.::.>(POS##.)(SYNT##punct)(LEX##.::.))(POS##VBZ)(SYNT##root)(LEX##run::v))";
		Assert.assertEquals(expected1, lct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation lctGenerator2 = TreeRepresentationGenerator.clctGenerator(parse, rg, ng, ig);
		String lct2 = lctGenerator2.getTextFromData();
		System.out.println(lct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(COMP##root<*::*,look::v>(COMP##nmod:tmod<look::v,yesterday::n>(POS##NN)(SYNT##nmod:tmod)(LEX##yesterday::n))(COMP##punct<look::v,_comma_::_comma_>(POS##,)(SYNT##punct)(LEX##,::,))(COMP##nsubj<look::v,i::p>(POS##PRP)(SYNT##nsubj)(LEX##i::p))(COMP##aux<look::v,be::v>(POS##VBD)(SYNT##aux)(LEX##be::v))(COMP##advmod<look::v,sea::n>(COMP##case<sea::n,at::i>(POS##IN)(SYNT##case)(LEX##at::i))(COMP##det<sea::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##advmod)(LEX##sea::n))(COMP##nmod:for<look::v,shell::n>(COMP##case<shell::n,for::i>(POS##IN)(SYNT##case)(LEX##for::i))(COMP##det<shell::n,a::d>(POS##DT)(SYNT##det)(LEX##a::d))(POS##NN)(SYNT##nmod:for)(LEX##shell::n))(COMP##punct<look::v,.::.>(POS##.)(SYNT##punct)(LEX##.::.))(POS##VBG)(SYNT##root)(LEX##look::v))";
		Assert.assertEquals(expected2, lct2);
	}
	
	@Test
	public void testCLCTExtractionCollapsedCC() {
		DependencyParser parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED_CCPROCESSED);
		parser.initialize();
		SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
		LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
		PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();
		
		DependencyGraph parse = parser.parse(testSentence1);
		TreeRepresentation lctGenerator1 = TreeRepresentationGenerator.clctGenerator(parse, rg, ng, ig);
		String lct1 = lctGenerator1.getTextFromData();
		System.out.println(lct1.replace("(", "[").replace(")", "]"));
		String expected1 = "(COMP##root<*::*,run::v>(COMP##nsubj<run::v,cat::n>(COMP##det<cat::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##nsubj)(LEX##cat::n))(COMP##nmod:over<run::v,grass::n>(COMP##case<grass::n,over::i>(POS##IN)(SYNT##case)(LEX##over::i))(COMP##det<grass::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##nmod:over)(LEX##grass::n))(COMP##punct<run::v,.::.>(POS##.)(SYNT##punct)(LEX##.::.))(POS##VBZ)(SYNT##root)(LEX##run::v))";
		Assert.assertEquals(expected1, lct1);
		
		parse = parser.parse(testSentence2);
		TreeRepresentation lctGenerator2 = TreeRepresentationGenerator.clctGenerator(parse, rg, ng, ig);
		String lct2 = lctGenerator2.getTextFromData();
		System.out.println(lct2.replace("(", "[").replace(")", "]"));
		String expected2 = "(COMP##root<*::*,look::v>(COMP##nmod:tmod<look::v,yesterday::n>(POS##NN)(SYNT##nmod:tmod)(LEX##yesterday::n))(COMP##punct<look::v,_comma_::_comma_>(POS##,)(SYNT##punct)(LEX##,::,))(COMP##nsubj<look::v,i::p>(POS##PRP)(SYNT##nsubj)(LEX##i::p))(COMP##aux<look::v,be::v>(POS##VBD)(SYNT##aux)(LEX##be::v))(COMP##advmod<look::v,sea::n>(COMP##case<sea::n,at::i>(POS##IN)(SYNT##case)(LEX##at::i))(COMP##det<sea::n,the::d>(POS##DT)(SYNT##det)(LEX##the::d))(POS##NN)(SYNT##advmod)(LEX##sea::n))(COMP##nmod:for<look::v,shell::n>(COMP##case<shell::n,for::i>(POS##IN)(SYNT##case)(LEX##for::i))(COMP##det<shell::n,a::d>(POS##DT)(SYNT##det)(LEX##a::d))(POS##NN)(SYNT##nmod:for)(LEX##shell::n))(COMP##punct<look::v,.::.>(POS##.)(SYNT##punct)(LEX##.::.))(POS##VBG)(SYNT##root)(LEX##look::v))";
		Assert.assertEquals(expected2, lct2);
	}
}
