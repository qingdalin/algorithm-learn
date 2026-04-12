package leetcode.leetcodeweek.test2026.test497;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/12 9:46
 * https://leetcode.cn/contest/weekly-contest-497/problems/find-the-degree-of-each-vertex/description/
 */
public class Code497_01 {
    public int[] findDegrees(int[][] matrix) {
        int n = matrix.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    cnt++;
                }
            }
            ans[i] = cnt;
        }
        return ans;
    }
}
