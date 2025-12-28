package algorithm.class98power;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/3 16:15
 * 泰波那契序列 Tn 定义如下：
 *
 * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
 *
 * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
 * https://leetcode.cn/problems/n-th-tribonacci-number/description/
 */
public class Tribonacci {
    public int tribonacci(int n) {
        // 0 1  1, 2  4  7 13
        // 0 1 ,2, 3  4  5 6
        //       1 a d
        //       1 b e
        //       1 c f
        // 1 1 0 2 1 1
        // 2 1 1 4 2 1
        // 4 2 1 7 4 2
        // a + b = 1
        // 2a + b + c = 2  a + c = 1
        // 4a + 2b + c = 4

        // c = 0 a = 1 b = 0

        // d + e = 1
        // 2d + e + f = 1   d + f = 0
        // 4d + 2e + f = 2
        // f = d = 0 e = 1
        int[][] start = { { 1, 1, 0 } };
        int[][] base = {
            { 1, 1, 0 },
            { 1, 0, 1 },
            { 1, 0, 0}
        };
        int[][] a = multiply(start, power(base, n));
        return a[0][2];
//        int[][] a = multiply(start, power(base, n - 2));
//        return a[0][0];
    }


    private static int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length;
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }

    public static int[][] power(int[][] base, int p) {
        int n = base.length;
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            // 对角线设置1就相当于ans=1
            ans[i][i] = 1;
        }
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                ans = multiply(ans, base);
            }
            base = multiply(base, base);
        }
        return ans;
    }
}
