package leetcode.leetcodeweek.test2026.test513;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/2 10:12
 * https://leetcode.cn/contest/weekly-contest-513/problems/maximize-pair-strength-using-gcd/description/
 */
public class Code513_01 {
    public static long maxPairStrength(int[] nums) {
        long ans = Long.MIN_VALUE;
        int n = nums.length;
        Arrays.sort(nums);
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                long mul = (long) nums[i] * nums[j];
                if (mul <= ans) {
                    break;
                }
                long gcd = gcd(nums[i], nums[j]);
                ans = Math.max(ans, mul / (gcd * gcd));
            }
        }
        return ans;
    }

    public static long maxPairStrength1(int[] nums) {
        long ans = Long.MIN_VALUE;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long gcd = gcd(nums[i], nums[j]);
                long cur = ((long) nums[i] * nums[j]) / (gcd * gcd);
                ans = Math.max(ans, cur);
            }
        }
        return ans;
    }

    public static long gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
