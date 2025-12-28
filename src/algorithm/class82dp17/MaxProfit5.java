package algorithm.class82dp17;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/7 15:07
 * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
 *
 * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 *
 * 返回获得利润的最大值。
 *
 * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/description/
 */
public class MaxProfit5 {
    public int maxProfit(int[] prices, int fee) {
        int prepare = -prices[0] - fee;
        int done = 0;
        for (int i = 1; i < prices.length; i++) {
            // 当前i位置不交易，交易的最大值
            done = Math.max(done, prepare + prices[i]);
            prepare = Math.max(prepare, done - prices[i] - fee);
        }
        return done;
    }
}
