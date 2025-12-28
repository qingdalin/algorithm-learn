package algorithm.class43;

/**
 * https://leetcode.cn/problems/palindrome-number/description/
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-27 15:31
 */
public class PalindromeNumber {
    public static boolean isPalindromeNumber(int num) {
        if (num < 0) {
            return false;
        }
        int offset = 1;
        while (num / offset >= 10) {
            offset *= 10;
        }
        while (num != 0) {
            if (num / offset != num % 10) {
                return false;
            }
            num = num % offset / 10;
            offset /= 100;
        }
        return true;
    }
}
