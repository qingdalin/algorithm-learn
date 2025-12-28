package algorithm.class75dp10;

import java.io.*;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-17 20:26
 * https://www.luogu.com.cn/problem/P1776
 * 多重背包
 * 每个物品有个数限制，二进制分组
 */
public class ManyBagQueue {
    public static int MAXN = 101;
    public static int MAXW = 40001;
    public static int[] w = new int[MAXN];
    public static int[] v = new int[MAXN];
    public static int[] c = new int[MAXN];
    public static int[] dp = new int[MAXW];
    public static int[] queue = new int[MAXW];
    public static int n, t, l, r;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            t = (int) st.nval;
            for (int i = 1; i <= n; i++) {
                st.nextToken(); v[i] = (int) st.nval;
                st.nextToken(); w[i] = (int) st.nval;
                st.nextToken(); c[i] = (int) st.nval;
            }
            out.println(f1());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int f1() {
        int[][] dp = new int[n + 1][t + 1];
        for (int i = 1; i <= n; i++) {
            // mod是模数，背包大小和个数减一的最小值
            for (int mod = 0; mod <= Math.min(t, w[i] - 1); mod++) {
                l = r = 0;
                for (int j = mod; j <= t; j += w[i]) {
                    // 队列里最好的一个加上物品个数的价值，小于当前列，则r--
                    while (l < r && dp[i - 1][queue[r - 1]] + inc(j - queue[r - 1], i) <= dp[i - 1][j]) {
                        r--;
                    }
                    queue[r++] = j;
                    // 判断是否过期
                    if (queue[l] == j - w[i] * (c[i] + 1)) {
                        // 检查单调队列最左的列号，是否过期
                        // 比如
                        // i号物品，重量为3，个数4
                        // queue[l]是队列头部的列号，假设是2
                        // 当j == 17时，依赖的格子为dp[i-1][17、14、11、8、5]
                        // 所以此时头部的列号2，过期了，要弹出
                        l++;
                    }
                    dp[i][j] = dp[i - 1][queue[l]] + inc(j - queue[l], i);
                }
            }
        }
        return dp[n][t];
    }

    private static int inc(int s, int i) {
        // s重量全部放入i物品的价值
        return s / w[i] * v[i];
    }

    private static int f2() {
        for (int i = 1; i <= n; i++) {
            // mod是模数，背包大小和个数减一的最小值
            for (int mod = 0; mod <= Math.min(t, w[i] - 1); mod++) {
                l = r = 0;
                // 因为空间压缩时，关于j的遍历是从右往左，而不是从左往右
                // 所以先让dp[i-1][...右侧的几个位置]进入窗口
                for (int j = t - mod, k = 0; j >= 0 && k <= c[i]; j -= w[i], k++) {
                    while (l < r && dp[j] + inc(queue[r - 1] - j , i) >= dp[queue[r - 1]]) {
                        r--;
                    }
                    queue[r++] = j;
                }
                for (int j = t - mod; j >= 0; j -= w[i]) {
                    dp[j] = dp[queue[l]] + inc(j - queue[l], i);
                    // 判断是否过期
                    if (queue[l] == j) {
                        // 倒着进队列，即将进行下一个 j -w[i], 如果l的位置等于j就是过期了
                        l++;
                    }
                    int enter = j - w[i] * (c[i] + 1);
                    if (enter >= 0) {
                        while (l < r && dp[enter] + inc(queue[r - 1] - enter , i) >= dp[queue[r - 1]]) {
                            r--;
                        }
                        queue[r++] = enter;
                    }
                }
            }
        }
        return dp[t];
    }
}
