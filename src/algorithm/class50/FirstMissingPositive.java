package algorithm.class50;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-03 11:56
 * https://leetcode.cn/problems/first-missing-positive/
 * 寻找缺失的第一个正数
 */
public class FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            if (nums[l] == l + 1) {
                l++;
            } else if (nums[l] <= l || nums[l] > r || nums[nums[l] - 1] == nums[l]) {
                swap(nums, l, -- r);
            } else {
                swap(nums, l, nums[l] - 1);
            }
        }
        return l + 1;
    }

    public void swap(int[] arr, int i, int j) {
        int tem = arr[i];
        arr[i] = arr[j];
        arr[j] = tem;
    }
}
