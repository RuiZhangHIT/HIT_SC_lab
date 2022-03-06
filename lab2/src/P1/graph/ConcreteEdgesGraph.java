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
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices, edges) = a mutable directed graph with labeled vertices
    //                         and edges with positive weight
    // Representation invariant:
    //   every edge is distinct, no two edges have the same source vertex and target vertex;
    //   the source vertex and target vertex in an edge must be in the vertices
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   vertices() makes defensive copies and return;
    //   other parameters and return values consist of immutable String, int, boolean or void
    
    /**
     * Create an empty ConcreteEdgesGraph.
     * 
     */
    public ConcreteEdgesGraph() {
    	checkRep();
    }

    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep(){
    	int n = edges.size();
    	if(n >= 2) {
    		for(int i = 0; i <= n-2; i++) {
    			L source = edges.get(i).getSource();
    			L target = edges.get(i).getTarget();
    			for(int j = i + 1; j <= n-1; j++) {
    				if(source.equals(edges.get(j).getSource()))
    					assert !target.equals(edges.get(j).getTarget());
    			}
    		}
    	}
    	for (Edge<L> edge : edges){
    		assert vertices.contains(edge.getSource());
    		assert vertices.contains(edge.getTarget());
        }
    }
    
    @Override public boolean add(L vertex) {
    	boolean b = vertices.add(vertex);
    	checkRep();
        return b;
    }
    
    @Override public int set(L source, L target, int weight) {
    	int x = 0;
        if(weight == 0) {
        	for (Edge<L> edge : edges){
            	if(edge.getSource().equals(source) && edge.getTarget().equals(target)){
            		x = edge.getWeight();
            		edges.remove(edge);
            		return x;
            	}
        	}
        }
        Edge<L> newedge;
        try {
        	newedge = new Edge<L>(source, target, weight);
        } catch (Exception e) {
        	if(weight == 0 && source != null && target != null) {
        		System.out.println("No such edge, can not remove it.");
        		return -1;
        	}
        	System.out.println("Illegal parameters, can not set the edge.");
    		return -1;
        }
        vertices.add(source);
        vertices.add(target);
        for (Edge<L> edge : edges){
        	if(edge.getSource().equals(newedge.getSource()) && edge.getTarget().equals(newedge.getTarget())){
        		x = edge.getWeight();
        		edges.remove(edge);
        		break;
        	}
        }
        edges.add(newedge);
        checkRep();
        return x;
    }
    
    @Override public boolean remove(L vertex) {
    	boolean b = vertices.remove(vertex);
    	int n = edges.size();
    	for (int i = n-1; i >= 0; i--) {
    		if(edges.get(i).getSource().equals(vertex) || edges.get(i).getTarget().equals(vertex)) {
    			edges.remove(i);
    		}
    	}
    	checkRep();
        return b;
    }
    
    @Override public Set<L> vertices() {
    	Set<L> newvertices = vertices;
    	checkRep();
    	return newvertices;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	Map<L, Integer> sources = new HashMap<>();
    	for (Edge<L> edge : edges){
        	if(edge.getTarget().equals(target)){
        		sources.put(edge.getSource(), edge.getWeight());
        	}
        }
    	checkRep();
    	return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	Map<L, Integer> targets = new HashMap<>();
    	for (Edge<L> edge : edges){
        	if(edge.getSource().equals(source)){
        		targets.put(edge.getTarget(), edge.getWeight());
        	}
        }
    	checkRep();
    	return targets;
    }
    
    /**
     * print the information of the graph.
     * 
     * @return a string representation of this graph, including the information of vertices and edges
     */
    @Override public String toString() {
        if (edges.isEmpty() && vertices.isEmpty()){
            return "Empty Graph\n";
        }
        String str = "Vertices : ";
        for (L vertice : vertices) {
        	str = str.concat(vertice + " ");
        }
        str = str.concat("\n");
        if (edges.size() == 0) {
        	return str;
        }
        str = str.concat("Edges :\n");
        for (Edge<L> edge : edges){
            str = str.concat(edge.toString());
        }
        checkRep();
        return str;
    }
}

/**
 * An edge of the ConcreteEdgesGraph.It is directed, containing two vertices
 * (one source and one target), and has a positive weight of type int.
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    private final L source;
    private final L target;
    private final int weight;
    
    // Abstraction function:
    //   AF(source, target, weight) = a directed edge with a source vertex,
    //                                a target vertex and a weight 
    // Representation invariant:
    //   weight > 0;
    //   source != null;
    //   target != null
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   source and target are Strings, weight is int, so they are guaranteed immutable;
    //   parameters and return values are immutable String, int or void
    
    /**
     * Create an edge. Initialize the source vertex, target vertex and weight.
     * 
     * @param source the source vertex of the edge
     * @param target the target vertex of the edge
     * @param weight the weight of the edge
     * @throws Exception if source == null, target == null, weight <= 0, they are illegal parameters
     */
	public Edge(L source, L target, int weight) throws Exception {
		if(source == null)
			throw new Exception("source vertex can not be empty");
		if(target == null)
			throw new Exception("target vertex can not be empty");
		if(weight <= 0)
			throw new Exception("weight must be positive");
		this.source = source;
		this.target = target;
		this.weight = weight;
		checkRep();
    }
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
	private void checkRep(){
    	assert weight > 0;
    	assert source != null;
    	assert target != null;
    }
    
	/**
	 * get the source vertex of the edge.
	 * 
	 * @return the source vertex of the edge
	 */
    public L getSource() {
    	checkRep();
    	return source;
    }
    
    /**
	 * get the target vertex of the edge.
	 * 
	 * @return the target vertex of the edge
	 */
    public L getTarget() {
    	checkRep();
    	return target;
    }
    
    /**
	 * get the weight of the edge.
	 * 
	 * @return the weight of the edge
	 */
    public int getWeight() {
    	checkRep();
    	return weight;
    }
    
    /**
     * print the information of the edge.
     * 
     * @return a string representation of this edge
     */
	@Override public String toString() {
		checkRep();
        return source + " -> " + target + ", weight is : " + weight + "\n";
    }
}
