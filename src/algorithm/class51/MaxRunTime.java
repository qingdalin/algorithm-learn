package algorithm.class51;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-04 20:21
 * https://leetcode.cn/problems/maximum-running-time-of-n-computers/
 * 二分法
 */
public class MaxRunTime {
    public long maxRunTime(int n, int[] arr) {
        long sum = 0L;
        int max = 0;
        for (int x : arr) {
            max = Math.max(max, x);
            sum += x;
        }
        if (sum >= (long) n * max) {
            // 如果电池累加和大于 max * 台数，说明都是碎片电池
            return sum / n;
        }
        long ans = 0L;
        // 如果到这说明最大运行时间不会超过max
        for (int l = 0, r = max, m; l <= r;) {
            m = l + ((r - l) >> 1);
            if (f(arr, n, m)) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }
    public boolean f(int[] arr, int n, int time ){
        long sum = 0L;
        for (int x : arr) {
            if (x > time) {
                n--;
            } else {
                sum += x;
            }
            if (sum >= (long) n * time) {
                return true;
            }
        }
        return false;
    }
}
