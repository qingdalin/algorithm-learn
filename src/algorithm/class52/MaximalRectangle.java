package algorithm.class52;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-09 14:34
 * https://leetcode.cn/problems/maximal-rectangle/
 * 最大矩形（柱状中最大的矩形累计）
 */
public class MaximalRectangle {
    public static int r;
    public static int MAXN = 200;
    public static int[] heights = new int[MAXN];
    public static int[] stack = new int[MAXN];
    public int maximalRectangle(char[][] matrix) {
        int ans = 0;
        int n = matrix.length;
        int m = matrix[0].length;
        Arrays.fill(heights, 0, m, 0);
        for (int i = 0; i < n; i++) {
            // 以第i行做矩形的底，求一个答案
            for (int j = 0; j < m; j++) {
                heights[j] = matrix[i][j] == '0' ? 0 : heights[j] + 1;
            }
            ans = Math.max(ans, largestRectangleArea(m));
        }
        return ans;
    }

    public int largestRectangleArea(int m) {
        int ans = 0, cur, left;
        for (int i = 0; i < m; i++) {
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
            ans = Math.max(ans, heights[cur] * (m - left - 1));
        }
        return ans;
    }
}
