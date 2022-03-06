package P3;

import java.util.*;

public class FriendshipGraph {
	//成员变量people存储加入社交网络图的人，nameset存储这些人的名字
	protected List<Person> people = new ArrayList<Person>();//此处为了测试改为了protected，一旦测试无误，可以改回private
	private Set<String> nameset = new HashSet<String>();

    /**
     * 在社交网络图中添加节点，即添加一个个的人
     * 
     * @param a 需加入社交网络图的人，需为Person类型
     */
	public void addVertex(Person a){
    	//首先对Person的姓名判断是否重复，若重复需退出程序
        if(nameset.contains(a.getname())){
            System.out.println("Person " + a.getname() + " already existed.");
            System.out.println("Each person must have a unique name.");
            System.exit(0);
        }
        people.add(a);
        nameset.add(a.getname());
    }

    /**
     * 在社交网络中添加边，即添加一对对朋友关系
     * 
     * @param a 有朋友关系的其中一个人，有向线段的始点，需为Person类型
     * @param b 有朋友关系的另外一个人，有向线段的终点，需为Person类型
     */
	public void addEdge(Person a, Person b){
		if(!people.contains(a)) {
			System.out.println(a.getname()+" is not in the graph.");
		}
		if(!people.contains(b)) {
			System.out.println(b.getname()+" is not in the graph.");
		}
		if(people.contains(a) && people.contains(b)) {
			//为了能拓展到有向图，假设两人间的社交关系为单向的
			a.addfriend(b);
		}
    }

    /**
     * 计算社交网络中两人之间最短距离，若输入的两人为同一人，返回为0；若两人之间无联系，返回为-1
     * 
     * @param a 需计算距离的其中一人，需为Person类型
     * @param b 需计算距离的另外一人，需为Person类型
     * @return 两人之间最短距离，需为int类型
     */
	public int getDistance(Person a, Person b){
		//若有任意一人不在社交网络中，会输出提示信息，返回值也为-1
		if(!people.contains(a)) {
			System.out.println(a.getname()+" is not in the graph.");
			if(!people.contains(b)) {
				System.out.println(b.getname()+" is not in the graph.");
			}
			return -1;
		}
		
		if(!people.contains(b)) {
			System.out.println(b.getname()+" is not in the graph.");
			return -1;
		}
		
		//同一个人，返回0
        if(a == b)
            return 0;
        //运用BFS的思想求解两点间最短距离，distance记录a到某人的最短距离，q辅助求解还需考虑哪些人与a的距离
        Map<Person,Integer> distance = new HashMap<Person,Integer>();
        Queue<Person> q = new LinkedList<>();
        //a到a的最短距离为0
        distance.put(a, 0);
        q.offer(a);
        while(!q.isEmpty()) {
        	//q非空时，取出第一个人curperson，并获取其朋友名单
        	Person curperson = q.poll();
        	List<Person> friend = curperson.getfriend();
        	//遍历curperson的朋友名单
        	for(Person p : friend) {
        		//若distance里尚未记录过a到这位朋友的最短距离，说明之前的遍历尚未到达此节点，需将其加入q中，并记录与a之间的最短距离
        		if(!distance.containsKey(p)) {
        			q.offer(p);
        			//与a之间的最短距离，即a与curperson之间最短距离加1
        			distance.put(p, distance.get(curperson)+1);
        			//若这位朋友就是b，则返回距离，终止循环
        			if(b == p)
        				return distance.get(b);
        		}
        	}
        }
        //队列已空，说明两人无联系，返回-1
        return -1;
    }
    
    public static void main(String[] args) {
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
