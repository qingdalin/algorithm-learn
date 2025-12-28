package algorithm.class72dp7;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-02 8:59
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 *
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的
 * 子序列
 * https://leetcode.cn/problems/longest-increasing-subsequence/description/
 */
public class LengthOfLIS {
    public int lengthOfLIS1(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        // 目前所有长度为i + 1的递增子序列，最小结尾
        int[] ends = new int[n];
        int len = 0;
        for (int i = 0, find; i < n; i++) {
            find = bs1(ends, len, nums[i]);
            if (find == -1) {
                ends[len++] = nums[i];
            } else {
                ends[find] = nums[i];
            }
        }
        return len;
    }

    private int bs1(int[] ends, int len, int num) {
        int l = 0, r = len - 1;
        int m = 0;
        int ans = -1;
        while (l <= r) {
            m = (l + r) / 2;
            if (num <= ends[m]) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }
}
