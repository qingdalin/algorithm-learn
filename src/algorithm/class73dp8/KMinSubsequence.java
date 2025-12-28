package algorithm.class73dp8;

import algorithm.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-16 9:14
 * 非负数组前k个最小的子序列累加和
 * 给定一个数组nums，含有n个数组，都是非负
 * 给定一个整数k，返回所有子序列中累加和最小的前k个累加和
 * 子序列是包含空集的
 * 1 <= n <= 10^5
 * 1 <= nums[i] <= 10^6
 * 1 <= k <= 10^5
 * 用01背包解法时间复杂度太高
 */
public class KMinSubsequence {
    // 暴力方法
    public static int[] topSubSum1(int[] nums, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        f1(nums, 0, 0, ans);
        ans.sort((a, b) -> a - b);
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = ans.get(i);
        }
        return res;
    }

    private static void f1(int[] nums, int i, int sum, ArrayList<Integer> ans) {
        // 当前i位置的累加和是sum
        if (i == nums.length) {
            ans.add(sum);
        } else {
            f1(nums, i + 1, sum, ans);
            f1(nums, i + 1, sum + nums[i], ans);
        }
    }

    // 01背包
    public static int[] topSubSum2(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int[] dp = new int[sum + 1];
        // dp[i][j] = 当前i位置的子序列和是j
        // 不要i位置；dp[i][j] = dp[i - 1][j]
        // 要i位置；dp[i][j] = dp[i - 1][j - nums[i]]
        dp[0] = 1;
        for (int num : nums) {
            for (int j = sum; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        int[] ans = new int[k];
        int index = 0;
        for (int j = 0; j <= sum && index < k; j++) {
            for (int i = 0; i < dp[j] && index < k; i++) {
                ans[index++] = j;
            }
        }
        return ans;
    }

    // 堆结构最优解
    public static int[] topSubSum3(int[] nums, int k) {
        Arrays.sort(nums);
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        int[] ans = new int[k];
        // 最右下标和子序列和
        heap.add(new int[] {0, nums[0]});
        for (int i = 1; i < k; i++) {
            int[] record = heap.poll();
            int right = record[0];
            int sum = record[1];
            ans[i] = sum;
            if (right + 1 < nums.length) {
                // 减去当前的和，把下一个放进来
                heap.add(new int[] {right + 1, sum - nums[right] + nums[right + 1]});
                // 不减去当前的，直接放入下一个
                heap.add(new int[] {right + 1, sum + nums[right + 1]});
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 15;
        int v = 40;
        int t = 1000;
        System.out.println("开始测试");
        for (int i = 0; i < t; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] nums = ArrayUtil.randomPositiveNumArray(len, v);
            int k = (int) (Math.random() * ((1 << len) - 1)) + 1;
            int[] ans1 = topSubSum1(nums, k);
            int[] ans2 = topSubSum2(nums, k);
            int[] ans3 = topSubSum3(nums, k);
            if (!equals(ans1, ans2) || !equals(ans2, ans3)) {
                System.out.println("出错了");
            }

        }
        System.out.println("结束测试");
    }

    private static boolean equals(int[] ans1, int[] ans2) {
        if (ans1.length != ans2.length) {
            return false;
        }
        for (int i = 0; i < ans1.length; i++) {
            if (ans1[i] != ans2[i]) {
                return false;
            }
        }
        return true;
    }

}
