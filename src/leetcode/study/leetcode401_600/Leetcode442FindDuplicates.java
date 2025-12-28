package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 14:45
 * https://leetcode.cn/problems/find-all-duplicates-in-an-array/
 */
public class Leetcode442FindDuplicates {
    public static List<Integer> findDuplicates(int[] nums) {
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();
        // 0 1 2 3 4 5 6 7
        // 4,3,2,7,8,2,3,1
        // 7,3,2,4,8,2,3,1
        // 3,3,2,4,8,2,7,1
        // 2,3,3,4,8,2,7,1
        // 3,2,3,4,8,2,7,1
        // 3,2,3,4,1,2,7,8
        // 1,2,3,4,3,2,7,8
        for (int i = 0; i < n; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                swap(i, nums[i] - 1, nums);
            }
        }
        for (int i = 0; i < n; i++) {
            if (i != nums[i] - 1) {
                ans.add(nums[i]);
            }
        }
        return ans;
    }

    private static void swap(int i, int j, int[] nums) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static List<Integer> findDuplicates1(int[] nums) {
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();
        int[] cnts = new int[n + 1];
        for (int num : nums) {
            if (++cnts[num] == 2) {
                ans.add(num);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {4,3,2,7,8,2,3,1};
        System.out.println(findDuplicates(arr));
    }
}
