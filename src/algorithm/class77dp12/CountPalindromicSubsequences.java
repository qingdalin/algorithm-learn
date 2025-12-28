package algorithm.class77dp12;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-23 14:07
 * 给你一个字符串 s ，返回 s 中不同的非空回文子序列个数 。由于答案可能很大，请返回对 109 + 7 取余 的结果。
 *
 * 字符串的子序列可以经由字符串删除 0 个或多个字符获得。
 *
 * 如果一个序列与它反转后的序列一致，那么它是回文序列。
 *
 * 如果存在某个 i , 满足 ai != bi ，则两个序列 a1, a2, ... 和 b1, b2, ... 不同。
 *
 * https://leetcode.cn/problems/count-different-palindromic-subsequences/description/
 */
public class CountPalindromicSubsequences {
    public int countPalindromicSubsequences(String str) {
        int mod = 1000000007;
        char[] s = str.toCharArray();
        int n = s.length;
        int[] last = new int[256];
        Arrays.fill(last, - 1);
        // 和i位置字符相同，在左边最靠近i出现的位置
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            left[i] = last[s[i]];
            last[s[i]] = i;
        }
        // 和i位置字符相同，在右边最靠近i出现的位置
        int[] right = new int[n];
        Arrays.fill(last, n);
        for (int i = n - 1; i >= 0; i--) {
            right[i] = last[s[i]];
            last[s[i]] = i;
        }
        long[][] dp = new long[n][n];
        for (int i = 0; i < n; i++) {
            // 只有1个字符，回文数是1
            dp[i][i] = 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s[i] != s[j]) {
                    // dp[i][j] = dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1]
                    dp[i][j] = dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1] + mod;
                } else {
                    int l = right[i];
                    int r = left[j];
                    if (l > r) {
                        // i到j范围不包含当前字符
                        dp[i][j] = dp[i + 1][j - 1] * 2 + 2;
                    } else if (l == r) {
                        // i到j范围包含1个当前字符
                        dp[i][j] = dp[i + 1][j - 1] * 2 + 1;
                    } else {
                        // i到j范围包含2个及以上当前字符
                        dp[i][j] = dp[i + 1][j - 1] * 2 - dp[l + 1][r - 1] + mod;
                    }
                }
                dp[i][j] %= mod;
            }
        }
        return (int)dp[0][n - 1];
    }
}
