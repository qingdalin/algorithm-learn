package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 9:51
 * https://leetcode.cn/problems/convert-a-number-to-hexadecimal/
 */
public class Leetcode405ToHex {
    public String toHex(int n) {
        if (n == 0) {
            return "0";
        }
        StringBuilder ans = new StringBuilder();
        while (n != 0) {
            int cur = n & 15;
            if (cur >= 10) {
                char i = (char) (cur - 10 + 'a');
                ans.insert(0, i);
            } else {
                ans.insert(0, cur);
            }
            n >>>= 4;
        }
        return ans.toString();
    }

    public String toHex2(int num) {
        // 8
        // 4 0
        // 2 0
        // 1
        if (num == 0) {
            return "0";
        }
        StringBuilder ans = new StringBuilder();
        long n = num;
        if (n < 0) {
            n = (long) (Math.pow(2, 32) + n);
        }
        while (n > 0) {
            long cur = n % 16;
            if (cur >= 10) {
                char i = (char) (cur - 10 + 'a');
                ans.insert(0, i);
            } else {
                ans.insert(0, cur);
            }
            n /= 16;
        }
        return ans.toString();
    }

    public String toHex1(int num) {
        return Integer.toHexString(num);
    }
}
