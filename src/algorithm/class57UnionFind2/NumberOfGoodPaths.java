package algorithm.class57UnionFind2;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-17 14:25
 * https://leetcode.cn/problems/number-of-good-paths/
 * 并查集
 */
public class NumberOfGoodPaths {
    public static int MAXN = 30000;
    public static int[] father = new int[MAXN];
    public static int[] maxCnt = new int[MAXN];
    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        int n = vals.length;
        build(n);
        int ans = n;
        Arrays.sort(edges, (e1, e2) -> Math.max(vals[e1[0]], vals[e1[1]]) - Math.max(vals[e2[0]], vals[e2[1]]));
        for (int[] edge : edges) {
            ans += union(edge[0], edge[1], vals);
        }
        return ans;
    }

    public int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public int union(int x, int y, int[] vals) {
        int fx = find(x);
        int fy = find(y);
        int path = 0;
        if (vals[fx] > vals[fy]) {
            father[fy] = fx;
        } else if (vals[fx] < vals[fy]) {
            father[fx] = fy;
        } else {
            path = maxCnt[fx] * maxCnt[fy];
            father[fy] = fx;
            maxCnt[fx] += maxCnt[fy];
        }
        return path;
    }

    private void build(int n) {
        for (int i = 0; i < n; i++) {
            father[i] = i;
            maxCnt[i] = 1;
        }
    }
}
