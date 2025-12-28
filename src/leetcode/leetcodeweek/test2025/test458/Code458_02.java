package leetcode.leetcodeweek.test2025.test458;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/13 9:04
 * https://leetcode.cn/contest/weekly-contest-458/problems/minimize-maximum-component-cost/
 */
public class Code458_02 {
    public static int MAXN = 50001;
    public static int[] father = new int[MAXN];
    public static int n, k, cnt, ans;
    public static int minCost(int len, int[][] edges, int kNum) {
        build(len, kNum, edges);
        for (int[] edge : edges) {
            int x = edge[0] + 1;
            int y = edge[1] + 1;
            int w = edge[2];
            int fx = find(x);
            int fy = find(y);
            if (fx != fy) {
                union(fx, fy);
                ans = Math.max(ans, w);
            }
            if (cnt == k) {
                return ans;
            }
        }
        return 0;
    }

    private static void build(int len, int kNum, int[][] edges) {
        n = len;
        k = kNum;
        cnt = n;
        ans = Integer.MIN_VALUE;
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fy] = fx;
            cnt--;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] arr = new int[][] {
            {0, 1, 4},
            {1, 2, 3},
            {1, 3, 2},
            {3, 4, 6},
        };
        int k = 2;
        System.out.println(minCost(n, arr, k));
    }
}
