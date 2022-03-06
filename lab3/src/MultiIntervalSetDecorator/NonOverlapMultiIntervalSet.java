package MultiIntervalSetDecorator;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * A decorator extends MultiIntervalSetDecorator and can make the MultiIntervalSet nonoverlap.
 * 
 */
public class NonOverlapMultiIntervalSet<L> extends MultiIntervalSetDecorator<L> {
	
	// Abstraction function:
    //   AF(multiintervalset) = a mutable set of intervals distributed on the timeline and is nonoverlap;
    //                          each interval has a label(can repeat), a start time and an end time
    // Representation invariant:
    //   all the intervals in the multiintervalset can not overlap
    // Safety from rep exposure:
    //   All fields are protected;
    //   final makes it impossible to change the reference;
    //   insert()'s parameters and return value are long, L(immutable) and void;
	//   other methods are actually methods of MultiIntervalSetDecorator, and MultiIntervalSetDecorator is safe from rep exposure
	
	/**
     * Create a NonOverlapMultiIntervalSet.
     * 
     */
	public NonOverlapMultiIntervalSet(MultiIntervalSet<L> multiintervalset) {
		super(multiintervalset);
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep(){
    	for (L label1 : multiintervalset.labels()) {
    		IntervalSet<Integer> inter1 = multiintervalset.intervals(label1);
			for (Integer i : inter1.labels()) {
				long start = inter1.start(i);
				long end = inter1.end(i);
				for (L label2 : multiintervalset.labels()) {
					IntervalSet<Integer> inter2 = multiintervalset.intervals(label2);
					for (Integer j : inter2.labels()) {
						if (!label1.equals(label2) || !i.equals(j))
						assert start > inter2.end(j) || end < inter2.start(j);
					}
				}
			}
    		
    	}
    }
	
	@Override
	public void insert(long start, long end, L label) {
		for (L label1 : multiintervalset.labels()) {
			IntervalSet<Integer> inter = multiintervalset.intervals(label1);
			for (Integer i : inter.labels()) {
				if ( (inter.start(i) <= start && start <= inter.end(i)) ||
					 (inter.start(i) <= end && end <= inter.end(i)) ||
					 (start <= inter.start(i) && inter.end(i) <= end) ||
					 (inter.start(i) <= start && end <= inter.end(i))) {
					System.out.println("Periods overlap. The insertion is not allowed.");
					checkRep();
					return ;
				}
			}
		}
		multiintervalset.insert(start, end, label);
		checkRep();
	}

}
