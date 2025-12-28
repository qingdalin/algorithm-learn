package algorithm.class67dp2;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-18 10:39
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 * https://leetcode.cn/problems/minimum-path-sum/description/
 */
public class MinPathSum {
    public static int[] move = new int[] {1, 0, 1};
    public int minPathSum1(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        // int[][] ans = new int[n][m];
        int ans = 0;
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                if (x == 0 && y == 0) {
                    ans = grid[x][y];
                } else if (x > 0 && y > 0){
                    grid[x][y] += Math.min(grid[x - 1][y], grid[x][y - 1]);
                } else if (x > 0) {
                    grid[x][y] += grid[x - 1][y];
                } else {
                    grid[x][y] += grid[x][y - 1];
                }
                ans = grid[x][y];
            }
        }
        return ans;
    }

    public int minPathSum3(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j] = -1;
            }
        }
        return f2(grid, n - 1, m - 1, dp);
    }

    public int f2(int[][] grid, int i, int j, int[][] dp) {
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int up = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            up = f2(grid, i - 1, j, dp);
        }
        if (j - 1 >= 0) {
            left = f2(grid, i, j - 1, dp);
        }
        dp[i][j] = grid[i][j] + Math.min(up, left);
        return dp[i][j];
    }

    public int f1(int[][] grid, int i, int j) {
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        int up = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            up = f1(grid, i - 1, j);
        }
        if (j - 1 >= 0) {
            left = f1(grid, i, j - 1);
        }
        return grid[i][j] + Math.min(up, left);
    }

    public int minPathSum4(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[0][i] = grid[0][i] + dp[0][i - 1];
        }
        for (int i = 1; i < n; i++) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }
        for (int i = 1; i < n; i++) {
            // 可以提取出来 dp[i][0] = grid[i][0] + dp[i - 1][0];
            for (int j = 1; j < m; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[n - 1][m - 1];
    }

    /**
     * 空间压缩技巧
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[] dp = new int[m];
        dp[0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i] = grid[0][i] + dp[i - 1];
        }
        for (int i = 1; i < n; i++) {
            dp[0] = grid[i][0] + dp[0];
            for (int j = 1; j < m; j++) {
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
            }
        }
        return dp[m - 1];
    }
}
