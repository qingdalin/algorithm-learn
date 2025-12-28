package algorithm.class104manacher;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/14 20:03
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * https://leetcode.cn/problems/longest-palindromic-substring/description/
 */
public class LongestPalindrome {
    public static int MAXN = 1001;
    public static char[] ss = new char[MAXN << 1];
    public static int[] p = new int[MAXN << 1];
    public static int n, max, end;
    public String longestPalindrome(String s) {
        manacher(s.toCharArray());
        return s.substring(end - max, end);
    }

    private void manacher(char[] s) {
        n = s.length * 2 + 1;
        for (int i = 0, j = 0; i < n; i++) {
            ss[i] = (i & 1) == 0 ? '#' : s[j++];
        }
        end = max = 0;
        for (int i = 0, r = 0, c = 0, len; i < n; i++) {
            len = r > i ? Math.min(r - i, p[2 * c - i]) : 1;
            while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
                len++;
            }
            if (i + len > r) {
                r = i + len;
                c = i;
            }
            if (len > max) {
                max = len - 1;
                end = (i + len - 1) / 2;
            }
            p[i] = len;
        }
    }
}
