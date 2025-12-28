package algorithm.class122;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/12 21:17
 * // 最小化旅行的价格总和(tarjan求lca)
 * // 有n个节点形成一棵树，每个节点上有点权，再给定很多路径
 * // 每条路径有开始点和结束点，路径代价就是从开始点到结束点的点权和
 * // 所有路径的代价总和就是旅行的价格总和
 * // 你可以选择把某些点的点权减少一半，来降低旅行的价格总和
 * // 但是要求选择的点不能相邻
 * // 返回旅行的价格总和最少能是多少
 * // 测试链接 : https://leetcode.cn/problems/minimize-the-total-price-of-the-trips/
 */
public class Code03_MinimizePriceOfTrips2 {
    public static int MAXN = 51;
    public static int MAXM = 101;
    public static int[] price = new int[MAXN];
    public static int[] num = new int[MAXN];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt, yes, no;
    public static int[] queryHead = new int[MAXN];
    public static int[] ans = new int[MAXM];
    public static int[] father = new int[MAXN];
    public static int[] unionFind = new int[MAXN];
    public static boolean[] visited = new boolean[MAXN];
    public static int[] queryNext = new int[MAXM << 1];
    public static int[] queryTo = new int[MAXM << 1];
    public static int[] queryIndex = new int[MAXM << 1];
    public static int qcnt;

    public static int minimumTotalPrice(int n, int[][] es, int[] ps, int[][] ts) {
        build(n);
        for (int i = 0, j = 1; i < n; i++, j++) {
            price[j] = ps[i];
        }
        for (int[] edge : es) {
            addEdge(edge[0] + 1, edge[1] + 1);
            addEdge(edge[1] + 1, edge[0] + 1);
        }
        int m = ts.length;
        for (int i = 0, u, v, j = 1; i < m; i++, j++) {
            u = ts[i][0] + 1;
            v = ts[i][1] + 1;
            addQuery(u, v, j);
            addQuery(v, u, j);
        }
        tarjan(1, 0);
        for (int i = 0, u, v, lca, lcaFather, j = 1; i < m; i++, j++) {
            u = ts[i][0] + 1;
            v = ts[i][1] + 1;
            lca = ans[j];
            lcaFather = father[lca];
            num[u]++;
            num[v]++;
            num[lca]--;
            num[lcaFather]--;
        }
        dfs2(1, 0);
        dp(1, 0);
        return Math.min(yes, no);
    }

    private static void tarjan(int u, int f) {
        visited[u] = true;
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                tarjan(to[e], u);
            }
        }
        for (int e = queryHead[u], v; e != 0; e = queryNext[e]) {
            v = queryTo[e];
            if (visited[v]) {
                ans[queryIndex[e]] = find(v);
            }
        }
        father[u] = f;
        unionFind[u] = f;
    }

    private static int find(int i) {
        if (i != unionFind[i]) {
            unionFind[i] = find(unionFind[i]);
        }
        return unionFind[i];
    }

    private static void addQuery(int u, int v, int i) {
        queryNext[qcnt] = queryHead[u];
        queryTo[qcnt] = v;
        queryIndex[qcnt] = i;
        queryHead[u] = qcnt++;
    }

    private static void dp(int u, int f) {
        int n = num[u] * price[u];
        int y = num[u] * (price[u] / 2);
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dp(v, u);
                n += Math.min(yes, no);
                y += no;
            }
        }
        yes = y;
        no = n;
    }

    private static void dfs2(int u, int f) {
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                dfs2(to[e], u);
            }
        }
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                num[u] += num[v];
            }
        }
    }



    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    private static void build(int n) {
        cnt = qcnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
        Arrays.fill(queryHead, 1, n + 1, 0);
        Arrays.fill(num, 1, n + 1, 0);
        Arrays.fill(visited, 1, n + 1, false);
        for (int i = 1; i <= n; i++) {
            unionFind[i] = i;
        }
    }

}
