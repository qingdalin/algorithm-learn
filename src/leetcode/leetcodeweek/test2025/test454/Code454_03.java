package leetcode.leetcodeweek.test2025.test454;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 10:23
 * https://leetcode.cn/problems/maximum-product-of-first-and-last-elements-of-a-subsequence/
 */
public class Code454_03 {
    public long maximumProduct(int[] nums, int m) {
        long ans = Long.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int n = nums.length;
        for (int i = m - 1; i < n; i++) {
            int left = nums[i - (m - 1)];
            min = Math.min(min, left);
            max = Math.max(max, left);
            int right = nums[i];
            ans = Math.max(ans, Math.max((long) right * min, (long) right * max));
        }
        return ans;
    }
}
