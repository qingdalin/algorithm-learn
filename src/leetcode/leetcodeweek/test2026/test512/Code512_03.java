package leetcode.leetcodeweek.test2026.test512;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/7/26 10:10
 * https://leetcode.cn/problems/count-valid-sequences/description/
 */
public class Code512_03 {
    public static int MOD = 1000000007;
    public static int MAX = 500000;
    public static long[] f = new long[MAX];
    public static long[] inv = new long[MAX];

    static {
        f[0] = 1;
        for (int i = 1; i < MAX; i++) {
            f[i] = f[i - 1] * i % MOD;
        }
        inv[MAX - 1] = pow(f[MAX - 1], MOD - 2);
        for (int i = MAX - 1; i > 0; i--) {
            inv[i - 1] = inv[i] * i % MOD;
        }
    }

    private static long pow(long x, int p) {
        long ans = 1;
        while (p != 0) {
            if ((p & 1) == 1) {
                ans = ans * x % MOD;
            }
            x = x * x % MOD;
            p >>= 1;
        }
        return ans;
    }
    // n个里选m个， n! / (m! * (n-m)!)
    public static long comb(int n, int m) {
        return f[n] * inv[m] % MOD * inv[n - m] % MOD;
    }

    public static int countValidSequences(int n, int k) {
        long ans = comb(n - 1, k - 1);
        if ((n - k) % 2 == 0) {
            ans = (ans - comb((n + k) / 2 - 1, k - 1) + MOD) % MOD;
        }
        return (int) ans;
    }
}
