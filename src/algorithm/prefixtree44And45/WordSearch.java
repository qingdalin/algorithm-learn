package algorithm.prefixtree44And45;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.cn/problems/word-search-ii/
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-28 20:14
 */
public class WordSearch {
    public static int MAX = 150001;
    public static int[][] tree = new int[MAX][26];
    public static int[] pass = new int[MAX];
    public static String[] end = new String[MAX];
    public static int cnt;
    public List<String> findWords(char[][] board, String[] words) {
        build(words);
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, 1, ans);
            }
        }
        clear();
        return ans;
    }

    // i  行
    // j  列
    // t  节点
    // 返回值代表收集到了几个答案
    private int dfs(char[][] board, int i, int j, int t, List<String> ans) {
        if (i < 0 || i == board.length || j < 0 || j < board[0].length || board[i][j] == 0) {
            return 0;
        }
        char tmp = board[i][j];
        int road = tmp - 'a';
        t = tree[t][road];
        if (pass[t] == 0) {
            return 0;
        }
        int fix = 0; // 一共收集的答案
        if (end[t] != null) {
            fix++;
            ans.add(end[t]);
            end[t] = null;
        }
        board[i][j] = 0;
        fix += dfs(board, i - 1, j, t, ans);
        fix += dfs(board, i + 1, j, t, ans);
        fix += dfs(board, i, j - 1, t, ans);
        fix += dfs(board, i, j + 1, t, ans);
        pass[t] -= fix;
        board[i][j] = tmp;
        return fix;
    }

    private void clear() {
        for (int i = 1; i <= cnt; i++) {
            Arrays.fill(tree[i], 0);
            pass[i] = 0;
            end[i] = null;
        }
    }

    private void build(String[] words) {
        cnt = 1;
        for (String word : words) {
            int cur = 1;
            pass[cur]++;
            for (int i = 0, path; i < word.length(); i++) {
                path = word.charAt(i) - 'a';
                if (tree[cur][path] == 0) {
                    tree[cur][path] = ++cnt;
                }
                cur = tree[cur][path];
                pass[cur]++;
            }
            end[cur] = word;
        }
    }
}
