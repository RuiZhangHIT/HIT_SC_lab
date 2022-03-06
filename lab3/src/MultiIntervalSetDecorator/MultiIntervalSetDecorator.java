package MultiIntervalSetDecorator;

import java.util.Set;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

/**
 * A decorator of MultiIntervalSet.
 * 
 */
public class MultiIntervalSetDecorator<L> extends MultiIntervalSet<L>{
	
	protected final MultiIntervalSet<L> multiintervalset;
	
	// Abstraction function:
    //   AF(multiintervalset) = a mutable set of intervals distributed on the timeline;
    //                          each interval has a label(can repeat), a start time and an end time
    // Representation invariant:
    //   true
    // Safety from rep exposure:
    //   All fields are protected;
    //   final makes it impossible to change the reference;
	//   All methods are actually methods of MultiIntervalSet, and MultiIntervalSet is safe from rep exposure
	
	/**
     * Create a MultiIntervalSetDecorator.
     * 
     */
	public MultiIntervalSetDecorator (MultiIntervalSet<L> multiintervalset) {
		this.multiintervalset = multiintervalset;
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
		multiintervalset.insert(start, end, label);
		checkRep();
	}
	
	@Override
	public boolean remove(L label) {
		boolean b = multiintervalset.remove(label);
		checkRep();
		return b;
	}
	
	@Override
	public Set<L> labels() {
		Set<L> labels = multiintervalset.labels();
		checkRep();
		return labels;
	}
	
	@Override
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
