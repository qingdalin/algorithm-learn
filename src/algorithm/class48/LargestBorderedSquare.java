package algorithm.class48;

/**
 * https://leetcode.cn/problems/largest-1-bordered-square/description/
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-02-03 15:47
 */
public class LargestBorderedSquare {
    public int largest1BorderedSquare(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        build(grid, n, m);
        if (sum(grid, 0, 0, n - 1, m - 1) == 0) {
            return 0;
        }
        int ans = 1;
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < m; b++) {
                for(int c = a + ans, d = b + ans, k = ans + 1; c < n && d < m; c++, d++, k++) {
                    if (sum(grid, a, b, c, d) - sum(grid, a + 1, b + 1, c - 1, d - 1) == (k - 1) * 4) {
                        ans = k;
                    }
                }
            }
        }
        return ans * ans;
    }

    private int sum(int[][] grid, int a, int b, int c, int d) {
        return a > c ? 0 : get(grid, c, d) - get(grid, c, b - 1) - get(grid, a - 1, d) + get(grid, a - 1, b - 1);
    }

    private void build(int[][] grid, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] += get(grid, i, j - 1) + get(grid, i - 1, j) - get(grid, i - 1, j - 1);
            }
        }
    }

    private int get(int[][] grid, int i, int j) {
        return (i < 0 || j < 0) ? 0 : grid[i][j];
    }
}
