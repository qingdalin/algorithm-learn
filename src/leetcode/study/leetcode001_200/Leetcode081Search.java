package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/7 16:10
 * https://leetcode.cn/problems/search-in-rotated-sorted-array-ii/
 */
public class Leetcode081Search {
    public boolean search(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == target) {
                return true;
            }
        }
        return false;
    }
}
