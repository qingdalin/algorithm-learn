package leetcode.leetcodeweek.test2026.test518;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/9/6 10:19
 * https://leetcode.cn/contest/weekly-contest-518/problems/minimum-cost-path-with-at-most-k-turns/
 */
public class Code518_04 {
    // 2 7 3
    // 1 4 5
    // x - 1, y 0
    // x, y + 1 1
    // x + 1, y 2
    // x, y - 1 3
    public static int minCost(int[][] grid, int k0) {
        int[][] move = {{0,-1}, {0,1}, {-1,0},{1,0}};
        int m = grid.length, n = grid[0].length;
        int[][][][] dis = new int[k0 + 1][m][n][4];
        for (int[][][] a : dis) {
            for (int[][] b : a) {
                for (int[] c : b) {
                    Arrays.fill(c, Integer.MAX_VALUE / 2);
                }
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[] {grid[0][0], k0, 0, 0, 1}); // 1向右
        pq.add(new int[] {grid[0][0], k0, 0, 0, 3}); // 3向下
        dis[k0][0][0][1] = dis[k0][0][0][3] = grid[0][0];
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int d = top[0];
            int k = top[1];
            int i = top[2];
            int j = top[3];
            int idx = top[4];
            if (i == m - 1 && j == n - 1) {
                return d;
            }
            if (d > dis[k][i][j][idx]) {
                continue;
            }
            for (int newIdx = 0; newIdx < 4; newIdx++) {
                int x = i + move[newIdx][0];
                int y = j + move[newIdx][1];
                if (x >= 0 && x < m && y >= 0 && y < n) {
                    int newK = k;
                    if (newIdx != idx) {
                        if (k == 0) {
                            continue;
                        }
                        newK--;
                    }
                    int newD = d + grid[x][y];
                    if (newD < dis[newK][x][y][newIdx]) {
                        dis[newK][x][y][newIdx] = newD;
                        pq.add(new int[] {newD, newK, x, y, newIdx});
                    }
                }
            }
        }
        return -1;
    }

    public static int minCost1(int[][] grid, int k) {
        int[] move = {-1, 0, 1, 0, -1};
        int ans = 0, n = grid.length, m = grid[0].length;
        boolean[][] vis = new boolean[n][m];
        int[][] distance = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[2] != b[2]) ? a[2] - b[2] : b[4] - a[4]);
        // arr[3] % 2 == 0 说明是上下移动，否则是左右移动
        heap.add(new int[] {0, 0, grid[0][0], -1, k});
        distance[0][0] = grid[0][0];
        while (!heap.isEmpty()) {
            int[] cur = heap.poll();
            int x = cur[0], y = cur[1], s = cur[2], pos = cur[3], time = cur[4];
            if (vis[x][y]) {
                continue;
            }
            if (x == n - 1 && y == m - 1) {
                return s;
            }
            vis[x][y] = true;
            for (int i = 0; i < 4; i++) {
                if (time == 0) {
                    if (pos == i % 2) {
                        int nx = x + move[i], ny = y + move[i + 1];
                        if (nx >= 0 && nx < n && ny >= 0 && ny < m && !vis[nx][ny]
                            && s + grid[nx][ny] < distance[nx][ny]) {
                            distance[nx][ny] = s + grid[nx][ny];
                            heap.add(new int[]{nx, ny, distance[nx][ny], i % 2, time});
                        }
                    }
                } else {
                    int nx = x + move[i], ny = y + move[i + 1];
                    if (nx >= 0 && nx < n && ny >= 0 && ny < m && !vis[nx][ny]
                        && s + grid[nx][ny] < distance[nx][ny]) {
                        distance[nx][ny] = s + grid[nx][ny];
                        int tmp = time;
                        if (pos != -1 && pos != i % 2) {
                            tmp--;
                        }
                        heap.add(new int[]{nx, ny, distance[nx][ny], i % 2, tmp});
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        int[][] arr = {
//            {2,7,3},
//            {1,4,5}
//        };
//        int k = 1;
        int[][] arr = {
            {4,1,9},
            {3,2,5},
            {4,8,6},
        };
        int k = 2;
        System.out.println(minCost(arr, k));
    }
}
