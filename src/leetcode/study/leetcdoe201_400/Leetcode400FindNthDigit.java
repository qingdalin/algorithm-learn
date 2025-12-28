package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 19:39
 * https://leetcode.cn/problems/nth-digit/
 */
public class Leetcode400FindNthDigit {
    public static int findNthDigit(int n) {
        // 1-9          9=1*9*10^0
        // 10-99        180=2*9*10^1
        // 100-999      2700=3*9*10^2
        // X=X*9*10^(x-1)
        int len = 1;
        while (len * 9 * Math.pow(10, len - 1) < n) {
            n -= len * 9 * Math.pow(10, len - 1);
            len++;
        }
        long s = (long) Math.pow(10, len - 1);
        long x = n / len - 1 + s;
        n -= (x - s + 1) * len;
        if (n == 0) {
            return (int) (x % 10);
        } else {
            return (int) ((x + 1) / Math.pow(10, len - n) % 10);
        }
    }

    public static void main(String[] args) {
        int n = 3256;
        System.out.println(findNthDigit(n));
    }
}
