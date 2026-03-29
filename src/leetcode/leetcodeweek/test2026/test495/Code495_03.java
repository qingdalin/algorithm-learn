package leetcode.leetcodeweek.test2026.test495;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/29 10:24
 * https://leetcode.cn/problems/sum-of-sortable-integers/solutions/3939560/yu-chu-li-mei-ju-yin-zi-pythonjavacgo-by-o0q9/
 */
public class Code495_03 {
    public static int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] nextDec = new int[n];
        nextDec[n - 1] = n;
        int p = n;
        // 对于每个 i，记录下一个递减的位置
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                p = i;
            }
            nextDec[i] = p;
        }
        int ans = 0;
        // 枚举k的因子
        for (int k = 1; k * k <= n; k++) {
            if (n % k == 0) {
                ans += solve(k, nums, nextDec);
                if (k * k < n) {
                    ans += solve(n / k, nums, nextDec);
                }
            }
        }
        return ans;
    }

    private static int solve(int k, int[] nums, int[] nextDec) {
        // 上一段的最大值
        int lastMax = 0;
        for (int r = k - 1; r < nums.length; r += k) {
            int l = r - k + 1;
            int m = nextDec[l];
            if (m >= r) {
                // [l, r] 是递增的，最小值为 nums[l]，最大值为 nums[r]
                // nums[l] 必须 >= 上一段的最大值
                if (nums[l] < lastMax) {
                    return 0;
                }
                lastMax = nums[r];
            } else {
                // [l, m] 是第一段，[m+1, r] 是第二段
                // 第二段必须是递增的，且第二段的最小值 >= 上一段的最大值，且第二段最大值 <= 第一段的最大值
                if (nextDec[m + 1] < r || nums[m + 1] < lastMax || nums[r] > nums[l]) {
                    return 0;
                }
                lastMax = nums[m];
            }
        }
        return k;
    }
}
