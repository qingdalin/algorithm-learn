package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/16 19:29
 * https://leetcode.cn/problems/add-digits/
 */
public class Leetcode258AddDigits {
    public int addDigits(int num) {
        if (num == 0) {
            return 0;
        }
        if (num % 9 == 0) {
            return 9;
        } else {
            return num % 9;
        }
    }

    public int addDigits1(int num) {
        // 32  16  8  4  2  1
        // 1    0  0  1  1  0
        int sum = 0;
        while (num > 9) {
            sum = 0;
            for (; num > 0;) {
                sum = (sum + num % 10);
                num /= 10;
            }
            num = sum;
        }
        return num;
    }
}
