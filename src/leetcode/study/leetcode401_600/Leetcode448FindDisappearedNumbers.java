package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 16:58
 * https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/
 */
public class Leetcode448FindDisappearedNumbers {
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                swap(i, nums[i] - 1, nums);
            }
        }
        for (int i = 0; i < n; i++) {
            if (i != nums[i] - 1) {
                ans.add(i + 1);
            }
        }
        return ans;
    }

    private static void swap(int i, int j, int[] nums) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    public static List<Integer> findDisappearedNumbers1(int[] nums) {
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();
        boolean[] vis = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            vis[nums[i]] = true;
        }
        for (int i = 1; i <= n; i++) {
            if (!vis[i]) {
                ans.add(i);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {4,3,2,7,8,2,3,1};
        System.out.println(findDisappearedNumbers(arr));
    }
}
