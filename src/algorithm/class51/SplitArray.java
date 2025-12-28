package algorithm.class51;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-03 17:00
 * https://leetcode.cn/problems/split-array-largest-sum/
 * 分割数组最大值，画匠问题
 */
public class SplitArray {
    public int splitArray(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int ans = 0;
        for (int l = 0, r = sum, m, need; l <= r;) {
            m = (l + r) / 2;
            need = f(nums, m);
            if (need <= k) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }
    public int f(int[] nums, int limit) {
        int parts = 1;
        int sum = 0;
        for (int num : nums) {
            if (num > limit) {
                return Integer.MAX_VALUE;
            }
            if (sum + num > limit) {
                parts++;
                sum = num;
            } else {
                sum += num;
            }
        }
        return parts;
    }
}
