package algorithm.class98power;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/4 7:58
 * .给定整数 n ，返回可以平铺 2 x n 的面板的方法的数量。返回对 109 + 7 取模 的值。
 *
 * 平铺指的是每个正方形都必须有瓷砖覆盖。两个平铺不同，
 * 当且仅当面板上有四个方向上的相邻单元中的两个，使得恰好有一个平铺有一个瓷砖占据两个正方形。
 * https://leetcode.cn/problems/domino-and-tromino-tiling/description/
 */
public class NumTilings {
    public static int mod = 1000000007;
    // f(n) = 2 * f(n - 1) + f(n - 3)
    public static int numTilings(int n) {
        //         2  a  b
        //         0  c  d
        //         1  e  f
        // 5  2 1  11 5  2
        // 11 5 2  24 11 5
        // 24 11 5 53 24 11
        // 5a + 2c + e = 5
        // 11a + 5c + 2e = 11  a + c = 1
        // 24a + 11c + 5e = 24
        // a = 1 c = 0 e = 0
        // 5b + 2d + f = 2
        // 11b + 5d + 2f = 5   b + d = 1
        // 24b + 11d + 5f = 11 b - d = -1
        // b = 0 d = 1 f = 0
        long[][] start = { { 5, 2, 1 } };
        long[][] base = {
            { 2, 1, 0},
            { 0, 0, 1},
            { 1, 0, 0},
        };
        long[][] a = multiply(start, power(base, n - 1));
        return (int)a[0][2];
    }

    private static long[][] multiply(long[][] a, long[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length;
        long[][] ans = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] = (ans[i][j] + ((a[i][c] % mod) * (b[c][j] % mod))) % mod;
                }
            }
        }
        return ans;
    }

    public static long[][] power(long[][] base, long p) {
        int n = base.length;
        long[][] ans = new long[n][n];
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

    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            System.out.println("平铺2 * " + i + " 个面板的方法数量是：" + numTilings(i));
        }
    }

    // h=1 表示有个小外挂角
    // h=0 表示没有个小外挂角
    private static int f(int n, int h) {
        if (n == 0) {
            return h == 0 ? 1 : 0;
        }
        if (n == 1) {
            return 1;
        }
        if (h == 1) {
            // n > 2且有外挂角有两种铺法，放一个多米诺加小外挂或者放一个托米诺没有外挂角
            return f(n - 1, 1) + f(n - 1, 0);
        } else {
            // n > 2没有外挂角有四种铺法
            // 竖着放一个多米诺无外挂角 n - 1
            // 横着放一个多米诺无外挂角 n - 2
            // 不同角度放一个托米诺无外挂角 n - 2
            return f(n - 1, 0) + f(n - 2, 0) + 2 * f(n - 2, 1);
        }
    }
}
