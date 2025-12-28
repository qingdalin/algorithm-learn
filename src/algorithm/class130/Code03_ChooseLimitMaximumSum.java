package algorithm.class130;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/6 20:09
 * // 不超过连续k个元素的最大累加和
 * // 给定一个长度为n的数组arr，你可以随意选择数字
 * // 要求选择的方案中，连续选择的个数不能超过k个
 * // 返回能得到的最大累加和
 * // 1 <= n、k <= 10^5
 * // 0 <= arr[i] <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P2627
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
public class Code03_ChooseLimitMaximumSum {
    public static int MAXN = 100001;
    public static long[] dp = new long[MAXN];
    public static long[] sum = new long[MAXN];
    public static int[] queue = new int[MAXN];
    public static int[] arr = new int[MAXN];
    public static int n, k, l, r;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            arr[i] = (int) in.nval;
        }
        System.out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute() {
        // 前缀和
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        l = r = 0;
        queue[r++] = 0;
        for (int i = 1; i <= n; i++) {
            // dp[i]:表示1..i范围不违规的情况下，最大收益
            // dp[9],k == 4,
            // 不要5的情况下 dp[4] + 6-9 -> dp[4] + sum[9] - sum[5]
            // 不要6的情况下 dp[5] + 7-9 -> dp[5] + sum[9] - sum[6]
            // 不要7的情况下 dp[6] + 8-9 -> dp[6] + sum[9] - sum[7]
            // 不要8的情况下 dp[7] + 9-9 -> dp[7] + sum[9] - sum[8]
            // 不要9的情况下 dp[8] + 0   -> dp[8] + sum[9] - sum[9]
            // 单调队列维护dp[i - 1] + sum[i]的最大和即可
            while (l < r && value(queue[r - 1]) <= value(i)) {
                r--;
            }
            queue[r++] = i;
            // 当前来到9位置，1 2 3 4 5 6 7 8 9 10
            //                    4位置过期，10位置进来
            if (l < r && queue[l] == i - k - 1) {
                l++;
            }
            dp[i] = value(queue[l]) + sum[i];
        }
        return dp[n];
    }

    private static long value(int i) {
        return i == 0 ? 0 : dp[i - 1] - sum[i];
    }
}
