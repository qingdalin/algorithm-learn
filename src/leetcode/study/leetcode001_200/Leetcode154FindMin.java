package leetcode.study.leetcode001_200;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/2 19:39
 * https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array-ii/
 */
public class Leetcode154FindMin {
    public static int findMin(int[] nums) {
        int ans = Integer.MAX_VALUE;
        for (int num : nums) {
            ans = Math.min(ans, num);
        }
        return ans;
    }

    public static int findMin1(int[] nums) {
        Arrays.sort(nums);
        return nums[0];
    }
}
