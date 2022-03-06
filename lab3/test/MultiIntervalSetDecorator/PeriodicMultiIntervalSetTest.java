package MultiIntervalSetDecorator;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * Tests for PeriodicMultiIntervalSet.
 * 
 */
public class PeriodicMultiIntervalSetTest {
    
	// Testing strategy:
	//
	// PeriodicMultiIntervalSet():
	//     the input MultiIntervalSet multiintervalset is empty, not empty
	//     the input long period < 0, = 0, > 0
	// getperiod():
	//     the input period < 0, = 0, > 0
	//
    //     other methods are actually methods of MultiIntervalSetDecorator, so the testing strategies and
	//     Junit tests are the same as MultiIntervalSetDecorator, here will no longer test    
    
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
	
	//Cover: the input MultiIntervalSet multiintervalset is empty, the input long period < 0
	@Test
	public void testPeriodicMultiIntervalSetEmpty() {
		PeriodicMultiIntervalSet<String> intervals = new PeriodicMultiIntervalSet<String>(new MultiIntervalSet<>(), -3);
	    assertEquals(Collections.emptySet(), intervals.labels());
	    assertEquals(3, intervals.getperiod());
	}
		
	//Cover: the input MultiIntervalSet multiintervalset is not empty, the input long period = 0
	@Test
	public void testPeriodicMultiIntervalSetNotEmpty() {
		MultiIntervalSet<String> multiinterval = new MultiIntervalSet<String>();
		multiinterval.insert(1, 2, "A");
		Set<String> string = new HashSet<String>();
		string.add("A");
		PeriodicMultiIntervalSet<String> intervals = new PeriodicMultiIntervalSet<String>(multiinterval, 0);
		IntervalSet<Integer> inter = IntervalSet.empty();
        inter.insert(1, 2, 0);
        assertEquals(string, intervals.labels());
        assertEquals(1, intervals.getperiod());
		
		IntervalSet<Integer> interval = intervals.intervals("A");
        for (Integer i : inter.labels()) {
        	assertEquals(inter.start(i), interval.start(i));
        	assertEquals(inter.end(i), interval.end(i));
        }
	}
	
	//Cover: the input MultiIntervalSet multiintervalset is not empty, the input long period > 0
	@Test
	public void testPeriodicMultiIntervalSetPositivePeriod() {
		MultiIntervalSet<String> multiinterval = new MultiIntervalSet<String>();
		multiinterval.insert(1, 2, "A");
		Set<String> string = new HashSet<String>();
		string.add("A");
		PeriodicMultiIntervalSet<String> intervals = new PeriodicMultiIntervalSet<String>(multiinterval, 7);
		IntervalSet<Integer> inter = IntervalSet.empty();
	    inter.insert(1, 2, 0);
	    assertEquals(string, intervals.labels());
	    assertEquals(7, intervals.getperiod());
		
		IntervalSet<Integer> interval = intervals.intervals("A");
	    for (Integer i : inter.labels()) {
	      	assertEquals(inter.start(i), interval.start(i));
	       	assertEquals(inter.end(i), interval.end(i));
	    }
	}
		
	//Cover:the input period > 0
	@Test
	public void testGetPeriodPositive() {
		MultiIntervalSet<String> multiinterval = new MultiIntervalSet<String>();
		multiinterval.insert(1, 2, "A");
		PeriodicMultiIntervalSet<String> intervals = new PeriodicMultiIntervalSet<String>(multiinterval, 8);
		assertEquals(8, intervals.getperiod());
	}
	
	//Cover:the input period > 0
	@Test
	public void testGetPeriodZero() {
		MultiIntervalSet<String> multiinterval = new MultiIntervalSet<String>();
		multiinterval.insert(1, 2, "A");
		PeriodicMultiIntervalSet<String> intervals = new PeriodicMultiIntervalSet<String>(multiinterval, 0);
		assertEquals(1, intervals.getperiod());
	}
	
	//Cover:the input period > 0
	@Test
	public void testGetPeriodNegative() {
		MultiIntervalSet<String> multiinterval = new MultiIntervalSet<String>();
		multiinterval.insert(1, 2, "A");
		PeriodicMultiIntervalSet<String> intervals = new PeriodicMultiIntervalSet<String>(multiinterval, -18);
		assertEquals(18, intervals.getperiod());
	}
    
}
