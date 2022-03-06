package ADTforApplication;

import java.util.Set;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;
import MultiIntervalSetDecorator.NonOverlapMultiIntervalSet;

/**
 * A class used for process arrangement. Every process is arranged in interval(s). The intervals can not overlap.
 * 
 * @param <L> type of labels in the ProcessIntervalSet, must be immutable
 */
public class ProcessIntervalSet<L> {
	
	private final MultiIntervalSet<L> multiintervalset = new NonOverlapMultiIntervalSet<L>(
																new MultiIntervalSet<L>());
	
	// Abstraction function:
    //   AF(multiintervalset) = a mutable set of intervals distributed on the timeline;
    //                          each interval has a label(can repeat), a start time and an end time;
	//                          the intervals can not overlap
    // Representation invariant:
    //   true
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
	//   All methods are actually methods of NonOverMultiIntervalSet, and it is safe from rep exposure
    
	/**
     * Create an empty ProcessIntervalSet.
     * 
     */
    public ProcessIntervalSet() {
    	checkRep();
    }
    
    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
    	assert true;
    }
    
	/**
	 * Insert an interval into the ProcessIntervalSet.
	 * If the start time is negative, or the start time is bigger than the end time, or an interval overlap with
	 * the interval to be inserted, print the information and do nothing to the ProcessIntervalSet.
	 * 
	 * @param start the start time of the interval to be inserted
	 * @param end the end time of the interval to be inserted
	 * @param label the label of the interval to be inserted
	 */
	public void insert(long start, long end, L label) {
		multiintervalset.insert(start, end, label);
		checkRep();
	}
	
	/**
	 * Remove an interval of the ProcessIntervalSet.
	 * 
	 * @param label the label of the interval to be removed
	 * @return true if this ProcessIntervalSet includes an interval with the given
	 * 		   label; otherwise false (and this ProcessIntervalSet is not modified)
	 */
	public boolean remove(L label) {
		boolean b = multiintervalset.remove(label);
		checkRep();
		return b;
	}
	
	/**
     * Get all the labels of intervals in this ProcessIntervalSet.
     * 
     * @return the set of labels of intervals in this ProcessIntervalSet.
     *         If this ProcessIntervalSet is empty, it will return an empty Set
     */
	public Set<L> labels() {
		Set<L> labels = multiintervalset.labels();
		checkRep();
		return labels;
	}
	
	/**
	 * Get all the intervals of the given label.
	 * 
	 * @param label the label to be checked for all the intervals related to it 
	 * @return an IntervalSet of the given label, all the intervals are ranked in
	 *         order of start time and the ranking number start with 0.If this
	 *         ProcessIntervalSet does not include any interval with the given label,
	 *         it will print the information and return an empty IntervalSet
	 */
	public IntervalSet<Integer> intervals(L label) {
		IntervalSet<Integer> intervals = multiintervalset.intervals(label);
		checkRep();
		return intervals;
	}
	
	@Override
	public String toString() {
		String s = multiintervalset.toString();
		checkRep();
		return s;
	}

}
