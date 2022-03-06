package IntervalSetDecorator;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import IntervalSet.CommonIntervalSet;
import IntervalSet.IntervalSet;

/**
 * Tests for IntervalSetDecorator.
 * 
 */
public class IntervalSetDecoratorTest {
      
	    // Testing strategy:
	    //     the methods are actually methods of IntervalSet, so the testing strategies and Junit tests are the same as IntervalSet
		//
	    // IntervalSetDecorator():
		//     the input IntervalSet intervalset is empty, not empty
	    // insert():
	    //     an interval of the given label already exists, not exist
		//     start < 0, = 0, > 0
		//     start < end, start = end, start > end
		//     the given label is null, not null
		// remove():
		//     an interval with the given label exists, not exist
	    //     the given label is null, not null
		// labels():
		//     the IntervalSetDecorator is empty, not empty
		// start():
	    //     an interval with the given label exists, not exist
	    //     the given label is null, not null
		// end():
	    //     an interval with the given label exists, not exist
	    //     the given label is null, not null
	    // toString()
        //     the IntervalSetDecorator is empty, not empty
	
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
	
	//Cover: the input IntervalSet intervalset is empty
	@Test
    public void testIntervalSetDecoratorEmpty() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
        assertEquals(Collections.emptySet(), intervals.labels());
    }
	
	//Cover: the input IntervalSet intervalset is not empty
	@Test
	public void testIntervalSetDecoratorNotEmpty() {
		IntervalSet<String> interval = new CommonIntervalSet<String>();
		interval.insert(1, 2, "A");
		Set<String> string = new HashSet<String>();
	    string.add("A");
	    IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(interval);   
	    assertEquals(string, intervals.labels());
	    assertEquals(1, intervals.start("A"));
	    assertEquals(2, intervals.end("A"));
	}
    
    //Cover: an interval of the given label not exist, start > 0, start < end, the given label is not null
    @Test
    public void testInsertLabelNotExist() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
        assertEquals(1, intervals.start("A"));
        assertEquals(2, intervals.end("A"));
    }
    
    //Cover: an interval of the given label already exists, start > 0, start < end, the given label is not null
    @Test
    public void testInsertLabelExist() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
        assertEquals(1, intervals.start("A"));
        assertEquals(2, intervals.end("A"));
        intervals.insert(1, 2, "A");
        assertEquals(string, intervals.labels());
        assertEquals(1, intervals.start("A"));
        assertEquals(2, intervals.end("A"));
    }
    
    //Cover: an interval of the given label not exist, start = 0, start < end, the given label is not null
    @Test
    public void testInsertZeroStart() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(0, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
        assertEquals(0, intervals.start("A"));
        assertEquals(2, intervals.end("A"));
    }
    
    //Cover: an interval of the given label not exist, start < 0, start < end, the given label is not null
    @Test
    public void testInsertNegativeStart() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(-1, 2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: an interval of the given label not exist, start > 0, start = end, the given label is not null
    @Test
    public void testInsertStartEqualEnd() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(2, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
    }
    
    //Cover: an interval of the given label not exist, start > 0, start > end, the given label is not null
    @Test
    public void testInsertStartBiggerThanEnd() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(3, -2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: start > 0, start < end, the given label is null
    @Test
    public void testInsertNullLabel() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(1, 2, null);
    	assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the interval of the given label not exist, the given label is not null
    @Test
    public void testRemoveIntervalNotExist() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertFalse(intervals.remove("B"));
        assertEquals(string, intervals.labels());
        assertEquals(1, intervals.start("A"));
        assertEquals(2, intervals.end("A"));
    }
    
    //Cover: the interval of the given label already exists, the given label is not null
    @Test
    public void testRemoveIntervalExist() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(0, 6, "A");
    	assertTrue(intervals.remove("A"));
        assertEquals(Collections.emptySet(), intervals.labels());
        assertEquals(-1, intervals.start("A"));
        assertEquals(-1, intervals.end("A"));
    }
    
    //Cover: the given label is null
    @Test
    public void testRemoveNullLabel() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(3, 6, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
    	assertFalse(intervals.remove(null));
        assertEquals(string, intervals.labels());
    }
    
    //Cover: the IntervalSetDecorator is empty
    @Test
    public void testLabelsIntervalSetEmpty() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the IntervalSetDecorator is not empty
    @Test
    public void testLabelsIntervalSetNotEmpty() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
    }
    
    //Cover: the interval of the given label not exist, the given label is not null
    @Test
    public void testStartIntervalNotExist() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
        assertEquals(-1, intervals.start("A"));
    }
    
    //Cover: the interval of the given label exists, the given label is not null
    @Test
    public void testStartIntervalExist() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(5, 8, "A");
        assertEquals(5, intervals.start("A"));
    }
    
    //Cover: the given label is null
    @Test
    public void testStartNullLabel() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
        assertEquals(-1, intervals.start(null));
    }
    
    //Cover: the interval of the given label not exist, the given label is not null
    @Test
    public void testEndIntervalNotExist() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
        assertEquals(-1, intervals.end("A"));
    }
    
    //Cover: the interval of the given label exists, the given label is not null
    @Test
    public void testEndIntervalExist() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
    	intervals.insert(3, 6, "A");
        assertEquals(6, intervals.end("A"));
    }
    
    //Cover: the given label is null
    @Test
    public void testEndNullLabel() {
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
        assertEquals(-1, intervals.end(null));
    }
    
    //Cover: the IntervalSetDecorator is empty
    @Test
    public void testtoStringEmpty(){
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
        assertEquals("Empty IntervalSet\n",intervals.toString());
    }
    
    //Cover: the IntervalSetDecorator is not empty
    @Test
    public void testtoStringNotEmpty(){
    	IntervalSetDecorator<String> intervals = new IntervalSetDecorator<String>(IntervalSet.empty());
        intervals.insert(2, 8, "lab3");
        assertEquals("Intervals :\n2 -> 8 : lab3\n",intervals.toString());
    }
    
}
