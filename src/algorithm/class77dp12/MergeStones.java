package algorithm.class77dp12;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-23 13:10
 * 有 n 堆石头排成一排，第 i 堆中有 stones[i] 块石头。
 *
 * 每次 移动 需要将 连续的 k 堆石头合并为一堆，而这次移动的成本为这 k 堆中石头的总数。
 *
 * 返回把所有石头合并成一堆的最低成本。如果无法合并成一堆，返回 -1 。
 * https://leetcode.cn/problems/minimum-cost-to-merge-stones/description/
 */
public class MergeStones {
    public int mergeStones(int[] stones, int k) {
        int n = stones.length;
        if ((n - 1) % (k - 1) != 0) {
            return -1;
        }
        // 求前缀和数组，l -- r的和 等于preSum[r+1] - preSum[l]
        int[] preSum = new int[n + 1];
        for (int i = 0, j = 1, sum = 0; i < n; i++, j++) {
            sum += stones[i];
            preSum[j] = sum;
        }
        int[][] dp = new int[n][n];
        for (int l = n - 2; l >= 0; l--) {
            for (int r = l + 1; r < n; r++) {
                int ans = Integer.MAX_VALUE;
                for (int m = l; m < r; m += (k - 1)) {
                    ans = Math.min(ans, dp[l][m] + dp[m + 1][r]);
                }
                if ((r - l) % (k - 1) == 0) {
                    ans += (preSum[r + 1] - preSum[l]);
                }
                dp[l][r] = ans;
            }
        }
        return dp[0][n - 1];
    }
}
