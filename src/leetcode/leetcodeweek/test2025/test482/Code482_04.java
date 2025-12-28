package leetcode.leetcodeweek.test2025.test482;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 9:53
 * https://leetcode.cn/contest/weekly-contest-482/problems/number-of-balanced-integers-in-a-range/
 */
public class Code482_04 {
    public static int MAXLEN = 17;
    public static long[][][] dp = new long[MAXLEN][11][2];
    public static long countBalanced(long low, long high) {
        long cnt1 = cnt(high);
        long cnt2 = cnt(low - 1);
        return cnt1 - cnt2;
    }

    private static long cnt(long num) {
        if (num < 11) {
            return 0;
        }
        int len = 1;
        int offset = 1;
        long tmp = num / 10;
        while (tmp > 0) {
            len++;
            offset *= 10;
            tmp /= 10;
        }
        build(len);
        return f(num, offset, len, 10, 0, 0, 0, 1);
    }

    private static long f(long num, int offset, int len, int pre, int free, int evenSum, int oddSum, int isOdd) {
        if (len == 0) {
            return evenSum == oddSum ? 1 : 0;
        }
        if (dp[len][pre][free] != -1) {
            return dp[len][pre][free];
        }
        int cur = (int) (num / offset % 10);
        long ans = 0;
        if (free == 0) {
            if (pre == 10) {
                ans += f(num, offset / 10, len - 1, pre, 1, 0, 0, isOdd);
                for (int i = 1; i < cur; i++) {
                    ans += f(num, offset / 10, len - 1, i, 1, evenSum, oddSum + i, isOdd ^ 1);
                }
                ans += f(num, offset / 10, len - 1, cur, 0, evenSum, oddSum + cur, isOdd ^ 1);
            } else {
                for (int i = 0; i <= 9; i++) {
                    if (isOdd == 1) {
                        oddSum = oddSum + i;
                    } else if (isOdd == 0){
                        evenSum = evenSum + i;
                    }
                    if (i < cur) {
                        ans += f(num, offset / 10, len - 1, i, 1, evenSum, oddSum, isOdd ^ 1);
                    } else if (i == cur) {
                        ans += f(num, offset / 10, len - 1, cur, 0, evenSum, oddSum, isOdd ^ 1);
                    }
                }
            }
        } else {
            if (pre == 10) {
                ans += f(num, offset / 10, len - 1, pre, 1, evenSum, oddSum, isOdd);
                for (int i = 1; i <= 9; i++) {
                    ans += f(num, offset / 10, len - 1, i, 1, evenSum, oddSum + i, isOdd ^ 1);
                }
            } else {
                for (int i = 0; i <= 9; i++) {
                    if (isOdd == 1) {
                        oddSum = oddSum + i;
                    } else if (isOdd == 0){
                        evenSum = evenSum + i;
                    }
                    ans += f(num, offset / 10, len - 1, i, 1, evenSum, oddSum, isOdd ^ 1);
                }
            }
        }
        dp[len][pre][free] = ans;
        return ans;
    }

    private static void build(int len) {
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j < 11; j++) {
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(countBalanced(1, 100));
    }
}
