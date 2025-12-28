package algorithm.class69dp4;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-26 11:35
 * 使用下面描述的算法可以扰乱字符串 s 得到字符串 t ：
 * 如果字符串的长度为 1 ，算法停止
 * 如果字符串的长度 > 1 ，执行下述步骤：
 * 在一个随机下标处将字符串分割成两个非空的子字符串。即，如果已知字符串 s ，则可以将其分成两个子字符串 x 和 y ，且满足 s = x + y 。
 * 随机 决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。即，在执行这一步骤之后，s 可能是 s = x + y 或者 s = y + x 。
 * 在 x 和 y 这两个子字符串上继续从步骤 1 开始递归执行此算法。
 * 给你两个 长度相等 的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。如果是，返回 true ；否则，返回 false 。
 * https://leetcode.cn/problems/scramble-string/description/
 */
public class IsScramble {
    public boolean isScramble1(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s2.length;
        return f1(s1, 0, n - 1, s2, 0, n - 1);
    }

    private boolean f1(char[] s1, int l1, int r1, char[] s2, int l2, int r2) {
        if (l1 == r1) {
            return s1[l1] == s2[l2];
        }
        // 不交错
        for (int i = l1, j = l2; i < r1; i++, j++) {
            if (f1(s1, l1, i, s2, l2, j) && f1(s1, i + 1, r1, s2, j + 1, r2)) {
                return true;
            }
        }
        // 交错
        //s1[l1........i][i + 1..r1]
        //s2[j...r2][l2.........j-1]
        for (int i = l1, j = r2; i < r1; i++, j--) {
            if (f1(s1, l1, i, s2, j, r2) && f1(s1, i + 1, r1, s2, l2, j - 1)) {
                return true;
            }
        }
        return false;
    }

    public boolean isScramble2(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s2.length;
        return f2(s1, s2, 0, 0, n);
    }

    private boolean f2(char[] s1, char[] s2, int l1, int l2, int len) {
        if (len == 1) {
            return s1[l1] == s2[l2];
        }
        // 不交错
        for (int k = 1; k < len; k++) {
            if (f2(s1, s2, l1, l2, k) && f2(s1, s2, l1 + k, l2 + k, len - k)) {
                return true;
            }
        }
        // 交错
        //s1[l1........i][i + 1..r1]
        //s2[j...r2][l2.........j-1]
        for (int k = 1, i = l1 + 1, j = l2 + len - 1; k < len; k++, i++, j--) {
            if (f2(s1, s2, l1, j, k) && f2(s1, s2, i, l2, len - k)) {
                return true;
            }
        }
        return false;
    }

    public boolean isScramble3(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s2.length;
        // 0 : 没展开
        // -1: 返回false
        // 1:  返回true
        int[][][] dp = new int[n][n][n + 1];
        return f3(s1, s2, 0, 0, n, dp);
    }

    private boolean f3(char[] s1, char[] s2, int l1, int l2, int len, int[][][] dp) {
        if (len == 1) {
            return s1[l1] == s2[l2];
        }
        if (dp[l1][l2][len] != 0) {
            return dp[l1][l2][len] == 1;
        }
        // 不交错
        boolean ans = false;
        for (int k = 1; k < len; k++) {
            if (f3(s1, s2, l1, l2, k, dp) && f3(s1, s2, l1 + k, l2 + k, len - k, dp)) {
                ans = true;
                break;
            }
        }
        // 交错
        //s1[l1........i][i + 1..r1]
        //s2[j...r2][l2.........j-1]
        if (!ans) {
            for (int k = 1, i = l1 + 1, j = l2 + len - 1; k < len; k++, i++, j--) {
                if (f3(s1, s2, l1, j, k, dp) && f3(s1, s2, i, l2, len - k, dp)) {
                    ans = true;
                    break;
                }
            }
        }
        dp[l1][l2][len] = ans ? 1 : -1;
        return ans;
    }

    public boolean isScramble(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s2.length;
        // 0 : 没展开
        // -1: 返回false
        // 1:  返回true
        boolean[][][] dp = new boolean[n][n][n + 1];
        // 当len为1的时候，判断字符是否相等
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][1] = s1[i] == s2[j];
            }
        }
        for (int len = 2; len <= n; len++) {
            for (int l1 = 0; l1 <= n - len; l1++) {
                for (int l2 = 0; l2 <= n - len; l2++) {
                    for (int k = 1; k <= len; k++) {
                        if (dp[l1][l2][k] && dp[l1 + k][l2 + k][len - k]) {
                            dp[l1][l2][len] = true;
                            break;
                        }
                    }
                    if (!dp[l1][l2][len]) {
                        for (int k = 1, i = l1 + 1, j = l2 + len - 1; k < len; k++, i++, j--) {
                            if (dp[l1][j][k] && dp[i][l2][len - k]) {
                                dp[l1][l2][len] = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return dp[0][0][n];
    }
}
