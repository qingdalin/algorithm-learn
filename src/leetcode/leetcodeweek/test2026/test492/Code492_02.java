package leetcode.leetcodeweek.test2026.test492;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/8 10:39
 * https://leetcode.cn/contest/weekly-contest-492/problems/find-the-smallest-balanced-index/
 */
public class Code492_02 {
    public static int smallestBalancedIndex(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return -1;
        }
        long[] preSum = new long[n];
        long[] sufMul = new long[n];
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            preSum[i] = sum;
        }
        long mul = 1;
        for (int i = n - 1; i >= 0; i--) {
            mul *= nums[i];
            if (mul <= 0) {
                sufMul[i] = Long.MAX_VALUE;
            } else {
                sufMul[i] = mul;
            }
        }
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                if (sufMul[i + 1] == 0) {
                    return i;
                }
            } else if (i == n - 1) {
                if (preSum[i - 1] == 1) {
                    return i;
                }
            } else {
                if (preSum[i - 1] == sufMul[i + 1]) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        //int[] arr = {2,8,2,2,5};
        int[] arr = {999,818,984,995,841,822,984,978,960,997,896,926,759,961,1000,562,1,1,1,87,4,1,40};
        System.out.println(smallestBalancedIndex(arr));
    }
}
