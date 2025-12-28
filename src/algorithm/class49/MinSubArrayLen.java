package algorithm.class49;

/**
 * https://leetcode.cn/problems/minimum-size-subarray-sum/
 * 滑动窗口
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-02-04 19:26
 */
public class MinSubArrayLen {
    public int minSubArrayLen(int target, int[] nums) {
        int ans = Integer.MAX_VALUE;
        for (int l = 0, r = 0, sum = 0; r < nums.length; r++) {
            sum += nums[r];
            while (sum - nums[l] >= target) {
                sum -= nums[l++];
            }
            if (sum >= target) {
                ans = Math.min(ans, r - l + 1);
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}
