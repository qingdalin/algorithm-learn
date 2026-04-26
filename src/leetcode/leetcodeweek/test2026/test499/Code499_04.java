package leetcode.leetcodeweek.test2026.test499;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/26 9:17
 * https://leetcode.cn/problems/maximum-sum-of-alternating-subsequence-with-distance-at-least-k/description/
 */
public class Code499_04 {
    class Fenwick {
        private final long[] f;
        Fenwick(int n) {
            f = new long[n];
        }

        public void update(int i, long val) {
            for (; i < f.length; i += i & -i) {
                f[i] = Math.max(f[i], val);
            }
        }

        public long preMax(int i) {
            long res = 0;
            for (; i > 0; i &= i - 1) {
                res = Math.max(res, f[i]);
            }
            return res;
        }
    }

    public long maxAlternatingSum(int[] nums, int k) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = nums.length;
        long[] fInc = new long[n];
        long[] fDec = new long[n];
        Fenwick inc = new Fenwick(n + 1);
        Fenwick dec = new Fenwick(n + 1);
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (i >= k) {
                int j = nums[i - k];
                inc.update(n - j, fInc[i - k]);
                dec.update(j + 1, fDec[i - k]);
            }
            int j = Arrays.binarySearch(sorted, x);
            nums[i] = j;
            fInc[i] = dec.preMax(j) + x;
            fDec[i] = inc.preMax(n - 1 - j) + x;
            ans = Math.max(ans, Math.max(fInc[i], fDec[i]));
        }
        return ans;
    }
}
