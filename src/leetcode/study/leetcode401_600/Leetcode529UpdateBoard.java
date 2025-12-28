package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 18:55
 * https://leetcode.cn/problems/minesweeper/
 */
public class Leetcode529UpdateBoard {
    public static int[][] move = {
        {-1, -1},
        {-1, 0},
        {-1, 1},
        {0, -1},
        {0, 1},
        {1, -1},
        {1, 0},
        {1, 1}
    };
    public static char[][] updateBoard(char[][] board, int[] click) {
        int i = click[0], j = click[1];
        int n = board.length, m = board[0].length;
        boolean[][] vis = new boolean[n][m];
        f(board, i, j, n, m, vis);
        return board;
    }

    private static void f(char[][] board, int i, int j, int n, int m, boolean[][] vis) {
        if (i < 0 || i == n || j < 0 || j == m || vis[i][j]) {
            return;
        }
        vis[i][j] = true;
        if (board[i][j] == 'M') {
            board[i][j] = 'X';
            return;
        }
        int cnt = 0;
        // '1' - '0' = 1
        for (int k = 0, x, y; k < 8; k++) {
            x = i + move[k][0];
            y = j + move[k][1];
            if (x < 0 || x == n || y < 0 || y == m || Character.isDigit(board[x][y])) {
                continue;
            }
            if (board[x][y] == 'M') {
                cnt++;
            }
        }
        if (cnt == 0) {
            board[i][j] = 'B';
            for (int k = 0, x, y; k < 8; k++) {
                x = i + move[k][0];
                y = j + move[k][1];
                if (x < 0 || x == n || y < 0 || y == m || Character.isDigit(board[x][y])) {
                    continue;
                }
                if (board[x][y] == 'M') {
                    cnt++;
                }
                f(board, x, y, n, m, vis);
            }
        } else {
            board[i][j] = (char) (cnt + '0');
        }
    }

    public static void main(String[] args) {
        int cnt = 1;
        char x = (char) (cnt + '0');
        System.out.println(x);
    }
}
