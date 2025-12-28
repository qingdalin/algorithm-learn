package leetcode.leetcodeweek.test2025.test463;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 9:08
 * https://leetcode.cn/contest/weekly-contest-463/problems/best-time-to-buy-and-sell-stock-using-strategy/
 */
public class Code463_01 {
    public static long maxProfit(int[] prices, int[] strategy, int k) {
        int n = prices.length;
        long[] sum = new long[(n + 1) / 2];
        int cnt = 0;
        for (int i = 0; i < n; i += 2) {
            if (strategy[i] == -1) {
                prices[i] *= -1;
            }
            sum[cnt] = prices[i];
            if (i + 1 < n && strategy[i + 1] == -1) {
                prices[i + 1] *= -1;
            }
            if (i + 1 < n) {
                sum[cnt] += prices[i + 1];
            }
            cnt++;
        }
        long ans = 0;
        long pre = sum[0];
        for (int i = 1; i < cnt; i++) {
            pre = Math.min(pre, prices[i] + pre);
            ans = Math.min(ans, pre);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr1 = {4,2,8};
        int[] arr2 = {-1,0,1};
        int k = 2;
        System.out.println(maxProfit(arr1, arr2, k));
    }
}
