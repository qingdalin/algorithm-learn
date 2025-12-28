package algorithm.class84dp19;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/13 9:17
 * 给你一个整数 n ，统计并返回各位数字都不同的数字 x 的个数，其中 0 <= x < 10n 。
 * https://leetcode.cn/problems/count-numbers-with-unique-digits/description/
 */
public class CountNumbersWithUniqueDigits {
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0) {
            return 1;
        }
        int ans = 10;
        for (int s = 9, i = 9, k = 2; k <= n; k++, i--) {
            s *= i;
            ans += s;
        }
        return ans;
    }
}
