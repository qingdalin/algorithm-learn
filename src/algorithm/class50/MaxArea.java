package algorithm.class50;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-01 20:53
 * https://leetcode.cn/problems/container-with-most-water/
 */
public class MaxArea {
    public int maxArea(int[] height) {
        int ans = 0;
        for (int l = 0, r = height.length - 1; l < r;) {
            ans = Math.max(ans, Math.min(height[l], height[r]) * (r - l));
            if (height[l] <= height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return ans;
    }
}
