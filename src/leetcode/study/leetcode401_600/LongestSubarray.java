package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/24 17:50
 * https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/?envType=daily-question&envId=2025-08-24
 */
public class LongestSubarray {
    public static int longestSubarray(int[] nums) {
        int n = nums.length;
        int ans = 0, zeroCnt = 0, pre = 0;
        for (int l = 0, r = 0; r < n; l = pre) {
            zeroCnt = 0;
            while (r < n && (zeroCnt < 1 || nums[r] == 1)) {
                if (nums[r] == 0) {
                    pre = r + 1;
                    zeroCnt++;
                }
                r++;
            }
            if (r - l - 1 >  ans) {
                ans = r - l - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = {0,1,1,1,0,1,1,0,1};
        int[] arr = {1,1,1};
        System.out.println(longestSubarray(arr));
    }
}
