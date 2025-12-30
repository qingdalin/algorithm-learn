package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 16:47
 * https://leetcode.cn/problems/find-the-closest-palindrome/
 */
public class Leetcode564NearestPalindromic {
    public String nearestPalindromic(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        String ans = "";
        for (int i = 0, j = n - 1; i <= j; i++, j--) {
            if (s[i] < s[j]) {
                s[j] = s[i];
            } else if (s[i] > s[j]) {
                s[i] = s[j];
            } else {
                s[i]--;
            }
        }
        return String.valueOf(s);
    }
}
