kelp-input-generator
=========

[**KeLP**][kelp-site] is the Kernel-based Learning Platform (Filice '15) developed in the [Semantic Analytics Group][sag-site] of
the [University of Roma Tor Vergata][uniroma2-site].

**kelp-input-generator** is a module that can be used to produce feature representations of data. In particular, at the moment it allows to derive tree representations to be used with Tree Kernels in KeLP.
This module depends on the [StanfordParser 3.7.0][stanford-parser] to produce dependency graphs of English sentences. Starting from these graphs, it allows to produce tree representations in the following format: GRCT, LOCT, LCT.
They are a tree view of a dependency graph, as introduced in (Croce et Al, 2011).

Notice that this software has been developed with extensibility in mind. It means that it provides interfaces as well as general data structures that can be extended to support ad-hoc needs.
For example, replacing the StanfordParser is supported by writing only a new Java class.

This module is released as open source software under the Apache 2.0 license and the source code is available on [Github][github].

**Usage Examples**: A detailed example can be found in the package [it.uniroma2.sag.kelp.input.main][example-package-site]

Further examples on how to generate dependency graphs tree representations are contained in the unit tests of the project, in the package: [it.uniroma2.sag.kelp.tree][test-package-site]
For example, let us assume we need to generate a GRCT representation of the sentence "The cat runs over the grass.".

The following code snippet will do the job:

```
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

public void generateGRCT() {
	// Instantiate a new DependencyParser, i.e., a StanfordParser
	DependencyParser parser = new StanfordParserWrapper();
	parser.initialize();
	// Initialize the label generators for: syntactic nodes, lexical nodes and part-of-speech nodes
	SyntElementLabelGenerator rg = new RelationNameLabelGenerator();
	LexicalElementLabelGenerator ng = new LemmaCompactPOSLabelGeneratorLowerCase();
	PosElementLabelGenerator ig = new OriginalPOSLabelGenerator();

	// Parser a sentence
	DependencyGraph parse = parser.parse("The cat runs over the grass.");

	// Generate the KeLP TreeRepresentation
	TreeRepresentation grctGenerator1 = TreeRepresentationGenerator.grctGenerator(parse, rg, ng, ig);
	String grct1 = grctGenerator1.getTextFromData();
	System.out.println(grct1.replace("(", "[").replace(")", "]"));
}

```

At the end of the execution of this method, it should be printed:

```
[SYNT##root[SYNT##nsubj[SYNT##det[POS##DT[LEX##the::d]]][POS##NN[LEX##cat::n]]][POS##VBZ[LEX##run::v]][SYNT##nmod[SYNT##case[POS##IN[LEX##over::i]]][SYNT##det[POS##DT[LEX##the::d]]][POS##NN[LEX##grass::n]]][SYNT##punct[POS##.[LEX##.::.]]]]
```

**Data Structures**

The fundamental data structures in this project are:

* *DependencyParser*: it is the Java interface modeling a Dependency Parser. If you want to replace the Stanford Parser or to add your own dependency parser, you should write a new class implementing this interface.
* *SyntElementLabelGenerator*: it is the Java interface that is responsible to produce the label for the nodes to be used for the KeLP [SyntacticStructureElement][synt-structure] class, corresponding to syntax nodes in the tree representation. An implementation of that class can be found [here][synt-label].
* *PosElementLabelGenerator*: it is the Java interface that is responsible to produce the label for the nodes to be used for the KeLP [PosStructureElement][pos-structure] class, corresponding to part-of-speech nodes in the tree representation. An implementation of that class can be found [here][pos-label].
* *LexicalElementLabelGenerator*: it is the Java interface that is responsible to produce the label for the nodes to be used for the KeLP [LexicalStructureElement][lexical-structure] class, corresponding to lexical nodes in the tree representation. An implementation of that class can be found [here][lexical-label].
* *DependencyGraph*: it is the data structure modeling a dependency graph. It is made of relations (DGRelation) and nodes (DGNode). Notice that the output of a DependencyParser wrapper is a DependencyGraph.
* *TreeRepresentationGenerator*: it contains the methods to generate KeLP TreeRepresentation objects starting from a DependecyGraph. It contains the following methods: grctGenerator, loctGenerator, lctGenerator


**Including kelp-input-generator in your project**

If you want to include the functionalities of this module in your project you can  easily include it with the following [Maven][maven-site] repository:

```
<repositories>
	<repository>
			<id>kelp_repo_snap</id>
			<name>KeLP Snapshots repository</name>
			<releases>
				<enabled>false</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>warn</checksumPolicy>
			</releases>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>fail</checksumPolicy>
			</snapshots>
			<url>http://sag.art.uniroma2.it:8081/artifactory/kelp-snapshot/</url>
		</repository>
		<repository>
			<id>kelp_repo_release</id>
			<name>KeLP Stable repository</name>
			<releases>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>warn</checksumPolicy>
			</releases>
			<snapshots>
				<enabled>false</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>fail</checksumPolicy>
			</snapshots>
			<url>http://sag.art.uniroma2.it:8081/artifactory/kelp-release/</url>
		</repository>
	</repositories>
```

Then, the [Maven][maven-site] dependency for the kelp-input-generator project is:

```
<dependency>
    <groupId>it.uniroma2.sag.kelp</groupId>
    <artifactId>kelp-input-generator</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```

**How to cite KeLP**

If you find KeLP useful in your researches, please cite the following paper:

```
@InProceedings{filice-EtAl:2015:ACL-IJCNLP-2015-System-Demonstrations,
	author = {Filice, Simone and Castellucci, Giuseppe and Croce, Danilo and Basili, Roberto},
	title = {KeLP: a Kernel-based Learning Platform for Natural Language Processing},
	booktitle = {Proceedings of ACL-IJCNLP 2015 System Demonstrations},
	month = {July},
	year = {2015},
	address = {Beijing, China},
	publisher = {Association for Computational Linguistics and The Asian Federation of Natural Language Processing},
	pages = {19--24},
	url = {http://www.aclweb.org/anthology/P15-4004}
}
```

**References**

(Croce '11) Danilo Croce, Alessandro Moschitti, and Roberto Basili. 2011. _Structured lexical similarity via convolution kernels on dependency trees_. In EMNLP, Edinburgh.

[synt-structure]: https://github.com/SAG-KeLP/kelp-additional-kernels/blob/master/src/main/java/it/uniroma2/sag/kelp/data/representation/structure/SyntacticStructureElement.java "Synt Structure Element"
[synt-label]: https://github.com/SAG-KeLP/kelp-input-generator/blob/master/src/main/java/it/uniroma2/sag/kelp/input/tree/generators/SyntElementLabelGenerator.java "Synt Label Generator"
[pos-structure]: https://github.com/SAG-KeLP/kelp-additional-kernels/blob/master/src/main/java/it/uniroma2/sag/kelp/data/representation/structure/SyntacticStructureElement.java "Pos Structure Element"
[pos-label]: https://github.com/SAG-KeLP/kelp-input-generator/blob/master/src/main/java/it/uniroma2/sag/kelp/input/tree/generators/OriginalPOSLabelGenerator.java "Pos Label Generator"
[lexical-structure]: https://github.com/SAG-KeLP/kelp-additional-kernels/blob/master/src/main/java/it/uniroma2/sag/kelp/data/representation/structure/SyntacticStructureElement.java "Lexical Structure Element"
[lexical-label]: https://github.com/SAG-KeLP/kelp-input-generator/blob/master/src/main/java/it/uniroma2/sag/kelp/input/tree/generators/OnlyLemmaLabelGenerator.java "Lexical Label Generator"
[test-package-site]: https://github.com/SAG-KeLP/kelp-input-generator/tree/master/src/test/java/it/uniroma2/sag/kelp/tree "Test Tree Generation"
[example-package-site]: https://github.com/SAG-KeLP/kelp-input-generator/blob/master/src/main/java/it/uniroma2/sag/kelp/input/main "Tree Generation Examples"
[stanford-parser]: https://nlp.stanford.edu/software/lex-parser.shtml "Stanford Parser"
[sag-site]: http://sag.art.uniroma2.it "SAG site"
[uniroma2-site]: http://www.uniroma2.it "University of Roma Tor Vergata"
[maven-site]: http://maven.apache.org "Apache Maven"
[kelp-site]: http://www.kelp-ml.org/ "KeLP website"
[github]: https://github.com/SAG-KeLP
