package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/16 19:59
 * https://leetcode.cn/problems/valid-sudoku/description/
 */
public class Leetcode036IsValidSudoku {
    public static boolean isValidSudoku(char[][] board) {
        int n = board.length;
        int m = board[0].length;
        for (int i = 0; i < n; i++) {
            int[] cnts = new int[9];
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                if (++cnts[board[i][j] - '1'] == 2) {
                    return false;
                }
            }
        }
        for (int j = 0; j < m; j++) {
            int[] cnts = new int[9];
            for (int i = 0; i < n; i++) {
                if (board[i][j] == '.') {
                    continue;
                }
                if (++cnts[board[i][j] - '1'] == 2) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                int[] cnts = new int[9];
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        if (board[k][l] == '.') {
                            continue;
                        }
                        if (++cnts[board[k][l] - '1'] == 2) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        char[][] s = new char[][]{
            {'.', '.', '.', '.', '5', '.', '.', '1', '.'},
            {'.', '4', '.', '3', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '3', '.', '.', '1'},
            {'8', '.', '.', '.', '.', '.', '.', '2', '.'},
            {'.', '.', '2', '.', '7', '.', '.', '.', '.'},
            {'.', '1', '5', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '2', '.', '.', '.'},
            {'.', '2', '.', '9', '.', '.', '.', '.', '.'},
            {'.', '.', '4', '.', '.', '.', '.', '.', '.'}};
        System.out.println(isValidSudoku(s));
    }
}
