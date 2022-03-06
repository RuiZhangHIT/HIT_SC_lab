package IntervalSetDecorator;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import IntervalSet.CommonIntervalSet;
import IntervalSet.IntervalSet;

/**
 * Tests for NoBlankIntervalSet.
 * 
 */
public class NoBlankIntervalSetTest {
    
	// Testing strategy:
	//
	// NoBlankIntervalSet():
	//     the input IntervalSet intervalset is empty, not empty
	// checkNoBlank():
	//     the given interval [start-end] is with no blank, with blank
	//     the start time < 0, = 0, > 0
	//     the start time < end time, = end time, > end time
	//
    //     other methods are actually methods of IntervalSetDecorator, so the testing strategies and
	//     Junit tests are the same as IntervalSetDecorator, here will no longer test    
    
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
	
	//Cover: the input IntervalSet intervalset is empty
	@Test
	public void testNoBlankIntervalSetEmpty() {
		NoBlankIntervalSet<String> intervals = new NoBlankIntervalSet<String>(IntervalSet.empty());
	    assertEquals(Collections.emptySet(), intervals.labels());
	}
		
	//Cover: the input IntervalSet intervalset is not empty
	@Test
	public void testNoBlankIntervalSetNotEmpty() {
		IntervalSet<String> interval = new CommonIntervalSet<String>();
		interval.insert(1, 2, "A");
		Set<String> string = new HashSet<String>();
		string.add("A");
		NoBlankIntervalSet<String> intervals = new NoBlankIntervalSet<String>(interval);   
		assertEquals(string, intervals.labels());
		assertEquals(1, intervals.start("A"));
		assertEquals(2, intervals.end("A"));
	}
	
	//Cover: the given interval [start-end] is with no blank, the start time > 0, the start time < end time
    @Test
    public void testCheckNoBlankWithNoBlank() {
    	NoBlankIntervalSet<String> intervals = new NoBlankIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(0, 1, "A");
    	intervals.insert(1, 3, "B");
    	intervals.insert(2, 4, "C");
        assertTrue(intervals.checkNoBlank(1, 4));
    }
    
    //Cover: the start time < 0, the start time < end time
    @Test
    public void testCheckNoBlankNegativeStart() {
    	NoBlankIntervalSet<String> intervals = new NoBlankIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(0, 1, "A");
    	intervals.insert(1, 3, "B");
    	intervals.insert(2, 4, "C");
        assertFalse(intervals.checkNoBlank(-2, 4));
    }
    
    //Cover: the start time > 0, the start time > end time
    @Test
    public void testCheckNoBlankStartBiggerthanEnd() {
    	NoBlankIntervalSet<String> intervals = new NoBlankIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(0, 1, "A");
    	intervals.insert(1, 3, "B");
    	intervals.insert(2, 4, "C");
        assertFalse(intervals.checkNoBlank(4, 2));
    }
    
    //Cover: the given interval [start-end] is with blanks, the start time = 0, the start time < end time
    @Test
    public void testCheckNoBlankWithBlanks() {
    	NoBlankIntervalSet<String> intervals = new NoBlankIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(0, 1, "A");
    	intervals.insert(0, 4, "B");
    	intervals.insert(6, 7, "C");
        assertFalse(intervals.checkNoBlank(0, 10));
    }
    
    //Cover: the given interval [start-end] is with blanks, the start time > 0, the start time = end time
    @Test
    public void testCheckNoBlankStartEqualsEnd() {
    	NoBlankIntervalSet<String> intervals = new NoBlankIntervalSet<String>(IntervalSet.empty());
    	intervals.insert(0, 1, "A");
    	intervals.insert(4, 4, "B");
    	intervals.insert(6, 7, "C");
        assertFalse(intervals.checkNoBlank(2, 2));
    }
    
}
