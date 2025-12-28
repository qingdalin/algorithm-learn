package algorithm.class139;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/14 11:11
 */
public class Exgcd {
    // 扩展欧几里得算法
    // 对于方程ax + by = gcd(a,b)
    // 当a和b确定，那么gcd(a,b)也确定
    // 扩展欧几里得算法可以给出a和b的最大公约数d、以及其中一个特解x、y
    // 特别注意要保证入参a和b没有负数

    public static long d, x, y, px, py;

    public static void exgcd(int a, int b) {
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

    public static void main(String[] args) {
        int a = 220;
        int b = 170;
        exgcd(a, b);
        System.out.println("gcd(" + a + ", " + b + ") = " + d);
        System.out.println(a + " * " + x + " + " + b + " * " + y + " = " + d);
        System.out.println("求逆元测试开始");
        int mod = 1000000007;
        int test = 10000000;
        for (int i = 1; i <= test; i++) {
            exgcd(i, mod);
            x = (x % mod + mod) % mod;
            if (x != farmat(i, mod)) {
                System.out.println("出错了");
            }
        }
        System.out.println("求逆元测试结束");
    }

    private static long farmat(long num, long mod) {
        return power(num, mod - 2, mod);
    }

    private static long power(long a, long b, long mod) {
        long ans = 1;
        while (b != 0) {
            if ((b & 1) == 1) {
                ans = ans * a % mod;
            }
            a = a * a % mod;
            b >>= 1;
        }
        return ans;
    }
}
