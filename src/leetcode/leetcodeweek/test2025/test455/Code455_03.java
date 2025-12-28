package leetcode.leetcodeweek.test2025.test455;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/22 10:01
 * https://leetcode.cn/contest/weekly-contest-455/problems/minimum-increments-to-equalize-leaf-paths/
 */
public class Code455_03 {
    public static int MAXN = 100001;
    public static int[] head = new int[MAXN];
    public static int[] weight;
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt = 0;
    public static int ans;
    public static int m, n;
    public static int minIncrease(int length, int[][] edges, int[] cost) {
        n = length;
        build(edges, cost);
        return dfs(1, 0);
    }

    public static int[][] ufe = new int[MAXN][3];
    public static int u, f, e, stackSize;

    public static void push(int u, int f, int e) {
        ufe[stackSize][0] = u;
        ufe[stackSize][1] = f;
        ufe[stackSize][2] = e;
        stackSize++;
    }

    public static void pop() {
        stackSize--;
        u = ufe[stackSize][0];
        f = ufe[stackSize][1];
        e = ufe[stackSize][2];
    }

    public static void dfs() {
        stackSize = 0;
        push(1, 0, -1);
        while (stackSize > 0) {
            pop();
            Set<Integer> set = new HashSet<>();
            if (e == -1) {
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (to[e] != f) {
                    push(to[e], u, -1);
                }
            }
        }
    }

    private static int dfs(int u, int f) {
        int ans = 0;
        Set<Integer> set = new HashSet<>();
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                set.add(weight[v]);
                ans += dfs(v, u);
            }
        }
        if (set.isEmpty()) {
            return 0;
        }
        return ans + set.size() - 1;
    }

    private static void build(int[][] edges, int[] cost) {
        m = edges.length;
        ans = 0;
        for (int i = 0, u, v; i < m; i++) {
            u = edges[i][0] + 1;
            v = edges[i][1] + 1;
            addEdge(u, v);
            addEdge(v, u);
        }
        weight = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            weight[i] = cost[i - 1];
        }
    }

    public static void addEdge(int u, int v) {
        next[++cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt;
    }

    public static void main(String[] args) {
//        int n = 5;
//        int[][] edges = new int[][] {
//            {0, 4},
//            {0, 1},
//            {1, 2},
//            {1, 3}
//        };
//        int[] cost = new int[] {3,4,1,1,7};
        int n = 3;
        int[][] edges = new int[][] {
            {0, 1},
            {1, 2}
        };
        int[] cost = new int[] {5,1,4};
        System.out.println(minIncrease(n, edges, cost));
    }
}
