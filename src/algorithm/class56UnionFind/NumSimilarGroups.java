package algorithm.class56UnionFind;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-16 16:59
 * https://leetcode.cn/problems/similar-string-groups/
 */
public class NumSimilarGroups {
    public static int sets;
    public static int[] father = new int[301];
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();
        build(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (find(i) != find(j)) {
                    int diff = 0;
                    for (int k = 0; k < m && diff < 3; k++) {
                        if (strs[i].charAt(k) != strs[j].charAt(k)) {
                            diff++;
                        }
                    }
                    if (diff == 0 || diff == 2) {
                        union(i, j);
                    }
                }
            }
        }
        return sets;
    }
    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fx] = fy;
            sets--;
        }
    }
    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }
    public static void build(int n) {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
        sets = n;
    }
}
