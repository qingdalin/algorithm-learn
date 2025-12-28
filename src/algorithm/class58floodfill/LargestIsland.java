package algorithm.class58floodfill;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-19 19:41
 * https://leetcode.cn/problems/making-a-large-island/
 * 洪水填充，最大人工岛
 */
public class LargestIsland {
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int id = 2;
        // 将连在一起的岛屿设置编号从2开始
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, n, m, i, j, id++);
                }
            }
        }
        // 记录未连在一起时，最大岛屿面积
        int[] size = new int[id];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] > 1) {
                    ans = Math.max(ans, ++size[grid[i][j]]);
                }
            }
        }
        // 访问过的地方记录
        int up, down, left, right, merge;
        boolean[] visited = new boolean[id];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    up = i > 0 ? grid[i - 1][j] : 0;
                    down = i < n - 1 ? grid[i + 1][j] : 0;
                    left = j > 0 ? grid[i][j - 1] : 0;
                    right = j < m - 1 ? grid[i][j + 1] : 0;
                    // 默认上被访问过
                    visited[up] = true;
                    merge = 1 + size[up];
                    if (!visited[down]) {
                        merge += size[down];
                        visited[down] = true;
                    }
                    if (!visited[left]) {
                        merge += size[left];
                        visited[left] = true;
                    }
                    if (!visited[right]) {
                        merge += size[right];
                        visited[right] = true;
                    }
                    ans = Math.max(ans, merge);
                    visited[up] = false;
                    visited[down] = false;
                    visited[left] = false;
                    visited[right] = false;
                }
            }
        }
        return ans;
    }
    public void dfs(int[][] grid, int n, int m, int i, int j, int id) {
        if (i < 0 || i == n || j < 0 || j == m || grid[i][j] != 1) {
            return;
        }
        grid[i][j] = id;
        dfs(grid, n, m, i - 1, j, id);
        dfs(grid, n, m, i + 1, j, id);
        dfs(grid, n, m, i, j - 1, id);
        dfs(grid, n, m, i, j + 1, id);
    }
}
