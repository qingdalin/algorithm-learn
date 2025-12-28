package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 15:51
 * https://leetcode.cn/problems/max-sum-of-rectangle-no-larger-than-k/
 */
public class Leetcode363MaxSumSubmatrix {
    public static int maxSumSubmatrix(int[][] matrix, int k) {
        int n = matrix.length;
        int m = matrix[0].length;
        // 4 5
        // 1 2
        // 4 9
        // 5 12
        // 获取左上(a,b)和右下(c,d)矩形的和
        // sum(c,d) - sum(c,b-1) - sum(a-1,d) + sum(a-1,b-1)
        int[][] sum= new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
               // 左 + 上 + 自己 - 左上
               sum[i][j] = sum[i][j - 1] + sum[i - 1][j] + matrix[i - 1][j - 1] - sum[i - 1][j - 1];
            }
        }
        int ans = Integer.MIN_VALUE;
        for (int a = 1; a <= n; a++) {
            for (int b = 1; b <= m; b++) {
                for (int c = a; c <= n; c++) {
                    for (int d = b; d <= m; d++) {
                        int cur = getSum(a, b, c, d, sum);
                        if (cur <= k) {
                            ans = Math.max(ans, cur);
                        }
                    }
                }
            }
        }
        return ans;
    }
    //   b   d
    // a
    // c
    public static int getSum(int a, int b, int c, int d, int[][] sum) {
        return sum[c][d] - sum[c][b - 1] - sum[a - 1][d] + sum[a - 1][b - 1];
    }

    public static void main(String[] args) {
        int[][] arr = new int[][] {
            {2,2,-1}
        };
        System.out.println(maxSumSubmatrix(arr, 0));
    }
}
