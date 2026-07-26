package leetcode.leetcodeweek.test2026.test512;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/7/26 10:10
 * https://leetcode.cn/contest/weekly-contest-512/problems/largest-integer-with-given-digit-sum/description/
 */
public class Code512_01 {
    public static int largestInteger(int n, int s) {
        if (9 * n < s) {
            return -1;
        }
        int ans = 0;
        for (int i = 1; i <= n; i++, s -= 9) {
            if (s <= 0) {
                ans *= 10;
            } else {
                if (s >= 9) {
                    ans = ans * 10 + 9;
                } else {
                    ans = ans * 10 + s;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 2, s = 17;
        System.out.println(largestInteger(n, s));
    }
}
