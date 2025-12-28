package algorithm.class71dp6;

import algorithm.ArrayUtil;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-01 16:12
 * // 删掉1个数字后长度为k的子数组最大累加和
 * // 给定一个数组nums，求必须删除一个数字后的新数组中
 * // 长度为k的子数组最大累加和，删除哪个数字随意
 * // 对数器验证
 */
public class DeleteOneNumberLengthKMaxSum {
    public static int deleteOneNumberLengthKMaxSum1(int[] nums, int k) {
        int n = nums.length;
        if (n <= k) {
            return 0;
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int[] tmp = randomDelete(nums, i);
            ans = Math.max(ans, maxSum(tmp, k));
        }
        return ans;
    }

    private static int maxSum(int[] tmp, int k) {
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i <= tmp.length - k; i++) {
            int curMax = 0;
            for (int j = i, cnt = 0; cnt < k; j++, cnt++) {
                curMax += tmp[j];
            }
            ans = Math.max(ans, curMax);
        }
        return ans;
    }

    private static int[] randomDelete(int[] nums, int index) {
        int n = nums.length;
        int[] ans = new int[n - 1];
        int i = 0;
        for (int j = 0; j < n; j++) {
            if (j != index) {
                ans[i++] = nums[j];
            }
        }
        return ans;
    }

    public static int deleteOneNumberLengthKMaxSum2(int[] nums, int k) {
        int n = nums.length;
        if (n <= k) {
            return 0;
        }
        int[] window = new int[n];
        int l = 0, r = 0, sum = 0;
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            while (l < r && nums[window[r - 1]] >= nums[i]) {
                // 单调队列，维持窗口的最小值下标
                r--;
            }
            window[r++] = i;
            sum += nums[i];
            if (i >= k) {
                ans = Math.max(ans, sum - nums[window[l]]);
                // 如果l过期
                if (i - k == window[l]) {
                    l++;
                }
                sum -= nums[i - k];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 200;
        int v = 1000;
        int t = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < t; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] nums = ArrayUtil.randomPositiveAndNegativeNumArray(len, v);
            int k = (int) (Math.random() * n) + 1;
            int ans1 = deleteOneNumberLengthKMaxSum1(nums, k);
            int ans2 = deleteOneNumberLengthKMaxSum2(nums, k);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }
}
