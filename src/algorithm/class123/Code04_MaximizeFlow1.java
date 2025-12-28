package algorithm.class123;

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
 * @date: 2024/10/18 21:07
 * // 选择节点做根使流量和最大(递归版)
 * // 给定一棵n个点的树，边的边权代表流量限制
 * // 从边上流过的流量，不能超过流量限制
 * // 现在想知道以某个节点做根时，流到所有叶节点的流量，最大是多少
 * // 测试链接 : http://poj.org/problem?id=3585
 * // 提交以下的code，提交时请把类名改成"Main"
 * // C++这么写能通过，java会因为递归层数太多而爆栈
 * // java能通过的写法参考本节课Code04_MaximizeFlow2文件
 */
public class Code04_MaximizeFlow1 {
    public static int MAXN = 200001;
    public static int n;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cnt;
    public static int[] degree = new int[MAXN];
    // flow[u] : 从u出发流向u节点为头的子树上，所有的叶节点，流量是多少
    public static int[] flow = new int[MAXN];
    // dp[u] : 从u出发流向u节点为根的整棵树上，所有的叶节点，流量是多少
    public static int[] dp = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int testCase = (int) in.nval;
        for (int t = 0; t < testCase; t++) {
            in.nextToken(); n = (int) in.nval;
            build();
            for (int j = 1, u, v, w; j <= n - 1; j++) {
                in.nextToken(); u = (int) in.nval;
                in.nextToken(); v = (int) in.nval;
                in.nextToken(); w = (int) in.nval;
                addEdge(u, v, w);
                addEdge(v, u, w);
                degree[u]++;
                degree[v]++;
            }
            dfs1(1, 0);
            dp[1] = flow[1];
            dfs2(1, 0);
            int ans = 0;
            for (int j = 1; j <= n; j++) {
                ans = Math.max(ans, dp[j]);
            }
            System.out.println(ans);
        }
        out.flush();
        out.close();
        br.close();
    }

    private static void dfs2(int u, int f) {
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                if (degree[u] == 1) {
                    dp[v] = flow[v] + weight[e];
                } else {
                    int uOut = dp[u] - Math.min(weight[e], flow[v]);
                    dp[v] = flow[v] + Math.min(uOut, weight[e]);
                }
                dfs2(v, u);
            }
        }
    }

    private static void dfs1(int u, int f) {
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs1(v, u);
            }
        }
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                if (degree[v] == 1) {
                    flow[u] += weight[e];
                } else {
                    flow[u] += Math.min(weight[e],  flow[v]);
                }
            }
        }
    }

    private static void addEdge(int u, int v, int w) {
        next[cnt] = head[u];
        to[cnt] = v;
        weight[cnt] = w;
        head[u] = cnt++;
    }

    private static void build() {
        cnt = 1;
        Arrays.fill(degree, 1, n + 1, 0);
        Arrays.fill(flow, 1, n + 1, 0);
        Arrays.fill(dp, 1, n + 1, 0);
        Arrays.fill(head, 1, n + 1, 0);
    }
}
