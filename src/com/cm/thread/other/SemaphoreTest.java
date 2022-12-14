package com.cm.thread.other;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 陈萌
 * @describe
 * @date 2022/8/29 23:33
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        // 信号量
        Semaphore semaphore = new Semaphore(2, true);

        new Thread(() -> {
            try {
                // 获得
                semaphore.acquire(1);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 释放
            semaphore.release(1);
        }).start();

        LockSupport.park();
    }

}
