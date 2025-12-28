package algorithm.class70dp5;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-29 19:48
 * 给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
 *
 * 环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ，
 * nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
 *
 * 子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i],
 * nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。
 * https://leetcode.cn/problems/maximum-sum-circular-subarray/description/
 */
public class MaxSubarraySumCircular {
    // 最大的两种情况，一个是当前子数组最大累加和，一种是 数组和 - 当前子数组最小累加和
    public int maxSubarraySumCircular(int[] nums) {
        int all = nums[0], maxSum = nums[0], minSum = nums[0];
        for (int i = 1, maxPre = nums[0], minPre = nums[0]; i < nums.length; i++) {
            all += nums[i];
            maxPre = Math.max(nums[i], maxPre + nums[i]);
            maxSum = Math.max(maxSum, maxPre);
            minPre = Math.min(nums[i], minPre + nums[i]);
            minSum = Math.min(minSum, minPre);
        }
        return all == minSum ? maxSum : Math.max(maxSum, all - minSum);
    }
}
