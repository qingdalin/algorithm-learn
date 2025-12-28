package algorithm.class74dp9;

import java.io.*;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-16 11:22
 * 分组背包
 * https://www.luogu.com.cn/problem/P1757
 *
 */
public class GroupBackpack {
    public static int MAXN = 1001;
    public static int MAXM = 1001;
    public static int n, m;
    public static int[] dp = new int[MAXM];
    public static int[][] arr = new int[MAXN][3];

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            m = (int) st.nval;
            st.nextToken();
            n = (int) st.nval;
            for (int i = 1; i <= n; i++) {
                st.nextToken(); arr[i][0] = (int) st.nval;
                st.nextToken(); arr[i][1] = (int) st.nval;
                st.nextToken(); arr[i][2] = (int) st.nval;
            }
            Arrays.sort(arr, 0, n + 1, (a, b) -> a[2] - b[2]);
            out.println(f1());
        }
        out.flush();
        out.close();
        bf.close();
    }
    // 空间压缩
    private static int f2() {
        // dp[i][j]: 表示第i组的最大容积不超过j情况下，最大价值是多少
        Arrays.fill(dp, 0, m + 1, 0);
        // dp[0][j]: 没有组，都是0默认的
        for (int start = 1, end = 2; start <= n;) {
            while (end <= n && arr[end][2] == arr[start][2]) {
                end++;
            }
            for (int j = m; j >= 0; j--) {
                // 2.要当前i组，判断要哪个价值最大
                for (int k = start; k < end; k++) {
                    if (j - arr[k][0] >= 0) {
                        dp[j] = Math.max(dp[j], dp[j - arr[k][0]] + arr[k][1]);
                    }
                }
            }
            start = end++;
        }
        return dp[m];
    }

    private static int f1() {
        int team = 1;
        for (int i = 2; i <= n; i++) {
            if (arr[i][2] != arr[i - 1][2]) {
                team++;
            }
        }
        // dp[i][j]: 表示第i组的最大容积不超过j情况下，最大价值是多少
        int[][] dp = new int[team + 1][m + 1];
        // dp[0][j]: 没有组，都是0默认的
        for (int start = 1, end = 2, i = 1; start <= n; i++) {
            while (end <= n && arr[end][2] == arr[start][2]) {
                end++;
            }
            for (int j = 0; j <= m; j++) {
                // 1.不要当前i组
                dp[i][j] = dp[i - 1][j];
                // 2.要当前i组，判断要哪个价值最大
                for (int k = start; k < end; k++) {
                    if (j - arr[k][0] >= 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - arr[k][0]] + arr[k][1]);
                    }
                }
            }
            start = end++;
        }
        return dp[team][m];
    }
}
