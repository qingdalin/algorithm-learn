package leetcode.leetcodeweek.test2026.test504;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/31 10:10
 * https://leetcode.cn/problems/lexicographically-maximum-mex-array/description/
 */
public class Code504_04 {
    public int[] maximumMEX(int[] nums) {
        int n = nums.length;
        ArrayDeque<Integer>[] pos = new ArrayDeque[n + 1];
        Arrays.setAll(pos, a -> new ArrayDeque<>());
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (x < n) {
                pos[x].addLast(i);
            }
        }
        int idx = 0;
        for (int i = 0; i < n; i++) {
            int start = i;
            int mex = 0;
            for (; ; mex++) {
                while (!pos[mex].isEmpty() && pos[mex].peekFirst() < start) {
                    pos[mex].pollFirst();
                }
                if (pos[mex].isEmpty()) {
                    break;
                }
                i = Math.max(i, pos[mex].peekFirst());
            }
            nums[idx++] = mex;
        }
        return Arrays.copyOf(nums, idx);
    }
}
