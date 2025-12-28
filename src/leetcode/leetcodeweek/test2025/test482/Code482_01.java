package leetcode.leetcodeweek.test2025.test482;

import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 9:52
 * https://leetcode.cn/contest/weekly-contest-482/problems/maximum-score-of-a-split/
 */
public class Code482_01 {
    public static long maximumScore(int[] nums) {
        int n = nums.length;
        long sum = 0, cur;
        long min = nums[n - 1];
        long ans = Long.MIN_VALUE;
        for (int i = 0; i < n - 1; i++) {
            sum += nums[i];
        }
        for (int i = n - 2; i >= 0; i--) {
            cur = sum - min;
            ans = Math.max(ans, cur);
            sum -= nums[i];
            min = Math.min(min, nums[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = {10,-1,3,-4,-5};
        int[] arr = {79,70,54,39,83,61};
        System.out.println(maximumScore(arr));
    }
}
