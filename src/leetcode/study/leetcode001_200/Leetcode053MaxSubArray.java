package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/18 19:06
 * https://leetcode.cn/problems/maximum-subarray/
 */
public class Leetcode053MaxSubArray {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int ans = nums[0];
        int pre = nums[0];
        for (int i = 1; i < n; i++) {
            pre = Math.max(nums[i], nums[i] + pre);
            ans = Math.max(ans, pre);
        }
        return ans;
    }
}
