package leetcode.study.leetcode401_600;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/24 20:15
 * https://leetcode.cn/problems/01-matrix/
 */
public class Leetcode542UpdateMatrix {
    public static int MAXN = 10001;
    public static int[][] que = new int[MAXN][2];
    public static int l, r, n, m;
    public static int[] move = {-1, 0, 1, 0, -1};
    // x - 1, y
    // x, y + 1
    // x + 1, y
    // x, y - 1
    public static int[][] updateMatrix(int[][] arr) {
        n = arr.length;
        m = arr[0].length;
        l = r = 0;
        int[][] ans = new int[n][m];
        boolean[][] vis = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == 0) {
                    vis[i][j] = true;
                    que[r][0] = i;
                    que[r++][1] = j;
                }
            }
        }
        while (l < r) {
            int x = que[l][0];
            int y = que[l++][1];
            for (int p = 0; p < 4; p++) {
                int nx = x + move[p];
                int ny = y + move[p + 1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && !vis[nx][ny]) {
                    ans[nx][ny] = 1 + ans[x][y];
                    que[r][0] = nx;
                    que[r++][1] = ny;
                    vis[nx][ny] = true;
                }
            }
        }
        return ans;
    }

    private static int f(int[][] arr, int i, int j) {
        l = r = 0;
        que[r][0] = i;
        que[r++][1] = j;
        int level = 0;
        boolean[][] vis = new boolean[n][m];
        vis[i][j] = true;
        while (l < r) {
            int size = r - l;
            level++;
            for (int k = 0; k < size; k++) {
                int x = que[l][0];
                int y = que[l++][1];
                if (arr[x][y] == 0) {
                    return level - 1;
                }
                for (int p = 0; p < 4; p++) {
                    int nx = x + move[p];
                    int ny = y + move[p + 1];
                    if (nx >= 0 && nx < n && ny >= 0 && ny < m && !vis[nx][ny]) {
                        que[r][0] = nx;
                        que[r++][1] = ny;
                        vis[nx][ny] = true;
                    }
                }
            }
        }
        return level;
    }

    public static void main(String[] args) {
        int[][] arr = {
            {0,0,0},
            {0,1,0},
            {1,1,1}
        };
        // int[][] t = [[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[1,1,1],[0,0,0]];
        System.out.println(Arrays.deepToString(updateMatrix(arr)));
    }


}
