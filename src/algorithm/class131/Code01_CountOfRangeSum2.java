package algorithm.class131;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/10 11:25
 * // 达标子数组的个数
 * // 给定一个长度为n的数组nums，给定两个整数lower和upper
 * // 子数组达标的条件是累加和在[lower, upper]范围上
 * // 返回nums中有多少个达标子数组
 * // 1 <= n <= 10^5
 * // nums[i]可能是任意整数
 * // -10^5 <= lower <= upper <= +10^5
 * // 测试链接 : https://leetcode.cn/problems/count-of-range-sum/
 */
public class Code01_CountOfRangeSum2 {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] tree = new int[MAXN];
    public static long[] sort = new long[MAXN];
    public static int countRangeSum(int[] nums, int lower, int upper) {
        // 数组去重
        build(nums);
        int ans = 0;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            ans += sum(rank(sum - lower)) - sum(rank(sum - upper - 1));
            // 判断sum本身是否合规
            if (lower <= sum && sum <= upper) {
                ans++;
            }
            add(rank(sum), 1);
        }
        return ans;
    }

    private static void add(int i, int c) {
        while (i <= m) {
            tree[i] += c;
            i += lowbit(i);
        }
    }

    private static int sum(int i) {
        int ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowbit(i);
        }
        return ans;
    }

    private static int lowbit(int i) {
        return i & -i;
    }

    private static int rank(long v) {
        int l = 1, r = m, mid = 0;
        int ans = 0;
        // 二分查询小于等于v的最右位置
        while (l <= r) {
            mid = (l + r) / 2;
            if (sort[mid] <= v) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    private static void build(int[] nums) {
        n = nums.length;
        for (int i = 1, j = 0; i <= n; i++, j++) {
            sort[i] = sort[i - 1] + nums[j];
        }
        Arrays.sort(sort, 1, n + 1);
        m = 1;
        for (int i = 2; i <= n; i++) {
            if (sort[m] != sort[i]) {
                sort[++m] = sort[i];
            }
        }
        Arrays.fill(tree, 1, m + 1, 0);
    }
}
