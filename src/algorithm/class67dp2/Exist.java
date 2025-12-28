package algorithm.class67dp2;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-18 15:25
 * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
 *
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 * https://leetcode.cn/problems/word-search/description/
 */
public class Exist {
    public boolean exist(char[][] board, String word) {
        char[] w = word.toCharArray();
        int n = board.length;
        int m = board[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (f(board, i, j, w, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean f(char[][] board, int i, int j, char[] w, int k) {
        // 当前位置 board[i][j]来到w[k]，请问后续能不能走出后续w[k...]
        if (k == w.length) {
            return true;
        }
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != w[k]) {
            return false;
        }
        char tmp = board[i][j];
        board[i][j] = 0;
        boolean ans = f(board, i - 1, j, w, k + 1) ||
            f(board, i + 1, j, w, k + 1) ||
            f(board, i, j - 1, w, k + 1) ||
            f(board, i, j + 1, w, k + 1) ;
        board[i][j] = tmp;
        return ans;
    }
}
