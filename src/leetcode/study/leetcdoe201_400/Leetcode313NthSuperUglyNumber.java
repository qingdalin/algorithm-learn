package leetcode.study.leetcdoe201_400;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 13:49
 * https://leetcode.cn/problems/super-ugly-number/
 */
public class Leetcode313NthSuperUglyNumber {
    public static int nthSuperUglyNumber(int n, int[] primes) {
        if (n == 1) {
            return 1;
        }
        long[] dp = new long[n + 1];
        int m = primes.length;
        int[] index = new int[m];
        long[] arr = new long[m];
        Arrays.fill(arr, 1);
        for (int i = 1; i <= n; i++) {
            long min = Long.MAX_VALUE;
            for (int j = 0; j < m; j++) {
                min = Math.min(min, arr[j]);
            }
            dp[i] = min;
            for (int j = 0; j < m; j++) {
                if (arr[j] == min) {
                    index[j]++;
                    arr[j] = dp[index[j]] * primes[j];
                }
            }
        }
        return (int) dp[n];
    }

    public static void main(String[] args) {
        int n = 12;
        int[] arr = new int[] {2,7,13,19};
        System.out.println(nthSuperUglyNumber(n, arr));
    }
}
