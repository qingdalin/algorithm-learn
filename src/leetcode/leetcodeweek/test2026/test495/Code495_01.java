package leetcode.leetcodeweek.test2026.test495;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/29 10:24
 * https://leetcode.cn/contest/weekly-contest-495/problems/first-matching-character-from-both-ends/
 */
public class Code495_01 {
    public static int firstMatchingIndex(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        for (int i = 0; i < n; i++) {
            if (s[i] == s[n - i - 1]) {
                return i;
            }
        }
        return -1;
    }
}
