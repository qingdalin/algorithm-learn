package leetcode.leetcodeweek.test2026.test487;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/2/1 10:18
 * https://leetcode.cn/contest/weekly-contest-487/problems/final-element-after-subarray-deletions/
 */
public class Code487_02 {
    // 2 9 3 4 6 3
    // 2 3 3 4 6 9
    //
    public static int finalElement(int[] nums) {
        return Math.max(nums[0], nums[nums.length - 1]);
    }
}
