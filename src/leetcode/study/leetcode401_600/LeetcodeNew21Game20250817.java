package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 7:17
 * https://leetcode.cn/problems/new-21-game/?envType=daily-question&envId=2025-08-17
 */
public class LeetcodeNew21Game20250817 {
    public static double new21Game(int n, int k, int maxPts) {
        if (k == 0) {
            return 1.0;
        }
        double[] dp = new double[k + maxPts];
        for (int i = k; i <= n && i < k + maxPts; i++) {
            dp[i] = 1.0;
        }
        dp[k - 1] = 1.0 * Math.min(n - k + 1, maxPts) / maxPts;
        for(int i = k - 2; i >= 0; i--) {
            dp[i] = dp[i + 1]  + (dp[i + 1] - dp[i + maxPts + 1]) / maxPts;
        }
        return dp[0];
    }

    public static double new21Game1(int n, int k, int maxPts) {
        if (k == 0) {
            return 1.0;
        }
        double[] dp = new double[k + maxPts];
        for (int i = k; i <= n && i < k + maxPts; i++) {
            dp[i] = 1.0;
        }
        for(int i = k - 1; i >= 0; i--) {
            for (int j = 1; j <= maxPts; j++) {
                dp[i] += dp[i + j] / maxPts;
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int n = 21, k = 17, max = 10;
        System.out.println(new21Game1(n, k, max));
    }
}
