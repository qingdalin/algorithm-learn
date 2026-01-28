package leetcode.study.everydayleetcode.every2026;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/28 19:31
 * https://leetcode.cn/problems/minimum-cost-path-with-teleportations/?envType=daily-question&envId=2026-01-28
 */
public class Code_20260128MinCost {
    public int minCost(int[][] grid, int k) {
        int n = grid.length, m = grid[0].length;
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(max, grid[i][j]);
            }
        }
        int[] sufMinF = new int[max + 2];
        Arrays.fill(sufMinF, Integer.MAX_VALUE);
        int[] minF = new int[max + 1];
        int[] f = new int[m + 1];
        for (int t = 0; t <= k; t++) {
            Arrays.fill(minF, Integer.MAX_VALUE);
            Arrays.fill(f, Integer.MAX_VALUE / 2);
            f[1] = -grid[0][0];
            for (int i = 0, x; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    x = grid[i][j];
                    f[j + 1] = Math.min(Math.min(f[j], f[j + 1]) + x, sufMinF[x]);
                    minF[x] = Math.min(minF[x], f[j + 1]);
                }
            }
            for (int i = max; i >= 0; i--) {
                sufMinF[i] = Math.min(sufMinF[i + 1], minF[i]);
            }
        }
        return f[m];
    }
}
