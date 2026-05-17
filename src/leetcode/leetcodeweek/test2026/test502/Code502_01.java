package leetcode.leetcodeweek.test2026.test502;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/17 9:26
 * https://leetcode.cn/contest/weekly-contest-502/problems/check-adjacent-digit-differences/description/
 */
public class Code502_01 {
    public boolean isAdjacentDiffAtMostTwo(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        for (int i = 0; i < n - 1; i++) {
            if (Math.abs(s[i] - s[i + 1]) > 2) {
                return false;
            }
        }
        return true;
    }
}
