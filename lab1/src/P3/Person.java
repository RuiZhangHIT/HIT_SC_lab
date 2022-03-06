package P3;

import java.util.*;

public class Person {
	//成员变量存储人的名字及朋友名单
    private String name;
    private List<Person> friend;
 
    /**
     * 初始化对象
     * 
     * @param personname 该人的姓名，需为String类型的
     */
    public Person(String personname){
        name = personname;
        friend = new ArrayList<Person>();
    }
    
    /**
     * 返回该人的名字
     * 
     * @return 该人的名字
     */
    public String getname() {
    	return name;
    }
    
    /**
     * 添加朋友进入该人的朋友名单
     * 
     * @param a 需添加的朋友，需为Person类型的
     */
    public void addfriend(Person a) {
    	friend.add(a);
    }
    
    /**
     * 返回该人的朋友名单
     * 
     * @return 该人的朋友名单
     */
    public List<Person> getfriend(){
    	return friend;
    }
}
