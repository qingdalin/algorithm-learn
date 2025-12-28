package algorithm.class99;

import java.math.BigInteger;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/4 15:14
 * // 连续阶乘逆元的线性递推
 * // 实现组合公式C(n,m)的计算
 * // 最终结果 % 1000000007后返回
 * // 0 <= m <= n <= 1000
 * // 对数器验证
 */
public class InverseFactorial {
    public static int mod = 1000000007;
    public static int limit = 1000;
    // fac[i]:i的阶乘 % mod
    public static long[] fac = new long[limit + 1];
    // inv[i]:i!的逆元 % mod
    public static long[] inv = new long[limit + 1];
    public static void build() {
        fac[0] = 1;
        for (int i = 1; i <= limit; i++) {
            fac[i] = ((long) i * fac[i - 1]) % mod;
            //fac[i] = ((long) i * fac[i - 1]) % mod;
        }
        inv[limit] = power(fac[limit], mod - 2, mod);
        for (int i = limit - 1; i >= 0; i--) {
            inv[i] = ((long) (i + 1) * inv[i + 1] ) % mod;
        }
    }
    // C(n, m)有多少种方法
    public static int c1(int n, int m) {
        BigInteger a = new BigInteger("1");
        BigInteger b = new BigInteger("1");
        BigInteger c = new BigInteger("1");
        for (int i = 2; i <= n; i++) {
            String cur = String.valueOf(i);
            a = a.multiply(new BigInteger(cur));
            if (i <= m) {
                b = b.multiply(new BigInteger(cur));
            }
            if (i <= n - m) {
                c = c.multiply(new BigInteger(cur));
            }
        }
        BigInteger ans = a.divide(b.multiply(c)).mod(new BigInteger(String.valueOf(mod)));
        return ans.intValue();
    }

    public static int c2(int n, int m) {
        long ans = fac[n];
        ans = (ans * inv[m]) % mod;
        ans = (ans * inv[n - m]) % mod;
        return (int) ans;
    }

    private static long power(long x, long n, int mod) {
        long ans = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                ans = (ans * x) % mod;
            }
            x = (x * x) % mod;
            n >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        int n = 500;
        build();;
        for (int m = 0; m <= n; m++) {
            int ans1 = c1(n, m);
            int ans2 = c2(n, m);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
        int a = 987;
        int b = 124;
        System.out.println("测试从" + a + "中选" + b + "个");
        System.out.println(c1(a, b));
        System.out.println(c2(a, b));
    }
}
