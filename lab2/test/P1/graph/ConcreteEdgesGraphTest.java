/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   the graph is empty, not empty
    //   the graph contains no vertices, only vertices, both vertices and edges
    
    //Cover: the graph is empty, contains no vertices
    @Test
    public void testtoStringEmpty(){
        ConcreteEdgesGraph<String> graph = new ConcreteEdgesGraph<>();
        assertEquals("Empty Graph\n",graph.toString());
    }
    
    //Cover: the graph is not empty, contains only vertices
    @Test
    public void testtoStringVertices(){
        ConcreteEdgesGraph<String> graph = new ConcreteEdgesGraph<>();
        graph.add("A");
        graph.add("B");
        assertEquals("Vertices : A B \n",graph.toString());
    }
    
    //Cover: the graph is not empty, contains both vertices and edges
    @Test
    public void testtoStringVerticesEdges(){
        ConcreteEdgesGraph<String> graph = new ConcreteEdgesGraph<>();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 2);
        graph.set("B", "C", 3);
        assertEquals("Vertices : A B C \nEdges :\nA -> B, weight is : 2\nB -> C, weight is : 3\n", graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   Edge():
    //      source and target are the same, different
    //      source = null, source != null
    //      target = null, target != null
    //      weight < 0, = 0, > 0
    //   getSource():
    //      source and target are the same, different
    //   getTarget():
    //      source and target are the same, different
    //   getWeight():
    //      source and target are the same, different
    //   toString():
    //      source and target are the same, different
    
    //Cover:source and target are different, source = null, target != null, weight > 0
    @Test(expected=Exception.class)
    public void testEdgeNullSource() throws Exception{
        new Edge<>(null, "B", 1);
    }
    
    //Cover:source and target are different, source != null, target = null, weight > 0
    @Test(expected=Exception.class)
    public void testEdgeNullTarget() throws Exception{
        new Edge<>("A", null, 2);
    }
       
    //Cover:source and target are different, source != null, target != null, weight < 0
    @Test(expected=Exception.class)
    public void testEdgeNegativeWeight() throws Exception{
        new Edge<>("A", "B", -1);
    }
    
    //Cover:source and target are different, source != null, target != null, weight = 0
    @Test(expected=Exception.class)
    public void testEdgeZeroWeight() throws Exception{
    	new Edge<>("A", "B", 0);
    }
    
    //Cover:source and target are different, source != null, target != null, weight > 0
    @Test
    public void testEdgeDifferent() throws Exception{
        Edge<String> edge = new Edge<>("A", "B", 1);
        assertEquals("A",edge.getSource());
        assertEquals("B",edge.getTarget());
        assertEquals(1,edge.getWeight());
        assertEquals("A -> B, weight is : 1\n", edge.toString());
    }
    
    //Cover:source and target are the same, source != null, target != null, weight > 0
    @Test
    public void testEdgeSame() throws Exception{
        Edge<String> edge = new Edge<>("A", "A", 1);
        assertEquals("A",edge.getSource());
        assertEquals("A",edge.getTarget());
        assertEquals(1,edge.getWeight());
        assertEquals("A -> A, weight is : 1\n", edge.toString());
    }
    
    //Cover:source and target are different
    @Test
    public void testgetSourceDifferent() throws Exception{
        Edge<String> edge = new Edge<>("A", "B", 1);
        assertEquals("A",edge.getSource());
    }
    
    //Cover:source and target are the same
    @Test
    public void testgetSourceSame() throws Exception{
        Edge<String> edge = new Edge<>("A", "A", 1);
        assertEquals("A",edge.getSource());
    }
    
    //Cover:source and target are different
    @Test
    public void testgetTargetDifferent() throws Exception{
        Edge<String> edge = new Edge<>("A", "B", 1);
        assertEquals("B",edge.getTarget());
    }
    
    //Cover:source and target are the same
    @Test
    public void testgetTargetSame() throws Exception{
        Edge<String> edge = new Edge<>("A", "A", 1);
        assertEquals("A",edge.getTarget());
    }
    
    //Cover:source and target are different
    @Test
    public void testgetWeightDifferent() throws Exception{
        Edge<String> edge = new Edge<>("A", "B", 1);
        assertEquals(1,edge.getWeight());
    }
    
    //Cover:source and target are the same
    @Test
    public void testgetWeightSame() throws Exception{
        Edge<String> edge = new Edge<>("A", "A", 1);
        assertEquals(1,edge.getWeight());
    }
    
    //Cover:source and target are different
    @Test
    public void testtoStringDifferent() throws Exception{
        Edge<String> edge = new Edge<>("A", "B", 1);
        assertEquals("A -> B, weight is : 1\n", edge.toString());
    }
    
    //Cover:source and target are different
    @Test
    public void testtoStringSame() throws Exception{
        Edge<String> edge = new Edge<>("A", "A", 1);
        assertEquals("A -> A, weight is : 1\n", edge.toString());
    }
}
