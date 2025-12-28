package algorithm.class90greedy02;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 17:20
 * 将一个整数拆分成k分，得到的最大值是多少，没有测试链接
 */
public class IntegerBreakByK {
    public static int minVal1(int n, int k) {
        return f1(n, k);
    }

    private static int f1(int n, int k) {
        if (k == 1) {
            return n;
        }
        int ans = Integer.MIN_VALUE;
        int mod = 1000000007;
        for (int cur = 1; cur <= n && (n - cur) >= (k - 1); cur++) {
            int curAns = cur * f1(n - cur, k - 1) % mod;
            ans = Math.max(ans, curAns);
        }
        return ans;
    }

    public static int minVal2(int n, int k) {
        // 值越接近，乘积最大
        int a = n / k;
        int b = n % k;
        int mod = 1000000007;
        long part1 = power(a + 1, b, mod);
        long part2 = power(a, k - b, mod);
        return (int) (part2 * part1) % mod;
    }

    private static long power(long x, int n, int mod) {
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
        int N = 30;
        System.out.println("测试开始");
        for (int i = 0; i < 2000; i++) {
            int n = (int) (Math.random() * N + 1);
            int k = (int) (Math.random() * n + 1);
            int ans1 = minVal1(n, k);
            int ans2 = minVal2(n, k);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
            if (i % 100 == 0) {
                System.out.println("测试第" + i + "组");
            }
        }
        System.out.println("测试结束");
    }
}
