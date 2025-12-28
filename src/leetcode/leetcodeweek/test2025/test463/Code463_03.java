package leetcode.leetcodeweek.test2025.test463;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 9:09
 * https://leetcode.cn/contest/weekly-contest-463/problems/minimum-sum-after-divisible-sum-deletions/
 */
public class Code463_03 {
    public static long minArraySum(int[] arr, int k) {
        long[] dp = new long[k];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;
        long f = 0;
        int sum = 0;
        for (int num : arr) {
            sum = (sum + num) % k;
            f = Math.min(f + num, dp[sum]);
            dp[sum] = f;
        }
        return f;
    }

    public static void main(String[] args) {
        int[] arr = {3,1,4,1,5};
        int k = 3;
        System.out.println(minArraySum(arr, k));
    }
}
