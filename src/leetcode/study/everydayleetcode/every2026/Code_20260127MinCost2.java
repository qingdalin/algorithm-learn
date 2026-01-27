package leetcode.study.everydayleetcode.every2026;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/27 18:51
 * https://leetcode.cn/problems/minimum-cost-path-with-edge-reversals/?envType=daily-question&envId=2026-01-27
 */
public class Code_20260127MinCost2 {
    public static int MAXN = 50001;
    public static int n;
    public static int[] dis = new int[MAXN];
    public static boolean[] vis = new boolean[MAXN];
    public static List<List<int[]>> graph = new ArrayList<>();
    public static PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    public static int minCost(int len, int[][] edges) {
        build(len, edges);
        return dijkstra();
    }

    private static int dijkstra() {
        dis[0] = 0;
        heap.add(new int[] {0, 0});
        while (!heap.isEmpty()) {
            int[] cur = heap.poll();
            int curDist = cur[0];
            int u = cur[1];
            if (u == n - 1) {
                return curDist;
            }
            if (vis[u]) {
                continue;
            }
            vis[u] = true;
            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                int w = edge[1];
                if (w + curDist < dis[v]) {
                    dis[v] = w + curDist;
                    heap.add(new int[] {dis[v], v});
                }
            }
        }
        return -1;
    }

    private static void build(int len, int[][] edges) {
        n = len;
        heap.clear();
        graph.clear();
        for (int i = 0; i <= n; i++) {
            dis[i] = Integer.MAX_VALUE;
            vis[i] = false;
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            graph.get(u).add(new int[] {v, w});
            graph.get(v).add(new int[] {u, w * 2});
        }
    }
}
