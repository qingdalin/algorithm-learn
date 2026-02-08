package leetcode.leetcodeweek.test2026.test488;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/2/8 10:35
 * https://leetcode.cn/problems/count-subarrays-with-cost-less-than-or-equal-to-k/solutions/3898726/on-zuo-fa-dan-diao-dui-lie-yue-duan-yue-l09a0/
 */
public class Code488_03 {
    public long countSubarrays(int[] nums, long k) {
        Deque<Integer> min = new ArrayDeque<>();
        Deque<Integer> max = new ArrayDeque<>();
        long ans = 0;
        int l = 0, n = nums.length;
        for (int r = 0; r < n; r++) {
            int x = nums[r];
            while (!min.isEmpty() && x <= nums[min.peekLast()]) {
                min.pollLast();
            }
            min.addLast(r);
            while (!max.isEmpty() && x >= nums[max.peekLast()]) {
                max.pollLast();
            }
            max.addLast(r);
            while ((long) (nums[max.peekFirst()] - nums[min.peekFirst()]) * (r - l + 1) > k) {
                l++;
                if (min.peekFirst() < l) {
                    min.pollFirst();
                }
                if (max.peekFirst() < l) {
                    max.pollFirst();
                }
            }
            ans += (r - l + 1);
        }
        return ans;
    }
}
