package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 14:38
 * https://leetcode.cn/problems/partition-equal-subset-sum/
 */
public class Leetcode416CanPartition {
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int s = 0;
        for (int num : nums) {
            s += num;
        }
        if (s % 2 != 0) {
            return false;
        }
        s = s / 2;
        boolean[][] dp = new boolean[n + 1][s + 1];
        dp[0][0] = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= s; j++) {
                boolean p1 = false;
                if (j >= nums[i]) {
                    p1 = dp[i][j - nums[i]];
                }
                boolean p2 = dp[i][j];
                dp[i + 1][j] = p1 || p2;
            }
        }
        return dp[n][s];
    }

    public boolean canPartition2(int[] nums) {
        int n = nums.length;
        int s = 0;
        for (int num : nums) {
            s += num;
        }
        if (s % 2 != 0) {
            return false;
        }
        int[][] dp = new int[n][s / 2 + 1];
        for (int[] cur : dp) {
            Arrays.fill(cur, -1);
        }
        return f1(n - 1, s / 2, nums, dp);
    }

    private boolean f1(int i, int sum, int[] nums, int[][] dp) {
        if (i < 0) {
            return sum == 0;
        }
        if (dp[i][sum] != -1) {
            return dp[i][sum] == 1;
        }
        boolean p1 = false;
        if (sum >= nums[i]) {
            p1 = f1(i - 1, sum - nums[i], nums, dp);
        }
        boolean p2 = f1(i - 1, sum, nums, dp);
        boolean ans = p1 || p2;
        dp[i][sum] = ans ? 1 : 2;
        return ans;
    }


    public boolean canPartition1(int[] nums) {
        int n = nums.length;
        Map<String, Boolean> dp = new HashMap<>();
        return f(0, 0, 0, nums, dp);
    }

    private boolean f(int i, int sum1, int sum2, int[] nums, Map<String, Boolean> dp) {
        if (i >= nums.length) {
            return sum1 == sum2;
        }
        String key = i + "#" + sum1 + "#" + sum2;
        if (dp.containsKey(key)) {
            return dp.get(key);
        }
        boolean ans = f(i + 1, sum1 + nums[i], sum2, nums, dp)
            || f(i + 1, sum1, sum2 + nums[i], nums, dp);
        dp.put(key, ans);
        return ans;
    }
}
