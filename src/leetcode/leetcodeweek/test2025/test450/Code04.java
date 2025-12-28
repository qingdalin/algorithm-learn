package leetcode.leetcodeweek.test2025.test450;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/18 9:17
 * https://leetcode.cn/contest/weekly-contest-450/problems/minimum-weighted-subgraph-with-the-required-paths-ii/
 */
public class Code04 {
    public static int MAXN = 100001;
    public static int MAXH = 19;
    public static int n, power;
    public static int[] head = new int[MAXN << 1];
    public static int[] next = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt = 0;
    public static int[][] stjump = new int[MAXN][MAXH];
    public static int[] deep = new int[MAXN];
    public static int[] distance = new int[MAXN];
    public static int[] minimumWeight(int[][] edges, int[][] queries) {
        n = edges.length;
        build(n);
        for (int i = 0; i < n; i++) {
            addEdge(edges[i][0] + 1, edges[i][1] + 1, edges[i][2]);
            addEdge(edges[i][1] + 1, edges[i][0] + 1, edges[i][2]);
        }
        dfs(1, 0);
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int a = queries[i][0] + 1, b = queries[i][1] + 1, c = queries[i][2] + 1;
            int sum = dis(a, b, lca(a, b));
            int d1 = dis(a, c, lca(a, c));
            int d2 = dis(b, c, lca(b, c));
            ans[i] = (sum + d1 + d2) / 2;
        }
        return ans;
    }

    public static int dis(int a, int b, int lca) {
        return distance[a] + distance[b] - 2 * distance[lca];
    }

    public static int log2(int n) {
        int ans = 0;
        while ((1 << ans) <= (n >> 1)) {
            ans++;
        }
        return ans;
    }

    public static void build(int n) {
        power = log2(n);
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
    }

    public static void addEdge(int u, int v, int w) {
        next[cnt] = head[u];
        to[cnt] = v;
        weight[cnt] = w;
        head[u] = cnt++;
    }

    public static void dfs(int u, int f) {
        deep[u] = deep[f] + 1;
        stjump[u][0] = f;
        for (int p = 1; p <= power; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                distance[to[e]] = weight[e] + distance[u];
                dfs(to[e], u);
            }
        }
    }

    public static int lca(int a, int b) {
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
            return a;
        }
        for (int p = power; p >= 0; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        return stjump[a][0];
    }

    public static void main(String[] args) {
//         int[][] edge = new int[][] {{0,1,2},{1,2,3},{1,3,5},{1,4,4},{2,5,6}};
//        int[][] q = new int[][] {{2,3,4},{0,2,5}};
        int[][] edge = new int[][] {{1,0,8},{0,2,7}};
        int[][] q = new int[][] {{0,1,2}};
        System.out.println(Arrays.toString(minimumWeight(edge, q)));
    }
}
