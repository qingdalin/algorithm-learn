package algorithm.class99;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/4 11:19
 */
public class InverseSingle {
    // 逆元使用的三个条件
    // a / b 能够整除
    // b 和 mod 最大公约数是1 b和mod互为质数
    // mod 是一个质数
    public static void main(String[] args) {
        // 1) 必须保证a/b可以整除
        // 2) 必须保证mod是质数
        // 3) 必须保证b和mod的最大公约数为1
        long power = power(88, 1500, 1000000007);
        int mod = 41;
        long b = 3671613L;
        long a = 67312L * b;
        System.out.println(compute1(a, b, mod));
        System.out.println(compute2(a, b, mod));
    }

    private static int compute2(long a, long b, int mod) {
        // (a % mod) * b的mod-2次方，最后mod
        long inv = power(b, mod - 2, mod);
        return (int) (((a % mod) * inv) % mod);
    }

    // 乘法快速幂
    // 计算b的n次方的结果%mod
    public static long power(long b, int n, int mod) {
        long ans = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                ans = (ans * b) % mod;
            }
            b = (b * b) % mod;
            n >>= 1;
        }
        return ans;
    }

    private static int compute1(long a, long b, int mod) {
        return (int) ((a  / b ) % mod);
    }
}
