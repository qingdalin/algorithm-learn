package leetcode.leetcodeweek.test2026.test503;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/24 8:55
 * https://leetcode.cn/contest/weekly-contest-503/problems/minimum-operations-to-sort-a-permutation/
 */
public class Code503_03 {
    public static int minOperations(int[] nums) {
        int n = nums.length;
        int cnt = 0, p = 0;
        for (int i = 1; i < n && cnt < 2; i++) {
            if (nums[i - 1] > nums[i]) {
                cnt++;
                p = i;
            }
        }
        if (cnt == 0) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        if (cnt == 1 && nums[0] > nums[n - 1]) {
            return Math.min(p, n - p + 2);
        }
        cnt = p = 0;
        for (int i = 1; i < n && cnt < 2; i++) {
            if (nums[i - 1] < nums[i]) {
                cnt++;
                p = i;
            }
        }
        if (cnt == 0) {
            return 1;
        }
        if (cnt == 1 && nums[0] < nums[n - 1]) {
            ans = Math.min(ans, Math.min(p, n - p) + 1);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int minOperations1(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            if (Math.abs(nums[i] - nums[i + 1]) != 1 && Math.abs(nums[i] - nums[i + 1]) != n - 1) {
                return -1;
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (i != nums[i]) {
                ans++;
            }
        }
        return ans;
    }
}
