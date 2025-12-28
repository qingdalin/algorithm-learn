package leetcode.study.leetcode001_200;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/25 19:33
 * https://leetcode.cn/problems/palindrome-partitioning-ii/
 */
public class Leetcode132MinCut {
    public static int minCut(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] palindrome = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                palindrome[i][j] = -1;
            }
        }
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return f(s, n - 1, palindrome, dp);
    }

    private static int f(char[] s, int r, int[][] palindrome, int[] dp) {
        if (isPalindrome(0, r, s, palindrome)) {
            return 0;
        }
        if (dp[r] != -1) {
            return dp[r];
        }
        int ans = Integer.MAX_VALUE;
        for (int l = 1; l < r; l++) {
            if (isPalindrome(l, r, s, palindrome)) {
                ans = Math.min(ans, f(s, l - 1, palindrome, dp) + 1);
            }
        }
        dp[r] = ans;
        return ans;
    }

    private static boolean isPalindrome(int l, int r, char[] s, int[][] palindrome) {
        if (l >= r) {
            return true;
        }
        if (palindrome[l][r] != -1) {
            return palindrome[l][r] == 1;
        }
        boolean ans = s[l] == s[r] && isPalindrome(l + 1, r - 1, s, palindrome);
        palindrome[l][r] = ans ? 1 : 0;
        return ans;
    }
}
