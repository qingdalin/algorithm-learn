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
 * @date: 2024/7/14 20:07
 * https://www.nowcoder.com/practice/4727c06b9ee9446cab2e859b4bb86bb8
 */
public class LCS {
    public static int MAXN = 5001;
    public static int[][] dp = new int[MAXN][MAXN];
    public static char[] s1, s2;
    public static char[] ans = new char[MAXN];
    public static int n, m, k;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String str1 = bf.readLine();
        String str2 = bf.readLine();
        s1 = str1.toCharArray();
        s2 = str2.toCharArray();
        n = s1.length;
        m = s2.length;
        int res = compute();
        if (res == 0) {
            out.println(-1);
        } else {
            for (int i = 0; i < k; i++) {
                out.print(ans[i]);
            }
            out.println();
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        dp();
        k = dp[n][m];
        if (k > 0) {
            for (int len = k, i = n, j = m; len > 0;) {
                if (s1[i - 1] == s2[j - 1]) {
                    ans[--len] = s1[i - 1];
                    i--;
                    j--;
                } else {
                    if (dp[i - 1][j] >= dp[i][j - 1]) {
                        i--;
                    } else {
                        j--;
                    }
                }
            }
        }
        return k;
    }

    private static void dp() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
    }
}
