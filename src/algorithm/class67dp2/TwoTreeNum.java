package algorithm.class67dp2;

import java.io.*;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-19 10:26
 * https://www.nowcoder.com/practice/aaefe5896cce4204b276e213e725f3ea
 */
public class TwoTreeNum {
    public static int mod = 1000000007;
    public static int MAXN = 51;
    public static int MAXM = 51;
    public static long[][] dp = new long[MAXN][MAXM];
    public static long[] dp2 = new long[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) st.nval;
            st.nextToken();
            int m = (int) st.nval;
            out.println(f4(n, m));
        }
        out.flush();
        out.close();
        bf.close();
    }

    public static int f4(int n, int m) {
        dp2[0] = 1;
        for (int i = 1; i <= n; i++) {
            dp2[i] = 0;
        }
        // 先依赖列
        for (int j = 1; j <= m; j++) {
            for (int i = n; i >= 1; i--) {
                dp2[i] = 0;
                for (int k = 0; k < i; k++) {
                    dp2[i] = (dp2[i] + dp2[k] * dp2[i - 1 - k]) % mod;
                }
            }
        }
        return (int) dp2[n];
    }

    public static int f3(int n, int m) {
        for (int i = 0; i < m; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = 0;
                for (int k = 0; k < i; k++) {
                    dp[i][j] = (dp[i][j] + dp[k][j - 1] * dp[i - 1 - k][j - 1]) % mod;
                }
            }
        }
        return (int) dp[n][m];
    }

    public static int f2(int n, int m, int[][] dp) {
        if (n == 0) {
            return 1;
        }
        if (m == 0) {
            return 0;
        }
        if (dp[n][m] != -1) {
            return dp[n][m];
        }
        long ans = 0;
        for (int k = 0; k < n; k++) {
            ans = (ans + ((long) f2(k, m - 1, dp) * f2(n - 1 - k, m - 1, dp))) % mod;
        }
        dp[n][m] = (int) ans;
        return dp[n][m];
    }

    // 节点数为n的高度不超过m的二叉树个数
    public static int f1(int n, int m) {
        if (n == 0) {
            return 1;
        }
        if (m == 0) {
            return 0;
        }
        int ans = 0;
        for (int k = 0; k < n; k++) {
            ans = ans + (f1(k, m - 1) * f1(n - 1 - k, m - 1));
        }
        return ans % mod;
    }
}
