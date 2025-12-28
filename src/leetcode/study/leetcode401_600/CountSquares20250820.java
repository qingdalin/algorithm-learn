package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/20 19:19
 * https://leetcode.cn/problems/count-square-submatrices-with-all-ones/?envType=daily-question&envId=2025-08-20
 */
public class CountSquares20250820 {
    public static int countSquares(int[][] arr) {
        int n = arr.length, m = arr[0].length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == 1 && i > 0 && j > 0) {
                    arr[i][j] += Math.min(arr[i - 1][j - 1], Math.min(arr[i - 1][j], arr[i][j - 1]));
                }
                ans += arr[i][j];
            }
        }
        return ans;
    }

    public static int countSquares1(int[][] arr) {
        int n = arr.length, m = arr[0].length;
        int[][] dp = new int[n + 1][m + 1];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == 1) {
                    dp[i + 1][j + 1] = Math.min(dp[i][j], Math.min(dp[i][j + 1], dp[i + 1][j])) + 1;
                    ans += dp[i + 1][j + 1];
                }
            }
        }
        return ans;
    }
}
