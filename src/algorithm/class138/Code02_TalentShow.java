package algorithm.class138;

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
 * @date: 2024/12/7 14:16
 * // 牛群的才艺展示
 * // 一共有n只牛，每只牛有重量和才艺两个属性值
 * // 要求一定要选若干只牛，使得总重量不少于w，并且选出的牛，希望让
 * // 才艺的和 / 重量的和，这个比值尽量大
 * // 返回该比值 * 1000的整数结果，小数部分舍弃
 * // 1 <= n <= 250
 * // 1 <= w <= 1000
 * // 1 <= 牛的重量 <= 10^6
 * // 1 <= 牛的才艺 <= 10^3
 * // 测试链接 : https://www.luogu.com.cn/problem/P4377
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_TalentShow {
    public static int MAXN = 251;
    public static int MAXW = 1001;
    public static int n, w;
    public static double sml = 1e-6;
    public static double NA = -1e9;
    // dp[i][j] : 1...i号牛自由选择，重量必须是j的情况下，最大的结余和
    // 特别的，dp[i][w]表示1...i号牛自由选择，重量必须是w、w+1...的所有情况中，最大的结余和
    // 为了节省时间和空间选择这么定义，同时做空间压缩
    public static double[] dp = new double[MAXW];

    public static int[] wight = new int[MAXN];
    public static int[] talent = new int[MAXN];
    public static double[] value = new double[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); w = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); wight[i] = (int) in.nval;
            in.nextToken(); talent[i] = (int) in.nval;
        }
        double l = 0, r = 0, x;
        for (int i = 1; i <= n; i++) {
            r += talent[i];
        }
        double ans = 0;
        while (l < r && r - l >= sml) {
            x = (r + l) / 2;
            if (check(x)) {
                ans = x;
                l = x + sml;
            } else {
                r = x - sml;
            }
        }
        out.println((int)(ans * 1000));
        out.flush();
        out.close();
        bf.close();
    }

    private static boolean check(double x) {
        for (int i = 1; i <= n; i++) {
            value[i] = (double) talent[i] - x * wight[i];
        }
        dp[0] = 0;
        Arrays.fill(dp, 1, w + 1, NA);
        for (int i = 1; i <= n; i++) {
            for (int p = w, j; p >= 0; p--) {
                j = (p + wight[i]);
                if (j >= w) {
                    dp[w] = Math.max(dp[w], dp[p] + value[i]);
                } else {
                    dp[j] = Math.max(dp[j], dp[p] + value[i]);
                }
            }
        }
        return dp[w] >= 0;
    }
}
