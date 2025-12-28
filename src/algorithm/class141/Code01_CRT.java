package algorithm.class141;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/15 19:13
 * // 中国剩余定理模版
 * // 给出n个同余方程，求满足同余方程的最小正数解x
 * // 一共n个同余方程，x ≡ ri(% mi)
 * // 1 <= n <= 10
 * // 0 <= ri、mi <= 10^5
 * // 所有mi一定互质
 * // 所有mi整体乘积 <= 10^18
 * // 测试链接 : https://www.luogu.com.cn/problem/P1495
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_CRT {
    public static int MAXN = 11;
    public static long[] r = new long[MAXN];
    public static long[] m = new long[MAXN];

    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); m[i] = (long) in.nval;
            in.nextToken(); r[i] = (long) in.nval;
        }
        out.println(crt());
        out.flush();
        out.close();
        bf.close();
    }

    private static long crt() {
        // 所有m的最小公倍数
        long lcm = 1;
        for (int i = 1; i <= n; i++) {
            lcm *= m[i];
        }
        long ans = 0, ai, ci;
        for (int i = 1; i <= n; i++) {
            // ci = ri * ai * ai%mi的逆元
            ai = lcm / m[i];
            exgcd(ai, m[i]);
            ci = multiply(r[i], multiply(ai, x, lcm), lcm);
            ans = (ans + ci) % lcm;
        }
        return ans;
    }

    // 讲解033 - 位运算实现乘法
    // a*b过程每一步都%mod，这么写是防止溢出，也叫龟速乘
    private static long multiply(long a, long b, long mod) {
        long ans = 0;
        a = (a % mod + mod) % mod;
        b = (b % mod + mod) % mod;
        while (b != 0) {
            if ((b & 1) == 1) {
                ans = (ans + a) % mod;
            }
            a = (a + a) % mod;
            b >>= 1;
        }
        return ans;
    }

    public static long x, y, d, px, py;
    private static void exgcd(long a, long b) {
        if (b == 0) {
            d = a;
            x = 1;
            y = 0;
        } else {
            exgcd(b, a % b);
            px = x;
            py = y;
            x = py;
            y = px - py * (a / b);
        }
    }
}
