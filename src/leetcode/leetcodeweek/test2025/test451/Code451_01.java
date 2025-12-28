package leetcode.leetcodeweek.test2025.test451;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/25 8:32
 * https://leetcode.cn/contest/weekly-contest-451/problems/find-minimum-log-transportation-cost/description/
 */
public class Code451_01 {
    public static long minCuttingCost(int n, int m, int k) {
        if (n <= k && m <= k) {
            return 0;
        }
        long ans = 0;
        if (n > k) {
            ans = (ans + (long) (n - k) * k);
        }
        if (m > k) {
            ans = (ans + (long) (m - k) * k);
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 4, m = 4, k = 6;
        System.out.println(minCuttingCost(n, m, k));
    }
}
