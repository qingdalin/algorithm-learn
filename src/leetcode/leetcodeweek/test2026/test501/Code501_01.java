package leetcode.leetcodeweek.test2026.test501;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/10 10:12
 * https://leetcode.cn/contest/weekly-contest-501/problems/concatenate-array-with-reverse/description/
 */
public class Code501_01 {
    public int[] concatWithReverse(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n * 2];
        for (int i = 0; i < n; i++) {
            ans[i] = nums[i];
        }
        for (int i = n, j = n - 1; i < n * 2; i++, j--) {
            ans[i] = nums[j];
        }
        return ans;
    }
}
