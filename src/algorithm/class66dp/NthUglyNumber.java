package algorithm.class66dp;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-13 19:50
 * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
 *
 * 丑数 就是质因子只包含 2、3 和 5 的正整数。
 * https://leetcode.cn/problems/ugly-number-ii/description/
 */
public class NthUglyNumber {
    public int nthUglyNumber(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        // i2,i3,i5是下标
        for (int i = 2, i2 = 1, i3 = 1, i5 = 1, cur, a, b, c; i <= n; i++) {
            a = dp[i2] * 2;
            b = dp[i3] * 3;
            c = dp[i5] * 5;
            cur = Math.min(Math.min(a, b), c);
            if (cur == a) {
                i2++;
            }
            if (cur == b) {
                i3++;
            }
            if (cur == c) {
                i5++;
            }
            dp[i] = cur;
        }
        return dp[n];
    }
}
