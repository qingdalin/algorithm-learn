package algorithm.class66dp;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-13 20:25
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 * https://leetcode.cn/problems/longest-valid-parentheses/description/
 */
public class LongestValidParentheses {

    public int longestValidParentheses1(String str) {
        char[] s = str.toCharArray();
        int[] dp = new int[s.length];
        dp[0] = 0;
        int ans = 0;
        // dp[i] 表示必须以i位置字符结尾，往左最多推多远能整体有效
        for (int i = 1, p; i < s.length; i++) {
            if (s[i] != '(') {
                p = i - dp[i - 1] - 1;
                if (p >= 0 && s[p] == '(') {
                    dp[i] = 2 + dp[i - 1] + (p - 1 >= 0 ? dp[p - 1] : 0);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public int longestValidParentheses(String str) {
        if (str.equals("")) {
            return 0;
        }
        char[] s = str.toCharArray();
        int[] dp = new int[s.length];
        dp[s.length - 1] = 0;
        int ans = 0;
        // dp[i] 表示必须以i位置字符开始，往右最多推多远能整体有效
        for (int i = s.length - 2, p; i >= 0 ; i--) {
            if (s[i] != ')') {
                p = i + dp[i + 1] + 1;
                if (p < s.length && s[p] == ')') {
                    dp[i] = 2 + dp[i + 1] + (p + 1 < s.length ? dp[p + 1] : 0);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
