package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 10:18
 * https://leetcode.cn/problems/longest-palindrome/
 */
public class Leetcode409LongestPalindrome {
    public int longestPalindrome(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(s[i], map.getOrDefault(s[i], 0) + 1);
        }
        int ans = 0;
        int odd = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            int cnt = entry.getValue();
            ans += cnt / 2 * 2;
            if ((cnt & 1) == 1) {
                odd = 1;
            }
        }
        return ans + odd;
    }
}
