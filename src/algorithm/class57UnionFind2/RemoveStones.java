package algorithm.class57UnionFind2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-17 12:10
 * https://leetcode.cn/problems/most-stones-removed-with-same-row-or-column/
 * 并查集
 */
public class RemoveStones {
    public static Map<Integer, Integer> colFirst = new HashMap<>();
    public static Map<Integer, Integer> rowFirst = new HashMap<>();
    public static int[] father = new int[1000];
    public static int sets;
    public int removeStones(int[][] stones) {
        int n = stones.length;
        build(n);
        for (int i = 0; i < n; i++) {
            int row = stones[i][0];
            int col = stones[i][1];
            if (!rowFirst.containsKey(row)) {
                rowFirst.put(row, i);
            } else {
                union(i, rowFirst.get(row));
            }
            if (!colFirst.containsKey(col)) {
                colFirst.put(col, i);
            } else {
                union(i, colFirst.get(col));
            }
        }
        return n - sets;
    }

    public int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }
    public void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fx] = fy;
            sets--;
        }
    }

    private void build(int n) {
        colFirst.clear();
        rowFirst.clear();
        for (int i = 0; i < n; i++) {
            father[i] = i;
        }
        sets = n;
    }
}
