package com.ct.design.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFS {
    public static void main(String[] args) {
        new DFS().run();
    }

    private void run() {
    }


    /**
     * 实现一个函数，检查二叉树是否平衡。在这个问题中，平衡树的定义如下：任意一个节点，其两棵子树的高度差不超过 1。
     */
    public boolean isBalanced(TreeNode root) {
//        if (root == null)
//            return true;
//        //由上向下遍历
//        //判读左右子树的高度差是否大于1
//        return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);

        //由下向上遍历
        return height2(root) >= 0;

    }


    private int height(TreeNode root) {
        if (root == null)
            return 0;

        return Math.max(height(root.left), height(root.right)) + 1;
    }

    private int height2(TreeNode root) {
        if (root == null)
            return 0;

        int leftHeight = height2(root.left);
        int rightHeight = height2(root.right);
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1)
            return -1;
        return Math.max(leftHeight, rightHeight) + 1;

    }

    /**
     * 请考虑一棵二叉树上所有的叶子，这些叶子的值按从左到右的顺序排列形成一个 叶值序列
     * 举个例子，如上图所示，给定一棵叶值序列为 (6, 7, 4, 9, 8) 的树。
     * 如果有两棵二叉树的叶值序列是相同，那么我们就认为它们是 叶相似 的。
     * 如果给定的两个头结点分别为 root1 和 root2 的树是叶相似的，则返回 true；否则返回 false 。
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return true;
        if (root1 == null || root2 == null)
            return false;

        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root1);
        stack2.push(root2);

        int num1 = 0;
        int num2 = 0;

        while (num1 == num2 && !stack1.isEmpty() && !stack2.isEmpty()) {
            num1 = getLeaf(stack1);
            num2 = getLeaf(stack2);
        }

        if (num1 != num2)
            return false;
        return stack1.isEmpty() && stack2.isEmpty();


    }

    private int getLeaf(Stack<TreeNode> stack) {
        while (!stack.isEmpty()) {
            TreeNode node1 = stack.pop();
            if (node1.left == null && node1.right == null) {
                return node1.val;
            } else {
                if (node1.right != null)
                    stack.push(node1.right);
                if (node1.left != null)
                    stack.push(node1.left);
            }
        }
        return 0;
    }


    /**
     * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
     * 说明: 叶子节点是指没有子节点的节点。
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> mList = new ArrayList<>();
        if (root == null)
            return mList;
        getPath("", root, mList);
        return mList;

    }

    private void getPath(String builder, TreeNode node, List<String> list) {

        String newBuilder = "".equals(builder) ? "" + node.val : builder + "->" + node.val;
        if (node.left == null && node.right == null) {
            list.add(newBuilder);
        }
        if (node.left != null)
            getPath(newBuilder, node.left, list);
        if (node.right != null)
            getPath(newBuilder, node.right, list);
    }


    /**
     * 给你一个树，请你 按中序遍历 重新排列树，使树中最左边的结点现在是树的根，并且每个结点没有左子结点，只有一个右子结点。
     */

    public TreeNode increasingBST(TreeNode root) {
        TreeNode result = new TreeNode(-1);
        TreeNode[] arr = new TreeNode[1];
        arr[0] = result;
        createTree(root, arr);
        return result.right;

    }

    private void createTree(TreeNode root, TreeNode[] result) {
        if (root.left != null)
            createTree(root.left, result);
        result[0].right = new TreeNode(root.val);
        result[0] = result[0].right;
        if (root.right != null)
            createTree(root.right, result);
    }


    /**
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
     */

    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(0, nums.length - 1, nums);
    }

    private TreeNode buildTree(int start, int end, int[] nums) {
        if (start > end)
            return null;

        //这里需要向上取整
        int mid = (int) Math.ceil((start + end) / 2.0);
        TreeNode root = new TreeNode(nums[mid]);
        root.left = buildTree(start, mid - 1, nums);
        root.right = buildTree(mid + 1, end, nums);

        return root;

    }

    /**
     * 输入一棵二叉树的根节点，求该树的深度。从根节点到叶节点依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度。
     */
    public int maxDepth(TreeNode root) {
        return height(root);
    }


    /**
     * 给定一个二叉树，检查它是否是镜像对称的。
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        if (root.left == null && root.right == null)
            return true;
        if (root.left == null || root.right == null)
            return false;

        Stack<TreeNode> stackLeft = new Stack<>();
        Stack<TreeNode> stackRight = new Stack<>();
        stackLeft.add(root.left);
        stackRight.add(root.right);
        while (!stackLeft.isEmpty() && !stackRight.isEmpty()) {
            TreeNode nodeLeft = stackLeft.pop();
            TreeNode nodeRight = stackRight.pop();
            if (nodeLeft == null && nodeRight == null)
                continue;
            if (nodeLeft == null || nodeRight == null || nodeLeft.val != nodeRight.val)
                return false;
            stackLeft.push(nodeLeft.left);
            stackRight.push(nodeRight.right);
            stackLeft.push(nodeLeft.right);
            stackRight.push(nodeRight.left);
        }
        return stackLeft.size() == stackRight.size();

    }

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * 本题中，一棵高度平衡二叉树定义为：
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
     */
    public boolean isBalanced2(TreeNode root) {
        //由上到下遍历
        if (root == null)
            return true;
        return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced2(root.left) && isBalanced2(root.right);


        //由下到上遍历


    }

    private int isBalanceHeight(TreeNode node) {
        if (node == null)
            return 0;
        int left = isBalanceHeight(node.left);
        int right = isBalanceHeight(node.right);

        if (left == -1 || right == -1 || Math.abs(left - right) > 1)
            return -1;

        return Math.max(left, right) + 1;
    }

    /**
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null)
            return false;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left == null && node.right == null && node.val == sum)
                return true;
            if (node.left != null) {
                node.left.val = node.left.val + node.val;
                stack.push(node.left);
            }

            if (node.right != null) {
                node.right.val = node.right.val + node.val;
                stack.push(node.right);
            }
        }


        return false;

    }

    /**
     * 给定两个二叉树，编写一个函数来检验它们是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p == null || q == null)
            return false;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 给定一个有序整数数组，元素各不相同且按升序排列，编写一个算法，创建一棵高度最小的二叉搜索树。
     */
    public TreeNode sortedArrayToBST2(int[] nums) {
        return buildTree2(0, nums.length - 1, nums);
    }

    private TreeNode buildTree2(int start, int end, int[] nums) {
        if (start > end)
            return null;
        int mid = (start + end + 1) >> 1;
        TreeNode treeNode = new TreeNode(nums[mid]);
        treeNode.left = buildTree2(start, mid - 1, nums);
        treeNode.right = buildTree2(mid + 1, end, nums);
        return treeNode;

    }

}
