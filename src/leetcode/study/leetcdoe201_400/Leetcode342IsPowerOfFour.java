package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 14:45
 * https://leetcode.cn/problems/power-of-four/
 */
public class Leetcode342IsPowerOfFour {
    public static boolean isPowerOfFour(int n) {
        // 1 2 4 8 16
        // 首先只能有1个二进制的1
        long num = n;
        if ((num &(num - 1)) != 0 || num == 0) {
            return false;
        }
        // 有一个1还得判断是不是4的幂
        int step = 0;
        while (n > 1) {
            step++;
            n >>= 1;
        }
        return step % 2 == 0;
    }

    public static boolean isPowerOfFour2(int n) {
        // 1 2 4 8 16
        // 首先只能有1个二进制的1
        int num = n;
        int one = 0;
        while (num > 0) {
            num &= (num - 1);
            one++;
        }
        if (one != 1) {
            return false;
        }
        // 有一个1还得判断是不是4的幂
        int step = 0;
        while (n > 1) {
            step++;
            n >>= 1;
        }
        return step % 2 == 0;
    }

    public static int MAXN = 17;
    public static long[] arr = new long[MAXN];
    static {
        arr[0] = 1;
        for (int i = 1; i < MAXN; i++) {
            arr[i] = 4 * arr[i - 1];
        }
    }

    public static boolean isPowerOfFour1(int n) {
        for (int i = 0; i < MAXN; i++) {
            if (n == arr[i]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int n = 0;
        System.out.println(isPowerOfFour(n));
    }
}
