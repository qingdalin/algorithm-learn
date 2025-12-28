package algorithm.class66dp;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-10 19:24
 * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
 *
 * "AAJF" ，将消息分组为 (1 1 10 6)
 * "KJF" ，将消息分组为 (11 10 6)
 * 注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
 *
 * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
 *
 * 题目数据保证答案肯定是一个 32 位 的整数。
 * https://leetcode.cn/problems/decode-ways/description/
 */
public class NumDecodings {
    public int numDecodings1(String s) {
        return f1(s.toCharArray(), 0);
    }

    public int f1(char[] s, int i) {
        if (i == s.length) {
            return 1;
        }
        int ans = 0;
        if (s[i] == '0') {
            ans = 0;
        } else {
            ans = f1(s, i + 1);
            if (i + 1 < s.length && ((s[i] - '0') * 10 + (s[i + 1] - '0') <= 26)) {
                ans += f1(s, i + 2);
            }
        }
        return ans;
    }

    public int numDecodings2(String s) {
        int[] dp = new int[s.length()];
        Arrays.fill(dp, -1);
        return f2(s.toCharArray(), 0, dp);
    }

    public int f2(char[] s, int i, int[] dp) {
        if (i == s.length) {
            return 1;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int ans = 0;
        if (s[i] == '0') {
            ans = 0;
        } else {
            ans = f2(s, i + 1, dp);
            if (i + 1 < s.length && ((s[i] - '0') * 10 + (s[i + 1] - '0') <= 26)) {
                ans += f2(s, i + 2, dp);
            }
        }
        dp[i] = ans;
        return ans;
    }

    public int numDecodings3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] == '0') {
                dp[i] = 0;
            } else {
                dp[i] = dp[i + 1];
                if (i + 1 < s.length && ((s[i] - '0') * 10 + (s[i + 1] - '0') <= 26)) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }

    public int numDecodings(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int nextNext = 0;
        int next = 1;
        for (int i = n - 1, cur; i >= 0; i--) {
            if (s[i] == '0') {
                cur = 0;
            } else {
                cur = next;
                if (i + 1 < s.length && ((s[i] - '0') * 10 + (s[i + 1] - '0') <= 26)) {
                    cur += nextNext;
                }
            }
            nextNext = next;
            next = cur;
        }
        return next;
    }
}
