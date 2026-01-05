package leetcode.study.everydayleetcode.every2025;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/30 19:44
 * https://leetcode.cn/problems/magic-squares-in-grid/description/?envType=daily-question&envId=2025-12-30
 */
public class Code_20251230NumMagicSquaresInside {
    public int numMagicSquaresInside(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        if (n * m < 9) {
            return 0;
        }
        int cnt = 0;
        for (int i = 0; i < n - 2; i++) {
            for (int j = 0; j < m - 2; j++) {
                if (check(i, j, grid)) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private boolean check(int i, int j, int[][] grid) {
        if (grid[i + 1][j + 1] != 5) {
            return false;
        }
        boolean[] vis = new boolean[16];
        for (int k = i; k <= i + 2; k++) {
            for (int l = j; l <= j + 2; l++) {
                int num = grid[k][l];
                if (num > 9 || num < 1 || vis[num]) {
                    return false;
                }
                vis[num] = true;
            }
        }
        // 先校验行
        int sum = 0;
        for (int k = j; k <= j + 2; k++) {
            sum += grid[i][k];
        }
        // 校验行的和是否相等
        for (int k = i, curSum; k <= i + 2; k++) {
            curSum = 0;
            for (int l = j; l <= j + 2; l++) {
                curSum += grid[k][l];
            }
            if (curSum != sum) {
                return false;
            }
        }
        // 校验列的和是否相等
        for (int k = j, curSum; k <= j + 2; k++) {
            curSum = 0;
            for (int l = i; l <= i + 2; l++) {
                curSum += grid[l][k];
            }
            if (curSum != sum) {
                return false;
            }
        }
        // 校验对角线(不校验也能过)
//        int curSum = 0;
//        for (int k = i, l = j, m = 0; m < 3; k++, l++, m++) {
//            curSum += grid[k][l];
//        }
//        if (curSum != sum) {
//            return false;
//        }
//        curSum = 0;
//        for (int k = i + 2, l = j, m = 0; m < 3; k--, l++, m++) {
//            curSum += grid[k][l];
//        }
//        if (curSum != sum) {
//            return false;
//        }
        return true;
    }
}
