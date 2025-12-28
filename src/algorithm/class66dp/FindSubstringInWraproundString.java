package algorithm.class66dp;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-15 20:00
 * 定义字符串 base 为一个 "abcdefghijklmnopqrstuvwxyz" 无限环绕的字符串，所以 base 看起来是这样的：
 *
 * "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".
 * 给你一个字符串 s ，请你统计并返回 s 中有多少 不同非空子串 也在 base 中出现。
 * https://leetcode.cn/problems/unique-substrings-in-wraparound-string/description/
 */
public class FindSubstringInWraproundString {
    public int findSubstringInWraproundString1(String str) {
        int n = str.length();
        int[] s = new int[n];
        for (int i = 0; i < n; i++) {
            s[i] = str.charAt(i) - 'a';
        }
        // s中必须以'a'结尾的子串，最大延伸长度是多少，延伸一定根据base规则
        int[] dp = new int[26];
        dp[s[0]] = 1;
        for (int i = 1, cur, pre, len = 1; i < n; i++) {
            cur = s[i];
            pre = s[i - 1];
            if ((pre == 25 && cur == 0) || pre + 1 == cur) {
                // 前一个是'z'且当前是'a' 或者 前一个比当前的ASCII少1
                len++;
            } else {
                len = 1;
            }
            dp[cur] = Math.max(dp[cur], len);
        }
        int ans = 0;
        for (int i = 0; i < 26; i++) {
            ans += dp[i];
        }
        return ans;
    }

    public int findSubstringInWraproundString(String str) {
        int n = str.length();
        int[] s = new int[n];
        for (int i = 0; i < n; i++) {
            s[i] = str.charAt(i) - 'a';
        }
        // s中必须以'a'开始的子串，最大延伸长度是多少，延伸一定根据base规则
        int[] dp = new int[26];
        // 最后一个字符串开始，只有自己是1
        dp[s[n - 1]] = 1;
        for (int i = 0, cur, last, len = 1; i < n - 1; i++) {
            cur = s[i];
            last = s[i + 1];
            if ((last == 0 && cur == 25) || cur + 1 == last) {
                // 下一个是'a'且当前是'z' 或者 后一个比当前的ASCII多1
                len++;
            } else {
                len = 1;
            }
            dp[cur] = Math.max(dp[cur], len);
        }
        int ans = 0;
        for (int i = 0; i < 26; i++) {
            ans += dp[i];
        }
        return ans;
    }
}
