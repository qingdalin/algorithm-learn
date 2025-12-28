package leetcode.leetcodeweek.test2025.test468;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 9:57
 * https://leetcode.cn/contest/weekly-contest-468/problems/maximum-total-subarray-value-i/
 */
public class Code468_02 {
    public long maxTotalValue(int[] nums, int k) {
        long min = Integer.MAX_VALUE, max = -1;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        return k * (max - min);
    }
}
