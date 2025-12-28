package algorithm.class85dp20;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/14 10:11
 * 不含前导零且相邻两个数字之差至少为
 * 2
 * 2 的正整数被称为 windy 数。windy 想知道，在
 * a 和 b 之间，包括 a 和 b ，总共有多少个 windy 数？
 * https://www.luogu.com.cn/problem/P2657
 */
public class WindyNum {
    public static int MAXN = 11;
    public static int[][][] dp = new int[MAXN][11][2];
    public static int a, b;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            a = (int) st.nval;
            st.nextToken();
            b = (int) st.nval;
            // 扩充重要
            System.out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        return cnt(b) - cnt(a - 1);
    }

    private static int cnt(int num) {
        if (num == 0) {
            return 1;
        }
        int len = 1;
        int offset = 1;
        int tmp = num / 10;
        while (tmp > 0) {
            len++;
            offset *= 10;
            tmp /= 10;
        }
        build(len);
        return f1(num, offset, len, 10, 0);
    }

    // pre == 10，表示前面没有选择数字
    // free == 0，表示不能自由选择
    // free == 1，表示可以自由选择
    private static int f1(int num, int offset, int len, int pre, int free) {
        if (len == 0) {
            return 1;
        }
        if (dp[len][pre][free] != -1) {
            return dp[len][pre][free];
        }
        int ans = 0;
        int cur = num / offset % 10;
        if (free == 0) {
            // 不能自由选择
            if (pre == 10) {
                // 前边没有选择数字，继续不选择往下走
                ans += f1(num, offset / 10, len - 1, pre, 1);
                // 选择小于cur的
                for (int i = 1; i < cur; i++) {
                    ans += f1(num, offset / 10, len - 1, i, 1);
                }
                ans += f1(num, offset / 10, len - 1, cur, 0);
            } else {
                // 前边选择过数字了
                for (int i = 0; i <= 9; i++) {
                    if (i <= pre - 2 || i >= pre + 2) {
                        if (i < cur) {
                            ans += f1(num, offset / 10, len - 1, i, 1);
                        } else if (i == cur) {
                            ans += f1(num, offset / 10, len - 1, cur, 0);
                        }
                    }
                }
            }
        } else {
            if (pre == 10) {
                ans += f1(num, offset / 10, len - 1, pre, 1);
                for (int i = 1; i <= 9; i++) {
                    ans += f1(num, offset / 10, len - 1, i, 1);
                }
            } else {
                for (int i = 0; i <= 9; i++) {
                    if (i <= pre - 2 || i >= pre + 2) {
                        ans += f1(num, offset / 10, len - 1, i, 1);
                    }
                }
            }
        }
        dp[len][pre][free] = ans;
        return ans;
    }

    private static void build(int len) {
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= 10; j++) {
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
            }
        }
    }
}
