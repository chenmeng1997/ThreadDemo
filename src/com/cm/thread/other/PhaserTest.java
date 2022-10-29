package com.cm.thread.other;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

/**
 * @author 陈萌
 * @describe
 * @date 2022/8/7 11:22
 */
public class PhaserTest {

    static Random r = new Random();
    static MarriagePhaser phaser = new MarriagePhaser();

    static void milliSleep(int milli) {
        try {
            Thread.sleep(milli);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        phaser.bulkRegister(5);
        for (int i = 0; i < 5; i++) {
            final int nameIndex = i;
            new Thread(() -> {
                PerSon p = new PerSon("PerSon" + nameIndex);

                p.arrive();
                phaser.arriveAndAwaitAdvance();

                p.eat();
                phaser.arriveAndAwaitAdvance();

                p.leave();
                phaser.arriveAndAwaitAdvance();

            }).start();
        }
    }

    static class PerSon implements Runnable {

        String name;

        public PerSon(String name) {
            this.name = name;
        }

        public void arrive() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s, 到达现场 \n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s, 吃完 \n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s, 离开 \n", name);
            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if ("新郎".equals(name) || "新娘".equals(name)) {
                milliSleep(r.nextInt(1000));
                System.out.printf("%s, 洞房！ \n", name);
                phaser.arriveAndAwaitAdvance();
            } else {
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }

    static class MarriagePhaser extends Phaser {
        protected boolean onAdvance(int phase, int registeredParties) {

            switch (phase) {
                case 0: {
                    System.out.println("所有人到齐 " + registeredParties);
                    System.out.println();
                    return false;
                }
                case 1: {
                    System.out.println("所有人吃完了 " + registeredParties);
                    System.out.println();
                    return false;
                }
                case 2: {
                    System.out.println("所有人离开了 " + registeredParties);
                    System.out.println();
                    return false;
                }
                case 3: {
                    System.out.println("婚礼结束 " + registeredParties);
                    return true;
                }
                default: {
                    return true;
                }
            }
        }
    }

}
