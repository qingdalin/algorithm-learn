package leetcode.leetcodeweek.test2026.test513;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/2 11:12
 * https://leetcode.cn/problems/count-subarrays-with-even-odd-ratio-ii/description/
 */
public class Code513_04 {
    public long countRatioSubarrays(int[] nums, int a, int b) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + (nums[i] % 2 == 0 ? -b : a);
        }
        long[] sortedS = sum.clone();
        Arrays.sort(sortedS);
        FenwickTree t = new FenwickTree(n + 1);
        long ans = 0;
        for (long s : sum) {
            int x = Arrays.binarySearch(sortedS, s) + 1;
            ans += t.pre(x);
            t.add(x);
        }
        return ans;
    }

    class FenwickTree {
        private final int[] tree;

        public FenwickTree(int n) {
            tree = new int[n + 1]; // 使用下标 1 到 n
        }

        // a[i] 增加 1
        // 1 <= i <= n
        // 时间复杂度 O(log n)
        public void add(int i) {
            for (; i < tree.length; i += i & -i) {
                tree[i]++;
            }
        }

        // 求前缀和 a[1] + ... + a[i]
        // 1 <= i <= n
        // 时间复杂度 O(log n)
        public int pre(int i) {
            int res = 0;
            for (; i > 0; i &= i - 1) {
                res += tree[i];
            }
            return res;
        }
    }
}
