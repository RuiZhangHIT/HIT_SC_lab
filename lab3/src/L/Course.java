package L;

/**
 * A course which has a course id, a name, a teacher's name and a location. It is immutable.
 * 
 */
public class Course {
	
	private final int id;
	private final String name;
	private final String tname;
	private final String loc;
	
	// Abstraction function:
    //   AF(id, name, tname, loc) = a course's id, name, teacher's name and the class location
    // Representation invariant:
    //   id > 0
	//   name != null
	//   tname != null
	//   loc != null
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   All are String and int, so they are guaranteed immutable;
    //   parameters and return values are immutable String, int
	
	/**
	 * Create a course. Initialize the id, name, teacher's name and the class location.
	 * 
	 * @param id the id of the course
	 * @param name the name of the course
	 * @param tname the teacher's name of the course
	 * @param loc the class location of the course
	 * @throws Exception if id <= 0, or name == null, or tname == null, or loc == null
	 */
	public Course(int id, String name, String tname, String loc) throws Exception {
		if (id <= 0) {
			throw new Exception("Each course's id must be positive. The initialization fails.");
		}
		this.id = id;
		if (name == null) {
			throw new Exception("Each course must have a name. The initialization fails.");
		}
        this.name = name;
        if (tname == null) {
			throw new Exception("Each course must have a teacher's name. The initialization fails.");
		}
		this.tname = tname;
		if (loc == null) {
			throw new Exception("Each course must have a class location. The initialization fails.");
		}
		this.loc = loc;
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
	private void checkRep(){
		assert id > 0;
    	assert name != null;
    	assert tname != null;
    	assert loc != null;
    }
	
	/**
	 * Get the id of the course.
     * 
     * @return the id of the course
	 */
	public int getid() {
		checkRep();
		return id;
	}
	
	/**
     * Get the name of the course.
     * 
     * @return the name of the course
     */
    public String getname() {
    	checkRep();
    	return name;
    }
    
    /**
	 * Get the teacher's name of the course.
     * 
     * @return the teacher's name of the course
	 */
	public String getteachername() {
		checkRep();
		return tname;
	}
	
	/**
	 * Get the class location of the course.
     * 
     * @return the class loction of the course
	 */
	public String getclasslocation() {
		checkRep();
		return loc;
	}
	
	@Override
    public boolean equals(Object o) {
    	if (!(o instanceof Course))
    		return false;
    	Course c = (Course) o;
    	checkRep();
    	return c.getid() == id;
    }
	
	@Override
    public int hashCode() {
    	checkRep();
    	return id;
    }
    
    @Override
    public String toString() {
    	checkRep();
    	return "id : " + getid() + " name : " + getname() + " teacher's name : " + getteachername()
    	       + " class location : " + getclasslocation();
    }

}
