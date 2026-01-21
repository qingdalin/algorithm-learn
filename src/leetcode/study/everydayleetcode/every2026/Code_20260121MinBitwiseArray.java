package leetcode.study.everydayleetcode.every2026;

import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/21 19:47
 * https://leetcode.cn/problems/construct-the-minimum-bitwise-array-ii/?envType=daily-question&envId=2026-01-21
 */
public class Code_20260121MinBitwiseArray {
    // 8 4 2 1
    // 1 0 1 1 11 9
    // 1 0 0 1
    // 1 0 1 0
    // 1 1 0 1
    // 0 0 1 0
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        for (int i = 0, cur; i < n; i++) {
            cur = nums.get(i);
            if (cur == 2) {
                ans[i] = -1;
            } else {
                int x = ~cur;
                int lowbit = x & -x;
                lowbit >>= 1;
                ans[i] = lowbit ^ cur;
            }
        }
        return ans;
    }

    private int f(int cur) {
        int l = 1, r = cur, mid, ans = -1;
        while (l <= r) {
            mid = l + (r - l) / 2;
            if (((mid + 1) ^ mid) <= cur) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid -  1;
            }
        }
        return ans;
    }
}
