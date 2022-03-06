/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
	// Testing strategy:
	//
    // add():
    //     vertex already existed, did not exist
    // set():
	// 	   weight <0, =0, >0
	//     source already existed, did not exist
	//     target already existed, did not exist
	//     source and target are the same, different
	// remove():
	//     vertex already existed, did not exist
	//     vertex has only edges to it, only edges from it, both edges to and from it, no edges to or from it
	// vertices():
	//     the graph is empty, not empty
	// sources():
    //     vertex already existed, did not exist
	//     vertex has no source, has source(s)
	// targets():
    //     vertex already existed, did not exist
	//     vertex has no target, has target(s)
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    //Cover: vertex did not existed
    @Test
    public void testAddNewVertex(){
        Graph<String> graph = emptyInstance();
        Set<String> string = new HashSet<String>();
        string.add("A");
        assertTrue(graph.add("A"));
        assertEquals(string, graph.vertices());  
    }
    
    //Cover: vertex already existed
    @Test
    public void testAddExistedVertex(){
        Graph<String> graph = emptyInstance();
        graph.add("A");
        Set<String> string = new HashSet<String>();
        string.add("A");
        assertFalse(graph.add("A"));
        assertEquals(string, graph.vertices());
    }
    
    //Cover: weight < 0, source already existed, target already existed, source and target are different
    @Test
    public void testIllegalWeight(){
    	Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals(-1, graph.set("A", "B", -1));
    }
    
    //Cover: weight > 0, source already existed, target did not exist, source and target are different
    @Test
    public void testTargetNotExsit(){
    	Graph<String> graph = emptyInstance();
        graph.add("A");
        Map<String, Integer> sourcevertex = new HashMap<>();
        sourcevertex.put("A", 1);
        Map<String, Integer> targetvertex = new HashMap<>();
        targetvertex.put("B", 1);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        assertEquals(0, graph.set("A", "B", 1));
        assertEquals(string, graph.vertices());
        assertEquals(sourcevertex, graph.sources("B"));
        assertEquals(targetvertex, graph.targets("A"));
    }
    
    //Cover: weight > 0, source did not exist, target already existed, source and target are different
    @Test
    public void testSourceNotExsit(){
    	Graph<String> graph = emptyInstance();
        graph.add("B");
        Map<String, Integer> sourcevertex = new HashMap<>();
        sourcevertex.put("A", 3);
        Map<String, Integer> targetvertex = new HashMap<>();
        targetvertex.put("B", 3);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        assertEquals(0, graph.set("A", "B", 3));
        assertEquals(string, graph.vertices());
        assertEquals(sourcevertex, graph.sources("B"));
        assertEquals(targetvertex, graph.targets("A"));
    }
    
    //Cover: weight > 0, source did not exist, target did not exist, source and target are different
    @Test
    public void testSourceTargetNotExsit(){
    	Graph<String> graph = emptyInstance();
    	Map<String, Integer> sourcevertex = new HashMap<>();
        sourcevertex.put("A", 5);
        Map<String, Integer> targetvertex = new HashMap<>();
        targetvertex.put("B", 5);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        assertEquals(0, graph.set("A", "B", 5));
        assertEquals(string, graph.vertices());
        assertEquals(sourcevertex, graph.sources("B"));
        assertEquals(targetvertex, graph.targets("A"));
    }
    
    //Cover: weight > 0, source did not exist, target did not exist, source and target are the same
    @Test
    public void testSameSourceTarget(){
    	Graph<String> graph = emptyInstance();
    	Map<String, Integer> sourcevertex = new HashMap<>();
        sourcevertex.put("A", 5);
        Map<String, Integer> targetvertex = new HashMap<>();
        targetvertex.put("A", 5);
        Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(0, graph.set("A", "A", 5));
        assertEquals(string, graph.vertices());
        assertEquals(sourcevertex, graph.sources("A"));
        assertEquals(targetvertex, graph.targets("A"));
    }
    
    //Cover: weight > 0, source already existed, target already existed, source and target are different
    @Test
    public void testChangeWeight(){
    	Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 7);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        Map<String, Integer> sourcevertex = new HashMap<>();
        sourcevertex.put("A", 9);
        Map<String, Integer> targetvertex = new HashMap<>();
        targetvertex.put("B", 9);
        assertEquals(7, graph.set("A", "B", 9));
        assertEquals(string, graph.vertices());
        assertEquals(sourcevertex, graph.sources("B"));
        assertEquals(targetvertex, graph.targets("A"));
    }
    
    //Cover: weight = 0, source did not existed, target did not existed, source and target are different
    @Test
    public void testZeroWeightNotExsitEdge(){
    	Graph<String> graph = emptyInstance();
        assertEquals(-1, graph.set("A", "B", 0));
        assertEquals(Collections.emptySet(), graph.vertices());
    }
    
    //Cover: weight = 0, source already existed, target already existed, source and target are different
    @Test
    public void testZeroWeight(){
    	Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 7);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        assertEquals(7, graph.set("A", "B", 0));
        assertEquals(string, graph.vertices());
        assertEquals(Collections.emptyMap(), graph.sources("B"));
        assertEquals(Collections.emptyMap(), graph.targets("A"));
    }
    
    //Cover: vertex already existed, has only edges to it
    @Test
    public void testOnlyEdgesTo() {
    	Graph<String> graph = emptyInstance();
        graph.set("A", "C", 1);
        graph.set("B", "C", 2);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        assertTrue(graph.remove("C"));
        assertEquals(string, graph.vertices());
        assertEquals(Collections.emptyMap(), graph.targets("A"));
        assertEquals(Collections.emptyMap(), graph.targets("B"));
    }
    
    //Cover: vertex already existed, has only edges from it
    @Test
    public void testOnlyEdgesFrom() {
    	Graph<String> graph = emptyInstance();
        graph.set("A", "B", 1);
        graph.set("A", "C", 2);
        Set<String> string = new HashSet<String>();
        string.add("B");
        string.add("C");
        assertTrue(graph.remove("A"));
        assertEquals(string, graph.vertices());
        assertEquals(Collections.emptyMap(), graph.sources("B"));
        assertEquals(Collections.emptyMap(), graph.sources("C"));
    }
    
    //Cover: vertex already existed, has both edges to and from it
    @Test
    public void testBothEdgesToAndFrom() {
    	Graph<String> graph = emptyInstance();
        graph.set("A", "B", 1);
        graph.set("B", "C", 2);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("C");
        assertTrue(graph.remove("B"));
        assertEquals(string, graph.vertices());
        assertEquals(Collections.emptyMap(), graph.targets("A"));
        assertEquals(Collections.emptyMap(), graph.sources("C"));
    }
     
    //Cover: vertex already existed, has no edges to or from it
    @Test
    public void testNoEdgesToOrFrom() {
    	Graph<String> graph = emptyInstance();
    	graph.add("B");
        graph.set("A", "C", 1);
        Map<String, Integer> targetvertex = new HashMap<>();
        targetvertex.put("C", 1);
        Map<String, Integer> sourcevertex = new HashMap<>();
        sourcevertex.put("A", 1);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("C");
        assertTrue(graph.remove("B"));
        assertEquals(string, graph.vertices());
        assertEquals(targetvertex, graph.targets("A"));
        assertEquals(sourcevertex, graph.sources("C"));
    }
    
    //Cover: vertex did not exist, has no edges to or from it
    @Test
    public void testVertexNotExsit() {
    	Graph<String> graph = emptyInstance();
    	graph.set("A", "C", 1);
        Map<String, Integer> targetvertex = new HashMap<>();
        targetvertex.put("C", 1);
        Map<String, Integer> sourcevertex = new HashMap<>();
        sourcevertex.put("A", 1);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("C");
        assertFalse(graph.remove("B"));
        assertEquals(string, graph.vertices());
        assertEquals(targetvertex, graph.targets("A"));
        assertEquals(sourcevertex, graph.sources("C"));
    }
    
    //Cover: graph contains no vertex
    @Test
    public void testEmptygraph(){
        Graph<String> graph=emptyInstance();
        assertEquals(Collections.emptySet(), graph.vertices());
    }
    
    //Cover: graph contains vertexes
    @Test
    public void testNotEmptygraph(){
        Graph<String> graph=emptyInstance();
        graph.add("A");
        graph.set("B", "C", 1);
        Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        string.add("C");
        assertEquals(string, graph.vertices());
    }
    
    //Cover: target already existed, has sources
    @Test
    public void testExsitedTargetHasSources(){
    	Graph<String> graph = emptyInstance();
        graph.set("A", "C", 1);
        graph.set("B", "C", 2);
        Map<String, Integer> sourcevertex = new HashMap<>();
        sourcevertex.put("A", 1);
        sourcevertex.put("B", 2);
        assertEquals(sourcevertex, graph.sources("C"));
    }
    
    //Cover: target already existed, has no source
    @Test
    public void testExsitedTargetHasNoSource(){
    	Graph<String> graph = emptyInstance();
        graph.add("A");
        assertEquals(Collections.emptyMap(), graph.sources("A"));
    }
    
    //Cover: target did not exist, has no source
    @Test
    public void testNotExsitedTarget(){
    	Graph<String> graph = emptyInstance();
        assertEquals(Collections.emptyMap(), graph.sources("A"));
    }
    
    //Cover: source already existed, has targets
    @Test
    public void testExsitedSourceHasTargets(){
    	Graph<String> graph = emptyInstance();
        graph.set("A", "B", 1);
        graph.set("A", "C", 2);
        Map<String, Integer> targetvertex = new HashMap<>();
        targetvertex.put("B", 1);
        targetvertex.put("C", 2);
        assertEquals(targetvertex, graph.targets("A"));
    }
    
    //Cover: source already existed, has no target
    @Test
    public void testExsitedSourceHasNoTarget(){
    	Graph<String> graph = emptyInstance();
        graph.add("A");
        assertEquals(Collections.emptyMap(), graph.targets("A"));
    }
    
    //Cover: source did not exist, has no target
    @Test
    public void testNotExsitedSource(){
    	Graph<String> graph = emptyInstance();
    	assertEquals(Collections.emptyMap(), graph.targets("A"));
    }

}
