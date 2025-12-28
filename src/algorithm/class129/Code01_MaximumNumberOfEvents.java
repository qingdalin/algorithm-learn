package algorithm.class129;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/3 8:32
 * // 参加会议II
 * // 给定n个会议，每个会议有开始时间、结束时间、收益三个值
 * // 参加会议就能得到收益，但是同一时间只能参加一个会议
 * // 一共能参加k个会议，如果选择参加某个会议，那么必须完整的参加完这个会议
 * // 会议结束日期是包含在会议内的，一个会议的结束时间等于另一个会议的开始时间，不能两个会议都参加
 * // 返回能得到的会议价值最大和
 * // 1 <= n * k <= 10^6
 * // 1 <= 开始时间、结束时间 <= 10^9
 * // 1 <= 收益 <= 10^6
 * // 测试链接 : https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/
 */
public class Code01_MaximumNumberOfEvents {
    public static int maxValue(int[][] events, int k) {
        int n = events.length;
        int[][] dp = new int[n][k + 1];
        // 按照结束时间排序
        Arrays.sort(events, (a, b) -> a[1] - b[1]);
        // dp[i][j]:表示前i个会议最多参加j个的最大收益，i越大收益越大有单调性可以二分
        for (int j = 1; j <= k; j++) {
            // 前一个会议的最大收益，都是第一个会议的收益
            dp[0][j] = events[0][2];
        }
        for (int i = 1; i < n; i++) {
            // 找寻小于当前会议开始时间的，尽量靠右的会议
            int pre = find(events, i - 1, events[i][0]);
            for (int j = 1; j <= k; j++) {
                // 当前会议选和不选的收益选最大
                dp[i][j] = Math.max(dp[i - 1][j], (pre == -1 ? 0 : dp[pre][j - 1]) + events[i][2]);
            }
        }
        return dp[n - 1][k];
    }

    private static int find(int[][] events, int i, int s) {
        int l = 0, r = i, mid = 0;
        int ans = -1;
        while (l <= r) {
            mid = (l + r) / 2;
            if (events[mid][1] < s) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }
}
