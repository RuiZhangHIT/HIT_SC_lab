package IntervalSet;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for instance methods of IntervalSet.
 * 
 */
public abstract class IntervalSetInstanceTest {
    
	// Testing strategy:
	//
    // insert():
    //     an interval of the given label already exists, not exist
	//     start < 0, = 0, > 0
	//     start < end, start = end, start > end
	//     the given label is null, not null
	// remove():
	//     an interval with the given label exists, not exist
    //     the given label is null, not null
	// labels():
	//     the IntervalSet is empty, not empty
	// start():
    //     an interval with the given label exists, not exist
    //     the given label is null, not null
	// end():
    //     an interval with the given label exists, not exist
    //     the given label is null, not null
	
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty IntervalSet of the particular implementation being tested
     */
    public abstract IntervalSet<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //Cover: an interval of the given label not exist, start > 0, start < end, the given label is not null
    @Test
    public void testInsertLabelNotExist() {
    	IntervalSet<String> intervals = emptyInstance();
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
    	IntervalSet<String> intervals = emptyInstance();
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
    	IntervalSet<String> intervals = emptyInstance();
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
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(-1, 2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: an interval of the given label not exist, start > 0, start = end, the given label is not null
    @Test
    public void testInsertStartEqualEnd() {
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(2, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
    }
    
    //Cover: an interval of the given label not exist, start > 0, start > end, the given label is not null
    @Test
    public void testInsertStartBiggerThanEnd() {
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(3, -2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: start > 0, start < end, the given label is null
    @Test
    public void testInsertNullLabel() {
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(1, 2, null);
    	assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the interval of the given label not exist, the given label is not null
    @Test
    public void testRemoveIntervalNotExist() {
    	IntervalSet<String> intervals = emptyInstance();
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
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(0, 6, "A");
    	assertTrue(intervals.remove("A"));
        assertEquals(Collections.emptySet(), intervals.labels());
        assertEquals(-1, intervals.start("A"));
        assertEquals(-1, intervals.end("A"));
    }
    
    //Cover: the given label is null
    @Test
    public void testRemoveNullLabel() {
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(3, 6, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
    	assertFalse(intervals.remove(null));
        assertEquals(string, intervals.labels());
    }
    
    //Cover: the IntervalSet is empty
    @Test
    public void testLabelsIntervalSetEmpty() {
    	IntervalSet<String> intervals = emptyInstance();
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the IntervalSet is not empty
    @Test
    public void testLabelsIntervalSetNotEmpty() {
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
    }
    
    //Cover: the interval of the given label not exist, the given label is not null
    @Test
    public void testStartIntervalNotExist() {
    	IntervalSet<String> intervals = emptyInstance();
        assertEquals(-1, intervals.start("A"));
    }
    
    //Cover: the interval of the given label exists, the given label is not null
    @Test
    public void testStartIntervalExist() {
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(5, 8, "A");
        assertEquals(5, intervals.start("A"));
    }
    
    //Cover: the given label is null
    @Test
    public void testStartNullLabel() {
    	IntervalSet<String> intervals = emptyInstance();
        assertEquals(-1, intervals.start(null));
    }
    
    //Cover: the interval of the given label not exist, the given label is not null
    @Test
    public void testEndIntervalNotExist() {
    	IntervalSet<String> intervals = emptyInstance();
        assertEquals(-1, intervals.end("A"));
    }
    
    //Cover: the interval of the given label exists, the given label is not null
    @Test
    public void testEndIntervalExist() {
    	IntervalSet<String> intervals = emptyInstance();
    	intervals.insert(3, 6, "A");
        assertEquals(6, intervals.end("A"));
    }
    
    //Cover: the given label is null
    @Test
    public void testEndNullLabel() {
    	IntervalSet<String> intervals = emptyInstance();
        assertEquals(-1, intervals.end(null));
    }
    
}
