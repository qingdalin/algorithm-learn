package leetcode.study.everydayleetcode.every2026;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/25 9:34
 * https://leetcode.cn/problems/minimum-difference-between-highest-and-lowest-of-k-scores/?envType=daily-question&envId=2026-01-25
 */
public class Code_20260125MinimumDifference {

    public static int minimumDifference(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int ans = Integer.MAX_VALUE;
        for (int i = 0, j = i + k - 1; j < n; i++, j++) {
            ans = Math.min(ans, nums[j] - nums[i]);
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    public static void main(String[] args) {
        int[] arr = {9,4,1,7};
        int k = 2;
        System.out.println(minimumDifference(arr, k));
    }
}
