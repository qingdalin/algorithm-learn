package algorithm.class104manacher;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/17 9:36
 * 给你一个字符串 s 和一个 正 整数 k 。
 *
 * 从字符串 s 中选出一组满足下述条件且 不重叠 的子字符串：
 *
 * 每个子字符串的长度 至少 为 k 。
 * 每个子字符串是一个 回文串 。
 * 返回最优方案中能选择的子字符串的 最大 数目。
 *
 * 子字符串 是字符串中一个连续的字符序列。
 * https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/description/
 */
public class MaxPalindromes {
    public static int MAXN = 2001;
    public static char[] ss = new char[MAXN << 1];
    public static int[] p = new int[MAXN << 1];
    public static int n;
    public int maxPalindromes(String s, int k) {
        manacherss(s.toCharArray());
        int ans = 0, next = 0;
        while ((next = find(next, k)) != -1) {
            ans++;
        }
        return ans;
    }

    private int find(int l, int k) {
        // l位置的字符必须保证是#
        for (int i = l, r = l, c = l, len; i < n; i++) {
            len = r > i ? Math.min(r - i, p[2 * c - i]) : 1;
            while (i + len < n && i - len >= l && ss[i + len] == ss[i - len]) {
                if (++len > k) {
                    return i + k + (ss[i + k] != '#' ? 1 : 0);
                }
                if (i + len > r) {
                    r = i + len;
                    c = i;
                }
                p[i] = len;
            }

        }
        return -1;
    }

    private void manacherss(char[] s) {
        n = 2 * s.length + 1;
        for (int i = 0, j = 0; i < n; i++) {
            ss[i] = (i & 1) == 1 ? s[j++] : '#';
        }
    }
}
