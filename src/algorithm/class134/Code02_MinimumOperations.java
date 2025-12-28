package algorithm.class134;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/27 20:12
 * // 全变成1的最少操作次数
 * // 一共有n个点，m条无向边，每个点的初始状态都是0
 * // 可以操作任意一个点，操作后该点以及相邻点的状态都会改变
 * // 最终是希望所有点都变成1状态，那么可能会若干方案都可以做到
 * // 那么其中存在需要最少操作次数的方案，打印这个最少操作次数
 * // 题目保证一定能做到所有点都变成1状态，并且没有重边和自环
 * // 1 <= n <= 35
 * // 1 <= m <= 595
 * // 测试链接 : https://www.luogu.com.cn/problem/P2962
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_MinimumOperations {
    public static int MAXN = 37;

    public static int[][] mat = new int[MAXN][MAXN];
    public static int[] op = new int[MAXN]; // 如果是自由元是否操作
    public static int n, m, ans;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        prepare();
        in.nextToken(); m = (int) in.nval;
        for (int i = 1, u, v; i <= m; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            mat[u][v] = 1;
            mat[v][u] = 1;
        }
        gauss();
        int sign = 1;
        for (int i = 1; i <= n; i++) {
            if (mat[i][i] == 0) {
                // 如果有多解
                sign = 0;
                break;
            }
        }
        if (sign == 1) {
            ans = 0;
            for (int i = 1; i <= n; i++) {
                if (mat[i][n + 1] == 1) {
                    ans++;
                }
            }
        } else {
            ans = n;
            dfs(n, 0);
        }
        out.println(ans);
        out.flush();
        out.close();
        bf.close();
    }

    // 从i+1行到n行，一共操作了num次
    private static void dfs(int i, int num) {
        if (num >= ans) {
            // 如果操作次数大于ans直接返回
            return;
        }
        if (i == 0) {
            ans = num;
        } else {
            if (mat[i][i] == 0) {
                // 当前是自由元
                // 不操作当前行的自由元，num不变去i-1决策
                op[i] = 0;
                dfs(i - 1, num);
                // 操作当前的自由元状态，num+1，去i-1决策
                op[i] = 1;
                dfs(i - 1, num + 1);
            } else {
                // 主元依赖自由元
                int cur = mat[i][n + 1];
                for (int j = i + 1; j <= n; j++) {
                    if (mat[i][j] == 1) {
                        // 依赖当前的自由元
                        cur ^= op[j];
                    }
                }
                // cur == 1: 需要改变当前主元
                // cur == 0: 不需要改变当前主元
                dfs(i - 1, num + cur);
            }
        }
    }

    private static void gauss() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (j < i && mat[j][j] == 1) {
                    continue;
                }
                if (mat[j][i] == 1) {
                    swap(i, j);
                    break;
                }
            }
            if (mat[i][i] == 1) {
                for (int j = 1; j <= n; j++) {
                    if (i != j && mat[j][i] == 1) {
                        for (int k = i; k <= n + 1; k++) {
                            mat[j][k] ^= mat[i][k];
                        }
                    }
                }
            }
        }
    }

    private static void swap(int i, int j) {
        int[] tmp = mat[i];
        mat[i] = mat[j];
        mat[j] = tmp;
    }

    private static void prepare() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                mat[i][j] = 0;
            }
            mat[i][i] = 1;
            mat[i][n + 1] = 1;
            op[i] = 0;
        }
    }
}
