package leetcode.study.leetcdoe201_400;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 13:52
 * https://leetcode.cn/problems/course-schedule/
 */
public class Leetcode207CanFinish {
    public static int MAXN = 5001;
    public static int[] queue = new int[MAXN];
    public static int[] indegree = new int[MAXN];
    public static int l, r, n;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN];
    public static int[] to = new int[MAXN];
    public static int cnt = 0;
    public boolean canFinish(int len, int[][] edges) {
        build(len);

        for (int[] edge : edges) {
            int u = edge[1] + 1;
            int v = edge[0] + 1;
            addEdge(u, v);
            indegree[v]++;
        }
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                ans++;
                queue[r++] = i;
            }
        }
        while (l < r) {
            int size = r - l;
            for (int i = 0; i < size; i++) {
                int cur = queue[l++];
                for(int e = head[cur], v; e > 0; e = next[e]) {
                    v = to[e];
                    if (--indegree[v] == 0) {
                        ans++;
                        queue[r++] = v;
                    }
                    if (ans == n) {
                        return true;
                    }
                }
            }
        }
        return ans == n;
    }

    public static void addEdge(int u, int v) {
        next[++cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt;
    }

    public static void build(int len) {
        n = len;
        cnt = 0;
        r = l = 0;
        Arrays.fill(head, 0, n + 1, 0);
        Arrays.fill(indegree, 0, n + 1, 0);
    }
}
