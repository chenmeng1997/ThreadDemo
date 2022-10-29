package com.cm.thread.other;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈萌
 * @describe 门闩
 * @date 2022/8/5 23:18
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
//        countDownLatchTest();
        joinTest();


    }

    /**
     * 用于保证 调用countDownLatch::countDown的线程 在调用countDownLatch.await 方法线程前执行
     *
     * @throws InterruptedException 异常
     */
    private static void countDownLatchTest() throws InterruptedException {
        Thread[] threads = new Thread[1000];
        // 门闩 等待多少个线程结束
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        for (int i = 0; i < threads.length; i++) {
            // 线程数量加一
            threads[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        long count = countDownLatch.getCount();
        // 等待门闩放开
        boolean await = countDownLatch.await(10, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName());

        System.out.println("count:" + count + "  await:" + await);
    }

    private static void joinTest() throws InterruptedException {
        Thread[] threads = new Thread[1000];

        for (int i = 0; i < threads.length; i++) {
            // 线程数量加一
            threads[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
            threads[i].start();
        }
        System.out.println(Thread.currentThread().getName());

    }

}
