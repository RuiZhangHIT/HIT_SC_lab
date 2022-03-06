package App;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import ADTforApplication.CourseIntervalSet;
import API.APIs;
import IntervalSet.IntervalSet;
import L.Course;
import MultiIntervalSet.MultiIntervalSet;

public class CourseScheduleApp {
	
	private final LocalDate startdate;
	private final int week;
	private final Map<Course, Integer> courselist;
	private CourseIntervalSet<Course> course = new CourseIntervalSet<>();
	
	public CourseScheduleApp(Scanner in) {
		System.out.println("Welcome to CourseScheduleApp!");
		System.out.println("Please input the start date(yyyy-mm-dd) of the semester.");
		System.out.println("Attetion : the start date must be Monday.");
		
		startdate = setStartDate(in);
		week = setWeekNumber(in);
		courselist = setCourseList(in);
	}
	
	private LocalDate setStartDate(Scanner in) {
		LocalDate startdate;
		DayOfWeek w = DayOfWeek.MONDAY; 
		do {
			System.out.println("start year:");
			int startyear = in.nextInt();
			System.out.println("start month:");
			int startmonth = in.nextInt();
			System.out.println("start day:");
			int startday = in.nextInt();
			startdate = LocalDate.of(startyear, startmonth, startday);
		}while(startdate.getDayOfWeek() != w);
		return startdate;
	}
	
	private int setWeekNumber(Scanner in) {
		System.out.println("How many weeks are there in this semester(positive number)?");
		int weeknumber;
		do {
			weeknumber = in.nextInt();
		}while(weeknumber <= 0);
		return weeknumber;
	}
	
	private Map<Course, Integer> setCourseList(Scanner in) {
		Map<Course, Integer> courselist = new HashMap<>();
		System.out.println("Please input the courses' information.");
		System.out.println("How many courses are there in the semester?");
		int n = in.nextInt();
		in.nextLine();
		for (int i = 0; i < n; i++) {
			System.out.println("course(" + (i + 1) + ")'s id:");
			int id = in.nextInt();
			in.nextLine();
			System.out.println("course(" + (i + 1) + ")'s name:");
			String name = in.nextLine();
			System.out.println("course(" + (i + 1) + ")'s teacher's name:");
			String tname = in.nextLine();
			System.out.println("course(" + (i + 1) + ")'s class location:");
			String loc = in.nextLine();
			int hour = -1;
			do {
				System.out.println("course(" + (i + 1) + ")'s class hour every week(positive even number):");
				hour = in.nextInt();
				in.nextLine();
			}while(hour < 0 || hour % 2 == 1);
			
			try {
				Course c = new Course(id, name, tname, loc);
				courselist.put(c, hour);
			} catch (Exception e) {
				System.out.println("Each course must have an id, a name, a teacher's name and a class location. The initialization fails.");
				e.printStackTrace();
			}
		}
		return courselist;
	}
	
	public void setCourse(Scanner in) {
		while(true) {
			System.out.println("1.Input \"1\" to arrange courses.");
			System.out.println("2.Input \"2\" to check the unfinished arrangments of courses.");
			System.out.println("3.Input \"3\" to see the free time ratio of every week.");
			System.out.println("4.Input \"4\" to see the conflict time ratio of every week.");
			System.out.println("5.Input \"5\" to see the course schedule of a specific day.");
			System.out.println("6.Input \"6\" to exit from the course schedule.");
			int choice = in.nextInt();
			in.nextLine();
			boolean b = false;
			switch(choice) {
			case 1 :
				course = arrangeCourse(in, course, courselist);
				break;
			case 2 :
				checkUnfinished(course, courselist);
				break;
			case 3 :
				calcFreeTimeRatio(course);
				break;
			case 4 :
				calcConflictRatio(course);
				break;
			case 5 :
				showCourseSchedule(in, course, startdate, week);
				break;
			case 6 :
				b = true;
				System.out.println("Thanks for your use of CourseScheduleApp!");
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
	
	private CourseIntervalSet<Course> arrangeCourse(Scanner in, CourseIntervalSet<Course> course, Map<Course, Integer> courselist) {
		System.out.println("Please choose a course to arrange the class time.");
		System.out.println("the course's id:");
		int id = in.nextInt();
		in.nextLine();
		Course c = null;
		for (Course c1 : courselist.keySet()) {
			if (c1.getid() == id)
				c = c1;
		}
		if (c == null) {
			System.out.println("No such a coourse in the semester.");
			return course;
		}
		IntervalSet<Integer> interval = course.intervals(c);
		long hour = 0;
		for (Integer i : interval.labels()) {
			hour += interval.end(i) - interval.start(i) + 1;
		}
		if (hour == courselist.get(c)) {
			System.out.println("The course has been arranged and already reached the class hour every week.");
			return course;
		}
		
		int day = -1;
		do {
			System.out.println("the course's class day:");
			System.out.println("Input number 1-7, for example: 1 means Monday, 5 means Friday.");
			day = in.nextInt();
			in.nextLine();
		}while (day < 1 || day > 7);
		
		int start = -1;
		do {
			System.out.println("Attention : The course's class time can only be 8-10, 10-12, 13-15, 15-17, 19-21.");
			System.out.println("the course's start time:");
			start = in.nextInt();
			in.nextLine();
		}while (start != 8 && start != 10 && start != 13 && start != 15 && start !=19);
		int end = start + 1;
		
		long coursestart = (long)day * 100 + (long)start;
		long courseend = (long)day * 100 + (long)end;
		course.insert(coursestart, courseend, c);
		return course;
	}
	
	private void checkUnfinished(CourseIntervalSet<Course> course, Map<Course, Integer> courselist) {
		boolean b =false;
		for (Course c : courselist.keySet()) {
			IntervalSet<Integer> interval = course.intervals(c);
			long hour = 0;
			for (Integer i : interval.labels()) {
				hour += interval.end(i) - interval.start(i) + 1;
			}
			if (hour < courselist.get(c)) {
				b = true;
				System.out.println("The arrangement for course " + c.toString() + " unfinished.");
			}
		}
		if (!b) {
			System.out.println("All the courses have been arranged.");
		}
	}
	
	private void calcFreeTimeRatio(CourseIntervalSet<Course> course) {
		APIs<Course> a = new APIs<>();
		MultiIntervalSet<Course> mcourse = new MultiIntervalSet<>();
		long mcoursestart = Long.MAX_VALUE;
		long mcourseend = Long.MIN_VALUE;
		for (Course c : course.labels()) {
			IntervalSet<Integer> interval = course.intervals(c);
			for (Integer i : interval.labels()) {
				long start = interval.start(i);
				
				long m = start / 100 - 1;
				long n = start % 100;
				if(n == 8)
					n = 0;
				else if (n == 10) 
					n = 2;
				else if (n == 13)
					n = 4;
				else if (n == 15)
					n = 6;
				else if (n == 19)
					n = 8;
				start = m * 10 + n;
				
				mcourse.insert(start, start + 1, c);
				if (start < mcoursestart)
					mcoursestart = start;
				if (start + 1 > mcourseend)
					mcourseend = start + 1;
			}
		}
		if (mcoursestart == Long.MAX_VALUE && mcourseend == Long.MIN_VALUE) {
			System.out.println("The free time ratio is 1.0");
			return ;
		}
		double mcourseduration = (double)(mcourseend - mcoursestart + 1);
		double freetime = a.calcFreeTimeRatio(mcourse) * mcourseduration + (2 * 5 * 7) - mcourseduration;
		System.out.println("The free time ratio is " + freetime / (2 * 5 * 7));
	}
	
	private void calcConflictRatio(CourseIntervalSet<Course> course) {
		APIs<Course> a = new APIs<>();
		MultiIntervalSet<Course> mcourse = new MultiIntervalSet<>();
		long mcoursestart = Long.MAX_VALUE;
		long mcourseend = Long.MIN_VALUE;
		for (Course c : course.labels()) {
			IntervalSet<Integer> interval = course.intervals(c);
			for (Integer i : interval.labels()) {
				long start = interval.start(i);
				
				long m = start / 100 - 1;
				long n = start % 100;
				if(n == 8)
					n = 0;
				else if (n == 10) 
					n = 2;
				else if (n == 13)
					n = 4;
				else if (n == 15)
					n = 6;
				else if (n == 19)
					n = 8;
				start = m * 10 + n;
				
				mcourse.insert(start, start + 1, c);
				if (start < mcoursestart)
					mcoursestart = start;
				if (start + 1 > mcourseend)
					mcourseend = start + 1;
			}
		}
		if (mcoursestart == Long.MAX_VALUE && mcourseend == Long.MIN_VALUE) {
			System.out.println("The conflict time ratio is 0.0");
			return ;
		}
		double mcourseduration = (double)(mcourseend - mcoursestart + 1);
		double conflicttime = a.calcConflictRatio(mcourse) * mcourseduration;
		System.out.println("The conflict time ratio is " + conflicttime / (2 * 5 * 7));
	}
	
	private void showCourseSchedule(Scanner in, CourseIntervalSet<Course> course, LocalDate startdate, int week) {
		System.out.println("Please input the day of which you want to check the course schedule:");
		System.out.println("the year:");
		int year = in.nextInt();
		System.out.println("the month:");
		int month = in.nextInt();
		System.out.println("the day:");
		int day = in.nextInt();
		LocalDate date = LocalDate.of(year, month, day);
		
		if (date.toEpochDay() - startdate.toEpochDay() > week * course.getperiod() - 1) {
			System.out.println("The input date is not in the semester.");
			return ;
		}
		long weeknumber = (date.toEpochDay() - startdate.toEpochDay()) / course.getperiod() + 1;
		System.out.println(date.toString() + "  Week" + weeknumber + " " + date.getDayOfWeek());
		long w = (long)date.getDayOfWeek().getValue();
		
		CourseIntervalSet<Course> daycourse = new CourseIntervalSet<>();
		for (Course c : course.labels()) {
			IntervalSet<Integer> interval = course.intervals(c);
			for (Integer i : interval.labels()) {
				long start = interval.start(i);
				long end = interval.end(i);
				if (start / 100 == w) {
					daycourse.insert(start % 100, end % 100, c);
				}
			}
		}
		
		if (daycourse.labels().isEmpty()) {
			System.out.println("No courses.");
			return ;
		}
		
		long laststart = -1; 
		Set<Course> set = new HashSet<>(); 
		for (Course c1 : daycourse.labels()) {
			int number = daycourse.intervals(c1).labels().size();
			for (int i = 0; i < number; i++ ) {
				long start = Long.MAX_VALUE;
				long end = -1;
				Course c = c1;
				
				for (Course c2 : daycourse.labels()) {
					IntervalSet<Integer> interval2 = daycourse.intervals(c2);
					for (Integer j : interval2.labels()) {
						if (interval2.start(j) == laststart && !set.contains(c2)) {
							start = interval2.start(j);
							end = interval2.end(j);
							c = c2;
						}
					}
				}
				
				for (Course c2 : daycourse.labels()) {
					IntervalSet<Integer> interval2 = daycourse.intervals(c2);
					for (Integer j : interval2.labels()) {
						if (interval2.start(j) < start && interval2.start(j) > laststart) {
							start = interval2.start(j);
							end = interval2.end(j);
							set.clear();
							c = c2;
						}
					}
				}
				
				if (start == Long.MAX_VALUE) {
					start = daycourse.intervals(c).start(number - 1);
					end = daycourse.intervals(c).end(number - 1);
				}
				laststart = start;
				set.add(c);
				System.out.println(c.toString() + " class time : " + start + ":00 -- " + (end + 1) + ":00");
								
			}
		}
	}
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		CourseScheduleApp course = new CourseScheduleApp(in);
		course.setCourse(in);
		in.close();
		
	}
	
}
