package algorithm.class68dp3;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-22 19:30
 * 使用m种颜色填满n个格子一共多少种方法
 */
public class Color {
    public static int mod = 1000000007;

    public static int ways1(int n, int m) {
        return f(new int[n], new boolean[m + 1], 0, n, m);
    }
    public static int f(int[] path, boolean[] set, int i, int n, int m) {
        if (i == n) {
            Arrays.fill(set, false);
            int colors = 0;
            for (int c : path) {
                if (!set[c]) {
                    set[c] = true;
                    colors++;
                }
            }
            return colors == m ? 1 : 0;
        } else {
            int ans = 0;
            for (int j = 1; j <= m; j++) {
                path[i] = j;
                ans += f(path, set, i + 1, n, m);
            }
            return ans;
        }
    }
    public static long[][] dp = new long[5001][5001];
    public static int ways2(int n, int m) {
        // dp[i][j]表示i个格子使用了j种颜色
        for (int i = 1; i <= n; i++) {
            // 使用一种颜色填充n个格子有m种方法
            dp[i][1] = m;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= m; j++) {
                dp[i][j] = (dp[i - 1][j] * j) % mod;
                dp[i][j] = ((dp[i - 1][j - 1] * (m - j + 1)) + dp[i][j]) % mod;
            }
        }
        return (int) dp[n][m];
    }

    public static void main(String[] args) {
        int n = 9;
        int m = 9;
        System.out.println("测试功能开始");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int ans1 = ways1(i, j);
                int ans2 = ways2(i, j);
                if (ans1 != ans2) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("测试功能结束");

        System.out.println("性能测试开始");
        n = 5000;
        m = 4877;
        long start = System.currentTimeMillis();
        int ans = ways2(n, m);
        long end = System.currentTimeMillis();
        System.out.println("取余后的结果：" + ans);
        System.out.println("性能测试的时间：" + (end - start) + "毫秒");
        System.out.println("性能测试结束");
    }
}
