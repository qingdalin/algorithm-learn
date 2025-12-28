package leetcode.leetcodeweek.test2025.test450;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/18 9:16
 * https://leetcode.cn/contest/weekly-contest-450/problems/smallest-index-with-digit-sum-equal-to-index/description/
 */
public class Code01 {
    public static int smallestIndex(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (check(nums[i], i)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean check(int num, int i) {
        int sum = 0;
        int len = 1;
        int offset = 1;
        int tmp = num / 10;
        while (tmp > 0) {
            len++;
            offset *= 10;
            tmp /= 10;
        }
        while (len > 0) {
            int cur = num / offset % 10;
            offset /= 10;
            len--;
            sum += cur;
        }
        return sum == i;
    }

    public static void main(String[] args) {
        //int[] arr = new int[] {1,2,3};
        int[] arr = new int[] {234};
        System.out.println(smallestIndex(arr));
    }
}
