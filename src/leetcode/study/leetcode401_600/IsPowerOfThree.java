package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/13 19:58
 * https://leetcode.cn/problems/power-of-three/?envType=daily-question&envId=2025-08-13
 */
public class IsPowerOfThree {
    public boolean isPowerOfThree(int n) {
        while (n != 0 && n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }


    public boolean isPowerOfThree1(int n) {
        return (n > 0 && 1162261467 % n == 0);
    }
}
