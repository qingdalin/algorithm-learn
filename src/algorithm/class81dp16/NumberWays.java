package algorithm.class81dp16;

import java.util.Arrays;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-30 19:42
 * 总共有 n 个人和 40 种不同的帽子，帽子编号从 1 到 40 。
 *
 * 给你一个整数列表的列表 hats ，其中 hats[i] 是第 i 个人所有喜欢帽子的列表。
 *
 * 请你给每个人安排一顶他喜欢的帽子，确保每个人戴的帽子跟别人都不一样，并返回方案数。
 *
 * 由于答案可能很大，请返回它对 10^9 + 7 取余后的结果。
 * https://leetcode.cn/problems/number-of-ways-to-wear-different-hats-to-each-other/description/
 */
public class NumberWays {
    public static int mod = 1000000007;

    public static void main(String[] args) {
        System.out.println("hello world");
    }
    public int numberWays(List<List<Integer>> arr) {
        int m = 0; // 帽子最大编号
        for (List<Integer> hats : arr) {
            for (Integer h : hats) {
                m = Math.max(m, h);
            }
        }
        int[] hats = new int[m + 1];
        int n = arr.size();
        for (int pi = 0; pi < n; pi++) {
            for (Integer hat : arr.get(pi)) {
                hats[hat] |= (1 << pi);
            }
        }
        int[][] dp = new int[m + 1][1 << n];
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], - 1);
        }
        return f1(hats, n, m, 1, 0, dp);
    }

    /*
     * hats 帽子被哪些人喜欢
     * n 一共n个人
     * m 一共m个帽子
     * i 当前来到i号帽子
     * s 当前i号帽子的满足人的状态，0是没满足，1是满足
     * dp 缓存dp
     */
    private int f1(int[] hats, int n, int m, int i, int s, int[][] dp) {
        if (s == (1 << n) - 1) {
            return 1; // n个人都满足状态，返回一种可能
        }
        if (i == m + 1) {
            return 0; // 帽子超过限制，人还没有满足，返回0；
        }
        if (dp[i][s] != -1) {
            return dp[i][s];
        }
        int cur = hats[i];
        // 不选当前颜色帽子也可能有方法
        int ans = f1(hats, n, m, i + 1, s, dp);
        for (int p = 0; p < n; p++) {
            // 当前帽子是这个人喜欢的，并且状态是0
            if (((cur & (1 << p)) != 0) && ((s & (1 << p)) == 0)) {
                ans = (ans + f1(hats, n, m, i + 1, s | (1 << p), dp)) % mod;
            }
        }
        dp[i][s] = ans;
        return ans;
    }

    private int f2(int[] hats, int n, int m, int i, int s, int[][] dp) {
        if (s == (1 << n) - 1) {
            return 1; // n个人都满足状态，返回一种可能
        }
        if (i == m + 1) {
            return 0; // 帽子超过限制，人还没有满足，返回0；
        }
        if (dp[i][s] != -1) {
            return dp[i][s];
        }
        int cur = hats[i];
        // 不选当前颜色帽子也可能有方法
        int ans = f2(hats, n, m, i + 1, s, dp);
        // 30节异或运算，求最右侧的1，51分开始
        int rightOne;
        while (cur != 0) {
            rightOne = cur & -cur;
            if ((s & rightOne) == 0) {
                ans = (ans + f2(hats, n, m, i + 1, s | rightOne, dp)) % mod;
            }
            cur ^= rightOne;
        }
        dp[i][s] = ans;
        return ans;
    }
}
