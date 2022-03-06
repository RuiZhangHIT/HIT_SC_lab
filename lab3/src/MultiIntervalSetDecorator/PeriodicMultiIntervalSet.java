package MultiIntervalSetDecorator;

import MultiIntervalSet.MultiIntervalSet;

/**
 * A decorator extends MultiIntervalSetDecorator and can make the MultiIntervalSet repeat periodically.
 * 
 */
public class PeriodicMultiIntervalSet<L> extends MultiIntervalSetDecorator<L> {
	
	private final long period;
	
	// Abstraction function:
    //   AF(multiintervalset, period) = a mutable set of intervals distributed on the timeline;
    //                                  each interval has a label(can repeat), a start time and an end time;
	//                                  the intervals repeat with a period
    // Representation invariant:
    //   the period must be positive
    // Safety from rep exposure:
    //   All fields are protected and private;
    //   final makes it impossible to change the reference;
    //   getperiod()'s return value is long;
	//   other methods are actually methods of MultiIntervalSetDecorator, and MultiIntervalSetDecorator is safe from rep exposure
	
	/**
     * Create a PeriodicMultiIntervalSet. The period must be positive. If the input period < 0, the real period will
     * be -period; if input period == 0, the real period will be 1.
     * 
     */
	public PeriodicMultiIntervalSet(MultiIntervalSet<L> multiintervalset, long period) {
		super(multiintervalset);
		if (period < 0)
			this.period = -period;
		else if (period == 0)
			this.period = 1;
		else
			this.period = period;
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
    	assert period > 0;
    }
	
	/**
	 * Get the repeat period.
	 * 
	 * @return the repeat period, if it has been set
	 */
	public long getperiod() {
		long p = period;
		checkRep();
		return p;
	}
	
}
