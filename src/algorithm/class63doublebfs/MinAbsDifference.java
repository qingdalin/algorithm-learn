package algorithm.class63doublebfs;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-14 15:22
 * 给你一个整数数组 nums 和一个目标值 goal 。
 *
 * 你需要从 nums 中选出一个子序列，使子序列元素总和最接近 goal 。也就是说，如果子序列元素和为 sum ，你需要 最小化绝对差 abs(sum - goal) 。
 *
 * 返回 abs(sum - goal) 可能的 最小值 。
 *
 * 注意，数组的子序列是通过移除原始数组中的某些元素（可能全部或无）而形成的数组。
 * https://leetcode.cn/problems/closest-subsequence-sum/description/
 */
public class MinAbsDifference {
    public static int MAXN = 1 << 20;
    public static int[] lsum = new int[MAXN];
    public static int[] rsum = new int[MAXN];
    public static int fill;
    public int minAbsDifference(int[] nums, int goal) {
        long max = 0;
        long min = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] >= 0) {
                max += nums[i];
            } else {
                min += nums[i];
            }
        }
        if (max < goal) {
            return (int) Math.abs(goal - max);
        }
        if (min > goal) {
            return (int) Math.abs(goal - min);
        }
        Arrays.sort(nums);
        fill = 0;
        collect(nums, 0, n >> 1, 0, lsum);
        int lsize = fill;
        fill = 0;
        collect(nums, n >> 1, n, 0, rsum);
        int rsize = fill;
        Arrays.sort(lsum, 0, lsize);
        Arrays.sort(rsum, 0, rsize);
        int ans = Math.abs(goal);
        for (int l = 0, r = rsize - 1; l < lsize; l++) {
            while (r > 0 && Math.abs(goal - (lsum[l] + rsum[r - 1])) <= Math.abs(goal - (lsum[l] + rsum[r]))) {
                r--;
            }
            ans = Math.min(ans, Math.abs(goal - (lsum[l] + rsum[r])));
        }
        return ans;
    }

    public static void collect(int[] nums, int i, int e, int s, int[] sum) {
        if (i == e) {
            sum[fill++] = s;
        } else {
            // 不剪枝暴力递归
            collect(nums, i + 1, e, s, sum);
            collect(nums, i + 1, e, s + nums[i], sum);
        }
    }

    public static void collect1(int[] nums, int i, int e, int s, int[] sum) {
        if (i == e) {
            sum[fill++] = s;
        } else {
            // 剪枝
            int j = i + 1;
            while (j < e && nums[j] == nums[i]) {
                j++;
            }
            for (int k = 0; k <= j - i; k++) {
                collect1(nums, j, e, s + k * nums[i], sum);
            }
        }
    }
}
