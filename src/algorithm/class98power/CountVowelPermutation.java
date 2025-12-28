package algorithm.class98power;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/4 9:04
 * 给你一个整数 n，请你帮忙统计一下我们可以按下述规则形成多少个长度为 n 的字符串：
 *
 * 字符串中的每个字符都应当是小写元音字母（'a', 'e', 'i', 'o', 'u'）
 * 每个元音 'a' 后面都只能跟着 'e'
 * 每个元音 'e' 后面只能跟着 'a' 或者是 'i'
 * 每个元音 'i' 后面 不能 再跟着另一个 'i'
 * 每个元音 'o' 后面只能跟着 'i' 或者是 'u'
 * 每个元音 'u' 后面只能跟着 'a'
 * 由于答案可能会很大，所以请你返回 模 10^9 + 7 之后的结果。
 * https://leetcode.cn/problems/count-vowels-permutation/description/
 */
public class CountVowelPermutation {
    // dp[i][j]: 字符长度为i的情况下，以j字符结尾，合法的数量
    // 长度为1，以a e i o u 方法都有1种
    // a结尾: 前一个只能是 e i u
    // e结尾: 前一个只能是 a i
    // i结尾: 前一个只能是 e o
    // o结尾: 前一个只能是 i
    // u结尾: 前一个只能是 i o
    public static int mod = 1000000007;
    public int countVowelPermutation(int n) {
        int[][] start = { { 1, 1, 1, 1, 1 } };
        int[][] base = {
            { 0, 1, 0, 0, 0 }, // a
            { 1, 0, 1, 0, 0 }, // e
            { 1, 1, 0, 1, 1 }, // i
            { 0, 0, 1, 0, 1 }, // o
            { 1, 0, 0, 0, 0 }  // u
        };
        int[][] ans = multiply(start, power(base, n));
        int res = 0;
        for (int a : ans[0]) {
            res = (res + a) % mod;
        }
        return res;
    }
    private static int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length;
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] = (int) (((long) a[i][c] * b[c][j] + ans[i][j]) % mod);
                }
            }
        }
        return ans;
    }

    public static int[][] power(int[][] base, int p) {
        int n = base.length;
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            // 对角线设置1就相当于ans=1
            ans[i][i] = 1;
        }
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                ans = multiply(ans, base);
            }
            base = multiply(base, base);
        }
        return ans;
    }
}
