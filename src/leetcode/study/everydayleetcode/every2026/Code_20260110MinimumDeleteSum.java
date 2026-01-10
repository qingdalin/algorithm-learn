package leetcode.study.everydayleetcode.every2026;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/10 16:16
 * https://leetcode.cn/problems/minimum-ascii-delete-sum-for-two-strings/?envType=daily-question&envId=2026-01-10
 */
public class Code_20260110MinimumDeleteSum {
    public static int minimumDeleteSum(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        int sum = str1.chars().sum() + str2.chars().sum();
        int[] dp = new int[m + 1];
        for (int i = 1; i <= n; i++) {
            int leftUp = 0, backUp;
            for (int j = 1; j <= m; j++) {
                backUp = dp[j];
                if (s1[i - 1] == s2[j - 1]) {
                    dp[j] = leftUp + s1[i - 1];
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                leftUp = backUp;
            }
        }
        return sum - dp[m] * 2;
    }

    public static int minimumDeleteSum3(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        int sum = str1.chars().sum() + str2.chars().sum();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + s1[i - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return sum - dp[n][m] * 2;
    }

    public static int minimumDeleteSum2(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        int sum = str1.chars().sum() + str2.chars().sum();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = -1;
            }
        }
        return sum - f2(s1, s2, n, m, dp) * 2;
    }

    private static int f2(char[] s1, char[] s2, int i, int j, int[][] dp) {
        if (i <= 0 || j <= 0) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int ans = 0;
        if (s1[i - 1] == s2[j - 1]) {
            ans = f2(s1, s2, i - 1, j - 1, dp) + s1[i - 1];
        } else {
            ans = Math.max(f2(s1, s2, i - 1, j, dp), f2(s1, s2, i, j - 1, dp));
        }
        dp[i][j] = ans;
        return ans;
    }

    public static int minimumDeleteSum1(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        int sum = str1.chars().sum() + str2.chars().sum();
        return sum - f1(s1, s2, n, m) * 2;
    }

    private static int f1(char[] s1, char[] s2, int i, int j) {
        if (i <= 0 || j <= 0) {
            return 0;
        }
        int ans = 0;
        if (s1[i - 1] == s2[j - 1]) {
            ans = f1(s1, s2, i - 1, j - 1) + s1[i - 1];
        } else {
            ans = Math.max(f1(s1, s2, i - 1, j), f1(s1, s2, i, j - 1));
        }
        return ans;
    }

    public static void main(String[] args) {
        String s1 = "sea";
        String s2 = "eat";
        System.out.println("s==" + (int) 's');
        System.out.println("e==" + (int) 'e');
        System.out.println("a==" + (int) 'a');
        System.out.println("t==" + (int) 't');
        System.out.println(minimumDeleteSum(s1, s2));
    }
}
