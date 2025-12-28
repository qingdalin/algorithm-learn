package algorithm.class81dp16;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/7 10:36
 * 给你一个长度为 n 的整数数组 nums ，这个数组中至多有 50 个不同的值。
 * 同时你有 m 个顾客的订单 quantity ，其中，整数 quantity[i] 是第 i 位顾客订单的数目。
 * 请你判断是否能将 nums 中的整数分配给这些顾客，且满足：
 * 第 i 位顾客 恰好 有 quantity[i] 个整数。
 * 第 i 位顾客拿到的整数都是 相同的 。
 * 每位顾客都满足上述两个要求。
 * 如果你可以分配 nums 中的整数满足上面的要求，那么请返回 true ，否则返回 false 。
 * https://leetcode.cn/problems/distribute-repeating-integers/description/
 */
public class CanDistribute {
    public boolean canDistribute(int[] nums, int[] quantity) {
        Arrays.sort(nums);
        int n = 1;
        int len = nums.length;
        for (int i = 1; i < len; i++) {
            if (nums[i - 1] != nums[i]) {
                n++;
            }
        }
        // cnt是统计nums里数字出现的次数
        int[] cnt = new int[n];
        int c = 1;
        for (int i = 1, j = 0; i < len; i++) {
            if (nums[i - 1] != nums[i]) {
                cnt[j++] = c;
                c = 1;
            } else {
                c++;
            }
        }
        cnt[n - 1] = c;
        int m = quantity.length;
        int[] sum = new int[1 << m];
        for (int i = 0, v, h; i < m; i++) {
            v = quantity[i];
            h = 1 << i;
            for (int j = 0; j < h; j++) {
                sum[h | j] = sum[j] + v;
            }
        }
        int[][] dp = new int[1 << m][n];
        return f(cnt, sum, (1 << m) - 1, 0, dp);
    }

    private boolean f(int[] cnt, int[] sum, int status, int index, int[][] dp) {
        if (status == 0) {
            // 如果状态全是0，说明搞定
            return true;
        }
        if (index == cnt.length) {
            // 状态不是0，已经没有可用的数字
            return false;
        }
        if (dp[status][index] != 0) {
            return dp[status][index] == 1;
        }
        boolean ans = false;
        int k = cnt[index];
        for (int j = status; j > 0; j = (j - 1) & status) {
            if (sum[j] <= k && f(cnt, sum, status ^ j, index + 1, dp)) {
                ans = true;
                break;
            }
        }
        if (!ans) {
            // 说明上边没有搞定，则不搞定当前状态，往下继续
            ans = f(cnt, sum, status, index + 1, dp);
        }
        dp[status][index] = ans ? 1 : -1;
        return ans;
    }
}
