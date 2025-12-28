package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/7 14:21
 * https://leetcode.cn/problems/combinations/
 */
public class Leetcode077Combine {
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        f(n, k, ans, 1, path);
        return ans;
    }

    private static void f(int n, int k, List<List<Integer>> ans, int i, List<Integer> path) {
        if (path.size() == k) {
            List<Integer> res = new ArrayList<>(path);
            ans.add(res);
            return;
        }
        if (i <= n) {
            f(n, k, ans, i + 1, path);
            path.add(i);
            f(n, k, ans, i + 1, path);
            path.remove(path.size() - 1);
        }
    }

    private static List<Integer> copy(List<Integer> path) {
        return new ArrayList<>(path);
    }

    public static void main(String[] args) {
        int n = 4;
        int k = 2;
        System.out.println(combine(n, k));
    }
}
