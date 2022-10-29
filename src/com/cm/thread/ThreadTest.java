package com.cm.thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈萌
 * @describe
 * @date 2022/8/5 22:18
 */
public class ThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 创建线程1
        Thread thread1 = new Thread(() -> {
            System.out.println("创建线程1" + Thread.currentThread().getName());
        });
        thread1.start();

        // 创建线程2
        Runnable runnable = () -> System.out.println("创建线程2" + Thread.currentThread().getName());
        Thread thread2 = new Thread(runnable);
        thread2.start();

        // 创建线程3
        Callable<String> callable = () -> "创建线程3" + System.currentTimeMillis();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread3 = new Thread(futureTask);
        thread3.start();
        String s1 = futureTask.get(10, TimeUnit.SECONDS);
        System.out.println(s1);
        // 创建线程4
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(() -> System.out.println("创建线程4"));

        Future<String> futureTask2 = singleThreadExecutor.submit(callable);
        String s = futureTask2.get();
        System.out.println(s);

        singleThreadExecutor.shutdownNow();

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("lisi");
        String name = threadLocal.get();
        threadLocal.remove();
    }

    public static void threadPoolExecutorTest() {
        new ThreadPoolExecutor(
                2,
                10,
                2000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

}
