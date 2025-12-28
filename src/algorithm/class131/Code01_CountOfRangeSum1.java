package algorithm.class131;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/10 10:45
 * // 达标子数组的个数
 * // 给定一个长度为n的数组nums，给定两个整数lower和upper
 * // 子数组达标的条件是累加和在[lower, upper]范围上
 * // 返回nums中有多少个达标子数组
 * // 1 <= n <= 10^5
 * // nums[i]可能是任意整数
 * // -10^5 <= lower <= upper <= +10^5
 * // 测试链接 : https://leetcode.cn/problems/count-of-range-sum/
 * 公司oj写过一样的，没过所有用例
 */
public class Code01_CountOfRangeSum1 {
    public static int MAXN = 100001;
    public static long[] sum = new long[MAXN];
    public static long[] help = new long[MAXN];
    public static int up, low;
    public static int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;
        sum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        up = upper;
        low = lower;
        return f(0, n - 1);
    }

    private static int f(int l, int r) {
        if (l == r) {
            // 只有一个元素，判断一下
            return low <= sum[l] && sum[l] <= up ? 1 : 0;
        }
        int m = (l + r) / 2;
        return f(l, m) + f(m + 1, r) + merge(l, m, r);
    }

    private static int merge(int l, int m, int r) {
        int ans = 0;
        int wl = l, wr = l;
        long max, min;
        for (int i = m + 1; i <= r; i++) {
            // 前缀和右多少落在 sum-low --- sum-up
            max = sum[i] - low;
            min = sum[i] - up;
            // [wl,wr) 左闭右开
            while (wr <= m && sum[wr] <= max) {
                wr++;
            }
            while (wl <= m && sum[wl] < min) {
                wl++;
            }
            ans += (wr - wl);
        }
        int a = l;
        int b = m + 1;
        int i = l;
        while (a <= m && b <= r) {
            help[i++] = sum[a] <= sum[b] ? sum[a++] : sum[b++];
        }
        while (a <= m) {
            help[i++] = sum[a++];
        }
        while (b <= r) {
            help[i++] = sum[b++];
        }
        for (i = l; i <= r; i++) {
            sum[i] = help[i];
        }
        return ans;
    }
}
