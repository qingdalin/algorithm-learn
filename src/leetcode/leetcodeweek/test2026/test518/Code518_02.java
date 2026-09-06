package leetcode.leetcodeweek.test2026.test518;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/9/6 10:19
 * https://leetcode.cn/contest/weekly-contest-518/problems/count-good-cyclic-rotations/
 */
public class Code518_02 {
    public static int countGoodRotations(int[] nums) {
        long preSum = 0, sufSum = 0;
        int ans = 0;
        int n = nums.length;
        int half = n / 2;
        for (int i = 0, j = i + half; i < half; i++, j++) {
            preSum += nums[i];
            sufSum += nums[j];
        }
        if (preSum > sufSum) {
            ans++;
        }
        for (int i = 0; i < n - 1; i++) {
            int diff = nums[(i + half) % n] - nums[i];
            preSum += diff;
            sufSum -= diff;
            if (preSum > sufSum) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr ={1,2,3,4,5,6};
        System.out.println(countGoodRotations(arr));
    }
}
