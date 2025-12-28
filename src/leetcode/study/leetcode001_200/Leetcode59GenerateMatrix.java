package leetcode.study.leetcode001_200;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/18 19:45
 * https://leetcode.cn/problems/spiral-matrix-ii/
 */
public class Leetcode59GenerateMatrix {
    public static int[][] generateMatrix(int n) {
        int[][] ans = new int[n][n];
        int top = 0, down = n - 1, left = 0, right = n - 1;
        int num = 1;
        int limit = n * n;
        for (; num <= limit;) {
            for (int i = left; i <= right; i++) {
                ans[top][i] = num++;
            }
            top++;
            for (int i = top; i <= down; i++) {
                ans[i][right] = num++;
            }
            right--;
            for (int i = right; i >= left; i--) {
                ans[down][i] = num++;
            }
            down--;
            for (int i = down; i >= top; i--) {
                ans[i][left] = num++;
            }
            left++;
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 3;
        System.out.println(Arrays.deepToString(generateMatrix(n)));
    }
}
