package algorithm.class108indextree;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/24 16:44
 * https://leetcode.cn/problems/range-sum-query-2d-mutable/description/
 * 二维树状数组单点增加，范围查询，付费
 */
public class TwoDimensionSingleAddIntervalQuery {
    class NumMatrix {
        int[][] tree;
        int[][] nums;
        int n;
        int m;

        public NumMatrix(int[][] matrix) {
            n = matrix.length;
            m = matrix[0].length;
            tree = new int[n + 1][m + 1];
            nums = new int[n + 1][m + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    update(i, j, matrix[i][j]);
                }
            }
        }

        // 实际二维数组的位置是(x,y)
        // 树状数组上的位置是(x+1, y+1)
        // 题目说的是单点更新，转化成单点增加(老值-新值)即可
        // 不要忘了在nums中把老值改成新值
        private void update(int x, int y, int v) {
            add(x + 1, y + 1, v - nums[x + 1][y + 1]);
            nums[x + 1][y + 1] = v;
        }

        private void add(int x, int y, int v) {
            for (int i = x; i <= n; i += lowBit(i)) {
                for (int j = y; j <= m; j += lowBit(j)) {
                    tree[i][j] += v;
                }
            }
        }

        private int lowBit(int i) {
            return i & -i;
        }

        // 实际二维数组的位置是(x,y)
        // 树状数组上的位置是(x+1, y+1)
        public int sumRegion(int a, int b, int c, int d) {
            return sum(c + 1, d + 1) - sum(a, d + 1) - sum(c + 1, b) + sum(a, b);
        }

        private int sum(int x, int y) {
            int ans = 0;
            for (int i = x; i > 0; i -= lowBit(i)) {
                for (int j = y; j > 0; j -= lowBit(j)) {
                    ans += tree[i][j];
                }
            }
            return ans;
        }
    }
}
