package algorithm.class74dp9;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-16 14:33
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 *
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
 * https://leetcode.cn/problems/regular-expression-matching/description/
 */
public class IsMatch {
    public boolean isMatch1(String str, String pat) {
        char[] s = str.toCharArray();
        char[] p = pat.toCharArray();
        return f1(s, p, 0, 0);
    }

    private boolean f1(char[] s, char[] p, int i, int j) {
        //  s[i...]往后，p[j...]出发往后匹配是否可以匹配上
        if (i == s.length) {
            // 1.s到结尾
            if (j == p.length) {
                // 1.1p也到结尾，说明匹配上
                return true;
            } else {
                // 1.2j没到结尾，j + 1是*，看j+2往后是否可以匹配
                return j + 1 < p.length && p[j + 1] == '*' && f1(s, p, i, j + 2);
            }
        } else if (j == p.length) {
            // 2. s没到结尾但是p到结尾
            return false;
        } else {
            // 3. s和p都没有到结尾
            if (j + 1 == p.length || p[j + 1] != '*') {
                // j + 1不是*：1.越界 2，不等于*
                // s[i]和p[j]必须匹配相等
                return (s[i] == p[j] || p[j] == '.') && f1(s, p, i + 1, j + 1);
            } else {
                // j + 1是*
                // i 和 j位置的字符不匹配
                boolean p1 = f1(s, p, i , j + 2);
                // i 和 j的位置匹配
                boolean p2 = (s[i] == p[j] || p[j] == '.') && f1(s, p, i + 1, j);
                // 有一种情况为true即可
                return p1 || p2;
            }
        }
    }

    public boolean isMatch2(String str, String pat) {
        char[] s = str.toCharArray();
        char[] p = pat.toCharArray();
        int n = s.length;
        int m = p.length;
        int[][] dp = new int[n + 1][m + 1];
        // dp[i][j] == 0 没计算过
        // dp[i][j] == 1 可以匹配上
        // dp[i][j] == 2 不能匹配上
        return f2(s, p, 0, 0, dp);
    }

    private boolean f2(char[] s, char[] p, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j] == 1;
        }
        //  s[i...]往后，p[j...]出发往后匹配是否可以匹配上
        boolean ans;
        if (i == s.length) {
            // 1.s到结尾
            if (j == p.length) {
                // 1.1p也到结尾，说明匹配上
                ans = true;
            } else {
                // 1.2j没到结尾，j + 1是*，看j+2往后是否可以匹配
                ans = j + 1 < p.length && p[j + 1] == '*' && f2(s, p, i, j + 2, dp);
            }
        } else if (j == p.length) {
            // 2. s没到结尾但是p到结尾
            ans = false;
        } else {
            // 3. s和p都没有到结尾
            if (j + 1 == p.length || p[j + 1] != '*') {
                // j + 1不是*：1.越界 2，不等于*
                // s[i]和p[j]必须匹配相等
                ans = (s[i] == p[j] || p[j] == '.') && f2(s, p, i + 1, j + 1, dp);
            } else {
                // j + 1是*
                // i 和 j位置的字符不匹配
                boolean p1 = f2(s, p, i , j + 2, dp);
                // i 和 j的位置匹配
                boolean p2 = (s[i] == p[j] || p[j] == '.') && f2(s, p, i + 1, j, dp);
                // 有一种情况为true即可
                ans = p1 || p2;
            }
        }
        dp[i][j] = ans ? 1 : 2;
        return ans;
    }

    public boolean isMatch(String str, String pat) {
        char[] s = str.toCharArray();
        char[] p = pat.toCharArray();
        int n = s.length;
        int m = p.length;
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[n][m] = true;
        for (int j = m - 1; j >= 0; j--) {
            dp[n][j] = j + 1 < p.length && p[j + 1] == '*' && dp[n][j + 2];
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (j + 1 == p.length || p[j + 1] != '*') {
                    // j + 1不是*：1.越界 2，不等于*
                    // s[i]和p[j]必须匹配相等
                    dp[i][j] = (s[i] == p[j] || p[j] == '.') && dp[i + 1][j + 1];
                } else {
                    // j + 1是*
                    // i 和 j位置的字符不匹配
                    // i 和 j的位置匹配
                    // 有一种情况为true即可
                    dp[i][j] = dp[i][j + 2] || ((s[i] == p[j] || p[j] == '.') && dp[i + 1][j]);
                }
            }
        }
        return dp[0][0];
    }
}
