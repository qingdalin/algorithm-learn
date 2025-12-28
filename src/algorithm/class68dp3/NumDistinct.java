package algorithm.class68dp3;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-19 14:44
 * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数，结果需要对 109 + 7 取模。
 */
public class NumDistinct {
    public int numDistinct1(String s, String t) {
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        return f1(s1, t1, s1.length, t1.length);
    }

    public int f1(char[] s, char[] t, int len1, int len2) {
        if (len2 == 0) {
            return 1;
        }
        if (len1 == 0) {
            return 0;
        }
        int ans = f1(s, t, len1 - 1, len2);
        if (s[len1 - 1] == t[len2 - 1]) {
            ans += f1(s, t, len1 - 1, len2 - 1);
        }
        return    ans;
    }

    public int numDistinct2(String s, String t) {
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        int n = s1.length;
        int m = t1.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = -1;
            }
        }
        return f2(s1, t1, n, m, dp);
    }

    public int f2(char[] s, char[] t, int len1, int len2, int[][] dp) {
        if (dp[len1][len2] != -1) {
            return dp[len1][len2];
        }
        if (len2 == 0) {
            return 1;
        }
        if (len1 == 0) {
            return 0;
        }
        int ans = f2(s, t, len1 - 1, len2, dp);
        if (s[len1 - 1] == t[len2 - 1]) {
            ans += f2(s, t, len1 - 1, len2 - 1, dp);
        }
        dp[len1][len2] = ans;
        return    ans;
    }
    // s[前缀长度为i]的所有子序列，有多少个子序列等于t[前缀长度为j]
    public int numDistinct3(String s, String t) {
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        int n = s1.length;
        int m = t1.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (s1[i - 1] == t1[j - 1]) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[n][m];
    }

    public int numDistinct4(String s, String t) {
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        int n = s1.length;
        int m = t1.length;
        int[] dp = new int[m + 1];
        dp[0] = 1;
        for (int i = 1; i <= m; i++) {
            dp[i] = 0;
        }
        for (int i = 1; i <= n; i++) {
            int leftUp = 1, bak;
            for (int j = 1; j <= m; j++) {
                bak = dp[j];
                dp[j] = dp[j];
                if (s1[i - 1] == t1[j - 1]) {
                    dp[j] += leftUp;
                }
                leftUp = bak;
            }
        }
        return dp[m];
    }

    public int numDistinct(String s, String t) {
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        int n = s1.length;
        int m = t1.length;
        int[] dp = new int[m + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= 1; j--) {
                if (s1[i - 1] == t1[j - 1]) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        return dp[m];
    }
}
