package algorithm.class98power;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/3 14:57
 */
public class BigShow {
    public static void main(String[] args) {
        System.out.println("矩阵乘法展示开始");
        f1();
        System.out.println("矩阵乘法展示结束");
        System.out.println();
        System.out.println("矩阵快速幂展示开始");
        f2();
        System.out.println("矩阵快速幂展示开始");
        System.out.println();
        System.out.println("斐波那契数列第n项");
        f3();
        System.out.println("矩阵快速幂求斐波那契第n项");
        f4();
    }

    public int fib(int n) {
        int[][] start = { { 1, 0 } };
        int[][] m = {
            { 1, 1 },
            { 1, 0 }
        };
        int[][] a = multiply(start, power(m, 1));
        return a[0][1];
    }

    private static void f4() {
        int[][] start = { { 1, 0 } };
        int[][] m = {
            { 1, 1 },
            { 1, 0 }
        };
        int[][] a = multiply(start, power(m, 1));
        print(a);
        System.out.println("======");
        int[][] b = multiply(start, power(m, 2));
        print(b);
        System.out.println("======");
        int[][] c = multiply(start, power(m, 3));
        print(c);
        System.out.println("======");
        int[][] d = multiply(start, power(m, 4));
        print(d);
    }

    private static void f3() {
        // 0  1  1  2  3  5  8 13 21 34...
        // 0  1  2  3  4  5  6  7  8  9
        int[][] start = { { 1, 0 } };
        int[][] m = {
            { 1, 1 },
            { 1, 0 }
        };
        int[][] a = multiply(start, m);
        //       1  1
        //       1  0
        //
        // 1  0  1  1
        print(a);
        System.out.println("======");
        int[][] b = multiply(a, m);
        //       1  1
        //       1  0
        //
        // 1  1  2  1
        print(b);
        System.out.println("======");
        int[][] c = multiply(b, m);
        //       1  1
        //       1  0
        //
        // 2  1  3  2
        print(c);
        System.out.println("======");
        int[][] d = multiply(c, m);
        //       1  1
        //       1  0
        //
        // 3  2  5  3
        print(d);
    }

    private static void f2() {
        // 只有正方形矩阵可以求幂
        int[][] a = { { 1, 2 }, { 3, 4 } };
        // 连乘得到矩阵a的5次方
        int[][] b = multiply(a, multiply(a, multiply(a, multiply(a, a))));
        print(b);
        System.out.println("======");
        // 矩阵快速幂得到矩阵a的5次方
        print(power(a, 5));
    }

    // 必须是正方形矩阵
    public static int[][] power(int[][] m, int p) {
        int n = m.length;
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            // 对角线设置1就相当于ans=1
            ans[i][i] = 1;
        }
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                ans = multiply(ans, m);
            }
            m = multiply(m, m);
        }
        return ans;
    }

    private static void f1() {
        System.out.println("矩阵乘法");
        //     2  3
        //     3  2
        // 1 3 11 9
        // 2 4 16 14
        // a的列数等于b的行数，必须等
        int[][] a = {{1, 3}, {2, 4}};
        int[][] b = {{2, 3}, {3, 2}};
        int[][] ans1 = multiply(a, b);
        print(ans1);
        System.out.println("======");
        int[][] c = { { 2, 4 }, { 3, 2 } };
        int[][] d = { { 2, 3, 2 }, { 3, 2, 3 } };
        //         2  3  2
        //         3  2  3
        //
        // 2  4   16 14 16
        // 3  2   12 13 12
        int[][] ans2 = multiply(c, d);
        print(ans2);
        System.out.println("======");
        int[][] e = { { 2, 4 }, { 1, 2 }, { 3, 1 } };
        int[][] f = { { 2, 3 }, { 4, 1 } };
        //          2  3
        //          4  1
        //
        // 2  4    20 10
        // 1  2    10  5
        // 3  1    10 10
        int[][] ans3 = multiply(e, f);
        print(ans3);
        System.out.println("======");
        int[][] g = { { 3, 1, 2 } };
        int[][] h = { { 1, 2, 1 }, { 3, 2, 1 }, { 4, 2, -2 } };
        //           1  2  1
        //           3  2  1
        //           4  2 -2
        //
        // 3  1  2  14 12  0
        int[][] ans4 = multiply(g, h);
        print(ans4);
    }

    // a的列数等于b的行数，必须等
    private static int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length;
        int[][] ans = new int[n][m];
        //         2  3  2
        //         3  2  3
        //
        // 2  4   16 14 16
        // 3  2   12 13 12
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }

    // 打印二维矩阵
    public static void print(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] < 10) {
                    System.out.print(m[i][j] + "   ");
                } else if (m[i][j] < 100) {
                    System.out.print(m[i][j] + "  ");
                } else {
                    System.out.print(m[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
