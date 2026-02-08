package leetcode.leetcodeweek.test2026.test488;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/2/8 10:35
 * https://leetcode.cn/contest/weekly-contest-488/problems/count-dominant-indices/
 */
public class Code488_01 {
    public static int dominantIndices(int[] nums) {
        int n = nums.length;
        int[] preSum = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            preSum[i] = sum;
        }
        int ans = 0;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] * (n - 1 - i) > (preSum[n - 1] - preSum[i])) {
                ans++;
            }
        }
        return ans;
    }
}
