package algorithm.class82dp17;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/7 13:34
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/description/
 */
public class MaxProfit3 {
    public int maxProfit1(int[] prices) {
        int n = prices.length;
        int[] dp1 = new int[n];
        // 在0.....i范围上进行一次交易，不要求在i时刻卖出，最大利润
        for (int i = 1, min = prices[0]; i < n; i++) {
            min = Math.min(min, prices[i]);
            // 卖出i和不卖出i两种可能性取最大
            dp1[i] = Math.max(dp1[i - 1], prices[i] - min);
        }
        int[] dp2 = new int[n];
        // 在0.....i范围上进行2次交易，要求在i时刻卖出，最大利润
        int ans = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                // 枚举0-i上所有卖出的可能，  在0-j卖出股票的最大值+i - j范围卖出股票的值
                dp2[i] = Math.max(dp2[i], dp1[j] + prices[i] - prices[j]);
            }
            ans = Math.max(ans, dp2[i]);
        }
        return ans;
    }

    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int[] dp1 = new int[n];
        // 在0.....i范围上进行一次交易，不要求在i时刻卖出，最大利润
        for (int i = 1, min = prices[0]; i < n; i++) {
            min = Math.min(min, prices[i]);
            // 卖出i和不卖出i两种可能性取最大
            dp1[i] = Math.max(dp1[i - 1], prices[i] - min);
        }
        int[] best = new int[n];
        best[0] = dp1[0] - prices[0];
        for (int i = 1; i < n; i++) {
            best[i] = Math.max(best[i - 1], dp1[i] - prices[i]);
        }
        int[] dp2 = new int[n];
        // 在0.....i范围上进行2次交易，要求在i时刻卖出，最大利润
        int ans = 0;
        for (int i = 1; i < n; i++) {
//            for (int j = 0; j <= i; j++) {
//                // 枚举0-i上所有卖出的可能，  在0-j卖出股票的最大值+i - j范围卖出股票的值
//                // dp1[j] - prices[j]的最大值，提前生成
//                dp2[i] = Math.max(dp2[i], dp1[j] + prices[i] - prices[j]);
//            }
            dp2[i] = prices[i] + best[i];
            ans = Math.max(ans, dp2[i]);
        }
        return ans;
    }

    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[] dp1 = new int[n];
        int[] best = new int[n];
        best[0] = dp1[0] - prices[0];
        int[] dp2 = new int[n];
        // 在0.....i范围上进行2次交易，要求在i时刻卖出，最大利润
        int ans = 0;
        // 在0.....i范围上进行一次交易，不要求在i时刻卖出，最大利润
        for (int i = 1, min = prices[0]; i < n; i++) {
            min = Math.min(min, prices[i]);
            // 卖出i和不卖出i两种可能性取最大
            dp1[i] = Math.max(dp1[i - 1], prices[i] - min);
            best[i] = Math.max(best[i - 1], dp1[i] - prices[i]);
            dp2[i] = prices[i] + best[i];
            ans = Math.max(ans, dp2[i]);
        }
        return ans;
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;
        // int[] dp1 = new int[n];
        int dp1 = 0;
        // int[] best = new int[n];
        int best = dp1 - prices[0];
        // int[] dp2 = new int[n];
        // int dp2 = 0;
        // 在0.....i范围上进行2次交易，要求在i时刻卖出，最大利润
        int ans = 0;
        // 在0.....i范围上进行一次交易，不要求在i时刻卖出，最大利润
        for (int i = 1, min = prices[0]; i < n; i++) {
            min = Math.min(min, prices[i]);
            // 卖出i和不卖出i两种可能性取最大
            dp1 = Math.max(dp1, prices[i] - min);
            best = Math.max(best, dp1 - prices[i]);
            // dp2 = prices[i] + best;
            ans = Math.max(ans, prices[i] + best);
        }
        return ans;
    }
}
