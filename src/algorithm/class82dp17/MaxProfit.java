package algorithm.class82dp17;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/7 11:30
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/">...</a>
 */
public class MaxProfit {
    public int maxProfit(int[] prices) {
        int ans = 0;
        for (int i = 1, min = prices[0]; i < prices.length; i++) {
            //  0 - i 范围最小值
            min = Math.min(min, prices[i]);
            // 每个时刻都算个答案
            ans = Math.max(ans, prices[i] - min);
        }
        return ans;
    }
}
