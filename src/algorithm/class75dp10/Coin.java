package algorithm.class75dp10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-21 19:55
 * 金钱找零，
 * 01背包。完全背包和多重背包
 * http://poj.org/problem?id=1742
 */
public class Coin {
    public static int MAXN = 101;
    public static int MAXM = 100001;
    public static int[] v = new int[MAXN];
    public static int[] c = new int[MAXN];
    public static boolean[] dp = new boolean[MAXM];
    public static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            if (n != 0 || m != 0) {
                for (int i = 1; i <= n; i++) {
                    st.nextToken(); v[i] = (int) st.nval;
                }
                for (int i = 1; i <= n; i++) {
                    st.nextToken(); c[i] = (int) st.nval;
                }
                // 扩充重要
                out.println(f1());
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int f1() {
        Arrays.fill(dp, 1, m + 1, false);
        // dp[i][j]: 表示1-i个硬币随意选，钱数为j是否可以找零
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            if (c[i] == 1) {
                // 01背包
                // dp[i][j]: dp[i - 1][j],不要当前硬币
                // dp[i][j]: dp[i - 1][j - v[i]],要当前硬币
                for (int j = m; j >= v[i]; j--) {
                    if (dp[j - v[i]]) {
                        dp[j] = true;
                    }
                }
            } else if (c[i] * v[i] > m) {
                // 完全背包
                // dp[i][j]: dp[i - 1][j],不要当前硬币
                // dp[i][j]: dp[i][j - v[i]],要当前硬币
                for (int j = v[i]; j <= m; j++) {
                    if (dp[j - v[i]]) {
                        dp[j] = true;
                    }
                }
            } else {
                // 多重背包
                // 只要前方有一个为true即可
                for (int mod = 0; mod < v[i]; mod++) {
                    int cntTrue = 0;
                    for (int j = m - mod, size = 0; j >= 0 && size <= c[i]; j -= v[i], size++) {
                        cntTrue += dp[j] ? 1 : 0;
                    }
                    for (int j = m - mod, l = j - v[i] * (c[i] + 1); j >= 1 ; j -= v[i], l -= v[i]) {
                        if (dp[j]) {
                            cntTrue--;
                        } else {
                            if (cntTrue != 0) {
                                dp[j] = true;
                            }
                        }
                        if (l >= 0) {
                            cntTrue += dp[l] ? 1 : 0;
                        }
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 1; i <= m; i++) {
            if (dp[i]) {
                ans++;
            }
        }
        return ans;
    }
}
