package P3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FriendshipGraphTest {
	
	/**
	 * 测试addVertex
	 */
	@Test
    public void addVertexTest(){
        FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        graph.addVertex(a);
        assertEquals(a, graph.people.get(0));
    }
	
	/**
	 * 测试addEdge
	 */
	@Test
	public void addEdgeTest(){
        FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        graph.addVertex(a);
        Person b = new Person("B");
        graph.addVertex(b);
        
        graph.addEdge(a,  b);
        assertEquals(b, a.getfriend().get(0));
        graph.addEdge(b,  a);
        assertEquals(a, b.getfriend().get(0));
    }

    /**
     * 测试getDistance
     */
	@Test
    public void getDistanceTest(){//复杂图测试，关系图在报告中已提供
		
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

}
