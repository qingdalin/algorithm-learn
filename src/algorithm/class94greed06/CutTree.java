package algorithm.class94greed06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 11:52
 * https://pintia.cn/problem-sets/91827364500/exam/problems/type/7?problemSetProblemId=91827367873&page=22
 */
public class CutTree {
    public static int MAXN = 251;
    public static int t, n, m;
    public static int[][] tree = new int[MAXN][2];
    public static int[][] dp = new int[MAXN][MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        st.nextToken();
        t = (int) st.nval;
        for (int i = 0; i < t; i++) {
            st.nextToken();
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            for (int j = 1; j <= n; j++) {
                st.nextToken();
                tree[j][0] = (int) st.nval;
            }
            for (int j = 1; j <= n; j++) {
                st.nextToken();
                tree[j][1] = (int) st.nval;
            }
            System.out.println(compute());
        }

        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        // 按照增加数量升序
        Arrays.sort(tree, 1, n + 1, (a, b) -> a[1] - b[1]);
        // dp[i][j]: 表示前i颗树，在j天内砍完的最大收益
        // dp[0][j]: 没有树，天数多少都是0收益
        // dp[i][0]: 没有天数，树有多少都是0收益
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // dp[i][j]:不砍当前i树，就取前i-1颗树在j天砍完，
                // 砍当前树，i - 1颗树在j - 1天砍完，在加上当前的树初始值和增长值
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] + tree[i][0] + (j - 1) * tree[i][1]);
            }
        }
        return dp[n][m];
    }
}
