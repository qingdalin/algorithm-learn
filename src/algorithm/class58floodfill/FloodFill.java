package algorithm.class58floodfill;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-18 20:16
 */
public class FloodFill {

    public static void main(String[] args) {
        char[][] board = {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        solve(board);
    }
    public static void solve(char[][] board) {
        // 从边界的O感染
        int n = board.length;
        int m = board[0].length;
        for (int j = 0; j < m; j++) {
            if (board[0][j] == 'O') {
                dfs(board, n, m, 0, j);
            }
            if (board[n - 1][j] == 'O') {
                dfs(board, n, m, n - 1, j);
            }
        }
        for (int i = 1; i < n - 1; i++) {
            if (board[i][0] == 'O') {
                dfs(board, n, m, i, 0);
            }
            if (board[i][m - 1] == 'O') {
                dfs(board, n, m, i, m - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == 'F') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public static void dfs(char[][] board, int n, int m, int i, int j) {
        if (i < 0 || i == n || j < 0 || j == m || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'F';
        dfs(board, n, m, i + 1, j);
        dfs(board, n, m, i - 1, j);
        dfs(board, n, m, i, j + 1);
        dfs(board, n, m, i, j - 1);
    }
}
