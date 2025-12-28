package algorithm.class62bfs;

import java.util.PriorityQueue;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-14 10:57
 * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
 * https://leetcode.cn/problems/trapping-rain-water-ii/description/
 */
public class TrapRainWater {
    public int trapRainWater(int[][] heightMap) {
        int n = heightMap.length;
        int m = heightMap[0].length;
        // 0  ->  行
        // 1  ->  列
        // 2  ->  雨水限度
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                    heap.add(new int[] {i, j, heightMap[i][j]});
                    visited[i][j] = true;
                } else {
                    visited[i][j] = false;
                }
            }
        }
        int[] move = new int[] {-1, 0, 1, 0, -1};
        int ans = 0;
        while (!heap.isEmpty()) {
            int[] record = heap.poll();
            int r = record[0];
            int c = record[1];
            int w = record[2];
            ans += w - heightMap[r][c];
            for (int i = 0; i < 4; i++) {
                int nr = r + move[i];
                int nc = c + move[i + 1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc]) {
                    heap.add(new int[]{nr, nc, Math.max(heightMap[nr][nc], w)});
                    visited[nr][nc] = true;
                }
            }
        }
        return ans;
    }
}
