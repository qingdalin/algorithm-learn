package leetcode.leetcodeweek.test2025.test476;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/16 10:35
 * https://leetcode.cn/contest/weekly-contest-476/problems/maximize-expression-of-three-elements/description/
 */
public class Code476_01 {
    public int maximizeExpressionOfThree(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return nums[n - 1] + nums[n - 2] - nums[0];
    }
}
