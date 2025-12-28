package leetcode.study.leetcode001_200;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 20:16
 * https://leetcode.cn/problems/longest-consecutive-sequence/description/
 */
public class Leetcode128LongestConsecutive {
    public static int longestConsecutive(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int s = 0;
        for (int i = 1; i < n; i++) {
            if (nums[s] != nums[i]) {
                nums[++s] = nums[i];
            }
        }
        int ans = 0;
        for (int l = 0, r = 0; l <= s; l = ++r) {
            while (r + 1 <= s && nums[r] + 1 == nums[r + 1]) {
                r++;
            }
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = new int[]{0,3,7,2,5,8,4,6,0,1};
//        int[] arr = new int[]{100,4,200,1,3,2};
        int[] arr = new int[] {};
        System.out.println(longestConsecutive(arr));
    }
}
