package com.cm.thread;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test {

    // 排成一条线的纸牌博弈问题
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[][] f = new int[arr.length][arr.length]; // 先拿
        int[][] s = new int[arr.length][arr.length]; // 后拿
        for (int j = 0; j < arr.length; j++) {
            f[j][j] = arr[j];
            for (int i = j - 1; i >= 0; i--) {
                f[i][j] = Math.max(arr[i] + s[i + 1][j], arr[j] + s[i][j - 1]);
                s[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
            }
        }
        return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
    }

    // 折纸
    public void process(int times, int curTimes, boolean down) {
        if (curTimes > times) { // 上一层已经是最下方的层数，没有下挂节点了
            return;
        }
        process(times, curTimes + 1, true);  // 根据观察，每一层的左节点都是凹
        System.out.print(down ? "down " : "up ");
        //每一层的右节点都是凸
        process(times, curTimes + 1, false);
    }

    public void paperOut(int times) {
        if (times < 1) {  // times小于1，代表没折纸
            return;
        }
        int curTimes = 1;  // 系统记录折纸的次数, 用来与times作比较用
        process(times, curTimes, true);  // 第一次默认是凹下去的

        Map<String, String> map = new HashMap<String, String>(16);

        map.put("", "");
        LinkedList<String> list = new LinkedList<String>();
        list.add("");

        CopyOnWriteArrayList<String> onWriteArrayList = new CopyOnWriteArrayList<>();
        onWriteArrayList.add("");
    }

}
