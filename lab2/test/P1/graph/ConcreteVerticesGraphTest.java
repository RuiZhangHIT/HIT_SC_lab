/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   the graph is empty, not empty
    //   the graph contains no vertices, only vertices, both vertices and edges
    
    //Cover: the graph is empty, contains no vertices
    @Test
    public void testtoStringEmpty(){
    	ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<>();
        assertEquals("Empty Graph\n",graph.toString());
    }
    
    //Cover: the graph is not empty, contains only vertices
    @Test
    public void testtoStringVertices(){
    	ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<>();
        graph.add("A");
        graph.add("B");
        assertEquals("Vertex : A\n\nVertex : B\n\n",graph.toString());
    }
    
    //Cover: the graph is not empty, contains both vertices and edges
    @Test
    public void testtoStringVerticesEdges(){
    	ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<>();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 2);
        graph.set("B", "C", 3);
        assertEquals("Vertex : A\nTargets :\nB weight : 2\n\n"
        		   + "Vertex : B\nSources :\nA weight : 2\nTargets :\nC weight : 3\n\n"
        		   + "Vertex : C\nSources :\nB weight : 3\n\n", graph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   Vertex():
    //      label = null, label != null
    //   setSource():
    //      source = null, source != null
    //      weight < 0, = 0, > 0 
    //   setTarget():
    //      target = null, target != null
    //      weight < 0, = 0, > 0
    //   getLabel():
    //      vertex has only label, has label and sources, has label and targets, has label, sources and targets
    //   getSources():
    //      vertex has sources, has no sources
    //   getTargets():
    //      vertex has targets, has no targets
    //   toString():
    //      vertex has only label, has label and sources, has label and targets, has label, sources and targets
    
    //Cover:label = null
    @Test(expected=Exception.class)
    public void testVertexNullLabel() throws Exception{
        new Vertex<>(null);
    }
    
    //Cover:label != null
    @Test
    public void testVertexLegalLabel() throws Exception{
        Vertex<String> vertex = new Vertex<>("A");
        assertEquals("A", vertex.getLabel());
        assertEquals(Collections.emptyMap(), vertex.getSources());
        assertEquals(Collections.emptyMap(), vertex.getTargets());
        assertEquals("Vertex : A\n\n", vertex.toString());
    }
    
    //Cover:source = null, weight > 0
    @Test(expected=Exception.class)
    public void testsetSourceNullSource() throws Exception{
        Vertex<String> vertex = new Vertex<>("A");
        vertex.setSource(null, 1);
    }
    
    //Cover:source != null, weight < 0
    @Test(expected=Exception.class)
    public void testsetSourceNegativeWeight() throws Exception{
        Vertex<String> vertex = new Vertex<>("A");
        vertex.setSource("B", -1);
    }
    
    //Cover:source != null, weight > 0
    @Test
    public void testsetSourcePositiveWeight() throws Exception{
        Vertex<String> vertex = new Vertex<>("A");
        Map<String, Integer> sources = new HashMap<>();
        sources.put("B", 1);
        assertEquals(0,vertex.setSource("B", 1));
        assertEquals("A", vertex.getLabel());
        assertEquals(sources, vertex.getSources());
        assertEquals(Collections.emptyMap(), vertex.getTargets());
        assertEquals("Vertex : A\nSources :\nB weight : 1\n\n", vertex.toString());
    }
    
    //Cover:source != null, weight > 0
    @Test
    public void testsetSourceChangeWeight() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        Map<String, Integer> sources = new HashMap<>();
        sources.put("B", 3);
        assertEquals(0,vertex.setSource("B", 1));
        assertEquals(1,vertex.setSource("B", 3));
        assertEquals("A", vertex.getLabel());
        assertEquals(sources, vertex.getSources());
        assertEquals(Collections.emptyMap(), vertex.getTargets());
        assertEquals("Vertex : A\nSources :\nB weight : 3\n\n", vertex.toString());
    }
    
    //Cover:source != null, weight = 0
    @Test(expected=Exception.class)
    public void testsetSourceNotExsitEdge() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setSource("B", 0);
    }
    
    //Cover:source != null, weight = 0
    @Test
    public void testsetSourceRemoveEdge() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        assertEquals(0,vertex.setSource("B", 3));
        assertEquals(3,vertex.setSource("B", 0));
        assertEquals("A", vertex.getLabel());
        assertEquals(Collections.emptyMap(), vertex.getSources());
        assertEquals(Collections.emptyMap(), vertex.getTargets());
        assertEquals("Vertex : A\n\n", vertex.toString());
    }
    
    //Cover:target = null, weight > 0
    @Test(expected=Exception.class)
    public void testsetTargetNullTarget() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setTarget(null, 1);
    }
    
    //Cover:target != null, weight < 0
    @Test(expected=Exception.class)
    public void testsetTargetNegativeWeight() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setTarget("B", -1);
    }
    
    //Cover:target != null, weight > 0
    @Test
    public void testsetTargetPositiveWeight() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        Map<String, Integer> targets = new HashMap<>();
        targets.put("B", 1);
        assertEquals(0,vertex.setTarget("B", 1));
        assertEquals("A", vertex.getLabel());
        assertEquals(targets, vertex.getTargets());
        assertEquals(Collections.emptyMap(), vertex.getSources());
        assertEquals("Vertex : A\nTargets :\nB weight : 1\n\n", vertex.toString());
    }
    
    //Cover:target != null, weight > 0
    @Test
    public void testsetTargetChangeWeight() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        Map<String, Integer> targets = new HashMap<>();
        targets.put("B", 3);
        assertEquals(0,vertex.setTarget("B", 1));
        assertEquals(1,vertex.setTarget("B", 3));
        assertEquals("A", vertex.getLabel());
        assertEquals(targets, vertex.getTargets());
        assertEquals(Collections.emptyMap(), vertex.getSources());
        assertEquals("Vertex : A\nTargets :\nB weight : 3\n\n", vertex.toString());
    }
    
    //Cover:target != null, weight = 0
    @Test(expected=Exception.class)
    public void testsetTargetNotExsitEdge() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setTarget("B", 0);
    }
    
    //Cover:target != null, weight = 0
    @Test
    public void testsetTargetRemoveEdge() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        assertEquals(0,vertex.setTarget("B", 3));
        assertEquals(3,vertex.setTarget("B", 0));
        assertEquals("A", vertex.getLabel());
        assertEquals(Collections.emptyMap(), vertex.getSources());
        assertEquals(Collections.emptyMap(), vertex.getTargets());
        assertEquals("Vertex : A\n\n", vertex.toString());
    }
    
    //Cover:vertex has only label
    @Test
    public void testgetLabelOnlyLabel() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        assertEquals("A", vertex.getLabel());
    }
    
    //Cover:vertex has label and sources
    @Test
    public void testgetLabelLabelAndSources() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setSource("B", 3);
        vertex.setSource("C", 5);
        assertEquals("A", vertex.getLabel());
    }
    
    //Cover:vertex has label and targets
    @Test
    public void testgetLabelLabelAndTargets() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setTarget("B", 3);
        vertex.setTarget("C", 5);
        assertEquals("A", vertex.getLabel());
    }
    
    //Cover:vertex has label, sources and targets
    @Test
    public void testgetLabelLabelSourcesAndTargets() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setSource("B", 3);
        vertex.setSource("C", 5);
        vertex.setTarget("B", 3);
        vertex.setTarget("C", 5);
        assertEquals("A", vertex.getLabel());
    }
    
    //Cover:vertex has sources
    @Test
    public void testgetSourcesHasSources() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setSource("B", 3);
        vertex.setSource("C", 5);
        Map<String, Integer> sources = new HashMap<>();
        sources.put("B", 3);
        sources.put("C", 5);
        assertEquals(sources, vertex.getSources());       
    }
    
    //Cover:vertex has no sources
    @Test
    public void testgetSourcesHasNoSources() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        assertEquals(Collections.emptyMap(), vertex.getSources());       
    }
    
    //Cover:vertex has sources
    @Test
    public void testgetTargetsHasTargets() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setTarget("B", 3);
        vertex.setTarget("C", 5);
        Map<String, Integer> targets = new HashMap<>();
        targets.put("B", 3);
        targets.put("C", 5);
        assertEquals(targets, vertex.getTargets());       
    }
    
    //Cover:vertex has no sources
    @Test
    public void testgetTargetsHasNoTargets() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        assertEquals(Collections.emptyMap(), vertex.getTargets());       
    }
    
    //Cover:vertex has only label
    @Test
    public void testtoStringOnlyLabel() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        assertEquals("Vertex : A\n\n", vertex.toString());
    }
    
    //Cover:vertex has label and sources
    @Test
    public void testgetLabeltoStringLabelAndSources() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setSource("B", 3);
        vertex.setSource("C", 5);
        assertEquals("Vertex : A\nSources :\nB weight : 3\nC weight : 5\n\n", vertex.toString());
    }
    
    //Cover:vertex has label and targets
    @Test
    public void testtoStringLabelAndTargets() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setTarget("B", 3);
        vertex.setTarget("C", 5);
        assertEquals("Vertex : A\nTargets :\nB weight : 3\nC weight : 5\n\n", vertex.toString());
    }
    
    //Cover:vertex has label, sources and targets
    @Test
    public void testtoStringLabelSourcesAndTargets() throws Exception{
    	Vertex<String> vertex = new Vertex<>("A");
        vertex.setSource("B", 3);
        vertex.setSource("C", 5);
        vertex.setTarget("B", 3);
        vertex.setTarget("C", 5);
        assertEquals("Vertex : A\nSources :\nB weight : 3\nC weight : 5\nTargets :\nB weight : 3\nC weight : 5\n\n", vertex.toString());
    }
}
