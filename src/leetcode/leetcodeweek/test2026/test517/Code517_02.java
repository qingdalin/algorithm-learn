package leetcode.leetcodeweek.test2026.test517;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/30 9:49
 * https://leetcode.cn/contest/weekly-contest-517/problems/sum-of-decoded-numbers/
 */
public class Code517_02 {
    public static int MOD = 1000000007;
    public static int sumDecoded(long[] nums) {
        long ans = 0;
        for (long num : nums) {
            long width = num % 10;
            long d = num / 10;
            int total = len(d);
            long curMod = power(10, total - width);
            long x = d / curMod;
            long y = d % curMod;
            long cur = power(x, y);
            ans = (ans + cur) % MOD;
        }
        return (int) ans;
    }

    public static int len(long n) {
        int ans = 0;
        while (n > 0) {
            ans++;
            n /= 10;
        }
        return ans;
    }

    public static long power(long x, long p) {
        long ans = 1;
        while (p > 0) {
            if ((p & 1) == 1) {
                ans = ans * x % MOD;
            }
            x = x * x % MOD;
            p >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 252;
        System.out.println(len(n));
        int a = 3, b = 3;
        System.out.println(power(a, b));
        long[] arr = {2522,2101};
        System.out.println(sumDecoded(arr));
    }


}
