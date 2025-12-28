package algorithm.class122;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/12 21:17
 * // 最小化旅行的价格总和(倍增方法求lca)
 * // 有n个节点形成一棵树，每个节点上有点权，再给定很多路径
 * // 每条路径有开始点和结束点，路径代价就是从开始点到结束点的点权和
 * // 所有路径的代价总和就是旅行的价格总和
 * // 你可以选择把某些点的点权减少一半，来降低旅行的价格总和
 * // 但是要求选择的点不能相邻
 * // 返回旅行的价格总和最少能是多少
 * // 测试链接 : https://leetcode.cn/problems/minimize-the-total-price-of-the-trips/
 */
public class Code03_MinimizePriceOfTrips1 {
    public static int MAXN = 51;
    public static int LIMIT = 6;
    public static int power;
    public static int[] price = new int[MAXN];
    public static int[] num = new int[MAXN];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt, yes, no;
    public static int[] deep = new int[MAXN];
    public static int[][] stjump = new int[MAXN][LIMIT];
    public static int minimumTotalPrice(int n, int[][] es, int[] ps, int[][] ts) {
        build(n);
        for (int i = 0, j = 1; i < n; i++, j++) {
            price[j] = ps[i];
        }
        for (int[] edge : es) {
            addEdge(edge[0] + 1, edge[1] + 1);
            addEdge(edge[1] + 1, edge[0] + 1);
        }
        dfs1(1, 0);
        for (int[] trip : ts) {
            int u = trip[0] + 1;
            int v = trip[1] + 1;
            int lca = lca(u, v);
            int lcaFather = stjump[lca][0];
            num[u]++;
            num[v]++;
            num[lca]--;
            num[lcaFather]--;
        }
        dfs2(1, 0);
        dp(1, 0);
        return Math.min(yes, no);
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

    private static int lca(int a, int b) {
        if (deep[a] < deep[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        for (int p = power; p >= 0; p--) {
            if (deep[stjump[a][p]] >= deep[b]) {
                a = stjump[a][p];
            }
        }
        if (a == b) {
            return b;
        }
        for (int p = power; p >= 0; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        return stjump[a][0];
    }

    private static void dfs1(int u, int f) {
        deep[u] = deep[f] + 1;
        stjump[u][0] = f;
        for (int p = 1; p <= power; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                dfs1(to[e], u);
            }
        }
    }

    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    private static void build(int n) {
        cnt = 1;
        power = log2(n);
        Arrays.fill(head, 1, n + 1, 0);
        Arrays.fill(num, 1, n + 1, 0);
    }

    private static int log2(int n) {
        int ans = 0;
        while ((1 << ans) <= (n >> 1)) {
            ans++;
        }
        return ans;
    }
}
