package algorithm.class73dp8;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-05 19:41
 * 给你一个非负整数数组 nums 和一个整数 target 。
 *
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 *
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 * https://leetcode.cn/problems/target-sum/description/
 */
public class FindTargetSumWays {
    public int findTargetSumWays1(int[] nums, int target) {
        return f1(nums, target, 0, 0);
    }

    private int f1(int[] nums, int target, int i, int sum) {
        if (i == nums.length) {
            return sum == target ? 1 : 0;
        }
        return f1(nums, target, i + 1, sum + nums[i]) + f1(nums, target, i + 1, sum - nums[i]);
    }

    public int findTargetSumWays2(int[] nums, int target) {
        Map<Integer, Map<Integer, Integer>> dp = new HashMap<>();
        return f2(nums, target, 0, 0, dp);
    }

    private int f2(int[] nums, int target, int i, int sum, Map<Integer, Map<Integer, Integer>> dp) {
        if (i == nums.length) {
            return sum == target ? 1 : 0;
        }
        if (dp.containsKey(i) && dp.get(i).containsKey(sum)) {
            return dp.get(i).get(sum);
        }
        int ans = f2(nums, target, i + 1, sum + nums[i], dp) + f2(nums, target, i + 1, sum - nums[i], dp);
        dp.putIfAbsent(i, new HashMap<>());
        dp.get(i).put(sum, ans);
        return ans;
    }

    public int findTargetSumWays3(int[] nums, int target) {
        int s = 0;
        for (int num : nums) {
            s += num;
        }
        if (target < -s || target > s) {
            return 0;
        }
        int n = nums.length;
        int m = 2 * s + 1;
        int[][] dp = new int[n + 1][m];
        dp[n][target + s] = 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = -s; j <= +s; j++) {
                if (j + nums[i] + s < m) {
                    dp[i][j + s] = dp[i + 1][j + nums[i] + s];
                }
                if (j - nums[i] + s >= 0) {
                    dp[i][j + s] += dp[i + 1][j - nums[i] + s];
                }
            }
        }
        return dp[0][s];
    }


    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        // target大于sum或者 target和sum的奇偶性不一样，直接返回
        if (target > sum || ((target & 1) ^ (sum & 1)) == 1) {
            return 0;
        }
        // [1, 2, 3, 4, 5] target == 3
        // 正数的集合A = {1, 3, 5}
        // 负数的集合B = {2, 4}
        // target = sum(A) - sum(B)
        // 两边同时加 sum(A) + sum(B)
        // target + sum(A) + sum(B) = sum(A) - sum(B) + sum(A) + sum(B)
        // target + sum = 2 * sum(A)
        // sum(A) = (target + sum) / 2,即求子序列累加和为A的数量
        return subSets(nums, (target + sum) / 2);
    }

    private int subSets(int[] nums, int t) {
        if (t < 0) {
            return 0;
        }
        int[] dp = new int[t + 1];
        // 一个数也没有，子序列和为0,有一个空集
        // dp[i][j] = 不要i和要i位置(不要i且子序列和必须为j)
        // dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i]]
        dp[0] = 1;
        for (int num : nums) {
            for (int j = t; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        return dp[t];
    }
}
