package algorithm.class91greedy03;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/24 19:45
 * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 *
 * 请你找出符合题意的 最短 子数组，并输出它的长度。
 * https://leetcode.cn/problems/shortest-unsorted-continuous-subarray/description/
 */
public class FindUnsortedSubarray {
    public int findUnsortedSubarray(int[] nums) {
        int n = nums.length;
        int right = -1;
        int max = Integer.MIN_VALUE;
        // max > 当前值，不达标，记录right
        for (int i = 0; i < n; i++) {
            if (max > nums[i]) {
                right = i;
            }
            max = Math.max(max, nums[i]);
        }
        int left = n;
        int min = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            if (min < nums[i]) {
                left = i;
            }
            min = Math.min(min, nums[i]);
        }
        return Math.max(0, (right - left + 1));
    }
}
