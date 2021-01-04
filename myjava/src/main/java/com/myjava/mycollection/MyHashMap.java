package com.myjava.mycollection;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/1/4 12:34
 * @Description:
 */
public class MyHashMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();
        String tom = map.put("tom", "12");
        String ss = map.put("tom", "13");
        System.out.println(tom);
        System.out.println(ss);
        ConcurrentHashMap<String, Integer> cmap = new ConcurrentHashMap<String, Integer>();
        Integer put = cmap.put("", 2);
        Integer put1 = cmap.put("", 1);
        System.out.println(put);
        System.out.println(put1);
//        final int MAXIMUM_CAPACITY = 1 << 30;
//        System.out.println(MAXIMUM_CAPACITY);
    }
}
