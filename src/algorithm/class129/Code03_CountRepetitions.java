package algorithm.class129;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/3 10:36
 * // 统计重复个数
 * // 如果字符串x删除一些字符，可以得到字符串y，那么就说y可以从x中获得
 * // 给定s1和a，代表s1拼接a次，记为字符串x
 * // 给定s2和b，代表s2拼接b次，记为字符串y
 * // 现在把y拼接m次之后，得到的字符串依然可能从x中获得，返回尽可能大的m
 * // s1、s2只由小写字母组成
 * // 1 <= s1长度、s2长度 <= 100
 * // 1 <= a、b <= 10^6
 * // 测试链接 : https://leetcode.cn/problems/count-the-repetitions/
 */
public class Code03_CountRepetitions {
    public static int getMaxRepetitions(String str1, int a, String str2, int b) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        // next[i][j]:表示s1的第i个字符出发向右至少多长的长度可以找到j字符
        int[][] next = new int[n][30];
        if (!find(s1, n, next, s2)) {
            return 0;
        }
        long[][] st = new long[n][30];
        // st[i][p]:表示s1从i位置出发，至少多少长度，可以找到2的p次方个s2
        for (int i = 0, len, cur; i < n; i++) {
            // 先计算2的0次方
            len = 0;
            cur = i;
            for (char c : s2) {
                len += next[cur][c - 'a'];
                cur = (cur + next[cur][c - 'a']) % n;
            }
            st[i][0] = len;
        }
        for (int p = 1; p <= 29; p++) {
            for (int i = 0; i < n; i++) {
                st[i][p] = st[i][p - 1] + st[(int) ((i + st[i][p - 1]) % n)][p - 1];
            }
        }
        long ans = 0;
        for (int p = 29, start = 0; p >= 0; p--) {
            if (st[start % n][p] + start <= (long) n * a) {
                ans += 1L << p;
                start += st[start % n][p];
            }
        }
        return (int) (ans / b);
    }

    private static boolean find(char[] s1, int n, int[][] next, char[] s2) {
        int[] right = new int[26];
        Arrays.fill(right, -1);
        for (int i = n - 1; i >= 0; i--) {
            right[s1[i] - 'a'] = i + n;
        }
        for (int i = n - 1; i >= 0; i--) {
            right[s1[i] - 'a'] = i;
            for (int j = 0; j < 26; j++) {
                if (right[j] != - 1) {
                    next[i][j] = right[j] - i + 1;
                } else {
                    next[i][j] = -1;
                }
            }
        }
        // 判断s2的字符是否都在s1，如果有不在的肯定结果是0
        for (char c : s2) {
            if (next[0][c - 'a'] == -1) {
                return false;
            }
        }
        return true;
    }
}
