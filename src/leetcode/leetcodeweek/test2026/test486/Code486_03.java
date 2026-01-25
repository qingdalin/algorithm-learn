package leetcode.leetcodeweek.test2026.test486;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/25 9:40
 * https://leetcode.cn/contest/weekly-contest-486/problems/pythagorean-distance-nodes-in-a-tree/description/
 */
public class Code486_03 {
    public static int MAXN = 100001;
    public static int n;
    public static int[] dx = new int[MAXN];
    public static int[] dy = new int[MAXN];
    public static int[] dz = new int[MAXN];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg;
    public static int specialNodes(int len, int[][] edges, int x, int y, int z) {
        build(len, edges);
        dfs(x + 1, 0, 0, dx);
        dfs(y + 1, 0, 0, dy);
        dfs(z + 1, 0, 0, dz);
        int ans = 0;
        long[] arr = new long[3];
        for (int i = 1; i <= n; i++) {
            arr[0] = dx[i];
            arr[1] = dy[i];
            arr[2] = dz[i];
            Arrays.sort(arr);
            if (arr[0] * arr[0] + arr[1] * arr[1] == arr[2] * arr[2]) {
                ans++;
            }
        }
        return ans;
    }

    private static void dfs(int u, int fa, int dist, int[] arr) {
        arr[u] = dist;
        for (int e = head[u]; e > 0; e = next[e]) {
            int v = to[e];
            if (v != fa) {
                dfs(v, u, dist + 1, arr);
            }
        }
    }

    private static void build(int len, int[][] edges) {
        n = len;
        cntg = 0;
        for (int i = 0; i <= n; i++) {
            dx[i] = dy[i] = dz[i] = head[i] = 0;
        }
        for (int i = 0, u, v; i < n - 1; i++) {
            u = edges[i][0] + 1;
            v = edges[i][1] + 1;
            addEdge(u, v);
            addEdge(v, u);
        }
    }

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void main(String[] args) {
        int n = 4;
        int x = 1;
        int y = 2;
        int z = 3;
        int[][] edge = {
            {0,1},
            {0,2},
            {0,3}
        };
        System.out.println(specialNodes(n, edge, x, y, z));
    }
}
