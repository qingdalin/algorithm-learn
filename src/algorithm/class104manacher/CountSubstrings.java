package algorithm.class104manacher;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/14 20:18
 * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
 *
 * 回文字符串 是正着读和倒过来读一样的字符串。
 *
 * 子字符串 是字符串中的由连续字符组成的一个序列。
 * https://leetcode.cn/problems/palindromic-substrings/description/
 */
public class CountSubstrings {
    public static int MAXN = 1001;
    public static char[] ss = new char[MAXN << 1];
    public static int[] p = new int[MAXN << 1];
    public static int n;
    public int countSubstrings(String s) {
        manacher(s.toCharArray());
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += p[i] / 2;
        }
        return sum;
    }

    private void manacher(char[] s) {
        manacherss(s);
        for (int i = 0, r = 0, c = 0, len; i < n; i++) {
            len = r > i ? Math.min(r - i, p[2 * c - i]) : 1;
            while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
                len++;
            }
            if (i + len > r) {
                r = i + len;
                c = i;
            }
            p[i] = len;
        }
    }

    private void manacherss(char[] s) {
        n = s.length * 2 + 1;
        for (int i = 0, j = 0; i < n; i++) {
            ss[i] = (i & 1) == 0 ? '#' : s[j++];
        }
    }
}
