package algorithm.class76dp11;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-22 13:08
 */
public class MinScoreTriangulation {
    public int minScoreTriangulation1(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        return f1(values, 0, n - 1, dp);
    }

    private int f1(int[] values, int l, int r, int[][] dp) {
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        int ans = Integer.MAX_VALUE;
        if (l == r || l + 1 == r) {
            return 0;
        } else {
            // 从l到 m的最小值，加上m到r上的最小值，加上当前三个定点
            for (int m = l + 1; m < r; m++) {
                ans = Math.min(ans, f1(values, l, m, dp) + f1(values, m, r, dp) + values[l] * values[r] * values[m]);
            }
        }
        dp[l][r] = ans;
        return ans;
    }

    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                dp[l][r] = Integer.MAX_VALUE;
                for (int m = l + 1; m < r; m++) {
                    dp[l][r] = Math.min(dp[l][r], dp[l][m] + dp[m][r] + values[l] * values[r] * values[m]);
                }
            }
        }
        return dp[0][n - 1];
    }
}
