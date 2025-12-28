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
public class Leetcode037SolveSudoku {
    public static int MAXN = 10;
    public static boolean[][] row = new boolean[MAXN][MAXN];
    public static boolean[][] col = new boolean[MAXN][MAXN];
    public static boolean[][][] block = new boolean[4][4][MAXN];
    public static boolean flag = false;
    public static List<int[]> list = new ArrayList<>();
    public static void solveSudoku(char[][] board) {
        flag = false;
        list.clear();
        for (int i = 0; i < MAXN; i++) {
            Arrays.fill(row[i], false);
            Arrays.fill(col[i], false);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < MAXN; k++) {
                    block[i][j][k] = false;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    list.add(new int[] {i, j});
                } else {
                    int num = board[i][j] - '0';
//                    row[i][num] = col[j][num] = block[(i + 2) / 3][(j + 2) / 3][num] = true;
                    row[i][num] = col[j][num] = block[i / 3][j / 3][num] = true;
                }
            }
        }
        dfs(board, 0);
    }

    private static void dfs(char[][] board, int pos) {
        if (pos == list.size()) {
            flag = true;
            return;
        }
        int[] cur = list.get(pos);
        int i = cur[0], j = cur[1];
        for (int num = 1; num <= 9 && !flag; num++) {
            if (!row[i][num] && !col[j][num] && !block[i / 3][j / 3][num]) {
                row[i][num] = col[j][num] = block[i / 3][j / 3][num] = true;
                board[i][j] = (char) (num + '0');
                dfs(board, pos + 1);
                row[i][num] = col[j][num] = block[i / 3][j / 3][num] = false;
            }
        }
    }

    public static void solveSudoku1(char[][] board) {
        int[][] col = new int[9][9];
        int[][] row = new int[9][9];
        int[][] grid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                col[i][j] = 1;
                row[i][j] = 1;
                grid[i][j] = 1;
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                if (col[i][board[i][j] - '1'] == 1) {
                    col[i][board[i][j] - '1']--;
                }
            }
        }
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (board[i][j] == '.') {
                    continue;
                }
                if (row[i][board[i][j] - '1'] == 1) {
                    row[i][board[i][j] - '1']--;
                }
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        if (board[k][l] == '.') {
                            continue;
                        }
                        if (grid[k][board[k][l] - '1'] == 1) {
                            grid[k][board[k][l] - '1']--;
                        }
                    }
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        char[][] s = new char[][]{
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        solveSudoku(s);
        System.out.println();
    }
}
