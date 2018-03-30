package com.example.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author VanceKing
 * @since 2018/3/23.
 */

public class MapSample {
    public static void main(String[] args) {
        testConcurrentModificationException2();

    }

    private static void testConcurrentModificationException2() {
        Map<Integer, String> mapObject = new HashMap<>();
        mapObject.put(0, "aaa");
        mapObject.put(1, "bbb");
        mapObject.put(2, "ccc");
        Iterator<Map.Entry<Integer, String>> iterator = mapObject.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
//            mapObject.put(3, "ddd");//会报异常。使用 ConcurrentHashMap 不会。
            if (entry.getKey() == 1) {//如果key == 1,移除元素并不会报异常
                iterator.remove();
            }
        }
        System.out.println("size: "+mapObject.size());
    }

    //fail-fast 机制
    private static void testConcurrentModificationException1() {
        Map<Integer, String> mapObject = new HashMap<>();
        mapObject.put(0, "aaa");
        mapObject.put(1, "bbb");
        mapObject.put(2, "ccc");
        for (Map.Entry<Integer, String> entry : mapObject.entrySet()) {
            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
            mapObject.put(3, "ddd");
        }
    }

    /*
    由1和2可知Map中最多一个 null key。由3和4可知允许多个null value。
     */
    private static void test1() {
        Map<String, String> mapObject = new HashMap<>();
        mapObject.put("0", "aaa");
        mapObject.put("1", "bbb");
        mapObject.put(null, null);//1
        printMap(mapObject);
        System.out.println("=============================");
        mapObject.put(null, "ccc");//2
        mapObject.put("2", null);//3
        mapObject.put("3", null);//4
        printMap(mapObject);
    }

    private static void printMap(Map<?, ?> map) {
        Set<? extends Map.Entry<?, ?>> entries = map.entrySet();
        for (Map.Entry<?, ?> entry : entries) {
            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
        }
    }
}
