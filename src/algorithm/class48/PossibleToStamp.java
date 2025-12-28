package algorithm.class48;

/**
 * 用邮票贴满网格，二维差分
 * https://leetcode.cn/problems/stamping-the-grid/
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-02-03 17:21
 */
public class PossibleToStamp {
    //public static int[][] sum = new int[][];
    public boolean possibleToStamp(int[][] grid, int h, int w) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] sum = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sum[i + 1][j + 1] = grid[i][j];
            }
        }
        int[][] diff = new int[n + 2][m + 2];
        build(sum, n, m);
        for (int a = 1, c = a + h - 1; c <= n; a++, c++) {
            for (int b = 1, d = b + w - 1; d <= m; b++, d++) {
                if (sum(sum, a, b, c, d) == 0) {
                    add(diff, a, b, c, d);
                }
            }
        }
        build(diff, n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0 && diff[i + 1][j + 1] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void add(int[][] diff, int a, int b, int c, int d) {
        diff[a][b] += 1;
        diff[c + 1][b] -= 1;
        diff[a][d + 1] -= 1;
        diff[c + 1][d + 1] += 1;
    }

    private int sum(int[][] sum, int a, int b, int c, int d) {
        return sum[c][d] - sum[c][b - 1] - sum[a - 1][d] + sum[a - 1][b - 1];
    }

    private void build(int[][] sum, int n, int m) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum[i][j] += sum[i][j - 1] + sum[i - 1][j] - sum[i - 1][j -1];
            }
        }
    }
}
