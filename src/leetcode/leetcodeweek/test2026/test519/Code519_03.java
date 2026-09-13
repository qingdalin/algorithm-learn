package leetcode.leetcodeweek.test2026.test519;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/9/13 10:43
 * https://leetcode.cn/problems/count-shadow-pairs-i/solutions/4027853/mei-ju-you-wei-hu-zuo-dan-diao-zhan-pyth-hmvp/
 */
public class Code519_03 {
    public long shadowPairs(int[] nums) {
        Deque<int[]> st = new ArrayDeque<>();
        st.add(new int[] {0, 0});
        int size = 0;
        long ans = 0;
        for (int x : nums) {
            while (st.getLast()[0] > x) {
                size -= st.removeLast()[1];
            }
            ans += size;
            if (st.getLast()[0] == x) {
                ans -= st.getLast()[1];
                st.getLast()[1]++;
            } else {
                st.add(new int[]{x, 1});
            }
            size++;
        }
        return ans;
    }
}
