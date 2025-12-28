package algorithm.class76dp11;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-22 15:12
 * 给定一个布尔表达式和一个期望的布尔结果 result，布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成。
 * 实现一个函数，算出有几种可使该表达式得出 result 值的括号方法。
 * https://leetcode.cn/problems/boolean-evaluation-lcci/description/
 */
public class CountEval {
    public int countEval(String str, int result) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][][] dp = new int[n][n][];
        int[] temp = f1(s, 0, n - 1, dp);
        return temp[result];
    }

    private int[] f1(char[] s, int l, int r, int[][][] dp) {
        if (dp[l][r] != null) {
            return dp[l][r];
        }

        int t = 0, f = 0;
        if (l == r) {
            // 如果只有一个字符
            f = s[l] == '0' ? 1: 0;
            t = s[l] == '1' ? 1: 0;
        } else {
            int[] tmp;
            for (int k = l + 1, a, b, c, d; k < r; k += 2) {
                tmp = f1(s, l, k - 1, dp);
                a = tmp[0];
                b = tmp[1];
                tmp = f1(s, k + 1, r, dp);
                c = tmp[0];
                d = tmp[1];
                if (s[k] == '&') {
                    f += (a * c + a * d + c * b);
                    t += (b * d);
                } else if (s[k] == '|') {
                    f += (c * a);
                    t += (b * c + b * d + d * a);
                } else {
                    f += (a * c + b * d);
                    t += (a * d + b * c);
                }
            }
        }
        int[] ft = new int[] {f, t};
        dp[l][r] = ft;
        return ft;
    }

    public static int countEval1(String str, int result) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][][] dp = new int[n][n][];
        int[] ft = f(s, 0, n - 1, dp);
        return ft[result];
    }

    // s[l...r]是表达式的一部分，且一定符合范式
    // 0/1  逻  0/1   逻       0/1
    //  l  l+1  l+2  l+3........r
    // s[l...r]  0 : ?
    //           1 : ?
    // ans : int[2] ans[0] = false方法数 ans[0] = true方法数
    public static int[] f(char[] s, int l, int r, int[][][] dp) {
        if (dp[l][r] != null) {
            return dp[l][r];
        }
        int f = 0;
        int t = 0;
        if (l == r) {
            // 只剩一个字符，0/1
            f = s[l] == '0' ? 1 : 0;
            t = s[l] == '1' ? 1 : 0;
        } else {
            int[] tmp;
            for (int k = l + 1, a, b, c, d; k < r; k += 2) {
                // l ... r
                // 枚举每一个逻辑符号最后执行 k = l+1 ... r-1  k+=2
                tmp = f(s, l, k - 1, dp);
                a = tmp[0];
                b = tmp[1];
                tmp = f(s, k + 1, r, dp);
                c = tmp[0];
                d = tmp[1];
                if (s[k] == '&') {
                    f += a * c + a * d + b * c;
                    t += b * d;
                } else if (s[k] == '|') {
                    f += a * c;
                    t += a * d + b * c + b * d;
                } else {
                    f += a * c + b * d;
                    t += a * d + b * c;
                }
            }
        }
        int[] ft = new int[] { f, t };
        dp[l][r] = ft;
        return ft;
    }
}
