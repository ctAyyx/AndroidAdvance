package com.ct.design.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Recall {
    public static void main(String[] args) {
        new Recall().run();
    }

    private void run() {
    }

    /**
     * 给你一个整数 n ，表示比赛中的队伍数。比赛遵循一种独特的赛制：
     * 如果当前队伍数是 偶数 ，那么每支队伍都会与另一支队伍配对。总共进行 n / 2 场比赛，且产生 n / 2 支队伍进入下一轮。
     * 如果当前队伍数为 奇数 ，那么将会随机轮空并晋级一支队伍，其余的队伍配对。总共进行 (n - 1) / 2 场比赛，且产生 (n - 1) / 2 + 1 支队伍进入下一轮。
     * 返回在比赛中进行的配对次数，直到决出获胜队伍为止。
     */
    public int numberOfMatches(int n) {
        return matches(n);
    }

    private int matches(int n) {
        if (n < 2)
            return 0;
        int num = 0;
        if ((n & 1) == 0) {
            num = n >> 1;
            return matches(num) + num;
        } else {
            num = ((n - 1) >> 1);
            return matches(num + 1) + num;
        }


    }

    /**
     * 求一个数组的全排列
     *
     */
    private List<List<Integer>> mList = new ArrayList<>();

    private List<List<Integer>> arrange(int[] arr) {
        List<Integer> list = new ArrayList<>();
        range(arr, list);
        return mList;
    }

    private void range(int[] arr, List<Integer> list) {

        if (list.size() == arr.length) {
            mList.add(new ArrayList<Integer>(list));
            return;
        }

        for (int num : arr
        ) {
            if (list.contains(num))
                continue;
            list.add(num);
            range(arr, list);
            list.remove(num);
        }
    }

}
