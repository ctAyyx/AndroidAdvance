package com.ct.design.alg;

import java.util.HashMap;
import java.util.Map;

public class DynamicRules {

    public static void main(String[] args) {
        new DynamicRules().run();
    }

    private void run() {
        int[] arr = new int[]{2, 3, 5};
        //System.out.println("筹零钱的最小次数:" + coinChange3(arr, 13));


        arr = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println("股票的最大利润:" + maxProfit01(arr));
        arr = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println("股票的最大利润:" + maxProfit02(arr));
        arr = new int[]{3, 3, 5, 0, 0, 3, 1, 4};
        System.out.println("股票的最大利润:" + maxProfit03(arr));

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
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    public int maxProfit2(int[] prices) {
        return 0;
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


    /**
     * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
     * 你可以认为每种硬币的数量是无限的。
     * |-- amount =0  0
     * f(amount)=|-- amount <0  -1
     * |-- min{f(amount-coin)+1|coin in coins} n>0
     * <p>
     * 现在这个解法只是列出了动态转移方程 还是属于暴力解法
     */
    public int coinChange(int[] coins, int amount) {
        if (amount < 0)
            return -1;
        if (amount == 0)
            return 0;
        int count = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subCount = coinChange(coins, amount - coin);
            if (subCount == -1)
                continue;
            count = Math.min(count, subCount + 1);
        }
        if (count == Integer.MAX_VALUE)
            count = -1;
        return count;
    }

    /**
     * 加入备忘录模式 解决重复计算的问题
     */
    private Map<Integer, Integer> map = new HashMap<>();

    public int coinChange2(int[] coins, int amount) {
        Integer result = map.get(amount);
        if (result != null)
            return result;

        if (amount < 0)
            return -1;
        if (amount == 0)
            return 0;
        int count = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subCount = coinChange(coins, amount - coin);
            if (subCount == -1)
                continue;
            count = Math.min(count, subCount + 1);
        }
        if (count == Integer.MAX_VALUE)
            count = -1;
        map.put(amount, count);
        return count;
    }


    /**
     * 以上 问题还可以使用 迭代法
     */
    public int coinChange3(int[] coins, int amount) {
        if (amount < 0)
            return -1;
        if (amount == 0)
            return 0;

        int[] arr = new int[amount + 1];

        for (int i = 0; i <= amount; i++) {
            if (i > 0)
                arr[i] = amount + 1;
            for (int coin : coins) {
                if (i - coin < 0) {
                    continue;
                }
                arr[i] = arr[i - coin] + 1;
            }
        }

        return arr[amount] == amount + 1 ? -1 : arr[amount];
    }


    /**
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     * <p>
     * 三维数组 dp[i][k][0或1]  i 表示第几天  k表示可以操作的次数  0表示没有股票 1表示持有股票
     * 这里我们把 卖出 才算成一次有效操作
     * --->
     * dp[i][k][0] =max{dp[i-1][k][0],dp[i-1][k][1]+prices[i]}
     * dp[i][k][1]=max{dp[i-1][k][1],dp[i-1][k-1][0]-prices[i]}
     */
    public int maxProfit01(int[] prices) {
        //这里的k =1
        //状态转移方程: dp[i][0]=max{dp[i-1][0],dp[i-1][1]+prices[i]}
        //            dp[i][1]=max{dp[i-1][1],-prices[i]}

        int n = prices.length;
        int[][] dp = new int[n][2];

//        for (int i = 0; i < n; i++) {
//            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
//            dp[i][1] = Math.max(dp[i - 1][1],  - prices[i]);
//        }


        //现在需要解决 i=0的时候 i-1是不行的
//        for (int i = 0; i < n; i++) {
//            if (i - 1 == -1) {
//                //没有持有股票 利润为0
//                dp[i][0] = 0;
//                //持有股票 利润 负的股票价格
//                dp[i][1] = -prices[i];
//                continue;
//            }
//
//
//            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
//            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
//        }
//
//        return dp[n - 1][0];

        //新的值 只和前一个状态有关
        //dp[-1][0] = 0
        int dp_i_0 = 0;
        //dp[-1][1]=Integer.MIN_VALUE
        int dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, -prices[i]);
        }

        return dp_i_0;

    }

    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    public int maxProfit02(int[] prices) {
        //这里 k=无穷大
        //状态转移方程: dp[i][k][0]=max{dp[i-1][k][0],dp[i-1][k][1]+prices[i]}
        //            dp[i][k][1]=max{dp[i-1][k][1],dp[i-1][k-1][0]-prices[i]}
        // 因为 k无穷大 所以 k = k-1是没有变化的;
        //             dp[i][0] = max{dp[i-1][0],dp[i-1][1]+prices[i]}
        //             dp[i][1]=max{dp[i-1][1],dp[i-1][0]-prices[i]}

        int n = prices.length;
//        int[][] dp = new int[n][2];
//        for (int i = 0; i < n; i++) {
//            if (i - 1 == -1) {
//                dp[i][0] = 0;
//                dp[i][1] = -prices[i];
//                continue;
//            }
//
//            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
//            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
//
//        }
//
//        return dp[n - 1][0];


        //优化  当前状态只和前一个状态有关
        int dp_i_0 = 0;
        int dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int pre = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, pre - prices[i]);
        }

        return dp_i_0;
    }

    /**
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    public int maxProfit03(int[] prices) {
        //这里 k=2
        //状态转移方程: dp[i][k][0]=max{dp[i-1][k][0],dp[i-1][k][1]+prices[i]}
        //            dp[i][k][1]=max{dp[i-1][k][1],dp[i-1][k-1][0]-prices[i]}

        int n = prices.length;
        int maxK = 2;
        int[][][] dp = new int[n][maxK + 1][2];

        for (int i = 0; i < n; i++) {
            for (int k = maxK; k >= 1; k--) {
                if (i - 1 == -1) {
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                    continue;
                }

                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }

        }

        return dp[n - 1][maxK][0];
    }




}
