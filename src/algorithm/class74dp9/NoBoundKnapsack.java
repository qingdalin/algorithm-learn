package algorithm.class74dp9;

import java.io.*;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-16 13:38
 * 完全背包
 * https://www.luogu.com.cn/problem/P1616
 */
public class NoBoundKnapsack {
    public static int MAXM = 10001;
    public static int MAXT = 10000001;
    public static int t, m;
    public static long[] dp = new long[MAXT];
    public static int[] costs = new int[MAXM];
    public static int[] vals = new int[MAXM];

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            t = (int) st.nval; // 背包容量
            st.nextToken();
            m = (int) st.nval; // 物品个数
            for (int i = 1; i <= m; i++) {
                st.nextToken(); costs[i] = (int) st.nval;
                st.nextToken(); vals[i] = (int) st.nval;
            }
            out.println(f1());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long f2() {
        Arrays.fill(dp, 0, t + 1, 0);
        // dp[i][j]: 表示第i个物品随意选背包容量不超过j的最大价值
        for (int i = 1; i <= m; i++) {
            for (int j = costs[i]; j <= t; j++) {
                dp[j] = Math.max(dp[j], dp[j - costs[i]] + vals[i]);
            }
        }
        return dp[t];
    }

    private static int f1() {
        long[][] dp = new long[m + 1][t + 1];
        // dp[i][j]: 表示第i个物品随意选背包容量不超过j的最大价值
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= t; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - costs[i] >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - costs[i]] + vals[i]);
                }
            }
        }
        return (int) dp[m][t];
    }
}
