package IntervalSet;

import java.util.*;

/**
 * An implementation of IntervalSet.
 * 
 * @param <L> type of labels in the CommonIntervalSet, must be immutable
 */
public class CommonIntervalSet<L> implements IntervalSet<L> {
    
    private final Map<L, Map<Long, Long>> intervals = new HashMap<>();
    
    // Abstraction function:
    //   AF(intervals) = a mutable set of intervals distributed on the timeline;
    //                   each interval has a specific label, a start time and an end time
    // Representation invariant:
    //   every label is distinct, no two intervals have the same label;
    //   labels should not be null;
    //   the start time can not be negetive;
    //   the start time can not be bigger than the end time
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   labels(), start() and end() make defensive copies and return;
    //   other parameters and return values consist of immutable String, boolean or void
    
    /**
     * Create an empty CommonIntervalSet.
     * 
     */
    public CommonIntervalSet() {
    	checkRep();
    }

    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep(){
    	for (L l : intervals.keySet()) {
    		assert l != null;
    		assert intervals.get(l).size() == 1;
    		for (long start : intervals.get(l).keySet()) {
    			assert start >= 0;
    			long end = intervals.get(l).get(start);
				assert start <= end;
			}
    	}
    }
    
	@Override
	public void insert(long start, long end, L label) {
		if (label == null) {
			System.out.println("The given label is undefined.");
			checkRep();
			return ;
		}
		for (L l : intervals.keySet()) {
			if (l.equals(label)) {
				System.out.println("An interval with the given label already exists.");
				checkRep();
				return ;
			}
		}
		if (start < 0 || start > end) {
			System.out.println("Illegal interval time.");
			checkRep();
			return ;
		}
		Map<Long, Long> time = new HashMap<>();
		time.put(start, end);
		intervals.put(label, time);
		checkRep();
	}
	
	@Override
	public boolean remove(L label) {
		if (label == null) {
			System.out.println("The given label is undefined.");
			checkRep();
			return false;
		}
		if (intervals.keySet().contains(label)) {
			intervals.remove(label);
			checkRep();
			return true;
		}
		checkRep();
		return false;
	}
	
	@Override
	public Set<L> labels() {
		Set<L> labels = intervals.keySet();
		checkRep();
		return labels;
	}

	@Override
	public long start(L label) {
		if (label == null) {
			System.out.println("The given label is undefined.");
			checkRep();
			return -1;
		}
		if (intervals.keySet().contains(label)) {
			for (long start : intervals.get(label).keySet()) {
				checkRep();
				return start;
			}
		}
		checkRep();
		return -1;
	}

	@Override
	public long end(L label) {
		if (label == null) {
			System.out.println("The given label is undefined.");
			checkRep();
			return -1;
		}
		if (intervals.keySet().contains(label)) {
			for (long end : intervals.get(label).values()) {
				checkRep();
				return end;
			}
		}
		checkRep();
		return -1;
	}
	
    /**
     * print the information of the CommonIntervalSet.
     * 
     * @return a string representation of this CommonIntervalSet, including the labels
     *         of intervals as well as their start time and end time.
     */
    @Override
    public String toString() {
        if (intervals.isEmpty()){
        	checkRep();
            return "Empty IntervalSet\n";
        }
        String str = "Intervals :\n";
        for (L l : intervals.keySet()) {
        	for (long start : intervals.get(l).keySet()) {
        		str = str.concat(String.valueOf(start));
			}
        	str = str.concat(" -> ");
        	for (long end : intervals.get(l).values()) {
        		str = str.concat(String.valueOf(end));
			}
        	str = str.concat(" : ");
        	str = str.concat(l.toString() + "\n");
        }
        checkRep();
        return str;
    }
    
}
