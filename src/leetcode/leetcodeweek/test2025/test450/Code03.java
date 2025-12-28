package leetcode.leetcodeweek.test2025.test450;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/18 9:17
 * https://leetcode.cn/contest/weekly-contest-450/problems/grid-teleportation-traversal/
 */
public class Code03 {
    public static int minMoves(String[] matrix) {
        int[] move = new int[] {-1, 0, 1, 0, -1};
        int n = matrix.length;
        int m = matrix[0].length();
        char[][] grid = getGrid(matrix, n, m);
        List<int[]>[] pos = pos(n, m, grid);
        int[][] distance = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        Deque<int[]> deque = new ArrayDeque<>();
        deque.addFirst(new int[] {0, 0});
        distance[0][0] = 0;
        while (!deque.isEmpty()) {
            int[] cur = deque.pollFirst();
            int x = cur[0];
            int y = cur[1];
            if (x == n - 1 && y == m - 1) {
                return distance[x][y];
            }
            if (grid[x][y] >= 'A' && grid[x][y] <= 'Z') {
                for (int[] next : pos[grid[x][y] - 'A']) {
                    int nx = next[0], ny = next[1];
                    if (distance[nx][ny] > distance[x][y]) {
                        distance[nx][ny] = distance[x][y];
                        deque.addFirst(new int[] {nx, ny});
                    }
                }
                pos[grid[x][y] - 'A'].clear();
            }
            for (int i = 0; i < 4; i++) {
                int nx = x + move[i];
                int ny = y + move[i + 1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && grid[nx][ny] != '#' && distance[nx][ny] > distance[x][y] + 1) {
                    distance[nx][ny] = distance[x][y] + 1;
                    deque.addLast(new int[] {nx, ny});
                }
            }
        }
        return -1;
    }

    private static List<int[]>[] pos(int n, int m, char[][] grid) {
        List<int[]>[] pos = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            pos[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] >= 'A' && grid[i][j] <= 'Z') {
                    pos[grid[i][j] - 'A'].add(new int[] {i, j});
                }
            }
        }
        return pos;
    }

    private static char[][] getGrid(String[] matrix, int n, int m) {
        char[][] grid = new char[n][m];
        for (int i = 0; i < n; i++) {
            grid[i] = matrix[i].toCharArray();
        }
        return grid;
    }
}
