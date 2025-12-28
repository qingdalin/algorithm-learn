package algorithm.class53;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-10 14:27
 * https://leetcode.cn/problems/count-submatrices-with-all-ones/
 * 单调栈，统计矩形
 */
public class NumSubmat {
    public static int MAXN = 150;
    public static int r;
    public static int[] stack = new int[MAXN];
    public static int[] heights = new int[MAXN];
    public int numSubmat(int[][] mat) {
        int ans = 0;
        int n = mat.length;
        int m = mat[0].length;
        Arrays.fill(heights, 0, m, 0);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                heights[j] = mat[i][j] == 0 ? 0 : heights[j] + 1;
            }
            ans += countFromBottom(m);
        }
        return ans;
    }

    public int countFromBottom(int m) {
        r = 0;
        int ans = 0, cur, left, bottom, len;
        for (int i = 0; i < m; i++) {
            while (r > 0 && heights[stack[r - 1]] >= heights[i]) {
                cur = stack[--r];
                if (heights[cur] > heights[i]) {
                    left = r == 0 ? -1 : stack[r -1];
                    len = i - left - 1;
                    bottom = Math.max(heights[i], left == -1 ? 0 : heights[left]);
                    ans += (heights[cur] - bottom) * len * (len + 1) / 2;
                }
            }
            stack[r++] = i;
        }
        while (r > 0) {
            cur = stack[--r];
            left = r == 0 ? -1 : stack[r -1];
            len = m - left - 1;
            bottom = left == -1 ? 0 : heights[left];
            ans += (heights[cur] - bottom) * len * (len + 1) / 2;
        }
        return ans;
    }
}
