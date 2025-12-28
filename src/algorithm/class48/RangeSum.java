package algorithm.class48;

/**
 * https://leetcode.cn/problems/range-sum-query-2d-immutable/
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-02-03 15:05
 */
public class RangeSum {
    int[][] sum;
    class NumMatrix {

        public NumMatrix(int[][] matrix) {
            int n = matrix.length;
            int m = matrix[0].length;
            sum = new int[n + 1][m + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    sum[i + 1][j + 1] = matrix[i][j];
                }
            }
            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < m + 1; j++) {
                    sum[i][j] += sum[i][j - 1] + sum[i - 1][j] - sum[i - 1][j - 1];
                }
            }
        }

        public int sumRegion(int a, int b, int c, int d) {
            c++;
            d++;
            return sum[c][d] - sum[c][b] - sum[a][d] + sum[a][b];
        }
    }
}
