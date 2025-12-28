package algorithm.class74dp9;

import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-16 13:04
 * 一张桌子上总共有 n 个硬币 栈 。每个栈有 正整数 个带面值的硬币。
 *
 * 每一次操作中，你可以从任意一个栈的 顶部 取出 1 个硬币，从栈中移除它，并放入你的钱包里。
 *
 * 给你一个列表 piles ，其中 piles[i] 是一个整数数组，分别表示第 i 个栈里 从顶到底 的硬币面值。同时给你一个正整数 k ，
 * 请你返回在 恰好 进行 k 次操作的前提下，你钱包里硬币面值之和 最大为多少 。
 * https://leetcode.cn/problems/maximum-value-of-k-coins-from-piles/description/
 */
public class MaxValueOfCoins {
    public int maxValueOfCoins1(List<List<Integer>> piles, int m) {
        int n = piles.size();
        // dp[i][j]: 表示第i组硬币为j个的时候最大价值
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            List<Integer> team = piles.get(i - 1);
            int t = Math.min(team.size(), m);
            // 每组的前缀和
            int[] preSum = new int[t + 1];
            for (int j = 0, sum = 0; j < t; j++) {
                sum += team.get(j);
                preSum[j + 1] = sum;
            }
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                for (int k = 1; k <= Math.min(t, j); k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k] + preSum[k]);
                }
            }
        }
        return dp[n][m];
    }

    public int maxValueOfCoins(List<List<Integer>> piles, int m) {
        int n = piles.size();
        // dp[i][j]: 表示第i组硬币为j个的时候最大价值
        int[] dp = new int[m + 1];
        for (int i = 1; i <= n; i++) {
            List<Integer> team = piles.get(i - 1);
            int t = Math.min(team.size(), m);
            // 每组的前缀和
            int[] preSum = new int[t + 1];
            for (int j = 0, sum = 0; j < t; j++) {
                sum += team.get(j);
                preSum[j + 1] = sum;
            }
            for (int j = m; j >= 0; j--) {
                for (int k = 1; k <= Math.min(t, j); k++) {
                    dp[j] = Math.max(dp[j], dp[j - k] + preSum[k]);
                }
            }
        }
        return dp[m];
    }
}
