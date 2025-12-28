package leetcode.leetcodeweek.test2025.test449;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/11 9:50
 * https://leetcode.cn/contest/weekly-contest-449/problems/maximum-sum-of-edge-values-in-a-graph/
 */
public class Code03 {
    public static int MAXN = 50001;
    public static int[] father = new int[MAXN];
    public static boolean[] visit = new boolean[MAXN];
    public static long maxScore(int n, int[][] edges) {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
        long t = n;
        long ans = 0;
        for (int i = 0, x, y, fx, fy; i < n; i++) {
            x = edges[i][0];
            y = edges[i][1];
            fx = find(x);
            fy = find(y);
            if (fx != fy) {
                if (!visit[x] && !visit[y]) {
                    ans = t * (t - 1);
                    t -= 2;
                    visit[x] = visit[y] = true;
                } else if (visit[x] && !visit[y]) {

                }
            }


        }
        return ans;
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(i);
        }
        return father[i];
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fy] = fx;
        }
    }
}
