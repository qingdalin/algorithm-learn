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
 * @date 2024-06-23 9:48
 * 有台奇怪的打印机有以下两个特殊要求：
 *
 * 打印机每次只能打印由 同一个字符 组成的序列。
 * 每次可以在从起始到结束的任意位置打印新字符，并且会覆盖掉原来已有的字符。
 * 给你一个字符串 s ，你的任务是计算这个打印机打印它需要的最少打印次数。
 * https://leetcode.cn/problems/strange-printer/description/
 * https://www.luogu.com.cn/problem/P4170
 */
public class StrangePrinter {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String line = bf.readLine();
        out.println(strangePrinter(line));
        out.flush();
        out.close();
        bf.close();
    }

    public static int strangePrinter(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] dp = new int[n][n];
        // dp[l][r]: 当l和r位置字符相等，通过l到r-1或者l+1到r解决,不会错过最优解
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = s[i] == s[i + 1] ? 1 : 2;
        }
        dp[n - 1][n - 1] = 1;
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                if (s[l] == s[r]) {
                    dp[l][r] = dp[l + 1][r];
                    // dp[l][r] = dp[l][r - 1];
                } else {
                    int ans = Integer.MAX_VALUE;
                    for (int m = l; m < r; m++) {
                        ans = Math.min(ans, dp[l][m] + dp[m + 1][r]);
                    }
                    dp[l][r] = ans;
                }
            }
        }
        return dp[0][n - 1];
    }
}
