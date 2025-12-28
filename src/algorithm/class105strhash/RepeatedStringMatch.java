package algorithm.class105strhash;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/18 14:53
 * https://leetcode.cn/problems/repeated-string-match/description/
 * 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
 *
 * 注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
 */
public class RepeatedStringMatch {
    public static int MAXN = 30001;
    public static int base = 499;
    public static long[] power = new long[MAXN];
    public static long[] hash = new long[MAXN];
    public static char[] s = new char[MAXN];
    public int repeatedStringMatch(String a, String b) {
        // 最多叠加k+1词
        char[] s1 = a.toCharArray();
        char[] s2 = b.toCharArray();
        int n = s1.length;
        int m = s2.length;
        int k = (m + n - 1) / n;
        int len = 0;
        for (int cnt = 0; cnt <= k; cnt++) {
            for (int i = 0; i < n; i++) {
                s[len++] = s1[i];
            }
        }
        build(len);
        long hash2 = s2[0] - 'a' + 1;
        for (int i = 1; i < m; i++) {
            hash2 = hash2 * base + s2[i] - 'a' + 1;
        }
        for (int l = 0, r = m - 1; r < len; l++, r++) {
            if (hash(l, r) == hash2) {
                return r < n * k ? k : (k + 1);
            }
        }
        return -1;
    }

    private long hash(int l, int r) {
        long ans = hash[r];
        if (l > 0) {
            ans -= hash[l - 1] * power[r - l + 1];
        }
        return ans;
    }

    private void build(int len) {
        power[0] = 1;
        for (int i = 1; i < len; i++) {
            power[i] = power[i - 1] * base;
        }
        hash[0] = s[0] - 'a' + 1;
        for (int i = 1; i < len; i++) {
            hash[i] = hash[i - 1] * base + s[i] - 'a' + 1;
        }
    }
}
