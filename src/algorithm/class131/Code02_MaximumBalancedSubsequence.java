package algorithm.class131;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/10 15:32
 * // 平衡子序列的最大和
 * // 给定一个长度为n的数组nums，下面定义平衡子序列
 * // 如果下标i和下标j被选进了子序列，i在j的左边
 * // 那么必须有nums[j] - nums[i] >= j - i
 * // 如果一个子序列中任意的两个下标都满足上面的要求，那子序列就是平衡的
 * // 返回nums所有平衡子序列里，最大的累加和是多少
 * // 1 <= n <= 10^5
 * // -10^9 <= nums[i] <= +10^9
 * // 测试链接 : https://leetcode.cn/problems/maximum-balanced-subsequence-sum/
 */
public class Code02_MaximumBalancedSubsequence {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] sort = new int[MAXN];
    public static long[] tree = new long[MAXN];
    public static long maxBalancedSubsequenceSum(int[] nums) {
        build(nums);
        long pre = 0;
        for (int i = 0, k; i < n; i++) {
            // 找到指标值的下标
            k = rank(nums[i] - i);
            pre = max(k);
            if (pre < 0) {
                // 前边的指标小于0，单独把i位置指标更新
                update(k, nums[i]);
            } else {
                // 前边的指标大于等于0，把i位置指标加上前边的一起更新
                update(k, nums[i] + pre);
            }
        }
        // 返回m位置的最大指标
        return max(m);
    }

    private static void update(int i, long v) {
        while (i <= m) {
            tree[i] = Math.max(v, tree[i]);
            i += lowbit(i);
        }
    }

    private static long max(int i) {
        long ans = Long.MIN_VALUE;
        while (i > 0) {
            ans = Math.max(ans, tree[i]);
            i -= lowbit(i);
        }
        return ans;
    }

    private static int lowbit(int i) {
        return i & -i;
    }

    private static int rank(int v) {
        int l = 1, r = m, mid = 0;
        int ans = 0;
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
        // 离散化
        n = nums.length;
        for (int i = 1, j = 0; i <= n; i++, j++) {
            sort[i] = (nums[j] - j);
        }
        Arrays.sort(sort, 1, n + 1);
        m = 1;
        for (int i = 2; i <= n; i++) {
            if (sort[m] != sort[i]) {
                sort[++m] = sort[i];
            }
        }
        Arrays.fill(tree, 1, m + 1, Long.MIN_VALUE);
    }
}
