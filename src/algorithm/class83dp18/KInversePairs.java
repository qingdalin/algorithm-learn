package algorithm.class83dp18;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/8 20:32
 * 对于一个整数数组 nums，逆序对是一对满足 0 <= i < j < nums.length 且 nums[i] > nums[j]的整数对 [i, j] 。
 *
 * 给你两个整数 n 和 k，找出所有包含从 1 到 n 的数字，且恰好拥有 k 个 逆序对 的不同的数组的个数。
 * 由于答案可能很大，只需要返回对 109 + 7 取余的结果。
 * https://leetcode.cn/problems/k-inverse-pairs-array/description/
 */
public class KInversePairs {
    public static int mod = 1000000007;
    public int kInversePairs1(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        // dp[i][j]:表示1.2.3.。i，形成的排列一定要有j个逆序对的方法
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= k; j++) {
                if (i > j) {
                    for (int p = 0; p <= j; p++) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][p]) % mod;
                    }
                } else {
                    for (int p = j - i + 1; p <= j; p++) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][p]) % mod;
                    }
                }
            }
        }
        return dp[n][k];
    }

    //
    public int kInversePairs2(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        // dp[i][j]:表示1.2.3.。i，形成的排列一定要有j个逆序对的方法
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= k; j++) {
                if (i > j) {
                    dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
                } else {
                    dp[i][j] =((dp[i][j - 1] + dp[i - 1][j]) % mod - dp[i - 1][j - i] + mod) % mod;
                }
            }
        }
        return dp[n][k];
    }
//    public int kInversePairs2(int n, int k) {
//        int[][] dp = new int[n + 1][k + 1];
//        // dp[i][j]:表示1.2.3.。i，形成的排列一定要有j个逆序对的方法
//        dp[0][0] = 1;
//        for (int i = 1; i <= n; i++) {
//            dp[i][0] = 1;
//            for (int j = 1; j <= k; j++) {
//                if (i > j) {
//                    dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
//                } else {
//                    dp[i][j] = (dp[i][j - 1] + dp[i - 1][j] - dp[i - 1][j - i]) % mod;
//                }
//            }
//        }
//        return dp[n][k];
//    }

    public int kInversePairs(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        // dp[i][j]:表示1.2.3.。i，形成的排列一定要有j个逆序对的方法
        dp[0][0] = 1;
        for (int i = 1, window; i <= n; i++) {
            dp[i][0] = 1;
            window = 1;
            for (int j = 1; j <= k; j++) {
                if (i > j) {
                    window = (window + dp[i - 1][j]) % mod;
                } else {
                    window = ((window + dp[i - 1][j]) % mod - dp[i - 1][j - i] + mod) % mod;
                }
                dp[i][j] = window;
            }
        }
        return dp[n][k];
    }
}
