package leetcode.leetcodeweek.test2025.test477;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/23 11:10
 * https://leetcode.cn/contest/weekly-contest-477/problems/concatenate-non-zero-digits-and-multiply-by-sum-ii/
 */
public class Code477_03 {
    public static int MOD = 1000000007;
    public static int[] sumAndMultiply(String str, int[][] queries) {
        int m = queries.length;
        int[] ans = new int[m];
        char[] s = str.toCharArray();
        int n = s.length;
        for (int i = 0; i < m; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            long num = 0, sum = 0;
            for (int j = l; j <= r; j++) {
                if (s[j] != '0') {
                    num = (num * 10 % MOD + (s[j] - '0')) % MOD;
                    sum = (sum + (s[j] - '0')) % MOD;
                }
            }
            ans[i] = (int) (num * sum % MOD);
        }
        return ans;
    }

    public static int f(int n) {
        if (n == 0) {
            return 0;
        }
        int len = 1, offset = 1;
        int tmp = n / 10;
        while (tmp > 0) {
            len++;
            offset *= 10;
            tmp /= 10;
        }
        int ans = 0;
        int sum = 0;
        for (int i = 0; i < len; i++, offset /= 10) {
            int cur = n / offset % 10;
            if (cur != 0) {
                ans = (ans * 10 + cur) % MOD;
                sum = (sum + cur);
            }
        }
        return sum * ans % MOD;
    }

    public static void main(String[] args) {
        String str = "10203004";
        int[][] queries = new int[][] {
            {0,7},
            {1,3},
            {4,6},
        };
        System.out.println(Arrays.toString(sumAndMultiply(str, queries)));
    }
}
