package com.cm.thread;

import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.*;

/**
 * @author 陈萌
 * @describe
 * @date 2022/8/5 22:18
 */
public class ThreadTest {

    private static class Node {
        public int data;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static Node[] getTwoErrNodes(Node head) {
        Node[] errs = new Node[2];
        if (head == null) {
            return errs;
        }
        Stack<Node> stack = new Stack<>();
        Node pre = null;
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (pre != null && pre.data > head.data) {
                    errs[0] = errs[0] == null ? pre : errs[0];
                    errs[1] = head;
                }
                pre = head;
                head = head.right;
            }
        }
        return errs;
    }

    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        int c = 1;// 循环次数
        int lcm = max;// 最小公约
        while (lcm % min != 0) {
            c++;
            lcm = max * c;
        }
        return lcm;
    }

    public static void main(String[] args) {
        Node node1 = new Node(4);
        Node node2 = new Node(2);
        Node node3 = new Node(5);
        Node node4 = new Node(3);
        Node node5 = new Node(1);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;

        Node[] nodes = getTwoErrNodes(node1);

        System.out.printf("[%s, %s]", nodes[0].data, nodes[1].data);
        System.out.printf("The second node is: %d%n", nodes[1].data);
    }


}
