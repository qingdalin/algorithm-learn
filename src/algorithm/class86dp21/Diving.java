package algorithm.class86dp21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/19 20:01
 * https://www.luogu.com.cn/problem/P1759
 * 不压缩空间过不了
 */
public class Diving {
    public static int MAXN = 101;
    public static int MAXM = 201;
    public static int[][][] dp = new int[MAXN][MAXM][MAXM];
    public static String[][][] path = new String[MAXN][MAXM][MAXM];
    public static int[] a = new int[MAXN];
    public static int[] b = new int[MAXN];
    public static int[] c = new int[MAXN];
    public static int n, v, m;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            m = (int) st.nval;
            st.nextToken(); v = (int) st.nval;
            st.nextToken(); n = (int) st.nval;
            for (int i = 1; i <= n; i++) {
                st.nextToken(); a[i] = (int) st.nval;
                st.nextToken(); b[i] = (int) st.nval;
                st.nextToken(); c[i] = (int) st.nval;
            }
            build();
            compute();
            System.out.println(dp[n][m][v]);
            System.out.println(path[n][m][v]);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static void compute() {
        String p2;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= v; k++) {
                    // 不要当前的货物,可能性1
                    dp[i][j][k] = dp[i - 1][j][k];
                    path[i][j][k] = path[i - 1][j][k];
                    if (j >= a[i] && k >= b[i]) {
                        if (path[i - 1][j - a[i]][k - b[i]] == null) {
                            p2 = String.valueOf(i);
                        } else {
                            p2 = path[i - 1][j - a[i]][k - b[i]] + " " + i;
                        }
                        if (dp[i][j][k] < dp[i - 1][j - a[i]][k - b[i]] + c[i]) {
                            // 可能性1 没有可能性2好
                            dp[i][j][k] = dp[i - 1][j - a[i]][k - b[i]] + c[i];
                            path[i][j][k] = p2;
                        } else if (dp[i][j][k] == dp[i - 1][j - a[i]][k - b[i]] + c[i]){
                            // 两种可能性一样，比字典序小的
                            if (p2.compareTo(path[i][j][k]) < 0) {
                                path[i][j][k] = p2;
                            }
                        }
                        // else {
                            // 可能性1好，值已经赋上了
                        // }
                    }
                }
            }
        }
    }

    private static void build() {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= v; k++) {
                    dp[i][j][k] = 0;
                    path[i][j][k] = null;
                }
            }
        }
    }
}
