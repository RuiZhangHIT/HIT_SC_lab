package P3;

import java.util.*;

public class FriendshipGraph {
	//��Ա����people�洢�����罻����ͼ���ˣ�nameset�洢��Щ�˵�����
	protected List<Person> people = new ArrayList<Person>();//�˴�Ϊ�˲��Ը�Ϊ��protected��һ���������󣬿��ԸĻ�private
	private Set<String> nameset = new HashSet<String>();

    /**
     * ���罻����ͼ����ӽڵ㣬�����һ��������
     * 
     * @param a ������罻����ͼ���ˣ���ΪPerson����
     */
	public void addVertex(Person a){
    	//���ȶ�Person�������ж��Ƿ��ظ������ظ����˳�����
        if(nameset.contains(a.getname())){
            System.out.println("Person " + a.getname() + " already existed.");
            System.out.println("Each person must have a unique name.");
            System.exit(0);
        }
        people.add(a);
        nameset.add(a.getname());
    }

    /**
     * ���罻��������ӱߣ������һ�Զ����ѹ�ϵ
     * 
     * @param a �����ѹ�ϵ������һ���ˣ������߶ε�ʼ�㣬��ΪPerson����
     * @param b �����ѹ�ϵ������һ���ˣ������߶ε��յ㣬��ΪPerson����
     */
	public void addEdge(Person a, Person b){
		if(!people.contains(a)) {
			System.out.println(a.getname()+" is not in the graph.");
		}
		if(!people.contains(b)) {
			System.out.println(b.getname()+" is not in the graph.");
		}
		if(people.contains(a) && people.contains(b)) {
			//Ϊ������չ������ͼ���������˼���罻��ϵΪ�����
			a.addfriend(b);
		}
    }

    /**
     * �����罻����������֮����̾��룬�����������Ϊͬһ�ˣ�����Ϊ0��������֮������ϵ������Ϊ-1
     * 
     * @param a �������������һ�ˣ���ΪPerson����
     * @param b �������������һ�ˣ���ΪPerson����
     * @return ����֮����̾��룬��Ϊint����
     */
	public int getDistance(Person a, Person b){
		//��������һ�˲����罻�����У��������ʾ��Ϣ������ֵҲΪ-1
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
		
		//ͬһ���ˣ�����0
        if(a == b)
            return 0;
        //����BFS��˼������������̾��룬distance��¼a��ĳ�˵���̾��룬q������⻹�迼����Щ����a�ľ���
        Map<Person,Integer> distance = new HashMap<Person,Integer>();
        Queue<Person> q = new LinkedList<>();
        //a��a����̾���Ϊ0
        distance.put(a, 0);
        q.offer(a);
        while(!q.isEmpty()) {
        	//q�ǿ�ʱ��ȡ����һ����curperson������ȡ����������
        	Person curperson = q.poll();
        	List<Person> friend = curperson.getfriend();
        	//����curperson����������
        	for(Person p : friend) {
        		//��distance����δ��¼��a����λ���ѵ���̾��룬˵��֮ǰ�ı�����δ����˽ڵ㣬�轫�����q�У�����¼��a֮�����̾���
        		if(!distance.containsKey(p)) {
        			q.offer(p);
        			//��a֮�����̾��룬��a��curperson֮����̾����1
        			distance.put(p, distance.get(curperson)+1);
        			//����λ���Ѿ���b���򷵻ؾ��룬��ֹѭ��
        			if(b == p)
        				return distance.get(b);
        		}
        	}
        }
        //�����ѿգ�˵����������ϵ������-1
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
