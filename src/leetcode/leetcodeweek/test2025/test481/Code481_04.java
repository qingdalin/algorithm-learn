package leetcode.leetcodeweek.test2025.test481;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/21 10:10
 * https://leetcode.cn/problems/total-sum-of-interaction-cost-in-tree-groups/solutions/3862088/gong-xian-fa-pythonjavacgo-by-endlessche-4nxs/
 */
public class Code481_04 {
    public static int MAXN = 100001;
    public static int MAXM = 21;
    public static int[] total = new int[MAXM];
    public static int[] head = new int[MAXN];
    public static int[] g = new int[MAXN];
    public static int[] nxt = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg = 0;
    public static int n, mx;
    public static long ans;
    public static long interactionCosts(int len, int[][] edges, int[] group) {
        build(len, edges, group);
        dfs(1, 0);
        return ans;
    }

    private static int[] dfs(int u, int fa) {
        int[] cntX = new int[mx + 1];
        cntX[g[u]] = 1;
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa) {
                int[] cntY = dfs(v, u);
                for (int i = 0; i <= mx; i++) {
                    ans += (long) cntY[i] * (total[i] - cntY[i]);
                    cntX[i] += cntY[i];
                }
            }
        }
        return cntX;
    }

    private static void build(int len, int[][] edges, int[] group) {
        n = len;
        cntg = 0;
        ans = 0;
        mx = 0;
        Arrays.fill(head, 1, n + 1, 0);
        Arrays.fill(total, 1, MAXM, 0);
        for (int[] edge : edges) {
            int u = edge[0] + 1;
            int v = edge[1] + 1;
            addEdge(u, v);
            addEdge(v, u);
        }
        for (int i : group) {
            total[i]++;
            mx = Math.max(mx, i);
        }
        for (int i = 1; i <= n; i++) {
            g[i] = group[i - 1];
        }
    }

    public static void addEdge(int u, int v) {
        nxt[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }
}
