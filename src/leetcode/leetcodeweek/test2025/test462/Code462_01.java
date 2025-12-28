package leetcode.leetcodeweek.test2025.test462;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/10 8:59
 * https://leetcode.cn/contest/weekly-contest-462/problems/flip-square-submatrix-vertically/description/
 */
public class Code462_01 {
    public int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {
        int n = Math.min(x + k - 1, grid.length - 1);
        int m = Math.min(y + k - 1, grid[0].length - 1);
        for (int up = x, down = n; up <= down; up++, down--) {
            int tmp = 0;
            for (int j = y; j <= m; j++) {
                tmp = grid[up][j];
                grid[up][j] = grid[down][j];
                grid[down][j] = tmp;
            }
        }
        return grid;
    }
}
