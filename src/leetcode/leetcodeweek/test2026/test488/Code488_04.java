package leetcode.leetcodeweek.test2026.test488;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/2/8 10:35
 * https://leetcode.cn/problems/maximum-score-using-exactly-k-pairs/solutions/3898739/zi-xu-lie-dpcong-ji-yi-hua-sou-suo-dao-d-y9aw/
 */
public class Code488_04 {
    public long maxScore(int[] nums1, int[] nums2, int knum) {
        int n = nums1.length, m = nums2.length;
        long[][][] dp = new long[knum + 1][n + 1][m + 1];
        for (int i = 1; i < knum + 1; i++) {
            for (int j = 0; j <= n; j++) {
                for (int l = 0; l <= m; l++) {
                    dp[i][j][l] = Long.MIN_VALUE;
                }
            }
        }
        for (int k = 1; k <= knum; k++) {
            for (int i = k - 1; i < n; i++) {
                for (int j = k - 1; j < m; j++) {
                    dp[k][i + 1][j + 1] = Math.max(Math.max(dp[k][i][j + 1], dp[k][i + 1][j]),
                        dp[k - 1][i][j] + ((long) nums1[i] * nums2[j]));
                }
            }
        }
        return dp[knum][n][m];
    }


    public long maxScore1(int[] nums1, int[] nums2, int k) {
        int n = nums1.length, m = nums2.length;
        long[][][] dp = new long[k + 1][n][m];
        for (int i = 0; i < k + 1; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l < m; l++) {
                    dp[i][j][l] = Long.MIN_VALUE;
                }
            }
        }
        return dfs(k, n - 1, m - 1, nums1, nums2, dp);
    }

    private long dfs(int k, int i, int j, int[] nums1, int[] nums2, long[][][] dp) {
        if (k == 0) {
            // 选完了
            return 0;
        }
        if (i + 1 < k || j + 1 < k) {
            // 剩余不足k个元素
            return Long.MIN_VALUE;
        }
        if (dp[k][i][j] != Long.MIN_VALUE) {
            return dp[k][i][j];
        }
        long p1 = dfs(k, i - 1, j, nums1, nums2, dp);
        long p2 = dfs(k, i, j - 1, nums1, nums2, dp);
        long p3 = dfs(k - 1, i - 1, j - 1, nums1, nums2, dp) + ((long) nums1[i] * nums2[j]);
        long ans = Math.max(p1, Math.max(p2, p3));
        dp[k][i][j] = ans;
        return ans;
    }
}
