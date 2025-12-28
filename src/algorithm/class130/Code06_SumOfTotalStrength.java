package algorithm.class130;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/9 11:31
 * // 巫师力量和
 * // 题目可以简化为如下的描述
 * // 给定一个长度为n的数组arr，下标从0开始
 * // 任何一个子数组的指标为，子数组累加和 * 子数组中最小值
 * // 返回arr中所有子数组指标的累加和，答案对 1000000007 取模
 * // 1 <= n <= 10^5
 * // 1 <= arr[i] <= 10^9
 * // 测试链接 : https://leetcode.cn/problems/sum-of-total-strength-of-wizards/
 */
public class Code06_SumOfTotalStrength {
    public static int mod = 1000000007;
    public static int totalStrength(int[] arr) {
        int n = arr.length;
        long ans = 0;
        // 单调栈
        int[] stack = new int[n];
        int size = 0;
        // 前缀和的前缀和
        int[] sumsum = new int[n];
        int pre = arr[0] % mod;
        sumsum[0] = pre;
        for (int i = 1; i < n; i++) {
            pre = (pre + arr[i]) % mod;
            sumsum[i] = (sumsum[i - 1] + pre) % mod;
        }
        for (int i = 0; i < n; i++) {
            while (size > 0 && arr[stack[size - 1]] >= arr[i]) {
                int m = stack[--size];
                int l = size > 0 ? stack[size - 1] : -1;
                ans = (ans + sum(arr, sumsum, l, m, i)) % mod;
            }
            stack[size++] = i;
        }
        while (size > 0) {
            int m = stack[--size];
            int l = size > 0 ? stack[size - 1] : -1;
            ans = (ans + sum(arr, sumsum, l, m, n)) % mod;
        }
        return (int) ans;
    }
    // 当前来到m，左边比m小的在3，右边比m小的在10
    //       l       m      r
    // 0 1 2 3 4 5 6 7 8 9 10
    // 4-7 4-8 4-9
    // 5-7 5-8 5-9
    // 6-7 6-8 6-9
    // 7-7 7-8 7-9
    // 以上是需要求的子数组和
    //
    private static int sum(int[] arr, int[] sumsum, int l, int m, int r) {
        long left = sumsum[r - 1] % mod;
        if (m - 1 >= 0) {
            // sumsum[0-9] - sumsum[0-6] = sumsum[7-9]
            left = (left - sumsum[m - 1] + mod) % mod;
        }
        // sumsum[7-9] * 4
        left = (left * (m - l)) % mod;
        long right = 0;
        if (m - 1 >= 0) {
            // sumsum[0-6]
            right = (right + sumsum[m - 1]) % mod;
        }
        if (l - 1 >= 0) {
            // sumsum[0-6] - sumsum[0-2] = sumsum[3-6]
            right = (right - sumsum[l - 1] + mod) % mod;
        }
        // sumsum[3-6] * 3
        right = (right * (r - m)) % mod;
        // left - right 就是所有子数组和
        // 4-7 4-8 4-9
        // 5-7 5-8 5-9
        // 6-7 6-8 6-9
        // 7-7 7-8 7-9
        // sum[7] - sum[3-6]
        // sum[7]就是原始数组的 1-1 1-2 1-3 1-4 1-5 1-6 1-7
        // sum[3]就是原始数组的 1-1 1-2 1-3 就是4-7
        // sum[4]就是原始数组的 1-1 1-2 1-3 1-4 就是5-7
        // sum[5]就是原始数组的 1-1 1-2 1-3 1-4 1-5 就是6-7
        // sum[6]就是原始数组的 1-1 1-2 1-3 1-4 1-5 1-6 就是7-7

        // sum[8]就是原始数组的 1-1 1-2 1-3 1-4 1-5 1-6 1-7 1-8
        // sum[3]就是原始数组的 1-1 1-2 1-3 就是4-8
        // sum[4]就是原始数组的 1-1 1-2 1-3 1-4 就是5-8
        // sum[5]就是原始数组的 1-1 1-2 1-3 1-4 1-5 就是6-8
        // sum[6]就是原始数组的 1-1 1-2 1-3 1-4 1-5 1-6 就是7-8

        // sum[9]就是原始数组的 1-1 1-2 1-3 1-4 1-5 1-6 1-7 1-8 1 -9
        // sum[3]就是原始数组的 1-1 1-2 1-3 就是4-9
        // sum[4]就是原始数组的 1-1 1-2 1-3 1-4 就是5-9
        // sum[5]就是原始数组的 1-1 1-2 1-3 1-4 1-5 就是6-9
        // sum[6]就是原始数组的 1-1 1-2 1-3 1-4 1-5 1-6 就是7-9
        // 所有子数组的和已经有了 乘以当前m的值
        return (int) (((left - right + mod) % mod) * arr[m] % mod);
    }
}
