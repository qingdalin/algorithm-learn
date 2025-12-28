package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/1 19:55
 * https://leetcode.cn/problems/coin-change-ii/
 */
public class Leetcode518Change {
    public static int change1(int m, int[] arr) {
        int n = arr.length;
        // 前i种硬币，刚好凑m元的方法数
        // dp[i][j]: 前i种硬币，刚好凑j元的方法数
        // 要当前i号硬币: dp[i - 1][j - arr[i]] ..dp[i - 1][j - arr[i]]
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = -1;
            }
        }
        return dfs(arr, 0, 0, m, dp);
    }

    private static int dfs(int[] arr, int i, int sum, int m, int[][] dp) {
        if (i == arr.length) {
            return sum == m ? 1 : 0;
        }
        if (dp[i][sum] !=  -1) {
            return dp[i][sum];
        }
        int ans = dfs(arr, i + 1, sum, m, dp);
        int len = (m - sum) / arr[i];
        for (int j = 1; j <= len; j++) {
            if (sum + j * arr[i] <= m) {
                ans += dfs(arr, i + 1, sum + j * arr[i], m, dp);
            }
        }
        dp[i][sum] = ans;
        return ans;
    }

    public static int change2(int m, int[] arr) {
        int n = arr.length;
        // 前i种硬币，刚好凑m元的方法数
        // dp[i][j]: 前i种硬币，刚好凑j元的方法数
        // 要当前i号硬币: dp[i - 1][j - arr[i]] ..dp[i - 1][j - arr[i]]
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = -1;
            }
        }
        return dfs2(arr, 0,  m, dp);
    }

    private static int dfs2(int[] arr, int i, int m, int[][] dp) {
        if (i == arr.length) {
            return 0 == m ? 1 : 0;
        }
        if (dp[i][m] !=  -1) {
            return dp[i][m];
        }
        int ans = dfs2(arr, i + 1, m, dp);
        int len = m / arr[i];
        for (int j = 1; j <= len; j++) {
            if (m - j * arr[i] >= 0) {
                ans += dfs2(arr, i + 1, m - j * arr[i], dp);
            }
        }
        dp[i][m] = ans;
        return ans;
    }

    public static int change3(int m, int[] arr) {
        int n = arr.length;
        // 前i种硬币，刚好凑m元的方法数
        // dp[i][j]: 前i种硬币，刚好凑j元的方法数
        // 要当前i号硬币: dp[i - 1][j - arr[i]] ..dp[i - 1][j - arr[i]]
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                if (arr[i] > j) {
                    dp[i + 1][j] = dp[i][j];
                } else {
                    dp[i + 1][j] = dp[i][j] + dp[i + 1][j - arr[i]];
                }
            }
        }
        return dp[n][m];
    }

    public static int change(int m, int[] arr) {
        int n = arr.length;
        // 前i种硬币，刚好凑m元的方法数
        // dp[i][j]: 前i种硬币，刚好凑j元的方法数
        // 要当前i号硬币: dp[i - 1][j - arr[i]] ..dp[i - 1][j - arr[i]]
        int[] dp = new int[m + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = arr[i]; j <= m; j++) {
                dp[j] += dp[j - arr[i]];
            }
        }
        return dp[m];
    }
}
