package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 17:01
 * https://leetcode.cn/problems/rotate-function/
 */
public class Leetcode396MaxRotateFunction {
    public static int maxRotateFunction(int[] nums) {
        int n = nums.length;
        int sum = 0;
        int cur = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            cur += i * nums[i];
        }
        int ans = cur;
        for(int i = n - 1; i > 0; i--) {
            cur = cur - (n - 1) * nums[i] + sum - nums[i];
            ans = Math.max(cur, ans);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {4,3,2,6};
        System.out.println(maxRotateFunction(arr));
    }
}
