package com.ct.design.alg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    public static void main(String[] args) {
        new BFS().run();
    }

    private void run() {
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

}
