package algorithm.class125;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/26 20:42
 *
// 摆放国王的方法数(轮廓线dp)
// 给定两个参数n和k，表示n*n的区域内要摆放k个国王
// 国王可以攻击临近的8个方向，所以摆放时不能让任何两个国王打架
// 返回摆放的方法数
// 1 <= n <= 9
// 1 <= k <= n*n
// 测试链接 : https://www.luogu.com.cn/problem/P1896
// 提交以下的code，提交时请把类名改成"Main"，有可能全部通过
// 不过更推荐写出空间压缩的版本
 */
public class Code04_KingsFighting1 {
    public static int MAXN = 9;
    public static int MAXK = 82;
    public static int n, kings, maxs;
    public static long[][][][][] dp = new long[MAXN][MAXN][1 << MAXN][2][MAXK];
    //public static long[] prepare = new long[1 << MAXM];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); kings = (int) in.nval;
        maxs = 1 << n;
        System.out.println(compute());
        out.flush();
        out.close();
        br.close();
    }

    private static long compute() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int s = 0; s < maxs; s++) {
                    for (int leftUp = 0; leftUp <= 1; leftUp++) {
                        for (int k = 0; k <= kings; k++) {
                            dp[i][j][s][leftUp][k] = -1;
                        }
                    }
                }
            }
        }
        return f(0, 0, 0, 0, kings);
    }

    private static long f(int i, int j, int s, int leftUp, int k) {
        if (i == n) {
            return k == 0 ? 1 : 0;
        }
        if (j == n) {
            return f(i + 1, 0, s, 0, k);
        }
        if (dp[i][j][s][leftUp][k] != -1) {
            return dp[i][j][s][leftUp][k];
        }
        int left = j == 0 ? 0 : get(s, j - 1);
        int up = get(s, j);
        int rightUp = get(s, j + 1);
        long ans = f(i, j + 1, set(s, j, 0), up, k);
        if (k > 0 && left == 0 && leftUp == 0 && up == 0 && rightUp == 0) {
            ans = (ans + f(i, j + 1, set(s, j, 1), up, k - 1));
        }
        dp[i][j][s][leftUp][k] = ans;
        return ans;
    }

    private static int get(int s, int j) {
        return (s >> j) & 1;
    }
    public static int set(int s, int j, int v) {
        return v == 0 ? (s & (~(1 << j))) : (s | (1 << j));
    }
}
