package IntervalSetDecorator;

import IntervalSet.IntervalSet;

/**
 * An implementation of IntervalSet. A decorator extends IntervalSetDecorator and can check if the IntervalSet is with no blank.
 * 
 */
public class NoBlankIntervalSet<L>  extends IntervalSetDecorator<L> implements IntervalSet<L> {
	
	// Abstraction function:
    //   AF(intervalset) = a mutable set of intervals distributed on the timeline;
    //                     each interval has a specific label, a start time and an end time
    // Representation invariant:
    //   true
    // Safety from rep exposure:
    //   All fields are protected;
    //   final makes it impossible to change the reference;
	//   checkNoBlank()'s parameters and return value are long, void;
	//   other methods are actually methods of IntervalSetDecorator, and IntervalSetDecorator is safe from rep exposure
	
	/**
     * Create a NoBlankIntervalSet.
     * 
     */
	public NoBlankIntervalSet(IntervalSet<L> intervalset) {
		super(intervalset);
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
		boolean noblank = true;
		for (int i = 0; i < intervalset.labels().size(); i++) {
			long s = Long.MAX_VALUE;
			long e = Long.MIN_VALUE;
			for (L label1 : intervalset.labels()) {
				if (intervalset.start(label1) < s && intervalset.end(label1) > lastend) {
					s = intervalset.start(label1);
					e = intervalset.end(label1);
				}
			}
			for (L label2 : intervalset.labels()) {
				if (intervalset.start(label2) == s && intervalset.end(label2) > e) {
					s = intervalset.start(label2);
					e = intervalset.end(label2);
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
			if (lastend >= end)
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
