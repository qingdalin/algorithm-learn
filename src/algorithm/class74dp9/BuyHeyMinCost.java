package algorithm.class74dp9;

import java.io.*;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-16 16:02
 * https://www.luogu.com.cn/problem/P2918
 * 完全背包
 */
public class BuyHeyMinCost {
    public static int MAXN = 101;
    public static int MAXM = 55001;
    public static int[] costs = new int[MAXN];
    public static int[] vals = new int[MAXN];
    public static int[] dp = new int[MAXM];
    public static int n, h, maxv, m;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            h = (int) st.nval;
            maxv = 0;
            for (int i = 1; i <= n; i++) {
                st.nextToken(); vals[i] = (int) st.nval;
                maxv = Math.max(maxv, vals[i]);
                st.nextToken(); costs[i] = (int) st.nval;
            }
            // 扩充重要
            m = h + maxv;
            out.println(f1());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int f2() {
        // dp[i][j]: 表示1 - i个公司严格购买j重量的干草，花费的最小费用
        Arrays.fill(dp, 1, m + 1, Integer.MAX_VALUE);
        // dp[0][0]: 0个公司买0分干草花费0元
        // dp[0][1...m]: 0个公司买j分干草花费无穷元
        for (int i = 1; i <= n; i++) {
            for (int j = vals[i]; j <= m; j++) {
                if (dp[j - vals[i]] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - vals[i]] + costs[i]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int j = h; j <= m; j++) {
            ans = Math.min(ans, dp[j]);
        }
        return ans;
    }

    private static int f1() {
        // dp[i][j]: 表示1 - i个公司严格购买j重量的干草，花费的最小费用
        int[][] dp = new int[n + 1][m + 1];
        Arrays.fill(dp[0], 1, m + 1, Integer.MAX_VALUE);
        // dp[0][0]: 0个公司买0分干草花费0元
        // dp[0][1...m]: 0个公司买j分干草花费无穷元
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - vals[i] >= 0 && dp[i][j - vals[i]] != Integer.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - vals[i]] + costs[i]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int j = h; j <= m; j++) {
            ans = Math.min(ans, dp[n][j]);
        }
        return ans;
    }
}
