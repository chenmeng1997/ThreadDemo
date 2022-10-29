package com.cm.thread.collections;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈萌
 * @describe 集合测试 synchronized 实现线程安全
 * @date 2022/9/5 21:36
 */
public abstract class VectorTest {

    /**
     * 门票
     */
    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票 编号：" + i);
        }
    }

    /**
     * 超卖
     * Vector 是线程安全的，但是 tickets.size tickets.remove 之间方法不保证原子性
     */
    public static void test1() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("销售了----" + tickets.remove(0));
                }
            }).start();
        }
    }

    /**
     * 超卖
     * Vector 是线程安全的，但是 tickets.size tickets.remove 之间方法不保证原子性
     */
    public static void test2() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (tickets) {
                    while (tickets.size() > 0) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("销售了----" + tickets.remove(0));
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

}
