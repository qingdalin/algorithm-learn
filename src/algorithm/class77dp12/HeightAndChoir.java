package algorithm.class77dp12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-23 10:32
 * https://www.luogu.com.cn/problem/P3205
 */
public class HeightAndChoir {
    public static int MAXN = 1001;
    public static int[] nums = new int[MAXN];
    public static int[][] dp = new int[MAXN][2];
    public static int n;
    public static int mod = 19650827;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 1; i <= n; i++) {
                st.nextToken(); nums[i] = (int) st.nval;
            }
            if (n == 1) {
                out.println(1);
            } else {
                out.println(f1());
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int f1() {
        // dp[l][r][0]:表示从l到r范围，要求l位置最后进来，形成l...r状态的方法数
        // dp[l][r][1]:表示从l到r范围，要求r位置最后进来，形成l...r状态的方法数
        int[][][] dp = new int[n + 1][n + 1][2];
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i + 1]) {
                dp[i][i + 1][0] = 1;
                dp[i][i + 1][1] = 1;
            }
        }
        for (int l = n - 2; l >= 1; l--) {
            for (int r = l + 2; r <= n; r++) {
                if (nums[l] < nums[l + 1]) {
                    dp[l][r][0] = (dp[l][r][0] + dp[l + 1][r][0]) % mod;
                }
                if (nums[l] < nums[r]) {
                    dp[l][r][0] = (dp[l][r][0] + dp[l + 1][r][1]) % mod;
                }
                if (nums[r] > nums[l]) {
                    dp[l][r][1] = (dp[l][r][1] + dp[l][r - 1][0]) % mod;
                }
                if (nums[r] > nums[r - 1]) {
                    dp[l][r][1] = (dp[l][r][1] + dp[l][r - 1][1]) % mod;
                }
            }
        }
        return (dp[1][n][0] + dp[1][n][1]) % mod;
    }

    private static int f2() {
        // dp[l][r][0]:表示从l到r范围，要求l位置最后进来，形成l...r状态的方法数
        // dp[l][r][1]:表示从l到r范围，要求r位置最后进来，形成l...r状态的方法数
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i + 1]) {
                dp[i + 1][0] = 1;
                dp[i + 1][1] = 1;
            }
        }
        for (int l = n - 2; l >= 1; l--) {
            for (int r = l + 2; r <= n; r++) {
                int a = 0, b = 0;
                if (nums[l] < nums[l + 1]) {
                    a = (a + dp[r][0]) % mod;
                }
                if (nums[l] < nums[r]) {
                    a = (a + dp[r][1]) % mod;
                }
                if (nums[r] > nums[l]) {
                    b = (b + dp[r - 1][0]) % mod;
                }
                if (nums[r] > nums[r - 1]) {
                    b = (b + dp[r - 1][1]) % mod;
                }
                dp[r][0] = a;
                dp[r][1] = b;
            }
        }
        return (dp[n][0] + dp[n][1]) % mod;
    }
}
