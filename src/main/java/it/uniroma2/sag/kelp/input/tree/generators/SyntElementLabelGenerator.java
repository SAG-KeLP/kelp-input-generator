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

package it.uniroma2.sag.kelp.input.tree.generators;

import it.uniroma2.sag.kelp.input.parser.model.DGRelation;
import it.uniroma2.sag.kelp.input.parser.model.DependencyGraph;

public interface SyntElementLabelGenerator {
	/**
	 * Method to get the label to be associated to a SyntacticStructureElement of KeLP.
	 * @param r the relation from which extract the information.
	 * @param g the graph to which the relation belongs.
	 * @return the label.
	 */
	public String getLabelOf(DGRelation r, DependencyGraph g);
}
