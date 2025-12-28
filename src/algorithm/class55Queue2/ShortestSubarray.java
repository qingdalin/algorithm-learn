package algorithm.class55Queue2;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-16 10:29
 * https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/
 * 前缀和加单调队列
 */
public class ShortestSubarray {
    public static int MAXN = 1000001;
    public static long[] sum = new long[MAXN];
    public static int[] queue = new int[MAXN];
    public static int h, t;
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int ans = Integer.MAX_VALUE;
        h = t = 0;
        for (int i = 0; i <= n; i++) {
            while (h < t && sum[i] - sum[queue[h]] >= k) {
                ans = Math.min(ans, i - queue[h++]);
            }
            while (h < t && sum[queue[t - 1]] >= sum[i]) {
                t--;
            }
            queue[t++] = i;
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
