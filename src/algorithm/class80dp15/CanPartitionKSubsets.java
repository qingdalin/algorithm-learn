package algorithm.class80dp15;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-30 10:33
 * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
 * https://leetcode.cn/problems/matchsticks-to-square/description/，和火柴拼接正方形一样，只是边是k
 * https://leetcode.cn/problems/partition-to-k-equal-sum-subsets/description/
 */
public class CanPartitionKSubsets {
    public boolean canPartitionKSubsets1(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % k != 0) {
            return false;
        }
        int n = nums.length;
        int[] dp = new int[1 << n];
        return f1(nums, sum / k, (1 << n) - 1, 0, k, dp);
    }
    private boolean f1(int[] nums, int limit, int status, int cur, int rest, int[] dp) {
        if (rest == 0) {
            // 如果4条边全部拼接好，并且火柴全部使用
            return status == 0;
        }
        if (dp[status] != 0) {
            return dp[status] == 1;
        }
        boolean ans = false;
        for (int i = 0; i < nums.length; i++) {
            if ((status & (1 << i)) != 0 && nums[i] + cur <= limit) {
                if (nums[i] + cur == limit) {
                    // 拼成一条边，rest减一，cur归0
                    ans = f1(nums, limit, status ^ (1 << i), 0, rest - 1, dp);
                } else {
                    ans = f1(nums, limit, status ^ (1 << i), nums[i] + cur, rest, dp);
                }
                if (ans) {
                    break;
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % k != 0) {
            return false;
        }
        Arrays.sort(nums);
        return f2(new int[k], sum / k, nums, nums.length - 1);
    }

    private boolean f2(int[] group, int target, int[] nums, int index) {
        if (index < 0) {
            return true;
        }
        int m = group.length;
        for (int i = 0; i < m; i++) {
            if (group[i] + nums[index] <= target) {
                group[i] += nums[index];
                if (f2(group, target, nums, index - 1)) {
                    return true;
                }
                group[i] -= nums[index];
                while (i + 1 < m && group[i] == group[i + 1]) {
                    i++;
                }
            }
        }
        return false;
    }

}
