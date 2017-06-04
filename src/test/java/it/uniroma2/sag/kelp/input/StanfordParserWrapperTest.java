package it.uniroma2.sag.kelp.input;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.stanford.nlp.ie.machinereading.RelationFeatureFactory.DEPENDENCY_TYPE;
import it.uniroma2.sag.kelp.input.parser.DependencyParser;
import it.uniroma2.sag.kelp.input.parser.impl.StanfordParserWrapper;
import it.uniroma2.sag.kelp.input.parser.model.DGNode;
import it.uniroma2.sag.kelp.input.parser.model.DGRelation;
import it.uniroma2.sag.kelp.input.parser.model.DependencyGraph;

public class StanfordParserWrapperTest {
	private static DependencyParser parser;

	@Test
	public void testSentence() {
		parser = new StanfordParserWrapper(DEPENDENCY_TYPE.BASIC);
		parser.initialize();

		DependencyGraph parse = parser.parse("The cat runs on the grass.");
		Assert.assertEquals(7, parse.getNodes().size());
		Assert.assertEquals(7, parse.getRelations().size());

		List<DGRelation> relations = parse.getRelations();
		for (DGRelation dgRelation : relations) {
			if (dgRelation.getProperties().get("type").equals("root")) {
				Assert.assertEquals("runs", parse.getRoot().getTarget().getProperties().get("surface"));
				Assert.assertEquals("run", parse.getRoot().getTarget().getProperties().get("lemma"));
				Assert.assertEquals("VBZ", parse.getRoot().getTarget().getProperties().get("pos"));
			} else if (dgRelation.getProperties().get("type").equals("nsubj")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals("cat", target.getProperties().get("surface"));
			} else if (dgRelation.getProperties().get("type").equals("det")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertTrue(source.getProperties().get("surface").equals("cat")
						|| source.getProperties().get("surface").equals("grass"));
				Assert.assertTrue(target.getProperties().get("surface").equals("The")
						|| target.getProperties().get("surface").equals("the"));
			} else if (dgRelation.getProperties().get("type").equals("nmod")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals("grass", target.getProperties().get("surface"));

			} else if (dgRelation.getProperties().get("type").equals("case")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("grass", source.getProperties().get("surface"));
				Assert.assertEquals("on", target.getProperties().get("surface"));
			} else if (dgRelation.getProperties().get("type").equals("punct")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals(".", target.getProperties().get("surface"));
			} else {
				Assert.fail();
			}
		}
	}

	@Test
	public void testSentenceCollapsed() {
		parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED);
		parser.initialize();

		DependencyGraph parse = parser.parse("The cat runs on the grass.");
		Assert.assertEquals(7, parse.getNodes().size());
		Assert.assertEquals(7, parse.getRelations().size());

		List<DGRelation> relations = parse.getRelations();
		for (DGRelation dgRelation : relations) {
			if (dgRelation.getProperties().get("type").equals("root")) {
				Assert.assertEquals("runs", parse.getRoot().getTarget().getProperties().get("surface"));
				Assert.assertEquals("run", parse.getRoot().getTarget().getProperties().get("lemma"));
				Assert.assertEquals("VBZ", parse.getRoot().getTarget().getProperties().get("pos"));
			} else if (dgRelation.getProperties().get("type").equals("nsubj")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals("cat", target.getProperties().get("surface"));
			} else if (dgRelation.getProperties().get("type").equals("det")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertTrue(source.getProperties().get("surface").equals("cat")
						|| source.getProperties().get("surface").equals("grass"));
				Assert.assertTrue(target.getProperties().get("surface").equals("The")
						|| target.getProperties().get("surface").equals("the"));
			} else if (dgRelation.getProperties().get("type").equals("nmod:on")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals("grass", target.getProperties().get("surface"));

			} else if (dgRelation.getProperties().get("type").equals("case")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("grass", source.getProperties().get("surface"));
				Assert.assertEquals("on", target.getProperties().get("surface"));
			} else if (dgRelation.getProperties().get("type").equals("punct")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals(".", target.getProperties().get("surface"));
			} else {
				Assert.fail();
			}
		}
	}

	@Test
	public void testSentenceCCCollapsed() {
		parser = new StanfordParserWrapper(DEPENDENCY_TYPE.COLLAPSED_CCPROCESSED);
		parser.initialize();

		DependencyGraph parse = parser.parse("The cat runs on the grass.");
		Assert.assertEquals(7, parse.getNodes().size());
		Assert.assertEquals(7, parse.getRelations().size());

		List<DGRelation> relations = parse.getRelations();
		for (DGRelation dgRelation : relations) {
			if (dgRelation.getProperties().get("type").equals("root")) {
				Assert.assertEquals("runs", parse.getRoot().getTarget().getProperties().get("surface"));
				Assert.assertEquals("run", parse.getRoot().getTarget().getProperties().get("lemma"));
				Assert.assertEquals("VBZ", parse.getRoot().getTarget().getProperties().get("pos"));
			} else if (dgRelation.getProperties().get("type").equals("nsubj")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals("cat", target.getProperties().get("surface"));
			} else if (dgRelation.getProperties().get("type").equals("det")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertTrue(source.getProperties().get("surface").equals("cat")
						|| source.getProperties().get("surface").equals("grass"));
				Assert.assertTrue(target.getProperties().get("surface").equals("The")
						|| target.getProperties().get("surface").equals("the"));
			} else if (dgRelation.getProperties().get("type").equals("nmod:on")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals("grass", target.getProperties().get("surface"));

			} else if (dgRelation.getProperties().get("type").equals("case")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("grass", source.getProperties().get("surface"));
				Assert.assertEquals("on", target.getProperties().get("surface"));
			} else if (dgRelation.getProperties().get("type").equals("punct")) {
				DGNode source = dgRelation.getSource();
				DGNode target = dgRelation.getTarget();

				Assert.assertEquals("runs", source.getProperties().get("surface"));
				Assert.assertEquals(".", target.getProperties().get("surface"));
			} else {
				Assert.fail();
			}
		}
	}

}
