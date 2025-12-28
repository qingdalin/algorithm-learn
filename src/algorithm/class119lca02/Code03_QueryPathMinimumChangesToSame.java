package algorithm.class119lca02;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/22 10:45
 * https://leetcode.cn/problems/minimum-edge-weight-equilibrium-queries-in-a-tree/description/
 * 现有一棵由 n 个节点组成的无向树，节点按从 0 到 n - 1 编号。
 * 给你一个整数 n 和一个长度为 n - 1 的二维整数数组 edges ，
 * 其中 edges[i] = [ui, vi, wi] 表示树中存在一条位于节点 ui 和节点 vi 之间、权重为 wi 的边。
 * 另给你一个长度为 m 的二维整数数组 queries ，其中 queries[i] = [ai, bi] 。
 * 对于每条查询，请你找出使从 ai 到 bi 路径上每条边的权重相等所需的 最小操作次数 。
 * 在一次操作中，你可以选择树上的任意一条边，并将其权重更改为任意值。
 * 注意：
 * 查询之间 相互独立 的，这意味着每条新的查询时，树都会回到 初始状态 。
 * 从 ai 到 bi的路径是一个由 不同 节点组成的序列，从节点 ai 开始，到节点 bi 结束，且序列中相邻的两个节点在树中共享一条边。
 * 返回一个长度为 m 的数组 answer ，其中 answer[i] 是第 i 条查询的答案。
 */
public class Code03_QueryPathMinimumChangesToSame {
    public static int MAXN = 10001;
    public static int MAXM = 20001;
    public static int MAXW = 26;
    public static int[] headEdge = new int[MAXN];
    public static int[] edgeNext = new int[MAXN << 1];
    public static int[] edgeTo = new int[MAXN << 1];
    public static int[] edgeVal = new int[MAXN << 1];
    public static int tcnt;

    public static int[][] wightCnt = new int[MAXN][MAXW + 1];

    public static int[] headQuery = new int[MAXM];
    public static int[] queryNext = new int[MAXM << 1];
    public static int[] queryTo = new int[MAXM << 1];
    public static int[] queryIndex = new int[MAXM << 1];
    public static int qcnt;

    public static boolean[] visited = new boolean[MAXN];
    public static int[] father = new int[MAXN];
    public static int[] lca = new int[MAXM];
    public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
        build(n);
        for (int i = 0; i < edges.length; i++) {
            addEdge(edges[i][0], edges[i][1], edges[i][2]);
            addEdge(edges[i][1], edges[i][0], edges[i][2]);
        }
        dfs(0, 0, -1);
        int m = queries.length;
        for (int i = 0; i < m; i++) {
            addQuery(queries[i][0], queries[i][1], i);
            addQuery(queries[i][1], queries[i][0], i);
        }
        tarjan(0, -1);
        int[] ans = new int[m];

        for (int i = 0, a, b, c; i < m; i++) {
            a = queries[i][0];
            b = queries[i][1];
            c = lca[i];
            int allCnt = 0, maxCnt = 0;
            for (int w = 1, wcnt; w <= MAXW; w++) {
                wcnt = wightCnt[a][w] + wightCnt[b][w] - 2 * wightCnt[c][w];
                maxCnt = Math.max(maxCnt, wcnt);
                allCnt += wcnt;
            }
            ans[i] = allCnt - maxCnt;
        }
        return ans;
    }

    private void tarjan(int u, int f) {
        visited[u] = true;
        for (int e = headEdge[u]; e != 0; e = edgeNext[e]) {
            if (edgeTo[e] != f) {
                tarjan(edgeTo[e], u);
            }
        }
        for (int e = headQuery[u]; e != 0; e = queryNext[e]) {
            int v = queryTo[e];
            if (visited[v]) {
                lca[queryIndex[e]] = find(v);
            }
        }
        father[u] = f;
    }

    private int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    private void addQuery(int u, int v, int i) {
        queryNext[qcnt] = headQuery[u];
        queryTo[qcnt] = v;
        queryIndex[qcnt] = i;
        headQuery[u] = qcnt++;
    }

    private void dfs(int u, int w, int f) {
        if (u == 0) {
            Arrays.fill(wightCnt[u], 0);
        } else {
            for (int i = 1; i <= MAXW; i++) {
                wightCnt[u][i] = wightCnt[f][i];
            }
            wightCnt[u][w]++;
        }
        for (int e = headEdge[u]; e != 0; e = edgeNext[e]) {
            if (edgeTo[e] != f) {
                dfs(edgeTo[e], edgeVal[e], u);
            }
        }
    }

    private void addEdge(int u, int v, int w) {
        edgeNext[tcnt] = headEdge[u];
        edgeTo[tcnt] = v;
        edgeVal[tcnt] = w;
        headEdge[u] = tcnt++;
    }

    private void build(int n) {
        qcnt = tcnt = 1;
        for (int i = 0; i < n; i++) {
            father[i] = i;
         }
        Arrays.fill(headEdge, 0, n, 0);
        Arrays.fill(headQuery, 0, n, 0);
        Arrays.fill(visited, 0, n, false);
    }
}
