package algorithm.class71dp6;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-01 9:00
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续
 * 子数组
 * （该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 *
 * 测试用例的答案是一个 32-位 整数。
 * https://leetcode.cn/problems/maximum-product-subarray/description/
 */
public class MaxProduct {
    public int maxProduct(int[] nums) {
        int ans = nums[0];
        for (int i = 1, min = nums[0], max = nums[0], minCur, maxCur; i < nums.length; i++) {
            minCur = Math.min(nums[i], Math.min(nums[i] * max, nums[i] * min));
            maxCur = Math.max(nums[i], Math.max(nums[i] * max, nums[i] * min));
            min = minCur;
            max = maxCur;
            ans = Math.max(ans, max);
        }
        return ans;
    }
}
