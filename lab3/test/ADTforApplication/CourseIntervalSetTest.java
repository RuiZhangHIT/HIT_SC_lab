package ADTforApplication;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import IntervalSet.IntervalSet;

/**
 * Tests for CourseIntervalSet.
 * 
 */
public class CourseIntervalSetTest {
    
	    // Testing strategy:
	    //     insert() is actually the method of OverlapMultiIntervalSet, so the testing strategies and Junit tests are almost the same as OverlapMultiIntervalSet
	    //     getperiod() is actually the method of PeriodicMultiIntervalSet, and the period is already set as 7(1 week)
		//     other methods are actually methods of MultiIntervalSetDecorator, so the testing strategies and Junit tests are almost the same as MultiIntervalSetDecorator
	    //
		// CourseIntervalSet():
        //     no inputs, only output is empty CourseIntervalSet
		// insert():
		//     the given interval already exists, not exist
		//     start < 0, = 0, > 0
		//     start < end, start = end, start > end
		//     the given label is null, not null
		//     the interval to be inserted overlap with exsiting intervals, nonverlap with exsiting intervals
		//     the overlap intervals are with the same label, different labels
		// labels():
		//     the CourseIntervalSet is empty, not empty
		// remove():
		//     an interval with the given label exists, not exist
	    //     the given label is null, not null
		// intervals():
		//     an interval with the given label exists, not exist
		//     the number of intervals =0, =1, >1
	    //     the given label is null, not null
		// getperiod():
		//     the input period is already set
		// toString():
	    //     the CourseIntervalSet is empty, not empty	
		
		@Test(expected=AssertionError.class)
	    public void testAssertionsEnabled() {
	        assert false; // make sure assertions are enabled with VM argument: -ea
	    }
		
		//Cover: no inputs, only output is empty CourseIntervalSet
		@Test
	    public void testCourseIntervalSet() {
			CourseIntervalSet<String> course = new CourseIntervalSet<>();
	        assertEquals(Collections.emptySet(), course.labels());
	    }
		
		//Cover: the interval not exist, start > 0, start < end, the given label is not null
	    //       interval to be inserted nonoverlap with exsiting intervals
	    @Test
	    public void testInsertLabelNotExist() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(1, 2, "A");
	    	course.insert(3, 4, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        inter.insert(3, 4, 1);
	        assertEquals(string, course.labels());
	        
	        IntervalSet<Integer> interval = course.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval already exists, start > 0, start < end, the given label is not null,
	    //       interval to be inserted overlap with exsiting intervals
	    //       the overlap intervals are with the same label
	    @Test
	    public void testInsertLabelExist() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(1, 2, "A");
	    	course.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        assertEquals(string, course.labels());
	        
	        IntervalSet<Integer> interval = course.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval not exist, start = 0, start < end, the given label is not null
	    @Test
	    public void testInsertZeroStart() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(0, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(0, 2, 0);
	        assertEquals(string, course.labels());
	        
	        IntervalSet<Integer> interval = course.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval not exist, start < 0, start < end, the given label is not null
	    @Test
	    public void testInsertNegativeStart() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(-1, 2, "A");
	        assertEquals(Collections.emptySet(), course.labels());
	    }
	    
	    //Cover: the interval not exist, start > 0, start = end, the given label is not null
	    @Test
	    public void testInsertStartEqualEnd() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(2, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(2, 2, 0);
	        assertEquals(string, course.labels());
	        
	        IntervalSet<Integer> interval = course.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval not exist, start > 0, start > end, the given label is not null
	    @Test
	    public void testInsertStartBiggerThanEnd() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(3, -2, "A");
	        assertEquals(Collections.emptySet(), course.labels());
	    }
	    
	    //Cover: start > 0, start < end, the given label is null
	    @Test
	    public void testInsertNullLabel() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(1, 2, null);
	    	assertEquals(Collections.emptySet(), course.labels());
	    }
	    
	    //Cover: interval to be inserted overlap with exsiting intervals, the overlap intervals are with different labels
	    @Test
	    public void testInsertOverlap() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(1, 2, "A");
	    	course.insert(2, 3, "B");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        assertEquals(string, course.labels());
	        
	        IntervalSet<Integer> interval = course.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: interval to be inserted nonoverlap with exsiting intervals
	    @Test
	    public void testInsertNonoverlap() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(1, 2, "A");
	    	course.insert(3, 3, "B");
	    	course.insert(4, 6, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        string.add("B");
	        IntervalSet<Integer> interA = IntervalSet.empty();
	        interA.insert(1, 2, 0);
	        interA.insert(4, 6, 1);
	        IntervalSet<Integer> interB = IntervalSet.empty();
	        interB.insert(3, 3, 0);
	        assertEquals(string, course.labels());
	        
	        IntervalSet<Integer> intervalA = course.intervals("A");
	        for (Integer i : interA.labels()) {
	        	assertEquals(interA.start(i), intervalA.start(i));
	        	assertEquals(interA.end(i), intervalA.end(i));
	        }
	        IntervalSet<Integer> interval2 = course.intervals("B");
	        for (Integer i : interB.labels()) {
	        	assertEquals(interB.start(i), interval2.start(i));
	        	assertEquals(interB.end(i), interval2.end(i));
	        }
	    }
	    
	    //Cover: the CourseIntervalSet is empty
	    @Test
	    public void testLabelsIntervalSetEmpty() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	        assertEquals(Collections.emptySet(), course.labels());
	    }
	    
	    //Cover: the CourseIntervalSet is not empty
	    @Test
	    public void testLabelsIntervalSetNotEmpty() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        assertEquals(string, course.labels());
	    }
	    
	    //Cover: the interval of the given label not exist, the given label is not null
	    @Test
	    public void testRemoveIntervalNotExist() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(1, 2, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	        assertFalse(course.remove("B"));
	        assertEquals(string, course.labels());
	    }
	    
	    //Cover: the interval of the given label already exists, the given label is not null
	    @Test
	    public void testRemoveIntervalExist() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(0, 6, "A");
	    	course.insert(10, 16, "A");
	    	assertTrue(course.remove("A"));
	        assertEquals(Collections.emptySet(), course.labels());
	    }
	    
	    //Cover: the given label is null
	    @Test
	    public void testRemoveNullLabel() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(3, 6, "A");
	    	Set<String> string = new HashSet<String>();
	        string.add("A");
	    	assertFalse(course.remove(null));
	        assertEquals(string, course.labels());
	    }
	    
	    //Cover: the interval of the given label already exists, the number of intervals > 1, the given label is not null
	    @Test
	    public void testIntervalsMorethanOne() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(4, 5, "A");
	    	course.insert(2, 3, "A");
	    	course.insert(1, 1, "A");
	    	IntervalSet<Integer> intervalA = course.intervals("A");
	        IntervalSet<Integer> interA = IntervalSet.empty();
	        interA.insert(1, 1, 0);
	        interA.insert(2, 3, 1);
	        interA.insert(4, 5, 2);
	        for (Integer i : interA.labels()) {
	        	assertEquals(interA.start(i), intervalA.start(i));
	        	assertEquals(interA.end(i), intervalA.end(i));
	        }
	    }
	    
	    //Cover: the interval of the given label already exists, the number of intervals = 1, the given label is not null
	    @Test
	    public void testIntervalsOnlyOne() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	course.insert(1, 2, "A");
	        IntervalSet<Integer> inter = IntervalSet.empty();
	        inter.insert(1, 2, 0);
	        
	        IntervalSet<Integer> interval = course.intervals("A");
	        for (Integer i : inter.labels()) {
	        	assertEquals(inter.start(i), interval.start(i));
	        	assertEquals(inter.end(i), interval.end(i));
	        }
	    }
	    
	    //Cover: the interval of the given label not exist, the number of intervals = 0, the given label is not null
	    @Test
	    public void testIntervalsNotExist() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	IntervalSet<Integer> interval = course.intervals("A");
	    	assertEquals(Collections.emptySet(), interval.labels());
	    }
	    
	    //Cover: the given label is null
	    @Test
	    public void testIntervalsNullLabel() {
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	    	IntervalSet<Integer> interval = course.intervals(null);
	    	assertEquals(Collections.emptySet(), interval.labels());
	    }
	    
	    //Cover:the input period is already set
		@Test
		public void testGetPeriod() {
			CourseIntervalSet<String> course = new CourseIntervalSet<>();
			assertEquals(7, course.getperiod());
		}
		
	    //Cover: the CourseIntervalSet is empty
	    @Test
	    public void testtoStringEmpty(){
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	        assertEquals("Empty IntervalSet\n",course.toString());
	    }
	    
	    //Cover: the CourseIntervalSet is not empty
	    @Test
	    public void testtoStringNotEmpty(){
	    	CourseIntervalSet<String> course = new CourseIntervalSet<>();
	        course.insert(4, 9, "lab3");
	        assertEquals("Intervals :\nlab3:\n4 -> 9\n",course.toString());
	    }
    
}
