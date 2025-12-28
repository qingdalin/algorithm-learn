package algorithm.class127;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/27 19:16
 * // 摘樱桃
 * // 给定一个n*n的正方形矩阵grid，每个格子值只有三种-1、0、1
 * // -1表示格子不能走、0表示格子可以走但是没有樱桃、1表示格子可以走且有一颗樱桃
 * // 你的目标是从左上角走到右下角，每一步只能 向下 或者 向右
 * // 然后从右下角走回左上角，每一步只能 向上 或者 向左
 * // 这个过程中，想尽量多的获得樱桃，但是有樱桃的格子，只能捡一次
 * // 返回最多能获得多少樱桃，如果不存在通路返回0
 * // 测试链接 : https://leetcode.cn/problems/cherry-pickup/
 * // 这里只讲述核心思路
 * // 有兴趣同学自己改出严格位置依赖的动态规划
 * // 有兴趣同学自己改出空间压缩版本的动态规划
 * // 之前的课大量涉及，这里不再赘述
 */
public class Code01_CherryPickup {
    public static int cherryPickup1(int[][] grid) {
        int n = grid.length;
        int[][][] dp = new int[n][n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    dp[i][j][k] = -2;
                }
            }
        }
        int ans = f(grid, 0, 0, 0, n, dp);
        return ans == -1 ? 0 : ans;
    }

    // A来到当前点(a, b)
    // B来到当前点(c, d), d = a + b - c
    // A和B有一个来到-1，返回-1
    // A和B来到同一个樱桃点，返回1
    // A和B来到不同樱桃点，返回2
    private static int f(int[][] grid, int a, int b, int c, int n, int[][][] dp) {
        int d = a + b - c;
        if (a == n || b == n || c == n || d == n || grid[a][b] == -1 || grid[c][d] == -1) {
            return -1;
        }
        if (a == n - 1 && b == n - 1) {
            return grid[a][b];
        }
        if (dp[a][b][c] != -2) {
            return dp[a][b][c];
        }
        int get = a == c && b == d ? grid[a][b] : (grid[a][b] + grid[c][d]);
        int next = f(grid, a + 1, b, c + 1, n, dp);
        next = Math.max(next, f(grid, a + 1, b, c, n, dp));
        next = Math.max(next, f(grid, a, b + 1, c + 1, n, dp));
        next = Math.max(next, f(grid, a, b + 1, c, n, dp));
        int ans = -1;
        if (next != -1) {
            ans = get + next;
        }
        dp[a][b][c] = ans;
        return ans;
    }

    public static int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][][] dp = new int[n][n][n];
        for (int c = 0; c < n; c++) {
            int d = n - 1 + n - 1 - c;
            dp[n - 1][n - 1][c] = grid[c][d];
        }
        for (int a = n - 2; a >= 0; a--) {
            for (int b = n - 2; b >= 0; b--) {
                for (int c = 0; c < n; c++) {
                    int d = a + b - c;
                    int ans;
                    int get = a == c && b == d ? grid[a][b] : (grid[a][b] + grid[c][d]);
                    int next = dp[a + 1][b][c + 1];
                    next = Math.max(next, dp[a + 1][b][c]);
                    next = Math.max(next, dp[a][b + 1][c + 1]);
                    next = Math.max(next, dp[a][b + 1][c]);
                    ans = -1;
                    if (next != -1) {
                        ans = get + next;
                    }
                    dp[a][b][c] = ans;
                }
            }
        }
        return dp[0][0][0] == -1 ? 0 : dp[0][0][0];
    }
}
