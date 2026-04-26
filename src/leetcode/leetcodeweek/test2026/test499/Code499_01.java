package leetcode.leetcodeweek.test2026.test499;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/26 9:16
 * https://leetcode.cn/contest/weekly-contest-499/problems/valid-elements-in-an-array/description/
 */
public class Code499_01 {
    public static List<Integer> findValidElements(int[] nums) {
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();
        ans.add(nums[0]);
        int[] sufMax = new int[n];
        sufMax[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            sufMax[i] = Math.max(sufMax[i + 1], nums[i]);
        }
        int preMax = nums[0];
        for (int i = 1; i < n; i++) {
            if (i == n - 1) {
                ans.add(nums[n - 1]);
            } else {
                if (nums[i] > preMax || nums[i] > sufMax[i + 1]) {
                    ans.add(nums[i]);
                }
                preMax = Math.max(preMax, nums[i]);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {1};
        System.out.println(findValidElements(arr));
    }
}
