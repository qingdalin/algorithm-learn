package leetcode.leetcodeweek.test2025.test481;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/21 10:10
 * https://leetcode.cn/contest/weekly-contest-481/problems/mirror-distance-of-an-integer/
 */
public class Code481_01 {
    public static int mirrorDistance(int n) {
        int num = n;
        int tmp = 0;
        while (num > 0) {
            tmp = tmp * 10 + num % 10;
            num /= 10;
        }
        return Math.abs(tmp - n);
    }

    public static void main(String[] args) {
        int n = 25;
        System.out.println(mirrorDistance(n));
    }
}
