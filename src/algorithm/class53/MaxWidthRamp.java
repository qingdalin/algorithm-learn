package algorithm.class53;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-10 10:32
 * https://leetcode.cn/problems/maximum-width-ramp/
 * 最大宽度的坡，单调栈
 */
public class MaxWidthRamp {
    public static int r;
    public static int[] stack = new int[50000];
    public int maxWidthRamp(int[] nums) {
        r = 1;
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[stack[r - 1]] > nums[i]) {
                stack[r++] = i;
            }
        }
        int ans = 0;
        for (int j = n - 1; j >= 0; j--) {
            while (r > 0 && nums[stack[r - 1]] <= nums[j]) {
                ans = Math.max(ans, j - stack[--r]);
            }
        }
        return ans;
    }
}
