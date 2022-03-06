package P2;

import java.util.*;

import P1.graph.Graph;

/**
 * A graph-based FriendshipGraph. The friendship relation is considered unidirectional.
 * It is mutable and represents friendships in a social network and can compute the distance
 * between two people in the graph.
 */
public class FriendshipGraph {
	
	private final Graph<Person> graph = Graph.empty();
	
	// Abstraction function:
    //   AF(graph) = a mutable directed graph represents friendships in a social network
    // Representation invariant:
	//   There are no edges in the graph which have the same source and target
    // Safety from rep exposure:
    //   All fields are private;
    //   final makes it impossible to change the reference;
    //   vertices(), sources() and targets() make defensive copies and return;
    //   other parameters and return values consist of immutable Person, int or void
	
	// Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
	private void checkRep(){
		for (Person p1 : graph.vertices()) {
			for (Person p2 : graph.targets(p1).keySet()) {
				assert !p1.equals(p2);
			}
		}
    }
	
	/**
	 * Add a person into the FriendshipGraph. If a person who has the same name is already existed in 
	 * the graph, the function will print the relative information and do nothing to the FriendshipGraph.
	 * 
	 * @param a person to be added into the graph
	 */
	public void addVertex(Person a) {
		for (Person p : graph.vertices()) {
			if (p.equals(a)){
				System.out.println("Each person must have a unique name.");
				checkRep();
				return ;
			}
		}
		
		graph.add(a);
		checkRep();
	}
	
	/**
	 * Make Person b a friend of Person a, if any one of these two people is not in the FriendshipGraph,
	 * the function will print the relative information and do nothing to the FriendshipGraph.
	 * 
	 * @param a a person who will have a new friend Person b
	 * @param b a person who will become a new friend of Person a
	 */
	public void addEdge(Person a, Person b) {
		if(!graph.vertices().contains(a)) {
			System.out.println(a.getname()+" is not in the graph.");
			if(!graph.vertices().contains(b)) {
				System.out.println(b.getname()+" is also not in the graph.");
			}
			checkRep();
			return ;
		}
				
		if(!graph.vertices().contains(b)) {
			System.out.println(b.getname()+" is not in the graph.");
			checkRep();
			return ;
		}
		
		if(graph.vertices().contains(a) && graph.vertices().contains(b)) {
			graph.set(a, b, 1);
		}
		checkRep();
	}
	
	/**
	 * Get the shortest distance between Person a and Person b. If any one of these two people is 
	 * not in the FriendshipGraph, the function will return -1; if there is no path connecting these
	 * two people, the function will return -1; if a and b are the same person, the function will return 0.
	 * 
	 * @param a the source vertex person
	 * @param b the target vertex person
	 * @return the shortest distance between Person a and Person b
	 */
	public int getDistance(Person a, Person b) {
		
		if(!graph.vertices().contains(a)) {
			System.out.println(a.getname()+" is not in the graph.");
			if(!graph.vertices().contains(b)) {
				System.out.println(b.getname()+" is also not in the graph.");
			}
			checkRep();
			return -1;
		}
			
		if(!graph.vertices().contains(b)) {
			System.out.println(b.getname()+" is not in the graph.");
			checkRep();
			return -1;
		}
		
		if(a == b) {
			checkRep();
			return 0;
		}
		
		Map<Person,Integer> distance = new HashMap<Person,Integer>();
		Queue<Person> q = new LinkedList<>();
		distance.put(a, 0);
		q.offer(a);
		while(!q.isEmpty()) {	
		    Person curperson = q.poll();
		    Map<Person, Integer> friends = graph.targets(curperson);
		    for(Person p : friends.keySet()) {
		        if(!distance.containsKey(p)) {
		        	q.offer(p);
		        	distance.put(p, distance.get(curperson)+1);
		        	if(b == p) {
		        		checkRep();
		        		return distance.get(b);
		        	}
		        }
		    }
		}
		
		checkRep();
		System.out.println("There is no path connecting the two people.");
		return -1;
	}
	
	/**
	 * Get all people in the FriendshipGraph, if there is nobody in the graph, the function
	 * will return an empty set.
	 * 
	 * @return the set of all people in the FriendshipGraph
	 */
	public Set<Person> getPeople() {
		Set<Person> people = new HashSet<>();
		people = graph.vertices();
		checkRep();
		return people;
	}
	
	/**
	 * Get the friends of Person a in the FriendshipGraph, if he has no friend or he is not in the graph,
	 * the function will return an empty set.
	 * 
	 * @param a a person who will be checked for his friends
	 * @return the set of friends of Person a
	 */
	public Set<Person> getFriends(Person a) {
		Set<Person> friends = new HashSet<>();
		for (Person p : graph.vertices()) {
			if (a.equals(p)) {
				friends = graph.targets(p).keySet();
			}
		}
		checkRep();
		return friends;
	}
	
	/**
	 * Main in Lab1.
	 * 
	 * @param args unused
	 * @throws Exception if rachel or ross or ben or kramer receives null as the initialization parameter
	 */
	public static void main(String[] args) throws Exception {
    	FriendshipGraph graph = new FriendshipGraph();
    	Person rachel = new Person("Rachel");
    	Person ross = new Person("Ross");
    	Person ben = new Person("Ben");
    	Person kramer = new Person("Kramer");
    	graph.addVertex(rachel);
    	graph.addVertex(ross);
    	graph.addVertex(ben);
    	graph.addVertex(kramer);
    	graph.addEdge(rachel, ross);
    	graph.addEdge(ross, rachel);
    	graph.addEdge(ross, ben);
    	graph.addEdge(ben, ross);
    	System.out.println(graph.getDistance(rachel, ross));
    	System.out.println(graph.getDistance(rachel, ben));
    	System.out.println(graph.getDistance(rachel, rachel));
    	System.out.println(graph.getDistance(rachel, kramer));
	}
	
}
