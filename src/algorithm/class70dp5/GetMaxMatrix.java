package algorithm.class70dp5;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-31 20:07
 * 给定一个正整数、负整数和 0 组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。
 *
 * 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角的行号和列号，r2, c2 分别代表右下角的行号和列号。
 * 若有多个满足条件的子矩阵，返回任意一个均可。
 * https://leetcode.cn/problems/max-submatrix-lcci/description/
 */
public class GetMaxMatrix {
    public int[] getMaxMatrix(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int max = Integer.MIN_VALUE;
        int a = 0, b = 0, c = 0, d = 0;
        int[] num = new int[m];
        for (int up = 0; up < n; up++) {
            Arrays.fill(num, 0);
            for (int down = up; down < n; down++) {
                for (int l = 0, r = 0, pre = Integer.MIN_VALUE; r < m; r++) {
                    // 求数组最大累加和，返回左右边界的问题
                    num[r] += matrix[down][r];
                    if (pre >= 0) {
                        pre += num[r];
                    } else {
                        pre = num[r];
                        l = r;
                    }
                    if (pre > max) {
                        max = pre;
                        a = up;
                        b = l;
                        c = down;
                        d = r;
                    }
                }
            }
        }
        return new int[] {a, b, c, d};
    }
}
