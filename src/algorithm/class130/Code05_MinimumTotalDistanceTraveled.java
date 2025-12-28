package algorithm.class130;

import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/9 10:16
 * // 最小移动总距离
 * // 所有工厂和机器人都分布在x轴上
 * // 给定长度为n的二维数组factory，factory[i][0]为i号工厂的位置，factory[i][1]为容量
 * // 给定长度为m的一维数组robot，robot[j]为第j个机器人的位置
 * // 每个工厂所在的位置都不同，每个机器人所在的位置都不同，机器人到工厂的距离为位置差的绝对值
 * // 所有机器人都是坏的，但是机器人可以去往任何工厂进行修理，但是不能超过某个工厂的容量
 * // 测试数据保证所有机器人都可以被维修，返回所有机器人移动的最小总距离
 * // 1 <= n、m <= 100
 * // -10^9 <= factory[i][0]、robot[j] <= +10^9
 * // 0 <= factory[i][1] <= m
 * // 测试链接 : https://leetcode.cn/problems/minimum-total-distance-traveled/
 */
public class Code05_MinimumTotalDistanceTraveled {
    public static long NA = Long.MAX_VALUE;
    public static int MAXN = 101;
    public static int MAXM = 101;
    public static int[] rot = new int[MAXM];
    public static int[][] fac = new int[MAXN][2];
    public static long[][] dp = new long[MAXN][MAXM];
    public static long[] sum = new long[MAXM];
    public static int[] queue = new int[MAXM];
    public static int n, m, l, r;
    public static long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        build(robot, factory);
        for (int i = 1, cap; i <= n; i++) {
            cap = fac[i][1];
            // 前缀和
            for (int j = 1; j <= m; j++) {
                sum[j] = sum[j - 1] + dist(i, j);
            }
            l = r = 0;
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (value(i, j) != NA) {
                    while (l < r && value(i, queue[r - 1]) >= value(i, j)) {
                        r--;
                    }
                    queue[r++] = j;
                }
                if (l < r && queue[l] == j - cap) {
                    l++;
                }
                if (l < r) {
                    dp[i][j] = Math.min(dp[i][j], value(i, queue[l]) + sum[j]);
                }
            }
        }
        return dp[n][m];
    }

    private static long value(int i, int j) {
        if (dp[i - 1][j - 1] == NA) {
            return NA;
        }
        return dp[i - 1][j - 1] - sum[j - 1];
    }

    private static long dist(int i, int j) {
        return (long) Math.abs(fac[i][0] - rot[j]);
    }

    private static void build(List<Integer> robot, int[][] factory) {
        n = factory.length;
        m = robot.size();
        for (int i = 1; i <= n; i++) {
            fac[i][0] = factory[i - 1][0];
            fac[i][1] = factory[i - 1][1];
        }
        for (int j = 1; j <= m; j++) {
            rot[j] = robot.get(j - 1);
        }
        Arrays.sort(fac, 1, n + 1, (a, b) -> a[0] - b[0]);
        Arrays.sort(rot, 1, m + 1);
        for (int j = 1; j <= m; j++) {
            dp[0][j] = NA;
        }
    }
}
