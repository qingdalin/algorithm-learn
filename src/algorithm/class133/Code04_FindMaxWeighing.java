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
 * @date: 2024/11/24 10:21
 * // 有一次错误称重求最重物品
 * // 一共有n个物品，编号1~n，定义合法方案如下：
 * // 1，每个物品的重量都是确定的
 * // 2，每个物品的重量一定都是正整数
 * // 3，最重的物品有且仅有1个
 * // 每次称重格式类似：3 2 5 6 10，代表本次称重涉3个物品，编号为2、5、6，总重量10
 * // 一共有n+1条称重数据，称重数据整体有效的条件为：
 * // 错误的称重数据有且仅有1条，只有排除这条错误称重，才能求出一种合法方案
 * // 如果称重数据有效，打印最重三角形的编号
 * // 如果称重数据无效，打印"illegal"
 * // 1 <= m <= n <= 100
 * // 测试链接 : https://www.luogu.com.cn/problem/P5027
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_FindMaxWeighing {
    public static int MAXN = 102;
    public static double[][] mat = new double[MAXN][MAXN];
    public static int[][] data = new int[MAXN][MAXN];
    public static int n, m;
    // 大于等于sml，认为不是0
    // 小于sml，认为是0
    public static double sml = 1e-7;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 1; i <= n + 1; i++) {
            in.nextToken(); m = (int) in.nval;
            for (int j = 1, cur; j <= m; j++) {
                in.nextToken();
                cur = (int) in.nval;
                data[i][cur] = 1;
            }
            in.nextToken();
            data[i][n + 1] = (int) in.nval;
        }
        int time = 0, ans = 0;
        for (int k = 1; k <= n + 1; k++) {
            swapData(k, n + 1);
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n + 1; j++) {
                    mat[i][j] = data[i][j];
                }
            }
            swapData(k, n + 1);
            int cur = check();
            if (cur != 0) {
                ans = cur;
                time++;
            }
        }
        if (time != 1) {
            out.println("illegal");
        } else {
            out.println(ans);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int check() {
        gauss();
        int maxtime = 0;
        int ans = 0;
        double maxv = Double.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            if (mat[i][i] == 0) {
                // 不管是多解还是矛盾，直接返回0，代表非法解
                return 0;
            }
            if (mat[i][n + 1] <= 0 || mat[i][n + 1] != (int) mat[i][n + 1]) {
                // 重量是负数或者不是整数
                return 0;
            }
            if (maxv < mat[i][n + 1]) {
                maxv = mat[i][n + 1];
                maxtime = 1;
                ans = i;
            } else if (maxv == mat[i][n + 1]) {
                maxtime++;
            }
        }
        // 必须在外边判断， 如果在for循环里判断，中间有可能有两次最大值，但是后边可能再来一个，变为1个
        if (maxtime > 1) {
            return 0;
        }
        return ans;
    }

    private static void gauss() {
        for (int i = 1; i <= n; i++) {
            int max = i;
            for (int j = 1; j <= n; j++) {
                if (j < i && Math.abs(mat[j][j]) >= sml) {
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
                    if (i != j) {
                        double rate = mat[j][i] / mat[i][i];
                        for (int k = i; k <= n + 1; k++) {
                            mat[j][k] -= mat[i][k] * rate;
                        }
                    }
                }
            }
        }
    }

    private static void swapData(int i, int j) {
        int[] tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private static void swap(int i, int max) {
        double[] tmp = mat[max];
        mat[max] = mat[i];
        mat[i] = tmp;
    }
}
