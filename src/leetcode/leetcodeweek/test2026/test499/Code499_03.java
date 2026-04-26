package leetcode.leetcodeweek.test2026.test499;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/26 9:16
 * https://leetcode.cn/problems/minimum-operations-to-make-array-non-decreasing/description/
 */
public class Code499_03 {
    public long minOperations(int[] nums) {
        long ans = 0;
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            ans += Math.max(nums[i] - nums[i - 1], 0);
        }
        return ans;
    }
}
