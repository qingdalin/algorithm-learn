package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/29 19:49
 * https://leetcode.cn/problems/alice-and-bob-playing-flower-game/?envType=daily-question&envId=2025-08-29
 */
public class FlowerGame {
    public static long flowerGame(int n, int m) {
        long ans = 0;
        boolean ismEven = m % 2 == 0;
        boolean isnEven = n % 2 == 0;
        if (ismEven && isnEven) {
            // 都是偶数
            ans += ((long) n * (m / 2));
        } else if (!ismEven && isnEven) {
            // m是奇数，n是偶数
            ans += ((long) m * (n / 2));
        } else if (ismEven && !isnEven) {
            // m是偶数，n是奇数
            ans += ((long) n * (m / 2));
        } else {
            // 都是奇数
            // 1 2 3
            // 1 2 3 4 5
            // 1(2 4)
            // 2(1 3 5)
            // 3(2 4)
            // n(m+1)    m*(n+1)      nm + n + mn + m
            // —————— + ——————————
            //    2        2
            ans += (long) ((m + 1) / 2) * (n / 2);
            ans += (long) (m / 2) * ((n + 1) / 2);
        }
//        for (int i = 1; i <= n; i++) {
//            if (i % 2 == 0) {
//                if (ismEven) {
//                    ans += m / 2;
//                } else {
//                    ans += ((m + 1) / 2);
//                }
//            } else {
//                if (ismEven) {
//                    ans += ((m + 1) / 2);
//                } else {
//                    ans += m / 2;
//                }
//            }
//        }
        // return n * m / 2;
        return ans;
    }
}
