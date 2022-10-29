package com.cm.thread.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 陈萌
 * @describe
 * @date 2022/9/5 22:20
 */
public class ConcurrentHashMapTest {

    public static void concurrentHashMapTest() {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("", "");
    }

    /**
     * ConcurrentSkipListMap 是多层链表结构
     *
     * 三层 1 50 100
     * 二层 1 10 20 ··· 100
     * 一层 1 2 3 4 5 6 ··· 50 ··· 100
     */
    public static void concurrentSkipListMapTest() {
        ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();

    }

    public static void test(){

        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        /**
         * 写的时候枷锁，将原list复制一份 新元素加在新list后面，将指向旧list的指针指向新list
         */
        copyOnWriteArrayList.add("");
        /**
         * 读的时候 从
         */
        copyOnWriteArrayList.get(0);
    }

    public static void main(String[] args) {
        concurrentHashMapTest();
    }

}
