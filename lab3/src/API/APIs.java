package API;

import java.util.Set;

import IntervalSet.IntervalSet;
import MultiIntervalSet.MultiIntervalSet;

public class APIs<L> {
	
	public double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) {
		long start = Long.MAX_VALUE;
		long end = Long.MIN_VALUE;
		double similar = 0;
		Set<L> s1labels = s1.labels();
		for (L label1 : s1labels) {
			IntervalSet<Integer> intervals1 = s1.intervals(label1);
			for (Integer i : intervals1.labels()) {
				long start1 = intervals1.start(i);
				long end1 = intervals1.end(i);
				if (start1 < start)
					start = start1;
				if (end1 > end)
					end = end1;					
				
				Set<L> s2labels = s2.labels();
				for (L label2 : s2labels) {
					IntervalSet<Integer> intervals2 = s2.intervals(label2);
					for (Integer j : intervals2.labels()) {
						long start2 = intervals2.start(j);
						long end2 = intervals2.end(j);
						if (start2 < start)
							start = start2;							
						if (end2 > end)
							end = end2;
													
						if (label1.equals(label2) && start1 <= start2 && start2 <= end1 && end1 <= end2)
							similar += end1 - start2 + 1;
						else if (label1.equals(label2) && start2 <= start1 && end1 <= end2)
							similar += end1 - start1 +1;
						else if (label1.equals(label2) && start2 <= start1 && start1 <= end2 && end2 <= end1)
							similar += end2 - start1 +1;
						else if (label1.equals(label2) && start1 <= start2 && end2 <= end1)
							similar += end2 - start2 +1;
					}
				}
			}
		}
		double similarity = similar / (double)(end - start + 1);
		return similarity;
	}
	
	public double calcConflictRatio(IntervalSet<L> set) {
		long start = Long.MAX_VALUE;
		long end = Long.MIN_VALUE;
		double conflict = 0;
		Set<L> labels = set.labels();
		for (L l : labels) {
			if (set.start(l) < start)
				start = set.start(l);
			if (set.end(l) > end)
				end = set.end(l);
		}
		for (long count = start; count <= end; count++) {
			int conflictnumber = 0;
			for (L l : labels) {
				if (set.start(l) <= count && count <= set.end(l))
					conflictnumber++;
				if (conflictnumber == 2) {
					conflict++;
					break;
				}
			}
		}
		double conflictratio = conflict / (double)(end - start + 1);
		return conflictratio;
	}
	
	public double calcConflictRatio(MultiIntervalSet<L> set) {
		long start = Long.MAX_VALUE;
		long end = Long.MIN_VALUE;
		double conflict = 0;
		Set<L> labels = set.labels();
		for (L l : labels) {
			IntervalSet<Integer> intervals = set.intervals(l);
			for (Integer i : intervals.labels()) {
				if (intervals.start(i) < start)
					start = intervals.start(i);
				if (intervals.end(i) > end)
					end = intervals.end(i);
			}
		}
		for (long count = start; count <= end; count++) {
			int conflictnumber = 0, breaktime = 0;
			for (L l : labels) {
				IntervalSet<Integer> intervals = set.intervals(l);
				for (Integer i : intervals.labels()) {
					if (intervals.start(i) <= count && count <= intervals.end(i))
						conflictnumber++;
					if (conflictnumber == 2) {
						conflict++;
						breaktime++;
						break;
					}
				}
				if (breaktime == 1)
					break;
			}
		}
		double conflictratio = conflict / (double)(end - start + 1);
		return conflictratio;
	}
	
	public double calcFreeTimeRatio(IntervalSet<L> set) {
		long start = Long.MAX_VALUE;
		long end = Long.MIN_VALUE;
		Set<L> labels = set.labels();
		for (L l : labels) {
			if (set.start(l) < start)
				start = set.start(l);
			if (set.end(l) > end)
				end = set.end(l);
		}
		double freetime = (double)(end - start + 1);
		for (long count = start; count <= end; count++) {
			for (L l : labels) {
				if (set.start(l) <= count && count <= set.end(l)) {
					freetime--;
					break;
				}	
			}
		}
		double freetimeratio = freetime / (double)(end - start + 1);
		return freetimeratio;
	}
	
	public double calcFreeTimeRatio(MultiIntervalSet<L> set) {
		long start = Long.MAX_VALUE;
		long end = Long.MIN_VALUE;
		Set<L> labels = set.labels();
		for (L l : labels) {
			IntervalSet<Integer> intervals = set.intervals(l);
			for (Integer i : intervals.labels()) {
				if (intervals.start(i) < start)
					start = intervals.start(i);
				if (intervals.end(i) > end)
					end = intervals.end(i);
			}
		}
		double freetime = (double)(end - start + 1);
		for (long count = start; count <= end; count++) {
			int breaktime = 0;
			for (L l : labels) {
				IntervalSet<Integer> intervals = set.intervals(l);
				for (Integer i : intervals.labels()) {
					if (intervals.start(i) <= count && count <= intervals.end(i)) {
						freetime--;
						breaktime++;
						break;
					}
				}
				if (breaktime == 1)
					break;
			}
		}
		double freetimeratio = freetime / (double)(end - start + 1);
		return freetimeratio;
	}
	
}
