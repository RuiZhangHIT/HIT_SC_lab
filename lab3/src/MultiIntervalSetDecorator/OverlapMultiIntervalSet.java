package MultiIntervalSetDecorator;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * A decorator extends MultiIntervalSetDecorator and can make the MultiIntervalSet able to overlap
 * (the intervals with the same label still nonoverlap).
 * 
 */
public class OverlapMultiIntervalSet<L> extends MultiIntervalSetDecorator<L> {
	
	// Abstraction function:
    //   AF(multiintervalset) = a mutable set of intervals distributed on the timeline and can overlap(the intervals with the same label still nonoverlap);
    //                          each interval has a label(can repeat), a start time and an end time
    // Representation invariant:
    //   the intervals with the same label in the multiintervalset can not overlap
    // Safety from rep exposure:
    //   All fields are protected;
    //   final makes it impossible to change the reference;
    //   insert()'s parameters and return value are long, L(immutable) and void;
	//   other methods are actually methods of MultiIntervalSetDecorator, and MultiIntervalSetDecorator is safe from rep exposure
	
	/**
     * Create an OverlapMultiIntervalSet.
     * 
     */
	public OverlapMultiIntervalSet(MultiIntervalSet<L> multiintervalset) {
		super(multiintervalset);
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep(){
    	for (L label : multiintervalset.labels()) {
    		IntervalSet<Integer> inter = multiintervalset.intervals(label);
			for (Integer i : inter.labels()) {
				long start = inter.start(i);
				long end = inter.end(i);
				for (Integer j : inter.labels()) {
					if (!i.equals(j))
					assert start > inter.end(j) || end < inter.start(j);
				}
			}
    		
    	}
    }
	
	@Override
	public void insert(long start, long end, L label) {
		IntervalSet<Integer> inter = multiintervalset.intervals(label);
		for (Integer i : inter.labels()) {
			if ( (inter.start(i) <= start && start <= inter.end(i)) ||
				 (inter.start(i) <= end && end <= inter.end(i)) ) {
				System.out.println("Periods overlap. The insertion is not allowed.");
				checkRep();
				return ;
			}
		}
		multiintervalset.insert(start, end, label);
		checkRep();
	}

}
