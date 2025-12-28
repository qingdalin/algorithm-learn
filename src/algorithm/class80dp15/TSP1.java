package algorithm.class80dp15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-30 11:35
 * https://www.luogu.com.cn/problem/P1171
 */
public class TSP1 {
    public static int MAXN = 19;
    public static int[][] graph = new int[MAXN][MAXN];
    public static int[][] dp = new int[1 << MAXN][MAXN];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            build();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    st.nextToken();
                    graph[i][j] = (int) st.nval;
                }
            }
            out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        // 从0号村子出发，状态status是1， 1表示走过，0表示没走过， 可选
        return f(1, 0);
    }

    private static int f(int s, int i) {
        if (s == ((1 << n) - 1)) {
            return graph[i][0];
        }
        if (dp[s][i] != 0) {
            return dp[s][i];
        }
        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if ((s & (1 << j)) == 0) {
                ans = Math.min(ans, graph[i][j] + f(s | (1 << j), j));
            }
        }
        dp[s][i] = ans;
        return ans;
    }

    private static void build() {
        for (int s = 0; s < (1 << n); s++) {
            for (int i = 0; i < n; i++) {
                dp[s][i] = 0;
            }
        }
    }
}
