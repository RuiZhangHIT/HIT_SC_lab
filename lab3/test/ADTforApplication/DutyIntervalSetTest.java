package ADTforApplication;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import IntervalSet.IntervalSet;

/**
 * Tests for DutyIntervalSet.
 * 
 */
public class DutyIntervalSetTest {
    
	    // Testing strategy:
	    //     insert() is actually the method of NonOverlapIntervalSet, so the testing strategies and Junit tests are almost the same as NonOverlapIntervalSet
	    //     checkNoBlank() is actually the method of NoBlankIntervalSet, so the testing strategies and Junit tests are almost the same as NoBlankIntervalSet
		//     other methods are actually methods of IntervalSetDecorator, so the testing strategies and Junit tests are almost the same as IntervalSetDecorator
	    //
		// DutyIntervalSet():
        //     no inputs, only output is empty DutyIntervalSet
		// insert():
		//     an interval of the given label already exists, not exist
		//     start < 0, = 0, > 0
		//     start < end, start = end, start > end
		//     the given label is null, not null
		//     the interval to be inserted overlap with exsiting intervals, nonverlap with exsiting intervals
		// labels():
		//     the CourseIntervalSet is empty, not empty
		// remove():
		//     an interval with the given label exists, not exist
		//     the given label is null, not null
		// intervals():
		//     an interval with the given label exists, not exist
		//     the number of intervals =0, =1, >1
		//     the given label is null, not null		
	    // checkNoBlank():
		//     the given interval [start-end] is with no blank, with blank
		//     the start time < 0, = 0, > 0
		//     the start time < end time, = end time, > end time
		// toString()
	    //     the DutyIntervalSet is empty, not empty
		
		@Test(expected=AssertionError.class)
	    public void testAssertionsEnabled() {
	        assert false; // make sure assertions are enabled with VM argument: -ea
	    }
		
		//Cover: no inputs, only output is empty DutyIntervalSet
		@Test
	    public void testDutyIntervalSet() {
			DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	        assertEquals(Collections.emptySet(), duty.labels());
	    }
		
		//Cover: an interval of the given label not exist, start > 0, start < end, the given label is not null
	    @Test
	    public void testInsertLabelNotExist() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        assertEquals(string, duty.labels());
	        
	        IntervalSet<Integer> interval = duty.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: an interval of the given label already exists, start > 0, start < end, the given label is not null,
	    //       interval to be inserted overlap with exsiting intervals
	    @Test
	    public void testInsertLabelExist() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        assertEquals(string, duty.labels());
	        
	        IntervalSet<Integer> interval = duty.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	        
	        duty.insert(1, 2, "A");
	        assertEquals(string, duty.labels());
	        
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: an interval of the given label not exist, start = 0, start < end, the given label is not null
	    @Test
	    public void testInsertZeroStart() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(0, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(0, 2, 0);
	        assertEquals(string, duty.labels());
	        
	        IntervalSet<Integer> interval = duty.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: an interval of the given label not exist, start < 0, start < end, the given label is not null
	    @Test
	    public void testInsertNegativeStart() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(-1, 2, "A");
	        assertEquals(Collections.emptySet(), duty.labels());
	    }
	    
	    //Cover: an interval of the given label not exist, start > 0, start = end, the given label is not null
	    @Test
	    public void testInsertStartEqualEnd() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(2, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(2, 2, 0);
	        assertEquals(string, duty.labels());
	        
	        IntervalSet<Integer> interval = duty.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: an interval of the given label not exist, start > 0, start > end, the given label is not null
	    @Test
	    public void testInsertStartBiggerThanEnd() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(3, -2, "A");
	        assertEquals(Collections.emptySet(), duty.labels());
	    }
	    
	    //Cover: start > 0, start < end, the given label is null
	    @Test
	    public void testInsertNullLabel() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(1, 2, null);
	    	assertEquals(Collections.emptySet(), duty.labels());
	    }
	    
	    //Cover: interval to be inserted overlap with exsiting intervals
	    @Test
	    public void testInsertOverlap() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(1, 2, "A");
	    	duty.insert(2, 2, "B");
	    	duty.insert(3, 4, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        inter.insert(3, 4, 1);
	        assertEquals(string, duty.labels());
	        
	        IntervalSet<Integer> interval = duty.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: interval to be inserted nonoverlap with exsiting intervals
	    @Test
	    public void testInsertNonoverlap() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(1, 2, "A");
	    	duty.insert(3, 3, "B");
	    	duty.insert(4, 6, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        string.add("B");
	        string.add("A");
	        IntervalSet<Integer> interA = IntervalSet.empty();
	        interA.insert(1, 2, 0);
	        interA.insert(4, 6, 1);
	        IntervalSet<Integer> interB = IntervalSet.empty();
	        interA.insert(3, 3, 0);
	        assertEquals(string, duty.labels());
	        
	        IntervalSet<Integer> intervalA = duty.intervals("A");
	        for (Integer i : interA.labels()) {
	        	assertEquals(interA.start(i), intervalA.start(i));
	        	assertEquals(interA.end(i), intervalA.end(i));
	        }
	        IntervalSet<Integer> intervalB = duty.intervals("B");
	        for (Integer i : interB.labels()) {
	        	assertEquals(interB.start(i), intervalB.start(i));
	        	assertEquals(interB.end(i), intervalB.end(i));
	        }
	    }
	    
	   //Cover: the DutyIntervalSet is empty
	    @Test
	    public void testLabelsIntervalSetEmpty() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	        assertEquals(Collections.emptySet(), duty.labels());
	    }
	    
	    //Cover: the DutyIntervalSet is not empty
	    @Test
	    public void testLabelsIntervalSetNotEmpty() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        assertEquals(string, duty.labels());
	    }
	    
	    //Cover: the interval of the given label not exist, the given label is not null
	    @Test
	    public void testRemoveIntervalNotExist() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        assertFalse(duty.remove("B"));
	        assertEquals(string, duty.labels());
	    }
	    
	    //Cover: the interval of the given label already exists, the given label is not null
	    @Test
	    public void testRemoveIntervalExist() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(0, 6, "A");
	    	duty.insert(10, 16, "A");
	    	assertTrue(duty.remove("A"));
	        assertEquals(Collections.emptySet(), duty.labels());
	    }
	    
	    //Cover: the given label is null
	    @Test
	    public void testRemoveNullLabel() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(3, 6, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	    	assertFalse(duty.remove(null));
	        assertEquals(string, duty.labels());
	    }
	    
	    //Cover: the interval of the given label already exists, the number of intervals > 1, the given label is not null
	    @Test
	    public void testIntervalsMorethanOne() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(4, 5, "A");
	    	duty.insert(2, 3, "A");
	    	duty.insert(1, 1, "A");
	    	duty.insert(6, 6, "B");
	    	duty.insert(0, 0, "B");
	    	IntervalSet<Integer> intervalA = duty.intervals("A");
	        IntervalSet<Integer> interA = IntervalSet.empty();
	        interA.insert(1, 1, 0);
	        interA.insert(2, 3, 1);
	        interA.insert(4, 5, 2);
	        for (Integer i : interA.labels()) {
	        	assertEquals(interA.start(i), intervalA.start(i));
	        	assertEquals(interA.end(i), intervalA.end(i));
	        }
	        IntervalSet<Integer> intervalB = duty.intervals("B");
	        IntervalSet<Integer> interB = IntervalSet.empty();
	        interB.insert(0, 0, 0);
	        interB.insert(6, 6, 1);
	        for (Integer i : interB.labels()) {
	        	assertEquals(interB.start(i), intervalB.start(i));
	        	assertEquals(interB.end(i), intervalB.end(i));
	        }
	    }
	    
	    //Cover: the interval of the given label already exists, the number of intervals = 1, the given label is not null
	    @Test
	    public void testIntervalsOnlyOne() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(1, 2, "A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        
	        IntervalSet<Integer> interval = duty.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval of the given label not exist, the number of intervals = 0, the given label is not null
	    @Test
	    public void testIntervalsNotExist() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	IntervalSet<Integer> interval = duty.intervals("A");
	    	assertEquals(Collections.emptySet(), interval.labels());
	    }
	    
	    //Cover: the given label is null
	    @Test
	    public void testIntervalsNullLabel() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	IntervalSet<Integer> interval = duty.intervals(null);
	    	assertEquals(Collections.emptySet(), interval.labels());
	    }
	    
	    //Cover: the given interval [start-end] is with no blank, the start time > 0, the start time < end time
	    @Test
	    public void testCheckNoBlankWithNoBlank() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(0, 1, "A");
	    	duty.insert(1, 3, "B");
	    	duty.insert(2, 4, "A");
	        assertTrue(duty.checknoblank(1, 4));
	    }
	    
	    //Cover: the start time < 0, the start time < end time
	    @Test
	    public void testCheckNoBlankNegativeStart() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(0, 1, "A");
	    	duty.insert(1, 3, "B");
	    	duty.insert(2, 4, "A");
	        assertFalse(duty.checknoblank(-2, 4));
	    }
	    
	    //Cover: the start time > 0, the start time > end time
	    @Test
	    public void testCheckNoBlankStartBiggerthanEnd() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(0, 1, "A");
	    	duty.insert(1, 3, "B");
	    	duty.insert(2, 4, "A");
	        assertFalse(duty.checknoblank(4, 2));
	    }
	    
	    //Cover: the given interval [start-end] is with blanks, the start time = 0, the start time < end time
	    @Test
	    public void testCheckNoBlankWithBlanks() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(0, 1, "A");
	    	duty.insert(4, 4, "B");
	    	duty.insert(6, 7, "A");
	        assertFalse(duty.checknoblank(0, 10));
	    }
	    
	    //Cover: the given interval [start-end] is with blanks, the start time > 0, the start time = end time
	    @Test
	    public void testCheckNoBlankStartEqualsEnd() {
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	    	duty.insert(0, 1, "A");
	    	duty.insert(4, 4, "B");
	    	duty.insert(6, 7, "A");
	        assertFalse(duty.checknoblank(2, 2));
	    }
	    
	    //Cover: the DutyIntervalSet is empty
	    @Test
	    public void testtoStringEmpty(){
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	        assertEquals("Empty IntervalSet\n",duty.toString());
	    }
	    
	    //Cover: the DutyIntervalSet is not empty
	    @Test
	    public void testtoStringNotEmpty(){
	    	DutyIntervalSet<String> duty = new DutyIntervalSet<>();
	        duty.insert(2, 8, "lab3");
	        assertEquals("Intervals :\nlab3:\n2 -> 8\n",duty.toString());
	    }
	    
}
