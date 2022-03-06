package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ADTforApplication.DutyIntervalSet;
import API.APIs;
import IntervalSet.IntervalSet;
import L.Employee;
import MultiIntervalSet.MultiIntervalSet;

public class DutyRosterApp {
	
	private final LocalDate startdate;
	private final LocalDate enddate;
	private final long duration;
	private Set<Employee> employee = new HashSet<>();
	private DutyIntervalSet<Employee> duty = new DutyIntervalSet<>();
	
	public DutyRosterApp(Scanner in) {
		System.out.println("Welcome to DutyRosterApp!");
		System.out.println("Please input the start date(yyyy-mm-dd) and the end date(yyyy-mm-dd).");
		
		startdate = setStartDate(in);
		enddate = setEndDate(in);
		duration = getDuration(startdate, enddate);
		employee = setEmployee(in);
	}
	
	public DutyRosterApp(String filename) throws Exception {
		System.out.println("Welcome to DutyRosterApp!");
		System.out.println("Initializing the duty roster with the file " + filename + " ......");
	    BufferedReader bf = new BufferedReader(new FileReader(filename));
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = bf.readLine()) != null) {
	        sb.append(line);
	    }
	    String input = sb.toString();
	    bf.close();
	    
	    Pattern pduration = Pattern.compile("Period\\{(\\d{4})-(\\d{2})-(\\d{2}),(\\d{4})-(\\d{2})-(\\d{2})\\}");
	    Matcher mduration = pduration.matcher(input);
	    if (!mduration.find()) {
	        throw new Exception("Fail to set the start date and the end date.");
	    }
	    String startyear = mduration.group(1);
	    String startmonth = mduration.group(2);
	    String startday = mduration.group(3);
	    startdate = LocalDate.of(Integer.valueOf(startyear), Integer.valueOf(startmonth), Integer.valueOf(startday));
	    String endyear = mduration.group(4);
	    String endmonth = mduration.group(5);
	    String endday = mduration.group(6);
	    enddate = LocalDate.of(Integer.valueOf(endyear), Integer.valueOf(endmonth), Integer.valueOf(endday));
	    
	    duration = getDuration(startdate, enddate);
	    
	    Pattern pemployee = Pattern.compile("([A-Z][a-z]+[A-Z][a-z]+)\\{([A-Z][A-Za-z\\s]+),(1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)-\\d{4}-\\d{4})\\}");
	    Matcher memployee = pemployee.matcher(input);
	    while (memployee.find()) {
	        String name = memployee.group(1);
	        String post = memployee.group(2);
	        String[] phonenumber = memployee.group(3).split("-");
	        StringBuilder phone = new StringBuilder();
	        for (String s : phonenumber) {
	            phone.append(s);
	        }
	        Employee e = new Employee(name, post, phone.toString());
	        employee.add(e);
	    }

	    Pattern proster = Pattern.compile("([A-Z][a-z]+[A-Z][a-z]+)\\{(\\d{4})-(\\d{2})-(\\d{2}),(\\d{4})-(\\d{2})-(\\d{2})\\}");
	    Matcher mroster = proster.matcher(input);
	    while (mroster.find()) {
	        String name = mroster.group(1);
	        boolean choose = false;
	        Employee e = null;
	        for (Employee e1 : employee) {
	            if (e1.getname().equals(name)) {
	                choose = true;
	                e = e1;
	                break;
	            }
	        }
	        if (!choose) {
	            throw new Exception("No such an employee in the roster.");
	        }
	        startyear = mroster.group(2);
	        startmonth = mroster.group(3);
	        startday = mroster.group(4);
	        LocalDate sd = LocalDate.of(Integer.valueOf(startyear), Integer.valueOf(startmonth), Integer.valueOf(startday));
	        endyear = mroster.group(5);
	        endmonth = mroster.group(6);
	        endday = mroster.group(7);
	        LocalDate ed = LocalDate.of(Integer.valueOf(endyear), Integer.valueOf(endmonth), Integer.valueOf(endday));
	        
	        long start = getDuration(startdate, sd) - 1;
			long end = getDuration(startdate, ed) - 1;
			if (getDuration(ed, enddate) >= 0) {
				duty.insert(start, end, e);
			}
			else {
				System.out.println("The interval's end date beyonds the duty's end date. Illegal!");
				System.out.println("Arrangement fails.");
			}
	    }
	    
	}
	
	private Set<Employee> setEmployee(Scanner in) {
		Set<Employee> employee = new HashSet<>();
		System.out.println("Please input the employees' information.");
		System.out.println("How many employees are there in the roster?");
		int n = in.nextInt();
		in.nextLine();
		for (int i = 0; i < n; i++) {
			System.out.println("employee(" + (i + 1) + ")'s name:");
			String name = in.nextLine();
			System.out.println("employee(" + (i + 1) + ")'s post:");
			String post = in.nextLine();
			System.out.println("employee(" + (i + 1) + ")'s phone number:");
			String phone = in.nextLine();
			try {
				Employee e = new Employee(name, post, phone);
				employee.add(e);
			} catch (Exception e) {
				System.out.println("Each employee must have a name, a post and a phone number. The initialization fails.");
				e.printStackTrace();
			}
		}
		return employee;
	}

	private LocalDate setStartDate(Scanner in) {
		System.out.println("start year:");
		int startyear = in.nextInt();
		System.out.println("start month:");
		int startmonth = in.nextInt();
		System.out.println("start day:");
		int startday = in.nextInt();
		LocalDate startdate = LocalDate.of(startyear, startmonth, startday);
		return startdate;
	}
	
	private LocalDate setEndDate(Scanner in) {
		System.out.println("end year:");
		int endyear = in.nextInt();
		System.out.println("end month:");
		int endmonth = in.nextInt();
		System.out.println("end day:");
		int endday = in.nextInt();
		LocalDate enddate = LocalDate.of(endyear, endmonth, endday);
		return enddate;
	}
	
	private long getDuration(LocalDate startdate, LocalDate enddate) {
		long duration = enddate.toEpochDay() - startdate.toEpochDay();
		return duration + 1;
	}	
	
	public void setRoster(Scanner in) {
		while (true) {
			System.out.println("1.Input \"1\" to arrange duty roster manually.");
			System.out.println("2.Input \"2\" to arrange duty roster automatically.");
			System.out.println("3.Input \"3\" to check if the duty roster is with blank intervals.");
			System.out.println("4.Input \"4\" to remove an employee from the roster.");
			System.out.println("5.Input \"5\" to see the duty roster.");
			System.out.println("6.Input \"6\" to exit from the duty roster.");
			int choice = in.nextInt();
			in.nextLine();
			boolean b = false;
			switch(choice) {
			case 1 :
				duty = manualRoster(in, employee, duty, startdate, enddate);
				break;
			case 2 :
				duty = autoRoster(employee, duty, duration);
				break;
			case 3 :
				checkNoBlank(duty, duration);
				break;
			case 4 :
				employee = removeEmployee(in, employee, duty);
				break;
			case 5:
				showDutyRoster(startdate, duty);
				break;
			case 6 :
				b = true;
				System.out.println("Thanks for your use of DutyRosterApp!");
				break;
			default :
				System.out.println("Illegal input number " + choice + ", the input must be 1-6.");
				break;
			}
			if (b)
				break;
			System.out.println();
			System.out.println();
		}
	}
	
	private DutyIntervalSet<Employee> manualRoster(Scanner in, Set<Employee> employee, DutyIntervalSet<Employee> duty, LocalDate startdate, LocalDate enddate) {
		System.out.println("Please choose an employee to arrange the duty interval.");
		System.out.println("the employee's name:");
		String name = in.nextLine();
		System.out.println("the employee's post:");
		String post = in.nextLine();
		System.out.println("the employee's phone number:");
		String phone = in.nextLine();
		try {
			Employee e = new Employee(name, post, phone);
			if (!employee.contains(e)) {
				System.out.println("No such an employee in the roster.");
				return duty;
			}
			else {
				System.out.println("Please input this employee's duty interval.");
				LocalDate sd = setStartDate(in);
				LocalDate ed = setEndDate(in);
				
				long start = getDuration(startdate, sd) - 1;
				long end = getDuration(startdate, ed) - 1;
				if (getDuration(ed, enddate) >= 0) {
					duty.insert(start, end, e);
				}
				else {
					System.out.println("The interval's end date beyonds the duty's end date. Illegal!");
					System.out.println("Arrangement fails.");
				}
			}
		} catch (Exception e) {
			System.out.println("Each employee must have a name, a post and a phone number. The initialization fails.");
			System.out.println("Arrangement fails.");
			e.printStackTrace();
		}
		return duty;
	}
	
	private DutyIntervalSet<Employee> autoRoster(Set<Employee> employee, DutyIntervalSet<Employee> duty, long duration) {
		int employeenumber = employee.size();
		long average = duration / (long) employeenumber;
		long count = 1;
		long start = 0;
		long end = average - 1;
		for (Employee e : employee) {
			if (start <= duration - 1 && count == employeenumber) {
				duty.insert(start, duration - 1, e);
				break;
			}
			duty.insert(start, end, e);
			count++;
			start += average;
			end += average;
		}
		return duty;
	}
	
	private void checkNoBlank(DutyIntervalSet<Employee> duty, long duration) {
		boolean b = duty.checknoblank(0, duration - 1);
		System.out.println("(PS:the numbers in [] are the difference between the actual date and the start date.\n"
				         + "For example, if the start date is 2021-7-1, [2-3] means [2021-7-3--2021-7-4].)");
		if (!b) {
			APIs<Employee> a = new APIs<>();
			MultiIntervalSet<Employee> iduty = new MultiIntervalSet<>();
			long idutystart = Long.MAX_VALUE;
			long idutyend = Long.MIN_VALUE;
			
			for (Employee e : duty.labels()) {
				IntervalSet<Integer> interval = duty.intervals(e);
				for (Integer i : interval.labels()) {
					long start = interval.start(i);
					long end = interval.end(i);
					iduty.insert(start, end, e);
					if (start < idutystart)
						idutystart = start;
					if (end > idutyend)
						idutyend = end;
				}
			}
			if (idutystart == Long.MAX_VALUE && idutyend == Long.MIN_VALUE) {
				System.out.println("The free time ratio is 1.0");
				return ;
			}
			double idutyduration = (double)(idutyend - idutystart + 1);
			double freetime = a.calcFreeTimeRatio(iduty) * idutyduration + (double) duration - idutyduration;
			System.out.println("The free time ratio is " + freetime / (double) duration);
		}
	}
	
	private Set<Employee> removeEmployee(Scanner in, Set<Employee> employee, DutyIntervalSet<Employee> duty) {
		System.out.println("Please choose an employee to remove from the roster.");
		System.out.println("the employee's name:");
		String name = in.nextLine();
		System.out.println("the employee's post:");
		String post = in.nextLine();
		System.out.println("the employee's phone number:");
		String phone = in.nextLine();
		try {
			Employee e = new Employee(name, post, phone);
			if (!employee.contains(e)) {
				System.out.println("No such an employee in the roster.");
				System.out.println("Remove fails.");
				return employee;
			}
			else {
				if (duty.labels().contains(e)) {
					System.out.println("The employee has been arranged duty interval and cannot be removed.");
					System.out.println("Remove fails.");
					return employee;
				}
				employee.remove(e);
				System.out.println("Remove success.");
				return employee;
			}
		} catch (Exception e) {
			System.out.println("Illeagl name, or post or phone number. The initialization fails.");
			e.printStackTrace();
		}
		System.out.println("Remove fails.");
		return employee;
	}
	
	private void showDutyRoster(LocalDate startdate, DutyIntervalSet<Employee> duty) {
		long laststart = -1;
		for (Employee e1 : duty.labels()) {
			for (int i = 0; i < duty.intervals(e1).labels().size(); i++) {
				long start = Long.MAX_VALUE;
				long end = -1;
				Employee e = e1;
				for (Employee e2 : duty.labels()) {
					for (int j = 0; j < duty.intervals(e2).labels().size(); j++) {
						if (duty.intervals(e2).start(j) < start && duty.intervals(e2).start(j) > laststart) {
							start = duty.intervals(e2).start(j);
							end = duty.intervals(e2).end(j);
							e = e2;
						}
					}
				}
				LocalDate sd = LocalDate.ofEpochDay(startdate.toEpochDay() + start);
				LocalDate ed = LocalDate.ofEpochDay(startdate.toEpochDay() + end);
				System.out.println(e.toString() + " " + "duty interval : "+ sd.toString() + " --> " + ed.toString());
				laststart = start;
			}
		}
		if (laststart == -1) {
			System.out.println("Empty duty roster!");
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		Scanner in = new Scanner(System.in);
//		DutyRosterApp duty = new DutyRosterApp("test\\txt\\test1.txt");
		DutyRosterApp duty = new DutyRosterApp(in);
		duty.setRoster(in);
		in.close();
		
	}

}
