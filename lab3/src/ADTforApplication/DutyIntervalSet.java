package ADTforApplication;

import java.util.Set;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;
import MultiIntervalSetDecorator.NoBlankMultiIntervalSet;
import MultiIntervalSetDecorator.NonOverlapMultiIntervalSet;

/**
 * A class used for duty arrangement. Every duty is arranged in interval(s). It is able to check if the whole
 * duty interval is with no blank. The intervals can not overlap.
 * 
 * @param <L> type of labels in the DutyIntervalSet, must be immutable
 */
public class DutyIntervalSet<L> {
	
	private final NoBlankMultiIntervalSet<L> multiintervalset = new NoBlankMultiIntervalSet<L>(
																new NonOverlapMultiIntervalSet<L>(
																	new MultiIntervalSet<L>()));
	
	// Abstraction function:
    //   AF(intervalset) = a mutable set of intervals distributed on the timeline;
    //                     each interval has a label(can repeat), a start time and an end time;
    //                     the intervals can not overlap
    // Representation invariant:
    //   true
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
	//   All methods are actually methods of NoBlankIntervalSet and NonOverIntervalSet, and they are safe from rep exposure
	
	/**
     * Create an empty DutyIntervalSet.
     * 
     */
    public DutyIntervalSet() {
    	checkRep();
    }
    
    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
    	assert true;
    }
    
    /**
	 * Insert an interval into the DutyIntervalSet.
	 * If the start time is negative, or the start time is bigger than the end time,
	 * or an interval overlap with the interval to be inserted, print the information and do nothing to the DutyIntervalSet.
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
	 * Remove an interval of the DutyIntervalSet.
	 * 
	 * @param label the label of the interval to be removed
	 * @return true if this DutyIntervalSet includes an interval with the given
	 * 		   label; otherwise false (and this DutyIntervalSet is not modified)
	 */
	public boolean remove(L label) {
		boolean b = multiintervalset.remove(label);
		checkRep();
		return b;
	}
	
	/**
     * Get all the labels of intervals in this DutyInteralSet.
     * 
     * @return the set of labels of intervals in this DutyIntervalSet
     *         If this DutyIntervalSet is empty, it will return an empty Set
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
	 *         DutyIntervalSet does not include any interval with the given label,
	 *         it will print the information and return an empty IntervalSet
	 */
	public IntervalSet<Integer> intervals(L label) {
		IntervalSet<Integer> intervals = multiintervalset.intervals(label);
		checkRep();
		return intervals;
	}
	
	/**
	 * Check if the given interval [start-end] is with no blank.If there are blank intervals in it, print the information
	 * to show which intervals are blank. The start time can not be negative, or bigger than the end time.
	 * 
	 * @param start the start time of the given interval
	 * @param end the end time of the given interval
	 * @return true if the given interval [start-end] is with no blank; otherwise, false. If the start time is negative or
	 *         bigger than the end time,false.
	 */
	public boolean checknoblank(long start, long end) {
		boolean b = multiintervalset.checkNoBlank(start, end);
		checkRep();
		return b;
	}
	
	@Override
	public String toString() {
		String s = multiintervalset.toString();
		checkRep();
		return s;
	}
	
}
