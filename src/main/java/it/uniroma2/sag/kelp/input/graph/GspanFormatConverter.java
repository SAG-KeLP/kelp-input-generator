/*
 * Copyright 2014-2015 Simone Filice and Giuseppe Castellucci and Danilo Croce and Roberto Basili
 * and Giovanni Da San Martino and Alessandro Moschitti
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

package it.uniroma2.sag.kelp.input.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import it.uniroma2.sag.kelp.data.representation.Representation;
import it.uniroma2.sag.kelp.data.representation.graph.DirectedGraphRepresentation;
import it.uniroma2.sag.kelp.data.representation.structure.StructureElement;
import it.uniroma2.sag.kelp.data.representation.structure.StructureElementFactory;

/**
 * 
 * The class converts data from the gSpan input format for graphs to the KeLP one. 
 * 
 * gSpan format (taken from the readme file inside the package 
 * 				 https://www.cs.ucsb.edu/~xyan/software/gSpan2009-02-20/gSpan6.tar.gz)
 * --- 
 * The input format can be inferred from the file of Chemical_340:
 *  "t # N"	means the Nth graph,
 *  "v M L"	means that the Mth vertex in this graph has label L,
 *  "e P Q L" means that there is an edge connecting the Pth vertex with the
 *  		  Qth vertex. The edge has label L.
 *  M, N, P, Q, and L are integers.
 *  --- 
 * Differently from the format specification above, we allow L to be a String. 
 * In case of a supervised learning task, target values can either be provided in a 
 * separate file (one target value only per row) or added to the row "t # N" as 
 * in "t # N TargetLabel"
 * 
 * @author Giovanni Da San Martino
 *
 */
public class GspanFormatConverter {

	public static final String GRAPH_START_MARKER = "t # ";
	public static final String GRAPH_NODE_MARKER = "v ";
	public static final String GRAPH_EDGE_MARKER = "e ";
	public static final String GRAPH_ID_LABEL_SEPARATOR = " ";
	
	private ArrayList<String> targetLabels;
	private String targetLabelsFileName;
	private int graphIndex; 
	
	public GspanFormatConverter() {
		targetLabels = new ArrayList<String>();
		graphIndex = -1;
		targetLabelsFileName = null;
	}
	
	/**
	 * Given a string encoding a graph in gSpan format, builds a graph object. 
	 * @return a Representation object
	 */
	private Representation buildGraphObjectFromString(String graphString) {
		DirectedGraphRepresentation graph = new DirectedGraphRepresentation();
		//int graphIndex;
		int nodeIndex;
		int secondNodeIndex;
		int contentStartingIndex;
		StructureElement nodeContent = null;
		String[] graphInfo;
		
		graphIndex += 1;
		for (String line : graphString.split(System.lineSeparator())) {
			if(line.startsWith(GRAPH_NODE_MARKER)) {
				contentStartingIndex = line.indexOf(GRAPH_ID_LABEL_SEPARATOR, GRAPH_NODE_MARKER.length());
				nodeIndex = Integer.parseInt(line.substring(GRAPH_NODE_MARKER.length(), contentStartingIndex));
				try {
					nodeContent = StructureElementFactory.getInstance().parseStructureElement(
							line.substring(contentStartingIndex));
				} catch (InstantiationException e) {
					System.err.println("ERROR: cannot parse node content");
					e.printStackTrace();
					System.exit(3);
				}
				graph.addNode(nodeIndex, nodeContent);
				//System.out.println("Found node with index " + nodeIndex + " and content " + nodeContent.getTextFromData());
			} else if (line.startsWith(GRAPH_EDGE_MARKER)) {
				contentStartingIndex = line.indexOf(GRAPH_ID_LABEL_SEPARATOR, GRAPH_EDGE_MARKER.length());
				nodeIndex = Integer.parseInt(line.substring(GRAPH_EDGE_MARKER.length(), contentStartingIndex));
				secondNodeIndex = Integer.parseInt(line.substring(contentStartingIndex+1, 
						line.indexOf(GRAPH_ID_LABEL_SEPARATOR, contentStartingIndex+1)));
				//the label of the edge is currently neglected because the graph kernels implemented do not use it
				graph.addEdge(nodeIndex, secondNodeIndex);
				//System.out.println("Found edge from " + nodeIndex + " to " + secondNodeIndex);
			} else if(line.startsWith(GRAPH_START_MARKER)) {
				graphInfo = line.substring(GRAPH_START_MARKER.length()).split(GRAPH_ID_LABEL_SEPARATOR);
				/*
				 * The index of the graph is currently not used, graphs are written in the 
				 * same order they appear in the file. If graph index is needed, uncomment the following lines
					graphIndex = Integer.parseInt(graphInfo[0]);
				 */
				if(graphInfo.length>1) {
					targetLabels.add(graphInfo[1]);
				}
				//ignore any other info on the same row
			} else if (line.length()>0){ //the String contains unrecognized contents (spurious spaces?)
				System.err.println("The following line contains unrecognized content " + line);
				System.exit(4);
			}
		}
		return (Representation) graph;
	}

	private void readTargetLabelsFromFile(String targetLabelsFileName) {
		BufferedReader br;
		try {
			String line;
			br = new BufferedReader(new FileReader(targetLabelsFileName));
			while ((line = br.readLine()) != null) {
				targetLabels.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: cannot find target labels file " + targetLabelsFileName);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("ERROR: cannot read target labels from file " + targetLabelsFileName);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Convert a file from gSpan to KeLP format.
	 * Target labels are provided in file <code>targetLabelsFileName</code>
	 * (one target label per row, no other chars on the same row) 
	 * The KeLP file is written to file <code>gSpanFileName.klp</code> 
	 * 
	 * @param gSpanFileName
 	 * @param targetLabelsFileName
	 */
	public void convertFile(String gSpanFileName) {
		
		String outputFileName = gSpanFileName + ".klp";
		StringBuilder graphString = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean startGraph = false;
		String line;
		String outputLine; 
		
		if(targetLabelsFileName!=null) {
			readTargetLabelsFromFile(targetLabelsFileName);
		}
		try {
			br = new BufferedReader(new FileReader(gSpanFileName));
			bw = new BufferedWriter(new FileWriter(outputFileName));
			while ((line = br.readLine()) != null) {
				//System.out.println("reading line " + line);
				if(line.startsWith(GRAPH_START_MARKER)) { 
					if(startGraph) { //a whole graph has been read
						//System.out.println("Found a graph:" + graphString.toString());
						outputLine = "|BG:graph| " + buildGraphObjectFromString(graphString.toString()).getTextFromData() + " |EG|"; 
						bw.write(((targetLabels.size()>0)?targetLabels.get(graphIndex)+" ":"") + outputLine + System.lineSeparator());
						System.out.print(" " + (graphIndex+1)); if((graphIndex+1) % 25 == 0) {System.out.println();}
						graphString.setLength(0); //delete current string content
					}
					startGraph = true;
				}
				graphString.append(line);
				graphString.append(System.lineSeparator());
			}
			if (graphString.length()>0) {//there is one last graph to convert
				//System.out.println("Found a last graph:" + graphString.toString());
				outputLine = "|BG:graph| " + buildGraphObjectFromString(graphString.toString()).getTextFromData() + " |EG|";;
				bw.write(((targetLabels.size()>0)?targetLabels.get(graphIndex)+" ":"") + outputLine + System.lineSeparator());
				System.out.println(" " + (graphIndex+1));
			}
			br.close();
			bw.close();
		} catch (FileNotFoundException e) {
			System.err.println(String.format("ERROR: cannot open file %s to convert its "
					+ "content from gSpan to KeLP format",gSpanFileName));
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println(String.format("ERROR: cannot read file %s content while "
					+ "converting it from gSpan to KeLP format",gSpanFileName));
			e.printStackTrace();
			System.exit(2);
		}
		System.out.format("%nConverted file %s from gSpan to KeLP format (output file: %s)%n", gSpanFileName, outputFileName);
	}

	public void setTargetLabelsFileName(String fileName) {
		targetLabelsFileName = fileName;
	}
	
	public static void main(String[] args) {

		if(args.length==0) {
			System.out.format("The class converts files from gSpan format to the KeLP one%n%n"
					+ "Parameters: gSpanFileName [targetLabelFileName]%n%ngSpanFileName: a file "
					+ "in gSpan format%ntargetLabelFileName (optional): if target labels are in a separate file(if no argument "
					+ "%nOutput file name will be gSpanFileName.klp)");
			System.exit(0);
		}
		
		String gSpanFileName = args[0];
		GspanFormatConverter gSpanConverter = new GspanFormatConverter();
		
		if(args.length>1) {
			gSpanConverter.setTargetLabelsFileName(args[1]);
		}
		
		gSpanConverter.convertFile(gSpanFileName);
		
	}

}
