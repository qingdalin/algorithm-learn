package leetcode.study.leetcode001_200;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/14 20:14
 * https://leetcode.cn/problems/next-permutation/
 */
public class Leetcode031NextPermutation {
    public static void nextPermutation1(int[] nums) {
        int n = nums.length;
        boolean flag = false;
        int idx = -1;
        int min = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            int j = i + 1;
            idx = -1;
            for (; j < n; j++) {
                if (nums[i] < nums[j]) {
                    if (idx == -1) {
                        idx = j;
                        min = nums[j];
                    } else if (nums[j] < nums[idx]) {
                        idx = j;
                        min = nums[j];
                    }
                }
                if (idx != -1) {
                    flag = true;
                    int tmp = nums[i];
                    nums[i] = nums[idx];
                    nums[idx] = tmp;
                }
            }

        }
        if (!flag) {
            int tmp = nums[0];
            nums[0] = nums[n - 1];
            nums[n - 1] = tmp;
        }
    }

    public static void nextPermutation(int[] nums) {
        int n = nums.length;
        boolean flag = false;
        int i = n - 2;
        int j = i + 1;
        int idx = -1;
        for (; i >= 0; i--) {
            j = i + 1;
            for (; j < n; j++) {
                if (nums[i] < nums[j]) {
                    if (idx == -1) {
                        idx = j;
                    } else if (nums[j] < nums[idx]) {
                        idx = j;
                    }
                }
            }
            if (idx != -1) {
                flag = true;
                int tmp = nums[i];
                nums[i] = nums[idx];
                nums[idx] = tmp;
                break;
            }
        }
        if (!flag) {
            Arrays.sort(nums);
        } else {
            Arrays.sort(nums, i + 1, j);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1,3,2};
        nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }
}
