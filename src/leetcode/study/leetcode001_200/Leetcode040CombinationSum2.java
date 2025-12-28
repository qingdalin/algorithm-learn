package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/23 19:27
 * https://leetcode.cn/problems/combination-sum-ii/
 */
public class Leetcode040CombinationSum2 {
//    public static List<List<Integer>> combinationSum2(int[] arr, int target) {
//        //Arrays.sort(arr);
//        int n = arr.length;
//        int[] cnt = new int[41];
//        int[] sorted = new int[n];
//        int len = 0;
//        for (int i = 0; i < n; i++) {
//            if (++cnt[arr[i]] * arr[i] > target) {
//                continue;
//            }
//            sorted[len++] = arr[i];
//        }
//
////        for (int i = 1; i < n; i++) {
////            if (arr[i] != arr[len]) {
////                sorted[len++] = arr[i];
////            }
////        }
//        Set<List<Integer>> ans = new HashSet<>();
//        List<Integer> path = new ArrayList<>();
//        f(sorted, len, 0, 0, path, ans, target);
//        return new ArrayList<>(ans);
//    }
//
//    private static void f(int[] arr, int n, int i, int sum, List<Integer> path, Set<List<Integer>> ans, int target) {
//        if (sum > target) {
//            return;
//        }
//        if (i == n) {
//            if (sum == target) {
//                ArrayList<Integer> tmp = new ArrayList<>(path);
//                tmp.sort((a, b) -> a - b);
//                ans.add(tmp);
//            }
//        } else {
//            path.add(arr[i]);
//            f(arr, n, i + 1, sum + arr[i], path, ans, target);
//            path.remove(path.size() - 1);
//            f(arr, n, i + 1, sum, path, ans, target);
//        }
//
//    }

    public static List<List<Integer>> combinationSum2(int[] arr, int target) {
        //Arrays.sort(arr);
        int n = arr.length;
        Set<List<Integer>> ans = new HashSet<>();
        List<Integer> path = new ArrayList<>();
        f(arr, n, 0, 0, path, ans, target);
        return new ArrayList<>(ans);
    }

    private static void f(int[] arr, int n, int i, int sum, List<Integer> path, Set<List<Integer>> ans, int target) {
        if (sum == target) {
            ArrayList<Integer> tmp = new ArrayList<>(path);
            tmp.sort((a, b) -> a - b);
            ans.add(tmp);
            return;
        }
        if (sum > target) {
            return;
        }
        if (i == n) {
            return;
        }
        path.add(arr[i]);
        f(arr, n, i + 1, sum + arr[i], path, ans, target);
        path.remove(path.size() - 1);
        while (i < n - 1 && arr[i] == arr[i + 1]) {
            i++;
        }
        f(arr, n, i + 1, sum, path, ans, target);
    }

    public static void main(String[] args) {
        //int[] arr = new int[] {2,3,6,7};
//        int[] arr = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
//        int[] arr = new int[] {10,1,2,7,6,1,5};
        int[] arr = new int[] {2,5,2,1,2};
//        int[] arr = new int[] {3,1,3,5,1,1};
        int t = 5;
        System.out.println(combinationSum2(arr, t));
    }
}
