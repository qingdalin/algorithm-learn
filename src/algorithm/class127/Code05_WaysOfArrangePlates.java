package algorithm.class127;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/30 20:24
 * // 摆盘子的方法
 * // 一共有n个盘子k种菜，所有盘子排成一排，每个盘子只能放一种菜
 * // 要求最多连续两个盘子菜品一样，更长的重复出现是不允许的
 * // 返回摆盘的方法数，答案对 1000000007 取模
 * // 1 <= n <= 1000
 * // 1 <= k <= 1000
 * // 来自真实大厂笔试，对数器验证
 */
public class Code05_WaysOfArrangePlates {
    public static int MOD = 1000000007;

    public static int dp1(int n, int k) {
        if (n == 1) {
            return k;
        }
        return (int) ((((long) f(n - 1, k) + f(n - 2, k)) * k) % MOD);
    }

    // i+1的盘子一定有菜，且i和i+1的菜品不一样，返回有多少种放置的办法
    private static int f(int i, int k) {
        if (i == 0) {
            // 盘子用完返回1种方法
            return 1;
        }
        if (i == 1) {
            // 只剩余一个盘子，要和前一个不一样，返回k-1
            return k - 1;
        }
        long p1 = f(i - 1, k);
        long p2 = f(i - 2, k);
        return (int) (((p1 + p2) * (k - 1)) % MOD);
    }

    public static int dp2(int n, int k) {
        if (n == 1) {
            return k;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = k - 1;
        for (int i = 2; i < n; i++) {
            long p1 = dp[i - 1];
            long p2 = dp[i - 2];
            dp[i] = (int) (((p1 + p2) * (k - 1)) % MOD);
        }
        return (int) ((((long) dp[n - 1] + dp[n - 2]) * k) % MOD);
    }

    public static int dp3(int n, int k) {
        if (n == 1) {
            return k;
        }
        int s = k - 1;
        int[][] start = {{1, s}};
        // 1  s  (1+s)*s   (s + (1+s)*s) * s
        // 0  1     2             3
        int[][] base = {{0, s}, {1, s}};
        int[][] ans = multiply(start, power(base, n - 2));
        return (int) ((((long) ans[0][0] + ans[0][1]) * k) % MOD);
    }

    private static int[][] power(int[][] m, int p) {
        int n = m.length;
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            ans[i][i] = 1;
        }
        while (p > 0) {
            if ((p & 1) == 1) {
                ans = multiply(ans, m);
            }
            m = multiply(m, m);
            p >>= 1;
        }
        return ans;
    }

    private static int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length;
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] = (int) (((long)ans[i][j] + (long)a[i][c] * b[c][j]) % MOD);
                }
            }
        }
        return ans;
    }

    public static int dp4(int n, int k) {
        if (n == 1) {
            return k;
        }
        int[] path = new int[n];
        return dfs(path, 0, k);
    }

    private static int dfs(int[] path, int i, int k) {
        if (i == path.length) {
            int len = 1;
            for (int j = 1; j < path.length; j++) {
                if (path[j - 1] == path[j]) {
                    len++;
                } else {
                    len = 1;
                }
                if (len > 2) {
                    return 0;
                }
            }
            return len > 2 ? 0 : 1;
        } else {
            int ans = 0;
            for (int j = 0; j < k; j++) {
                path[i] = j;
                ans += dfs(path, i + 1, k);
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println("功能测试开始");
        for (int n = 1; n <= 8; n++) {
            for (int k = 1; k <= 8; k++) {
                int ans1 = dp1(n, k);
                int ans2 = dp2(n, k);
                int ans3 = dp3(n, k);
                int ans4 = dp4(n, k);
                if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("功能测试结束");
        System.out.println();
        System.out.println("性能测试开始");
        int n = 550501636;
        int k = 50501636;
        long start, end;
        System.out.println("一共有" + n + "格盘子和" + k + "盘菜");
        start = System.currentTimeMillis();
        int ans1 = dp2(n, k);
        end = System.currentTimeMillis();
        System.out.println("dp2的" + ans1 + "方法执行时长：" + (end - start));

        start = System.currentTimeMillis();
        int ans2 = dp3(n, k);
        end = System.currentTimeMillis();
        System.out.println("dp3的" + ans2 + "方法执行时长：" + (end - start));
        System.out.println("性能测试结束");
    }
}
