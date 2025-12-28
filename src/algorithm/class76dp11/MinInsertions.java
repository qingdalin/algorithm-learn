package algorithm.class76dp11;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-22 9:59
 * 给你一个字符串 s ，每一次操作你都可以在字符串的任意位置插入任意字符。
 *
 * 请你返回让 s 成为回文串的 最少操作次数 。
 *
 * 「回文串」是正读和反读都相同的字符串
 * https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/description/
 */
public class MinInsertions {
    public int minInsertions1(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        return f1(s, 0, n - 1);
    }

    // 字符串s，从l到r范围上，添加至少几个字符可以是s变为回文串
    private int f1(char[] s, int l, int r) {
        if (l == r) {
            // 只有1个字符天然回文
            return 0;
        }
        if (l + 1 == r) {
            // 如果s两个字符，如果相等不需要添加，不等添加一个
            return s[l] == s[r] ? 0 : 1;
        }
        if (s[l] == s[r]) {
            // 如果相等，则在l- 1和r - 1范围上至少添加几个字符变为回文
            return f1(s, l + 1, r - 1);
        } else {
            // l和r不等，则解决l位置的字符，去l + 1，r范围上，
            // 或者解决r位置，去l，r - 1范围上尝试，取最小值
            return Math.min(f1(s, l + 1, r), f1(s, l, r - 1)) + 1;
        }
    }

    public int minInsertions2(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        return f2(s, 0, n - 1, dp);
    }

    // 字符串s，从l到r范围上，添加至少几个字符可以是s变为回文串
    private int f2(char[] s, int l, int r, int[][] dp) {
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        int ans = 0;
        if (l == r) {
            // 只有1个字符天然回文
            ans = 0;
        } else if (l + 1 == r) {
            // 如果s两个字符，如果相等不需要添加，不等添加一个
            ans = s[l] == s[r] ? 0 : 1;
        } else {
            if (s[l] == s[r]) {
                // 如果相等，则在l- 1和r - 1范围上至少添加几个字符变为回文
                ans = f2(s, l + 1, r - 1, dp);
            } else {
                // l和r不等，则解决l位置的字符，去l + 1，r范围上，
                // 或者解决r位置，去l，r - 1范围上尝试，取最小值
                ans = Math.min(f2(s, l + 1, r, dp), f2(s, l, r - 1, dp)) + 1;
            }
        }

        dp[l][r] = ans;
        return ans;
    }

    public int minInsertions3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] dp = new int[n][n];
        dp[n - 1][n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = 0;
                } else if (i + 1 == j) {
                    dp[i][j] = s[i] == s[j] ? 0 : 1;
                } else {
                    if (s[i] == s[j]) {
                        dp[i][j] = dp[i + 1][j - 1];
                    } else {
                        dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    public int minInsertions4(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] dp = new int[n][n];
        for (int l = 0; l < n - 1; l++) {
            dp[l][l + 1] = s[l] == s[l + 1] ? 0 : 1;
        }
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                if (s[l] == s[r]) {
                    dp[l][r] = dp[l + 1][r - 1];
                } else {
                    dp[l][r] = Math.min(dp[l + 1][r], dp[l][r - 1]) + 1;
                }
            }
        }
        return dp[0][n - 1];
    }

    public int minInsertions(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        if (n < 2) {
            return 0;
        }
        int[] dp = new int[n];
        dp[n - 1] = s[n - 1] == s[n - 2] ? 0 : 1;
        for (int l = n - 3, leftDown = 0, tmp = 0; l >= 0; l--) {
            leftDown = dp[l + 1];
            dp[l + 1] = s[l] == s[l + 1] ? 0 : 1;
            for (int r = l + 2; r < n; r++) {
                tmp = dp[r];
                if (s[l] == s[r]) {
                    dp[r] = leftDown;
                } else {
                    dp[r] = Math.min(dp[r], dp[r - 1]) + 1;
                }
                leftDown = tmp;
            }
        }
        return dp[n - 1];
    }

    // 空间压缩
    // 本题有关空间压缩的实现，可以参考讲解067，题目4，最长回文子序列问题的讲解
    // 这两个题空间压缩写法高度相似
    // 因为之前的课多次讲过空间压缩的内容，所以这里不再赘述
    public static int minInsertions6(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        if (n < 2) {
            return 0;
        }
        int[] dp = new int[n];
        dp[n - 1] = s[n - 2] == s[n - 1] ? 0 : 1;
        for (int l = n - 3, leftDown, backUp; l >= 0; l--) {
            leftDown = dp[l + 1];
            dp[l + 1] = s[l] == s[l + 1] ? 0 : 1;
            for (int r = l + 2; r < n; r++) {
                backUp = dp[r];
                if (s[l] == s[r]) {
                    dp[r] = leftDown;
                } else {
                    dp[r] = Math.min(dp[r - 1], dp[r]) + 1;
                }
                leftDown = backUp;
            }
        }
        return dp[n - 1];
    }
}
