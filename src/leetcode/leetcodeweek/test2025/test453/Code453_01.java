package leetcode.leetcodeweek.test2025.test453;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/8 10:19
 * https://leetcode.cn/contest/weekly-contest-453/problems/transform-array-to-all-equal-elements/
 */
public class Code453_01 {
    public static boolean canMakeEqual(int[] nums, int k) {
        return check(nums, k, 1) || check(nums, k, -1);
    }

    public static boolean check(int[] arr, int k, int x) {
        int n = arr.length;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = arr[i];
        }
        int cnt = 0;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] != x) {
                nums[i] *= -1;
                nums[i + 1] *= -1;
                cnt++;
            }
        }
        return nums[n - 1] == x && cnt <= k;
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {1,-1,1,-1,1};
//        int k = 3;
        // [-1,1,1,1,-1,-1,-1,1,1]
        int[] arr = new int[] {-1,-1,-1,1,1,1};
        int k = 5;
        System.out.println(canMakeEqual(arr, k));
    }
}
