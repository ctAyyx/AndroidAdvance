package com.ct.design.alg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinarySearch {

    public static void main(String[] args) {
        new BinarySearch().run();
    }

    private void run() {
        System.out.println(mySqrt(2147395599));
        // System.out.println(mySqrt(2));
    }

    /**
     * 稀疏数组搜索。有个排好序的字符串数组，其中散布着一些空字符串，编写一种方法，找出给定字符串的位置。
     */
    public int findString(String[] words, String s) {
        int start = 0;
        int end = words.length - 1;

        while (start <= end) {
            int mid = (start + end) >> 1;
            String tar = words[mid];

            int res = tar.compareTo(s);
            if (res == 0)
                return mid;
            else if (res < 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }


        }
        return -1;
    }


    /**
     * 给定两个数组，编写一个函数来计算它们的交集。
     */
    public int[] intersect(int[] nums1, int[] nums2) {


        int[] arr1, arr2;
        if (nums1.length >= nums2.length) {
            arr1 = nums2;
            arr2 = nums1;
        } else {
            arr1 = nums1;
            arr2 = nums2;
        }


        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> mList = new ArrayList<>();
        for (int num : arr1
        ) {
            Integer count = map.get(num);
            if (count == null)
                count = 1;
            else
                count += 1;
            map.put(num, count);
        }

        for (int num : arr2
        ) {
            Integer count = map.get(num);
            if (count != null && count > 0) {
                mList.add(num);
                map.put(num, count - 1);
            }
        }

        int size = mList.size();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = mList.get(i);
        }

        return arr;
    }

    /**
     * 实现 int sqrt(int x) 函数。
     * <p>
     * 计算并返回 x 的平方根，其中 x 是非负整数。
     * <p>
     * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
     */
    public int mySqrt(int x) {
        if (x < 2)
            return x;
        int start;
        int end;
        start = 0;
        end = 65535;
        while (start <= end) {
            int mid = (start + end) >> 1;
            int preNum = mid * mid;
            if (preNum < 0) {
                end = mid;
                continue;
            }

            if (preNum > x) {
                end = mid;
                continue;
            }

            if (preNum == x) {
                start = mid;
                break;
            }
            if (preNum < x) {
                if (start == mid)
                    break;
                start = mid;
            }
        }


        return start;

    }


    /**
     * 你总共有 n 枚硬币，你需要将它们摆成一个阶梯形状，第 k 行就必须正好有 k 枚硬币。
     * 给定一个数字 n，找出可形成完整阶梯行的总行数。
     * n 是一个非负整数，并且在32位有符号整型的范围内。
     */
    public int arrangeCoins(int n) {
        int start = 1;
        int end = n >> 1 > 65535 ? 65535 : n;
        while (start <= end) {
            int mid = (start + end) >> 1;
            int preNum = (1 + mid) * mid >> 1;


        }
        return start;
    }

}
