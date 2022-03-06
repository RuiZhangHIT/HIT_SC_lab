package App;

import java.util.*;

import ADTforApplication.ProcessIntervalSet;
import IntervalSet.IntervalSet;
import L.Process;

public class ProcessScheduleApp {
	
	private final Set<Process> p;
	private final ProcessIntervalSet<Process> process = new ProcessIntervalSet<>();
	
	public ProcessScheduleApp(Scanner in) {
		System.out.println("Welcome to ProcessScheduleApp!");
		p = setProcess(in);
	}
	
	private Set<Process> setProcess(Scanner in) {
		Set<Process> process = new HashSet<>();
		System.out.println("Please input the processes' information.");
		System.out.println("How many processes are there in the schedule?");
		int n = in.nextInt();
		in.nextLine();
		for (int i = 0; i < n; i++) {
			System.out.println("process(" + (i + 1) + ")'s pid:");
			int pid = in.nextInt();
			in.nextLine();
			System.out.println("process(" + (i + 1) + ")'s name:");
			String name = in.nextLine();
			System.out.println("process(" + (i + 1) + ")'s shortest execvution time:");
			int st = in.nextInt();
			in.nextLine();
			System.out.println("process(" + (i + 1) + ")'s longest execvution time:");
			int lt = in.nextInt();
			in.nextLine();
			try {
				Process p = new Process(pid, name, st, lt);
				process.add(p);
			} catch (Exception e) {
				System.out.println("Each process must have a pid, a name, a shortest execvution time and a longest execvution time. The initialization fails.");
				e.printStackTrace();
			}
		}
		return process;
	}
	
	public void chooseProcessStrategy(Scanner in) {
		while (true) {
			boolean b = false;
			System.out.println("1.Input \"1\" to arrange process randomly.");
			System.out.println("2.Input \"2\" to arrange process with \"shortest first\" strategy.");
			int choice = in.nextInt();
			in.nextLine();
			switch(choice) {
				case 1 :
					RandomStrategy(process, p);
					break;
				case 2 :
					ShortestFirstStrategy(process, p);
					break;
				default :
					System.out.println("Illegal input number " + choice + ", the input must be 1 or 2.");
					b = true;
					break;
			}
			if (!b)
				break;
			System.out.println();
			System.out.println();
		}
		System.out.println("Thanks for your use of ProcessScheduleApp!");
	}
	
	private void RandomStrategy(ProcessIntervalSet<Process> process, Set<Process> p) {
		Random r = new Random();
		Set<Process> pp = p;
		ProcessIntervalSet<Process> pprocess = process;
		
		Object[] o = pp.toArray();
		int number = pp.size();
		Process[] pa = new Process[number];
		for (int i = 0; i < number; i++) {
			if (o[i] instanceof Process)
				pa[i] = (Process) o[i];
		}
		
		Map<Process, Integer> pi = new HashMap<>();
		for (Process p1 : pp) {
			pi.put(p1, 0);
		}
		
		int start = 0;
		Process lastp1 = null;
		while (!pp.isEmpty()) {
		
			int rtime;
			Process p1;
			do {
				int pnumber = Math.abs(r.nextInt() % number);
				rtime = Math.abs(r.nextInt() % 20) + 1;
				p1 = pa[pnumber];
			}while (!pp.contains(p1) || p1.equals(lastp1));
			showProcessSchedule(pprocess);
			System.out.println("Processing " + p1.toString() + "......");
			System.out.println();
			System.out.println();
			
			if (rtime > p1.getlongesttime()) {
				rtime = p1.getlongesttime();
			}
			if (rtime + pi.get(p1) > p1.getlongesttime()) {
				rtime = p1.getlongesttime() - pi.get(p1);
			}
			if (rtime + pi.get(p1) < p1.getshortesttime() && pp.size() == 1) {
				rtime = p1.getshortesttime() - pi.get(p1);
			}
			pprocess.insert(start, start + rtime - 1, p1);
			start += rtime;
			lastp1 = p1;
			
			pi.put(p1, pi.get(p1) + rtime);
			if (pi.get(p1) >= p1.getshortesttime()) {
				pp.remove(p1);
			}
			
			int free = Math.abs(r.nextInt() % 4);
			if (free == 0 && !pp.isEmpty()) {
				int ftime = Math.abs(r.nextInt() % 10) + 1;
				start += ftime;
				lastp1 = null;
				showProcessSchedule(pprocess);
				System.out.println("No processing. Freetime state.");
				System.out.println();
				System.out.println();
			}
		}
		System.out.println("Finish processing.");
	}
	
	private void ShortestFirstStrategy(ProcessIntervalSet<Process> process, Set<Process> p) {
		Random r = new Random();
		Set<Process> pp = p;
		ProcessIntervalSet<Process> pprocess = process;
		
		Map<Process, Integer> pi = new HashMap<>();
		for (Process p1 : pp) {
			pi.put(p1, 0);
		}
		
		int start = 0;
		Process lastp1 = null;
		while (!pp.isEmpty()) {
		
			int time = Integer.MAX_VALUE;
			Process p1 = null;
			for (Process p2 : pp) {
				if (p2.getlongesttime() - pi.get(p2) < time) {
					p1 = p2;
					time = p1.getlongesttime() - pi.get(p1);
				}
			}
			if (p1.equals(lastp1)) {
				int ftime = Math.abs(r.nextInt() % 10) + 1;
				start += ftime;
				lastp1 = null;
				showProcessSchedule(pprocess);
				System.out.println("No processing. Freetime state.");
				System.out.println();
				System.out.println();
				continue;
			}
			showProcessSchedule(pprocess);
			System.out.println("Processing " + p1.toString() + "......");
			System.out.println();
			System.out.println();
			
			int rtime = Math.abs(r.nextInt() % 20) + 1;
			if (rtime > p1.getlongesttime()) {
				rtime = p1.getlongesttime();
			}
			if (rtime + pi.get(p1) > p1.getlongesttime()) {
				rtime = p1.getlongesttime() - pi.get(p1);
			}
			pprocess.insert(start, start + rtime - 1, p1);
			start += rtime;
			lastp1 = p1;
			
			pi.put(p1, pi.get(p1) + rtime);
			if (pi.get(p1) >= p1.getshortesttime()) {
				pp.remove(p1);
			}
			
			int free = Math.abs(r.nextInt() % 4);
			if (free == 0 && !pp.isEmpty()) {
				int ftime = Math.abs(r.nextInt() % 10) + 1;
				start += ftime;
				lastp1 = null;
				showProcessSchedule(pprocess);
				System.out.println("No processing. Freetime state.");
				System.out.println();
				System.out.println();
			}
		}
		System.out.println("Finish processing.");
	}
	
	private void showProcessSchedule(ProcessIntervalSet<Process> process) {
		if (process.labels().isEmpty())
			return ;
		long laststart = -1;
		for (Process p1 : process.labels()) {
			int number = process.intervals(p1).labels().size();
			for (int i = 0; i < number; i++ ) {				
				long start = Long.MAX_VALUE;
				long end = -1;
				Process p = null;
				for (Process p2 : process.labels()) {
					IntervalSet<Integer> interval2 = process.intervals(p2);
					for (Integer j : interval2.labels()) {
						if (interval2.start(j) < start && interval2.start(j) > laststart) {
							start = interval2.start(j);
							end = interval2.end(j);
							p = p2;
						}
					}
				}
				
				laststart = start;
				System.out.println(p.toString() + " execvution time : " + start + " --> " + (end + 1));
			}
		}
	}
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		ProcessScheduleApp process = new ProcessScheduleApp(in);		
		process.chooseProcessStrategy(in);
		in.close();
		
	}

}
