package com.ct.framework;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Offer {
    public static void main(String[] args) {
        new Offer().run();
    }

    private void run() {
        int[][] arr = new int[][]{
                {1, 2, 3, 4}, {5, 6, 7, 8}, {1, 2, 3, 4}, {5, 6, 7, 8}
        };
        System.out.println("遍历结果:" + Arrays.toString(spiralOrder(arr)));
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
        if (root == null)
            return true;
        return compareNode(root.left, root.right);

    }

    private boolean compareNode(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null)
            return true;
        if (node1 == null || node2 == null || node1.val != node2.val)
            return false;

        return compareNode(node1.left, node2.right) && compareNode(node1.right, node2.left);
    }


    /**
     * 请完成一个函数，输入一个二叉树，该函数输出它的镜像。
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null)
            return null;

        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null)
                stack.add(node.left);
            if (node.right != null)
                stack.add(node.right);
        }
        return root;

    }

    /**
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
     */
    public int[] spiralOrder(int[][] matrix) {

        if (matrix.length < 1)
            return new int[0];

        int xIndex = 0;
        int yIndex = 0;
        int xSize = matrix[0].length;
        int ySize = matrix.length;
        int size = xSize * ySize;
        int[] arr = new int[size];
        int index = 0;

        int xStart = 0;
        int yStart = 0;
        while (index < size) {
            while (index < size && xIndex < xSize - xStart) {
                arr[index] = matrix[yIndex][xIndex];
                index++;
                xIndex++;
            }
            xIndex--;
            yIndex++;
            while (index < size && yIndex < ySize - yStart) {
                arr[index] = matrix[yIndex][xIndex];
                index++;
                yIndex++;
            }
            yIndex--;
            xIndex--;
            while (index < size && xIndex >= xStart) {
                arr[index] = matrix[yIndex][xIndex];
                index++;
                xIndex--;
            }
            xIndex++;
            yIndex--;
            xStart++;
            while (index < size && yIndex > yStart) {
                arr[index] = matrix[yIndex][xIndex];
                index++;
                yIndex--;
            }
            yIndex++;
            xIndex++;
            yStart++;
        }


        return arr;
    }


    /**
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，
     * 调用 min、push 及 pop 的时间复杂度都是 O(1)。
     */
    private class MinStack {


        private Stack<Integer> stackA, stackB;

        /**
         * initialize your data structure here.
         */
        public MinStack() {
            stackA = new Stack<>();
            stackB = new Stack<>();
        }

        public void push(int x) {
            stackA.add(x);
            if (stackB.empty() || x <= stackB.peek())
                stackB.add(x);
        }

        public void pop() {
            if (stackA.pop() == stackB.peek())
                stackB.pop();
        }

        public int top() {
            return stackA.peek();
        }

        public int min() {
            return stackB.peek();
        }
    }

    /**
     * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
     * <p>
     * 广度优先遍历 BFS 队列
     */
    public int[] levelOrder(TreeNode root) {
        if (root == null)
            return new int[0];

        List<Integer> mList = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            mList.add(node.val);
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }

        int size = mList.size();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = mList.get(i);
        }
        return arr;

    }


    /**
     * 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> mList = new ArrayList<>();
        if (root == null)
            return mList;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int shouldPollNum = 0;
        while (!queue.isEmpty()) {
            shouldPollNum = queue.size();
            List<Integer> list = new ArrayList<>();
            while (shouldPollNum > 0) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
                shouldPollNum--;
            }
            mList.add(list);

        }
        return mList;
    }


    /**
     * 请实现一个函数按照之字形顺序打印二叉树，
     * 即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，
     * 第三行再按照从左到右的顺序打印，其他行以此类推。
     */
    public List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> mList = new ArrayList<>();
        if (root == null)
            return mList;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean dir = false; //false 从右到左 true 从左到右
        int shouldPollNum;
        while (!queue.isEmpty()) {
            shouldPollNum = queue.size();
            List<Integer> list = new ArrayList<>();
            while (shouldPollNum > 0) {
                //判断读取顺序
                //
                TreeNode node = dir ? queue.pollLast() : queue.pollFirst();
                list.add(node.val);
                //保存新的数据
                if (dir) {
                    //当前数据是从右到左
                    if (node.right != null)
                        queue.addFirst(node.right);
                    if (node.left != null)
                        queue.addFirst(node.left);
                } else {
                    //当前数据是从左到右
                    if (node.left != null)
                        queue.add(node.left);
                    if (node.right != null)
                        queue.add(node.right);
                }
                shouldPollNum--;

            }
            mList.add(list);
            dir = !dir;

        }
        return mList;
    }

    /**
     * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。
     * 例如输入字符串"I am a student. "，则输出"student. a am I"。
     */
    public String reverseWords(String s) {
        if (s == null || "".equals(s))
            return "";

        int size = s.length();
        int preIndex = size - 1;
        StringBuilder builder = new StringBuilder();
        for (int i = size - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                if (preIndex == i) {
                    preIndex--;
                    continue;
                }
                if (builder.length() != 0)
                    builder.append(' ');
                builder.append(s.substring(i + 1, preIndex + 1));

                preIndex = i;
                preIndex--;
            }

        }

        if (preIndex > 0) {
            if (builder.length() != 0)
                builder.append(' ');
            builder.append(s.substring(0, preIndex + 1));
        }

        return builder.toString();
    }

    /**
     * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。
     * 比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
     */
    public String reverseLeftWords(String s, int n) {
        if (s == null || "".equals(s) || s.length() <= n || n == 0)
            return s;
        StringBuilder builder = new StringBuilder();
        int size = s.length();
        builder.append(s.substring(n, size));
        builder.append(s.substring(0, n));

        return builder.toString();


    }

    /**
     * 给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
     */

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0)
            return nums;

        int size = nums.length;
        int n = size - k + 1;
        int[] result = new int[n];
        int preIndex = -1;

        for (int i = 0; i < n; i++) {
            int maxNum;
            if (preIndex < i) {
                maxNum = nums[i];
                preIndex = i;
                for (int j = i + 1; j < i + k; j++) {
                    if (nums[j] >= maxNum) {
                        maxNum = nums[j];
                        preIndex = j;
                    }
                }
            } else {
                maxNum = nums[preIndex];
                if (nums[i + k - 1] >= maxNum) {
                    maxNum = nums[i + k - 1];
                    preIndex = i + k - 1;
                }
            }


            result[i] = maxNum;

        }


        return result;

    }

    /**
     * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，
     * 而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
     */
    public boolean isStraight(int[] nums) {



    }
}

