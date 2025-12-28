package algorithm.class67dp2;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-19 11:22
 * 给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度。
 *
 * 对于每个单元格，你可以往上，下，左，右四个方向移动。 你 不能 在 对角线 方向上移动或移动到 边界外（即不允许环绕）。
 * https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/description/
 */
public class LongestIncreasingPath {
    public int longestIncreasingPath1(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, f(grid, i, j));
            }
        }
        return ans;
    }

    public int f(int[][] grid, int i, int j) {
        int next = 0;
        if (i > 0 && grid[i - 1][j] > grid[i][j]) {
            next = Math.max(next, f(grid, i - 1, j));
        }
        if (i < grid.length - 1 && grid[i + 1][j] > grid[i][j]) {
            next = Math.max(next, f(grid, i + 1, j));
        }
        if (j > 0 && grid[i][j - 1] > grid[i][j]) {
            next = Math.max(next, f(grid, i, j - 1));
        }
        if (j < grid[0].length - 1 && grid[i][j + 1] > grid[i][j]) {
            next = Math.max(next, f(grid, i, j + 1));
        }
        return next + 1;
    }

    public int longestIncreasingPath(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int ans = 0;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, f1(grid, i, j, dp));
            }
        }
        return ans;
    }

    public int f1(int[][] grid, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        int next = 0;
        if (i > 0 && grid[i - 1][j] > grid[i][j]) {
            next = Math.max(next, f1(grid, i - 1, j, dp));
        }
        if (i < grid.length - 1 && grid[i + 1][j] > grid[i][j]) {
            next = Math.max(next, f1(grid, i + 1, j, dp));
        }
        if (j > 0 && grid[i][j - 1] > grid[i][j]) {
            next = Math.max(next, f1(grid, i, j - 1, dp));
        }
        if (j < grid[0].length - 1 && grid[i][j + 1] > grid[i][j]) {
            next = Math.max(next, f1(grid, i, j + 1, dp));
        }
        dp[i][j] = next + 1;
        return next + 1;
    }
}
