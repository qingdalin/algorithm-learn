package algorithm.class52;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-09 14:16
 * https://leetcode.cn/problems/largest-rectangle-in-histogram/
 * 柱状中最大的矩形
 */
public class LargestRectangleArea {
    public static int[] stack = new int[100000];
    public static int r;
    public static int largestRectangleArea(int[] heights) {
        int ans = 0, cur, left;
        for (int i = 0; i < heights.length; i++) {
            while (r > 0 && heights[stack[r - 1]] >= heights[i]) {
                cur = stack[--r];
                left = r > 0 ? stack[r - 1] : -1;
                ans = Math.max(ans, heights[cur] * (i - left - 1));
            }
            stack[r++] = i;
        }
        while (r > 0) {
            cur = stack[--r];
            left = r > 0 ? stack[r - 1] : -1;
            ans = Math.max(ans, heights[cur] * (heights.length - left - 1));
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] heights = {2,1,5,6,2,3};
        largestRectangleArea(heights);
    }
}
