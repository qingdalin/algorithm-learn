package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/13 8:43
 * https://leetcode.cn/problems/maximal-square/
 */
public class Leetcode221MaximalSquare {
    public static int maximalSquare(char[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        int[][] dp = new int[n][m];
        // dp[i][j]: 前i行前j列最大正方形面积是多少
        int max = Integer.MIN_VALUE;
        for (int j = 0; j < m; j++) {
            dp[0][j] = arr[0][j] == '1' ? 1 : 0;
            max = Math.max(max, dp[0][j]);
        }
        for (int i = 0; i < n; i++) {
            dp[i][0] = arr[i][0] == '1' ? 1 : 0;
            max = Math.max(max, dp[i][0]);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (arr[i][j] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][ j - 1]) + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max * max;
    }

    public static void main(String[] args) {
        char[][] arr = new char[][]{
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
        };
        System.out.println(maximalSquare(arr));
    }
}
