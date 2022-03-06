package P2;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for FriendshipGraph.
 */
class FriendshipGraphTest {
	
	/*
     * Testing FriendshipGraph...
     */
	
	// Testing strategy:
	//   addVertex():
	//      a person already exists in the graph, not in the graph
	//   addEdge():
	//      Person a already exists in the graph, not in the graph
	//      Person b already exists in the graph, not in the graph
	//   getDistance():
	//      Person a and Person b are the same, different
	//      there is a path connecting a and b, no path connecting a and b
    //      Person a already exists in the graph, not in the graph
    //      Person b already exists in the graph, not in the graph
	//   getPeople():
	//      the graph is empty, not empty
	//   getFriends():
	//      Person a has friends, no friend
	//      Person a already exists in the graph, not in the graph

	/**
	 * addVertexTest in Lab1
	 * @throws Exception if a receives null as the initialization parameter
	 */
	@Test
    public void addVertexTest() throws Exception{
        FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        graph.addVertex(a);
        Set<Person> people = new HashSet<>();
        people.add(a);
        assertEquals(people, graph.getPeople());
    }
	
	/**
	 * addEdgeTest in Lab1
	 * @throws Exception if a or b receives null as the initialization parameter
	 */
	@Test
	public void addEdgeTest() throws Exception{
        FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        graph.addVertex(a);
        Person b = new Person("B");
        graph.addVertex(b);
        graph.addEdge(a,  b);
        Set<Person> people = new HashSet<>();
        people.add(a);
        people.add(b);
        assertEquals(people, graph.getPeople());
        Set<Person> friends = new HashSet<>();
        friends.add(b);
        assertEquals(friends, graph.getFriends(a));
    }

    /**
     * getDistanceTest in Lab1
     * @throws Exception if a or b or c or d or e or f or g or h or i or j receives null as 
     *                   the initialization parameter
     */
	@Test
    public void getDistanceTest() throws Exception{
		
        FriendshipGraph graph = new FriendshipGraph();

        Person a = new Person("A");
        Person b = new Person("B");
        Person c = new Person("C");
        Person d = new Person("D");
        Person e = new Person("E");
        Person f = new Person("F");
        Person g = new Person("G");
        Person h = new Person("H");
        Person i = new Person("I");
        Person j = new Person("J");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        graph.addVertex(j);

        graph.addEdge(a, b);
        graph.addEdge(b, a);
        graph.addEdge(a, d);
        graph.addEdge(d, a);
        graph.addEdge(b, d);
        graph.addEdge(d, b);
        graph.addEdge(c, d);
        graph.addEdge(d, c);
        graph.addEdge(d, e);
        graph.addEdge(e, d);
        graph.addEdge(c, f);
        graph.addEdge(f, c);
        graph.addEdge(e, g);
        graph.addEdge(g, e);
        graph.addEdge(f, g);
        graph.addEdge(g, f);
        graph.addEdge(h, i);
        graph.addEdge(i, h);
        graph.addEdge(i, j);
        graph.addEdge(j, i);

        assertEquals(2, graph.getDistance(a, e));
        assertEquals(1, graph.getDistance(a, d));
        assertEquals(3, graph.getDistance(a, g));
        assertEquals(3, graph.getDistance(b, f));
        assertEquals(2, graph.getDistance(d, f));
        assertEquals(2, graph.getDistance(h, j));
        assertEquals(0, graph.getDistance(i ,i));
        assertEquals(-1, graph.getDistance(d, j));
        assertEquals(-1, graph.getDistance(c, i));
        assertEquals(-1, graph.getDistance(f, h));
    }
	
	//Cover:a person already exists in the graph
	@Test
    public void testaddVertexAlreadyExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        graph.addVertex(a);
        graph.addVertex(a);
	}
	
	//Cover:a person not in the graph
	@Test
	public void testaddVertexNotExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        graph.addVertex(a);
        Set<Person> people = new HashSet<>();
        people.add(a);
        assertEquals(people, graph.getPeople());
	}
	
	//Cover:Person a in the graph, Person b not in the graph
	@Test
	public void testaddEdgeBNotExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
	    Person a = new Person("A");
	    Person b = new Person("B");
	    graph.addVertex(a);
	    graph.addEdge(a, b);
	    Set<Person> people = new HashSet<>();
	    people.add(a);
	    assertEquals(people, graph.getPeople());
	    assertEquals(Collections.emptySet(), graph.getFriends(a));
	}
	
	//Cover:Person a not in the graph, Person b in the graph
	@Test
	public void testaddEdgeANotExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		Person b = new Person("B");
		graph.addVertex(b);
		graph.addEdge(a, b);
		Set<Person> people = new HashSet<>();
		people.add(b);
		assertEquals(people, graph.getPeople());
		assertEquals(Collections.emptySet(), graph.getFriends(a));
	}
	
	//Cover:Person a in the graph, Person b in the graph
	@Test
	public void testaddEdgeBothExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		Person b = new Person("B");
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addEdge(a, b);
		Set<Person> people = new HashSet<>();
		people.add(b);
		people.add(a);
		Set<Person> friends = new HashSet<>();
        friends.add(b);
        assertEquals(friends, graph.getFriends(a));
		assertEquals(people, graph.getPeople());
	}
	
	//Cover:Person a and Person b are different, there is a path connecting a and b, a and b in the graph
	@Test
	public void testgetDistanceBothExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		Person b = new Person("B");
		Person c = new Person("C");
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addEdge(a, b);
		graph.addEdge(b, c);
	    assertEquals(2, graph.getDistance(a, c));
	}
	
	//Cover:Person a and Person b are different, there is no path connecting a and b, a and b in the graph
	@Test
	public void testgetDistanceNoPath() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		Person b = new Person("B");
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addEdge(a, b);
		assertEquals(-1, graph.getDistance(b, a));
	}
	
	//Cover:Person a and Person b are the same, there is no path connecting a and b, a and b in the graph
	@Test
	public void testgetDistanceABSame() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		graph.addVertex(a);
		assertEquals(0, graph.getDistance(a, a));
	}
	
	//Cover:Person a and Person b are different, there is no path connecting a and b, a not in the graph, b in the graph
	@Test
	public void testgetDistanceANotExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		Person b = new Person("B");
		graph.addVertex(b);
		assertEquals(-1, graph.getDistance(a, b));
	}
	
	//Cover:Person a and Person b are different, there is no path connecting a and b, a in the graph, b not in the graph
	@Test
	public void testgetDistanceBNotExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		Person b = new Person("B");
		graph.addVertex(a);
		assertEquals(-1, graph.getDistance(a, b));
	}
	
	//Cover:the graph is empty
	@Test
	public void testgetPeopleEmptyGraph() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		assertEquals(Collections.emptySet(), graph.getPeople());
	}
	
	//Cover:the graph is not empty
	@Test
	public void testgetPeopleNotEmptyGraph() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        graph.addVertex(a);
        Set<Person> people = new HashSet<>();
        people.add(a);
        assertEquals(people, graph.getPeople());
	}
	
	//Cover:Person a is not in the graph
	@Test
	public void testgetFriendsNotExist() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
	    Person a = new Person("A");
	    assertEquals(Collections.emptySet(), graph.getFriends(a));
	}
	
	//Cover:Person a is in the graph and has no friend
	@Test
	public void testgetFriendsNoFriend() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		graph.addVertex(a);
		assertEquals(Collections.emptySet(), graph.getFriends(a));
	}
	
	//Cover:Person a is in the graph and has friends
	@Test
	public void testgetFriendsHasFriends() throws Exception{
        FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        graph.addVertex(a);
        Person b = new Person("B");
        graph.addVertex(b);
        graph.addEdge(a,  b);
        Set<Person> friends = new HashSet<>();
        friends.add(b);
        assertEquals(friends, graph.getFriends(a));
    }
	
}
