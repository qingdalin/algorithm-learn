package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 16:16
 * https://leetcode.cn/problems/non-decreasing-subsequences/
 */
public class Leetcode491FindSubsequences {
    public static List<List<Integer>> findSubsequences(int[] arr) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        f(0, arr, Integer.MIN_VALUE, path, ans);
        return ans;
    }

    private static void f(int i, int[] arr, int pre, List<Integer> path, List<List<Integer>> ans) {
        if (arr.length == i) {
            if (path.size() > 1) {
                ans.add(new ArrayList<>(path));
            }
        } else {
            if (arr[i] >= pre) {
                path.add(arr[i]);
                f(i + 1, arr, arr[i], path, ans);
                path.remove(path.size() - 1);
            }
            if (arr[i] != pre) {
                f(i + 1, arr, pre, path, ans);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4,4,3,2,1};
        System.out.println(findSubsequences(arr));
    }
}
