package leetcode.leetcodeweek.test2025.test457;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/6 9:50
 * https://leetcode.cn/contest/weekly-contest-457/problems/minimum-time-for-k-connected-components/
 */
public class Code457_03 {
    public static int MAXN = 100001;
    public static int n, cnt;
    public static int[] father = new int[MAXN];
    public static int minTime(int len, int[][] edges, int k) {
        n = len;
        cnt = n;
        Arrays.sort(edges, (a, b) -> b[2] - a[2]);
        build();
        for (int[] edge : edges) {
            int fx = find(edge[0] + 1);
            int fy = find(edge[1] + 1);
            union(fx, fy);
            if (cnt < k) {
                return edge[2];
            }
        }

        return 0;
    }

    public static int find(int i) {
        if (father[i] != i) {
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

    private static void build() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
    }

    public static void main(String[] args) {
        int[][] edges = new int[][] {
//            {0,1,2},
//            {1,2,4}
            {0,1,3}
        };
        int k = 2;
        int n = 2;
        System.out.println(minTime(n, edges, k));
    }
}
