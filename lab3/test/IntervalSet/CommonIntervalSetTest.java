package IntervalSet;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for CommonIntervalSet.
 * 
 * This class runs the IntervalSetInstanceTest tests against CommonIntervalSet, as
 * well as tests for that particular implementation.
 * 
 * Tests against the IntervalSet spec should be in IntervalSetInstanceTest.
 */
public class CommonIntervalSetTest extends IntervalSetInstanceTest {
    
    /*
     * Provide a CommonIntervalSet for tests in IntervalSetInstanceTest.
     */
    @Override public IntervalSet<String> emptyInstance() {
        return new CommonIntervalSet<String>();
    }
    
    /*
     * Testing CommonIntervalSet...
     */
    
    // Testing strategy for CommonIntervalSet.toString()
    //   the IntervalSet is empty, not empty
    
    //Cover: the IntervalSet is empty
    @Test
    public void testtoStringEmpty(){
    	CommonIntervalSet<String> intervals = new CommonIntervalSet<>();
        assertEquals("Empty IntervalSet\n",intervals.toString());
    }
    
    //Cover: the IntervalSet is not empty
    @Test
    public void testtoStringNotEmpty(){
    	CommonIntervalSet<String> intervals = new CommonIntervalSet<>();
        intervals.insert(2, 8, "lab3");
        assertEquals("Intervals :\n2 -> 8 : lab3\n",intervals.toString());
    }
    
}
