package algorithm.class133;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/24 8:47
 * // 高斯消元解决加法方程组模版(区分是否有唯一解)
 * // 一共有n个变量，给定n个加法方程，构成一个加法方程组
 * // 如果方程组存在矛盾或者无法确定唯一解，打印"No Solution"
 * // 如果方程组存在唯一解，打印每个变量的值，保留小数点后两位
 * // 1 <= n <= 100
 * // 测试链接 : https://www.luogu.com.cn/problem/P3389
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_GaussAdd {
    public static int MAXN = 101;
    public static double[][] mat = new double[MAXN][MAXN + 1];
    public static int n;
    // 大于等于sml，认为不是0
    // 小于sml，认为是0
    public static double sml = 1e-7;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n + 1; j++) {
                in.nextToken();
                mat[i][j] = in.nval;
            }
        }
        int ans = gauss();
        if (ans == 0) {
            out.println("No Solution");
        } else {
            for (int i = 1; i <= n; i++) {
                out.printf("%.2f\n", mat[i][n + 1]);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static void swap(int i, int max) {
        double[] tmp = mat[max];
        mat[max] = mat[i];
        mat[i] = tmp;
    }

    private static int gauss() {
        for (int i = 1; i <= n; i++) {
            int max = i;
            for (int j = i + 1; j <= n; j++) {
                if (Math.abs(mat[j][i]) > Math.abs(mat[max][i])) {
                    // 来到i行，遍历每一行，选出当前i列，，最大的行，
                    max = j;
                }
            }
            swap(i, max);
            if (Math.abs(mat[i][i]) < sml) {
                return 0;
            }
            // 将当前i行i列的值变为1的,其余值除以i行i列的值
            double tmp = mat[i][i];
            for (int j = 1; j <= n + 1; j++) {
                mat[i][j] /= tmp;
            }
            // 从第1行到第n行，i列的值变为0
            for (int j = 1; j <= n; j++) {
                if (j != i) {
                    double rate = mat[j][i] / mat[i][i];
                    // j行的每个值都减去，i行的每个值乘以rate
                    for (int k = i; k <= n + 1; k++) {
                        mat[j][k] -= mat[i][k] * rate;
                    }
                }
            }
        }
        return 1;
    }
}
