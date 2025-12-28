package algorithm.class70dp5;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-31 19:30
 * 沿街有一排连续的房屋。每间房屋内都藏有一定的现金。现在有一位小偷计划从这些房屋中窃取现金。
 *
 * 由于相邻的房屋装有相互连通的防盗系统，所以小偷 不会窃取相邻的房屋 。
 *
 * 小偷的 窃取能力 定义为他在窃取过程中能从单间房屋中窃取的 最大金额 。
 *
 * 给你一个整数数组 nums 表示每间房屋存放的现金金额。形式上，从左起第 i 间房屋中放有 nums[i] 美元。
 *
 * 另给你一个整数 k ，表示窃贼将会窃取的 最少 房屋数。小偷总能窃取至少 k 间房屋。
 *
 * 返回小偷的 最小 窃取能力。
 * https://leetcode.cn/problems/house-robber-iv/description/
 */
public class Rob3 {
    public int minCapability(int[] nums, int k) {
        int n = nums.length, l = nums[0], r = nums[0];
        for (int i = 1; i < n; i++) {
            l = Math.min(l, nums[i]);
            r = Math.max(r, nums[i]);
        }
        int ans = 0, m;
        while (l <= r) {
            m = (l + r) / 2;
            if (mostRob(nums, n, m) >= k) {
                // 能力m可以偷取k，更新答案，往左二分
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    private int mostRob(int[] nums, int n, int ability) {
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] <= ability) {
                ans++;
                i++;
            }
        }
        return ans;
    }

    private int mostRob2(int[] nums, int n, int ability) {
        if (n == 1) {
            return nums[0] <= ability ? 1 : 0;
        }
        if (n == 2) {
            return (nums[0] <= ability || nums[1] <= ability) ? 1 : 0;
        }
        int cur = 0;
        int prePre = nums[0] <= ability ? 1 : 0;
        int pre = (nums[0] <= ability || nums[1] <= ability) ? 1 : 0;
        for (int i = 2; i < n; i++) {
            // i位置的房间偷的话，加上dp[i - 2]位置的房间
            // i位置的房间不偷，就是dp[i - 1]的房间数
            // 两者去最大值
            cur = Math.max(pre, (nums[i] <= ability ? 1 : 0) + prePre);
            prePre = pre;
            pre = cur;
        }
        return cur;
    }

    private int mostRob1(int[] nums, int n, int ability) {
        if (n == 1) {
            return nums[0] <= ability ? 1 : 0;
        }
        if (n == 2) {
            return (nums[0] <= ability || nums[1] <= ability) ? 1 : 0;
        }
        int[] dp = new int[n];
        dp[0] = nums[0] <= ability ? 1 : 0;
        dp[1] = (nums[0] <= ability || nums[1] <= ability) ? 1 : 0;
        for (int i = 2; i < n; i++) {
            // i位置的房间偷的话，加上dp[i - 2]位置的房间
            // i位置的房间不偷，就是dp[i - 1]的房间数
            // 两者去最大值
            dp[i] = Math.max(dp[i - 1], (nums[i] <= ability ? 1 : 0) + dp[i - 2]);
        }
        return dp[n - 1];
    }
}
