package algorithm.class49;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/
 * 无重复字符的最长子串
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-02-04 19:57
 */
public class LengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        char[] arr = s.toCharArray();
        int n = s.length();
        int[] last = new int[256];
        Arrays.fill(last, -1);
        int ans = 0;
        for (int r = 0, l = 0; r < n; r++) {
            l = Math.max(l, last[arr[r]] + 1);
            ans = Math.max(ans, r - l + 1);
            last[arr[r]] = r;
        }
        return ans;
    }
}
