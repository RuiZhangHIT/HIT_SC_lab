package IntervalSetDecorator;

import java.util.Set;

import IntervalSet.IntervalSet;

/**
 * An implementation of IntervalSet. A decorator extends IntervalSetDecorator and can make the IntervalSet nonoverlap.
 * 
 */
public class NonOverlapIntervalSet<L> extends IntervalSetDecorator<L> implements IntervalSet<L> {
	
	// Abstraction function:
    //   AF(intervalset) = a mutable set of intervals distributed on the timeline and is nonoverlap;
    //                     each interval has a specific label, a start time and an end time
    // Representation invariant:
    //   all the intervals in the intervalset can not overlap
    // Safety from rep exposure:
    //   All fields are protected;
    //   final makes it impossible to change the reference;
	//   insert()'s parameters and return value are long, L(immutable) and void;
	//   other methods are actually methods of IntervalSetDecorator, and IntervalSetDecorator is safe from rep exposure
	
	/**
     * Create a NonOverlapIntervalSet.
     * 
     */
	public NonOverlapIntervalSet(IntervalSet<L> intervalset) {
		super(intervalset);
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep(){
    	Set<L> labels = intervalset.labels();
    	for (L l1 : labels) {
    		long start = intervalset.start(l1);
    		long end = intervalset.end(l1);
    		for (L l2 : labels) {
    			if (!l2.equals(l1)) {
    				assert start > intervalset.end(l2) || end < intervalset.start(l2);
    			}
    		}
    	}
    }
    
	@Override
	public void insert(long start, long end, L label) {
		for (L l : intervalset.labels()) {
			if ( (intervalset.start(l) <= start && start <= intervalset.end(l)) ||
				 (intervalset.start(l) <= end && end <= intervalset.end(l)) ) {
				System.out.println("Periods overlap. The insertion is not allowed.");
				checkRep();
				return ;
			}
		}
		intervalset.insert(start, end, label);
		checkRep();
	}

}
