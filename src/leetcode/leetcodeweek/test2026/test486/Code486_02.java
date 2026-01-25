package leetcode.leetcodeweek.test2026.test486;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/25 9:40
 * https://leetcode.cn/contest/weekly-contest-486/problems/rotate-non-negative-elements/
 */
public class Code486_02 {
    public static int[] rotateElements(int[] nums, int k) {
        int n = nums.length;
        int[] arr = new int[n];
        int len = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] >= 0) {
                arr[len++] = nums[i];
            }
        }
        int[] tmpArr = new int[len];
        for (int i = 0; i < len; i++) {
            tmpArr[(i - k + len * ((k + len - 1) / len)) % len] = arr[i];
        }
        for (int i = 0, j = 0; i < n; i++) {
            if (nums[i] >= 0) {
                nums[i] = tmpArr[j++];
            }
        }
        return nums;
    }

    public static void main(String[] args) {
//        int[] arr = {1,-2,3,-4};
//        int[] arr = {5,4,-9,6};
        int[] arr = {-7,0};
        int k = 52739;
        System.out.println(Arrays.toString(rotateElements(arr, k)));
    }
}
