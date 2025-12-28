package leetcode.leetcodeweek.test2025.test464;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/24 8:52
 * https://leetcode.cn/contest/weekly-contest-464/problems/gcd-of-odd-and-even-sums/description/
 */
public class Code464_01 {
    public static int gcdOfOddEvenSums(int n) {
        int sumOdd = 0, sumEven = 0;
        for (int i = 1, odd = 1, even = 2; i <= n; i++, odd += 2, even += 2) {
            sumOdd += odd;
            sumEven += even;
        }
        return gcd(sumEven, sumOdd);
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a :gcd(b, a % b);
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println(gcdOfOddEvenSums(n));
    }
}
