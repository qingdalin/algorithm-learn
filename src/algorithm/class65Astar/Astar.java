package algorithm.class65Astar;

import java.util.PriorityQueue;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-22 20:46
 * A星算法
 */
public class Astar {
    public static int[] move = new int[]{-1, 0, 1, 0, -1};
    public static int dijkstra(int[][] grid, int startx, int starty, int targetx, int targety) {
        if (grid[startx][starty] == 0 || grid[targetx][targety] == 0) {
            return -1;
        }
        int n = grid.length;
        int m = grid[0].length;
        int[][] distance = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        boolean[][] visited = new boolean[n][m];
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        distance[startx][starty] = 1;
        heap.add(new int[] {startx, starty, 1});
        while (!heap.isEmpty()) {
            int[] record = heap.poll();
            int x = record[0];
            int y = record[1];
            if (visited[x][y]) {
                continue;
            }
            if (x == targetx && y == targety) {
                return distance[x][y];
            }
            visited[x][y] = true;
            for (int i = 0; i < 4; i++) {
                int nx = x + move[i];
                int ny = y + move[i + 1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m
                    && !visited[nx][ny] && distance[x][y] + 1 < distance[nx][ny]) {
                    distance[nx][ny] = distance[x][y] + 1;
                    heap.add(new int[] {nx, ny, distance[x][y] + 1});
                }
            }
        }
        return -1;
    }
    // 曼哈顿距离
    public static int f1(int x, int y, int targetx, int targety) {
        return Math.abs(x - targetx) + Math.abs(y - targety);
    }
    // 对角线距离
    public static int f2(int x, int y, int targetx, int targety) {
        return Math.max(Math.abs(x - targetx), Math.abs(y - targety));
    }

    public static int astar(int[][] grid, int startx, int starty, int targetx, int targety) {
        if (grid[startx][starty] == 0 || grid[targetx][targety] == 0) {
            return -1;
        }
        int n = grid.length;
        int m = grid[0].length;
        int[][] distance = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        boolean[][] visited = new boolean[n][m];
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        distance[startx][starty] = 1;
        heap.add(new int[] {startx, starty, 1 + f1(startx, starty, targetx, targety)});
        while (!heap.isEmpty()) {
            int[] record = heap.poll();
            int x = record[0];
            int y = record[1];
            if (visited[x][y]) {
                continue;
            }
            if (x == targetx && y == targety) {
                return distance[x][y];
            }
            visited[x][y] = true;
            for (int i = 0; i < 4; i++) {
                int nx = x + move[i];
                int ny = y + move[i + 1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m
                    && !visited[nx][ny] && distance[x][y] + 1 < distance[nx][ny]) {
                    distance[nx][ny] = distance[x][y] + 1;
                    heap.add(new int[] {nx, ny, distance[x][y] + 1 + f1(nx, ny, targetx, targety)});
                }
            }
        }
        return -1;
    }

    public static int[][] randomGrid(int n) {
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Math.random() < 0.3) {
                    grid[i][j] = 0;
                } else {
                    grid[i][j] = 1;
                }
            }
        }
        return grid;
    }

    public static void main(String[] args) {
        int len = 100; // 100 * 100 网格
        int timeTest = 10000;
        System.out.println("功能测试开始");
        for (int i = 0; i < timeTest; i++) {
            int n = (int) ((Math.random() * len) + 2);
            int[][] grid = randomGrid(n);
            int startx = (int) (Math.random() * n);
            int starty = (int) (Math.random() * n);
            int targetx = (int) (Math.random() * n);
            int targety = (int) (Math.random() * n);
            int ans1 = dijkstra(grid, startx, starty, targetx, targety);
            int ans2 = astar(grid, startx, starty, targetx, targety);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("功能测试结束");

        System.out.println("性能测试开始");
        int[][] g = randomGrid(4000);
        int startx = 0, starty = 0;
        int targetx = 3900, targety = 3900;
        long start = System.currentTimeMillis();
        int ans1 = dijkstra(g, startx, starty, targetx, targety);
        long end = System.currentTimeMillis();
        System.out.println("dijkstra算法结果：" + ans1 + "，耗时：" + (end - start));
        start = System.currentTimeMillis();
        int ans2 = astar(g, startx, starty, targetx, targety);
        end = System.currentTimeMillis();
        System.out.println("A星算法结果：" + ans2 + "，耗时：" + (end - start));
        System.out.println("性能测试结束");
    }
}
