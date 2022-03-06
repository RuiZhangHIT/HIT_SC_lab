package MultiIntervalSetDecorator;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * Tests for NoBlankMultiIntervalSet.
 * 
 */
public class NoBlankMultiIntervalSetTest {
    
	// Testing strategy:
	//
	// NoBlankMultiIntervalSet():
	//     the input MultiIntervalSet multiintervalset is empty, not empty
	// checkNoBlank():
	//     the given interval [start-end] is with no blank, with blank
	//     the start time < 0, = 0, > 0
	//     the start time < end time, = end time, > end time
	//
    //     other methods are actually methods of MultiIntervalSetDecorator, so the testing strategies and
	//     Junit tests are the same as MultiIntervalSetDecorator, here will no longer test    
    
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
	
	//Cover: the input MultiIntervalSet multiintervalset is empty
	@Test
	public void testNoBlankMultiIntervalSetEmpty() {
		NoBlankMultiIntervalSet<String> intervals = new NoBlankMultiIntervalSet<String>(new MultiIntervalSet<>());
	    assertEquals(Collections.emptySet(), intervals.labels());
	}
		
	//Cover: the input MultiIntervalSet multiintervalset is not empty
	@Test
	public void testNoBlankMultiIntervalSetNotEmpty() {
		MultiIntervalSet<String> multiinterval = new MultiIntervalSet<String>();
		multiinterval.insert(1, 2, "A");
		Set<String> string = new HashSet<String>();
		string.add("A");
		NoBlankMultiIntervalSet<String> intervals = new NoBlankMultiIntervalSet<String>(multiinterval); 
		IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(1, 2, 0);
        assertEquals(string, intervals.labels());
		
		IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
	}
	
	//Cover: the given interval [start-end] is with no blank, the start time > 0, the start time < end time
    @Test
    public void testCheckNoBlankWithNoBlank() {
    	NoBlankMultiIntervalSet<String> intervals = new NoBlankMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(0, 1, "A");
    	intervals.insert(1, 3, "B");
    	intervals.insert(2, 4, "A");
        assertTrue(intervals.checkNoBlank(1, 4));
    }
    
    //Cover: the start time < 0, the start time < end time
    @Test
    public void testCheckNoBlankNegativeStart() {
    	NoBlankMultiIntervalSet<String> intervals = new NoBlankMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(0, 1, "A");
    	intervals.insert(1, 3, "B");
    	intervals.insert(2, 4, "A");
        assertFalse(intervals.checkNoBlank(-2, 4));
    }
    
    //Cover: the start time > 0, the start time > end time
    @Test
    public void testCheckNoBlankStartBiggerthanEnd() {
    	NoBlankMultiIntervalSet<String> intervals = new NoBlankMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(0, 1, "A");
    	intervals.insert(1, 3, "B");
    	intervals.insert(2, 4, "A");
        assertFalse(intervals.checkNoBlank(4, 2));
    }
    
    //Cover: the given interval [start-end] is with blanks, the start time = 0, the start time < end time
    @Test
    public void testCheckNoBlankWithBlanks() {
    	NoBlankMultiIntervalSet<String> intervals = new NoBlankMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(0, 1, "A");
    	intervals.insert(0, 4, "B");
    	intervals.insert(6, 7, "A");
        assertFalse(intervals.checkNoBlank(0, 10));
    }
    
    //Cover: the given interval [start-end] is with blanks, the start time > 0, the start time = end time
    @Test
    public void testCheckNoBlankStartEqualsEnd() {
    	NoBlankMultiIntervalSet<String> intervals = new NoBlankMultiIntervalSet<String>(new MultiIntervalSet<>());
    	intervals.insert(0, 1, "A");
    	intervals.insert(4, 4, "B");
    	intervals.insert(6, 7, "A");
        assertFalse(intervals.checkNoBlank(2, 2));
    }
    
}
