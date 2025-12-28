package leetcode.leetcodeweek.test2025.test477;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/23 10:48
 * https://leetcode.cn/contest/weekly-contest-477/problems/concatenate-non-zero-digits-and-multiply-by-sum-i/description/
 */
public class Code477_01 {
    public static long sumAndMultiply(int n) {
        if (n == 0) {
            return 0;
        }
        int len = 1, offset = 1;
        int tmp = n / 10;
        while (tmp > 0) {
            len++;
            offset *= 10;
            tmp /= 10;
        }
        long ans = 0;
        long sum = 0;
        for (int i = 0; i < len; i++, offset /= 10) {
            int cur = n / offset % 10;
            if (cur != 0) {
                ans = (ans * 10 + cur);
                sum += cur;
            }
        }
        return sum * ans;
    }

    public static void main(String[] args) {
        int n = 1000;
        System.out.println(sumAndMultiply(n));
    }
}
