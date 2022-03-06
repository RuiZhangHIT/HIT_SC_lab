package IntervalSet;

import java.util.Set;

/**
 * A mutable set of intervals distributed on the timeline.
 * Each interval owns a specific label, and the labels can not repeat.
 *
 * @param <L> type of labels in the IntervalSet, must be immutable
 */
public interface IntervalSet<L> {
	
	/**
	 * Create an empty IntervalSet.
     * 
     * @param <L> type of labels in the IntervalSet, must be immutable
     * @return a new empty IntervalSet
	 */
	public static <L> IntervalSet<L> empty() {
		return new CommonIntervalSet<>();
	}
	
	/**
	 * Insert an interval into the IntervalSet.
	 * If the label repeats, or the start time is negative, or the start
	 * time is bigger than the end time, print the information and
	 * do nothing to the IntervalSet.
	 * 
	 * @param start the start time of the interval to be inserted
	 * @param end the end time of the interval to be inserted
	 * @param label the label of the interval to be inserted
	 */
	void insert(long start, long end, L label);
	
	/**
	 * Remove an interval of the IntervalSet.
	 * 
	 * @param label the label of the interval to be removed
	 * @return true if this IntervalSet includes an interval with the given
	 * 		   label; otherwise false (and this IntervalSet is not modified)
	 */
	boolean remove(L label);
	
	/**
     * Get all the labels of intervals in this InteralSet.
     * 
     * @return the set of labels of intervals in this IntervalSet
     *         If this IntervalSet is empty, it will return an empty Set
     */
	Set<L> labels();
	
	/**
	 * Get the start time of the interval with the given label.
	 * 
	 * @param label the label of the interval
	 * @return the start time of the interval; if the interval of the given
	 *         label is not in the IntervalSet, return -1
	 */
	long start(L label);
	
	/**
	 * Get the end time of the interval with the given label.
	 * 
	 * @param label the label of the interval
	 * @return the end time of the interval; if the interval of the given
	 *         label is not in the IntervalSet, return -1
	 */
	long end(L label);

}
