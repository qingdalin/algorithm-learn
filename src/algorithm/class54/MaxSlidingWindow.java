package algorithm.class54;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-11 20:00
 * https://leetcode.cn/problems/sliding-window-maximum/
 * 滑动窗口的最大值：单调队列，最大值
 */
public class MaxSlidingWindow {
    public static int h, t;
    public static int[] deque = new int[100000];
    public int[] maxSlidingWindow(int[] nums, int k) {
        h = t = 0;
        int n = nums.length;
        for (int i = 0; i < k - 1; i++) {
            // 先让k - 1个数进窗口
            // h -> t
            // 大 -> 小
            while (h < t && nums[deque[t - 1]] <= nums[i]) {
                // 如果当前数大于等于队列尾，则尾部数弹出
                t--;
            }
            // 如果当前数小于尾部数直接加入队列
            deque[t++] = i;
        }
        int m = n - k + 1;
        int[] ans = new int[m];
        for (int l = 0, r = k - 1; l < m; l++, r++) {
            // r位置数进队列
            while (h < t && nums[deque[t - 1]] <= nums[r]) {
                t--;
            }
            // 放入r位置数
            deque[t++] = r;
            // 收集答案
            ans[l] = nums[deque[h]];
            // l位置数弹出
            if (deque[h] == l) {
                h++;
            }
        }
        return ans;
    }
}
