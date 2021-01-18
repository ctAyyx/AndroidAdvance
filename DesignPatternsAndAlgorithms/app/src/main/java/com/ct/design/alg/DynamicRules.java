package com.ct.design.alg;

public class DynamicRules {

    public static void main(String[] args) {
        new DynamicRules().run();
    }

    private void run() {

    }

    /**
     * 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
     * 最初，黑板上有一个数字 N 。在每个玩家的回合，玩家需要执行以下操作：
     * 选出任一 x，满足 0 < x < N 且 N % x == 0 。
     * 用 N - x 替换黑板上的数字 N 。
     * 如果玩家无法执行这些操作，就会输掉游戏。
     * 只有在爱丽丝在游戏中取得胜利时才返回 True，否则返回 False。假设两个玩家都以最佳状态参与游戏。
     */
    public boolean divisorGame(int N) {
        //如果N = 1 AC无法操作 AC失败
        //如果N = 2 Ac操作1 通过N=1得知 BB失败
        //如果N = 3 Ac操作1 通过N=2得知 AC失败
        //如果N = 4 Ac操作1 通过N=3得知 BB失败
        //         Ac操作2 通过N=2得知 Ac失败
        //....
        //猜想 谁先操作奇数 谁就会失败

        return N % 2 == 0;
    }


    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
     * 注意：你不能在买入股票前卖出股票。
     */
    public int maxProfit(int[] prices) {
        //记录最低股票价格
        int minPrice = Integer.MAX_VALUE;
        //记录最大利润
        int maxPrice = 0;
        for (int price : prices) {
            //获取最低价格
            minPrice = Math.min(price, minPrice);

            //获取最高利润
            maxPrice = Math.max(maxPrice, price - minPrice);
        }
        return maxPrice;
    }

    /**
     * 数组的每个索引作为一个阶梯，第 i个阶梯对应着一个非负数的体力花费值 cost[i](索引从0开始)。
     * 每当你爬上一个阶梯你都要花费对应的体力花费值，然后你可以选择继续爬一个阶梯或者爬两个阶梯。
     * 您需要找到达到楼层顶部的最低花费。在开始时，你可以选择从索引为 0 或 1 的元素作为初始阶梯。
     */
    public int minCostClimbingStairs(int[] cost) {
        //如果我们已经到达了楼层顶部
        //下楼需要的体力 则是我们上楼需要的体力
        //记录走一步 或 走两步花费的体力
        int p1 = 0;
        int p2 = 0;
        int size = cost.length - 1;

        for (int i = size; i >= 0; i--) {
            int t1 = Math.min(p1, p2);
            int t2 = t1 + cost[i];
            p1 = p2;
            p2 = t2;
        }

        return Math.min(p1, p2);
    }

    /**
     * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
     * 要求时间复杂度为O(n)。
     */
    public int maxSubArray(int[] nums) {
        int maxNum = nums[0];
        for (int i = 1, size = nums.length; i < size; i++) {

            //如果 前面的数据小于0
            //直接判断最大值
            if (nums[i - 1] >= 0) {
                nums[i] = nums[i - 1] + nums[i];
            }
            maxNum = Math.max(maxNum, nums[i]);

        }
        return maxNum;
    }

    /**
     * 三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模1000000007。
     */
    public int waysToStep(int n) {
        int p1 = 1;
        if (n < 2)
            return p1;
        int p2 = 2;
        if (n < 3)
            return p2;
        long p3 = 4;
        if (n < 4)
            return (int) p3;

        for (int i = 4; i <= n; i++) {
            long pi = (p1 % 1000000007 + p2 % 1000000007 + p3 % 1000000007) % 1000000007;
            p1 = p2 % 1000000007;
            p2 = (int) (p3 % 1000000007);
            p3 = pi;
        }

        return (int) p3;
    }

    /**
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
     * 进阶：
     * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
     */
    public boolean isSubsequence(String s, String t) {
        if ("".equals(s))
            return true;
        if (s.length() > t.length())
            return false;

        int index1 = 0;
        int index2 = 0;

        int size1 = s.length();
        int size2 = t.length();

        while ((size1 - index1) <= (size2 - index2)) {
            while (
                    (size1 - index1) <= (size2 - index2)
                            && index2 < size2
                            && t.charAt(index2) != s.charAt(index1)) {
                index2++;
            }
            if ((size1 - index1) > (size2 - index2) || index2 >= size2)
                return false;
            if (t.charAt(index2) == s.charAt(index1)) {
                index2++;
                index1++;
            }
            if (index1 >= size1)
                return true;
        }

        return false;
    }


}
