package ADTforApplication;

import java.util.Set;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;
import MultiIntervalSetDecorator.NonOverlapMultiIntervalSet;
import MultiIntervalSetDecorator.PeriodicMultiIntervalSet;

/**
 * A class used for course arrangement. Every course is arranged in interval(s). All the intervals will repeat with a
 * period of 1 week(7 days). The intervals cannot overlap with each other.
 * 
 * @param <L> type of labels in the CourseIntervalSet, must be immutable
 */
public class CourseIntervalSet<L> {
	
	private final PeriodicMultiIntervalSet<L> multiintervalset = new PeriodicMultiIntervalSet<L>(
																	new NonOverlapMultiIntervalSet<L>(
																		new MultiIntervalSet<L>()), 7);
	
	// Abstraction function:
    //   AF(multiintervalset) = a mutable set of intervals distributed on the timeline;
	//                          the intervals cannot overlap with each other;
	//                          the intervals repeat with a period of 1 week(7 days);
    //                          each interval has a label(can repeat), a start time and an end time
    // Representation invariant:
    //   true
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
	//   All methods are actually methods of PeriodicMultiIntervalSet and OverlapMultiIntervalSet, and they are safe from rep exposure
    
	/**
     * Create an empty CourseIntervalSet.
     * 
     */
    public CourseIntervalSet() {
    	checkRep();
    }
    
    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
    	assert true;
    }
	
    /**
	 * Insert an interval into the CourseIntervalSet.
	 * If the start time is negative, or the start time is bigger than the end time, or an interval
	 * overlaps with the interval to be inserted, print the information and do nothing to the CourseIntervalSet.
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
	 * Remove an interval of the CourseIntervalSet.
	 * 
	 * @param label the label of the interval to be removed
	 * @return true if this CourseIntervalSet includes an interval with the given
	 * 		   label; otherwise false (and this CourseIntervalSet is not modified)
	 */
	public boolean remove(L label) {
		boolean b = multiintervalset.remove(label);
		checkRep();
		return b;
	}
	
	/**
     * Get all the labels of intervals in this CourseIntervalSet.
     * 
     * @return the set of labels of intervals in this CourseIntervalSet.
     *         If this CourseIntervalSet is empty, it will return an empty Set
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
	 *         CourseIntervalSet does not include any interval with the given label,
	 *         it will print the information and return an empty IntervalSet
	 */
	public IntervalSet<Integer> intervals(L label) {
		IntervalSet<Integer> intervals = multiintervalset.intervals(label);
		checkRep();
		return intervals;
	}
	
	/**
	 * Get the repeat period.
	 * 
	 * @return the repeat period, if it has been set
	 */
	public long getperiod() {
		long period = multiintervalset.getperiod();
		checkRep();
		return period;
	}
	
	@Override
	public String toString() {
		String s = multiintervalset.toString();
		checkRep();
		return s;
	}
	
}
