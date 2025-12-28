package algorithm.class51;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-04 19:34
 * https://leetcode.cn/problems/find-k-th-smallest-pair-distance/
 */
public class SmallestDistancePair {
    public int smallestDistancePair(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int ans = 0;
        for (int l = 0, r = nums[n - 1] - nums[0], m, cnt; l <= r;) {
            m = (r + l) / 2;
            if (f(nums, m) >= k) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }
    public int f(int[] nums, int limit) {
        int cnt = 0;
        for (int l = 0, r = 0; l < nums.length; l++) {
            while (r + 1 < nums.length && nums[r + 1] - nums[l] <= limit) {
                r++;
            }
            cnt += r - l;
        }
        return cnt;
    }
}
