package IntervalSetDecorator;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import IntervalSet.CommonIntervalSet;
import IntervalSet.IntervalSet;

/**
 * Tests for NonOverlapIntervalSet.
 * 
 */
public class NonOverlapIntervalSetTest {
    
	// Testing strategy:
	//
	// NonOverlapIntervalSet():
	//     the input IntervalSet intervalset is empty, not empty
	// insert():
    //     an interval of the given label already exists, not exist
	//     start < 0, = 0, > 0
	//     start < end, start = end, start > end
	//     the given label is null, not null
	//     the interval to be inserted overlap with exsiting intervals, nonverlap with exsiting intervals
	//
    //     other methods are actually methods of IntervalSetDecorator, so the testing strategies and
	//     Junit tests are the same as IntervalSetDecorator, here will no longer test    
    
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
	
	//Cover: the input IntervalSet intervalset is empty
	@Test
	public void testNonOverlapIntervalSetEmpty() {
		NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
	    assertEquals(Collections.emptySet(), intervals.labels());
	}
		
	//Cover: the input IntervalSet intervalset is not empty
	@Test
	public void testNonOverlapIntervalSetNotEmpty() {
		IntervalSet<String> interval = new CommonIntervalSet<String>();
		interval.insert(1, 2, "A");
		Set<String> string = new HashSet<String>();
		string.add("A");
		NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(interval);   
		assertEquals(string, intervals.labels());
		assertEquals(1, intervals.start("A"));
		assertEquals(2, intervals.end("A"));
	}
	
	//Cover: an interval of the given label not exist, start > 0, start < end, the given label is not null
    @Test
    public void testInsertLabelNotExist() {
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
        assertEquals(1, intervals.start("A"));
        assertEquals(2, intervals.end("A"));
    }
    
    //Cover: an interval of the given label already exists, start > 0, start < end, the given label is not null,
    //       interval to be inserted overlap with exsiting intervals
    @Test
    public void testInsertLabelExist() {
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
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
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
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
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(-1, 2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: an interval of the given label not exist, start > 0, start = end, the given label is not null
    @Test
    public void testInsertStartEqualEnd() {
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(2, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
    }
    
    //Cover: an interval of the given label not exist, start > 0, start > end, the given label is not null
    @Test
    public void testInsertStartBiggerThanEnd() {
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(3, -2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: start > 0, start < end, the given label is null
    @Test
    public void testInsertNullLabel() {
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(1, 2, null);
    	assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: interval to be inserted overlap with exsiting intervals
    @Test
    public void testInsertOverlap() {
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(1, 2, "A");
    	intervals.insert(2, 2, "B");
    	intervals.insert(3, 4, "C");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("C");
        assertEquals(string, intervals.labels());
    }
    
    //Cover: interval to be inserted nonoverlap with exsiting intervals
    @Test
    public void testInsertNonoverlap() {
    	NonOverlapIntervalSet<String> intervals = new NonOverlapIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(1, 2, "A");
    	intervals.insert(3, 3, "B");
    	intervals.insert(4, 6, "C");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        string.add("C");
        assertEquals(string, intervals.labels());
    }
    
}
