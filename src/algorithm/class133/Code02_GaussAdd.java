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
 * @date: 2024/11/24 9:23
 * // 高斯消元解决加法方程组模版(区分矛盾、多解、唯一解)
 * // 一共有n个变量，给定n个加法方程，构成一个加法方程组
 * // 如果方程组存在矛盾，打印-1
 * // 如果方程组无法确定唯一解，打印0
 * // 如果方程组存在唯一解，打印每个变量的值，保留小数点后两位
 * // 1 <= n <= 50
 * // 测试链接 : https://www.luogu.com.cn/problem/P2455
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_GaussAdd {
    public static int MAXN = 51;
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
        gauss();
        int ans = 1;
        for (int i = 1; i <= n; i++) {
            if (Math.abs(mat[i][i]) < sml && Math.abs(mat[i][n + 1]) >= sml) {
                // 矛盾
                ans = -1;
                break;
            }
            if (Math.abs(mat[i][i]) < sml) {
                // 多解
                ans = 0;
            }
        }
        if (ans == 1) {
            for (int i = 1; i <= n; i++) {
                out.printf("x" + i + "=" + "%.2f\n", mat[i][n + 1]);
            }
        } else {
            out.println(ans);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static void gauss() {
        for (int i = 1; i <= n; i++) {
            int max = i;
            // 严格区分矛盾，多解，唯一解
            for (int j = 1; j <= n; j++) {
                if (j < i && Math.abs(mat[j][j]) >= sml) {
                    // 如果行数j小于i，并且值大于等于0，
                    continue;
                }
                if (Math.abs(mat[j][i]) > Math.abs(mat[max][i])) {
                    max = j;
                }
            }
            swap(i, max);
            if (Math.abs(mat[i][i]) >= sml) {
                double tmp = mat[i][i];
                for (int j = i; j <= n + 1; j++) {
                    mat[i][j] /= tmp;
                }
                for (int j = 1; j <= n; j++) {
                    if (j != i) {
                        double rate = mat[j][i] / mat[i][i];
                        for (int k = i; k <= n + 1; k++) {
                            mat[j][k] -= mat[i][k] * rate;
                        }
                    }
                }
            }
        }
    }

    private static void swap(int i, int max) {
        double[] tmp = mat[max];
        mat[max] = mat[i];
        mat[i] = tmp;
    }
}
