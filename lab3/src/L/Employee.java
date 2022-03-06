package L;

/**
 * An employee who has a name, a post and a phone number. It is immutable.
 * 
 */
public class Employee {
	
	private final String name;
	private final String post;
	private final String phone;
	
	// Abstraction function:
    //   AF(name, post, phone) = an employee's name, post and phone number
    // Representation invariant:
    //   name.matches("[A-Z][a-z]+[A-Z][a-z]+")
	//   post.matches("[A-Z][A-Za-z\\s]+")
	//   phone.matches("1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{4}\\d{4}") 
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   All are Strings, so they are guaranteed immutable;
    //   parameters and return values are immutable Strings
	
	/**
	 * Create an employee. Initialize the name, post and phone number of the employee.
	 * 
	 * @param name the name of the employee
	 * @param post the post of the employee
	 * @param phone the phone number of the employee
	 * @throws Exception if !name.matches("[A-Z][a-z]+[A-Z][a-z]+")
	 *                      !post.matches("[A-Z][A-Za-z\\s]+")
	 *                      !phone.matches("1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{4}\\d{4}") 
	 */
	public Employee(String name, String post, String phone) throws Exception {
		if (!name.matches("[A-Z][a-z]+[A-Z][a-z]+")) {
			throw new Exception("Illegal employee name. The initialization fails.");
		}
        this.name = name;
        if (!post.matches("[A-Z][A-Za-z\\s]+")) {
			throw new Exception("Illegal employee post. The initialization fails.");
		}
		this.post = post;
		if (!phone.matches("1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{4}\\d{4}")) {
			throw new Exception("Illegal employee phone number. The initialization fails.");
		}
		this.phone = phone;
		checkRep();
	}
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
	private void checkRep(){
    	assert name.matches("[A-Z][a-z]+[A-Z][a-z]+)");
    	assert post.matches("[A-Z][A-Za-z\\s]+");
    	assert phone.matches("1(?:3\\\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\\\d|9\\\\d)\\\\d{4}\\\\d{4}");
    }
	
	/**
     * Get the name of the employee.
     * 
     * @return the name of the employee
     */
    public String getname() {
    	checkRep();
    	return name;
    }
    
    /**
     * Get the post of the employee.
     * 
     * @return the post of the employee
     */
    public String getpost() {
    	checkRep();
    	return post;
    }
    
    /**
     * Get the phone number of the employee.
     * 
     * @return the phone number of the employee
     */
    public String getphone() {
    	checkRep();
    	return phone;
    }
	
	@Override
    public boolean equals(Object o) {
    	if (!(o instanceof Employee))
    		return false;
    	Employee e = (Employee) o;
    	checkRep();
    	return e.getname().equals(name) && e.getpost().equals(post) && e.getphone().equals(phone);
    }
	
	@Override
    public int hashCode() {
    	checkRep();
    	return name.length() + post.length() + phone.length();
    }
    
    @Override
    public String toString() {
    	checkRep();
    	return "name : " + getname() + " post : " + getpost() + " phone : " + getphone();
    }
    
}
