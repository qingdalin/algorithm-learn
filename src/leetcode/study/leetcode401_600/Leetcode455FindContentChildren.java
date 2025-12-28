package leetcode.study.leetcode401_600;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/20 19:37
 * https://leetcode.cn/problems/assign-cookies/
 */
public class Leetcode455FindContentChildren {
    public static int findContentChildren(int[] g, int[] s) {
        int ans = 0, n = g.length, m = s.length;
        Arrays.sort(g);
        Arrays.sort(s);
        for (int i = 0, j = 0; i < n && j < m;) {
            if (s[j] >= g[i]) {
                i++;
                j++;
                ans++;
            } else {
                j++;
            }
        }
        return ans;
    }
}
