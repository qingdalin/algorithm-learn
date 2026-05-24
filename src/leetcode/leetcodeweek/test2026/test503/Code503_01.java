package leetcode.leetcodeweek.test2026.test503;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/24 8:53
 * https://leetcode.cn/contest/weekly-contest-503/problems/limit-occurrences-in-sorted-array/description/
 */
public class Code503_01 {
    public static int[] limitOccurrences(int[] nums, int k) {
        int cnt = 0, j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || nums[i] == nums[i - 1]) {
                cnt++;
            } else {
                cnt = 1;
            }
            if (cnt <= k) {
                nums[j++] = nums[i];
            }
        }
        return Arrays.copyOf(nums, j);
    }

    public static int[] limitOccurrences1(int[] nums, int k) {
        int n = nums.length;
        int[] tmp = new int[n];
        int idx = 0;
        Map<Integer, Integer> cnt = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            cnt.merge(nums[i], 1, Integer::sum);
        }
        for (Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
            int i = entry.getKey();
            int val = entry.getValue();
            for (int j = 0; j < Math.min(k, val); j++) {
                tmp[idx++] = i;
            }
        }
        int[] ans = new int[idx];
        for (int i = 0; i < idx; i++) {
            ans[i] = tmp[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {1,1,1,2,2,3};
        int k = 2;
        System.out.println(Arrays.toString(limitOccurrences(arr, k)));
    }
}
