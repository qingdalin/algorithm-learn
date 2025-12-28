package algorithm.class83dp18;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/8 19:54
 * 你打算利用空闲时间来做兼职工作赚些零花钱。
 *
 * 这里有 n 份兼职工作，每份工作预计从 startTime[i] 开始到 endTime[i] 结束，报酬为 profit[i]。
 *
 * 给你一份兼职工作表，包含开始时间 startTime，结束时间 endTime 和预计报酬 profit 三个数组，请你计算并返回可以获得的最大报酬。
 *
 * 注意，时间上出现重叠的 2 份工作不能同时进行。
 *
 * 如果你选择的工作在时间 X 结束，那么你可以立刻进行在时间 X 开始的下一份工作。
 * https://leetcode.cn/problems/maximum-profit-in-job-scheduling/description/
 */
public class JobScheduling {
    public static int MAXN = 50001;
    public static int[][] job = new int[MAXN][3];
    public static int[] dp = new int[MAXN];
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        for (int i = 0; i < n; i++) {
            job[i][0] = startTime[i];
            job[i][1] = endTime[i];
            job[i][2] = profit[i];
        }
        // 按照结束时间排序
        Arrays.sort(job, 0, n, (a, b) -> a[1] - b[1]);
        // dp[i]: 第i份工作获得的最大收益
        dp[0] = job[0][2];
        for (int i = 1, start; i < n; i++) {
            start = job[i][0];
            dp[i] = job[i][2];
            if (job[0][1] <= start) {
                dp[i] += dp[find(i - 1, start)];
            }
            dp[i] = Math.max(dp[i], dp[i - 1]);
        }
        return dp[n - 1];
    }

    private int find(int i, int start) {
        int ans = 0;
        int l = 0;
        int r = i;
        int m = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (job[m][1] <= start) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }
}
