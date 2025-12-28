package algorithm.class92greed04;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/27 11:21
 * 给你两个正整数数组 nums 和 target ，两个数组长度相等。
 *
 * 在一次操作中，你可以选择两个 不同 的下标 i 和 j ，其中 0 <= i, j < nums.length ，并且：
 *
 * 令 nums[i] = nums[i] + 2 且
 * 令 nums[j] = nums[j] - 2 。
 * 如果两个数组中每个元素出现的频率相等，我们称两个数组是 相似 的。
 *
 * 请你返回将 nums 变得与 target 相似的最少操作次数。测试数据保证 nums 一定能变得与 target 相似。
 * https://leetcode.cn/problems/minimum-number-of-operations-to-make-arrays-similar/description/
 */
public class KakeSimilar {
    public long makeSimilar1(int[] nums, int[] target) {
        Arrays.sort(nums);
        Arrays.sort(target);
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += Math.abs(nums[i] - target[i]);
        }
        return sum / 4;
    }

    public long makeSimilar(int[] nums, int[] target) {
        // nums 和 target 奇数在左，偶数在右，排序
        int n = nums.length;
        int oddSize = split(nums, n);
        split(target, n);
        Arrays.sort(nums, 0, oddSize);
        Arrays.sort(nums, oddSize, n);
        Arrays.sort(target, 0, oddSize);
        Arrays.sort(target, oddSize, n);
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.abs((long) nums[i] - target[i]);
        }
        return sum / 4;
    }

    private int split(int[] nums, int n) {
        int oddSize = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] % 2 != 0) {
                swap(nums, i, oddSize++);
            }
        }
        return oddSize;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
