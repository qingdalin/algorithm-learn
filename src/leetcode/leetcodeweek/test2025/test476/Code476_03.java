package leetcode.leetcodeweek.test2025.test476;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/16 10:42
 * https://leetcode.cn/contest/weekly-contest-476/problems/count-distinct-integers-after-removing-zeros/
 */
public class Code476_03 {
    // 101 102 120 321 3256
    // 1101
    public static long countDistinct(long n) {
        int len = 1;
        long offset = 1;
        long tmp = n / 10;
        while (tmp > 0) {
            len++;
            offset *= 10;
            tmp /= 10;
        }
        long[][][] dp = new long[len + 1][11][2];
        for (int i = 0; i < len + 1; i++) {
            for (int j = 0; j < 11; j++) {
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
            }
        }
        long ans = f(n, offset, len, 10, 0, dp);
        return ans - 1;
    }

    private static long f(long num, long offset, int len, int pre, int free, long[][][] dp) {
        if (len == 0) {
            return 1;
        }
        if (dp[len][pre][free] != -1) {
            return dp[len][pre][free];
        }
        int cur = (int) (num / offset % 10);
        long ans = 0;
        if (free == 0) {
            if (pre == 10) {
                ans += f(num, offset / 10, len - 1, 10, 1, dp);
                for (int i = 1; i < cur; i++) {
                    ans += f(num, offset / 10, len - 1, i, 1, dp);
                }
                ans += f(num, offset / 10, len - 1, cur, 0, dp);
            } else {
                for (int i = 1; i <= 9; i++) {
                    if (i < cur) {
                        ans += f(num, offset / 10, len - 1, i, 1, dp);
                    } else if (i == cur){
                        ans += f(num, offset / 10, len - 1, cur, 0, dp);
                    }
                }
            }
        } else {
            if (pre == 10) {
                ans += f(num, offset / 10, len - 1, 10, 1, dp);
            }
            for (int i = 1; i <= 9; i++) {
                ans += f(num, offset / 10, len - 1, i, 1, dp);
            }
        }
        dp[len][pre][free] = ans;
        return ans;
    }

    public static void main(String[] args) {
        long n = 11652754537L;
        System.out.println(countDistinct(n));
//        Set<String> set = new HashSet<>();
//        for (int i = 1; i <= n; i++) {
//            String s = String.valueOf(i).replaceAll("0", "");
//            set.add(s);
//        }
//        System.out.println(set.size());
//        int len = 1;
//        int offset = 1;
//        int tmp = n / 10;
//        while (tmp > 0) {
//            len++;
//            offset *= 10;
//            tmp /= 10;
//        }
//        System.out.println(len + " " + offset);
    }
}
