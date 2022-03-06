package MultiIntervalSetDecorator;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * Tests for MultiIntervalSetDecorator.
 * 
 */
public class MultiIntervalSetDecoratorTest {
      
	// Testing strategy:
    //     the methods are actually methods of MultiIntervalSet, so the testing strategies and Junit tests
	//     are the same as MultiIntervalSet
	//
	// MultiIntervalSetDecorator():
	//     the input MultiIntervalSet multiintervalset is empty, not empty
	// insert():
	//     the given interval already exists, not exist
    //     start < 0, = 0, > 0
	//     start < end, start = end, start > end
    //     the given label is null, not null
	// labels():
	//     the MultiIntervalSetDecorator is empty, not empty
	// remove():
	//     an interval with the given label exists, not exist
    //     the given label is null, not null
	// intervals():
	//     an interval with the given label exists, not exist
	//     the number of intervals =0, =1, >1
    //     the given label is null, not null
	// toString():
    //     the MultiIntervalSetDecorator is empty, not empty	
	
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //Cover: the MultiIntervalSetDecorator is empty
    @Test
    public void testMultiIntervalSetDecoratorEmpty(){
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
        assertEquals(Collections.emptySet(),intervals.labels());
    }
    
    //Cover: the MultiIntervalSetDecorator is not empty
    @Test
    public void testMultiIntervalSetDecoratorNotEmpty(){
    	IntervalSet<String> initial = IntervalSet.empty();
    	initial.insert(2, 6, "Java");
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>(initial));
    	Set<String> string = new HashSet<String>();
        string.add("Java");
        IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(2, 6, 0);
        assertEquals(string, intervals.labels());
        
        IntervalSet<Integer> interval = intervals.intervals("Java");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
    }
    
    //Cover: the interval not exist, start > 0, start < end, the given label is not null
    @Test
    public void testInsertLabelNotExist() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
    	intervals.insert(2, 3, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(1, 2, 0);
        inter.insert(2, 3, 1);
        assertEquals(string, intervals.labels());
        
        IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
    }
    
    //Cover: the interval already exists, start > 0, start < end, the given label is not null
    @Test
    public void testInsertLabelExist() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(1, 2, 0);
        inter.insert(1, 2, 1);
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
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
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
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(-1, 2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the interval not exist, start > 0, start = end, the given label is not null
    @Test
    public void testInsertStartEqualEnd() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
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
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(3, -2, "A");
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: start > 0, start < end, the given label is null
    @Test
    public void testInsertNullLabel() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, null);
    	assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the MultiIntervalSetDecorator is empty
    @Test
    public void testLabelsIntervalSetEmpty() {
    	MultiIntervalSet<String> intervals = new MultiIntervalSet<>();
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the MultiIntervalSetDecorator is not empty
    @Test
    public void testLabelsIntervalSetNotEmpty() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertEquals(string, intervals.labels());
    }
    
    //Cover: the interval of the given label not exist, the given label is not null
    @Test
    public void testRemoveIntervalNotExist() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
        assertFalse(intervals.remove("B"));
        assertEquals(string, intervals.labels());
    }
    
    //Cover: the interval of the given label already exists, the given label is not null
    @Test
    public void testRemoveIntervalExist() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(0, 6, "A");
    	intervals.insert(10, 16, "A");
    	assertTrue(intervals.remove("A"));
        assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    //Cover: the given label is null
    @Test
    public void testRemoveNullLabel() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(3, 6, "A");
    	Set<String> string = new HashSet<String>();
        string.add("A");
    	assertFalse(intervals.remove(null));
        assertEquals(string, intervals.labels());
    }
    
    //Cover: the interval of the given label already exists, the number of intervals > 1, the given label is not null
    @Test
    public void testIntervalsMorethanOne() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(3, 4, "A");
    	intervals.insert(2, 3, "A");
    	intervals.insert(1, 2, "A");
    	intervals.insert(5, 6, "B");
    	intervals.insert(0, 1, "B");
    	IntervalSet<Integer> intervalA = intervals.intervals("A");
        IntervalSet<Integer> interA = IntervalSet.empty();
        interA.insert(1, 2, 0);
        interA.insert(2, 3, 1);
        interA.insert(3, 4, 2);
        for (Integer i : interA.labels()) {
        	assertEquals(interA.start(i), intervalA.start(i));
        	assertEquals(interA.end(i), intervalA.end(i));
        }
        IntervalSet<Integer> intervalB = intervals.intervals("B");
        IntervalSet<Integer> interB = IntervalSet.empty();
        interB.insert(0, 1, 0);
        interB.insert(5, 6, 1);
        for (Integer i : interB.labels()) {
        	assertEquals(interB.start(i), intervalB.start(i));
        	assertEquals(interB.end(i), intervalB.end(i));
        }
    }
    
    //Cover: the interval of the given label already exists, the number of intervals = 1, the given label is not null
    @Test
    public void testIntervalsOnlyOne() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	intervals.insert(1, 2, "A");
        IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(1, 2, 0);
        
        IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
    }
    
    //Cover: the interval of the given label not exist, the number of intervals = 0, the given label is not null
    @Test
    public void testIntervalsNotExist() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	IntervalSet<Integer> interval = intervals.intervals("A");
    	assertEquals(Collections.emptySet(), interval.labels());
    }
    
    //Cover: the given label is null
    @Test
    public void testIntervalsNullLabel() {
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
    	IntervalSet<Integer> interval = intervals.intervals(null);
    	assertEquals(Collections.emptySet(), interval.labels());
    }
    
    //Cover: the MultiIntervalSetDecorator is empty
    @Test
    public void testtoStringEmpty(){
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
        assertEquals("Empty IntervalSet\n",intervals.toString());
    }
    
    //Cover: the MultiIntervalSetDecorator is not empty
    @Test
    public void testtoStringNotEmpty(){
    	MultiIntervalSetDecorator<String> intervals = new MultiIntervalSetDecorator<String>(new MultiIntervalSet<>());
        intervals.insert(2, 8, "lab3");
        intervals.insert(4, 9, "lab3");
        intervals.insert(6, 6, "difficult");
        assertEquals("Intervals :\nlab3:\n2 -> 8\n4 -> 9\ndifficult:\n6 -> 6\n",intervals.toString());
    }
    
}
