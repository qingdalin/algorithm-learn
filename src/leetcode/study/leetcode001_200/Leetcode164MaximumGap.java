package leetcode.study.leetcode001_200;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/2 20:01
 * https://leetcode.cn/problems/maximum-gap/
 */
public class Leetcode164MaximumGap {
    public int maximumGap(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        Arrays.sort(nums);
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n - 1; i++) {
            ans = Math.max(ans, nums[i + 1] - nums[i]);
        }
        return ans;
    }
}
