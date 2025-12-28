package algorithm.class79dp14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-29 16:01
 * https://www.luogu.com.cn/problem/P2014
 */
public class CourseSelect {
    public static int n, m;
    public static List<List<Integer>> graph = new ArrayList<>();
    public static int MAXN = 301;
    public static int[][][] dp = new int[MAXN][][];
    public static int[] nums = new int[MAXN];
    static {
        for (int i = 0; i < MAXN; i++) {
            graph.add(new ArrayList<>());
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval + 1;
            build(n);
            for (int i = 1, pre; i <= n; i++) {
                st.nextToken();
                pre = (int) st.nval;
                graph.get(pre).add(i);
                st.nextToken();
                nums[i] = (int) st.nval;
            }
            out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        for (int i = 0; i <= n; i++) {
            dp[i] = new int[graph.get(i).size() + 1][m + 1];
        }
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k <= m; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        return f(0, graph.get(0).size(), m);
    }

    // dp[i][j][k]: 表示i开头的节点，选前j可子树，一共选m门课程，最大学分
    // 只在i号节点，及其i号节点下方的前j颗子树上挑选节点
    // 一共选k个节点，并且保证节点连成一片，返回最大累加和
    private static int f(int i, int j, int k) {
        if (k == 0) {
            return 0;
        }
        // 没有子树，或者只选1门课，只能是选i节点
        if (j == 0 || k == 1) {
            return nums[i];
        }
        if (dp[i][j][k] != -1) {
            return dp[i][j][k];
        }
        // 不选最后一颗子树的结果
        int ans = f(i, j - 1, k);
        // 第j颗子树的头结点v
        int v = graph.get(i).get(j - 1);
        for (int s = 1; s < k; s++) {
            ans = Math.max(ans, f(i, j - 1, k - s) + f(v, graph.get(v).size(), s));
        }
        dp[i][j][k] = ans;
        return ans;
    }

    private static void build(int n) {
        for (int i = 0; i <= n; i++) {
            graph.get(i).clear();
        }
    }
}
