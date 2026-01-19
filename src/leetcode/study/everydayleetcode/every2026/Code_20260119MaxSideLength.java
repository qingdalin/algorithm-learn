package leetcode.study.everydayleetcode.every2026;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/19 19:23
 * https://leetcode.cn/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/?envType=daily-question&envId=2026-01-19
 */
public class Code_20260119MaxSideLength {
    // 1 1
    // 1 1
    // 1 2
    // 2 4
    public static int maxSideLength(int[][] mat, int threshold) {
        int n = mat.length, m = mat[0].length;
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int sum = 0;
                if (i - 1 >= 0) {
                    sum += mat[i - 1][j];
                }
                if (j - 1 >= 0) {
                    sum += mat[i][j - 1];
                }
                if (i - 1 >= 0 && j - 1 >= 0) {
                    sum -= mat[i - 1][j - 1];
                }
                mat[i][j] += sum;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = i + max, l = j + max; k < n && l < m; k++, l++) {
                    int sum = mat[k][l];
                    if (i - 1 >= 0) {
                        sum -= mat[i - 1][l];
                    }
                    if (j - 1 >= 0) {
                        sum -= mat[k][j - 1];
                    }
                    if (i - 1 >= 0 && j - 1 >= 0) {
                        sum += mat[i - 1][j - 1];
                    }
                    if (sum > threshold) {
                        break;
                    }
                    max = Math.max(max, k - i + 1);
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[][] arr = {
            {1,1,3,2,4,3,2},
            {1,1,3,2,4,3,2},
            {1,1,3,2,4,3,2}
        };
        int num = 4;
        System.out.println(maxSideLength(arr, num));
    }
}
