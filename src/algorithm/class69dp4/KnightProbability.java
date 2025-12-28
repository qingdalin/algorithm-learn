package algorithm.class69dp4;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-26 9:32
 * 在一个 n x n 的国际象棋棋盘上，一个骑士从单元格 (row, column) 开始，并尝试进行 k 次移动。行和列是 从 0 开始 的，所以左上单元格是 (0,0) ，右下单元格是 (n - 1, n - 1) 。
 *
 * 象棋骑士有8种可能的走法，如下图所示。每次移动在基本方向上是两个单元格，然后在正交方向上是一个单元格。
 * 每次骑士要移动时，它都会随机从8种可能的移动中选择一种(即使棋子会离开棋盘)，然后移动到那里。
 *
 * 骑士继续移动，直到它走了 k 步或离开了棋盘。
 *
 * 返回 骑士在棋盘停止移动后仍留在棋盘上的概率 。
 * https://leetcode.cn/problems/knight-probability-in-chessboard/description/
 */
public class KnightProbability {
    public double knightProbability1(int n, int k, int row, int column) {
        return f1(n, k, row, column);
    }

    private double f1(int n, int k, int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            return 0;
        }
        double ans = 0;
        if (k == 0) {
            ans = 1;
        } else {
            ans += f1(n, k - 1, i - 2, j - 1) / 8;
            ans += f1(n, k - 1, i - 2, j + 1) / 8;
            ans += f1(n, k - 1, i + 2, j + 1) / 8;
            ans += f1(n, k - 1, i + 2, j - 1) / 8;
            ans += f1(n, k - 1, i - 1, j - 2) / 8;
            ans += f1(n, k - 1, i - 1, j + 2) / 8;
            ans += f1(n, k - 1, i + 1, j + 2) / 8;
            ans += f1(n, k - 1, i + 1, j - 2) / 8;
        }
        return ans;
    }

    public double knightProbability2(int n, int k, int row, int column) {
        double[][][] dp = new double[n][n][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l <= k; l++) {
                    dp[i][j][l] = -1;
                }
            }
        }
        return f2(n, k, row, column, dp);
    }

    private double f2(int n, int k, int i, int j, double[][][] dp) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            return 0;
        }
        if (dp[i][j][k] != -1) {
            return dp[i][j][k];
        }
        double ans = 0;
        if (k == 0) {
            ans = 1;
        } else {
            ans += f2(n, k - 1, i - 2, j - 1, dp) / 8;
            ans += f2(n, k - 1, i - 2, j + 1, dp) / 8;
            ans += f2(n, k - 1, i + 2, j + 1, dp) / 8;
            ans += f2(n, k - 1, i + 2, j - 1, dp) / 8;
            ans += f2(n, k - 1, i - 1, j - 2, dp) / 8;
            ans += f2(n, k - 1, i - 1, j + 2, dp) / 8;
            ans += f2(n, k - 1, i + 1, j + 2, dp) / 8;
            ans += f2(n, k - 1, i + 1, j - 2, dp) / 8;
        }
        dp[i][j][k] = ans;
        return ans;
    }

    public double knightProbability(int n, int k, int row, int column) {
        double[][][] dp = new double[n][n][k + 1];
        for (int i = row; i < n; i++) {
            for (int j = column; j < n; j++) {
                dp[i][j][0] = 1;
                for (int l = 1; l <= k; l++) {
                    if (i - 2 >= 0) {
                        if (j - 1 >= 0) {
                            dp[i][j][l] += dp[i - 2][j - 1][l - 1] / 8;
                        }
                        if (j + 1 < n) {
                            dp[i][j][l] += dp[i - 2][j + 1][l - 1] / 8;
                        }
                    }
                    if (i + 2 < n) {
                        if (j - 1 >= 0) {
                            dp[i][j][l] += dp[i + 2][j - 1][l - 1] / 8;
                        }
                        if (j + 1 < n) {
                            dp[i][j][l] += dp[i + 2][j + 1][l - 1] / 8;
                        }
                    }
                    if (i - 1 >= 0) {
                        if (j - 2 >= 0) {
                            dp[i][j][l] += dp[i - 1][j - 2][l - 1] / 8;
                        }
                        if (j + 2 < n) {
                            dp[i][j][l] += dp[i - 1][j + 2][l - 1] / 8;
                        }
                    }
                    if (i + 1 < n) {
                        if (j - 2 >= 0) {
                            dp[i][j][l] += dp[i + 1][j - 2][l - 1] / 8;
                        }
                        if (j + 2 < n) {
                            dp[i][j][l] += dp[i + 1][j + 2][l - 1] / 8;
                        }
                    }
                }
            }
        }
        return dp[row][column][k];
    }
}
