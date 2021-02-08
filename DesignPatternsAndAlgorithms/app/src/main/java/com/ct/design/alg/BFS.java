package com.ct.design.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class BFS {
    public static void main(String[] args) {
        new BFS().run();
    }


    private TreeNode initTree() {
        TreeNode node1 = new TreeNode(9);
        TreeNode node2 = new TreeNode(7, null, node1);
        TreeNode node3 = new TreeNode(6);
        TreeNode node7 = new TreeNode(3, node3, node2);
        TreeNode node4 = new TreeNode(8);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(4, node4, null);
        TreeNode node8 = new TreeNode(2, node6, node5);
        TreeNode node9 = new TreeNode(1, node8, node7);
        return node9;
    }

    private void run() {
//        TreeNode node = initTree();
//        System.out.println("树的最小高度:" + minHeight(node));

       String[] dead =new String[]{"0201","0101","0102","1212","2002"};
        System.out.println("开锁的最少步骤:"+openLock(dead,"0202"));
    }


    /**
     * 给定一个二叉树，返回其节点值自底向上的层序遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> mList = new LinkedList<>();
        if (root == null)
            return mList;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int num;
        while (!queue.isEmpty()) {
            num = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null)
                    queue.offer(node.left);
                if (node.right != null)
                    queue.offer(node.right);
            }
            mList.addFirst(list);

        }

        return mList;
    }

    /**
     * 二叉树的最小高度
     */
    public int minHeight(TreeNode node) {
        if (node == null) return 0;
        int height = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode root = queue.poll();
                if (root.left == null && root.right == null)
                    return height;
                if (root.left != null)
                    queue.offer(root.left);
                if (root.right != null)
                    queue.offer(root.right);
            }
            height++;
        }

        return height;
    }


    /**
     * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。每个拨轮可以自由旋转：
     * 例如把 '9' 变为  '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
     * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
     * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
     * 字符串 target 代表可以解锁的数字，你需要给出最小的旋转次数，如果无论如何不能解锁，返回 -1。
     */
    public int openLock(String[] deadends, String target) {

        //思路 如果不考虑死亡数字的话 也就是一个数字可以向上拨动 和 向下拨动两种情况 比如 0->1 0->9
        // 那么就可以穷举出 0000 拨动一个数字的所有情况 0001，0010 ...八种情况
        // 这就相当于是一个有 8 个分支的树 通过BFS 获取指定数据一样了
        //代码如下:
//        Queue<String> queue = new LinkedList<>();
//        queue.offer("0000");
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            for (int i = 0; i < size; i++) {
//                String node = queue.poll();
//                for (int j = 0; j < 4; j++) {
//                    String upStr = up(node, j);
//                    String downStr = down(node, j);
//                    queue.offer(upStr);
//                    queue.offer(downStr);
//                }
//            }
//        }

        //现在只需要解决
        //1.死亡数字
        //2.返回条件
        //3.循环调用 因为 0000会出现0001 0001会出现0000

        Set<String> used = new TreeSet<>();
        Set<String> dead = new TreeSet<>();
        Queue<String> queue = new LinkedList<>();
        Collections.addAll(dead, deadends);
        queue.offer("0000");
        used.add("0000");
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String node = queue.poll();
                //已经使用过 或者是死亡数字
                if (dead.contains(node))
                    continue;
                if (node.equals(target))
                    return step;

                for (int j = 0; j < 4; j++) {
                    String upStr = up(node, j);
                    String downStr = down(node, j);

                    if (used.add(upStr))
                        queue.offer(upStr);
                    if (used.add(downStr))
                        queue.offer(downStr);
                }

            }
            step++;
        }
        return -1;

    }

    private String up(String target, int index) {
        char[] chars = target.toCharArray();
        char ch = chars[index];
        if (ch == '9')
            ch = '0';
        else
            ch += 1;
        chars[index] = ch;
        return new String(chars);
    }

    private String down(String target, int index) {
        char[] chars = target.toCharArray();
        char ch = chars[index];
        if (ch == '0')
            ch = '9';
        else
            ch -= 1;
        chars[index] = ch;
        return new String(chars);
    }

}
