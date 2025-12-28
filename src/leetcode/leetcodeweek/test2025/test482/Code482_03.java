package leetcode.leetcodeweek.test2025.test482;

import java.math.BigInteger;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 9:53
 * https://leetcode.cn/contest/weekly-contest-482/problems/smallest-all-ones-multiple/
 */
public class Code482_03 {
    public static int minAllOneMultiple(int k) {
        // 111111
        // 8 4 2 1
        // 1 1 0 1
        if (k % 2 == 0 || k % 10 == 5) {
            return -1;
        }
        int x = 1 % k;
        for (int i = 1;;i++) {
            if (x == 0) {
                return i;
            }
            x = (x * 10 + 1) % k;
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 200; i++) {
            int ans = minAllOneMultiple(i);
            if (ans == -1) {
                continue;
            }
            System.out.print(Integer.toBinaryString(i) + "     ");
            System.out.println(i + " : == " + ans);

        }
    }
}
