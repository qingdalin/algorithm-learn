package algorithm.class58floodfill;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-19 20:19
 * https://leetcode.cn/problems/bricks-falling-when-hit/
 * 洪水填充和并查集都可以，打砖块
 */
public class HitBricks {
    public static int n, m;
    public static int[][] g;
    public int[] hitBricks(int[][] grid, int[][] hits) {
        n = grid.length;
        m = grid[0].length;
        g = grid;
        int[] ans = new int[hits.length];
        // 1.将所有炮弹地方减一
        for (int[] hit : hits) {
            g[hit[0]][hit[1]]--;
        }
        // 2.将天花板都和相连的改为2
        for (int i = 0; i < m; i++) {
            dfs(0, i);
        }
        // 3.时光倒流
        for (int i = hits.length - 1, row, col; i >= 0; i--) {
            row = hits[i][0];
            col = hits[i][1];
            // 3.1将炮弹地方加1，如果为1，并且为（天花 或者 上下左右有2）计算所有的1改为2，将结果减一，就是答案
            g[row][col]++;
            if (worth(row, col)) {
                ans[i] += dfs(row, col) - 1;
            }
        }
        return ans;
    }
    public boolean worth(int i, int j) {
        return g[i][j] == 1 &&
            (i == 0
                || i > 0 && g[i - 1][j] == 2
                || i < n - 1 && g[i + 1][j] == 2
                || j > 0 && g[i][j - 1] == 2
                || j < m - 1 && g[i][j +1] == 2);
    }
    public int dfs(int i, int j) {
        if (i < 0 || i == n || j < 0 || j == m || g[i][j] != 1) {
            return 0;
        }
        g[i][j] = 2;
        return 1 + dfs(i - 1, j) + dfs(i + 1, j) + dfs(i, j - 1) + dfs(i, j + 1);
    }
}
