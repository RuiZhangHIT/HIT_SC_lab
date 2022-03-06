/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.*;
import java.util.*;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   AF(graph) = a mutable directed graph initialized with a corpus of text,
    //               vertices in the graph are words, edges in the graph count 
    //               the number of times "w1" is followed by "w2" in the corpus
    // Representation invariant:
    //   If the number of vertices is more than one, every vertex must have at least one edge adjacent to it;
    //   At most one vertex has no source vertices;
    //   At most one vertex has no target vertices;
    //   words are non-empty case-insensitive strings of non-space non-newline characters
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   vertices(), sources() and targets() make defensive copies and return;
    //   other parameters and return values consist of immutable String, boolean or void
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph (more than one
     *        space between two file words is also OK)
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(corpus));
    	String line;
    	while((line = reader.readLine()) != null) {
    		lines.add(line);
    	}
    	reader.close();
    	
    	List<String> words = new ArrayList<>();
    	for(int i = 0; i < lines.size(); i++) {
    		List<String> word = new ArrayList<>(Arrays.asList(lines.get(i).toLowerCase().split(" ")));
    		words.addAll(word);
    	}
    	for(int i = words.size() - 1; i >= 0; i--) {
    		if (words.get(i).equals("")) {
    			words.remove(i);
    		}
    	}
    	
    	if (words.size() == 1) {
    		graph.add(words.get(0));
    	}
    	for (int i = 0; i < words.size() - 1; i++) {
            int weight = graph.set(words.get(i), words.get(i + 1), 1);
            if (weight > 0)
                graph.set(words.get(i), words.get(i + 1), weight + 1);
        }
    	
    	checkRep();
    }
    
    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
    	Set<String> vertices = graph.vertices();
    	int SourceNumber = 0, TargetNumber = 0;
    	if (vertices.size() <= 1) {
    		return ;
    	}
    	
    	for (String vertex : vertices) {
    		assert !vertex.equals("");
    		assert vertex.equals(vertex.toLowerCase());
    		String[] s1 = vertex.split(" ");
    		String[] s2 = vertex.split("\n");
    		assert s1.length == 1;
    		assert s2.length == 1;
    		
    		if (graph.sources(vertex).equals(Collections.emptyMap())) {
    			SourceNumber++;
    			assert !graph.targets(vertex).equals(Collections.emptyMap());
    		}
    		
    		if (graph.targets(vertex).equals(Collections.emptyMap())) {
    			TargetNumber++;
    			assert !graph.sources(vertex).equals(Collections.emptyMap());
    		}
    	}
        assert SourceNumber <= 1;
        assert TargetNumber <= 1;
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem (more than one space between 
     *        two poem words is also OK)
     * @return poem (as described above)
     */
     public String poem(String input) {
     	 List<String> inputword = Arrays.asList(input.split(" "));
     	 List<String> inputwords = new ArrayList<>();
     	 inputwords.addAll(inputword);
    	 for(int i = inputwords.size() - 1; i >= 0; i--) {
     		if (inputwords.get(i).equals("")) {
     			inputwords.remove(i);
     		}
     	 }
    	 
    	 if (inputwords.size() == 0)
    		 return "";
    	 
    	 String output = inputwords.get(0);
    	 if (inputwords.size() == 1)
    		 return output;
    	 
    	 for (int i = 0; i < inputwords.size() - 1; i++) {
    		 output = output.concat(" ");
    		 Map<String, Integer> target = graph.targets(inputwords.get(i).toLowerCase());
             Map<String, Integer> source = graph.sources(inputwords.get(i + 1).toLowerCase());
             int weight = 0, newweight;
             String bridge = null;
    		 for (String t : target.keySet()) {
    			 if (source.containsKey(t)) {
    				 newweight = target.get(t) + source.get(t);
    				 if (newweight > weight) {
    					 weight = newweight;
    					 bridge = t;
    				 }
    			 }
    		 }
    		 if (weight > 0) {
    			 output = output.concat(bridge);
    			 output = output.concat(" ");
    		 }
    		 output = output.concat(inputwords.get(i + 1));
    	 }
    	 
    	 checkRep();
    	 return output;
     }
    
    /**
     * Get the vertices of GraphPoet.
     * 
     * @return the vertices of GraphPoet
     */
    public Set<String> vertices() {
    	Set<String> v = graph.vertices();
    	checkRep();
    	return v;
    }
    
    /**
     * Get the sources of target.
     * 
     * @return the sources of target
     */
    public Map<String, Integer> sources(String target) {
    	Map<String, Integer> s = graph.sources(target);
    	checkRep();
    	return s;
    }
    
    /**
     * Get the targets of source.
     * 
     * @return the targets of source
     */
    public Map<String, Integer> targets(String source) {
    	Map<String, Integer> t = graph.targets(source);
    	checkRep();
    	return t;
    }
    
    /**
     * print the information of the graph.
     * 
     * @return a string representation of this graph, including the information of vertices and edges
     */
    @Override public String toString() {
    	checkRep();
        return graph.toString();
    }
}
