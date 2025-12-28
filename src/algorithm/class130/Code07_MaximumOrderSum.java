package algorithm.class130;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/9 13:46
 * // 子数组最大变序和
 * // 给定一个长度为n的数组arr，变序和定义如下
 * // 数组中每个值都可以减小或者不变，必须把整体变成严格升序的
 * // 所有方案中，能得到的最大累加和，叫做数组的变序和
 * // 比如[1,100,7]，变序和14，方案为变成[1,6,7]
 * // 比如[5,4,9]，变序和16，方案为变成[3,4,9]
 * // 比如[1,4,2]，变序和3，方案为变成[0,1,2]
 * // 返回arr所有子数组的变序和中，最大的那个
 * // 1 <= n、arr[i] <= 10^6
 * // 来自真实大厂笔试，对数器验证
 */
public class Code07_MaximumOrderSum {
    public static long maxSum1(int[] arr) {
        int n = arr.length;
        int max = 0;
        for (int num : arr) {
            max = Math.max(max, num);
        }
        long[][] dp = new long[n][max + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= max; j++) {
                dp[i][j] = -1;
            }
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, f1(arr, i, arr[i], dp));
        }
        return ans;
    }

    private static long f1(int[] arr, int i, int p, long[][] dp) {
        // 子数字一定以i结尾的情况下，变成的数字不大于p，最大累加和是多少
        if (p <= 0 || i < 0) {
            return 0;
        }
        if (dp[i][p] != -1) {
            return dp[i][p];
        }
        int cur = Math.min(arr[i], p);
        long next = f1(arr, i - 1, cur - 1, dp);
        long ans = (long) cur + next;
        dp[i][p] = ans;
        return ans;
    }

    public static long maxSum2(int[] arr) {
        int n = arr.length;
        int[] stack = new int[n];
        int size = 0;
        long[] dp = new long[n];
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int curIdx = i;
            int curVal = arr[curIdx];
            while (curIdx > 0 && size > 0) {
                int topInx = stack[size - 1];
                int topVal = arr[topInx];
                if (topVal >= curVal) {
                    // 1、栈顶元素值大于等于当前元素值
                    size--;
                } else {
                    int indDiff = curIdx - topInx;
                    int valDiff = curVal - topVal;
                    // 值差大于等于位差
                    if (valDiff >= indDiff) {
                        dp[i] += sum(curVal, indDiff) + dp[topInx];
                        curVal = 0;
                        curIdx = 0;
                        break;
                    } else {
                        dp[i] += sum(curVal, indDiff);
                        curVal -= indDiff;
                        curIdx = topInx;
                        size--;
                    }
                }
            }
            if (curVal > 0) {
                dp[i] += sum(curVal, curIdx + 1);
            }
            stack[size++] = i;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    private static long sum(int max, int n) {
        n = Math.min(max, n);
        return ((long) (max + max - n + 1) * n )/ 2;
    }

    public static void main(String[] args) {
        int n = 100;
        int v = 100;
        int test = 10000;
        System.out.println("功能测试开始");
        for (int t = 0; t < test; t++) {
            int size = (int) (Math.random() * n) + 1;
            int[] arr = randomArr(size, v);
            long ans1 = maxSum1(arr);
            long ans2 = maxSum2(arr);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("功能测试结束");
        System.out.println();
        System.out.println("性能测试开始");
        n = 1000000;
        v = 1000000;
        System.out.println("数组长度位" + n + "，数组最大值" + v);
        int[] arr = randomArr(n, v);
        long start = System.currentTimeMillis();
        long ans = maxSum2(arr);
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "毫秒, 结果是：" + ans);
        System.out.println("性能测试结束");
    }

    private static int[] randomArr(int n, int v) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int)(Math.random() * v) + 1;
        }
        return arr;
    }
}
