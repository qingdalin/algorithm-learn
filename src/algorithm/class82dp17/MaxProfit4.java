package algorithm.class82dp17;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/7 14:34
 * 给你一个整数数组 prices 和一个整数 k ，其中 prices[i] 是某支给定的股票在第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。也就是说，你最多可以买 k 次，卖 k 次。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/description/
 * 如果k足够大，和买卖股票2类似
 */
public class MaxProfit4 {
    public int free(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            ans += Math.max(0, prices[i] - prices[i - 1]);
        }
        return ans;
    }
    public int maxProfit1(int k, int[] prices) {
        int n = prices.length;
        if (k >= n / 2) {
            return free(prices);
        }
        int[][] dp = new int[k + 1][n];
        // dp[i][j]: 必须交易i次，在0.j范围上，最大利润
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i][j - 1];// 第j天不参与交易
                for (int p = 0; p < j; p++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][p] + prices[j] - prices[p]);
                }
            }
        }
        return dp[k][n - 1];
    }

    public int maxProfit2(int k, int[] prices) {
        int n = prices.length;
        if (k >= n / 2) {
            return free(prices);
        }
        int[][] dp = new int[k + 1][n];
        // dp[i][j]: 必须交易i次，在0.j范围上，最大利润
        for (int i = 1, best; i <= k; i++) {
            best = dp[i - 1][0] - prices[0];
            for (int j = 1; j < n; j++) {
//                dp[i][j] = dp[i][j - 1];// 第j天不参与交易
//                for (int p = 0; p < j; p++) {
//                    // 求dp[i - 1][p] - prices[p]的最大值
//                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][p] + prices[j] - prices[p]);
//                }
                dp[i][j] = Math.max(dp[i][j - 1], best + prices[j]);
                best = Math.max(best, dp[i - 1][j] - prices[j]);
            }
        }
        return dp[k][n - 1];
    }

    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (k >= n / 2) {
            return free(prices);
        }
        int[] dp = new int[n];
        // dp[i][j]: 必须交易i次，在0.j范围上，最大利润
        for (int i = 1, best, tmp; i <= k; i++) {
            best = dp[0] - prices[0];
            for (int j = 1; j < n; j++) {
                tmp = dp[j];
                dp[j] = Math.max(dp[j - 1], best + prices[j]);
                best = Math.max(best, tmp - prices[j]);
            }
        }
        return dp[n - 1];
    }
}
