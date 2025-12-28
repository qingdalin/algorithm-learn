package leetcode.leetcodeweek.test2025.test461;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 7:58
 * https://leetcode.cn/contest/weekly-contest-461/problems/trionic-array-i/description/
 */
public class Code461_01 {
    public static boolean isTrionic(int[] nums) {
        int n = nums.length;
        int i = 1, p = -1, q = -1;
        while (i < n && nums[i] - nums[i - 1] > 0) {
            i++;
        }
        if (i < n && nums[i] - nums[i - 1] == 0) {
            return false;
        }
        if (i == 1) {
            return false;
        }
        p = i;
        while (i < n && nums[i] - nums[i - 1] < 0) {
            i++;
        }
        if (i < n && nums[i] - nums[i - 1] == 0) {
            return false;
        }
        if (i == p) {
            return false;
        }
        q = i;
        if (q >= n) {
            return false;
        }
        for (int j = q + 1; j < n; j++) {
            if (nums[j] - nums[j - 1] <= 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // int[] arr = new int[] {1,3,5,4,2,6};
//        int[] arr = new int[] {2, 1,3};
//        int[] arr = new int[] {1, 2,3};
        // int[] arr = new int[] {3, 7, 1};
        int[] arr = new int[] {6,8,2,5,9};
        System.out.println(isTrionic(arr));
    }
}
