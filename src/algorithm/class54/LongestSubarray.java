package algorithm.class54;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-15 20:18
 * https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
 * 1438. 绝对差不超过限制的最长连续子数组
 */
public class LongestSubarray {
    public static int MAXN = 100000;
    public static int[] maxQueue = new int[MAXN];
    public static int[] minQueue = new int[MAXN];
    public static int maxh, maxt, minh, mint;
    public static int[] arr;
    public int longestSubarray(int[] nums, int limit) {
        maxh = maxt = minh = mint = 0;
        int n = nums.length;
        arr = nums;
        int ans = 0;
        for (int l = 0, r = 0; l < n; l++) {
            while (r < n && ok(limit, arr[r])){
                push(r++);
            }
            ans = Math.max(ans, r - l);
            pop(l);
        }
        return ans;
    }
    public static boolean ok(int limit, int num) {
        // r位置数进来最大值
        int max = maxh < maxt ? Math.max(arr[maxQueue[maxh]], num) : num;
        // r位置数进来最小值
        int min = minh < mint ? Math.min(arr[minQueue[minh]], num) : num;
        return max - min <= limit;
    }
    public static void push(int r) {
        while (maxh < maxt && arr[maxQueue[maxt - 1]] <= arr[r]) {
            maxt--;
        }
        maxQueue[maxt++] = r;
        while (minh < mint && arr[minQueue[mint - 1]] >= arr[r]) {
            mint--;
        }
        minQueue[mint++] = r;
    }
    public static void pop(int l) {
        if (maxh < maxt && maxQueue[maxh] == l) {
            maxh++;
        }
        if (minh < mint && minQueue[minh] == l) {
            minh++;
        }
    }
}
