package algorithm.class67dp2;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-19 9:03
 * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
 *
 * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
 * https://leetcode.cn/problems/longest-palindromic-subsequence/description/
 */
public class LongestPalindromeSubseq {
    public int longestPalindromeSubseq1(String s) {
        char[] s1 = s.toCharArray();
        int n = s1.length;
        char[] s2 = new char[n];
        for (int l = 0, r = n - 1; l < n; l++, r--) {
            s2[l] = s1[r];
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int leftUp = 0, backUp;
            for (int j = 1; j <= n; j++) {
                backUp = dp[j];
                if (s1[i - 1] == s2[j - 1]) {
                    dp[j] = leftUp + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                leftUp = backUp;
            }
        }
        return dp[n];
    }

    public int longestPalindromeSubseq2(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        return f1(s, 0, n - 1);
    }
    // 表示在s[][l, r]最长的公共子序列有所长
    public int f1(char[] s, int l, int r) {
        if (l == r) {
            return 1;
        }
        if (l + 1 == r) {
            return s[l] == s[r] ? 2 : 1;
        }
        if (s[l] == s[r]) {
            return f1(s, l + 1, r - 1) + 2;
        } else {
            return Math.max(f1(s, l + 1, r), f1(s, l, r - 1));
        }
    }

    public int longestPalindromeSubseq3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        return f2(s, 0, n - 1, dp);
    }
    // 表示在s[][l, r]最长的公共子序列有所长
    public int f2(char[] s, int l, int r, int[][] dp) {
        if (l == r) {
            return 1;
        }
        if (l + 1 == r) {
            return s[l] == s[r] ? 2 : 1;
        }
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        int ans;
        if (s[l] == s[r]) {
            ans = f2(s, l + 1, r - 1, dp) + 2;
        } else {
            ans = Math.max(f2(s, l + 1, r, dp), f2(s, l, r - 1, dp));
        }
        dp[l][r] = ans;
        return ans;
    }

    public int longestPalindromeSubseq4(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] dp = new int[n][n];
        for (int l = n - 1; l >= 0; l--) {
            dp[l][l] = 1;
            if (l + 1 < n) {
                dp[l][l + 1] = s[l] == s[l + 1] ? 2 : 1;
            }
            for (int r = l + 2; r < n; r++) {
                if (s[l] == s[r]){
                    dp[l][r] = 2 + dp[l + 1][r - 1];
                } else {
                    dp[l][r] = Math.max(dp[l + 1][r],dp[l][r - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    public int longestPalindromeSubseq(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[] dp = new int[n];
        for (int l = n - 1, leftDown = 0, backUp; l >= 0; l--) {
            dp[l] = 1;
            if (l + 1 < n) {
                leftDown = dp[l + 1];
                dp[l + 1] = s[l] == s[l + 1] ? 2 : 1;
            }
            for (int r = l + 2; r < n; r++) {
                backUp = dp[r];
                if (s[l] == s[r]){
                    dp[r] = 2 + leftDown;
                } else {
                    dp[r] = Math.max(dp[r],dp[r - 1]);
                }
                leftDown = backUp;
            }
        }
        return dp[n - 1];
    }
}
