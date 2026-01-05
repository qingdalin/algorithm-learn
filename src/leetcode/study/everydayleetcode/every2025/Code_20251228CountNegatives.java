package leetcode.study.everydayleetcode.every2025;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 10:26
 * https://leetcode.cn/problems/count-negative-numbers-in-a-sorted-matrix/description/?envType=daily-question&envId=2025-12-28
 */
public class Code_20251228CountNegatives {
    //     0   1   2   3
    // 0 [ 4,  3,  2, -1],
    // 1 [ 3,  2,  1, -1],
    // 2 [ 1,  1, -1, -2],
    // 3 [-1, -1, -2, -3]
    public int countNegatives(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int cnt = 0;
        int i = 0, j = m - 1;
        for (; i < n && j >= 0;) {
            if (grid[i][j] < 0) {
                cnt += n - i;
                j--;
            } else {
                i++;
            }
        }
        return cnt;
    }

    public int countNegatives1(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] < 0) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
