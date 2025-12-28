package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/16 20:28
 * https://leetcode.cn/problems/ugly-number/
 */
public class Leetcode263IsUgly {
    public static boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        int[] arr = new int[] {2, 3, 5};
        for (int i : arr) {
            while (n % i == 0) {
                n /= i;
            }
        }
        return n == 1;
    }

    public static boolean isUgly1(int n) {
        if (n <= 0 || n == 2147483647) {
            return false;
        }
        if (n == 1) {
            return true;
        }
        for (int i = 2;  i * i <= n; i++) {
            while (n % i == 0) {
                if (i != 2 && i != 3 && i != 5) {
                    return false;
                }
                n /= i;
            }
        }
        if (n > 1 && n != 2 && n != 3 && n != 5) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isUgly(2147483647));
    }
}
