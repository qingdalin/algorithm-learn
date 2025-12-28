package leetcode.leetcodeweek.test2025.test480;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/14 9:47
 * https://leetcode.cn/contest/weekly-contest-480/problems/absolute-difference-between-maximum-and-minimum-k-elements/
 */
public class Code480_01 {
    public static int absDifference(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int sum1 = 0, sum2 = 0;
        for (int i = 0, j = n - 1; i < n && k > 0; i++, j--, k--) {
            sum1 += nums[i];
            sum2 += nums[j];
        }
        return Math.abs(sum2 - sum1);
    }
}
