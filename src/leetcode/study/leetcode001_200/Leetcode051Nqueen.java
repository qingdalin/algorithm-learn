package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/14 20:50
 * https://leetcode.cn/problems/n-queens/
 */
public class Leetcode051Nqueen {
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        int[] path = new int[n];
        f(0, path, n, ans);
        return ans;
    }

    private static void f(int i, int[] path, int n, List<List<String>> ans) {
        if (i == n) {
            List<String> curAns = new ArrayList<>();
            for (int k = 0; k < n; k++) {
                char[] curRow = new char[n];
                for (int j = 0; j < n; j++) {
                    curRow[j] = '.';
                }
                curRow[path[k]] = 'Q';
                curAns.add(String.valueOf(curRow));
            }
            ans.add(curAns);
        } else {
            for (int j = 0; j < n; j++) {
                if (check(i, j, path)) {
                    path[i] = j;
                    f(i + 1, path, n, ans);
                }
            }
        }
    }

    private static boolean check(int i, int j, int[] path) {
        for (int k = 0; k < i; k++) {
            if (j == path[k] || Math.abs(i - k) == Math.abs(j - path[k])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println(solveNQueens(n));
    }
}
