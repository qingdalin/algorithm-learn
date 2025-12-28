package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 13:59
 * https://leetcode.cn/problems/third-maximum-number/
 */
public class Leetcode414ThirdMax {
    public int thirdMax(int[] nums) {
        long one = Long.MIN_VALUE;
        long two = Long.MIN_VALUE;
        long three = Long.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > one) {
                three = two;
                two = one;
                one = nums[i];
            } else if (nums[i] > two && nums[i] != one) {
                three = two;
                two = nums[i];
            } else if (nums[i] > three && nums[i] != two && nums[i] != one) {
                three = nums[i];
            }
        }
        return (int) (three == Long.MIN_VALUE ? one : three);
    }
}
