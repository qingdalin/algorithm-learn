package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/29 20:40
 * https://leetcode.cn/problems/guess-number-higher-or-lower-ii/
 */
public class Leetcode375GetMoneyAmount {
    public static int MAXN = 201;
    public static int[][] dp = new int[MAXN][MAXN];

    public static int getMoneyAmount(int n) {
        for (int i = 0; i < MAXN; i++) {
            for (int j = 0; j < MAXN; j++) {
                dp[i][j] = -1;
            }
        }
        return f(1, n);
    }
    private static int f(int l, int r) {
        if (l >= r) {
            return 0;
        }
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        int ans = Integer.MAX_VALUE;
        for (int i = l; i <= r; i++) {
            int cur = Math.max(f(l, i - 1), f(i + 1, r)) + i;
            ans = Math.min(ans, cur);
        }
        dp[l][r] = ans;
        return ans;
    }
    public static int[] sum = new int[MAXN];
    static {
        for (int i = 1; i < MAXN; i++) {
            sum[i] = sum[i - 1] + i;
        }
    }
    public static int bs(int l, int r) {
        int limit = (sum[r] + sum[l]) / 2;
        // 找到大于等于limit的最小的下标
        int m, ans = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (sum[m] >= limit) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(getMoneyAmount(10));
    }
}
