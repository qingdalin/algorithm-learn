package leetcode.leetcodeweek.test2025.test457;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/6 9:50
 * https://leetcode.cn/contest/weekly-contest-457/problems/power-grid-maintenance/
 */
public class Code457_02 {
    public static int MAXN = 100001;
    public static int n;
    public static int[] father = new int[MAXN];
    public static boolean[] vis = new boolean[MAXN];
    public static TreeSet<Integer>[] set = new TreeSet[MAXN];
    public static int cnt = 0;
    static {
        for (int i = 0; i < MAXN; i++) {
            set[i] = new TreeSet<>();
        }
    }
    public static int[] processQueries(int c, int[][] connections, int[][] queries) {
        n = c;
        build();
        for (int[] cur : connections) {
            int x = cur[0];
            int y = cur[1];
            int fx = find(x);
            int fy = find(y);
            union(fx, fy);
        }
        for (int i = 1; i <= n; i++) {
            if (father[i] == i) {
                if (set[cnt + 1] == null) {
                    set[++cnt] = new TreeSet<>();
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            set[find(i)].add(i);
        }
        List<Integer> ans = new ArrayList<>();
        for (int[] cur : queries) {
            TreeSet<Integer> list = set[father[cur[1]]];
            int res = -1;
            if (cur[0] == 1) {
                if (vis[cur[1]]) {
                    while (!list.isEmpty()) {
                        if (!vis[list.first()]) {
                            res = list.first();
                            break;
                        } else {
                            list.pollFirst();
                        }
                    }
//                    for (int num : list) {
//                        if (!vis[num]) {
//                            res = num;
//                            break;
//                        }
//                    }
                } else {
                    res = cur[1];
                }
                ans.add(res);
            } else {
                vis[cur[1]] = true;
            }
        }
        int size = ans.size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = ans.get(i);
        }
        return result;
    }

    private static void build() {
        cnt = 0;
        for (int i = 0; i <= n; i++) {
            father[i] = i;
            vis[i] = false;
            set[i].clear();
        }
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
        }
    }

    public static void main(String[] args) {
        int c = 3;
        int[][] connections = new int[][] {
//            {1, 2},
//            {2, 3},
//            {3, 4},
//            {4, 5}
        };
        int[][] queries = new int[][] {
//            {1, 3},
//            {2, 1},
//            {1, 1},
//            {2, 2},
//            {1, 2}
            {1, 1},
            {2, 1},
            {1, 1}
        };
        System.out.println(Arrays.toString(processQueries(c, connections, queries)));
    }
}
