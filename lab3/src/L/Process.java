package L;

/**
 * A process which has a pid, a name, a shortest execvution time and a longest execvution time. It is immutable.
 * 
 */
public class Process {
	
	private final int pid;
	private final String name;
	private final int st;
	private final int lt;
	
	// Abstraction function:
    //   AF(pid, name, st, lt) = a process's pid, name, shortest execvution time and longest execvution time
    // Representation invariant:
    //   name != null
	//   pid > 0
	//   0 < st <= lt
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   All are String and int, so they are guaranteed immutable;
    //   parameters and return values are immutable String, int
	
	/**
	 * Create a process. Initialize the pid, name, shortest execvution time and longest execvution time of the process.
	 * 
	 * @param pid the pid of the process
	 * @param name the name of the process
	 * @param st the shortest execvution time of the process
	 * @param lt the longest execvution time of the process
	 * @throws Exception if pid <= 0, or name == null, or st <= 0, or lt < st
	 */
	public Process(int pid, String name, int st, int lt) throws Exception {
		if (pid <= 0) {
			throw new Exception("Each process's pid must be positive. The initialization fails.");
		}
		this.pid = pid;
		if (name == null) {
			throw new Exception("Each process must have a name. The initialization fails.");
		}
        this.name = name;
        if (st <= 0) {
			throw new Exception("Each process's shortest execvution time must be positive. The initialization fails.");
		}
		this.st = st;
		if (lt < this.st) {
			throw new Exception("Each process's longest execvution time must be longer than shortest execvution time. The initialization fails.");
		}
		this.lt = lt;
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
	private void checkRep(){
		assert pid > 0;
    	assert name != null;
    	assert st > 0;
    	assert st <= lt;
    }
	
	/**
	 * Get the pid of the process.
     * 
     * @return the pid of the process
	 */
	public int getpid() {
		checkRep();
		return pid;
	}
	
	/**
     * Get the name of the process.
     * 
     * @return the name of the process
     */
    public String getname() {
    	checkRep();
    	return name;
    }
    
    /**
	 * Get the shortest execvution time of the process.
     * 
     * @return the shortest execvution time of the process
	 */
	public int getshortesttime() {
		checkRep();
		return st;
	}
	
	/**
	 * Get the longest execvution time of the process.
     * 
     * @return the longest execvution time of the process
	 */
	public int getlongesttime() {
		checkRep();
		return lt;
	}
	
	@Override
    public boolean equals(Object o) {
    	if (!(o instanceof Process))
    		return false;
    	Process p = (Process) o;
    	checkRep();
    	return p.getpid() == pid;
    }
	
	@Override
    public int hashCode() {
    	checkRep();
    	return pid;
    }
    
    @Override
    public String toString() {
    	checkRep();
    	return "pid : " + getpid() + " name : " + getname() + " shortest execvution time : " + getshortesttime()
    	       + " longest execvution time : " + getlongesttime();
    }
    
}