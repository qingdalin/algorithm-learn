package algorithm.class125;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/21 21:14
 * // 种草的方法数(轮廓线dp空间压缩)
 * // 给定一个n*m的二维网格grid
 * // 网格里只有0、1两种值，0表示该田地不能种草，1表示该田地可以种草
 * // 种草的时候，任何两个种了草的田地不能相邻，相邻包括上、下、左、右四个方向
 * // 你可以随意决定种多少草，只要不破坏上面的规则即可
 * // 返回种草的方法数，答案对100000000取模
 * // 1 <= n, m <= 12
 * // 测试链接 : https://www.luogu.com.cn/problem/P1879
 * // 提交以下的code，提交时请把类名改成"Main"
 * // 普通状压dp的版本无法通过所有测试用例
 * // 有些测试样本会超时，这是dfs过程很费时导致的
 */
public class Code01_CornFields3 {
    public static int MAXN = 12;
    public static int MAXM = 12;
    public static int mod = 100000000;
    public static int n, m, maxs;
    public static int[][] grid = new int[MAXN][MAXM << 1];
    public static int[][] dp = new int[MAXM + 1][1 << MAXM];
    public static int[] prepare = new int[1 << MAXM];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        maxs = 1 << m;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                in.nextToken();
                grid[i][j] = (int) in.nval;
            }
        }
        System.out.println(compute());
        out.flush();
        out.close();
        br.close();
    }

    private static int compute() {
        Arrays.fill(prepare, 0, maxs, 1);
        for (int i = n - 1; i >= 0; i--) {
            for (int s = 0; s < maxs; s++) {
                dp[m][s] = prepare[s];
            }
            for (int j = m - 1; j >= 0; j--) {
                for (int s = 0; s < maxs; s++) {
                    // 不要当前档案
                    int ans = dp[j + 1][set(s, j, 0)];
                    if (grid[i][j] == 1 && (j == 0 || get(s, j - 1) == 0) && get(s, j) == 0) {
                        ans = (ans + dp[j + 1][set(s, j, 1)]) % mod;
                    }
                    dp[j][s] = ans;
                }
            }
            for (int s = 0; s < maxs; s++) {
                prepare[s] = dp[0][s];
            }
        }
        return dp[0][0];
    }



    // 将s的第j列状态设置为v
    private static int set(int s, int j, int v) {
        return v == 0 ? (s & (~(1 << j))) : (s | (1 << j));
    }

    // 返回s的第j列是什么状态，0或1
    private static int get(int s, int j) {
        return (s >> j) & 1;
    }
}
