package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/28 19:08
 * https://leetcode.cn/problems/largest-divisible-subset/
 */
public class Leetcode368LargestDivisibleSubset {
    public static List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int len = nums.length;
        Arrays.sort(nums);
        int[] dp = new int[len];
        Arrays.fill(dp, 1);
        int maxSize = 1;
        int maxVal = dp[0];
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] > maxSize) {
                maxSize = dp[i];
                maxVal = nums[i];
            }
        }
        if (maxSize == 1) {
            res.add(nums[0]);
            return res;
        }
        for(int i = len - 1; i >= 0 && maxSize > 0; i--) {
            if (dp[i] == maxSize && maxVal % nums[i] == 0) {
                res.add(nums[i]);
                maxVal = nums[i];
                maxSize--;
            }
        }
        return res;
    }

    public static List<Integer> largestDivisibleSubset1(int[] arr) {
        List<Integer> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(arr);
        f(0, arr, path, ans);
        return ans;
    }

    private static void f(int i, int[] arr, List<Integer> path, List<Integer> ans) {
        if (i >= arr.length) {
            if (path.size() > ans.size()) {
                ans.clear();
                for (int j = 0; j < path.size(); j++) {
                    ans.add(path.get(j));
                }
            }
            return;
        }
        f(i + 1, arr, path, ans);
        if (!ans.isEmpty() && arr[i] % ans.get(ans.size() - 1) == 0) {
            ans.add(arr[i]);
            f(i + 1, arr, path, ans);
            ans.remove(ans.size() - 1);
        }
    }

    public static void main(String[] args) {
        //int[] arr = new int[] {1, 2, 3};
        int[] arr = new int[] {5,9,18,54,108,540,90,180,360,720};
        System.out.println(largestDivisibleSubset(arr));
    }
}
