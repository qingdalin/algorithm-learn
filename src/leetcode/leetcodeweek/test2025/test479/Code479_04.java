package leetcode.leetcodeweek.test2025.test479;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/7 10:00
 * https://leetcode.cn/contest/weekly-contest-479/problems/maximum-subgraph-score-in-a-tree/
 */
public class Code479_04 {
    public static int MAXN = 100001;
    public static int[] head = new int[MAXN];
    public static int[] nxt = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg = 0;
    public static int[] sum = new int[MAXN];
    public static int[] good = new int[MAXN];
    public static int n;

    public static int[] maxSubgraphScore(int n, int[][] edges, int[] g) {
        build(edges, g);
        dfs(1, 0, 0);
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = good[i + 1];
        }
        return ans;
    }

    private static void dfs(int u, int fa, int pathSum) {
        sum[u] = good[u] == 1 ? 1 : -1;
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa) {
                dfs(v, u, pathSum + sum[u]);
                sum[u] = Math.max(sum[u], sum[v] + sum[u]);
            }
        }
    }

    private static void build(int[][] edges, int[] g) {
        n = edges.length;
        cntg = 0;
        for (int i = 1; i <= n; i++) {
            head[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            good[i + 1] = g[i];
        }
        for (int i = 0; i < n; i++) {
            addEdge(edges[i][0] + 1, edges[i][1] + 1);
            addEdge(edges[i][1] + 1, edges[i][0] + 1);
        }
    }

    public static void addEdge(int u, int v) {
        nxt[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void main(String[] args) {
        int n = 3;
        int[][] edges = {
            {0, 1},
            {1, 2}};
        int[] g = {1, 0, 1};
        System.out.println(Arrays.toString(maxSubgraphScore(n, edges, g)));
    }
}
