package algorithm.class67dp2;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-18 16:11
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 *
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 *
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 * https://leetcode.cn/problems/longest-common-subsequence/description/
 */
public class LongestCommonSubsequence {
    public int longestCommonSubsequence1(String text1, String text2) {
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        return f1(s1, s2, s1.length - 1, s2.length - 1);
    }

    public int f1(char[] s1, char[] s2, int i1, int i2) {
        // i1 是 s1 序列的结尾字符
        // i2 是 s2 序列的结尾字符
        if (i1 < 0 || i2 < 0) {
            return 0;
        }
        // 不要s1和s2的结尾
        int p1 = f1(s1, s2, i1 - 1, i2 - 1);
        // 不要s1结尾，要s2结尾
        int p2 = f1(s1, s2, i1 - 1, i2);
        // 要s1结尾，不要s2结尾
        int p3 = f1(s1, s2, i1, i2 - 1);
        // 只有结尾相同才要第四种可能, 比p1多一种可能性
        int p4 = s1[i1] == s2[i2] ? (p1 + 1) : 0;
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    public int longestCommonSubsequence2(String text1, String text2) {
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        return f2(s1, s2, s1.length, s2.length);
    }

    public int f2(char[] s1, char[] s2, int len1, int len2) {
        // len1是s1的前缀长度
        // len2是s2的前缀长度
        if (len1 == 0 || len2 == 0) {
            return 0;
        }
        int ans = 0;
        if (s1[len1 - 1] == s2[len2 - 1]) {
            ans = f2(s1, s2, len1 - 1, len2 - 1) + 1;
        } else {
            ans = Math.max(f2(s1, s2, len1 - 1, len2), f2(s1, s2, len1, len2 - 1));
        }
        return ans;
    }

    public int longestCommonSubsequence3(String text1, String text2) {
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        int len1 = s1.length;
        int len2 = s2.length;
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                dp[i][j] = -1;
            }
        }
        return f3(s1, s2, len1, len2, dp);
    }

    public int f3(char[] s1, char[] s2, int len1, int len2, int[][] dp) {
        // len1是s1的前缀长度
        // len2是s2的前缀长度
        if (dp[len1][len2] != -1) {
            return dp[len1][len2];
        }
        if (len1 == 0 || len2 == 0) {
            return 0;
        }
        int ans = 0;
        if (s1[len1 - 1] == s2[len2 - 1]) {
            ans = f3(s1, s2, len1 - 1, len2 - 1, dp) + 1;
        } else {
            ans = Math.max(f3(s1, s2, len1 - 1, len2, dp), f3(s1, s2, len1, len2 - 1, dp));
        }
        dp[len1][len2] = ans;
        return ans;
    }

    public int longestCommonSubsequence4(String text1, String text2) {
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        int len1 = s1.length;
        int len2 = s2.length;
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[len1][len2];
    }

    public int longestCommonSubsequence(String text1, String text2) {
        char[] s1, s2;
        if (text1.length() >= text2.length()) {
            s1 = text1.toCharArray();
            s2 = text2.toCharArray();
        } else {
            s1 = text2.toCharArray();
            s2 = text1.toCharArray();
        }
        int n = s1.length;
        int m = s2.length;
        int[] dp = new int[m + 1];
        for (int i = 1; i <= n; i++) {
            int leftUp = 0, backUp;
            for (int j = 1; j <= m; j++) {
                backUp = dp[j];
                if (s1[i - 1] == s2[j - 1]) {
                    dp[j] = leftUp + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                leftUp = backUp;
            }
        }
        return dp[m];
    }
}
