package algorithm.class66dp;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-28 19:58
 * 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，
 * 后面的每一项数字都是前面两项数字的和。也就是：
 *
 * F(0) = 0，F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 * https://leetcode.cn/problems/fibonacci-number/description/
 */
public class Fib {
    public int fib(int n) {
        return fib2(n);
    }

    public int fib2(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return fibCache(n, dp);
    }
    public int fibCache(int i, int[] dp) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int ans = fibCache(i - 1, dp) + fibCache(i - 2, dp);
        dp[i] = ans;
        return ans;
    }

    public int fib1(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public int fib3(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i -2];
        }
        return dp[n];
    }

    public int fib4(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int lastLast = 0;
        int last = 1;
        int cur = 0;
        for (int i = 2; i <= n; i++) {
            cur = last + lastLast;
            lastLast = last;
            last = cur;
        }
        return cur;
    }

    /**
     * 矩阵快速幂logn复杂度
     * @param n
     * @return
     */
    public int fib5(int n) {
        int[][] start = { { 1, 0 } };
        int[][] m = {
            { 1, 1 },
            { 1, 0 }
        };
        int[][] a = multiply(start, power(m, n));
        return a[0][1];
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

    public static int[][] power(int[][] m, int p) {
        int n = m.length;
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            // 对角线设置1就相当于ans=1
            ans[i][i] = 1;
        }
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                ans = multiply(ans, m);
            }
            m = multiply(m, m);
        }
        return ans;
    }
}
