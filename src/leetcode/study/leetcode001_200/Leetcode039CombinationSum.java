package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/23 19:05
 * https://leetcode.cn/problems/combination-sum/
 */
public class Leetcode039CombinationSum {
    public static List<List<Integer>> combinationSum(int[] arr, int target) {
        int n = arr.length;
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        f(arr, n, 0, 0, path, ans, target);
        return ans;
    }

    private static void f(int[] arr, int n, int i, int sum, List<Integer> path, List<List<Integer>> ans, int target) {
        if (i == n) {
            if (sum == target) {
                ans.add(new ArrayList<>(path));
            }
        } else {
            if (sum <= target) {
                path.add(arr[i]);
                f(arr, n, i, sum + arr[i], path, ans, target);
                path.remove(path.size() - 1);
                f(arr, n, i + 1, sum, path, ans, target);
            }
        }
    }

    public static void main(String[] args) {
        //int[] arr = new int[] {2,3,6,7};
        int[] arr = new int[] {2,3,5};
        int t = 8;
        System.out.println(combinationSum(arr, t));
    }
}
