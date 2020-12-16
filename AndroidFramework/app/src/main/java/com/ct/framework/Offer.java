package com.ct.framework;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Offer {
    public static void main(String[] args) {
        new Offer().run();
    }

    private void run() {

    }


    /**
     * 03找出数组中重复的数字。
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了
     * ，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
     */
    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums
        ) {
            if (!set.add(num))
                return num;
        }
        return -1;
    }

    /**
     * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
     * 请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {

        return false;

    }


    /**
     * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
     */
    public String replaceSpace(String s) {
        int n = s.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == ' ') {
                builder.append("%20");
            } else
                builder.append(s.charAt(i));
        }
        return builder.toString();

    }

    /**
     * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）
     */
    public int[] reversePrint(ListNode head) {
        int n = 0;
        ListNode preNode = head;
        while (preNode != null) {
            n++;
            preNode = preNode.next;
        }
        int[] arr = new int[n];
        int index = n - 1;
        preNode = head;
        while (preNode != null) {
            arr[index] = preNode.val;
            preNode = preNode.next;
            index--;
        }

        return arr;
    }


    /**
     * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
     * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
     */
    private class CQueue {
        private Stack<Integer> stack1;
        private Stack<Integer> stack2;

        public CQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        public void appendTail(int value) {
            stack1.push(value);
        }

        public int deleteHead() {

            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }

            if (stack2.isEmpty())
                return -1;
            return stack2.pop();
        }
    }


    /**
     * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。斐波那契数列的定义如下：
     * F(0) = 0,   F(1) = 1
     * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
     * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
     */
    public int fib(int n) {

        int p0 = 0;
        if (n == 0)
            return p0;
        int p1 = 1;
        if (n == 1)
            return p1;
        for (int i = 2; i <= n; i++) {
            int t0 = p0 % 1000000007 + p1 % 1000000007;
            p0 = p1 % 1000000007;
            p1 = t0 % 1000000007;
        }
        return p1;

    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
     */
    public int numWays(int n) {
        int p0 = 1;
        if (n <= 1)
            return p0;
        int p1 = 2;
        if (n == 2)
            return p1;
        for (int i = 3; i <= n; i++) {
            int t0 = p0 % 1000000007 + p1 % 1000000007;

            p0 = p1 % 1000000007;
            p1 = t0 % 1000000007;
        }
        return p1;
    }

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1
     */
    public int minArray(int[] numbers) {
        int n = numbers.length - 1;
        for (int i = 0; i < n; i++) {
            if (numbers[i] > numbers[i + 1])
                return numbers[i + 1];
        }
        return numbers[0];
    }

    /**
     * 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），
     * 每段绳子的长度记为 k[0],k[1]...k[m-1] 。请问 k[0]*k[1]*...*k[m-1] 可能的最大乘积是多少？
     * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
     */
//    public int cuttingRope(int n) {
//
//        //TODO
//
//    }

    /**
     * 请实现一个函数，输入一个整数（以二进制串形式），输出该数二进制表示中 1 的个数。例如，把 9 表示成二进制是 1001，有 2 位是 1。
     * 因此，如果输入 9，则该函数输出 2。
     */
    public int hammingWeight(int n) {
        int num = 0;
        int size = 32;
        while (size > 0) {
            System.out.println("--->" + ((n & 1) == 1));
            if ((n & 1) == 1)
                num++;
            n = n >>> 1;
            size--;
        }
        return num;
    }


    /**
     * 输入数字 n，
     * 按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，
     * 则打印出 1、2、3 一直到最大的 3 位数 999。
     */
    public int[] printNumbers(int n) {
        if (n <= 0)
            return null;

        int size = (int) Math.pow(10, n);
        int[] arr = new int[size];
        for (int i = 1; i <= size; i++) {
            arr[i - 1] = i;
        }

        return arr;
    }


    /**
     * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
     * 返回删除后的链表的头节点
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head == null)
            return null;
        if (head.val == val)
            return head.next;

        ListNode preNode = head;
        ListNode curNode = head.next;
        while (curNode != null) {
            if (curNode.val == val) {
                preNode.next = curNode.next;
                break;
            } else {
                preNode = curNode;
                curNode = curNode.next;
            }
        }
        return head;
    }

    /**
     * 给定一个二叉树，检查它是否是镜像对称的。
     */
    public boolean isSymmetric(TreeNode root) {
        if (root.left == null && root.right == null)
            return true;
        if (root.left != null && root.right != null) {
            Queue<TreeNode> stack1 = new LinkedList<>();
            Queue<TreeNode> stack2 = new LinkedList<>();
            stack1.add(root.left);
            stack2.add(root.right);
            while (!(stack1.isEmpty() && stack2.isEmpty())) {
                TreeNode node1 = stack1.poll();
                TreeNode node2 = stack2.poll();
                if ((node1 == null && node2 != null)
                        || (node2 == null && node1 != null)
                        || (node1.val != node2.val)
                )
                    return false;
                if (node1 == null && node2 == null)
                    continue;
                stack1.add(node1.left);
                stack1.add(node1.right);
                stack2.add(node2.right);
                stack2.add(node2.left);

            }
            if (stack1.size() != stack2.size())
                return false;
            return true;

        }

        return false;

    }

}

