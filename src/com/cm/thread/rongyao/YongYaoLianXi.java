package com.cm.thread.rongyao;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author 陈萌
 * @date 2023/7/20 18:18
 * @description
 */
public class YongYaoLianXi {


    public static void main(String[] args) {

        // 1、有效括号长度
        // int validParentheses = longestValidParentheses(")))((()())");

        // 2、在其他数都出现偶数次的数组中找到出现奇数次的数
        // int number = getNumber(new int[]{1, 2, 1, 3, 2});

        // 3、分金
        // lessMoney(new int[]{30, 10, 20});

        // 4、排序
        // int[] sortArr = sortArr(new int[]{2, 0, 1, 2, 0});

        // 9、二分查找
        // int i = findIt(5, 4, new int[]{1, 2, 4, 4, 5});

        // 最小公倍数
        int lcm = lcm(13, 4);

    }

    /**
     * 第一题
     * 括号字符串的最长有效长度，给定一个括号字符串str，返回最长的能够完全正确匹配括号字符字串的长度。
     * <p>
     * 思路：
     * 以"(" 为起点，统计左括号个数，再统计匹配的右括号个数，括号数*2
     */
    private static int longestValidParentheses(String str) {

        // 字符串校验
        if (str == null || str.length() < 2) {
            return 0;
        }

        // 括号个数
        int num = 0;
        // 左括号个数
        int sTop = 0;

        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case '(':
                    sTop++;
                    break;
                case ')':
                    if (sTop > 0) {
                        sTop--;
                        num++;
                        break;
                    }
                default: {
                    break;
                }
            }
        }
        return num * 2;
    }

    /**
     * 在其他数都出现偶数次的情况下找到出现奇数次的数
     * <p>
     * 思路
     * 此题可以采用异或的思想解决。异或满足交换律。
     * 假设有一个数组arr = {4,6,3,2,4,3,6,6,6}
     * 4⊕6⊕3⊕2⊕4⊕3⊕6⊕6⊕6 = 4⊕4⊕3⊕3⊕6⊕6⊕6⊕6⊕2
     * <p>
     * 一个数与另一个数异或两次是其本身；
     * 一个数与自身异或，结果为零；
     * 一个数与零异或，结果为其本身。
     */
    public static int getNumber(int[] arr) {
        if (arr == null) {
            return 0;
        }
        int result = 0;
        for (int mid : arr) {
            result ^= mid;
        }
        return result;
    }


    /**
     * 分金结果
     * 思路：
     * 建立小根堆。每次从堆中取出最小的两个值，计算加和，并将其添加到代价中，再将加和加入到小根堆中，直至小根堆中只剩下一个元素停止。【切割时就从上往下切】
     *
     * @param arr 数组
     * @return 钱
     */
    public static int lessMoney(int[] arr) {
        int sum = 0;
        if (arr.length == 0) {
            return sum;
        }

        // 值加入有序队列
        Queue<Integer> queue = new PriorityQueue<>((o1, o2) -> o1 - o2);
        for (int value : arr) {
            queue.add(value);
        }

        // 最小值相加 和加入队列
        while (!queue.isEmpty()) {
            Integer min1 = queue.poll();
            if (!queue.isEmpty()) {
                Integer min2 = queue.poll();
                sum += min1;
                sum += min2;
                queue.add(sum);
            }
        }
        return sum;
    }

    /**
     * 数组的 partition 调整
     */
    private static int[] sortArr(int[] arr) {
        // 校验
        if (arr == null || arr.length == 0) {
            return arr;
        }

        // 排序
        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j + 1 < arr.length - i; j++) {
                // 位置反转
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * @param n 数组的长度
     * @param v 需要查找的值
     * @param a 已知数组
     * @return 所在的位置哦。不是index，是从1开始的位置哦
     */
    public static int findIt(int n, int v, int[] a) {

        if (n == 0) {
            return 1;
        }

        // 定义左值、中值、右值
        int left = 0;
        int right = n - 1;
        int index;

        while (left <= right) {
            // 取中值
            index = (left + right) / 2;

            // v在左侧
            if (v <= a[index]) {
                if (index == 0 || a[index - 1] < v) {
                    return index + 1;
                } else {
                    right = index - 1;
                }
            } else {
                left = index + 1;
            }
        }
        return n + 1;
    }

    // 10、括号字符串的有效性
    public static boolean isValid(String s) {

        if (s == null || s.isEmpty()) {
            return false;
        }

        char[] chars = s.toCharArray();
        // 左括号个数
        int left = 0;
        for (char c : chars) {
            // 非括号
            if ('(' != c && ')' != c) {
                return false;
            }
            // 右括号 没有对应的左括号
            if (c == ')' && --left < 0) {
                return false;
            }

            // 左括号个数
            if (c == '(') {
                left++;
            }
        }
        return left == 0;
    }

    // 两数字最小公倍数
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }

        int max = Math.max(a, b);
        int min = Math.min(a, b);

        // 循环次数
        int c = 1;
        // 最小公约
        int lcm = max;

        while (lcm % min != 0) {
            c++;
            lcm = max * c;
        }
        return lcm;
    }

}
