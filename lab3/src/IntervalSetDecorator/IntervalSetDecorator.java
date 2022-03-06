package IntervalSetDecorator;

import java.util.Set;

import IntervalSet.IntervalSet;

/**
 * An implementation of IntervalSet. A decorator of IntervalSet.
 * 
 */
public class IntervalSetDecorator<L> implements IntervalSet<L> {
	
	protected final IntervalSet<L> intervalset;
	
	// Abstraction function:
    //   AF(intervalset) = a mutable set of intervals distributed on the timeline;
    //                     each interval has a specific label, a start time and an end time
    // Representation invariant:
    //   true
    // Safety from rep exposure:
    //   All fields are protected;
    //   final makes it impossible to change the reference;
	//   All methods are actually methods of IntervalSet, and IntervalSet is safe from rep exposure
	
	/**
     * Create an IntervalSetDecorator.
     * 
     */
	public IntervalSetDecorator (IntervalSet<L> intervalset) {
		this.intervalset = intervalset;
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep(){
    	assert true;
    }
	
	@Override
	public void insert(long start, long end, L label) {
		intervalset.insert(start, end, label);
		checkRep();
	}
	
	@Override
	public boolean remove(L label) {
		boolean b = intervalset.remove(label);
		checkRep();
		return b;
	}
	
	@Override
	public Set<L> labels() {
		Set<L> labels = intervalset.labels();
		checkRep();
		return labels;
	}
	
	@Override
	public long start(L label) {
		long start = intervalset.start(label);
		checkRep();
		return start;
	}
	
	@Override
	public long end(L label) {
		long end = intervalset.end(label);
		checkRep();
		return end;
	}
	
	@Override
	public String toString() {
		String s = intervalset.toString();
		checkRep();
		return s;
	}

}
