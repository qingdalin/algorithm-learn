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
 * @date: 2024/10/22 20:55
 * // 贴瓷砖的方法数(轮廓线dp)
 * // 给定两个参数n和m，表示n行m列的空白区域
 * // 有无限多的1*2规格的瓷砖，目标是严丝合缝的铺满所有的空白区域
 * // 返回有多少种铺满的方法
 * // 1 <= n, m <= 11
 * // 测试链接 : http://poj.org/problem?id=2411
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
public class Code02_PavingTile1 {
    public static int MAXN = 11;
    public static int MAXM = 11;
    public static int n, m, maxs;
    public static long[][][] dp = new long[MAXN][MAXM][1 << MAXM];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken(); m = (int) in.nval;
            maxs = 1 << m;
            if (n != 0 || m != 0) {
                System.out.println(compute());
            }
        }
        out.flush();
        out.close();
        br.close();
    }

    private static long compute() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int s = 0; s < maxs; s++) {
                    dp[i][j][s] = -1;
                }
            }
        }
        return f(0, 0, 0);
    }

    // i - 1行是竖摆的状态s[j....m-1]
    // i 行是竖摆的状态s[0.......j-1]
    // s是轮廓线
    // 返回后续一共多少种方法
    private static long f(int i, int j, int s) {
        if (i == n) {
            return 1;
        }
        if (j == m) {
            return f(i + 1, 0, s);
        }
        if (dp[i][j][s] != -1) {
            return dp[i][j][s];
        }
        long ans = 0;
        if (get(s, j) == 1) {
            // 如果上方是竖摆，当前不摆，设置竖摆状态是0
            ans = f(i, j + 1, set(s, j, 0));
        } else {
            if (i + 1 < n) {
                // 可以竖摆
                ans = f(i, j + 1, set(s, j, 1));
            }
            if (j + 1 < m && get(s, j + 1) == 0) {
                // 不越界可以横摆，并且下一格也没有竖摆
                ans += f(i, j + 2, s);
            }
        }
        dp[i][j][s] = ans;
        return ans;
    }

    private static int set(int s, int j, int v) {
        return v == 0 ? (s & (~(1 << j))) : s | (1 << j);
    }

    private static int get(int s, int j) {
        return (s >> j) & 1;
    }
}
