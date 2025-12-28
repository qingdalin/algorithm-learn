package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 11:21
 * https://leetcode.cn/problems/minimum-height-trees/
 */
public class Leetcode310FindMinHeightTrees {
    public static int MAXN = 20001;
    public static int n;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt = 0;
    public static int[] deep = new int[MAXN];
    public static List<Integer> ans = new ArrayList<>();
    public static int max;
    public static List<Integer> findMinHeightTrees(int total, int[][] edges) {
        if (total == 1) {
            ans.clear();
            ans.add(0);
            return ans;
        }
        if (total == 2) {
            ans.clear();
            ans.add(0);
            ans.add(1);
            return ans;
        }
        n = total;
        build(edges);
        dfs1(1, 0);
        int i = dfs2(1, 0);
        return ans;
    }

    private static int dfs2(int u, int f) {
        int cur = deep[u];
        int son = -10000000;
        for (int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                son = Math.max(son, dfs2(v, u));
            }
        }
        if (son == -10000000) {
            return cur;
        }
        int dep = Math.max(cur, son - cur + 1);
        if (dep < max) {
            ans.clear();
            ans.add(u - 1);
            max = dep;
        } else if (dep == max) {
            ans.add(u - 1);
        }
        return Math.max(cur, son);
    }

    private static void dfs1(int u, int f) {
        deep[u] = deep[f] + 1;
        for (int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs1(v, u);
            }
        }
    }

    public static void build(int[][] edges) {
        for (int i = 0; i <= n; i++) {
            head[i] = 0;
        }
        max = Integer.MAX_VALUE;
        ans.clear();
        cnt = 0;
        for (int[] edge : edges) {
            int u = edge[0] + 1;
            int v = edge[1] + 1;
            addEdge(u, v);
            addEdge(v, u);
        }
    }

    public static void addEdge(int u, int v) {
        next[++cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt;
    }

    public static void main(String[] args) {
//        int n = 6;
//        int[][] arr = new int[][]{
//            {3, 0},
//            {3, 1},
//            {3, 2},
//            {3, 4},
//            {5, 4}
//        };
//        int n = 4;
//        int[][] arr = new int[][]{
//            {1, 0},
//            {1, 2},
//            {1, 3}
//        };
        int n = 2;
        int[][] arr = new int[][]{
            {0, 1}
        };
        System.out.println(findMinHeightTrees(n, arr));
    }
}
