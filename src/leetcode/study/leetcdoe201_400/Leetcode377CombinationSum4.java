package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/30 20:10
 * https://leetcode.cn/problems/combination-sum-iv/
 */
public class Leetcode377CombinationSum4 {
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] <= i) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }

    public int combinationSum41(int[] nums, int target) {
        int[] dp = new int[target + 1];
        for (int i = 0; i <= target; i++) {
            dp[i] = -1;
        }
        return f(nums, target, dp);
    }

    private int f(int[] nums, int i, int[] dp) {
        if (i == 0) {
            return 1;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int ans = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] <= i) {
                ans += f(nums, i - nums[j], dp);
            }
        }
        dp[i] = ans;
        return ans;
    }

}
