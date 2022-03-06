/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices) = a mutable directed graph with vertices which have information about their labels,
    //                  their sources and the weights of edges, targets and the weights of edges
    // Representation invariant:
    //   If A is a source of B, then B must be a target of A, and two weights are the same;
    //   If A is a target of B, then B must be a source of A, and two weights are the same;
    //   Every vertex is distinct, no two vertices have the same label
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   vertices(), sources() and targets() make defensive copies and return;
    //   other parameters and return values consist of immutable String, int, boolean or void
    
    /**
     * Create an empty ConcreteVerticesGraph.
     * 
     */
    public ConcreteVerticesGraph() {
    	checkRep();
    }
    
    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep(){
    	int n = vertices.size();
    	if(n >= 2) {
    		
    		for (int i = 0; i <= n - 2; i++) {
    			for (int j = i + 1; j <= n - 1; j++) {
    				assert !(vertices.get(i).getLabel().equals(vertices.get(j).getLabel()));
    			}
    		}
    		
    		for(Vertex<L> vertex : vertices) {
    			L label = vertex.getLabel();
    			for(L source : vertex.getSources().keySet()) {
    				int weight = vertex.getSources().get(source);
    				int k;
    				for(k = 0; k < n; k++) {
    					if(vertices.get(k).getLabel().equals(source)) {
    						assert vertices.get(k).getTargets().containsKey(label);
    						assert vertices.get(k).getTargets().get(label).equals(weight);
    						break;
    					}
    				}
    				assert k < n;
    			}
    		}
    		
    		for(Vertex<L> vertex : vertices) {
    			L label = vertex.getLabel();
    			for(L target : vertex.getTargets().keySet()) {
    				int weight = vertex.getTargets().get(target);
    				int k;
    				for(k = 0; k < n; k++) {
    					if(vertices.get(k).getLabel().equals(target)) {
    						assert vertices.get(k).getSources().containsKey(label);
    						assert vertices.get(k).getSources().get(label).equals(weight);
    						break;
    					}
    				}
    				assert k < n;
    			}
    		}
    		
    	}
    }
    
    @Override public boolean add(L vertex) {
    	Vertex<L> newvertex;
		try {
			newvertex = new Vertex<L>(vertex);
		} catch (Exception e) {
			System.out.println("vertex label can not be empty");
    		return false;
		}
		for (Vertex<L> v : vertices) {
			if (v.getLabel().equals(vertex))
	    		return false;
		}
        boolean b = vertices.add(newvertex);
        checkRep();
        return b;
    }
    
    @Override public int set(L source, L target, int weight) {
    	if (source == null) {
    		System.out.println("source can not be empty");
    		return -1;
    	}
    	if (target == null) {
    		System.out.println("target can not be empty");
    		return -1;
    	}
    	if (weight < 0) {
    		System.out.println("weight can not be set negative");
    		return -1;
    	}
    	
    	int x = 0;
    	if (weight == 0) {
    		int n = vertices.size();
    		if (n == 0) {
    			System.out.println("No such edge, can not remove it.");
        		return -1;
    		}
    		for (int i = 0; i < n; i++) {
        		if (vertices.get(i).getLabel().equals(source)) {
        			try {
    					x = vertices.get(i).setTarget(target, weight);
    				} catch (Exception e) {
    	        		return -1;
    				}
        		}
        		if (vertices.get(i).getLabel().equals(target)) {
        			try {
    					x = vertices.get(i).setSource(source, weight);
    					return x;
    				} catch (Exception e) {
    	        		return -1;
    				}
        		}
        		if (i == n - 1) {
        			System.out.println("No such edge, can not remove it.");
	        		return -1;
        		}
    		}
    	}
    	
        add(source);
        add(target);
        for (Vertex<L> vertex : vertices){
            if(vertex.getLabel().equals(source))
				try {
					x = vertex.setTarget(target, weight);
				} catch (Exception e) {
					return -1;
				}
            if(vertex.getLabel().equals(target))
				try {
					x = vertex.setSource(source, weight);
				} catch (Exception e) {
					return -1;
				}
        }
        checkRep();
        return x;
    	
    }
    
    @Override public boolean remove(L vertex) {
    	int i, n = vertices.size();
    	for (i = 0; i < n; i++) {
    		if (vertices.get(i).getLabel().equals(vertex)) {
    			vertices.remove(i);
    			break;
    		}
    	}
    	if (i == n)
    		return false;
    	
    	for (Vertex<L> v : vertices) {
    		if (v.getSources().containsKey(vertex))
				try {
					v.setSource(vertex, 0);
				} catch (Exception e) {
					return false;
				}
    	}
    	
    	for (Vertex<L> v : vertices) {
    		if (v.getTargets().containsKey(vertex))
				try {
					v.setTarget(vertex, 0);
				} catch (Exception e) {
					return false;
				}
    	}
    	
    	checkRep();
    	return true;
    }
    
    @Override public Set<L> vertices() {
    	Set<L> labels = new HashSet<>();
    	for (Vertex<L> v : vertices) {
    		labels.add(v.getLabel());
    	}
    	checkRep();
    	return labels;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	Map<L, Integer> sources = new HashMap<>();
    	for (Vertex<L> v : vertices) {
    		if (v.getLabel().equals(target)) {
    			sources = v.getSources();
    			break;
    		}
    	}
    	checkRep();
    	return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	Map<L, Integer> targets = new HashMap<>();
    	for (Vertex<L> v : vertices) {
    		if (v.getLabel().equals(source)) {
    			targets = v.getTargets();
    			break;
    		}
    	}
    	checkRep();
    	return targets;
    }
    
    /**
     * print the information of the graph.
     * 
     * @return a string representation of this graph, including the information of vertices
     *         and their sources and targets
     */
    @Override public String toString() {
        String str = "";
        if (vertices.size() == 0) {
        	return "Empty Graph\n";
        }
        for (Vertex<L> vertex : vertices) {
    		str = str.concat(vertex.toString());
    	}
        checkRep();
        return str;
    }
}

/**
 * A vertex of the ConcreteVerticesGraph. It has two Maps, one contains the vertices
 * have an edge to it and the corresponding weight, the other contains the vertices
 * have an edge from it and the corresponding weight.
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
	private final L label;
    private final Map<L, Integer> sources = new HashMap<>();
    private final Map<L, Integer> targets = new HashMap<>();
    
    // Abstraction function:
    //   AF(sources, targets) = a vertex's source vertices which have edges to it(along with the edges' weight),
    //                          and its target vertices which have edges from it(along with the edges' weight) 
    // Representation invariant:
    //   label != null;
    //   every value in sources is positive;
    //   every value in targets is positive
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   label is String, so it is guaranteed immutable;
    //   getSources() and getTargets() make defensive copies and return
    
    /**
     * Create a vertex. Initialize the label of the vertex.
     * 
     * @param label the label of the vertex
     * @throws Exception if label == null
     */
    public Vertex(L label) throws Exception {
    	if(label == null)
			throw new Exception("vertex label can not be empty");
    	this.label = label;
    	checkRep();
    }
    
    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
    	assert label != null;
    	for (int i : sources.values()) {
    		assert i > 0;
    	}
    	for (int j : targets.values()) {
    		assert j > 0;
    	}
    }
    
    /**
     * set source vertex of this vertex
     * 
     * @param source the label of the source vertex
     * @param weight the positive weight of the edge from source vertex to this
     *         vertex, if the weight is zero, remove the previous edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     * @throws Exception if weight < 0, source ==null, weight == 0 but the edge did not exist
     */
    public int setSource(L source, int weight) throws Exception {
    	if(source == null)
			throw new Exception("source vertex can not be set empty");
    	
    	int x;
        if (sources.containsKey(source)) {
        	x = sources.get(source);
        }
        else {
        	x = 0;
        }
        
        if (weight > 0) {
        	sources.put(source, weight);
        }
        else if(weight == 0) {
        	if(x == 0)
        		throw new Exception("the edge did not exist");
        	else
        		sources.remove(source);
        }
        else {
        	throw new Exception("weight can not be set negative");
        }
        checkRep();
        return x;
    }
    
    /**
     * set target vertex of this vertex
     * 
     * @param target the label of the target vertex
     * @param weight the positive weight of the edge from this vertex to target
     *         vertex, if the weight is zero, remove the previous edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     * @throws Exception if weight < 0, target == null, weight == 0 but the edge did not exist
     */
    public int setTarget(L target, int weight) throws Exception {
    	if(target == null)
			throw new Exception("target vertex can not be set empty");
    	
    	int x;
        if (targets.containsKey(target)) {
        	x = targets.get(target);
        }
        else {
        	x = 0;
        }
        
        if (weight > 0) {
        	targets.put(target, weight);
        }
        else if(weight == 0) {
        	if(x == 0)
        		throw new Exception("the edge did not exist");
        	else
        		targets.remove(target);
        }
        else {
        	throw new Exception("weight can not be set negative");
        }
        checkRep();
        return x;
    }
    
    /**
     * get the label of the vertex.
     * 
     * @return the label of the vertex
     */
    public L getLabel() {
    	checkRep();
    	return label;
    }
    
    /**
     * get the source vertices of the vertex, along with the weight of edges
     * 
     * @return a map where the key set is the set of labels of vertices such
     *         that this vertex has an edge from them, and the value for each
     *         key is the positive weight of the edge from the key to this vertex
     */
    public Map<L, Integer> getSources() {
    	Map<L, Integer> newsources = sources;
    	checkRep();
    	return newsources;
    }
    
    /**
     * get the target vertices of the vertex, along with the weight of edges
     * 
     * @return a map where the key set is the set of labels of vertices such
     *         that this vertex has an edge to them, and the value for each
     *         key is the positive weight of the edge from this vertex to the key
     */
    public Map<L, Integer> getTargets() {
    	Map<L, Integer> newtargets = targets;
    	checkRep();
    	return newtargets;
    }
    
    /**
     * print the information of the vertex.
     * 
     * @return a string representation of this vertex
     */
    @Override public String toString() {
        String str = "Vertex : " + label + "\n";
        if (sources.size() > 0) {
        	str = str.concat("Sources :\n");
        }
        for (L s : sources.keySet()) {
    		str = str.concat(s + " weight : " + sources.get(s) + "\n");
    	}
        if (targets.size() > 0) {
        	str = str.concat("Targets :\n");
        }
        for (L s : targets.keySet()) {
    		str = str.concat(s + " weight : " + targets.get(s) + "\n");
    	}
        str = str.concat("\n");
        checkRep();
        return str;
    }
}
