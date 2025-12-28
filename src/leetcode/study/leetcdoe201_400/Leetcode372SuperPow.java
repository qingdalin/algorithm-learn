package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/28 20:11
 * https://leetcode.cn/problems/super-pow/
 */
public class Leetcode372SuperPow {
    public static int MOD = 1337;
    public static int superPow(int a, int[] b) {
        int n = b.length;
        // a的34次方
        // a的4次方乘以a的30次方
        long ans = 1;
        for(int i = n - 1; i >= 0; i--) {
            ans = power(a, (long) b[i]) * ans % MOD;
            a = (int) power(a, 10);
        }
        return (int) ans;
    }

    public static long power(long x, long p) {
        long ans = 1;
        while (p > 0) {
            if ((p & 1) != 0) {
                ans = ans * x % MOD;
            }
            x = x * x % MOD;
            p >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(superPow(2, new int[] {1,1}));
    }
}
