package algorithm.class132;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/17 8:30
 * // 休息k分钟最大会议和
 * // 给定一个长度为n的数组arr，表示从早到晚发生的会议，各自召开的分钟数
 * // 当选择一个会议并参加之后，必须休息k分钟
 * // 返回能参加的会议时长最大累加和
 * // 比如，arr = { 200, 5, 6, 14, 7, 300 }，k = 15
 * // 最好的选择为，选择200分钟的会议，然后必须休息15分钟
 * // 那么接下来的5分钟、6分钟、14分钟的会议注定错过
 * // 然后放弃7分钟的会议，而选择参加300分钟的会议
 * // 最终返回500
 * // 1 <= n、arr[i]、k <= 10^6
 * // 来自真实大厂笔试，对数器验证
 */
public class Code01_MeetingRestK {
    // 不减少枚举，时间复杂度n^2
    public static long best1(int[] arr, int k) {
        int n = arr.length;
        // dp[i]:表示从i。。。。后面，不违反规则的情况下，最大的会议时间
        long[] dp = new long[n + 1];
        for (int i = n - 1, j, sum; i >= 0; i--) {
            for (j = i + 1, sum = 0; j < n && sum < k; j++) {
                sum += arr[j];
            }
            // 不要当前i位置和要i位置的会议，就得找到上一个会议的最大值，pk最大值
            dp[i] = Math.max(dp[i + 1], dp[j] + arr[i]);
        }
        return dp[0];
    }

    public static long best2(int[] arr, int k) {
        int n = arr.length;
        int[] jump = new int[n + 1];
        for (int i = 0, l = 1, r = 1, sum = 0; i < n - 1; i++, l++) {
            while (r < n && sum < k) {
                sum += arr[r++];
            }
            jump[i] = r;
            sum -= arr[l];
        }
        // 补一个位置
        jump[n - 1] = n;
        long[] dp = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = Math.max(dp[i + 1], dp[jump[i]] + arr[i]);
        }
        return dp[0];
     }

    public static void main(String[] args) {
        int n = 1000;
        int v = 3000;
        int test = 10000;
        System.out.println("测试开始");
        for (int t = 0; t < test; t++) {
            int size = (int) (Math.random() * n) + 1;
            int[] arr = randomArr(size, v);
            int k = (int) (Math.random() * v);
            long ans1 = best1(arr, k);
            long ans2 = best2(arr, k);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }

    private static int[] randomArr(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * v);
        }
        return ans;
    }
}
