package algorithm.class50;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-02-28 19:48
 * https://leetcode.cn/problems/trapping-rain-water/description/
 * 接雨水
 */
public class TrappingRainWater {
    public int trap1(int[] height) {
        int n = height.length;
        int[] lmax = new int[n];
        int[] rmax = new int[n];
        lmax[0] = height[0];
        for (int i = 1; i < n; i++) {
            lmax[i] = Math.max(lmax[i - 1], height[i]);
        }
        rmax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rmax[i] = Math.max(rmax[i + 1], height[i]);
        }
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            ans += Math.max(0, Math.min(lmax[i - 1], rmax[i + 1]) - height[i]);
        }
        return ans;
    }

    public int trap(int[] height) {
        int l = 1, r = height.length - 2, lmax = height[0], rmax = height[height.length - 1];
        int ans = 0;
        while (l <= r) {
            if (lmax <= rmax) {
                ans += Math.max(0, lmax - height[l]);
                lmax = Math.max(lmax, height[l++]);
            } else {
                ans += Math.max(0, rmax - height[r]);
                rmax = Math.max(rmax, height[r--]);
            }
        }
        return ans;
    }
}
