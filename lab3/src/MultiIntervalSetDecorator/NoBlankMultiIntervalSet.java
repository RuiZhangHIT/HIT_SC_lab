package MultiIntervalSetDecorator;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * A decorator extends MultiIntervalSetDecorator and can check if the MultiIntervalSet is with no blank.
 * 
 */
public class NoBlankMultiIntervalSet<L> extends MultiIntervalSetDecorator<L> {
	
	// Abstraction function:
    //   AF(multiintervalset) = a mutable set of intervals distributed on the timeline;
    //                          each interval has a label(can repeat), a start time and an end time
    // Representation invariant:
    //   true
    // Safety from rep exposure:
    //   All fields are protected;
    //   final makes it impossible to change the reference;
    //   checkNoBlank()'s parameters and return value are long, void;
	//   other methods are actually methods of MultiIntervalSetDecorator, and MultiIntervalSetDecorator is safe from rep exposure
	
	/**
     * Create a NoBlankMultiIntervalSet.
     * 
     */
	public NoBlankMultiIntervalSet(MultiIntervalSet<L> multiintervalset) {
		super(multiintervalset);
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep(){
    	assert true;
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
	public boolean checkNoBlank(long start, long end) {
		if (start < 0) {
			System.out.println("The start time can not be negative.");
			checkRep();
			return false;
		}
		if (start > end) {
			System.out.println("The start time can not be bigger than the end time.");
			checkRep();
			return false;
		}
		long lastend = start - 1;
		boolean noblank = true, b = false;
		for (L lablel : multiintervalset.labels()) {
			IntervalSet<Integer> interval = multiintervalset.intervals(lablel);
			for (int i = 0; i < interval.labels().size(); i++) {
				long s = Long.MAX_VALUE;
				long e = Long.MIN_VALUE;
				
				for (L label1 : multiintervalset.labels()) {
					IntervalSet<Integer> interval1 = multiintervalset.intervals(label1);
					for (Integer j : interval1.labels()) {
						if (interval1.start(j) < s && interval1.end(j) > lastend) {
							s = interval1.start(j);
							e = interval1.end(j);
						}
					}
				}
				for (L label2 : multiintervalset.labels()) {
					IntervalSet<Integer> interval2 = multiintervalset.intervals(label2);
					for (Integer k : interval2.labels()) {
						if (interval2.start(k) == s && interval2.end(k) > e) {
							s = interval2.start(k);
							e = interval2.end(k);
						}
					}
				}
				if (s > lastend + 1 && start <= lastend && s != Long.MAX_VALUE) {
					System.out.println("Period [" + (lastend + 1) + "-" + (s - 1) + "] is blank.");
					noblank = false;
				}
				else if (start < s && start > lastend && s > end && s != Long.MAX_VALUE) {
					System.out.println("Period [" + (start) + "-" + end + "] is blank.");
					noblank = false;
				}
				if (e != Long.MIN_VALUE)
					lastend = e;
				if (lastend >= end) {
					b = true;
					break;
				}
			}
			if (b)
				break;
		}
		if (lastend < end) {
			System.out.println("Period [" + (lastend + 1) + "-" + end + "] is blank.");
			noblank = false;
		}
		if (noblank) {
			System.out.println("Period [" + start + "-" + end + "] is with no blank.");
		}
		checkRep();
		return noblank;
	}

}
