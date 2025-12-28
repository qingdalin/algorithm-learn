package algorithm.class69dp4;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-26 10:15
 * 给你一个下标从 0 开始的 m x n 整数矩阵 grid 和一个整数 k 。你从起点 (0, 0) 出发，每一步只能往 下 或者往 右 ，
 * 你想要到达终点 (m - 1, n - 1) 。
 *
 * 请你返回路径和能被 k 整除的路径数目，由于答案可能很大，返回答案对 109 + 7 取余 的结果。
 * https://leetcode.cn/problems/paths-in-matrix-whose-sum-is-divisible-by-k/description/
 */
public class NumberOfPaths {
    public static int mod = 1000000007;
    public int numberOfPaths1(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        return f1(grid, n, m, k, 0, 0, 0);
    }

    // 当前位置[i][j] % k 的余数为r
    private int f1(int[][] grid, int n, int m, int k, int i, int j, int r) {
        if (i == n - 1 && j == m - 1) {
            return grid[i][j] % k == r ? 1 : 0;
        }
        // 剩余还需要的模数
        int need = (k + r - grid[i][j] % k) % k;
        int ans = 0;
        if (i + 1 < n) {
            ans = f1(grid, n, m, k, i + 1, j, need);
        }
        if (j + 1 < m) {
            ans = (ans + f1(grid, n, m, k, i, j + 1, need)) % mod;
        }
        return ans;
    }

    public int numberOfPaths2(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        int[][][] dp = new int[n][m][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int l = 0; l < k; l++) {
                    dp[i][j][l] = -1;
                }
            }
        }
        return f2(grid, n, m, k, 0, 0, 0, dp);
    }

    // 当前位置[i][j] % k 的余数为r
    private int f2(int[][] grid, int n, int m, int k, int i, int j, int r, int[][][] dp) {
        if (i == n - 1 && j == m - 1) {
            return grid[i][j] % k == r ? 1 : 0;
        }
        if (dp[i][j][r] != -1) {
            return dp[i][j][r];
        }
        // 剩余还需要的模数
        int need = (k + r - grid[i][j] % k) % k;
        int ans = 0;
        if (i + 1 < n) {
            ans = f2(grid, n, m, k, i + 1, j, need, dp);
        }
        if (j + 1 < m) {
            ans = (ans + f2(grid, n, m, k, i, j + 1, need, dp)) % mod;
        }
        dp[i][j][r] = ans;
        return ans;
    }

    public int numberOfPaths3(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        int[][][] dp = new int[n][m][k];
        for (int l = 0; l < k; l++) {
            dp[n - 1][m - 1][l] = grid[n - 1][m -1] % k == l ? 1 : 0;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                for (int l = 0; l < k; l++) {
                    int need = (k + l - grid[i][j] % k) % k;
                    if (i + 1 < n) {
                        dp[i][j][l] = dp[i + 1][j][need];
                    }
                    if (j + 1 < m) {
                        dp[i][j][l] = (dp[i][j][l] + dp[i][j + 1][need]) % mod;
                    }
                }
            }
        }
        return dp[0][0][0];
    }

    public int numberOfPaths4(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        int[][][] dp = new int[n][m][k];
        for (int r = 0; r < k; r++) {
            dp[n - 1][m - 1][r] = grid[n - 1][m -1] % k == r ? 1 : 0;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                for (int r = 0; r < k; r++) {
                    int need = (k + r - grid[i][j] % k) % k;
                    if (i + 1 < n) {
                        dp[i][j][r] = dp[i + 1][j][need];
                    }
                    if (j + 1 < m) {
                        dp[i][j][r] = (dp[i][j][r] + dp[i][j + 1][need]) % mod;
                    }
                }
            }
        }
        return dp[0][0][0];
    }

    public int numberOfPaths(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        int[][][] dp = new int[n][m][k];
        dp[n - 1][m - 1][grid[n - 1][m -1] % k] = 1;
        for (int i = n - 2; i >= 0; i--) {
            for (int r = 0; r < k; r++) {
                dp[i][m - 1][r] = dp[i + 1][m - 1][(k + r - grid[i][m - 1] % k) % k];
            }
        }
        for (int j = m - 2; j >= 0; j--) {
            for (int r = 0; r < k; r++) {
                dp[n - 1][j][r] = dp[n - 1][j + 1][(k + r - grid[n - 1][j] % k) % k];
            }
        }
        for (int i = n - 2, need; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                for (int l = 0; l < k; l++) {
                    need = (k + l - grid[i][j] % k) % k;
                    dp[i][j][l] = dp[i + 1][j][need];
                    dp[i][j][l] = (dp[i][j][l] + dp[i][j + 1][need]) % mod;
                }
            }
        }
        return dp[0][0][0];
    }
}
