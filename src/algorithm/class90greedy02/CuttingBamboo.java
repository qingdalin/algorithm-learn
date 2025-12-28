package algorithm.class90greedy02;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 16:59
 * 现需要将一根长为正整数 bamboo_len 的竹子砍为若干段，每段长度均为 正整数。请返回每段竹子长度的 最大乘积 是多少。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 * https://leetcode.cn/problems/jian-sheng-zi-ii-lcof/description/
 * https://leetcode.cn/problems/integer-break/description/
 */
public class CuttingBamboo {
    // 快速幂
    public static long power(long x, long n, int mod) {
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
    public int cuttingBamboo(int n) {
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 2;
        }
        int mod = 1000000007;
        // n = 4 -> 2 * 2              4 % 3 == 1
        // n = 5 -> 3 * 2              5 % 3 == 2
        // n = 6 -> 3 * 3              6 % 3 == 0
        // n = 7 -> 3 * 2 * 2          7 % 3 == 1
        // n = 8 -> 3 * 3 * 2          8 % 3 == 2
        // n = 9 -> 3 * 3 * 3          9 % 3 == 0
        // n = 10 -> 3 * 3 * 2 * 2     10 % 3 == 1
        int tail = n % 3 == 0 ? 1 : (n % 3 == 1 ? 4 : 2);
        int power = (tail == 1 ? n : (n - tail)) / 3;
        return (int) (power(3, power, mod) * tail % mod);
    }
}
