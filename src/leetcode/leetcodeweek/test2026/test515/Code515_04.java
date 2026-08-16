package leetcode.leetcodeweek.test2026.test515;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/16 9:50
 * https://leetcode.cn/contest/weekly-contest-515/problems/elevator-requests-iii/
 */
public class Code515_04 {
    public static long elevatorRequests(int n, int start, int[][] requests) {
        int m = requests.length;
        long[][] dp = new long[1 << m][m];
        for (int i = 0; i < m; i++) {
            int[] req = requests[i];
            dp[1 << i][i] = Math.max(Math.abs(req[1] - start), req[0]);
        }
        for (int mask = 1; mask < 1 << m; mask++) {
            if ((mask & (mask - 1)) == 0) {
                continue;
            }
            for (int i = 0; i < m; i++) {
                if (((mask >> i) & 1) == 0) {
                    continue;
                }
                int t = requests[i][0], x = requests[i][1];
                long res = Long.MAX_VALUE;
                int msk = mask ^ (1 << i);
                for (int j = 0; j < m; j++) {
                    if ((msk >> j & 1) > 0) {
                        res = Math.min(res, dp[msk][j]
                            + Math.abs(x - requests[j][1]));
                    }
                }
                dp[mask][i] = Math.max(res, t);
            }
        }
        long ans = Long.MAX_VALUE;
        for (long x : dp[(1 << m) - 1]) {
            ans = Math.min(ans, x);
        }
        return ans;
    }

    public static long elevatorRequests1(int n, int start, int[][] requests) {
        int m = requests.length;
        long[][] dp = new long[1 << m][m];
        for (long[] row : dp) {
            Arrays.fill(row, -1);
        }
        long ans = Long.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            ans = Math.min(ans, dfs((1 << m) - 1, i, start, requests, dp));
        }
        return ans;
    }

    private static long dfs(int mask, int i, int start, int[][] requests, long[][] dp) {
        mask ^= 1 << i;
        int[] req = requests[i];
        int t = req[0], x = req[1];
        if (mask == 0) {
            return Math.max(Math.abs(x - start), t);
        }
        if (dp[mask][i] != -1) {
            return dp[mask][i];
        }
        long res = Long.MAX_VALUE;
        for (int j = 0; j < requests.length; j++) {
            if ((mask >> j & 1) > 0) {
                res = Math.min(res, dfs(mask, j, start, requests, dp)
                    + Math.abs(x - requests[j][1]));
            }
        }
        res = Math.max(res, t);
        dp[mask][i] = res;
        return res;
    }
}
