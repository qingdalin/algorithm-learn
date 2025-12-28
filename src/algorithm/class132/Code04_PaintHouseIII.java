package algorithm.class132;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/17 12:51
 * // 粉刷房子III
 * // 房子有n个，从左到右排列，编号1..n，颜色有c种，编号1..c
 * // 给定数组house，house[i]表示房子的颜色，如果house[i]为0说明房子没有涂色
 * // 你必须给每个没有涂色的房子涂上颜色，如果有颜色的房子不能改变颜色
 * // 给定二维数组cost，cost[i][v]表示如果i号房涂成v号颜色，需要花费多少钱
 * // 相邻的、拥有同一种颜色的房子为1个街区
 * // 比如如果所有房子的颜色为: {1, 1, 2, 3, 2, 2}，那么一共4个街区
 * // 最终所有的房子涂完颜色，一定要形成t个街区，返回最少的花费
 * // 1 <= t <= n <= 100
 * // 1 <= c <= 20
 * // 0 <= house[i] <= c
 * // 1 <= cost[i][v] <= 10^4
 * // 测试链接 : https://leetcode.cn/problems/paint-house-iii/
 */
public class Code04_PaintHouseIII {
    public static int MAXN = 101;
    public static int MAXC = 21;

    public static int NA = Integer.MAX_VALUE;
    public static int[] house = new int[MAXN];
    public static int[][] cost = new int[MAXN][MAXC];
    public static int n, c, t;
    public static int minCost1(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
        build(houses, costs, hsize, csize, tsize);
        t++;
        int[][][] dp = new int[n + 1][t + 1][c + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= t; j++) {
                for (int k = 0; k <= c; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        int ans = f(n, t, 0, dp);
        return ans == NA ? -1 : ans;
    }

    // 1。。。i个房子范围去设置颜色，一定要有j个街区，v是i+1号房子的颜色，最少的花费是多少
    private static int f(int i, int j, int v, int[][][] dp) {
        if (j == 0) {
            return NA;
        }
        if (i == 0) {
            return j == 1 ? 0 : NA;
        }
        if (dp[i][j][v] != -1) {
            return dp[i][j][v];
        }
        int ans = NA;
        if (house[i] != 0) {
            // i号房子已经涂色,且与i+1位置颜色一致，那么街区数不变，往i-1递归
            if (house[i] == v) {
                ans = f(i - 1, j, house[i], dp);
            } else {
                ans = f(i - 1, j - 1, house[i], dp);
            }
        } else {
            for (int color = 1, next; color <= c; color++) {
                if (color == v) {
                    next = f(i - 1, j, color, dp);
                } else {
                    next = f(i - 1, j - 1, color, dp);
                }
                if (next != NA) {
                    ans = Math.min(ans, cost[i][color] + next);
                }
            }
        }
        dp[i][j][v] = ans;
        return ans;
    }

    private static void build(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
        n = hsize;
        c = csize;
        t = tsize;
        for (int i = 1; i <= n; i++) {
            house[i] = houses[i - 1];
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= c; j++) {
                cost[i][j] = costs[i - 1][j - 1];
            }
        }
    }

    public static int minCost2(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
        build(houses, costs, hsize, csize, tsize);
        t++;
        int[][][] dp = new int[n + 1][t + 1][c + 1];
        for (int i = 0; i <= n; i++) {
            for (int v = 0; v <= c; v++) {
                dp[i][0][v] = NA;
            }
        }
        for (int j = 0; j <= t; j++) {
            for (int v = 0; v <= c; v++) {
                dp[0][j][v] = j == 1 ? 0 : NA;
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= t; j++) {
                for (int v = 0; v <= c; v++) {
                    int ans = NA;
                    if (house[i] != 0) {
                        // i号房子已经涂色,且与i+1位置颜色一致，那么街区数不变，往i-1递归
                        if (house[i] == v) {
                            ans = dp[i - 1][j][house[i]];
                        } else {
                            ans = dp[i - 1][j - 1][house[i]];
                        }
                    } else {
                        for (int color = 1, next; color <= c; color++) {
                            if (color == v) {
                                next = dp[i - 1][j][color];
                            } else {
                                next = dp[i - 1][j - 1][color];
                            }
                            if (next != NA) {
                                ans = Math.min(ans, cost[i][color] + next);
                            }
                        }
                    }
                    dp[i][j][v] = ans;
                }
            }
        }
        return dp[n][t][0] == NA ? -1 : dp[n][t][0];
    }

    public static int minCost3(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
        build(houses, costs, hsize, csize, tsize);
        t++;
        int[][] dp = new int[t + 1][c + 1];
        int[][] memory = new int[t + 1][c + 1];
        for (int i = 0; i <= n; i++) {
            for (int v = 0; v <= c; v++) {
                memory[0][v] = dp[0][v] = NA;
            }
        }
        for (int j = 0; j <= t; j++) {
            for (int v = 0; v <= c; v++) {
                memory[j][v] = j == 1 ? 0 : NA;
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= t; j++) {
                for (int v = 0; v <= c; v++) {
                    int ans = NA;
                    if (house[i] != 0) {
                        // i号房子已经涂色,且与i+1位置颜色一致，那么街区数不变，往i-1递归
                        if (house[i] == v) {
                            ans = memory[j][house[i]];
                        } else {
                            ans = memory[j - 1][house[i]];
                        }
                    } else {
                        for (int color = 1, next; color <= c; color++) {
                            if (color == v) {
                                next = memory[j][color];
                            } else {
                                next = memory[j - 1][color];
                            }
                            if (next != NA) {
                                ans = Math.min(ans, cost[i][color] + next);
                            }
                        }
                    }
                    dp[j][v] = ans;
                }
            }
            int[][] tmp = memory;
            memory = dp;
            dp = tmp;
        }
        return memory[t][0] == NA ? -1 : memory[t][0];
    }

    public static int minCost(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
        build(houses, costs, hsize, csize, tsize);
        t++;
        int[][] dp = new int[t + 1][c + 1];
        int[][] memory = new int[t + 1][c + 1];
        for (int i = 0; i <= n; i++) {
            for (int v = 0; v <= c; v++) {
                memory[0][v] = dp[0][v] = NA;
            }
        }
        for (int j = 0; j <= t; j++) {
            for (int v = 0; v <= c; v++) {
                memory[j][v] = j == 1 ? 0 : NA;
            }
        }
        int[] pre = new int[c + 2];
        int[] suf = new int[c + 2];
        pre[0] = suf[c + 1] = NA;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= t; j++) {
                for (int v = 1; v <= c; v++) {
                    pre[v] = pre[v - 1];
                    if (memory[j - 1][v] != NA) {
                        pre[v] = Math.min(pre[v], memory[j - 1][v] + cost[i][v]);
                    }
                }
                for (int v = c; v >= 1; v--) {
                    suf[v] = suf[v + 1];
                    if (memory[j - 1][v] != NA) {
                        suf[v] = Math.min(suf[v], memory[j - 1][v] + cost[i][v]);
                    }
                }
                for (int v = 0; v <= c; v++) {
                    int ans = NA;
                    if (house[i] != 0) {
                        // i号房子已经涂色,且与i+1位置颜色一致，那么街区数不变，往i-1递归
                        if (house[i] == v) {
                            ans = memory[j][house[i]];
                        } else {
                            ans = memory[j - 1][house[i]];
                        }
                    } else {
                        if (v == 0) {
                            ans = suf[1];
                        } else {
                            ans = Math.min(suf[v + 1], pre[v - 1]);
                            if (memory[j][v] != NA) {
                                ans = Math.min(ans, memory[j][v] + cost[i][v]);
                            }
                        }
                    }
                    dp[j][v] = ans;
                }
            }
            int[][] tmp = memory;
            memory = dp;
            dp = tmp;
        }
        return memory[t][0] == NA ? -1 : memory[t][0];
    }
}
