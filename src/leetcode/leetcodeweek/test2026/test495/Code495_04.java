package leetcode.leetcodeweek.test2026.test495;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/29 10:24
 * https://leetcode.cn/contest/weekly-contest-495/problems/incremental-even-weighted-cycle-queries/description/
 * 带权并查集
 */
public class Code495_04 {
    public static int MAXN = 50001;
    public static int[] father = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int n;
    public static int numberOfEdgesAdded(int len, int[][] edges) {
        build(len);
        int ans = 0;
        for (int[] edge : edges) {
            if (union(edge[0] + 1, edge[1] + 1, edge[2])) {
                ans++;
            }
        }
        return ans;
    }

    private static void build(int len) {
        n = len;
        for (int i = 1; i <= n; i++) {
            father[i] = i;
            siz[i] = 0;
        }
    }

    public static int find(int i) {
        if (i != father[i]) {
            int fa = find(father[i]);
            siz[i] ^= siz[father[i]];
            father[i] = fa;
        }
        return father[i];
    }

    public static boolean union(int x, int y, int w) {
        int fx = find(x);
        int fy = find(y);
        if (fx == fy) {
            return (siz[x] ^ siz[y]) == w;
        }
        siz[fx] = w ^ siz[y] ^ siz[x];
        father[fx] = fy;
        return true;
    }
}
