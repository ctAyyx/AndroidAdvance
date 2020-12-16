package com.ct.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class Demo {

    public static void main(String[] args) {

        //doArray();
        //doLinkedList();
        // doSort();
        //doString();

        doTree();
        int a = 1444;
        System.out.println("&运算:" + (a & 11) + "----" + (a % 12));
//        System.out.println("&运算:" + (a | 4 & 10));
//        System.out.println("&运算:" + (a | 4 & 10));
        System.out.println("^运算:" + (a ^ 3));

//        List<List<Integer>> mList = generate(0);
//        System.out.println("结果:" + mList);


    }

    private static void doTree() {
        TreeNode node1 = new TreeNode(9);
        TreeNode node2 = new TreeNode(7, null, node1);
        TreeNode node3 = new TreeNode(6);
        TreeNode node7 = new TreeNode(3, node3, node2);
        TreeNode node4 = new TreeNode(8);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(4, node4, null);
        TreeNode node8 = new TreeNode(2, node6, node5);
        TreeNode node9 = new TreeNode(1, node8, node7);


        System.out.println("获取的前序遍历:" + Arrays.toString(preorderTraversal(node9, 0).toArray()));
        System.out.println("获取的中序遍历:" + Arrays.toString(preorderTraversal(node9, 1).toArray()));
        System.out.println("获取的后序遍历:" + Arrays.toString(preorderTraversal(node9, 2).toArray()));
        System.out.println("获取的深度优先遍历:" + Arrays.toString(preorderTraversal(node9, 3).toArray()));
        System.out.println("获取的广度优先遍历:" + Arrays.toString(preorderTraversal(node9, 4).toArray()));
    }

    private static void doArray() {
        int[] arr = new int[]{0, 2, 3};

        //System.out.printf("缺失的数据:" + missingNumber2(arr));

        //arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        //System.out.println("排列数组:" + Arrays.toString(exchange(arr)));

//        arr = new int[]{1, 11, 5, 156, 13, 34, 654, 36, 58};
//        insertSort(arr);
//        System.out.println("插入排序数组:" + Arrays.toString(arr));

        //arr = new int[]{1, 1, 2, 2, 3, 4, 5, 5, 5};
        // System.out.println("删除排序数组重复项:" + removeDuplicates(arr) + "-->" + Arrays.toString(arr));

//        arr = new int[]{1, 2, 3, 1, 2, 3, 2, 3, 3};
//        System.out.println("出现的次数是否唯一:" + uniqueOccurrences(arr));

        arr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        System.out.println("是否是山峰数组:" + validMountainArray(arr));

    }


    private static void doLinkedList() {

        ListNode node1 = new ListNode(4);
        ListNode node2 = new ListNode(1);
        ListNode node3 = new ListNode(8);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
        //node4.next = node5;

        //System.out.println(printNode(reverseList(node1)));

        //System.out.println("中间值:" + findMidValue(node1).val);


        //node5.next = node2;
        //System.out.println("链表是否成环:" + hasCycle(node1));

        System.out.println("获取相交节点:" + getIntersectionNode2(node1, node5));

        //System.out.println("获取倒数第K个节点:" + getKthFromEnd(node1, 3).val);

        //System.out.println(printNode(insertionSortList(node1)));


    }


    private static void doSort() {
        int[] arr = new int[]{2, 5};
        //System.out.println("获取目标值:" + search(arr, 5));


        //System.out.println("是否是有效字符串:" + isValid("{}"));

        arr = new int[]{4, 1, 2, 1, 2};
        System.out.println("数组只出现一次的数:" + singleNumber(arr));
    }


    private static void doString() {
        //System.out.println("二进制相加:" + addBinary("1010", "111"));
        System.out.println("翻转字符串:" + reverseWords("Let's take LeetCode contest"));
    }

    public static int missingNumber(int[] nums) {

        int size = nums.length;

        int start = 0;
        int end = size;

        int total = ((start + end) * (size + 1)) / 2;

        int preTotal = 0;
        for (int num : nums) preTotal += num;

        return total - preTotal;

    }


    public static int missingNumber2(int[] nums) {
        int i = 0;
        int j = nums.length - 1;

        while (i <= j) {
            int m = (i + j) / 2;

            if (nums[m] == m)
                i = m + 1;
            else
                j = m - 1;

        }
        return i;

    }


    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分
     */

    public static int[] exchange(int[] nums) {

        if (nums.length < 1)
            return nums;

        int low = 0;
        int high = nums.length - 1;

        while (low < high) {

            if (nums[low] % 2 != 0)
                low++;
            else {
                if (nums[high] % 2 != 0) {
                    int temp = nums[low];
                    nums[low] = nums[high];
                    nums[high] = temp;
                    low++;
                }
                high--;

            }
        }

        return nums;

    }

    public static void insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int preIndex = i - 1;
            int curValue = nums[i];

            while (preIndex >= 0 && (nums[preIndex] > curValue)) {
                nums[preIndex + 1] = nums[preIndex];
                preIndex--;
            }
            nums[preIndex + 1] = curValue;
        }

    }


    /**
     * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成
     */
    public static int removeDuplicates(int[] nums) {

        if (nums.length < 1)
            return nums.length;
        int curIndex = 0;
        int preIndex = 1;
        int size = nums.length;
        int newSize = 0;

        while (preIndex < size) {
            if (nums[curIndex] != nums[preIndex]) {
                newSize++;
                nums[++curIndex] = nums[preIndex];
            }


            preIndex++;
        }

        return ++newSize;

    }


    public static String printNode(ListNode node) {
        StringBuilder builder = new StringBuilder();
        while (node != null) {
            builder.append(node.val + "-->");
            node = node.next;
        }

        return builder.toString();
    }

    /**
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode preNode = head;
        ListNode curNode = head.next;
        preNode.next = null;

        while (curNode != null) {
            ListNode tempNode = curNode.next;
            curNode.next = preNode;
            preNode = curNode;
            curNode = tempNode;
        }

        return preNode;

    }

    /**
     * 如何在一次遍历中找到单个链表的中值?
     */
    public static ListNode findMidValue(ListNode head) {

        ListNode lowNode = head;
        ListNode highNode = head;

        while (highNode != null) {
            ListNode next = highNode.next;
            if (next != null) {
                lowNode = lowNode.next;
                highNode = next.next;
            } else
                break;

        }
        return lowNode;

    }


    /**
     * 给定一个链表，判断链表中是否有环。
     * <p>
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * <p>
     * 如果链表中存在环，则返回 true 。 否则，返回 false 。
     */
    public static boolean hasCycle(ListNode head) {

        ListNode lowNode = head;

        ListNode highNode = head;

        while (highNode != null) {
            ListNode next = highNode.next;

            if (next != null) {
                lowNode = lowNode.next;
                highNode = next.next;
            } else
                return false;

            if (highNode != null && lowNode.val == highNode.val)
                return true;

        }

        return false;


    }


    /**
     * 输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        ListNode preNode = new ListNode(0);
        ListNode head = preNode;

        while (true) {
            if (l1.val <= l2.val) {
                preNode.next = l1;
                preNode = preNode.next;
                if (l1.next == null) {
                    preNode.next = l2;
                    break;
                } else
                    l1 = l1.next;
            } else {
                preNode.next = l2;
                preNode = preNode.next;
                if (l2.next == null) {
                    preNode.next = l1;
                    break;
                } else
                    l2 = l2.next;
            }


        }

        return head.next;

    }


    /**
     * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开始，它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。
     * https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/
     */
    public static ListNode getKthFromEnd(ListNode head, int k) {

        ListNode lowNode = head;
        ListNode high = head;
        for (int i = 0; i < k; i++) {

            high = high.next;
        }

        while (high != null) {
            high = high.next;
            lowNode = lowNode.next;
        }


        return lowNode;

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        Stack<ListNode> s1 = new Stack<>();
        Stack<ListNode> s2 = new Stack<>();
        while (l1 != null || l2 != null) {
            if (l1 != null) {
                s1.push(l1);
                l1 = l1.next;
            }
            if (l2 != null) {
                s2.push(l2);
                l2 = l2.next;
            }
        }


        //进位
        int temp = 0;


        ListNode pre = null;

        while (!s1.isEmpty() || !s2.isEmpty()) {
            int n1 = s1.isEmpty() ? 0 : s1.pop().val;
            int n2 = s2.isEmpty() ? 0 : s2.pop().val;
            int num = n1 + n2 + temp;
            temp = 0;
            if (num > 9) {
                num = num % 10;
                temp = 1;
            }


            //在这里我们直接构建链表
            ListNode ln = new ListNode(num);
            if (pre != null) {
                ln.next = pre;
            }
            pre = ln;

        }
        if (temp != 0) {
            ListNode ln = new ListNode(temp);
            ln.next = pre;
            pre = ln;
        }
        return pre;

    }


    /**
     * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
     * <p>
     * 链接：https://leetcode-cn.com/problems/binary-search
     */
    public static int search(int[] nums, int target) {

        if (nums == null || nums.length == 0)
            return -1;

        if (nums[0] == target)
            return 0;

        int low = 0;
        int high = nums.length - 1;


        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (target == nums[mid])
                return mid;
            if (target < nums[mid])
                high = mid - 1;
            else
                low = mid + 1;

        }
        return -1;

    }


    /**
     * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4
     */

//    public int[] getLeastNumbers(int[] arr, int k) {
//
//        if (arr == null || arr.length <= k)
//            return arr;
//
//        int length = arr.length - 1;
//
//
//
//
//    }

    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     */
    public static boolean isValid(String s) {

        if (s == null || (s.length() & 1) == 1)
            return false;

        Stack<Character> stack = new Stack<Character>();

        int length = s.length();
        for (int i = 0; i < length; i++) {

            char cr = s.charAt(i);
            if (cr == '{' || cr == '[' || cr == '(')
                stack.push(cr);
            else {
                if (stack.isEmpty())
                    return false;
                char topCr = stack.pop();
                switch (topCr) {
                    case '{':
                        if (cr != '}')
                            return false;
                        break;
                    case '[':
                        if (cr != ']')
                            return false;
                        break;
                    case '(':
                        if (cr != ')')
                            return false;
                        break;
                }
            }

        }

        return stack.isEmpty();

    }


    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * <p>
     * 如果不存在公共前缀，返回空字符串 ""。
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        if (strs.length == 1)
            return strs[0];

        String maxStr = strs[0];

        for (int i = 1; i < strs.length; i++) {
            if (maxStr.equals(""))
                return "";
            String curStr = strs[i];

            if (maxStr.length() > curStr.length())
                maxStr = maxStr.substring(0, curStr.length());
            int size = maxStr.length();
            for (int j = 0; j < size; j++) {
                if (maxStr.charAt(j) != curStr.charAt(j)) {
                    if (j == 0)
                        return "";
                    maxStr = maxStr.substring(0, j);
                    break;
                }


            }
        }

        return maxStr;

    }


    /**
     * 给你两个二进制字符串，返回它们的和（用二进制表示）。
     * <p>
     * 输入为 非空 字符串且只包含数字 1 和 0
     */
    public static String addBinary(String a, String b) {
        if ("".equals(a) || "".equals(b))
            return a + b;
        int aIndex = a.length() - 1;
        int bIndex = b.length() - 1;

        int temp = 0;
        StringBuilder str = new StringBuilder();
        while (aIndex >= 0 || bIndex >= 0) {
            int curA = aIndex >= 0 ? a.charAt(aIndex--) - '0' : 0;
            int curB = bIndex >= 0 ? b.charAt(bIndex--) - '0' : 0;

            int sum = curA + curB + temp;
            temp = sum >> 1;
            str.insert(0, sum & 1);

        }

        if (temp > 0)
            str.insert(0, temp);

        return str.toString();

    }


    /**
     * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     */
    public static String reverseWords(String s) {
        if (s == null || s.trim().length() < 2)
            return s;

        int low = 0;
        int high = 0;
        int size = s.length();

        boolean findSpace = false;
        StringBuilder builder = new StringBuilder();
        while (high < size) {

            if (s.charAt(high) == ' ') {
                if (!findSpace) {
                    findSpace = true;
                    String rewardStr = s.substring(low, high);
                    int subSize = high - low;
                    for (int i = subSize - 1; i >= 0; i--) {
                        builder.append(rewardStr.charAt(i));
                    }
                }

                builder.append(s.charAt(high));
            }

            if (s.charAt(high) != ' ' && findSpace) {
                findSpace = false;
                low = high;
            }


            high++;
        }

        if (high > low) {
            if (!findSpace) {
                String rewardStr = s.substring(low, high);
                int subSize = high - low;
                for (int i = subSize - 1; i >= 0; i--) {
                    builder.append(rewardStr.charAt(i));
                }
            }
        }


        return builder.toString();

    }


    /**
     * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
     */
    public static char firstUniqChar(String s) {

        char[] array = s.toCharArray();
        int[] temp = new int[26];
        for (char ch : array) {
            int result = ch - 'a';
            temp[result] = temp[result] + 1;
        }

        for (char ch : array) {
            int result = ch - 'a';
            if (temp[result] == 1)
                return ch;
        }


        return ' ';
    }

    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * <p>
     * 说明：
     * <p>
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     */
    public static int singleNumber(int[] nums) {

        int result = 0;
        for (int num : nums) {
            result ^= num;
        }

        return result;

    }

    /**
     * 给定一个二叉树，返回它的 前序 遍历。
     */


    public static List<Integer> preorderTraversal(TreeNode root, int tyep) {
        List<Integer> mList = new ArrayList<>();
        if (root == null)
            return mList;
        switch (tyep) {
            case 0:
                sortFront(root, mList);
                break;
            case 1:
                sortInfix(root, mList);
                break;
            case 2:
                sortBack(root, mList);
                break;
            case 3:
                findDFS(root, mList);
                break;
            case 4:
                findBFS(root, mList);
                break;
        }

        return mList;

    }


    /**
     * 树的前序遍历
     */
    public static void sortFront(TreeNode node, List<Integer> list) {
        if (node == null)
            return;
        list.add(node.val);
        if (node.left != null)
            sortFront(node.left, list);
        if (node.right != null)
            sortFront(node.right, list);
    }


    /**
     * 树的中序遍历
     */
    public static void sortInfix(TreeNode node, List<Integer> list) {
        if (node == null)
            return;
        if (node.left != null)
            sortInfix(node.left, list);
        list.add(node.val);
        if (node.right != null)
            sortInfix(node.right, list);

    }

    /**
     * 树的后序遍历
     */
    public static void sortBack(TreeNode node, List<Integer> list) {
        if (node == null)
            return;
        if (node.left != null)
            sortBack(node.left, list);
        if (node.right != null)
            sortBack(node.right, list);
        list.add(node.val);
    }


    /**
     * 深度遍历
     */
    public static void findDFS(TreeNode root, List<Integer> list) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node.val);
            if (node.left != null)
                stack.push(node.left);
            if (node.right != null)
                stack.push(node.right);
        }

    }

    /**
     * 广度遍历
     */
    public static void findBFS(TreeNode root, List<Integer> list) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
    }


    /**
     * 给你一个数组 nums 。数组「动态和」的计算公式为：runningSum[i] = sum(nums[0]…nums[i]) 。
     * 请返回 nums 的动态和。
     */
    public static int[] runningSum(int[] nums) {
        if (nums == null || nums.length == 1)
            return nums;
        int size = nums.length;
        for (int i = 1; i < size; i++) {
            nums[i] = nums[i] + nums[i - 1];
        }
        return nums;
    }

    /**
     * 给你一个整数数组 nums 。
     * <p>
     * 如果一组数字 (i,j) 满足 nums[i] == nums[j] 且 i < j ，就可以认为这是一组 好数对 。
     * <p>
     * 返回好数对的数目。
     */

    public static int numIdenticalPairs(int[] nums) {

        if (nums == null || nums.length < 2)
            return 0;

        int count = 0;
        int low = 0;
        int high = 1;
        int size = nums.length;

        while (low < (size - 1)) {
            while (high < size) {
                if (nums[low] == nums[high])
                    count++;
                high++;
            }
            low++;
            high = low + 1;

        }

        return count;

    }

    /**
     * 给你一个数组 nums ，数组中有 2n 个元素，按 [x1,x2,...,xn,y1,y2,...,yn] 的格式排列。
     * <p>
     * 请你将数组按 [x1,y1,x2,y2,...,xn,yn] 格式重新排列，返回重排后的数组。
     * <p>
     */
    public static int[] shuffle(int[] nums, int n) {
        if (nums == null || (nums.length & 1) == 1)
            return nums;
        int[] xArr = new int[n];
        int[] yArr = new int[n];
        for (int i = 0; i < n; i++) {
            xArr[i] = nums[i];
            yArr[i] = nums[n + i];
        }

        for (int i = 0; i < n; i++) {
            nums[i * 2] = xArr[i];
            nums[i * 2 + 1] = yArr[i];
        }


        return nums;
    }


    /**
     * 给你一个整数数组 arr，请你帮忙统计数组中每个数的出现次数。
     * <p>
     * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
     * <p>
     * 未完成
     */
    public static boolean uniqueOccurrences(int[] arr) {

        if (arr == null || arr.length == 0)
            return false;
        if (arr.length == 1)
            return true;


        int low = 0;
        int high = 1;
        int size = arr.length;
        TreeSet<Integer> set = new TreeSet<>();
        int repeat = 1;

        while (low < size) {
            System.out.println("LOw:" + low + "---high:" + high);
            if (high >= size) {
                low++;
                high = low + 1;
                if (!set.add(repeat))
                    return false;
                else
                    repeat = 1;
            }

            int num = arr[high];

            if (num != arr[low]) {
                high++;
                continue;
            }

            while (low < high && arr[low + 1] == num) {
                low++;
                repeat++;
            }
            if (low == high) {
                low = high;
                high = low + 1;
                continue;
            }

            if (low < high) {
                int temp = arr[low + 1];
                arr[low + 1] = num;
                arr[high] = temp;

                low++;
                high++;
                repeat++;
            }

            System.out.println(Arrays.toString(arr) + "日志:" + repeat);


        }

        return true;

    }


    /**
     * 给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。
     * <p>
     * 例如，从根到叶子节点路径 1->2->3 代表数字 123。
     * <p>
     * 计算从根到叶子节点生成的所有数字之和。
     */
    public static int sumNumbers(TreeNode root) {

        if (root == null)
            return 0;

        int[] result = new int[]{0};
        getNodeCount(root, 0, result);
        return result[0];

    }

    private static void getNodeCount(TreeNode node, int nodeNum, int[] result) {

        //子节点
        int curNodeNum = nodeNum * 10 + node.val;
        if (node.left == null && node.right == null) {
            int value = result[0];
            value = value + curNodeNum;
            result[0] = value;
            return;
        }
        if (node.left != null)
            getNodeCount(node.left, curNodeNum, result);


        if (node.right != null)
            getNodeCount(node.right, curNodeNum, result);


    }


    /**
     * 给你一个数组 candies 和一个整数 extraCandies ，其中 candies[i] 代表第 i 个孩子拥有的糖果数目。
     * <p>
     * 对每一个孩子，检查是否存在一种方案，将额外的 extraCandies 个糖果分配给孩子们之后，此孩子有 最多 的糖果。注意，允许有多个孩子同时拥有 最多 的糖果数目。
     */

    public static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {


        List<Boolean> mList = new ArrayList<>();
        if (candies == null)
            return mList;
        if (candies.length == 1) {
            mList.add(true);
            return mList;
        }
        int max = 0;

        int low = 0;
        int high = candies.length - 1;
        while (true) {


        }


    }


    /**
     * 给定一个包含 0 和 1 的二维网格地图，其中 1 表示陆地 0 表示水域。
     * <p>
     * 网格中的格子水平和垂直方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
     * <p>
     * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
     */


    public static int islandPerimeter(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;

        int count = 0;

        int x = grid[0].length;
        int y = grid.length;

        int index1 = 0;

        while (index1 < y) {
            int index2 = 0;
            while (index2 < x) {


                if (grid[index1][index2] == 1) {
                    //判断上下左右是否存在岛屿
                    int top = index1 - 1;
                    int left = index2 - 1;
                    int right = index2 + 1;
                    int bottom = index1 + 1;
                    count += 4;
                    if (top >= 0 && grid[top][index2] == 1) {
                        count--;
                    }
                    if (left >= 0 && grid[index1][left] == 1) {
                        count--;
                    }
                    if (bottom < y && grid[bottom][index2] == 1) {
                        count--;
                    }
                    if (right < x && grid[index1][right] == 1) {
                        count--;
                    }


                }
                index2++;
            }
            index1++;
        }

        return count;

    }


    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * <p>
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * <p>
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     */
    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        int temp = 0;

        ListNode resultNode = null;
        ListNode preNode = null;
        while (l1 != null || l2 != null) {
            int num1 = l1 == null ? 0 : l1.val;
            int num2 = l2 == null ? 0 : l2.val;
            int num = num1 + num2 + temp;
            temp = 0;
            if (num > 9) {
                num = num % 10;
                temp = 1;
            }

            if (resultNode == null) {
                resultNode = new ListNode(num);
                preNode = resultNode;
            } else {
                preNode.next = new ListNode(num);
                preNode = preNode.next;
            }

            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;


        }

        if (temp != 0) {
            preNode.next = new ListNode(temp);

        }

        return resultNode;

    }

    /**
     * 给定一个矩阵 A， 返回 A 的转置矩阵。
     * <p>
     * 矩阵的转置是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
     */
    public static int[][] transpose(int[][] A) {

        if (A == null)
            return null;

        int x = A.length;
        int y = A[0].length;


        int[][] result = new int[y][x];

        int index = 0;
        while (index < x) {
            int index2 = 0;
            while (index2 < y) {
                result[index2][index] = A[index][index2];
                index2++;
            }

            index++;
        }


        return result;

    }


    /**
     * 给你一个正整数数组 arr ，请你计算所有可能的奇数长度子数组的和。
     * <p>
     * 子数组 定义为原数组中的一个连续子序列。
     * <p>
     * 请你返回 arr 中 所有奇数长度子数组的和
     */
    public static int sumOddLengthSubarrays(int[] arr) {

        if (arr == null || arr.length == 0)
            return 0;

        int step = 0;
        int size = arr.length;

        int count = 0;
        int index;
        while (step < size) {
            index = 0;
            while (index + step < size) {
                for (int i = index; i <= index + step; i++) {
                    count += arr[i];
                }
                index++;
            }


            step += 2;
        }


        return count;
    }


    /**
     * 给定一个整数数组 A，如果它是有效的山脉数组就返回 true，否则返回 false。
     * <p>
     * 让我们回顾一下，如果 A 满足下述条件，那么它是一个山脉数组：
     * <p>
     * A.length >= 3
     * 在 0 < i < A.length - 1 条件下，存在 i 使得：
     * A[0] < A[1] < ... A[i-1] < A[i]
     * A[i] > A[i+1] > ... > A[A.length - 1]
     */

    public static boolean validMountainArray(int[] A) {

        if (A == null || A.length < 3)
            return false;

        int low = 1;
        int high = A.length - 2;

        int lowIndex = 0;
        int highIndex = A.length - 1;

        while (low < highIndex && A[lowIndex] < A[low]) {
            lowIndex = low;
            low++;
        }


        while (highIndex > lowIndex && high >= 0 && A[high] > A[highIndex]) {
            highIndex = high;
            high--;
        }


        return (lowIndex == highIndex) && (lowIndex != 0) && (highIndex != A.length - 1);

    }


    /**
     * 给你一个整数 n，请你返回 任意 一个由 n 个 各不相同 的整数组成的数组，并且这 n 个数相加和为 0 。
     */

    public static int[] sumZero(int n) {

        if (n <= 0)
            return null;

        int[] arr = new int[n];
        if (arr.length < 2) {
            arr[0] = 0;
            return arr;
        }
        int low = 0;
        int high = 1;

        int mid = n >> 1;
        while (true) {
            if (low >= n)
                break;
            if (high >= n) {
                arr[low] = 0;
                break;
            }

            arr[low] = mid;
            arr[high] = -mid;

            mid--;
            low += 2;
            high += 2;
        }


        return arr;
    }

    /**
     * 输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
     * <p>
     * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
     */

    public static int[][] findContinuousSequence(int target) {


        //获取 最大数组数量
        int max = (int) Math.sqrt(target * 2);

        int[][] arr = new int[max][];
        int index = 0;
        while (max > 1) {
            int num = (target * 2 + max - max * max);
            if (num % (2 * max) == 0) {
                int start = num / (2 * max);
                int[] subArr = new int[max];
                for (int i = 0; i < max; i++) {
                    subArr[i] = start + i;
                }
                arr[index] = subArr;
                index++;
            }

            max--;
        }


        return Arrays.copyOf(arr, index);


    }


    /**
     * 给你一个整数数组 A，请你给数组中的每个元素 A[i] 都加上一个任意数字 x （-K <= x <= K），从而得到一个新数组 B 。
     * <p>
     * 返回数组 B 的最大值和最小值之间可能存在的最小差值。
     */
    public int smallestRangeI(int[] A, int K) {

        if (A.length < 2)
            return 0;

        int min = A[0];
        int max = A[0];
        int size = A.length;

        for (int i = 1; i < size; i++) {
            int num = A[i];
            if (num < min) {
                min = num;
            } else if (num > max) {
                max = num;
            }
        }

        int diff = max - min;

        if (diff <= K * 2)
            return 0;

        return diff - K * 2;


    }


    /**
     * 给定一个非负整数数组 A， A 中一半整数是奇数，一半整数是偶数。
     * <p>
     * 对数组进行排序，以便当 A[i] 为奇数时，i 也是奇数；当 A[i] 为偶数时， i 也是偶数。
     * <p>
     * 你可以返回任何满足上述条件的数组作为答案。
     */

    public int[] sortArrayByParityII(int[] A) {
        if (A.length < 1)
            return A;

        int low = 0;
        int high = A.length - 1;
        int size = A.length;


        while (true) {
            while (0 <= high && (high & 1) == (A[high] & 1)) {
                high -= 2;
            }
            if (high < 0)
                break;

            while (low <= size && (low & 1) == (A[low] & 1)) {
                low += 2;
            }

            if (low > size)
                break;
            //记录当前需要交换的值
            int num = A[high];
            A[high] = A[low];
            A[low] = num;


        }

        return A;
    }


    /**
     * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
     */
    public static ListNode removeDuplicateNodes(ListNode head) {


        if (head == null || head.next == null)
            return head;

        ListNode result = null;
        ListNode preNode = result;
        Set<Integer> set = new TreeSet();

        while (head != null) {
            if (set.add(head.val)) {
                if (result == null) {
                    result = head;
                    preNode = result;
                } else {
                    preNode.next = head;
                    preNode = preNode.next;
                }

            }
            head = head.next;

        }
        preNode.next = null;
        return result;
    }


    /**
     * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums
        ) {
            if (num >= target)
                break;
            int preNum = target - num;

            if (map.get(num) == null) {
                map.put(preNum, num);
            } else {
                result[0] = preNum;
                result[1] = num;
                break;
            }

        }

        return result;

    }


    /**
     * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
     * <p>
     * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
     * <p>
     * 必须原地修改，只允许使用额外常数空间。
     * <p>
     * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     */
    public static void nextPermutation(int[] nums) {
        if (nums.length < 2)
            return;
        int size = nums.length;
        int low = 0;
        int high = 0;
        int index = 0;
        int position = 1;

        while (position < size) {

            int preNum = nums[index];
            int curNum = nums[position];
            //如果前一位大于当前位
            if (preNum > curNum) {
                low = position;
                high = index;
                index = low;
            } else if (preNum < curNum) {
                low = index;
                high = position;
                index = high;
            } else {
                low = position;
                high = position;
                index = position;
            }


            position++;
        }

        if (high < low) {
            //倒置
        }

        if (high == low) {

            //都一致 111
            return;
        }

        if (low < high) {
            int temp = nums[low];
        }


    }

    /**
     * 对链表进行插入排序
     */
    public static ListNode insertionSortList(ListNode head) {

        if (head == null || head.next == null)
            return head;

        ListNode resultNode = null;
        ListNode node = head;

        while (node != null) {

            //当前节点指针
            ListNode curNode = resultNode;
            //前驱节点指针
            ListNode preNode = null;

            while (curNode != null && curNode.val > node.val) {
                preNode = curNode;
                curNode = curNode.next;
            }

            //节点的值 大于等于 比较的值
            ListNode nd = node;
            node = node.next;

            if (preNode != null) {
                preNode.next = nd;
            } else
                resultNode = nd;
            nd.next = curNode;


        }


        //ListNode preNode = null;
        node = null;

        while (resultNode != null) {
            ListNode nd = resultNode;
            resultNode = resultNode.next;
            nd.next = node;
            node = nd;

        }


        return node;

    }


    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     */

    public static int[] twoSum2(int[] nums, int target) {
        if (nums == null || nums.length < 2)
            return null;

        int[] result = new int[2];
        int size = nums.length;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < size; i++
        ) {
            int num = nums[i];
            Integer num2 = map.get(num);
            if (num2 != null) {
                result[0] = num2;
                result[1] = i;
                return result;
            }

            int expectNum = target - num;
            map.put(expectNum, i);
        }

        return null;
    }


    /**
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * <p>
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     */
    public static int removeElement(int[] nums, int val) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {

            while (low <= high && nums[low] != val) {
                low++;
            }

            while (low <= high && nums[high] == val) {
                high--;
            }

            if (low <= high) {
                int num = nums[low];
                int temp = nums[high];

                nums[low] = temp;
                nums[high] = num;
                low++;
                high--;
            }


        }

        return high + 1;

    }


    /**
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     */
    public static int maxSubArray(int[] nums) {


        // 贪心算法
        // 比较前面的值+当前值的和 和 当前值 取最大值
        int preCount = 0;
        int maxCount = nums[0];

        for (int num : nums
        ) {
            preCount = Math.max(preCount + num, num);
            maxCount = Math.max(maxCount, preCount);
        }


        return maxCount;
    }

    /**
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     * <p>
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * <p>
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     */
    public static int[] plusOne(int[] digits) {

        int carryBit = 1;
        int size = digits.length - 1;

        while (size > 0) {
            int num = digits[size];
            num = num + carryBit;
            carryBit = 0;
            if (num > 9) {
                num = num % 10;
                carryBit = 1;
            }

            digits[size] = num;

            if (carryBit == 0)
                break;

            size--;
        }

        if (carryBit == 1) {
            int[] result = new int[digits.length + 1];
            result[0] = carryBit;
            System.arraycopy(digits, 0, result, 1, digits.length);

        }


        return digits;

    }


    /**
     * 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
     * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
     * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int high = m + n - 1;
        int index1 = m - 1;
        int index2 = n - 1;

        while (true) {


            if (index1 < 0 && index2 >= 0) {
                int num2 = nums2[index2];
                nums1[high] = num2;
                high--;
                index2--;
                continue;
            }

            if (index2 < 0 && index1 >= 0) {
                int num1 = nums1[index1];
                nums1[high] = num1;
                high--;
                index1--;
                continue;
            }

            if (index1 < 0 && index2 < 0)
                break;


            int num1 = nums1[index1];
            int num2 = nums2[index2];

            if (num1 >= num2) {
                nums1[high] = num1;
                high--;
                index1--;
            } else {
                nums1[high] = num2;
                high--;
                index2--;
            }

        }

    }


    /**
     * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
     * 在杨辉三角中，每个数是它左上方和右上方的数的和。
     */
    public static List<List<Integer>> generate(int numRows) {

        List<List<Integer>> result = new ArrayList<>();
        if (numRows > 0)
            doGenerate(numRows, result);

        return result;
    }

    private static void doGenerate(int numRows, List<List<Integer>> result) {
        List<Integer> list = new ArrayList<>();
        if (numRows <= 1) {
            list.add(1);
        } else {
            if (result.size() < numRows)
                doGenerate(numRows - 1, result);
            List<Integer> perList = result.get(numRows - 2);
            for (int i = 0; i < numRows; i++) {
                int leftNum = 0;
                if (i - 1 >= 0)
                    leftNum = perList.get(i - 1);

                int rightNum = 0;
                if (i < numRows - 1)
                    rightNum = perList.get(i);

                list.add(leftNum + rightNum);


            }

        }
        result.add(list);

    }

    /**
     * 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
     */
    public static List<Integer> getRow(int rowIndex) {
        return doGetRow(rowIndex);
    }

    private static List<Integer> doGetRow(int rowIndex) {
        List<Integer> mList = new ArrayList<>();
        if (rowIndex == 0) {
            mList.add(1);
            return mList;
        }
        List<Integer> perList = doGetRow(rowIndex - 1);
        for (int i = 0; i < rowIndex; i++) {
            int leftNum = 0;
            if (i - 1 >= 0)
                leftNum = perList.get(i - 1);

            int rightNum = 0;
            if (i < rowIndex - 1)
                rightNum = perList.get(i);

            mList.add(leftNum + rightNum);
        }
        return mList;


    }

    /**
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
     * <p>
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     */
    public static int majorityElement(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums
        ) {

            Integer expect = map.get(num);
            if (expect == null) {
                map.put(num, 1);
            } else {
                map.put(num, 1 + expect);
            }
        }

        int mid = nums.length >> 1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()
        ) {
            if (entry.getValue() > mid)
                return entry.getKey();
        }

        return 0;
    }


    /**
     * 实现 strStr() 函数。
     * <p>
     * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
     */
    public static int strStr(String haystack, String needle) {
        if (needle == null || needle.equals(""))
            return 0;
        if (haystack == null || haystack.length() < needle.length())
            return -1;


        char[] hayArray = haystack.toCharArray();
        char[] neeArray = needle.toCharArray();
        int haySize = hayArray.length;
        int neeSize = neeArray.length;

        int index = -1;
        for (int i = 0; i < haySize; i++) {
            index = i;
            for (int j = 0; j < neeSize; j++) {
                if (i + neeSize > haySize) {
                    index = -1;
                    return index;
                }

                if (hayArray[i + j] != neeArray[j]) {
                    index = -1;
                    break;
                }

            }

            if (index != -1)
                return index;


        }

        return index;


    }


    /**
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * <p>
     * 说明：本题中，我们将空字符串定义为有效的回文串。
     */

    public static boolean isPalindrome(String s) {

        if (s == null)
            return false;
        if (s.length() < 2)
            return true;
        int lowIndex = 0;
        int highIndex = s.length() - 1;
        int size = s.length();
        while (lowIndex < highIndex) {

            while (lowIndex < size && !Character.isLetterOrDigit(s.charAt(lowIndex)))
                lowIndex++;
            while (highIndex > -1 && !Character.isLetterOrDigit(s.charAt(highIndex)))
                highIndex--;

            if (lowIndex > highIndex)
                return true;
            if (Character.toLowerCase(s.charAt(lowIndex)) != Character.toLowerCase(s.charAt(highIndex))) {
                return false;
            }
            lowIndex++;
            highIndex--;

        }
        return true;

    }


    /**
     * 请判断一个链表是否为回文链表
     * <p>
     * 1. 将所有数据取出来 在判断是否是回文
     * 2. 采用递归的方式
     * 3. 由中间翻转后面的链表
     * <p>
     * 这里采用递归
     */
    private ListNode frontPointer;//前置节点

    public boolean isPalindrome(ListNode head) {
        frontPointer = head;
        return recursivelyCheck(head);

    }

    private boolean recursivelyCheck(ListNode currentNode) {

        if (currentNode != null) {
            //为了回调到最后一个节点
            if (!recursivelyCheck(currentNode.next))
                return false;
            //开始回升
            if (frontPointer.val != currentNode.val)
                return false;
            frontPointer = frontPointer.next;

        }

        return true;
    }


    /**
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     */
    public void moveZeroes(int[] nums) {
        int size = nums.length;
        int[] preNums = new int[size];
        System.arraycopy(nums, 0, preNums, 0, size);

        int lowIndex = 0;
        int highIndex = size - 1;
        for (int i = 0; i < size; i++) {
            int num = preNums[i];
            if (num == 0) {
                nums[highIndex] = 0;
                highIndex--;
            } else {
                nums[lowIndex] = num;
                lowIndex++;
            }

        }

    }


    /**
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
     * <p>
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     */

    public void reverseString(char[] s) {

        int low = 0;
        int high = s.length - 1;
        while (low < high) {
            char lowVal = s[low];
            char highVal = s[high];
            s[low] = highVal;
            s[high] = lowVal;
            low++;
            high--;
        }

    }

    /**
     * 给你一个字符串 s 和一个 长度相同 的整数数组 indices 。
     * 请你重新排列字符串 s ，其中第 i 个字符需要移动到 indices[i] 指示的位置。
     * 返回重新排列后的字符串。
     */
    public String restoreString(String s, int[] indices) {
        char[] srcArr = s.toCharArray();
        char[] tempArr = new char[srcArr.length];

        int size = indices.length;
        for (int i = 0; i < size; i++) {
            int index = indices[i];
            tempArr[index] = srcArr[i];

        }
        return new String(tempArr);
    }

    /**
     * 反转一个单链表。
     */
    public ListNode reverseList2(ListNode head) {

        if (head == null || head.next == null)
            return head;

        ListNode preNode = head;
        ListNode frontNode = null;

        while (preNode != null) {
            ListNode currentNode = preNode;
            preNode = preNode.next;
            currentNode.next = frontNode;
            frontNode = currentNode;
        }

        return frontNode;

    }

    /**
     * 你和你的朋友，两个人一起玩 Nim 游戏：
     * <p>
     * 桌子上有一堆石头。
     * 你们轮流进行自己的回合，你作为先手。
     * 每一回合，轮到的人拿掉 1 - 3 块石头。
     * 拿掉最后一块石头的人就是获胜者。
     * 假设你们每一步都是最优解。请编写一个函数，来判断你是否可以在给定石头数量为 n 的情况下赢得游戏。如果可以赢，返回 true；否则，返回 false 。
     */
    public boolean canWinNim(int n) {


        return n % 4 != 0;

    }

    /**
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {

        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        ListNode mergeNode = null;
        ListNode preNode = null;

        while (l1 != null && l2 != null) {

            while (l1 != null && l1.val <= l2.val) {
                if (mergeNode == null) {
                    mergeNode = l1;
                    preNode = mergeNode;
                } else {
                    preNode.next = l1;
                    preNode = l1;
                }
                l1 = l1.next;
            }

            if (l1 == null) {
                preNode.next = l2;
                break;
            }

            while (l2 != null && l2.val <= l1.val) {
                if (mergeNode == null) {
                    mergeNode = l2;
                    preNode = mergeNode;
                } else {
                    preNode.next = l2;
                    preNode = l2;
                }
                l2 = l2.next;
            }

            if (l2 == null) {
                preNode.next = l1;
                break;
            }


        }


        return mergeNode;
    }


    /**
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     */
    public boolean isPalindrome(int x) {

        if (x < 0 || (x % 10 == 0 && x != 0))
            return false;

        int revertedNum = 0;
        while (revertedNum < x) {
            int tempNum = x % 10;
            x = x / 10;
            revertedNum = revertedNum * 10 + tempNum;
        }

        return revertedNum == x || revertedNum / 10 == x;

    }

    /**
     * 编写一个程序，找到两个单链表相交的起始节点。
     * 快慢指针
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode quickNode = headA;
        ListNode lowNode = headB;
        boolean quickStatus = false;
        boolean lowStatus = false;
        while (true) {
            if (quickNode == lowNode)
                return quickNode;

            if (quickNode.next != null)
                quickNode = quickNode.next;
            else {
                quickNode = quickStatus ? headA : headB;
                quickStatus = !quickStatus;
            }

            if (lowNode.next != null)
                lowNode = lowNode.next;
            else {
                lowNode = lowStatus ? headB : headA;
                lowStatus = !lowStatus;
            }

        }


    }

    public static ListNode getIntersectionNode2(ListNode headA, ListNode headB) {


        ListNode p = headA, q = headB;
        while (p != q) {
            p = (p == null) ? headB : p.next;
            q = (q == null) ? headA : q.next;
        }
        return p;
    }


    /**
     * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
     * <p>
     * push(x) —— 将元素 x 推入栈中。
     * pop() —— 删除栈顶的元素。
     * top() —— 获取栈顶元素。
     * getMin() —— 检索栈中的最小元素。
     */
    class MinStack {

        ListNode currentNode = null;

        /**
         * initialize your data structure here.
         */
        public MinStack() {

        }

        public void push(int x) {
            if (currentNode == null) {
            }

            ListNode node = new ListNode(x);
            node.next = currentNode;
            currentNode = node;

        }

        public void pop() {
            currentNode = currentNode.next;
        }

        public int top() {
            if (currentNode == null)
                throw new NullPointerException("");
            return currentNode.val;

        }

        public int getMin() {
            if (currentNode == null)
                throw new NullPointerException("");
            int minValue = currentNode.val;
            ListNode preNode = currentNode.next;
            while (preNode != null) {
                minValue = Math.min(minValue, preNode.val);
                preNode = preNode.next;
            }
            return minValue;

        }

    }


    /**
     * 给定一个整数数组，判断是否存在重复元素。
     * <p>
     * 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
     */
    public boolean containsDuplicate(int[] nums) {

        Set<Integer> set = new TreeSet<>();
        for (int num : nums
        ) {
            if (!set.add(num))
                return true;
        }
        return false;
    }

    /**
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 注意：给定 n 是一个正整数。
     */
    public int climbStairs(int n) {

        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        int a = 1, b = 2, r = 0;
        for (int i = 3; i <= n; i++) {
            r = a + b;
            a = b;
            b = r;
        }
        return r;

    }


    /**
     * 给定一个整数，编写一个函数来判断它是否是 2 的幂次方
     */
    public boolean isPowerOfTwo(int n) {
        if (n < 1)
            return false;
        return (n & (n - 1)) == 0;
    }


    /**
     * 一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。
     * 在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。
     * 给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
     */
    public int massage(int[] nums) {

        //动态规则
        //

        if (nums.length == 0)
            return 0;
        int size = nums.length;
        //上一次的最优解
        int preOptimal = 0;
        //当前节点的最优解
        int curOptimal = nums[0];

        for (int i = 1; i < size; i++) {

            //获取当前存在的最优解
            int optimal0 = Math.max(preOptimal, curOptimal);
            int optimal1 = preOptimal + nums[i];

            preOptimal = optimal0;
            curOptimal = optimal1;

        }

        return Math.max(preOptimal, curOptimal);


    }


    public int minCostClimbingStairs(int[] cost) {

        int p0 = 0;
        int p1 = 0;
        int size = cost.length - 1;
        for (int i = size; i >= 0; i--) {
            int t0 = cost[i] + Math.min(p0, p1);
            System.out.println("当前P0:" + p0 + "   P1:" + p1 + "  t0:" + t0);
            p1 = p0;
            p0 = t0;
        }


        return Math.min(p0, p1);
    }

    public int waysToStep(int n) {
        int p1 = 1;
        if (n < 2)
            return p1;
        int p2 = 2;
        if (n < 3)
            return p2;
        int p3 = 4;

        for (int i = 4; i <= n; i++) {

            int pi = p1 % 1000000007 + p2 % 1000000007 + p3 % 1000000007;


            p1 = p2 % 1000000007;
            p2 = p3 % 1000000007;
            p3 = pi;

        }

        return p3;

    }

}
