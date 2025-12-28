package leetcode.leetcodeweek.test2025.test449;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/11 9:50
 * https://leetcode.cn/contest/weekly-contest-449/problems/equal-sum-grid-partition-i/
 */
public class Code02 {
    // 1 4
    // 2 3
    // 1 5
    // 3 10,
    public static boolean canPartitionGrid(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        getSum(grid, n, m);
        for (int j = 0; j < m; j++) {
            if (2 * grid[n - 1][j] == grid[n - 1][m - 1]) {
                return true;
            }
        }
        for (int i = 0; i < n; i++) {
            if (2 * grid[i][m - 1] == grid[n - 1][m - 1]) {
                return true;
            }
        }
        return false;
    }

    private static void getSum(int[][] grid, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 左+上+自己-左上
                int sum = 0;
                if (i - 1 >= 0) {
                    sum += grid[i - 1][j];
                }
                if (j - 1 >= 0) {
                    sum += grid[i][j - 1];
                }
                if (i - 1 >= 0 && j - 1 >= 0) {
                    sum -= grid[i - 1][j - 1];
                }
                grid[i][j] += sum;
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid = new int[][] {{1,4}, {2,3}};
        System.out.println(canPartitionGrid(grid));
    }
}
