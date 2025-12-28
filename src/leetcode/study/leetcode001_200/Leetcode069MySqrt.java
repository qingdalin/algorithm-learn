package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/23 19:29
 * https://leetcode.cn/problems/sqrtx/
 */
public class Leetcode069MySqrt {
    public static int mySqrt(int x) {
        if (x == 1) {
            return 1;
        }
        long l = 0, r = x / 2, ans = 0;
        while (l <= r) {
            long mid = (l + r) / 2;
            if (check(mid, x)) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return (int) ans;
    }

    private static boolean check(long num, long x) {
        return num * num <= x;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(0));
        System.out.println(mySqrt(2147395599));
    }
}
