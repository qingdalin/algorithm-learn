package algorithm.class56UnionFind;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-16 17:21
 * https://leetcode.cn/problems/number-of-islands/
 * 并查集做法和洪水填充做法
 */
public class NumIslands {
    /*
        并查集方式
     */
    public static int[] father = new int[90001];
    public static int sets, col;
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        build(n, m, grid);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    if (j > 0 && grid[i][j - 1] == '1') {
                        union(i, j, i, j - 1);
                    }
                    if (i > 0 && grid[i - 1][j] == '1') {
                        union(i, j, i - 1, j);
                    }
                }
            }
        }
        return sets;
    }
    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void union(int a, int b, int c, int d) {
        int fx = find(index(a, b));
        int fy = find(index(c, d));
        if (fx != fy) {
            father[fx] = fy;
            sets--;
        }
    }

    private void build(int n, int m, char[][] grid) {
        sets = 0;
        col = m;
        for (int i = 0; i < n; i++) {
            for (int j = 0, index; j < m; j++) {
                if (grid[i][j] == '1') {
                    sets++;
                    index = index(i, j);
                    father[index] = index;
                }
            }
        }
    }
    public static int index(int a, int b) {
        return a * col + b;
    }

    public int numIslands1(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }

    public void dfs(char[][] grid, int i, int j) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }
}
