package leetcode.leetcodeweek.test2026.test516;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/23 8:14
 * https://leetcode.cn/problems/longest-subarray-with-at-most-k-distinct-prime-factors/description/
 */
public class Code516_03 {
    public static int MX = 100001;
    public static List<Integer>[] primeFactors = new ArrayList[MX];
    static {
        Arrays.setAll(primeFactors, a -> new ArrayList<>());
        for (int i = 2; i < MX; i++) {
            if (primeFactors[i].isEmpty()) {
                for (int j = i; j < MX; j += i) {
                    primeFactors[j].add(i);
                }
            }
        }
    }
    public static int longestSubarray(int[] nums, int k) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        int left = 0, ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            for (int p : primeFactors[x]) {
                cnt.merge(p, 1, Integer::sum);
            }
            while (cnt.size() > k) {
                for (int p : primeFactors[nums[left]]) {
                    int c = cnt.merge(p, -1, Integer::sum);
                    if (c == 0) {
                        cnt.remove(p);
                    }
                }
                left++;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }
}
