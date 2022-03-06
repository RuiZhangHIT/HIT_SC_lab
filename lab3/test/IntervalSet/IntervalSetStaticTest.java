package IntervalSet;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for static methods of IntervalSet.
 * 
 * Instance methods are tested in IntervalSetInstanceTest.
 */
public class IntervalSetStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty IntervalSet
    //     observe with labels()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyLabelsEmpty() {
        assertEquals("expected empty() IntervalSet to have no intervals",
                Collections.emptySet(), IntervalSet.empty().labels());
    }
    
}
