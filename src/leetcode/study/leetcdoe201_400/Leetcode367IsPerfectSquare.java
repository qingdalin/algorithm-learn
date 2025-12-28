package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/28 18:55
 * https://leetcode.cn/problems/valid-perfect-square/
 */
public class Leetcode367IsPerfectSquare {
    public boolean isPerfectSquare(int num) {
        long i = 1, square = 1;
        while (square < num) {
            if (square == num) {
                return true;
            }
            i++;
            square = i * i;
        }
        return false;
    }

    public boolean isPerfectSquare1(int num) {
        long l = 1, r = num, m;
        while (l <= r) {
            m = (l + r) / 2;
            long cur = m * m;
            if (cur == num) {
                return true;
            } else if (cur < num) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return false;
    }
}
