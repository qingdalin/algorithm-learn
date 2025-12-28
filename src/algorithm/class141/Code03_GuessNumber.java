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
 * @date: 2024/12/15 20:33
 * // 猜数字
 * // 给定两个长度为n数组，一组为r1，r2，r3...，另一组为m1，m2，m3...
 * // 其中第二组数字两两互质，求最小正数解x
 * // 要求x满足，mi | (x - ri)，即(x - ri)是mi的整数倍
 * // 1 <= n <= 10
 * // -10^9 <= ri <= +10^9
 * // 1 <= mi <= 6 * 10^3
 * // 所有mi的乘积 <= 10^18
 * // 测试链接 : https://www.luogu.com.cn/problem/P3868
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_GuessNumber {
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
            in.nextToken(); r[i] = (long) in.nval;
        }
        for (int i = 1; i <= n; i++) {
            in.nextToken(); m[i] = (long) in.nval;
        }
        for (int i = 1; i <= n; i++) {
            r[i] = (r[i] % m[i] + m[i]) % m[i];
        }
        out.println(crt());
        out.println(excrt());
        out.flush();
        out.close();
        bf.close();
    }

    private static long excrt() {
        // 所有m的最小公倍数
        long lcm = 1, b, c, tmp, tail = 0, x0;
        // ans = lcm * x + tail
        for (int i = 1; i <= n; i++) {
            // ans = mi * y + ri 相减
            // lcm * x + mi * y = ri - tail
            // ax + by = c
            // a = lcm, b = mi, c = ri - tail
            b = m[i];
            c = ((r[i] - tail) % b + b) % b;
            exgcd(lcm, b);
            if (c % d != 0) {
                return -1;
            }
            // x是一组特解, 得到最小的x0正数解
            // x0 = x * (c/d) + b/d * n
            // 通解 x = x0 + b/d * n;
            x0 = multiply(x, c / d, b / d);
            // ans = lcm * (x0 + b/d * n) + tail
            // ans = lcm * (b/d) * n + (lcm * x0 + tail)
            // ans = lcm'*x + tail'
            tmp = lcm * (b / d);
            tail = (tail + multiply(lcm, x0, tmp)) % tmp;
            lcm = tmp;
        }
        return tail;
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
