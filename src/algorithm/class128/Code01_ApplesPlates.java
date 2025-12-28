package algorithm.class128;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/2 10:26
 * // 苹果和盘子
 * // 有m个苹果，认为苹果之间无差别，有n个盘子，认为盘子之间无差别
 * // 比如5个苹果如果放进3个盘子，那么(1, 3, 1) (1, 1, 3) (3, 1, 1)认为是同一种方法
 * // 允许有些盘子是空的，返回有多少种放置方法
 * // 测试链接 : https://www.nowcoder.com/practice/bfd8234bb5e84be0b493656e390bdebf
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
public class Code01_ApplesPlates {
    public static int MAXN = 201;
    public static int MAXM = 7;
    public static int m, n;
    public static int[][] dp = new int[MAXN][MAXM];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); n = (int) in.nval;
        System.out.println(compute());
        out.flush();
        out.close();
        br.close();
    }

    private static int compute() {
        if (m < n) {
            // 分的份数比数字本身还大
            return 0;
        }
        if (m == n) {
            // 等分
            return 1;
        }
        // m > n,先至少分n份，剩余就和m个苹果分到n个盘子允许为空一样
        m -= n;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = -1;
            }
        }
        return f(m, n);
    }

    private static int f(int m, int n) {
        if (m == 0) {
            // 苹果为0，一种方法
            return 1;
        }
        if (n == 0) {
            // 苹果不为0，盘子为0，返回0
            return 0;
        }
        if (dp[m][n] != -1) {
            return dp[m][n];
        }
        int ans;
        if (m < n) {
            // 苹果数量小于盘子
            ans = f(m, m);
        } else {
            // 两种情况
            // 1允许盘子有空
            // 2不允许盘子有空，每个盘子至少一个
            ans = f(m, n - 1) + f(m - n, n);
        }
        dp[m][n] = ans;
        return ans;
    }
}
