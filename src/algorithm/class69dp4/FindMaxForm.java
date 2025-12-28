package algorithm.class69dp4;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-24 19:57
 * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
 *
 * 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
 *
 * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
 * https://leetcode.cn/problems/ones-and-zeroes/description/
 * 从0或者length递归，结果一样
 */
public class FindMaxForm {
    public static int zeros, ones;
    public int findMaxForm1(String[] strs, int m, int n) {
        return f1(strs, 0, m, n);
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int[][][] dp = new int[strs.length][m + 1][n + 1];
        for (int i = 0; i < strs.length; i++) {
            for (int z = 0; z <= m; z++) {
                for (int o = 0; o <= n; o++) {
                    dp[i][z][o] = -1;
                }
            }
        }
        return f(strs, strs.length, m, n, dp);
    }

    private int f(String[] s, int i, int z, int o, int[][][] dp) {
        if (i == 0) {
            return 0;
        }
        if (dp[i - 1][z][o] != -1) {
            return dp[i - 1][z][o];
        }
        // 从s[i..]自由选择，0的数量不超过z，1的数量不超过o，最多可以选几个字符
        // 当前字符不要
        int p1 = f(s, i - 1, z, o, dp);
        int p2 = 0;
        zeroAndOnes(s[i - 1]);
        if (zeros <= z && ones <= o) {
            p2 = 1 + f(s, i - 1, z - zeros, o - ones, dp);
        }
        dp[i - 1][z][o] = Math.max(p1, p2);
        return Math.max(p1, p2);
    }

    private int f1(String[] s, int i, int z, int o) {
        if (i == s.length) {
            return 0;
        }
        // 从s[i..]自由选择，0的数量不超过z，1的数量不超过o，最多可以选几个字符
        // 当前字符不要
        int p1 = f1(s, i + 1, z, o);
        int p2 = 0;
        zeroAndOnes(s[i]);
        if (zeros <= z && ones <= o) {
            p2 = 1 + f1(s, i + 1, z - zeros, o - ones);
        }
        return Math.max(p1, p2);
    }

    private void zeroAndOnes(String s) {
        zeros = 0;
        ones = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                zeros++;
            } else {
                ones++;
            }
        }
    }

    public int findMaxForm2(String[] strs, int m, int n) {
        int[][][] dp = new int[strs.length][m + 1][n + 1];
        for (int i = 0; i < strs.length; i++) {
            for (int z = 0; z <= m; z++) {
                for (int o = 0; o <= n; o++) {
                    dp[i][z][o] = -1;
                }
            }
        }
        return f2(strs, 0, m, n, dp);
    }

    private int f2(String[] s, int i, int z, int o, int[][][] dp) {
        if (i == s.length) {
            return 0;
        }
        if (dp[i][z][o] != -1) {
            return dp[i][z][o];
        }
        // 从s[i..]自由选择，0的数量不超过z，1的数量不超过0，最多可以选几个字符
        // 当前字符不要
        int p1 = f2(s, i + 1, z, o, dp);
        int p2 = 0;
        zeroAndOnes(s[i]);
        if (zeros <= z && ones <= o) {
            p2 = 1 + f2(s, i + 1, z - zeros, o - ones, dp);
        }
        dp[i][z][o] = Math.max(p1, p2);
        return Math.max(p1, p2);
    }

    public int findMaxForm3(String[] strs, int m, int n) {
        int[][][] dp = new int[strs.length + 1][m + 1][n + 1];
        // dp[strs.length][...][...] = 0;
        for (int i = strs.length - 1; i >= 0; i--) {
            zeroAndOnes(strs[i]);
            for (int z = 0, p1, p2; z <= m; z++) {
                for (int o = 0; o <= n; o++) {
                    p1 = dp[i + 1][z][o];
                    p2 = 0;
                    if (zeros <= z && ones <= o) {
                        p2 = 1 + dp[i + 1][z - zeros][o - ones];
                    }
                    dp[i][z][o] = Math.max(p1, p2);
                }
            }
        }
        return dp[0][m][n];
    }

    public int findMaxForm4(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String s : strs) {
            zeroAndOnes(s);
            for (int z = m; z >= zeros ; z--) {
                for (int o = n; o >= ones ; o--) {
                    dp[z][o] = Math.max(dp[z][o], dp[z - zeros][o - ones] + 1);
                }
            }
        }
        return dp[m][n];
    }
}
