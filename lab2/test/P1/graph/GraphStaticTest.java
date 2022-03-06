/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // test IntegerGraph
    @Test
    public void testIntegerGraphadd() {
    	Graph<Integer> graph = Graph.empty();
        Set<Integer> integer = new HashSet<Integer>();
        integer.add(1);
        assertTrue(graph.add(1));
        assertEquals(integer, graph.vertices());
    }
    
    @Test
    public void testIntegerGraphset() {
    	Graph<Integer> graph = Graph.empty();
        Set<Integer> integer = new HashSet<Integer>();
        integer.add(3);
        integer.add(4);
        Map<Integer, Integer> source = new HashMap<>();
        source.put(3, 666);
        Map<Integer, Integer> target = new HashMap<>();
        target.put(4, 666);
        assertEquals(0, graph.set(3, 4, 666));
        assertEquals(integer, graph.vertices());
        assertEquals(source, graph.sources(4));
        assertEquals(target, graph.targets(3));
    }
    
    @Test
    public void testIntegerGraphremove() {
    	Graph<Integer> graph = Graph.empty();
    	graph.set(5, 6, 7);
    	graph.add(8);
        Set<Integer> integer = new HashSet<Integer>();
        integer.add(6);
        integer.add(8);
        assertTrue(graph.remove(5));
        assertEquals(integer, graph.vertices());
        assertEquals(Collections.emptyMap(), graph.sources(6));
    }
    
    @Test
    public void testIntegerGraphvertices() {
    	Graph<Integer> graph = Graph.empty();
    	graph.set(12, 24, 36);
        Set<Integer> integer = new HashSet<Integer>();
        integer.add(12);
        integer.add(24);
        assertEquals(integer, graph.vertices());
    }
    
    @Test
    public void testIntegerGraphsources() {
    	Graph<Integer> graph = Graph.empty();
        graph.set(10, 20, 100);
        Map<Integer, Integer> source = new HashMap<>();
        source.put(10, 100);
        assertEquals(source, graph.sources(20));
    }
    
    @Test
    public void testIntegerGraphtargets() {
    	Graph<Integer> graph = Graph.empty();
        graph.set(10, 20, 100);
        Map<Integer, Integer> target = new HashMap<>();
        target.put(20, 100);
        assertEquals(target, graph.targets(10));
    }
    
    // test DoubleGraph
    @Test
    public void testDoubleGraphadd() {
    	Graph<Double> graph = Graph.empty();
        Set<Double> d = new HashSet<Double>();
        d.add(1.1);
        assertTrue(graph.add(1.1));
        assertEquals(d, graph.vertices());
    }
    
    @Test
    public void testDoubleGraphset() {
    	Graph<Double> graph = Graph.empty();
        Set<Double> d = new HashSet<Double>();
        d.add(3.14);
        d.add(4.0);
        Map<Double, Integer> source = new HashMap<>();
        source.put(3.14, 666);
        Map<Double, Integer> target = new HashMap<>();
        target.put(4.0, 666);
        assertEquals(0, graph.set(3.14, 4.0, 666));
        assertEquals(d, graph.vertices());
        assertEquals(source, graph.sources(4.0));
        assertEquals(target, graph.targets(3.14));
    }
    
    @Test
    public void testDoubleGraphremove() {
    	Graph<Double> graph = Graph.empty();
    	graph.set(5.6, 6.7, 7);
    	graph.add(8.9);
        Set<Double> d = new HashSet<Double>();
        d.add(6.7);
        d.add(8.9);
        assertTrue(graph.remove(5.6));
        assertEquals(d, graph.vertices());
        assertEquals(Collections.emptyMap(), graph.sources(6.7));
    }
    
    @Test
    public void testDoubleGraphvertices() {
    	Graph<Double> graph = Graph.empty();
    	graph.set(1.2, 2.4, 36);
        Set<Double> d = new HashSet<Double>();
        d.add(1.2);
        d.add(2.4);
        assertEquals(d, graph.vertices());
    }
    
    @Test
    public void testDoubleGraphsources() {
    	Graph<Double> graph = Graph.empty();
        graph.set(10.10, 20.01, 100);
        Map<Double, Integer> source = new HashMap<>();
        source.put(10.10, 100);
        assertEquals(source, graph.sources(20.01));
    }
    
    @Test
    public void testDoubleGraphtargets() {
    	Graph<Double> graph = Graph.empty();
        graph.set(10.10, 20.01, 100);
        Map<Double, Integer> target = new HashMap<>();
        target.put(20.01, 100);
        assertEquals(target, graph.targets(10.10));
    }
}
