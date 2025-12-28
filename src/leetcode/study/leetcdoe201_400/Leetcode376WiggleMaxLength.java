package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/30 19:43
 * https://leetcode.cn/problems/wiggle-subsequence/
 */
public class Leetcode376WiggleMaxLength {

    public static int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }
        int pre = nums[1] - nums[0];
        int ans = pre != 0 ? 2 : 1;
        for (int i = 2; i < n; i++) {
            int diff = nums[i] - nums[i - 1];
            if ((diff > 0 && pre <= 0) || (diff < 0 && pre >= 0)) {
                ans++;
                pre = diff;
            }
        }
        return ans;
    }



    public static int wiggleMaxLength1(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return nums[0] != nums[1] ? 2 : 1;
        }
        int[] diff = new int[n];
        for (int i = 1; i < n; i++) {
            int cur = nums[i] - nums[i - 1];
            if (cur > 0) {
                diff[i] = 1;
            } else if (cur < 0) {
                diff[i] = -1;
            }
        }
        int ans = Integer.MIN_VALUE;
        for (int r = 1, l = 1; l < n; l = ++r) {
            while (r + 1 < n && diff[r] + diff[r + 1] == 0) {
                r++;
            }
            ans = Math.max(ans, r - l + 1);
        }
        return ans + 1;
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {1,7,4,9,2,5};
        int[] arr = new int[] {1,17,5,10,13,15,10,5,16,8};
        System.out.println(wiggleMaxLength(arr));
    }
}
