package leetcode.study.everydayleetcode.every2026;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/5 19:48
 * https://leetcode.cn/problems/maximum-matrix-sum/?envType=daily-question&envId=2026-01-05
 */
public class Code_20260105MaxMatrixSum {
    public long maxMatrixSum(int[][] matrix) {
        long ans = 0;
        int negCnt = 0;
        int n = matrix.length, m = matrix[0].length, min = Integer.MAX_VALUE;
        for (int i = 0, cur; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cur = matrix[i][j];
                if (cur < 0) {
                    negCnt++;
                    cur = -cur;
                }
                min = Math.min(min, cur);
                ans += cur;
            }
        }
        if (negCnt % 2 == 0) {
            return ans;
        } else {
            return ans - 2L * min;
        }
    }
}
