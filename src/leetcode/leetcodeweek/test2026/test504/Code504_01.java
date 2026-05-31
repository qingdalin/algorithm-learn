package leetcode.leetcodeweek.test2026.test504;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/31 10:10
 * https://leetcode.cn/contest/weekly-contest-504/problems/digit-frequency-score/description/
 */
public class Code504_01 {
    public int digitFrequencyScore(int n) {
        int ans = 0;
        while (n > 0) {
            ans = ans + (n % 10);
            n /= 10;
        }
        return ans;
    }
}
