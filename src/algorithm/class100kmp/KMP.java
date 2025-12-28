package algorithm.class100kmp;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/7 19:43
 * https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/description/
 * 给你两个字符串 haystack 和 needle ，
 * 请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
 * 如果 needle 不是 haystack 的一部分，则返回  -1 。
 */
public class KMP {
    public int strStr(String haystack, String needle) {
        return kmp(haystack.toCharArray(), needle.toCharArray());
    }

    public static int kmp(char[] s1, char[] s2) {
        int n = s1.length;
        int m = s2.length;
        // x和y表示s1和s2的位置
        int x = 0, y = 0;
        int[] next = nextArray(s2, m);
        while (x < n && y < m) {
            if (s1[x] == s2[y]) {
                x++;
                y++;
            } else if (y == 0) {
                x++;
            } else {
                y = next[y];
            }
        }
        // 要么x越界，要么y越界
        return y == m ? x - y : -1;
    }

    private static int[] nextArray(char[] s2, int m) {
        if (m == 1) {
            return new int[] {-1};
        }
        int[] next = new int[m];
        next[0] = -1;
        next[1] = 0;
        // cn表示前一个需要比对的位置
        int i = 2, cn = 0;
        while (i < m) {
            if (s2[i - 1] == s2[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }
}
