package algorithm.class82dp17;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/7 16:48
 * 给定一个长度为 n 的字符串 s ，其中 s[i] 是:
 *
 * “D” 意味着减少，或者
 * “I” 意味着增加
 * 有效排列 是对有 n + 1 个在 [0, n]  范围内的整数的一个排列 perm ，使得对所有的 i：
 *
 * 如果 s[i] == 'D'，那么 perm[i] > perm[i+1]，以及；
 * 如果 s[i] == 'I'，那么 perm[i] < perm[i+1]。
 * 返回 有效排列  perm的数量 。因为答案可能很大，所以请返回你的答案对 109 + 7 取余
 * https://leetcode.cn/problems/valid-permutations-for-di-sequence/description/
 */
public class NumPermsDISequence {
    public static int mod = 1000000007;
    public int numPermsDISequence1(String s) {
        return f1(s.toCharArray(), 0, s.length() + 1, s.length() + 1);
    }

    // i表示当前的位置，i-1位置已经确定，i位置没有确定
    // less表示当前比i-1位置小的有几个
    // 0-i-1范围已经使用i个数字
    // 比i位置大的一共n - i - less
    private int f1(char[] s, int i, int less, int n) {
        int ans = 0;
        if (i == n) {
            ans = 1;
        } else if (i == 0 || s[i - 1] == 'D') {
            // D表示下降，默认0就是下降
            for (int nextLess = 0; nextLess < less; nextLess++) {
                ans = (ans + f1(s, i + 1, nextLess, n)) % mod;
            }
        } else {
//            for (int nextLess = less; nextLess < n - i; nextLess++) {
//                ans = (ans + f1(s, i + 1, nextLess, n)) % mod;
//            }
            for (int nextLess = less, k = 1; k <= n - i - less;k++, nextLess++) {
                ans = (ans + f1(s, i + 1, nextLess, n)) % mod;
            }
        }
        return ans;
    }

    public int numPermsDISequence2(String str) {
        int n = str.length() + 1;
        char[] s = str.toCharArray();
        int[][] dp = new int[n + 1][n + 1];
        for (int less = 0; less <= n; less++) {
            dp[n][less] = 1;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int less = 0; less <= n; less++) {
                if (i == 0 || s[i - 1] == 'D') {
                    // D表示下降，默认0就是下降
                    for (int nextLess = 0; nextLess < less; nextLess++) {
                        dp[i][less] = (dp[i][less] + dp[i + 1][nextLess]) % mod;
                    }
                } else {
                    for (int nextLess = less, k = 1; k <= n - i - less;k++, nextLess++) {
                        dp[i][less] = (dp[i][less] + dp[i + 1][nextLess]) % mod;
                    }
                }
            }
        }
        return dp[0][n];
    }

    public int numPermsDISequence(String str) {
        int n = str.length() + 1;
        char[] s = str.toCharArray();
        int[][] dp = new int[n + 1][n + 1];
        for (int less = 0; less <= n; less++) {
            dp[n][less] = 1;
        }
        for (int i = n - 1; i >= 0; i--) {
            if (i == 0 || s[i - 1] == 'D') {
                // D表示下降，默认0就是下降
                // 依赖左方和左下
                dp[i][1] = dp[i + 1][0];
                for (int less = 2; less <= n; less++) {
                    dp[i][less] = (dp[i][less - 1] + dp[i + 1][less - 1]) % mod;
                }
            } else {
                // 依赖右方和下方
                dp[i][n - i - 1] = dp[i + 1][n - i - 1];
                for (int less = n - i - 2; less >= 0; less--) {
                    dp[i][less] = (dp[i][less + 1] + dp[i + 1][less]) % mod;
                }
            }
        }
        return dp[0][n];
    }
}
