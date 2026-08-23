package leetcode.leetcodeweek.test2026.test516;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/23 8:14
 * https://leetcode.cn/problems/valid-k-unique-subarrays-i/description/
 */
public class Code516_04 {
    private static final Random random = new Random();
    public boolean[] validSubarrays(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        Map<Integer, Long> hash = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (!hash.containsKey(x)) {
                hash.put(x, random.nextLong());
            }
            sum[i + 1] = sum[i] ^ hash.get(x);
        }
        List<int[]>[] groups = new ArrayList[n];
        Arrays.setAll(groups, x -> new ArrayList<>());
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            groups[q[1]].add(new int[] {q[0], i});
        }
        FenwickTree t = new FenwickTree(n);
        Map<Integer, Integer> last = new HashMap<>(hash.size());
        boolean[] ans = new boolean[queries.length];
        for (int r = 0; r < n; r++) {
            int x = nums[r];
            if (last.containsKey(x)) {
                t.update(last.get(x), -1);
            }

            last.put(x, r);
            t.update(r, 1);
            for (int[] p : groups[r]) {
                int l = p[0];
                ans[p[1]] = sum[r + 1] == sum[l] && t.query(l, r) == k;
            }
        }
        return ans;
    }

    class FenwickTree {
        private final int[] tree;
        public FenwickTree(int n) {
            tree = new int[n + 1];
        }

        public void update(int i, int val) {
            for (i++; i < tree.length; i += i & -i) {
                tree[i] += val;
            }
        }

        public int pre(int i) {
            int res = 0;
            for (i++; i > 0; i &= i - 1) {
                res += tree[i];
            }
            return res;
        }

        public int query(int l, int r) {
            return pre(r) - pre(l - 1);
        }
    }
}
