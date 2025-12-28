package leetcode.study.leetcode401_600;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/31 16:15
 * https://leetcode.cn/problems/next-greater-element-ii/
 */
public class Leetcode503NextGreaterElements {
    public static int MAXN = 20002;
    public static int r;
    public static int[] stack = new int[MAXN];
    public static int[] nextGreaterElements(int[] nums) {
        r = 0;
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        for (int i = 0, idx; i < 2 * n; i++) {
            idx = i % n;
            while (r > 0 && nums[stack[r - 1]] < nums[idx]) {
                ans[stack[r - 1]] = nums[idx];
                r--;
            }
            stack[r++] = idx;
        }

        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = {1,2,3,4,3};
        int[] arr = {1,2,1};
        System.out.println(Arrays.toString(nextGreaterElements(arr)));
    }
}
