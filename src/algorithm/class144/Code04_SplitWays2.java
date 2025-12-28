package algorithm.class144;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/29 13:26
 * // 分割的方法数
 * // 有同学找到了测试链接，题意几乎一样，而且数据量小很多
 * // 唯一的区别是：
 * // 课上讲的题意，单独的3可以分裂成(1, 2)、(2, 1)，一共两种方式
 * // 测试链接题意，单独的3可以分裂成(0, 3)、(1, 2)、(2, 1)、(3, 0)，一共四种方式
 * // 也就是对单独的v来说，课上讲的题意，分裂方式为v-1种。测试链接题意，分裂方式为v+1种
 * // 别的没有任何区别，实现代码中唯一有注释的那行，是仅有的改动
 * // 测试链接 : https://leetcode.cn/problems/find-the-count-of-monotonic-pairs-ii/
 */
public class Code04_SplitWays2 {
    public int countOfPairs(int[] arr) {
        int k = arr[0] - 1;
        int n = arr.length;
        for (int i = 1; i < n && k > 0; i++) {
            if (arr[i - 1] > arr[i]) {
                // 后边数字小于前边数组
                k -= arr[i - 1] - arr[i];
            }
        }
        if (k <= 0) {
            return 0;
        }
        return c(k + n - 1, n);
    }
    public static int MOD = 1000000007;
    // n里取k个数
    private static int c(int n, int k) {
        long fac = 1;
        long inv1 = 1;
        long inv2 = 1;
        for (int i = 1; i <= n; i++) {
            fac = (fac * i) % MOD;
            if (i == k) {
                inv1 = power(fac, MOD - 2);
            }
            if (i == n - k) {
                inv2 = power(fac, MOD - 2);
            }
        }
        return (int) ((((fac * inv1) % MOD) * inv2) % MOD);
    }

    private static long power(long x, int p) {
        long ans = 1;
        while (p != 0) {
            // todo 不是x&1 == 1，而是p&1 == 1
            if ((p & 1) == 1) {
                ans = (ans * x) % MOD;
            }
            x = x * x % MOD;
            p >>= 1;
        }
        return ans;
    }
}
