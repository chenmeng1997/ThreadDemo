package com.cm.thread.lock;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈萌
 * @describe
 * @date 2022/8/5 22:21
 */
public class LockTest {

    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) throws InterruptedException {

        /*reentrantLockTest();
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        readLock.tryLock();
        readLock.lockInterruptibly();
        readLock.unlock();*/
        //test();
        exchangerTest();
    }

    private static void reentrantLockTest() throws InterruptedException {

        ReentrantLock reentrantLock = new ReentrantLock();

        Condition condition = reentrantLock.newCondition();

        Thread thread2 = new Thread(() -> {
            try {
                long start = System.currentTimeMillis();
                System.out.println(start);
                reentrantLock.tryLock(1, TimeUnit.SECONDS);
//                Thread.sleep(200);
                boolean await = condition.await(1000, TimeUnit.MILLISECONDS);
                System.out.println("thread2 模拟代码执行 await:" + await);
                long end = System.currentTimeMillis();
                System.out.println("thread2:" + (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        });

        Thread thread1 = new Thread(() -> {
            try {
                long start = System.currentTimeMillis();
                System.out.println(start);
                reentrantLock.tryLock(1, TimeUnit.SECONDS);
                System.out.println("thread1 模拟代码执行");
                Thread.sleep(800);
                condition.signalAll();
                long end = System.currentTimeMillis();
                System.out.println("thread1:" + (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        });

/*
        thread2.join(50);
        thread1.start();
        thread2.start();
*/
        thread2.start();
        thread1.start();
    }

    static void test() {
        char[] nums = {'1', '2', '3', '4', '5'};
        char[] chars = {'A', 'B', 'C', 'D', 'E'};

        t1 = new Thread(() -> {
            for (char aChar : chars) {
                System.out.print(aChar);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");
        t2 = new Thread(() -> {
            for (char aChar : nums) {
                LockSupport.park();
                System.out.println(aChar);
                LockSupport.unpark(t1);
            }
        }, "t2");
        t1.start();
        t2.start();

    }

    public static void exchangerTest() {

        Exchanger<String> exchanger = new Exchanger<>();
        char[] a = {'A', 'B', 'C'};
        char[] n = {'1', '2', '3'};
        new Thread(() -> {
            for (char c : a) {
                System.out.print(c);
                try {
                    exchanger.exchange("T1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (char c : n) {
                try {
                    exchanger.exchange("T2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(c);
            }
        }).start();
    }

}
