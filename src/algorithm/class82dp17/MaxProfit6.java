package algorithm.class82dp17;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/7 15:27
 * 给定一个整数数组prices，其中第  prices[i] 表示第 i 天的股票价格 。
 *
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 *
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/
 */
public class MaxProfit6 {
    public int maxProfit1(int[] prices) {
        int n = prices.length;
        if (n < 2) {
            return 0;
        }
        // 在0-i范围上，可以交易无限次，但必须减去一次购买，获得的最大收益
        int[] prepare = new int[n];
        prepare[1] = Math.max(-prices[0], -prices[1]);
        // 在0-i范围上，可以交易无限次 获得的最大收益
        int[] done = new int[n];
        done[1] = Math.max(0, prices[1] - prices[0]);
        for (int i = 2; i < n; i++) {
            done[i] = Math.max(done[i - 1], prepare[i - 1] + prices[i]);
            prepare[i] = Math.max(prepare[i - 1], done[i - 2] -  prices[i]);
        }
        return done[n - 1];
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n < 2) {
            return 0;
        }
        // 在0-i范围上，可以交易无限次，但必须减去一次购买，获得的最大收益
        // int[] prepare = new int[n];
        int prepare = Math.max(-prices[0], -prices[1]);
        // 在0-i范围上，可以交易无限次 获得的最大收益
        // int[] done = new int[n];
        int done1 = Math.max(0, prices[1] - prices[0]);
        int done2 = 0;
        for (int i = 2, curDone; i < n; i++) {
            curDone = Math.max(done1, prepare + prices[i]);
            prepare = Math.max(prepare, done2 -  prices[i]);
            done2 = done1;
            done1 = curDone;
        }
        return done1;
    }
}
