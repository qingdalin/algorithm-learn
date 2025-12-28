package algorithm.class126;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/27 11:08
 * // 用三种不同颜色为网格涂色
 * // 给你两个整数m和n，表示m*n的网格，其中每个单元格最开始是白色
 * // 请你用红、绿、蓝三种颜色为每个单元格涂色，所有单元格都需要被涂色
 * // 要求相邻单元格的颜色一定要不同
 * // 返回网格涂色的方法数，答案对 1000000007 取模
 * // 1 <= m <= 5
 * // 1 <= n <= 1000
 * // 测试链接 : https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/
 * // 有兴趣的同学可以自己改一下空间压缩的版本
 */
public class Code02_GridPainting {
    public static int MAXN = 1000;
    public static int MAXM = 5;
    public static int MOD = 1000000007;
    public static int MAXS = (int) Math.pow(3, MAXM);
    public static int n, m, maxs, size;
    public static int[][][] dp = new int[MAXN][MAXM][MAXS];
    // 第一行的状态，所有可能性
    public static int[] first = new int[MAXS];
    public static int colorTheGrid(int row, int col) {
        build(row, col);
        int ans = 0;
        for (int i = 0; i < size; i++) {
            ans = (ans + f(1, 0, first[i], 1)) % MOD;
        }
        return ans;
    }

    private static int f(int i, int j, int s, int bit) {
        if (i == n) {
            return 1;
        }
        if (j == m) {
            return f(i + 1, 0, s, 1);
        }
        if (dp[i][j][s] != -1) {
            return dp[i][j][s];
        }
        int ans = 0;
        int left = j == 0 ? -1 : get(s, bit / 3);
        int up = get(s, bit);
        if (left != 0 && up != 0) {
            ans = (ans + f(i, j + 1, set(s, bit, 0), bit * 3)) % MOD;
        }
        if (left != 1 && up != 1) {
            ans = (ans + f(i, j + 1, set(s, bit, 1), bit * 3)) % MOD;
        }
        if (left != 2 && up != 2) {
            ans = (ans + f(i, j + 1, set(s, bit, 2), bit * 3)) % MOD;
        }
        dp[i][j][s] = ans;
        return ans;
    }

    private static void build(int row, int col) {
        n = Math.max(row, col);
        m = Math.min(row, col);
        maxs = (int) Math.pow(3, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int s = 0; s < maxs; s++) {
                    dp[i][j][s] = -1;
                }
            }
        }
        size = 0;
        dfs(0, 0, 1);
    }

    private static void dfs(int j, int s, int bit) {
        if (j == m) {
            first[size++] = s;
        } else {
            int left = j == 0 ? -1 : get(s, bit / 3);
            if (left != 0) {
                dfs(j + 1, set(s, bit, 0), bit * 3);
            }
            if (left != 1) {
                dfs(j + 1, set(s, bit, 1), bit * 3);
            }
            if (left != 2) {
                dfs(j + 1, set(s, bit, 2), bit * 3);
            }
        }
    }

    private static int set(int s, int bit, int v) {
        return s - get(s, bit) * bit + v * bit;
    }

    private static int get(int s, int bit) {
        return s / bit % 3;
    }
}
