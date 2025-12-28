package algorithm.class56UnionFind;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-16 16:42
 * https://leetcode.cn/problems/couples-holding-hands/
 * 情侣牵手：并查集
 */
public class MinSwapsCouples {
    public static int[] father = new int[30];
    public static int sets;
    public int minSwapsCouples(int[] row) {
        int n = row.length;
        build(n / 2);
        for (int i = 0; i < n; i += 2) {
            union(row[i] / 2, row[i + 1] / 2);
        }
        return n / 2 - sets;
    }
    public static void build(int m) {
        for (int i = 0; i < m; i++) {
            father[i] = i;
        }
        sets = m;
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
}
