package leetcode.leetcodeweek.test2026.test506;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/6/14 9:15
 * https://leetcode.cn/contest/weekly-contest-506/problems/check-good-integer/description/
 */
public class Code506_01 {
    public static boolean checkGoodInteger(int n) {
        int digitSum = 0, squareSum = 0;
        while (n > 0) {
            int cur = n % 10;
            digitSum += cur;
            squareSum += cur * cur;
            n /= 10;
        }
        return squareSum - digitSum >= 50;
    }
}
