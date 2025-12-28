package leetcode.leetcodeweek.test2025.test454;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 10:23
 * https://leetcode.cn/contest/weekly-contest-454/problems/count-special-triplets/
 */
public class Code454_02 {
    public int specialTriplets(int[] nums) {
        final int MOD = 1000000007;
        long ans = 0;

        // mp1[x]: count of element x before the current middle element
        // mp2[x]: count of element x after the current middle element
        Map<Integer, Integer> pre = new HashMap<>();
        Map<Integer, Integer> suf = new HashMap<>();

        // Initialize mp2 with all elements (all start as "after")
        for (int x : nums) {
            suf.put(x, suf.getOrDefault(x, 0) + 1);
        }

        // Process each element as the middle element
        for (int x : nums) {
            // Remove current element from mp2 (it's now the middle)
            suf.put(x, suf.get(x) - 1);

            // Calculate valid triplets where current x is middle
            int countBefore = pre.getOrDefault(x * 2, 0);
            int countAfter = suf.getOrDefault(x * 2, 0);
            ans = (ans + (long)countBefore * countAfter) % MOD;

            // Add current element to mp1 (for future elements)
            pre.put(x, pre.getOrDefault(x, 0) + 1);
        }

        return (int)ans;
    }
}
