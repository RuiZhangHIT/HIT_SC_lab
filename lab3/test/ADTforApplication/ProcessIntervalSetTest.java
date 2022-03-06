package ADTforApplication;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import IntervalSet.IntervalSet;

/**
 * Tests for ProcessIntervalSet.
 * 
 */
public class ProcessIntervalSetTest {
    
	    // Testing strategy:
	    //     insert() is actually the method of NonOverlapMultiIntervalSet, so the testing strategies and Junit tests are almost the same as NonOverlapMultiIntervalSet
		//     other methods are actually methods of MultiIntervalSetDecorator, so the testing strategies and Junit tests are almost the same as MultiIntervalSetDecorator
	    //
		// ProcessIntervalSet():
        //     no inputs, only output is empty ProcessIntervalSet
		// insert():
		//     the given interval already exists, not exist
		//     start < 0, = 0, > 0
		//     start < end, start = end, start > end
		//     the given label is null, not null
		//     the interval to be inserted overlap with exsiting intervals, nonverlap with exsiting intervals
		// labels():
		//     the ProcessIntervalSet is empty, not empty
		// remove():
		//     an interval with the given label exists, not exist
	    //     the given label is null, not null
		// intervals():
		//     an interval with the given label exists, not exist
		//     the number of intervals =0, =1, >1
	    //     the given label is null, not null
		// toString():
	    //     the ProcessIntervalSet is empty, not empty	
		
		@Test(expected=AssertionError.class)
	    public void testAssertionsEnabled() {
	        assert false; // make sure assertions are enabled with VM argument: -ea
	    }
		
		//Cover: no inputs, only output is empty ProcessIntervalSet
		@Test
	    public void testProcessIntervalSet() {
			ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	        assertEquals(Collections.emptySet(), process.labels());
	    }
		
		//Cover: the interval not exist, start > 0, start < end, the given label is not null
	    //       interval to be inserted nonoverlap with exsiting intervals
	    @Test
	    public void testInsertLabelNotExist() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(1, 2, "A");
	    	process.insert(3, 4, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        inter.insert(3, 4, 1);
	        assertEquals(string, process.labels());
	        
	        IntervalSet<Integer> interval = process.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval already exists, start > 0, start < end, the given label is not null,
	    //       interval to be inserted overlap with exsiting intervals
	    @Test
	    public void testInsertLabelExist() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(1, 2, "A");
	    	process.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        assertEquals(string, process.labels());
	        
	        IntervalSet<Integer> interval = process.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval not exist, start = 0, start < end, the given label is not null
	    @Test
	    public void testInsertZeroStart() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(0, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(0, 2, 0);
	        assertEquals(string, process.labels());
	        
	        IntervalSet<Integer> interval = process.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval not exist, start < 0, start < end, the given label is not null
	    @Test
	    public void testInsertNegativeStart() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(-1, 2, "A");
	        assertEquals(Collections.emptySet(), process.labels());
	    }
	    
	    //Cover: the interval not exist, start > 0, start = end, the given label is not null
	    @Test
	    public void testInsertStartEqualEnd() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(2, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(2, 2, 0);
	        assertEquals(string, process.labels());
	        
	        IntervalSet<Integer> interval = process.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval not exist, start > 0, start > end, the given label is not null
	    @Test
	    public void testInsertStartBiggerThanEnd() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(3, -2, "A");
	        assertEquals(Collections.emptySet(), process.labels());
	    }
	    
	    //Cover: start > 0, start < end, the given label is null
	    @Test
	    public void testInsertNullLabel() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(1, 2, null);
	    	assertEquals(Collections.emptySet(), process.labels());
	    }
	    
	    //Cover: interval to be inserted overlap with exsiting intervals
	    @Test
	    public void testInsertOverlap() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(1, 2, "A");
	    	process.insert(2, 3, "A");
	    	process.insert(3, 4, "B");
	    	process.insert(4, 5, "C");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        string.add("B");
	        IntervalSet<Integer> interA = IntervalSet.empty();
	        interA.insert(1, 2, 0);
	        IntervalSet<Integer> interB = IntervalSet.empty();
	        interB.insert(3, 4, 0);
	        assertEquals(string, process.labels());
	        
	        IntervalSet<Integer> interval1 = process.intervals("A");
	        for (Integer i : interA.labels()) {
	        	assertEquals(interA.start(i), interval1.start(i));
	        	assertEquals(interA.end(i), interval1.end(i));
	        }
	        IntervalSet<Integer> interval2 = process.intervals("B");
	        for (Integer i : interB.labels()) {
	        	assertEquals(interB.start(i), interval2.start(i));
	        	assertEquals(interB.end(i), interval2.end(i));
	        }
	    }
	    
	    //Cover: interval to be inserted nonoverlap with exsiting intervals
	    @Test
	    public void testInsertNonoverlap() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(1, 2, "A");
	    	process.insert(3, 3, "B");
	    	process.insert(4, 6, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        string.add("B");
	        IntervalSet<Integer> interA = IntervalSet.empty();
	        interA.insert(1, 2, 0);
	        interA.insert(4, 6, 1);
	        IntervalSet<Integer> interB = IntervalSet.empty();
	        interB.insert(3, 3, 0);
	        assertEquals(string, process.labels());
	        
	        IntervalSet<Integer> intervalA = process.intervals("A");
	        for (Integer i : interA.labels()) {
	        	assertEquals(interA.start(i), intervalA.start(i));
	        	assertEquals(interA.end(i), intervalA.end(i));
	        }
	        IntervalSet<Integer> interval2 = process.intervals("B");
	        for (Integer i : interB.labels()) {
	        	assertEquals(interB.start(i), interval2.start(i));
	        	assertEquals(interB.end(i), interval2.end(i));
	        }
	    }
	    
	    //Cover: the ProcessIntervalSet is empty
	    @Test
	    public void testLabelsIntervalSetEmpty() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	        assertEquals(Collections.emptySet(), process.labels());
	    }
	    
	    //Cover: the ProcessIntervalSet is not empty
	    @Test
	    public void testLabelsIntervalSetNotEmpty() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        assertEquals(string, process.labels());
	    }
	    
	    //Cover: the interval of the given label not exist, the given label is not null
	    @Test
	    public void testRemoveIntervalNotExist() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        assertFalse(process.remove("B"));
	        assertEquals(string, process.labels());
	    }
	    
	    //Cover: the interval of the given label already exists, the given label is not null
	    @Test
	    public void testRemoveIntervalExist() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(0, 6, "A");
	    	process.insert(10, 16, "A");
	    	assertTrue(process.remove("A"));
	        assertEquals(Collections.emptySet(), process.labels());
	    }
	    
	    //Cover: the given label is null
	    @Test
	    public void testRemoveNullLabel() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(3, 6, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	    	assertFalse(process.remove(null));
	        assertEquals(string, process.labels());
	    }
	    
	    //Cover: the interval of the given label already exists, the number of intervals > 1, the given label is not null
	    @Test
	    public void testIntervalsMorethanOne() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(5, 6, "A");
	    	process.insert(3, 4, "A");
	    	process.insert(1, 2, "A");
	    	process.insert(7, 8, "B");
	    	process.insert(0, 0, "B");
	    	IntervalSet<Integer> intervalA = process.intervals("A");
	        IntervalSet<Integer> interA = IntervalSet.empty();
	        interA.insert(1, 2, 0);
	        interA.insert(3, 4, 1);
	        interA.insert(5, 6, 2);
	        for (Integer i : interA.labels()) {
	        	assertEquals(interA.start(i), intervalA.start(i));
	        	assertEquals(interA.end(i), intervalA.end(i));
	        }
	        IntervalSet<Integer> intervalB = process.intervals("B");
	        IntervalSet<Integer> interB = IntervalSet.empty();
	        interB.insert(0, 0, 0);
	        interB.insert(7, 8, 1);
	        for (Integer i : interB.labels()) {
	        	assertEquals(interB.start(i), intervalB.start(i));
	        	assertEquals(interB.end(i), intervalB.end(i));
	        }
	    }
	    
	    //Cover: the interval of the given label already exists, the number of intervals = 1, the given label is not null
	    @Test
	    public void testIntervalsOnlyOne() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	process.insert(1, 2, "A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        
	        IntervalSet<Integer> interval = process.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval of the given label not exist, the number of intervals = 0, the given label is not null
	    @Test
	    public void testIntervalsNotExist() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	IntervalSet<Integer> interval = process.intervals("A");
	    	assertEquals(Collections.emptySet(), interval.labels());
	    }
	    
	    //Cover: the given label is null
	    @Test
	    public void testIntervalsNullLabel() {
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	    	IntervalSet<Integer> interval = process.intervals(null);
	    	assertEquals(Collections.emptySet(), interval.labels());
	    }
	    
	    //Cover: the ProcessIntervalSet is empty
	    @Test
	    public void testtoStringEmpty(){
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	        assertEquals("Empty IntervalSet\n",process.toString());
	    }
	    
	    //Cover: the ProcessIntervalSet is not empty
	    @Test
	    public void testtoStringNotEmpty(){
	    	ProcessIntervalSet<String> process = new ProcessIntervalSet<>();
	        process.insert(3, 5, "lab3");
	        process.insert(6, 6, "difficult");
	        assertEquals("Intervals :\nlab3:\n3 -> 5\ndifficult:\n6 -> 6\n",process.toString());
	    }
    
}
