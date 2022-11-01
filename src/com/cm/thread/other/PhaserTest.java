package com.cm.thread.other;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Phaser;

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
        // 批量注册
        phaser.bulkRegister(5);
        for (int i = 0; i < 5; i++) {
            Scanner input = new Scanner(System.in);
            String perSonName = input.nextLine();
            final int nameIndex = i;
            if (!("新郎".equals(perSonName) || "新娘".equals(perSonName))) {
                perSonName = perSonName + i;
            }
            String finalPerSonName = perSonName;
            new Thread(() -> {
                PerSon p = new PerSon(finalPerSonName + nameIndex);

                // 到达现场
                p.arrive();
                // 到达并等待前进
                phaser.arriveAndAwaitAdvance();

                // 吃饭
                p.eat();
                phaser.arriveAndAwaitAdvance();

                // 离开
                p.leave();
                phaser.arriveAndAwaitAdvance();

            }).start();
        }
    }

    /**
     * 人物
     */
    static class PerSon implements Runnable {

        protected String name;

        public PerSon(String name) {
            this.name = name;
        }

        /**
         * 到达现场
         */
        public void arrive() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s, 到达现场 \n", name);
            // 到达并等待前进
            phaser.arriveAndAwaitAdvance();
        }

        /**
         * 吃完
         */
        public void eat() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s, 吃完 \n", name);
            phaser.arriveAndAwaitAdvance();
        }

        /**
         * 离开
         */
        public void leave() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s, 离开 \n", name);
            phaser.arriveAndAwaitAdvance();
        }

        /**
         * 洞房
         */
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

    /**
     * 婚姻
     */
    static class MarriagePhaser extends Phaser {
        /**
         * 在推进
         *
         * @param phase             阶段
         * @param registeredParties 注册方
         * @return boolean
         */
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
