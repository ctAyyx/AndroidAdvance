package com.ct.design.alg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Arr {

    public static void main(String[] args) {
        new Arr().run();
    }

    private void run() {
        int[] arr = new int[]{37, 12, 28, 9, 100, 56, 80, 5, 12};


    }

    /**
     * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
     */
    public int[] sortedSquares(int[] A) {
        if (A == null || A.length == 0)
            return A;
        int size = A.length;
        int low = 0;
        int high = size - 1;
        int index = size - 1;
        int[] arr = new int[size];

        while (low <= high) {
            int num = A[low];
            int num2 = A[high];
            if (num2 * num2 >= num * num) {
                arr[index] = num2 * num2;
                high--;
                index--;
            } else {
                arr[index] = num * num;
                index--;
                low++;
            }
        }

        return arr;

    }

    /**
     * 给你一个整数数组 arr，请你判断数组中是否存在连续三个元素都是奇数的情况：如果存在，请返回 true ；否则，返回 false
     */
    public boolean threeConsecutiveOdds(int[] arr) {
        if (arr == null || arr.length < 3)
            return false;

        int count = 0;
        for (int num : arr
        ) {

            if ((num & 1) == 1) {
                count++;
            } else
                count = 0;
            if (count > 2)
                return true;

        }
        return false;
    }

    /**
     * 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，并且 i 和 j 的差的 绝对值 至多为 k。
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || k == 0)
            return false;
        Map<Integer, Integer> map = new HashMap<>();

        int size = nums.length;
        for (int i = 0; i < size; i++) {
            int num = nums[i];
            Integer index = map.get(num);
            if (index == null) {
                map.put(num, i);
            } else {
                if (i - index <= k)
                    return true;
                else
                    map.put(num, i);
            }
        }

        return false;

    }

    /**
     * 给你一个 m x n 的整数网格 accounts ，其中 accounts[i][j] 是第 i​​​​​​​​​​​​ 位客户在第 j 家银行托管的资产数量。返回最富有客户所拥有的 资产总量 。
     * 客户的 资产总量 就是他们在各家银行托管的资产数量之和。最富有客户就是 资产总量 最大的客户。
     */
    public int maximumWealth(int[][] accounts) {
        if (accounts == null || accounts.length == 0)
            return 0;

        int max = 0;
        for (int[] arr : accounts
        ) {
            int cur = 0;
            for (int num : arr) {
                cur += num;
            }
            max = Math.max(cur, max);
        }

        return max;
    }


    /**
     * 给定一个二进制数组， 计算其中最大连续1的个数。
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int max = nums[0];
        int size = nums.length;

        for (int i = 1; i < size; i++) {
            int num = nums[i];
            if (num == 1) {
                nums[i] = nums[i - 1] + num;
                max = Math.max(max, nums[i]);
            }


        }
        return max;
    }

    /**
     * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：l
     */
    public int fib(int n) {
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;

        int num1 = 0;
        int num2 = 1;

        for (int i = 2; i <= n; i++) {
            int num = num1 + num2;
            num1 = num2;
            num2 = num;
        }
        return num2;

    }

}
