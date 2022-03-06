package API;

import static org.junit.Assert.*;

import org.junit.Test;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * Tests for APIs.
 * 
 */
public class APIsTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // the test for Similarity in the lab manual 
	// (PS: The representations for start time and end time are a little different.)
    @Test
    public void testSimilarity() {
    	APIs<String> a = new APIs<>();
    	MultiIntervalSet<String> s1 = new MultiIntervalSet<>();
    	s1.insert(0, 4, "A");
    	s1.insert(10, 19, "B");
    	s1.insert(20, 24, "A");
    	s1.insert(25, 29,"B");
    	MultiIntervalSet<String> s2 = new MultiIntervalSet<>();
    	s2.insert(0, 4, "C");
    	s2.insert(10, 19, "B");
    	s2.insert(20, 34, "A");
    	assertEquals((double)3/7, a.Similarity(s1, s2), 0.0000001);
    }
    
    // another test for Similarity
    @Test
    public void testSimilarity2() {
    	APIs<String> a = new APIs<>();
    	MultiIntervalSet<String> s1 = new MultiIntervalSet<>();
    	s1.insert(4, 9, "A");
    	s1.insert(10, 12, "B");
    	s1.insert(15, 17, "C");
    	s1.insert(21, 29, "D");
    	MultiIntervalSet<String> s2 = new MultiIntervalSet<>();
    	s2.insert(0, 5, "A");
    	s2.insert(6, 7, "B");
    	s2.insert(8, 10, "A");
    	s2.insert(11, 19,"C");
    	s2.insert(24, 26, "D");
    	assertEquals((double)1/3, a.Similarity(s1, s2), 0.0000001);
    }
	
	// a test for calcConflictRatio(IntervalSet<L> set) 
	// (PS: Pay attetion to more than 2 intervals' confliction.)
    @Test
    public void testcalcConflictRatioIntervalSet() {
    	APIs<String> a = new APIs<>();
    	IntervalSet<String> set = IntervalSet.empty();
    	set.insert(1, 4, "A");
    	set.insert(2, 6, "B");
    	set.insert(4, 10, "C");
    	assertEquals((double)1/2, a.calcConflictRatio(set), 0);
    }
	
	// a test for calcConflictRatio(MultiIntervalSet<L> set) 
	// (PS: Pay attetion to more than 2 intervals' confliction.)
    @Test
    public void testcalcConflictRatioMultiIntervalSet() {
    	APIs<String> a = new APIs<>();
    	MultiIntervalSet<String> mulset = new MultiIntervalSet<>();
    	mulset.insert(0, 5, "A");
    	mulset.insert(2, 8, "A");
    	mulset.insert(10, 15, "B");
    	mulset.insert(4, 19, "C");
    	assertEquals((double)13/20, a.calcConflictRatio(mulset), 0);
    }
	
	// a test for calcFreeTimeRatio(IntervalSet<L> set) 
	// (PS: Pay attetion to more than 2 intervals' confliction.)
    @Test
    public void testcalcFreeTimeRatioIntervalSet() {
    	APIs<String> a = new APIs<>();
    	IntervalSet<String> set = IntervalSet.empty();
    	set.insert(1, 3, "A");
    	set.insert(5, 6, "B");
    	set.insert(4, 6, "C");
    	set.insert(9, 10, "D");
    	assertEquals((double)1/5, a.calcFreeTimeRatio(set), 0);
    }
	
	// a test for calcFreeTimeRatio(MultiIntervalSet<L> set) 
	// (PS: Pay attetion to more than 2 intervals' confliction.)
    @Test
    public void testcalcFreeTimeRatioMultiIntervalSet() {
    	APIs<String> a = new APIs<>();
    	MultiIntervalSet<String> mulset = new MultiIntervalSet<>();
    	mulset.insert(0, 5, "A");
    	mulset.insert(2, 6, "A");
    	mulset.insert(5, 7, "B");
    	mulset.insert(10, 15, "B");
    	mulset.insert(19, 19, "B");
    	mulset.insert(14, 16, "C");
    	assertEquals((double)1/5, a.calcFreeTimeRatio(mulset), 0);
    }
	
}
