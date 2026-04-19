package leetcode.leetcodeweek.test2026.test498;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/19 9:55
 * https://leetcode.cn/contest/weekly-contest-498/problems/multi-source-flood-fill/
 */
public class Code498_03 {
    public static int MAXN = 100001;
    public static int[][] que = new int[MAXN][3];
    public static int l, r;
    public static int[] move = {-1, 0, 1, 0, -1};
    public static int[][] colorGrid(int n, int m, int[][] sources) {
        int[][] ans = new int[n][m];
        r = 0;
        l = 0;
        Arrays.sort(sources, (a, b) -> b[2] - a[2]);
        for (int[] arr : sources) {
            ans[arr[0]][arr[1]] = arr[2];
            que[r++] = arr;
        }
        while (r - l > 0) {
            int size = r - l;
            for (int i = 0, color, x, y; i < size; i++) {
                x = que[l][0];
                y = que[l][1];
                color = que[l][2];
                l++;
                for (int j = 0; j < 4; j++) {
                    int nx = x + move[j];
                    int ny = y + move[j + 1];
                    if (nx >= 0 && nx < n && ny >= 0 && ny < m && ans[nx][ny] == 0) {
                        ans[nx][ny] = color;
                        que[r++] = new int[] {nx, ny, color};
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 3, m = 3;
        int[][] arr = {
            {0,0,1},
            {2,2,2}
        };
        System.out.println(Arrays.deepToString(colorGrid(n, m, arr)));
    }
}
