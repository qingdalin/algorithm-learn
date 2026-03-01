package leetcode.leetcodeweek.test2026.test491;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/1 10:24
 * https://leetcode.cn/contest/weekly-contest-491/problems/minimum-bitwise-or-from-grid/
 */
public class Code491_03 {
    // 5 = 101
    // 3 = 011
    // 6 = 110
    // 4 = 100
    public int minimumOR(int[][] grid) {
        int max = 0;
        int n = grid.length, m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(max, grid[i][j]);
            }
        }
        int bitLen = 32 - Integer.numberOfLeadingZeros(max);
        int ans = 0;
        for (int k = bitLen - 1; k >= 0; k--) {
            int mask = ans | ((1 << k) - 1);
            for (int i = 0; i < n; i++) {
                boolean ok = false;
                for (int j = 0; j < m; j++) {
                    if ((grid[i][j] | mask) == mask) {
                        ok = true;
                        break;
                    }
                }
                if (!ok) {
                    ans |= 1 << k;
                    break;
                }
            }
        }
        return ans;
    }
}
