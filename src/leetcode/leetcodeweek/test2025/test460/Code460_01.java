package leetcode.leetcodeweek.test2025.test460;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 9:21
 * https://leetcode.cn/contest/weekly-contest-460/problems/maximum-median-sum-of-subsequences-of-size-3/
 */
public class Code460_01 {
    // 2,1,3,2,1,3
    // 1 1 2 2 3 3
    //
    public static long maximumMedianSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        long ans = 0;
        for (int l = 0, r = n - 2; l < r; l++, r -= 2) {
            ans += nums[r];
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {2,1,3,2,1,3};
        int[] arr = new int[] {1,1,10,10,10,10};
        System.out.println(maximumMedianSum(arr));
    }
}
