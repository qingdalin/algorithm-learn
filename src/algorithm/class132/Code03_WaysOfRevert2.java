package algorithm.class132;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/17 10:19
 * // 还原数组的方法数(优化枚举)
 * // 原本有一个长度为n的数组arr，下标从1开始，数组中都是<=200的正数
 * // 并且任意i位置的数字都满足 : arr[i] <= max(arr[i-1], arr[i+1])
 * // 特别的，arr[1] <= arr[2]，arr[n] <= arr[n-1]
 * // 但是输入的arr中有些数字丢失了，丢失的数字用0表示
 * // 返回还原成不违规的arr有多少种方法，答案对 998244353 取模
 * // 3 <= n <= 10^4
 * // 测试链接 : https://www.nowcoder.com/practice/49c5284278974cbda474ec13d8bd86a9
 * // 提交以下的code，提交时请把类名改成"Main"，无法通过所有测试用例
 * // 需要优化枚举才能通过所有测试用例，具体看本节课Code03_WaysOfRevert2文件的实现
 */
public class Code03_WaysOfRevert2 {
    public static int MAXN = 10001;
    public static int mod = 998244353;
    public static int[] arr = new int[MAXN];

    public static int n;
    public static int m = 200;
    public static int[] pre1 = new int[m + 1];
    public static int[] suf0 = new int[m + 1];
    public static int[][] dp = new int[m + 1][2];
    public static int[][] memory = new int[m + 1][2];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            arr[i] = (int) in.nval;
        }
        out.println(compute3());
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute3() {
        for (int v = 0; v <= m; v++) {
            memory[v][0] = 0;
            memory[v][1] = 1;
        }
        for (int i = 1; i <= n; i++) {
            prepare();
            for (int v = 0; v <= m; v++) {
                for (int s = 0; s <= 1; s++) {
                    int ans = 0;
                    if (arr[i] != 0) {
                        // arr[i] < v && s == 0
                        // i位置小于i+1位置的v 并且 i+1 > i+2,直接返回0
                        // 否则进行i-1位置判断
                        if (arr[i] >= v || s == 1) {
                            ans = memory[arr[i]][arr[i] > v ? 0 : 1];
                        }
                    } else {
                        if (v + 1 <= m) {
                            ans = (ans + suf0[v + 1]) % mod;
                        }
                        if (v != 0) {
                            ans = (ans + memory[v][1]) % mod;
                        }
                        if (s == 1 && v - 1 >= 0) {
                            ans = (ans + pre1[v - 1]) % mod;
                        }
                    }
                    dp[v][s]= ans;
                }
            }
            int[][] tmp = memory;
            memory = dp;
            dp = tmp;
        }
        // 从1....i范围，i+1的数字是v
        // s==0表示 s[i+1] > s[i+2]
        // s==1表示 s[i+1] <=s[i+2]
        // 返回有多少种还原方法
        return memory[0][1];
    }

    private static void prepare() {
        suf0[m] = memory[m][0];
        for (int i = m - 1; i >= 1; i--) {
            suf0[i] = (suf0[i + 1] + memory[i][0]) % mod;
        }
        pre1[1] = memory[1][1];
        for (int i = 2; i <= m; i++) {
            pre1[i] = (pre1[i - 1] + memory[i][1]) % mod;
        }
    }
}
