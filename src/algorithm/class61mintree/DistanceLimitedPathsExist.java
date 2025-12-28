package algorithm.class61mintree;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-06 14:08
 * https://leetcode.cn/problems/checking-existence-of-edge-length-limited-paths/
 * 并查集和最小生成树
 */
public class DistanceLimitedPathsExist {
    public static int MAXN = 100001;
    public static int[] father = new int[MAXN];
    public static int[][] questions = new int[MAXN][4];
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        int m = edgeList.length;
        Arrays.sort(edgeList, 0, m, (a, b) -> a[2] - b[2]);
        int k = queries.length;
        boolean[] ans = new boolean[k];
        for (int i = 0; i < k; i++) {
            questions[i][0] = queries[i][0];
            questions[i][1] = queries[i][1];
            questions[i][2] = queries[i][2];
            questions[i][3] = i;
        }
        build(n);
        Arrays.sort(questions, 0, k, (a, b) -> a[2] - b[2]);
        for (int i = 0, j = 0; i < k; i++) {
            for (; j < m && edgeList[j][2] < questions[i][2]; j++) {
                union(edgeList[j][0], edgeList[j][1]);
            }
            ans[questions[i][3]] = isSameSet(questions[i][0], questions[i][1]);
        }
        return ans;
    }
    public static void build(int n) {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static boolean isSameSet(int x, int y) {
        return find(x) == find(y);
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fx] = fy;
        }
    }
}
