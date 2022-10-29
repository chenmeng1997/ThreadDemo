package com.cm.thread.other;

import java.util.concurrent.*;

/**
 * @author 陈萌
 * @describe 满CyclicBarrier 中线程数，线程批量启动 限流用
 * @date 2022/8/5 23:43
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        cyclicBarrierTest();
    }

    private static void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, () -> System.out.println("满人发车"));
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await(1, TimeUnit.SECONDS);
                    // cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
