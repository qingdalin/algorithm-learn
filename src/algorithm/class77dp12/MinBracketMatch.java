package algorithm.class77dp12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-23 9:11
 * 给定一个由 '[' ，']'，'('，‘)’ 组成的字符串，请问最少插入多少个括号就能使这个字符串的所有括号左右配对。
 * 例如当前串是 "([[])"，那么插入一个']'即可满足。
 *
 * 数据范围：字符串长度满足1≤n≤100
 * 输入描述：
 * 仅一行，输入一个字符串，字符串仅由 '[' ，']' ，'(' ，‘)’ 组成
 * 输出描述：
 * 输出最少插入多少个括号
 * https://www.nowcoder.com/practice/e391767d80d942d29e6095a935a5b96b
 */
public class MinBracketMatch {
    public static int MAXN = 101;
    public static String line;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while ((line = bf.readLine()) != null) {
            char[] s = line.toCharArray();
            out.println(f2(s));
        }
        out.flush();
        out.close();
        bf.close();
    }
    private static int f2(char[] s) {
        int n = s.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = (s[i] == '(' && s[i + 1] == ')') || (s[i] == '[' && s[i + 1] == ']') ? 0 : 2;
        }
        dp[n - 1][n - 1] = 1;
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                int p1 = Integer.MAX_VALUE;
                if ((s[l] == '(' && s[r] == ')') || (s[l] == '[' && s[r] == ']')) {
                    p1 = dp[l + 1][r - 1];
                }
                int p2 = Integer.MAX_VALUE;
                for (int m = l; m < r; m++) {
                    p2 = Math.min(p2, dp[l][m] + dp[m + 1][r]);
                }
                dp[l][r] = Math.min(p1, p2);
            }
        }
        return dp[0][n - 1];
    }

    private static int f1(char[] s, int l, int r, int[][] dp) {
        if (l == r) {
            return 1;
        }
        if (l + 1 == r) {
            if ((s[l] == '(' && s[r] == ')') || (s[l] == '[' && s[r] == ']')) {
                return 0;
            }
            return 2;
        }
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        int p1 = Integer.MAX_VALUE;
        if ((s[l] == '(' && s[r] == ')') || (s[l] == '[' && s[r] == ']')) {
            p1 = f1(s, l + 1, r - 1, dp);
        }
        int p2 = Integer.MAX_VALUE;
        for (int m = l; m < r; m++) {
            p2 = Math.min(p2, f1(s, l, m, dp) + f1(s, m + 1, r, dp));
        }
        int ans = Math.min(p1, p2);
        dp[l][r] = ans;
        return ans;
    }
}
