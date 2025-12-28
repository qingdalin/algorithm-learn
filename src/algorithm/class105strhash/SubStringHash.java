package algorithm.class105strhash;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/18 14:08
 */
public class SubStringHash {
    public static int MAXN = 10001;
    public static int base = 499;
    public static long[] power = new long[MAXN];
    public static long[] hash = new long[MAXN];
    public int strStr(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        build(s1, n);
        long hash2 = s2[0] - 'a' + 1;
        for (int i = 1; i < m; i++) {
            hash2 = hash2 * base + s2[i] - 'a' + 1;
        }
        for (int l = 0, r = m - 1; r < n; l++, r++) {
            if (hash(l, r) == hash2) {
                return l;
            }
        }
        return -1;
    }

    private long hash(int l, int r) {
        // 子串的hash值
        // i-j的 hash[j] - hash[i - 1] * base的(j - i + 1)次方
        long ans = hash[r];
        if (l > 0) {
            ans -= hash[l - 1] * power[r - l + 1];
        }
        return ans;
    }

    private void build(char[] s, int n) {
        power[0] = 1;
        for (int i = 1; i < n; i++) {
            power[i] = power[i - 1] * base;
        }
        hash[0] = (long) s[0] - 'a' + 1;
        for (int i = 1; i < n; i++) {
            hash[i] = hash[i - 1] * base + s[i] - 'a' + 1;
        }
    }
}
