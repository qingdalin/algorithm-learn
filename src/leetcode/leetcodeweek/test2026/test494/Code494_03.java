package leetcode.leetcodeweek.test2026.test494;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/22 10:02
 * https://leetcode.cn/problems/minimum-removals-to-achieve-target-xor/solutions/3933376/mo-ban-qia-hao-zhuang-man-xing-0-1-bei-b-llbw/
 */
public class Code494_03 {
    public int minRemovals(int[] nums, int target) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(num, max);
        }
        int m = 32 - Integer.numberOfLeadingZeros(max);
        // 比如max是20位，m = 20
        // target是15位，m = 15
        if (m < 32 - Integer.numberOfLeadingZeros(target)) {
            // 数组的最大值也没有target大
            return -1;
        }
        int n = nums.length;
        int[][] f = new int[n + 1][1 << m];
        for (int[] row : f) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        f[0][0] = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = 0; j < (1 << m); j++) {
                f[i + 1][j] = Math.max(f[i][j], f[i][j ^ x] + 1);
            }
        }
        if (f[n][target] < 0) {
            return -1;
        }
        return nums.length - f[n][target];
    }
}
