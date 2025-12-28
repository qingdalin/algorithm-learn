package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/30 19:20
 * https://leetcode.cn/problems/longest-subarray-with-maximum-bitwise-and/?envType=daily-question&envId=2025-07-30
 */
public class LongestSubarray {
    public static int longestSubarray(int[] nums) {
        int n = nums.length;
        int ans = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, nums[i]);
        }
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == max) {
                cnt++;
                ans = Math.max(ans, cnt);
            } else {
                cnt = 0;
            }
        }
        return ans;
    }

    public static int longestSubarray1(int[] nums) {
        int n = nums.length;
        int ans = 1;
        int max = Integer.MIN_VALUE;
        for (int l = 0, r = 0; l < n; l = ++r) {
            while (r + 1 < n && nums[r] == nums[r + 1]) {
                r++;
            }
            if (nums[r] > max) {
                max = nums[r];
                ans = r - l + 1;
            } else if (nums[r] == max) {
                ans = Math.max(ans, r - l + 1);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        //int[] arr = new int[] {100, 5, 5};
        int[] arr = new int[] {96317,96317,96317,96317,96317,96317,96317,96317,96317,279979};
        System.out.println(longestSubarray(arr));
    }
}
