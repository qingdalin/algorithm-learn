package algorithm.class128;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/2 16:28
 * // 相邻必选的子序列最大中位数
 * // 给定一个长度为n的数组arr
 * // 合法子序列定义为，任意相邻的两个数至少要有一个被挑选所组成的子序列
 * // 求所有合法子序列中，最大中位数是多少
 * // 中位数的定义为上中位数
 * // [1, 2, 3, 4]的上中位数是2
 * // [1, 2, 3, 4, 5]的上中位数是3
 * // 2 <=  n <= 10^5
 * // 1 <= arr[i] <= 10^9
 * // 来自真实大厂笔试，对数器验证
 */
public class Code05_MaximizeMedian {
    public static int maximizeMedian(int[] arr) {
        int n = arr.length;
        int[] sort = new int[n];
        for (int i = 0; i < n; i++) {
            sort[i] = arr[i];
        }
        Arrays.sort(sort);
        int[] help = new int[n];
        int[][] dp = new int[n + 1][2];
        int l = 0;
        int r = n - 1;
        int m = 0;
        int ans = -1;
        while (l <= r) {
            m = (l + r) / 2;
            if (check(arr, help, dp, sort[m], n)) {
                ans = sort[m];
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }

    private static boolean check(int[] arr, int[] help, int[][] dp, int x, int n) {
        for (int i = 0; i < n; i++) {
            help[i] = arr[i] >= x ? 1 : -1;
        }
        return dp(help, dp, n) > 0;
    }

    private static int dp(int[] arr, int[][] dp, int n) {
        // dp[i][0]: 表示i位置数可以选或者不选，子序列的最大累加和是多少,i...范围上形成合法子序列的最大累加和
        // 如果i不选 dp[i][0] = dp[i+1][1]
        // 如果i选择 dp[i][1] = arr[i] + dp[i+1][0]
        // 两种选择取最大
        // dp[i][1]: 表示i位置数需要选，子序列的最大累加和是多少,i...范围上形成合法子序列的最大累加和
        // i必须选 dp[i][1] = dp[i + 1][0] + arr[i]
        for (int i = n - 1; i >= 0; i--) {
            dp[i][0] = Math.max(dp[i + 1][1], dp[i + 1][0] + arr[i]);
            dp[i][1] = dp[i + 1][0] + arr[i];
        }
        return dp[0][0];
    }

    public static int right(int[] arr) {
        int[] path = new int[arr.length];
        return f(path, arr, 0, true, 0);
    }

    private static int f(int[] path, int[] arr, int i, boolean pre, int size) {
        if (i == arr.length) {
            if (size == 0) {
                return 0;
            }
            int[] sort = new int[size];
            for (int j = 0; j < size; j++) {
                sort[j] = path[j];
            }
            Arrays.sort(sort);
            return sort[(sort.length - 1) / 2];
        } else {
            path[size] = arr[i];
            int ans = f(path, arr, i + 1, true, size + 1);
            if (pre) {
                ans = Math.max(ans, f(path, arr, i + 1, false, size));
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int n = 20;
        int v = 100;
        int test = 10000;
        System.out.println("功能测试开始");
        for (int t = 0; t < test; t++) {
            int size = (int) (Math.random() * n) + 1;
            int[] arr = randomArr(size, v);
            int ans1 = maximizeMedian(arr);
            int ans2 = right(arr);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("功能测试结束");
        System.out.println();

        System.out.println("性能测试开始");
        n = 100000;
        v = 50000000;
        System.out.println("数组长度为：" + n + "，值的大小为：" + v);
        long start = System.currentTimeMillis();
        int[] arr = randomArr(n, v);
        int ans = maximizeMedian(arr);
        long end = System.currentTimeMillis();
        System.out.println("答案是："+ ans +",耗时：" + (end - start));
        System.out.println("性能测试结束");
    }

    private static int[] randomArr(int n, int v) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * v);
        }
        return arr;
    }
}
