package algorithm.class147;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/1/11 13:49
 * // 任意前缀上红多于黑
 * // 有n个红和n个黑，要组成2n长度的数列，保证任意前缀上，红的数量 >= 黑的数量
 * // 返回有多少种排列方法，答案对 100 取模
 * // 1 <= n <= 100
 * // 测试链接 : https://www.luogu.com.cn/problem/P1722
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_RedMore {
    public static int MOD = 100;
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        n = (int) in.nval;
        out.println(compute4(n));
        out.flush();
        out.close();
        bf.close();
    }

    // f(n)=求和f(i)*f(n-1-i)
    private static long compute4(int n) {
        long[] f = new long[n + 1];
        f[0] = f[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int l = 0, r = i - 1; l < i; l++, r--) {
                f[i] = (f[i] + f[l] * f[r] % MOD) % MOD;
            }
        }
        return f[n];
    }
}
