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
 * @date: 2025/1/11 13:44
 * // 圆上连线
 * // 圆上有2n个点，这些点成对连接起来，形成n条线段，任意两条线段不能相交，返回连接的方法数
 * // 注意！答案不对 10^9 + 7 取模！而是对 10^8 + 7 取模！
 * // 1 <= n <= 2999
 * // 测试链接 : https://www.luogu.com.cn/problem/P1976
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_CircleLine {
    public static int MAXN = 6000;
    public static int MOD = 100000007;
    public static long[] fac = new long[MAXN];
    public static long[] inv = new long[MAXN];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        n = (int) in.nval;
        out.println(compute1(n));
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

    // 卡特兰数方式1
    // C(2n,n)-C(2n,n-1)
    private static long compute1(int n) {
        build1(2 * n);
        return (c(2 * n, n) - c(2 * n, n - 1) + MOD) % MOD;
    }

    private static long c(int n, int k) {
        return (((fac[n] * inv[k]) % MOD) * inv[n - k]) % MOD;
    }

    private static void build1(int n) {
        fac[0] = inv[0] = 1;
        fac[1] = 1;
        for (int i = 2; i <= n; i++) {
            fac[i] = ((long) i * fac[i - 1]) % MOD;
        }
        inv[n] = power(fac[n], MOD - 2);
        for (int i = n - 1; i >= 1; i--) {
            inv[i] = ((long) (i + 1) * inv[i + 1]) % MOD;
        }
    }

    private static long power(long x, long p) {
        long ans = 1;
        while (p > 0) {
            if ((p & 1) == 1) {
                ans = (ans * x) % MOD;
            }
            x = (x * x) % MOD;
            p >>= 1;
        }
        return ans;
    }
}
