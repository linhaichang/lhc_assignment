package cn.haichang.assignment;

import java.util.*;

/**
 * @author HaiChang
 * @date 2020/10/26
 **/
public class Test {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("小2");
        list.add("小2");
        list.add("小1");
        list.add("小4");
        Map statistics = statistics(list);
        System.out.println(statistics);
    }
    private static Map<String,Integer> statistics(List<String > list){
        HashMap<String , Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if (map.get(list.get(i))!=null){
                map.put(list.get(i),map.get(list.get(i))+1);
            }else {
                map.put(list.get(i),1);
            }
        }
        return map;
    }
}
