package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 11:28
 * https://leetcode.cn/problems/arranging-coins/
 */
public class Leetcode441ArrangeCoins {
    public static int arrangeCoins(int n) {
        long l = 1, r = n, ans = 0, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            long cur = (1 + mid) * mid / 2;
            if (cur <= n) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return (int) ans;
    }

    public static int arrangeCoins1(int n) {
        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            cnt++;
            n -= i;
        }
        return cnt;
    }

    public static void main(String[] args) {
//         System.out.println(arrangeCoins(1804289383));
        System.out.println(arrangeCoins(8));
    }
}
