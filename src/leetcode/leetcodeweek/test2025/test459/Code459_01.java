package leetcode.leetcodeweek.test2025.test459;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/20 10:10
 * https://leetcode.cn/contest/weekly-contest-459/problems/check-divisibility-by-digit-sum-and-product/
 */
public class Code459_01 {
    public static boolean checkDivisibility(int n) {
        int a = 0, b = 1;
        int tmp = n;
        while (n > 0) {
            int num = n % 10;
            a += num;
            b *= num;
            n /= 10;
        }
        return tmp % (a + b) == 0;
    }

    public static void main(String[] args) {
        int n = 100000;
        System.out.println(checkDivisibility(n));
    }
}
