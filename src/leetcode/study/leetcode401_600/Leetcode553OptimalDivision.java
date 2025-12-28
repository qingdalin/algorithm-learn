package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 15:02
 * https://leetcode.cn/problems/optimal-division/
 */
public class Leetcode553OptimalDivision {
    public String optimalDivision(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return String.valueOf(nums[0]);
        }
        if (n == 2) {
            return nums[0] + "/" + nums[1];
        }
        StringBuilder sb = new StringBuilder();
        sb.append(nums[0]).append("/(").append(nums[1]);
        for (int i = 2; i < n; i++) {
            sb.append("/");
            sb.append(nums[i]);
        }
        sb.append(")");
        return sb.toString();
    }
}
