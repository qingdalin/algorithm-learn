package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/28 19:48
 * https://leetcode.cn/problems/sum-of-two-integers/
 */
public class Leetcode371GetSum {
    public static int getSum(int a, int b) {
        // 8 4 2 1
        // 0 0 1 0 2
        // 0 0 1 1 3
        //
        return add(a, b);
    }

    public static int add(int a, int b) {
        int ans = a;
        while (b != 0) {
            ans = a ^ b;
            b = (a & b) << 1;
            a = ans;
        }
        return ans;
    }
}
