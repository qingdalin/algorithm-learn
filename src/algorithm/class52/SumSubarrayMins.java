package algorithm.class52;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-09 13:48
 * // 子数组的最小值之和
 * // 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
 * // 由于答案可能很大，答案对 1000000007 取模
 * // 测试链接 : https://leetcode.cn/problems/sum-of-subarray-minimums/
 */
public class SumSubarrayMins {
    public static int r;
    public static int MOD = 1000000007;
    public static int[] stack = new int[30000];
    public int sumSubarrayMins(int[] arr) {
        int cur = 0;
        long ans = 0L;
        r = 0;
        for (int i = 0; i < arr.length; i++) {
            while (r > 0 && arr[stack[r - 1]] >= arr[i]) {
                cur = stack[--r];
                int left = r > 0 ? stack[r - 1] : -1;
                ans = (ans + (long) (cur - left) * (i - cur) * arr[cur]) % MOD;
            }
            stack[r++] = i;
        }
        while (r > 0) {
            cur = stack[--r];
            int left = r > 0 ? stack[r - 1] : -1;
            ans = (ans + (long) (cur - left) * (arr.length - cur) * arr[cur]) % MOD;
        }
        return (int) ans;
    }
}
