package leetcode.leetcodeweek.test2026.test519;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/9/13 10:43
 * https://leetcode.cn/contest/weekly-contest-519/problems/cyclically-shift-rows-and-columns/description/
 */
public class Code519_01 {
    public static int[][] cyclicShift(int n, int[][] grid, int[] rowShift, int[] colShift) {
        int[][] ans = new int[n][n];
        for (int row = 0; row < n; row++) {
            int[] curRow = grid[row];
            int k = rowShift[row];
            for (int i = 0; i < n; i++) {
                int j = (i - k + n) % n;
                ans[row][j] = curRow[i];
            }
        }
        int[][] ansTmp = new int[n][n];
        for (int col = 0; col < n; col++) {
            int k = colShift[col];
            for (int i = 0; i < n; i++) {
                int j = (i - k + n) % n;
                ansTmp[j][col] = ans[i][col];
            }
        }
        return ansTmp;
    }

    public static void main(String[] args) {
        int n = 3;
        int[][] arr = {
            {1,2,3},
            {4,5,6},
            {7,8,9},
        };
        int[] rowShift = {1,2,0};
        int[] colShift = {2,2,1};
        System.out.println(Arrays.deepToString(cyclicShift(n, arr, rowShift, colShift)));
    }
}
