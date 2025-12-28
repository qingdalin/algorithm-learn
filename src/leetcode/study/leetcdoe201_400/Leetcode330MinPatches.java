package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 16:23
 * https://leetcode.cn/problems/patching-array/
 */
public class Leetcode330MinPatches {
    public int minPatches(int[] nums, int n) {
        // 8 4 2 1
        // 0 0 1 1
        // 0 0 0 1
        int ans = 0, i = 0;
        long s = 1;
        while (s <= n) {
            if (i < nums.length && nums[i] <= s) {
                s += nums[i++];
            } else {
                s *= 2;
                ans++;
            }
        }
        return ans;
    }
}
