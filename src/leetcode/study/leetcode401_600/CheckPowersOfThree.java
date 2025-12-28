package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/15 19:20
 * https://leetcode.cn/problems/check-if-number-is-a-sum-of-powers-of-three/?envType=daily-question&envId=2025-08-15
 */
public class CheckPowersOfThree {
    public static int MAXN = 16;
    public static int[] power = new int[MAXN];
    static {
        power[0] = 1;
        for (int i = 1; i < MAXN; i++) {
            power[i] = power[i - 1] * 3;
        }
    }
    public static boolean checkPowersOfThree(int n) {
        for (int i = MAXN - 1; i >= 0; i--) {
            if (n >= power[i]) {
                n -= power[i];
            }
        }
        return n == 0;
    }
}
