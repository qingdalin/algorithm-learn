package algorithm.class135;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/1 9:30
 * // 格子全变成0的操作方案
 * // 有一个n*m的二维网格，给定每个网格的初始值，一定是0、1、2中的一个
 * // 如果某个网格获得了一些数值加成，也会用%3的方式变成0、1、2中的一个
 * // 比如有个网格一开始值是1，获得4的加成之后，值为(1+4)%3 = 2
 * // 有一个神奇的刷子，一旦在某个网格处刷一下，该网格会获得2的加成
 * // 并且该网格上、下、左、右的格子，都会获得1的加成
 * // 最终目标是所有网格都变成0，题目保证一定有解，但不保证唯一解
 * // 得到哪一种方案都可以，打印一共需要刷几下，并且把操作方案打印出来
 * // 1 <= n、m <= 30
 * // 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5755
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
// TODO 提交答案未接受
public class Code01_GamblerBo {
    public static int MAXN = 1001;
    public static int MOD = 3;
    public static int[][] mat = new int[MAXN][MAXN];
    public static int[] dir = {0, -1, 0, 1, 0};
    public static int[] inv = new int[MAXN];
    public static int n, m, ans, s;
    public static void main(String[] args) throws IOException {
        inv();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        int test = (int) in.nval;
        for (int t = 0; t < test; t++) {
            in.nextToken(); n = (int) in.nval;
            in.nextToken(); m = (int) in.nval;
            s = m * n;
            prepare();
            for (int i = 1; i <= s; i++) {
                in.nextToken();
                mat[i][s + 1] = (3 - (int) in.nval) % MOD;
            }
            gauss(s);
            ans = 0;
            for (int i = 1; i <= s; i++) {
                if (mat[i][i] != 0) {
                    ans += mat[i][s + 1];
                }
            }
            out.println(ans);
            for (int i = 1, id = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++, id++) {
                    while (mat[id][s + 1]-- > 0) {
                        out.println(i + " " + j);
                    }
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static void gauss(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (j < i && mat[j][j] != 0) {
                    continue;
                }
                if (mat[j][i] != 0) {
                    swap(i, j);
                    break;
                }
            }
            if (mat[i][i] != 0) {
                for (int j = 1; j <= n; j++) {
                    if (j != i && mat[j][i] != 0) {
                        int gcd = gcd(mat[i][i], mat[j][i]);
                        int a = mat[i][i];
                        int b = mat[j][i];
                        if (j < i && mat[j][j] != 0) {
                            // 调整主元系数，和模板区别
                            mat[j][j] = (mat[j][j] * a) % MOD;
                        }
                        for (int k = i; k <= n + 1; k++) {
                            mat[j][k] = ((mat[j][k] * a - mat[i][k] * b) % MOD + MOD) % MOD;
                        }
                    }
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            if (mat[i][i] != 0) {
                mat[i][n + 1] = (mat[i][n + 1] * inv[mat[i][i]]) % MOD;
                mat[i][i] = 1;
            }
        }
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static void swap(int i, int j) {
        int[] tmp = mat[i];
        mat[i] = mat[j];
        mat[j] = tmp;
    }

    private static void prepare() {
        for (int i = 1; i <= s; i++) {
            for (int j = 1; j <= s + 1; j++) {
                mat[i][j] = 0;
            }
        }
        int cur, col, row;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cur = i * m + j + 1;
                mat[cur][cur] = 2;
                for (int d = 0; d <= 3; d++) {
                    row = i + dir[d];
                    col = j + dir[d + 1];
                    if (row >= 0 && row < n && col >= 0 && col < m) {
                        mat[cur][row * m + col + 1] = 1;
                    }
                }
            }
        }
    }

    private static void inv() {
        inv[1] = 1;
        for (int i = 2; i < MOD; i++) {
            inv[i] = (int) (MOD - (long)inv[MOD % i] * (MOD / i) % MOD);
        }
    }
}
