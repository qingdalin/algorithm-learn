package algorithm.class72dp7;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-02 10:48
 * 给你一个下标从 0 开始包含 n 个正整数的数组 arr ，和一个正整数 k 。
 *
 * 如果对于每个满足 k <= i <= n-1 的下标 i ，都有 arr[i-k] <= arr[i] ，那么我们称 arr 是 K 递增 的。
 *
 * 比方说，arr = [4, 1, 5, 2, 6, 2] 对于 k = 2 是 K 递增的，因为：
 * arr[0] <= arr[2] (4 <= 5)
 * arr[1] <= arr[3] (1 <= 2)
 * arr[2] <= arr[4] (5 <= 6)
 * arr[3] <= arr[5] (2 <= 2)
 * 但是，相同的数组 arr 对于 k = 1 不是 K 递增的（因为 arr[0] > arr[1]），对于 k = 3 也不是 K 递增的（因为 arr[0] > arr[3] ）。
 * 每一次 操作 中，你可以选择一个下标 i 并将 arr[i] 改成任意 正整数。
 *
 * 请你返回对于给定的 k ，使数组变成 K 递增的 最少操作次数 。
 * https://leetcode.cn/problems/minimum-operations-to-make-the-array-k-increasing/description/
 */
public class KIncreasing {
    public static int MAXN = 100001;
    public static int[] ends = new int[MAXN];
    public static int[] nums = new int[MAXN];
    public int kIncreasing(int[] arr, int k) {
        int n = arr.length;
        int ans = 0;
        for (int i = 0; i < k; i++) {
            int size = 0;
            for (int j = i; j < n; j += k) {
                nums[size++] = arr[j];
            }
            ans += size - maxLongSubNoDecreasing(size);
        }
        return ans;
    }

    // 最长不下降子序列
    private int maxLongSubNoDecreasing(int size) {
        int len = 0;
        int find = 0;
        for (int i = 0; i < size; i++) {
            find = bs(len, nums[i]);
            if (find == -1) {
                ends[len++] = nums[i];
            } else {
                ends[find] = nums[i];
            }
        }
        return len;
    }

    private int bs(int len, int num) {
        int l = 0, r = len - 1, m = 0, ans = -1;
        while (l <= r) {
            m = (l + r) / 2;
            // 保证不下降
            if (num < ends[m]) {
              ans = m;
              r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }
}
