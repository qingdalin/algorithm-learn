package leetcode.study.everydayleetcode.every2026;

import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/27 18:51
 * https://leetcode.cn/problems/minimum-cost-path-with-edge-reversals/?envType=daily-question&envId=2026-01-27
 */
public class Code_20260127MinCost {
    public static int MAXN = 50001;
    public static int n;
    public static int[] dis = new int[MAXN];
    public static boolean[] vis = new boolean[MAXN];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cntg;
    public static PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    public static int minCost(int len, int[][] edges) {
        build(len, edges);
        return dijkstra();
    }

    private static int dijkstra() {
        dis[1] = 0;
        heap.add(new int[] {0, 1});
        while (!heap.isEmpty()) {
            int[] cur = heap.poll();
            int curDist = cur[0];
            int u = cur[1];
            if (u == n) {
                return curDist;
            }
            if (vis[u]) {
                continue;
            }
            vis[u] = true;
            for (int e = head[u], v, w; e > 0; e = next[e]) {
                v = to[e];
                w = weight[e];
                if (curDist + w < dis[v]) {
                    dis[v] = curDist + w;
                    heap.add(new int[] {dis[v], v});
                }
            }
        }
        return -1;
    }

    private static void build(int len, int[][] edges) {
        n = len;
        cntg = 0;
        for (int i = 0; i <= n; i++) {
            dis[i] = Integer.MAX_VALUE;
            head[i] = 0;
            vis[i] = false;
        }
        heap.clear();
        for (int[] edge : edges) {
            int u = edge[0] + 1;
            int v = edge[1] + 1;
            int w = edge[2];
            addEdge(u, v, w);
            addEdge(v, u, w * 2);
        }
    }

    private static void addEdge(int u, int v, int w) {
        next[++cntg] = head[u];
        to[cntg] = v;
        weight[cntg] = w;
        head[u] = cntg;
    }
}
