package leetcode.leetcodeweek.test2025.test465;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/31 10:06
 * https://leetcode.cn/contest/weekly-contest-465/problems/restore-finishing-order/
 */
public class Code465_01 {
    public static int[] recoverOrder(int[] order, int[] friends) {
        int n = order.length, m = friends.length;
        int[] ans = new int[m];
        Set<Integer> set = new HashSet<>();
        for (int friend : friends) {
            set.add(friend);
        }
        for (int i = 0, j = 0; i < n; i++) {
            if (set.contains(order[i])) {
                ans[j++] = order[i];
            }
        }
        return ans;
    }
}
