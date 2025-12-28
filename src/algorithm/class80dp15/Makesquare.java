package algorithm.class80dp15;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-30 10:21
 * 你将得到一个整数数组 matchsticks ，其中 matchsticks[i] 是第 i 个火柴棒的长度。
 * 你要用 所有的火柴棍 拼成一个正方形。你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次 。
 *
 * 如果你能使这个正方形，则返回 true ，否则返回 false 。
 * https://leetcode.cn/problems/matchsticks-to-square/description/
 */
public class Makesquare {
    public boolean makesquare(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 4 != 0) {
            return false;
        }
        int n = nums.length;
        int[] dp = new int[1 << n];
        return f(nums, sum / 4, (1 << n) - 1, 0, 4, dp);
    }

    /*
        limit: 边长多少限制
        status: 已经选择的火柴状态
        cur: 当前边的长度
        rest: 还剩余几条边
     */
    private boolean f(int[] nums, int limit, int status, int cur, int rest, int[] dp) {
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
                    ans = f(nums, limit, status ^ (1 << i), 0, rest - 1, dp);
                } else {
                    ans = f(nums, limit, status ^ (1 << i), nums[i] + cur, rest, dp);
                }
                if (ans) {
                    break;
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }
}
