package algorithm.class68dp3;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-20 19:59
 * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
 *
 * 两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空
 * 子字符串
 * ：
 *
 * s = s1 + s2 + ... + sn
 * t = t1 + t2 + ... + tm
 * |n - m| <= 1
 * 交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
 */
public class IsInterleave {
    public boolean isInterleave2(String str1, String str2, String str3) {
        if (str1.length() + str2.length() != str3.length()) {
            return false;
        }
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        char[] s3 = str3.toCharArray();
        int n = s1.length;
        int m = s2.length;
        boolean[][] dp = new boolean[n + 1][m + 1];
        // dp[i][j] 表示s1的前i个字符和s2的前j个字符是否可以组成s3的前i+j个字符
        dp[0][0] =true;
        for (int i = 1; i <= n; i++) {
            if (s1[i - 1] != s3[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }
        for (int j = 1; j <= m; j++) {
            if (s2[j - 1] != s3[j - 1]) {
                // 只要有一个不等，后续全是false
                break;
            }
            dp[0][j] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = (s1[i - 1] == s3[i + j - 1] && dp[i - 1][j]) ||
                    (s2[j - 1] == s3[i + j - 1] && dp[i][j -1]);
            }
        }
        return dp[n][m];
    }
    // 空间压缩
    public boolean isInterleave(String str1, String str2, String str3) {
        if (str1.length() + str2.length() != str3.length()) {
            return false;
        }
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        char[] s3 = str3.toCharArray();
        int n = s1.length;
        int m = s2.length;
        boolean[] dp = new boolean[m + 1];
        // dp[i][j] 表示s1的前i个字符和s2的前j个字符是否可以组成s3的前i+j个字符
        dp[0] =true;
        for (int j = 1; j <= m; j++) {
            if (s2[j - 1] != s3[j - 1]) {
                // 只要有一个不等，后续全是false
                break;
            }
            dp[j] = true;
        }
        for (int i = 1; i <= n; i++) {
            dp[0] = s1[i - 1] == s3[i - 1] && dp[0];
            for (int j = 1; j <= m; j++) {
                dp[j] = (s1[i - 1] == s3[i + j - 1] && dp[j]) ||
                    (s2[j - 1] == s3[i + j - 1] && dp[j -1]);
            }
        }
        return dp[m];
    }
}
