package leetcode.leetcodeweek.test2025.test453;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/8 10:20
 * https://leetcode.cn/contest/weekly-contest-453/problems/count-partitions-with-max-min-difference-at-most-k/
 */
public class Code453_03 {
    public static int MOD = 1000000007;
    public static int countPartitions(int[] nums, int k) {
        int n = nums.length;
        int ans = f(nums, n, k, 0, nums[0], nums[0]);
        return ans;
    }

    private static int f(int[] nums, int n, int k, int i, int max, int min) {
        if (max - min > k) {
            return 0;
        }
        if (i == n) {
            return 1;
        }
        if (nums[i] >= k) {
            return f(nums, n, k, i + 1, nums[i], nums[i]);
        }
        long ans = 0;
        for (int j = i; j < n; j++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
            if (max - min > k) {
                break;
            }
            ans = (ans + f(nums, n, k, i + 1, max, min)) % MOD;
        }
        return (int) ans;
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {9,4,1,3,7};
//        int k = 4;
        int[] arr = new int[] {3,3,4};
        int k = 0;
        System.out.println(countPartitions(arr, k));
    }
}
