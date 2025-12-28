package leetcode.leetcodeweek.test2025.test461;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 7:58
 * https://leetcode.cn/contest/weekly-contest-461/problems/trionic-array-ii/description/
 */
public class Code461_04 {
    public static long maxSumTrionic(int[] nums) {
        long ans = Long.MIN_VALUE;
        int n = nums.length;
        for (int i = 0; i < n;) {
            i++;
            if (nums[i - 1] >= nums[i]) {
                continue;
            }
            int start = i - 1;
            while (i < n && nums[i - 1] < nums[i]) {
                i++;
            }
            if (i == n || nums[i - 1] <= nums[i]) {
                continue;
            }
            int peak = i - 1;
            long res = nums[peak - 1] + nums[peak];
            while (i < n && nums[i - 1] > nums[i]) {
                res += nums[i];
                i++;
            }
            if (i == n || nums[i - 1] >= nums[i]) {
                continue;
            }
            int bottom = i - 1;
            res += nums[i];
            while (i < n && nums[i - 1] < nums[i]) {
                i++;
            }
            long maxSum = 0;
            long sum = 0;
            for (int j = peak - 2; j >= start; j--) {
                sum += nums[j];
                maxSum = Math.max(maxSum, sum);
            }
            res += maxSum;
            maxSum = sum = 0;
            for (int j = bottom + 2; j < i; j++) {
                sum += nums[j];
                maxSum = Math.max(maxSum, sum);
            }
            res += maxSum;
            ans = Math.max(ans, res);
            i = bottom;
        }
        return ans;
    }

    public static long maxSumTrionic1(int[] nums) {
        long ans = Long.MIN_VALUE;
        int n = nums.length;
        long[] preSum = new long[n];
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            preSum[i] = sum;
        }
        for (int l = 0, r = 1, p = -1, q = -1; r < n; r++) {
            while (r < n && nums[r] - nums[r - 1] > 0) {
                r++;
            }
            if (r < n && nums[r] - nums[r - 1] == 0 || r == 1) {
                l = r;
                continue;
            }
            p = r;
            while (r < n && nums[r] - nums[r - 1] < 0) {
                r++;
            }
            if (r < n && nums[r] - nums[r - 1] == 0 || r == p) {
                l = r;
                continue;
            }
            q = r;
            if (q >= n) {
                l = r;
                continue;
            }
            long cursum = nums[r];
            while (r + 1 < n && nums[r + 1] - nums[r] > 0) {
                ans = Math.max(ans, query(l, r, preSum));
                r++;
            }
            ans = Math.max(ans, query(l, r, preSum));
            l = q;
        }
        return ans;
    }

    public static long query(int l, int r, long[] preSum) {
        if (l == 0) {
            return preSum[r];
        }
        return preSum[r] - preSum[l - 1];
    }

    public static void main(String[] args) {
        // int[] arr = new int[] {1,3,5,4,2,6};
//        int[] arr = new int[] {2, 1,3};
//        int[] arr = new int[] {1, 2,3};
        // int[] arr = new int[] {3, 7, 1};
//         int[] arr = new int[] {1,4,2,7};
//        int[] arr = new int[] {0,-2,-1,-3,0,2,-1};
//        int[] arr = new int[] {2,993,-791,-635,-569};
//        int[] arr = new int[] {395,731,-892,-619,-238,634};
//        int[] arr = new int[] {-222,209,-246,680,-662,412};
        int[] arr = new int[] {-533,224,-324,251,231,479};
        System.out.println(maxSumTrionic(arr));
    }
}
