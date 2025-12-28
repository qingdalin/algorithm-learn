package algorithm.class84dp19;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/13 19:09
 * 给你两个数字字符串 num1 和 num2 ，以及两个整数 max_sum 和 min_sum 。如果一个整数 x 满足以下条件，我们称它是一个好整数：
 *
 * num1 <= x <= num2
 * min_sum <= digit_sum(x) <= max_sum.
 * 请你返回好整数的数目。答案可能很大，请返回答案对 109 + 7 取余后的结果。
 *
 * 注意，digit_sum(x) 表示 x 各位数字之和。
 * https://leetcode.cn/problems/count-of-integers/description/
 */
public class Count {
    public static int MAXN = 23;
    public static int MAXM = 401;
    public static int mod = 1000000007;
    public static char[] num;
    public static int max,min,len;
    public static int[][][] dp = new int[MAXN][MAXM][2];
    public int count(String num1, String num2, int min_sum, int max_sum) {
        max = max_sum;
        min = min_sum;
        num = num2.toCharArray();
        len = num.length;
        build();
        int ans = f1(0, 0, 0);
        num = num1.toCharArray();
        len = num.length;
        build();
        ans = (ans - f1(0, 0, 0) + mod) % mod;
        if (check()) {
            // 如果最小值也满足，那么加1
            ans = (ans + 1) % mod;
        }
        return ans;
    }

    private boolean check() {
        int sum = 0;
        for (char n : num) {
            sum += (n - '0');
        }
        return sum >= min && sum <= max;
    }

    // 当前来到i位数的累加和为sum，是否可以自由选择
    private int f1(int i, int sum, int free) {
        // 如果sum大于max直接返回0
        if (sum > max) {
            return 0;
        }
        // 如果sum+后边位数都是9也小于min直接返回0
        if (sum + (len - i) * 9 < min) {
            return 0;
        }
        if (len == i) {
            return 1;
        }
        if (dp[i][sum][free] != -1) {
            return dp[i][sum][free];
        }
        int ans = 0;
        int cur = num[i] - '0';
        if (free == 0) {
            for (int pick = 0; pick < cur; pick++) {
                // 小于当前位可以自由选择，接下来
                ans = (ans + f1(i + 1, sum + pick, 1)) % mod;
            }
            // 等于当前cur，不能自由选择
            ans = (ans + f1(i + 1, sum + cur, 0)) % mod;
        } else {
            for (int pick = 0; pick <= 9; pick++) {
                ans = (ans + f1(i + 1, sum + pick, 1)) % mod;
            }
        }
        dp[i][sum][free] = ans;
        return ans;
    }

    private void build() {
        for (int i = 0; i < MAXN; i++) {
            for (int j = 0; j < MAXM; j++) {
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
            }
        }
    }
}
