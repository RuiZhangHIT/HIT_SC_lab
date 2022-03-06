package P2;

/**
 * A person who has a name. It is immutable.
 * 
 */
public class Person {
	
    private final String name;
    
    // Abstraction function:
    //   AF(name) = a person's name
    // Representation invariant:
    //   name != null
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   name is String, so it is guaranteed immutable;
    //   parameters and return values are immutable Strings
 
    /**
     * Create a person. Initialize the name of the person.
     * 
     * @param personname the name of the person
     * @throws Exception if personname == null
     */
    public Person(String personname) throws Exception{
    	if (personname == null)
    		throw new Exception("each person must have a name");
        name = personname;
        checkRep();
    }
    
    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
	private void checkRep(){
    	assert name != null;
    }
    
    /**
     * get the name of the person
     * 
     * @return the name of the person
     */
    public String getname() {
    	checkRep();
    	return name;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (!(o instanceof Person))
    		return false;
    	Person p = (Person) o;
    	checkRep();
    	return p.getname().equals(name);
    }
    
    @Override
    public int hashCode() {
    	checkRep();
    	return getname().length();
    }
    
    @Override
    public String toString() {
    	checkRep();
    	return "this person's name is " + getname() + "\n";
    }
}
