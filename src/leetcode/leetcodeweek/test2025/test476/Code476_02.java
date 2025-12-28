package leetcode.leetcodeweek.test2025.test476;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/16 10:37
 * https://leetcode.cn/contest/weekly-contest-476/problems/minimum-string-length-after-balanced-removals/
 */
public class Code476_02 {
    public int minLengthAfterRemovals(String str) {
        int acnt = 0, bcnt = 0;
        char[] s = str.toCharArray();
        for (char c : s) {
            if (c == 'a') {
                acnt++;
            } else {
                bcnt++;
            }
        }
        return Math.abs(acnt - bcnt);
    }
}
