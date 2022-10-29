package com.cm.thread.collections;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author 陈萌
 * @describe CAS 实现
 * @date 2022/9/5 21:55
 */
public class QueueTest {

    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
    BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
    BlockingQueue<String> priorityBlockingQueue = new PriorityBlockingQueue<>();
    DelayQueue<Delayed> delayQueue = new DelayQueue<>();
    BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();
    static LinkedTransferQueue<String> linkedTransferQueue = new LinkedTransferQueue<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.offer("票 编号：" + i);
        }
    }

    public static void concurrentLinkedQueueTest() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String poll = tickets.poll();
                    if (poll == null) {
                        break;
                    }
                    System.out.println("销售了----" + poll);
                }
            }).start();
        }
    }

    public static void linkedTransferQueueTest() throws InterruptedException {
        new Thread(() -> {

            String poll = null;
            try {
                poll = linkedTransferQueue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("poll:"+ poll);
        }).start();

        linkedTransferQueue.transfer("aaaa");

    }

    public static void main(String[] args) throws InterruptedException {
        linkedTransferQueueTest();
    }

}
