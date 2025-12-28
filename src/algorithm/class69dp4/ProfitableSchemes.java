package algorithm.class69dp4;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-26 8:05
 * 集团里有 n 名员工，他们可以完成各种各样的工作创造利润。
 *
 * 第 i 种工作会产生 profit[i] 的利润，它要求 group[i] 名成员共同参与。如果成员参与了其中一项工作，就不能参与另一项工作。
 *
 * 工作的任何至少产生 minProfit 利润的子集称为 盈利计划 。并且工作的成员总数最多为 n 。
 *
 * 有多少种计划可以选择？因为答案很大，所以 返回结果模 10^9 + 7 的值。
 * https://leetcode.cn/problems/profitable-schemes/description/
 */
public class ProfitableSchemes {
    public static int mod = 1000000007;
    public int profitableSchemes1(int n, int minProfit, int[] group, int[] profit) {
        return f1(group, profit, 0, n, minProfit);
    }

    // 第i号工作
    // r现在剩余的人 r <= 0说明无法选择工作，
    // s剩余需完成的利润 s <= 0说明利润达标
    // 最终返回一共多少种结果 p1 + p2
    private int f1(int[] group, int[] profit, int i, int r, int s) {
        if (r <= 0) {
            return s <= 0 ? 1 : 0;
        }
        if (i == group.length) {
            return s <= 0 ? 1 : 0;
        }
        int p1 = f1(group, profit, i + 1, r, s);
        int p2 = 0;
        if (r >= group[i]) {
            p2 = f1(group, profit, i + 1, r - group[i], s - profit[i]);
        }
        return (p1 + p2) % mod;
    }

    public int profitableSchemes2(int n, int minProfit, int[] group, int[] profit) {
        int[][][] dp = new int[group.length][n + 1][minProfit + 1];
        for (int i = 0; i < group.length; i++) {
            for (int r = 0; r <= n; r++) {
                for (int s = 0; s <= minProfit; s++) {
                    dp[i][r][s] = -1;
                }
            }
        }
        return f2(group, profit, 0, n, minProfit, dp);
    }

    // 第i号工作
    // r现在剩余的人 r <= 0说明无法选择工作，
    // s剩余需完成的利润 s <= 0说明利润达标
    // 最终返回一共多少种结果 p1 + p2
    private int f2(int[] group, int[] profit, int i, int r, int s, int[][][] dp) {

        if (r <= 0) {
            return s <= 0 ? 1 : 0;
        }
        if (i == group.length) {
            return s <= 0 ? 1 : 0;
        }
        if (dp[i][r][s] != -1) {
            return dp[i][r][s];
        }
        int p1 = f2(group, profit, i + 1, r, s, dp);
        int p2 = 0;
        if (r >= group[i]) {
            p2 = f2(group, profit, i + 1, r - group[i], Math.max(0, s - profit[i]), dp);
        }
        dp[i][r][s] = (p1 + p2) % mod;
        return (p1 + p2) % mod;
    }

    public int profitableSchemes3(int n, int minProfit, int[] group, int[] profit) {
        int[][][] dp = new int[group.length + 1][n + 1][minProfit + 1];
        for (int r = 0; r <= n; r++) {
            // 越界，利润已经剩余0
            dp[group.length][r][0] = 1;
        }
        for (int i = group.length - 1; i >= 0; i--) {
            for (int r = n; r >= 0; r--) {
                for (int s = minProfit; s >= 0; s--) {
                    int p1 = dp[i + 1][r][s];
                    int p2 = r >= group[i] ? dp[i + 1][r - group[i]][Math.max(0, s - profit[i])] : 0;
                    dp[i][r][s] = (p1 + p2) % mod;
                }
            }
        }
        return dp[0][n][minProfit];
    }

    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int[][] dp = new int[n + 1][minProfit + 1];
        for (int r = 0; r <= n; r++) {
            // 越界，利润已经剩余0
            dp[r][0] = 1;
        }
        for (int i = group.length - 1; i >= 0; i--) {
            for (int r = n; r >= 0; r--) {
                for (int s = minProfit; s >= 0; s--) {
                    int p1 = dp[r][s];
                    int p2 = r >= group[i] ? dp[r - group[i]][Math.max(0, s - profit[i])] : 0;
                    dp[r][s] = (p1 + p2) % mod;
                }
            }
        }
        return dp[n][minProfit];
    }
}
