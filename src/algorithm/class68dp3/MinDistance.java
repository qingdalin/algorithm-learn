package algorithm.class68dp3;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-19 16:02
 * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
 *
 * 你可以对一个单词进行如下三种操作：
 *
 * 插入一个字符 代价a
 * 删除一个字符 代价b
 * 替换一个字符 代价c
 */
public class MinDistance {
    public int minDistance1(String word1, String word2) {
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();
        int len1 = s1.length;
        int len2 = s2.length;
        return f1(s1, s2, len1, len2);
    }
    public int f1(char[] s1, char[] s2, int len1, int len2) {
        if (len1 == 0) {
            return len2;
        }
        if (len2 == 0) {
            return len1;
        }
        //1. 把s1的len1 - 1 变为 s2的len2 - 1
        int ans = Integer.MAX_VALUE;
        if (s1[len1 - 1] == s2[len2 - 1]) {
            ans = f1(s1, s2, len1 - 1, len2 - 1);
        } else {
            ans = Math.min(f1(s1, s2, len1 - 1, len2 - 1) + 1,
                Math.min(f1(s1, s2, len1, len2 - 1) + 1, f1(s1, s2, len1 - 1, len2) + 1));
        }
        return ans;
    }

    public int minDistance2(String word1, String word2) {
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();
        int len1 = s1.length;
        int len2 = s2.length;
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                dp[i][j] = -1;
            }
        }
        return f2(s1, s2, len1, len2, dp);
    }
    public int f2(char[] s1, char[] s2, int len1, int len2, int[][] dp) {
        if (len1 == 0) {
            return len2;
        }
        if (len2 == 0) {
            return len1;
        }
        if (dp[len1][len2] != -1) {
            return dp[len1][len2];
        }
        //1. 把s1的len1 - 1 变为 s2的len2 - 1
        int ans = Integer.MAX_VALUE;
        if (s1[len1 - 1] == s2[len2 - 1]) {
            ans = f2(s1, s2, len1 - 1, len2 - 1, dp);
        } else {
            ans = Math.min(f2(s1, s2, len1 - 1, len2 - 1, dp) + 1,
                Math.min(f2(s1, s2, len1, len2 - 1, dp) + 1, f2(s1, s2, len1 - 1, len2, dp) + 1));
        }
        dp[len1][len2] = ans;
        return ans;
    }

    public int minDistance3(String word1, String word2) {
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();
        int len1 = s1.length;
        int len2 = s2.length;
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // dp[i - 1][j - 1] + 1,  1是替换代价
                    // dp[i][j - 1] + 1,      1是插入代价
                    // dp[i - 1][j] + 1,      1是删除代价
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1));
                }
            }
        }
        return dp[len1][len2];
    }

    public int minDistance(String word1, String word2) {
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        int[] dp = new int[m + 1];
        for (int j = 0; j <= m; j++) {
            dp[j] = j;
        }
        for (int i = 1, leftUp, bak; i <= n; i++) {
            leftUp = i - 1;
            dp[0] = i;
            for (int j = 1; j <= m; j++) {
                bak = dp[j];
                if (s1[i - 1] == s2[j - 1]) {
                    dp[j] = leftUp;
                } else {
                    // leftUp + 1 ,     1是替换代价
                    // dp[j - 1] + 1 ,  1是插入代价
                    // dp[j] + 1 ,      1是删除代价
                    dp[j] = Math.min(leftUp + 1, Math.min(dp[j - 1] + 1, dp[j] + 1));
                }
                leftUp = bak;
            }
        }
        return dp[m];
    }
}
