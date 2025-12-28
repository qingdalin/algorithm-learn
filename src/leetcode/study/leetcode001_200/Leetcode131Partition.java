package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/23 20:09
 */
public class Leetcode131Partition {
    public static int MAXN = 17;
    public static boolean[][] visited = new boolean[MAXN][MAXN];
    public static int n;
    public static List<List<String>> ans = new ArrayList<>();
    public static List<String> path = new ArrayList<>();
    public static List<List<String>> partition(String str) {
        ans.clear();
        path.clear();
        char[] s = str.toCharArray();
        n = s.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                visited[i][j] = true;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                visited[i][j] = (s[i] == s[j]) && visited[i + 1][j - 1];
            }
        }
        dfs(s, 0);
        return ans;
    }

    private static void dfs(char[] s, int i) {
        if (i == s.length) {
            ans.add(new ArrayList<>(path));
        } else {
            for (int j = i; j < n; j++) {
                if (visited[i][j]) {
                    path.add(String.valueOf(s, i, j - i + 1));
                    dfs(s, j + 1);
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    private static boolean check(List<Character> path) {
        if (path.isEmpty()) {
            return false;
        }
        int n = path.size();
        for (int l = 0, r = n - 1; l <= r; l++, r--) {
            if (path.get(l) != path.get(r)) {
                return false;
            }
        }
        return true;
    }

    private static List<String> copy(List<Character> path) {
        List<String> ans = new ArrayList<>();
        for (Character c : path) {
            ans.add(String.valueOf(c));
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "aab";
        System.out.println(partition(s));
    }
}
