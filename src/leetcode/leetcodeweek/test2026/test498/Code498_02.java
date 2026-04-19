package leetcode.leetcodeweek.test2026.test498;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/19 9:55
 * https://leetcode.cn/contest/weekly-contest-498/problems/smallest-stable-index-ii/
 */
public class Code498_02 {
    public static int MAXN = 100001;
    public static int[] que = new int[MAXN];
    public static int firstStableIndex(int[] nums, int k) {
        int n = nums.length, l = 0, r = 0;
        for (int i = 0; i < n; i++) {
            while (r - l > 0 && nums[que[r - 1]] > nums[i]) {
                r--;
            }
            que[r++] = i;
        }
        int max = nums[0];
        for (int i = 0; i < n; i++) {
            max = Math.max(max, nums[i]);
            int cur = max - nums[que[l]];
            if (cur <= k) {
                return i;
            }
            if (i == que[l]) {
                l++;
            }
        }
        return -1;
    }
}
