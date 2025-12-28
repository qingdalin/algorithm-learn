package algorithm.class66dp;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-10 19:24
 * 一条包含字母 A-Z 的消息通过以下的方式进行了 编码 ：
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。例如，"11106" 可以映射为：
 *
 * "AAJF" 对应分组 (1 1 10 6)
 * "KJF" 对应分组 (11 10 6)
 * 注意，像 (1 11 06) 这样的分组是无效的，因为 "06" 不可以映射为 'F' ，因为 "6" 与 "06" 不同。
 *
 * 除了 上面描述的数字字母映射方案，编码消息中可能包含 '*' 字符，可以表示从 '1' 到 '9' 的任一数字（不包括 '0'）。
 * 例如，编码字符串 "1*" 可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条消息。对 "1*" 进行解码，
 * 相当于解码该字符串可以表示的任何编码消息。
 *
 * 给你一个字符串 s ，由数字和 '*' 字符组成，返回 解码 该字符串的方法 数目 。
 *
 * 由于答案数目可能非常大，返回 109 + 7 的 模 。
 * https://leetcode.cn/problems/decode-ways-ii/description/
 */
public class NumDecodings2 {
    public static int MOD = 1000000007;
    public int numDecodings1(String s) {
        return f1(s.toCharArray(), 0) % MOD;
    }

    public int f1(char[] s, int i) {
        if (i == s.length) {
            return 1;
        }
        if (s[i] == '0') {
            return  0;
        }
        // 单独i位置转换
        int ans = f1(s, i + 1) * (s[i] == '*' ? 9 : 1) % MOD;
        if (i + 1 < s.length) {
            if (s[i] != '*') {
                if (s[i + 1] != '*') {
                    if (((s[i] - '0') * 10 + (s[i + 1] - '0')) <= 26) {
                        ans += f1(s, i + 2) % MOD;
                    }
                } else {
                    if (s[i] == '1') {
                        ans += f1(s, i + 2) * 9 % MOD;
                    }
                    if (s[i] == '2') {
                        ans += f1(s, i + 2) * 6 % MOD;
                    }
                }
            } else {
                if (s[i + 1] != '*') {
                    if (s[i + 1] <= '6') {
                        ans += f1(s, i + 2) * 2 % MOD;
                    } else {
                        ans += f1(s, i + 2) % MOD;
                    }
                } else {
                    ans += f1(s, i + 2) * 15 % MOD;
                }
            }
        }
        return ans % MOD;
    }

    public int numDecodings2(String s) {
        long[] dp = new long[s.length()];
        Arrays.fill(dp, -1);
        return (int) f2(s.toCharArray(), 0, dp);
    }

    public long f2(char[] s, int i, long[] dp) {
        if (i == s.length) {
            return 1;
        }
        if (s[i] == '0') {
            return  0;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        // 单独i位置转换
        long ans = f2(s, i + 1, dp) * (s[i] == '*' ? 9 : 1);
        if (i + 1 < s.length) {
            if (s[i] != '*') {
                if (s[i + 1] != '*') {
                    if (((s[i] - '0') * 10 + (s[i + 1] - '0')) <= 26) {
                        ans += f2(s, i + 2, dp);
                    }
                } else {
                    if (s[i] == '1') {
                        ans += f2(s, i + 2, dp) * 9;
                    }
                    if (s[i] == '2') {
                        ans += f2(s, i + 2, dp) * 6;
                    }
                }
            } else {
                if (s[i + 1] != '*') {
                    if (s[i + 1] <= '6') {
                        ans += f2(s, i + 2, dp) * 2;
                    } else {
                        ans += f2(s, i + 2, dp);
                    }
                } else {
                    ans += f2(s, i + 2, dp) * 15;
                }
            }
        }
        dp[i] = ans % MOD;
        return ans % MOD;
    }

    public int numDecodings3(String s) {
        int n = s.length();
        long[] dp = new long[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                dp[i] = 0;
            } else {
                dp[i] = dp[i + 1] * (s.charAt(i) == '*' ? 9 : 1);
                if (i + 1 < n) {
                    if (s.charAt(i) != '*') {
                        if (s.charAt(i + 1) != '*') {
                            if (((s.charAt(i) - '0') * 10 + (s.charAt(i + 1) - '0')) <= 26) {
                                dp[i] += dp[i + 2];
                            }
                        } else {
                            if (s.charAt(i) == '1') {
                                dp[i] += dp[i + 2] * 9;
                            }
                            if (s.charAt(i) == '2') {
                                dp[i] += dp[i + 2] * 6;
                            }
                        }
                    } else {
                        if (s.charAt(i + 1) != '*') {
                            if (s.charAt(i + 1) <= '6') {
                                dp[i] += dp[i + 2] * 2;
                            } else {
                                dp[i] += dp[i + 2];
                            }
                        } else {
                            dp[i] += dp[i + 2] * 15;
                        }
                    }
                    dp[i] %= MOD;
                }
            }
        }
        return (int) dp[0] % MOD;
    }

    public int numDecodings(String s) {
        int n = s.length();
        long next = 1;
        long nextNext = 0;
        long ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                ans = 0;
            } else {
                ans = next * (s.charAt(i) == '*' ? 9 : 1);
                if (i + 1 < n) {
                    if (s.charAt(i) != '*') {
                        if (s.charAt(i + 1) != '*') {
                            if (((s.charAt(i) - '0') * 10 + (s.charAt(i + 1) - '0')) <= 26) {
                                ans += nextNext;
                            }
                        } else {
                            if (s.charAt(i) == '1') {
                                ans += nextNext * 9;
                            }
                            if (s.charAt(i) == '2') {
                                ans += nextNext * 6;
                            }
                        }
                    } else {
                        if (s.charAt(i + 1) != '*') {
                            if (s.charAt(i + 1) <= '6') {
                                ans += nextNext * 2;
                            } else {
                                ans += nextNext;
                            }
                        } else {
                            ans += nextNext * 15;
                        }
                    }
                }
            }
            // cur    next     nextNext
            // next   nextNext
            nextNext = next % MOD;
            next = ans % MOD;
        }
        return (int) next;
    }
}
