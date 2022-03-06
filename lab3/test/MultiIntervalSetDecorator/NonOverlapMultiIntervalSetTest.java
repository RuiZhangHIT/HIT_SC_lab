package MultiIntervalSetDecorator;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * Tests for NonOverlapMultiIntervalSet.
 * 
 */
public class NonOverlapMultiIntervalSetTest {
    
	// Testing strategy:
	//
	// NonOverlapMultiIntervalSet():
	//     the input MultiIntervalSet multiintervalset is empty, not empty
	// insert():
	//     the given interval already exists, not exist
	//     start < 0, = 0, > 0
	//     start < end, start = end, start > end
	//     the given label is null, not null
	//     the interval to be inserted overlap with exsiting intervals, nonverlap with exsiting intervals
	//
    //     other methods are actually methods of MultiIntervalSetDecorator, so the testing strategies and
	//     Junit tests are the same as MultiIntervalSetDecorator, here will no longer test    
    
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
	
	//Cover: the input MultiIntervalSet multiintervalset is empty
	@Test
	public void testNonOverlapMultiIntervalSetEmpty() {
		NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
	    assertEquals(Collections.emptySet(), intervals.labels());
	}
		
	//Cover: the input MultiIntervalSet multiintervalset is not empty
	@Test
	public void testNonOverlapMultiIntervalSetNotEmpty() {
		MultiIntervalSet<String> multiinterval = new MultiIntervalSet<String>();
		multiinterval.insert(1, 2, "A");
		Set<String> string = new HashSet<String>();
		string.add("A");
		NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(multiinterval);  
		IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(1, 2, 0);
        assertEquals(string, intervals.labels());
		
		IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
	}
	
	//Cover: the interval not exist, start > 0, start < end, the given label is not null
    //       interval to be inserted nonoverlap with exsiting intervals
    @Test
    public void testInsertLabelNotExist() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
    	intervals.insert(3, 4, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(1, 2, 0);
        inter.insert(3, 4, 1);
        assertEquals(string, intervals.labels());
        
        IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
    }
    
    //Cover: the interval already exists, start > 0, start < end, the given label is not null,
    //       interval to be inserted overlap with exsiting intervals
    @Test
    public void testInsertLabelExist() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(1, 2, 0);
        assertEquals(string, intervals.labels());
        
        IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
    }
    
    //Cover: the interval not exist, start = 0, start < end, the given label is not null
    @Test
    public void testInsertZeroStart() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(0, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(0, 2, 0);
        assertEquals(string, intervals.labels());
        
        IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
    }
    
    //Cover: the interval not exist, start < 0, start < end, the given label is not null
    @Test
    public void testInsertNegativeStart() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(-1, 2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the interval not exist, start > 0, start = end, the given label is not null
    @Test
    public void testInsertStartEqualEnd() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(2, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(2, 2, 0);
        assertEquals(string, intervals.labels());
        
        IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
    }
    
    //Cover: the interval not exist, start > 0, start > end, the given label is not null
    @Test
    public void testInsertStartBiggerThanEnd() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(3, -2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: start > 0, start < end, the given label is null
    @Test
    public void testInsertNullLabel() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, null);
    	assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: interval to be inserted overlap with exsiting intervals
    @Test
    public void testInsertOverlap() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
    	intervals.insert(2, 3, "A");
    	intervals.insert(3, 4, "B");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        IntervalSet<Integer> interA = IntervalSet.empty();
        interA.insert(1, 2, 0);
        IntervalSet<Integer> interB = IntervalSet.empty();
        interB.insert(3, 4, 0);
        assertEquals(string, intervals.labels());
        
        IntervalSet<Integer> interval1 = intervals.intervals("A");
        for (Integer i : interA.labels()) {
        	assertEquals(interA.start(i), interval1.start(i));
        	assertEquals(interA.end(i), interval1.end(i));
        }
        IntervalSet<Integer> interval2 = intervals.intervals("B");
        for (Integer i : interB.labels()) {
        	assertEquals(interB.start(i), interval2.start(i));
        	assertEquals(interB.end(i), interval2.end(i));
        }
    }
    
    //Cover: interval to be inserted nonoverlap with exsiting intervals
    @Test
    public void testInsertNonoverlap() {
    	NonOverlapMultiIntervalSet<String> intervals = new NonOverlapMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
    	intervals.insert(3, 3, "B");
    	intervals.insert(4, 6, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        string.add("B");
        IntervalSet<Integer> interA = IntervalSet.empty();
        interA.insert(1, 2, 0);
        interA.insert(4, 6, 1);
        IntervalSet<Integer> interB = IntervalSet.empty();
        interB.insert(3, 3, 0);
        assertEquals(string, intervals.labels());
        
        IntervalSet<Integer> intervalA = intervals.intervals("A");
        for (Integer i : interA.labels()) {
        	assertEquals(interA.start(i), intervalA.start(i));
        	assertEquals(interA.end(i), intervalA.end(i));
        }
        IntervalSet<Integer> interval2 = intervals.intervals("B");
        for (Integer i : interB.labels()) {
        	assertEquals(interB.start(i), interval2.start(i));
        	assertEquals(interB.end(i), interval2.end(i));
        }
    }
    
}
