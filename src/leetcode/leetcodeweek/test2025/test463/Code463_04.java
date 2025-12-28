package leetcode.leetcodeweek.test2025.test463;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 9:09
 * https://leetcode.cn/problems/xor-after-range-multiplication-queries-ii/description/
 */
public class Code463_04 {
    public static int MAXN = 100001;
    public static int MOD = 1_000_000_007;
    public static int MAXB = 401;
    public static int[][] diff = new int[MAXB][];
    public static int blen, n, m;
    public static int xorAfterQueries(int[] nums, int[][] queries) {
        n = nums.length;
        m = queries.length;
        blen = (int) Math.sqrt(m);
        for (int i = 0; i < blen; i++) {
            diff[i] = null;
        }
        long v;
        for (int i = 0, l, r, k; i < m; i++) {
            l = queries[i][0];
            r = queries[i][1];
            k = queries[i][2];
            v = queries[i][3];
            if (k < blen) {
                if (diff[k] == null) {
                    diff[k] = new int[n + k];
                    Arrays.fill(diff[k], 1);
                }
                diff[k][l] = (int) (diff[k][l] * v % MOD);
                r = r - (r - l) % k + k;
                diff[k][r] = (int) (diff[k][r] * power(v, MOD - 2) % MOD);
            } else {
                for (int j = l; j <= r; j += k) {
                    nums[j] = (int) (nums[j] * v % MOD);
                }
            }
        }
        for (int k = 0; k < blen; k++) {
            int[] d = diff[k];
            if (d == null) {
                continue;
            }
            for (int start = 0; start < k; start++) {
                long muld = 1;
                for (int i = start; i < n; i += k) {
                    muld = muld * d[i] % MOD;
                    nums[i] = (int) (nums[i] * muld % MOD);
                }
            }
        }
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }

    private static long power(long x, int p) {
        long ans = 1;
        while (p > 0) {
            if ((p & 1) == 1) {
                ans = (ans * x) % MOD;
            }
            x = x * x % MOD;
            p >>= 1;
        }
        return ans;
    }
}
