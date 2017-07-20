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

package it.uniroma2.sag.kelp.input.parser;

import it.uniroma2.sag.kelp.input.parser.model.DependencyGraph;

/**
 * Interface for a Dependency Parser.
 */
public interface DependencyParser {
	/**
	 * Method intended to initialize the parser.
	 */
	public void initialize();
	
	/**
	 * Method intended to finalize the parser.
	 * @throws Throwable
	 */
	public void finalize() throws Throwable;
	
	/**
	 * Method to parse a sentence.
	 * 
	 * @param sentence The sentence to be parsed.
	 * @return an instance of a DependencyGraph object representing the graph of 
	 * dependencies associated to the sentence.
	 */
	public DependencyGraph parse(String sentence);
}
