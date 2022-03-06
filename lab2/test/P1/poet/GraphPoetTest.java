/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
	//   GraphPoet():
    //      the file contains no word, only one word, more than one word
	//      the file contains same words(case-insensitive), different words
	//      the words in the file are all upper cases, lower cases, both cases
	//      the file did not exist, the file exists
	//      only one space between two words, more than one space between two words
	//   poem():
	//      no bridge word between two words, only one bridge between two words, more than one bridge between two words
    //      vertex in the graph, vertex not in the graph
	//      only one space between two poem words, more than one space between two poem words
	//      no poem word, only one poem word, more than one poem word
	//   vertices():
	//      it is actually a method of graph which has already been tested, here will no longer test
	//   sources():
    //      it is actually a method of graph which has already been tested, here will no longer test
	//   targets():
    //      it is actually a method of graph which has already been tested, here will no longer test
	//   toString():
    //      it is actually a method of graph which has already been tested, here will no longer test
	
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //Cover:the file did not exist
    @Test(expected=IOException.class)
    public void testGraphPoetNoFile() throws IOException {
        new GraphPoet(new File("test/P1/poet/nofile.txt"));
    }
    
    //Cover:the file contains no word, the file exists
    @Test
    public void testGraphPoetEmptyFile() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/emptyfile.txt"));
        assertEquals(Collections.emptySet(), gp.vertices());
        assertEquals("Empty Graph\n",gp.toString());
    }
    
    //Cover:the file contains only one word, the file exists
    @Test
    public void testGraphPoetOneWord() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/oneword.txt"));
        Set<String> v = new HashSet<>();
        v.add("oneword");
        assertEquals(v, gp.vertices());
        assertEquals("Vertices : oneword \n",gp.toString());
    }
    
    //Cover:the file contains more than one word, same words, upper cases, the file exists, only one space between two words
    @Test
    public void testGraphPoetUpperCases() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/uppercases.txt"));
        Set<String> v = new HashSet<>();
        v.add("hello");
        v.add("world");
        v.add("new");
        v.add("big");
        v.add("small");
        v.add("middle");
        Map<String, Integer> s = new HashMap<>();
        s.put("world", 1);
        Map<String, Integer> t = new HashMap<>();
        t.put("world", 2);
        assertEquals(v, gp.vertices());
        assertEquals(s, gp.sources("hello"));
        assertEquals(t, gp.targets("hello"));
        assertEquals("Vertices : small new big world middle hello \n"
        		   + "Edges :\nworld -> hello, weight is : 1\nhello -> world, weight is : 2\n"
        		   + "new -> world, weight is : 1\nworld -> new, weight is : 2\n"
        		   + "new -> small, weight is : 1\n"
        		   + "big -> small, weight is : 1\nsmall -> big, weight is : 2\n"
        		   + "middle -> big, weight is : 1\nbig -> middle, weight is : 2\n", gp.toString());
    }
    
    //Cover:the file contains more than one word, different words, lower cases, the file exists, only one space between two words
    @Test
    public void testGraphPoetLowerCases() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/lowercases.txt"));
        Set<String> v = new HashSet<>();
        v.add("hello");
        v.add("world");
        Map<String, Integer> s = new HashMap<>();
        s.put("world", 1);
        Map<String, Integer> t = new HashMap<>();
        t.put("world", 2);
        assertEquals(v, gp.vertices());
        assertEquals(s, gp.sources("hello"));
        assertEquals(t, gp.targets("hello"));
        assertEquals("Vertices : world hello \n"
     		       + "Edges :\nworld -> hello, weight is : 1\nhello -> world, weight is : 2\n", gp.toString());
    }
    
    //Cover:the file contains more than one word, different words, both cases, the file exists, more than one space between two words
    @Test
    public void testGraphPoetBothCases() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/bothcases.txt"));
        Set<String> v = new HashSet<>();
        v.add("hello");
        v.add("world");
        v.add("java");
        v.add("is");
        v.add("amazing!");
        v.add("i");
        v.add("love");
        v.add("programming");
        Map<String, Integer> s = new HashMap<>();
        s.put("amazing!", 1);
        Map<String, Integer> t = new HashMap<>();
        t.put("love", 1);
        assertEquals(v, gp.vertices());
        assertEquals(s, gp.sources("i"));
        assertEquals(t, gp.targets("i"));
        assertEquals("Vertices : love world java amazing! i is hello programming \n"
  		           + "Edges :\nhello -> world, weight is : 1\nworld -> java, weight is : 1\n"
  		           + "java -> is, weight is : 1\nis -> amazing!, weight is : 1\n"
  		           + "amazing! -> i, weight is : 1\ni -> love, weight is : 1\n"
  		           + "love -> programming, weight is : 1\n", gp.toString());
    }
    
    //Cover:no bridge word between two words, vertex not in the graph, only one space between two poem words, more than one poem word
    @Test
    public void testpoemNoBridge() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/uppercases.txt"));
        assertEquals("Java is Amazing!", gp.poem("Java is Amazing!"));
    }
    
    //Cover:only one bridge between two words, vertex in the graph, only one space between two poem words, more than one poem word
    @Test
    public void testpoemOneBridge() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/bothcases.txt"));
        assertEquals("Hello world Java is amazing!", gp.poem("Hello Java amazing!"));
    }
    
    //Cover:more than one bridge between two words, vertex in the graph, only one space between two poem words, more than one poem word
    @Test
    public void testpoemMorethanOneBridge() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/uppercases.txt"));
        assertEquals("World new World Big small big", gp.poem("World World Big big"));
    }
    
    //Cover:only one bridge between two words, vertex not in the graph, more than one space between two poem words, more than one poem word
    @Test
    public void testpoemMorethanOneSpace() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/bothcases.txt"));
        assertEquals("Hello world Java amazing", gp.poem("Hello     Java amazing"));
    }
    
    //Cover:no poem word
    @Test
    public void testpoemNoPoemWord() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/lowercases.txt"));
        assertEquals("", gp.poem(""));
    }
    
    //Cover:vertex not in the graph, only one poem word
    @Test
    public void testpoemOnlyOnePoemWord() throws IOException {
        GraphPoet gp = new GraphPoet(new File("test/P1/poet/lowercases.txt"));
        assertEquals("lab2", gp.poem("lab2"));
    }
    
}
