package algorithm.class144;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/29 13:04
 * // 分割的方法数
 * // 给定一个长度为n的数组A, 将其分割成数组B和数组C，满足A[i] = B[i] + C[i]
 * // 也就是一个数字分成两份，然后各自进入B和C，要求B[i], C[i] >= 1
 * // 同时要求，B数组从左到右不能降序，C数组从左到右不能升序
 * // 比如，A = { 5, 4, 5 }，一种有效的划分，B = { 2, 2, 3 }，C = { 3, 2, 2 }
 * // 返回有多少种有效的划分方式
 * // 1 <= n <= 10^7
 * // 1 <= A[i] <= 10^7
 * // 最终结果可能很大，答案对 1000000007 取模
 * // 来自真实大厂笔试题，该实现为对数器版本
 * // 有同学找到了测试链接，就是Code04_SplitWays2文件
 */
public class Code04_SplitWays1 {
    public static int ways1(int[] arr) {
        int ans = 0;
        for (int b = 1, c = arr[0] - 1; b < arr[0]; b++, c--) {
            ans += f(arr, 1, b, c);
        }
        return ans;
    }

    // 当前来到i位置，
    // b数组的前一个数字是ip,b数组不下降
    // c数组的前一个数字是dp,c数组不上升
    private static int f(int[] arr, int i, int preb, int prec) {
        if (i == arr.length) {
            return 1;
        }
        int ans = 0;
        for (int b = 1, c = arr[i] - 1; b < arr[i]; b++, c--) {
            if (b >= preb && c <= prec) {
                ans += f(arr, i + 1, b, c);
            }
        }
        return ans;
    }
    public static int ways2(int[] arr) {
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

    public static void main(String[] args) {
        System.out.println("功能测试开始");
        int N = 10;
        int V = 20;
        int test = 10000;
        for (int t = 0; t < test; t++) {
            int n = ((int) (Math.random() * N)) + 1;
            int[] arr = randomArr(n, V);
            int ans1 = ways1(arr);
            int ans2 = ways2(arr);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("功能测试结束");
        System.out.println();
        System.out.println("性能测试开始");
        int n = 10000000;
        int v = 10000000;
        System.out.println("随机生成的数据测试");
        System.out.println("数组长度是：" + n);
        System.out.println("数值范围是：[1, " + n + "]");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * v) + 1;
        }
        long start, end;
        start = System.currentTimeMillis();
        int ans = ways2(arr);
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");
        System.out.println("运行最慢的数据测试");
        System.out.println("数组长度 : " + n);
        System.out.println("数值都是 : " + v);
        System.out.println("这种情况其实是复杂度最高的情况");
        for (int i = 0; i < n; i++) {
            arr[i] = v;
        }
        start = System.currentTimeMillis();
        ways2(arr);
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");
        System.out.println("性能测试结束");
    }

    private static int[] randomArr(int n, int v) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * v) + 1;
        }
        return arr;
    }
}
