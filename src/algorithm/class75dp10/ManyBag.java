package algorithm.class75dp10;

import java.io.*;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-17 19:55
 * https://www.luogu.com.cn/problem/P1776
 * 多重背包
 * 每个物品有个数限制，时间复杂度高，不分用例不通过
 */
public class ManyBag {
    public static int MAXN = 101;
    public static int MAXW = 40001;
    public static int[] wights = new int[MAXN];
    public static int[] vals = new int[MAXN];
    public static int[] cnts = new int[MAXN];
    public static int[] dp = new int[MAXW];
    public static int n, w;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            w = (int) st.nval;
            for (int i = 1; i <= n; i++) {
                st.nextToken(); vals[i] = (int) st.nval;
                st.nextToken(); wights[i] = (int) st.nval;
                st.nextToken(); cnts[i] = (int) st.nval;
            }
            // 扩充重要
            out.println(f1());
        }
        out.flush();
        out.close();
        bf.close();
    }
    private static int f2() {
        Arrays.fill(dp, 0, w + 1, 0);
        for (int i = 1; i <= n; i++) {
            for (int j = w; j >= 0; j--) {
                for (int k = 1; k <= cnts[i] && j - k * wights[i] >= 0; k++) {
                    dp[j] = Math.max(dp[j], dp[j - k * wights[i]] + k * vals[i]);
                }
            }
        }
        return dp[w];
    }

    private static int f1() {
        int[][] dp = new int[n + 1][w + 1];
        // dp[i][j]: 表示1-i号物品随意选，个数不超过限制，重量也不超过j下的最大价值
        // dp[0][j..]: 一个物品也没有，价值自然为0
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= w; j++) {
                // 1.不要当前物品，则价值是前一个物品的最大价值
                dp[i][j] = dp[i - 1][j];
                for (int k = 1; k <= cnts[i] && j - k * wights[i] >= 0; k++) {
                    // 要当前物品
                    // 从1到k枚举所有可能性
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * wights[i]] + k * vals[i]);
                }
            }
        }
        return dp[n][w];
    }
}
