package leetcode.leetcodeweek.test2026.test486;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/25 9:41
 * https://leetcode.cn/contest/weekly-contest-486/problems/find-nth-smallest-integer-with-k-one-bits/
 */
public class Code486_04 {
    // 3个1，第7小
    // 111
    // 1011 1101 1110
    // 10011 10101 110110 11001 11010 11100
    // 1010
    // 0001
    // 1001
    public static int MAXN = 51;
    public static long[][] comb = new long[MAXN][MAXN];
    static {
        for (int i = 0; i < MAXN; i++) {
            comb[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                // i个里选j个的方案数：要当前则i-1个里选j-1个 + 不要当前则i-1个里选j个
                comb[i][j] = comb[i - 1][j - 1] + comb[i - 1][j];
            }
        }
    }
    public static long nthSmallest(long n, int k) {
        long ans = 0;
        for (int i = 49; k > 0; i--) {
            long c = comb[i][k];
            if (n > c) {
                n -= c;
                ans |= 1L << i;
                k--;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 4, k = 3;
        System.out.println(nthSmallest(n , k));
    }
}
