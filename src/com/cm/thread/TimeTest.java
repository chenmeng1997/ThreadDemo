package com.cm.thread;

import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈萌
 * @date 2024/1/16 22:46
 * @description 定时任务
 */
public class TimeTest {

    private static void testTime() {

        new ThreadLocal<>()

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("时间：" + dateFormat.format(System.currentTimeMillis()));
            }
        };
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(5);
        poolExecutor.scheduleWithFixedDelay(timerTask, 1, 1, TimeUnit.SECONDS);
    }


    public static void main(String[] args) {
        testTime();
    }

}
