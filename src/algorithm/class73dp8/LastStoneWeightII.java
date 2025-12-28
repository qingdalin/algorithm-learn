package algorithm.class73dp8;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-12 20:01
 * 有一堆石头，用整数数组 stones 表示。其中 stones[i] 表示第 i 块石头的重量。
 *
 * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
 *
 * 如果 x == y，那么两块石头都会被完全粉碎；
 * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
 * 最后，最多只会剩下一块 石头。返回此石头 最小的可能重量 。如果没有石头剩下，就返回 0。
 * https://leetcode.cn/problems/last-stone-weight-ii/description/
 */
public class LastStoneWeightII {
    public int lastStoneWeightII(int[] stones) {
        // 两个集合 A 和 B
        // 求Sum(A)和Sum(B)的差值
        // 选出一半的数累加和尽可能接近数组累加和sum的一半
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        int near = near(stones, sum / 2);
        return sum - near - near;
    }

    public int near(int[] nums, int t) {
        int[] dp = new int[t + 1];
        // dp[i][j]:前i个数的累加和最大为j且不超过t
        for (int i = 0; i < nums.length; i++) {
            for (int j = t; j >= nums[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }
        return dp[t];
    }
}
