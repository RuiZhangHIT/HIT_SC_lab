package MultiIntervalSet;

import java.util.*;

import IntervalSet.IntervalSet;

/**
 * A mutable set of intervals distributed on the timeline.
 * Each interval owns a label, and the labels can repeat.
 *
 * @param <L> type of labels in the MultiIntervalSet, must be immutable
 */
public class MultiIntervalSet<L> {
	
	private final Map<Integer, IntervalSet<L>> intervals = new HashMap<>();
	
	// Abstraction function:
    //   AF(intervals) = a mutable set of intervals distributed on the timeline;
    //                   each interval has a label(can repeat), a start time and an end time
    // Representation invariant:
	//   if intervals.get(n).labels().contains(label), then intervals.get(n - 1).labels().contains(label) (n >= 1);
	//   intervals.get(n).start(label) > intervals.get(n - 1).start(label) or 
	//   intervals.get(n).start(label) == intervals.get(n - 1).start(label) && intervals.get(n).end(label) >= intervals.get(n - 1).end(label) (n >= 1)
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   labels() and intervals() make defensive copies and return;
    //   other parameters and return values consist of immutable String, boolean or void
	
	/**
	 * Create an empty MultiIntervalSet.
	 * 
	 */
	public MultiIntervalSet() {
		;
	}
	
	/**
	 * Create a MultiIntervalSet with the given IntervalSet initial.
	 * 
	 * @param initial an IntervalSet with distinct labels
	 */
	public MultiIntervalSet(IntervalSet<L> initial) {
    	intervals.put(0, initial);
    }
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
    	int size = intervals.size();
    	int i = size - 1;
    	while (i > 0) {
    		for (L label : intervals.get(i).labels()) {
    			assert intervals.get(i - 1).labels().contains(label);
    			assert intervals.get(i).start(label) > intervals.get(i - 1).start(label) ||
    				   (intervals.get(i).start(label) == intervals.get(i - 1).start(label) 
    				   && intervals.get(i).end(label) >= intervals.get(i - 1).end(label));
    		}
    		i--;
    	}
    }
	
	/**
	 * Insert an interval into the MultiIntervalSet.
	 * If the start time is negative, or the start time is bigger than the
	 * end time, print the information and do nothing to the MultiIntervalSet.
	 * 
	 * @param start the start time of the interval to be inserted
	 * @param end the end time of the interval to be inserted
	 * @param label the label of the interval to be inserted
	 */
	public void insert(long start, long end, L label) {
		int i = 0;
		while (i < intervals.size()) {
			if (intervals.get(i).labels().contains(label)) {
				long starttime = intervals.get(i).start(label);
				long endtime = intervals.get(i).end(label);
				if (starttime < start || (starttime == start && endtime <= end)) {
					i++;
					continue;
				}
				else {
					intervals.get(i).remove(label);
					intervals.get(i).insert(start, end, label);
					i++;
					while (i < intervals.size() && intervals.get(i).labels().contains(label)) {
						long newstarttime = intervals.get(i).start(label);
						long newendtime = intervals.get(i).end(label);
						intervals.get(i).remove(label);
						intervals.get(i).insert(starttime, endtime, label);
						starttime = newstarttime;
						endtime = newendtime;
						i++;
					}
					if (i < intervals.size()) {
						intervals.get(i).insert(starttime, endtime, label);
						checkRep();
						return ;
					}
					IntervalSet<L> interval = IntervalSet.empty();
					interval.insert(starttime, endtime, label);
					intervals.put(i, interval);
					checkRep();
					return ;
				}
			}
			else {
				intervals.get(i).insert(start, end, label);
				checkRep();
				return ;
			}
		}
		
		IntervalSet<L> interval = IntervalSet.empty();
		interval.insert(start, end, label);
		intervals.put(i, interval);
		checkRep();
	}
	
	/**
     * Get all the labels of intervals in this MultiInteralSet.
     * 
     * @return the set of labels of intervals in this MultiIntervalSet.
     *         If this MultiIntervalSet is empty, it will return an empty Set
     */
	public Set<L> labels() {
		Set<L> labels = new HashSet<>();
		for (IntervalSet<L> i : intervals.values()) {
			labels.addAll(i.labels());
		}
		checkRep();
		return labels;
	}
	
	/**
	 * Remove an interval of the MultiIntervalSet.
	 * 
	 * @param label the label of the interval to be removed
	 * @return true if this MultiIntervalSet includes an interval with the given
	 * 		   label; otherwise false (and this MultiIntervalSet is not modified)
	 */
	public boolean remove(L label) {
		if (label == null) {
			System.out.println("The given label is undefined.");
			checkRep();
			return false;
		}
		int i = 0;
		while (i < intervals.size() && intervals.get(i).labels().contains(label)) {
			intervals.get(i).remove(label);
			i++;				
		}
		if (i == 0) {
			checkRep();
			return false;
		}
		checkRep();
		return true;
	}
	
	/**
	 * Get all the intervals of the given label.
	 * 
	 * @param label the label to be checked for all the intervals related to it 
	 * @return an IntervalSet of the given label, all the intervals are ranked in
	 *         order of start time and the ranking number start with 0.If this
	 *         MultiIntervalSet does not include any interval with the given label,
	 *         it will print the information and return an empty IntervalSet
	 */
	public IntervalSet<Integer> intervals(L label) {
		IntervalSet<Integer> interval = IntervalSet.empty();
		int i = 0;
		while (i < intervals.size() && intervals.get(i).labels().contains(label)) {
			long start = intervals.get(i).start(label);
			long end = intervals.get(i).end(label);
			interval.insert(start, end, i);
			i++;				
		}
		checkRep();
		return interval;
	}
	
	/**
     * print the information of the MultiIntervalSet.
     * 
     * @return a string representation of this MultiIntervalSet, including the labels
     *         of intervals as well as their start time and end time.
     */
    @Override
    public String toString() {
        if (intervals.isEmpty()){
        	checkRep();
            return "Empty IntervalSet\n";
        }
        String str = "Intervals :\n";
        for (L l : intervals.get(0).labels()) {
        	str = str.concat(l.toString() + ":\n");
        	for (int i = 0; i < intervals.size(); i++) {
        		if (!intervals.get(i).labels().contains(l))
        			break;
        		str = str.concat(String.valueOf(intervals.get(i).start(l)));
        		str = str.concat(" -> ");
        		str = str.concat(String.valueOf(intervals.get(i).end(l)));
        		str = str.concat("\n");
        	}
        }
        checkRep();
        return str;
    }
    
}
