package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/16 20:27
 * https://leetcode.cn/problems/sudoku-solver/
 */
public class Leetcode037SolveSudoku01 {
    public static int MAXN = 10;
    public static int[] row = new int[MAXN];
    public static int[] col = new int[MAXN];
    public static int[][] block = new int[4][4];
    public static boolean flag = false;
    public static List<int[]> list = new ArrayList<>();
    public static void solveSudoku(char[][] board) {
        flag = false;
        list.clear();
        Arrays.fill(row, 0);
        Arrays.fill(col, 0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                block[i][j] = 0;
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    list.add(new int[] {i, j});
                } else {
                    int num = board[i][j] - '0';
                    set(i, j, num);
                }
            }
        }
        dfs(board, 0);
    }

    private static void set(int i, int j, int num) {
        int cur = 1 << (num - 1);
        row[i] ^= cur;
        col[j] ^= cur;
        block[i / 3][j / 3] ^= cur;
    }

    private static void dfs(char[][] board, int pos) {
        if (pos == list.size()) {
            flag = true;
            return;
        }
        int[] cur = list.get(pos);
        int i = cur[0], j = cur[1];
        int mark = ~(row[i] | col[j] | block[i / 3][j / 3]) & 0x1ff;
        for (; mark != 0 && !flag ; mark &= (mark - 1)) {
            int k = mark & -mark;
            int num = 0;
            for (; k != 0;) {
                k >>= 1;
                num++;
            }
            set(i, j, num);
            board[i][j] = (char) (num + '0');
            dfs(board, pos + 1);
            set(i, j, num );
        }
    }


}
