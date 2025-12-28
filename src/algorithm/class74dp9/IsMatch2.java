package algorithm.class74dp9;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-16 15:10
 * 给你一个输入字符串 (s) 和一个字符模式 (p) ，请你实现一个支持 '?' 和 '*' 匹配规则的通配符匹配：
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符序列（包括空字符序列）。
 * 判定匹配成功的充要条件是：字符模式必须能够 完全匹配 输入字符串（而不是部分匹配）。
 * https://leetcode.cn/problems/wildcard-matching/description/
 */
public class IsMatch2 {
    public boolean isMatch(String str, String pat) {
        char[] s = str.toCharArray();
        char[] p = pat.toCharArray();
        int n = s.length;
        int m = p.length;
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[n][m] = true;
        for (int j = m - 1; j >= 0 && p[j] == '*'; j--) {
            dp[n][j] = true;
         }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (p[j] != '*') {
                    // j位置不是*，那么i和j位置要一样，往后匹配
                    dp[i][j] = (s[i] == p[j] || p[j] == '?') && dp[i + 1][j + 1];
                } else {
                    // j位置是*，1.不让搞定i，j往后，或者搞定i往后，j不变
                    dp[i][j] = dp[i][j + 1] || dp[i + 1][j];
                }
            }
        }
        return dp[0][0];
    }
}
