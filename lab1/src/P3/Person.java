package P3;

import java.util.*;

public class Person {
	//��Ա�����洢�˵����ּ���������
    private String name;
    private List<Person> friend;
 
    /**
     * ��ʼ������
     * 
     * @param personname ���˵���������ΪString���͵�
     */
    public Person(String personname){
        name = personname;
        friend = new ArrayList<Person>();
    }
    
    /**
     * ���ظ��˵�����
     * 
     * @return ���˵�����
     */
    public String getname() {
    	return name;
    }
    
    /**
     * ������ѽ�����˵���������
     * 
     * @param a ����ӵ����ѣ���ΪPerson���͵�
     */
    public void addfriend(Person a) {
    	friend.add(a);
    }
    
    /**
     * ���ظ��˵���������
     * 
     * @return ���˵���������
     */
    public List<Person> getfriend(){
    	return friend;
    }
}
