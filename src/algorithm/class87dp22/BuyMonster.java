package algorithm.class87dp22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/20 10:05
 * https://www.nowcoder.com/practice/736e12861f9746ab8ae064d4aae2d5a9
 * 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
 * 如果你当前的能力，小于i号怪兽的能力，你必须付出money[i]的钱，贿赂这个怪兽，
 * 然后怪兽就会加入你，他的能力直接累加到你的能力上；如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，
 * 你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上。
 * 返回通过所有的怪兽，需要花的最小钱数。
 *
 * 输入描述：
 * 第一行输入一个整数N，表示你的能力值N<=500
 * 接下来N行，每行输入两个整数，表示怪兽的力量和需要的金钱
 */
public class BuyMonster {
    public static int MAXN = 501;
    public static int[] a = new int[MAXN];
    public static int[] b = new int[MAXN];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 1; i <= n; i++) {
                st.nextToken();
                a[i] = (int) st.nval;
                st.nextToken();
                b[i] = (int) st.nval;
            }
            int ans = compute4();
            System.out.println(ans);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute1() {
        int m = 0;
        for (int i = 1; i <= n; i++) {
            m += b[i];
        }
        // dp[i][j] 表示第i个怪兽花钱不超过j的情况下，获取的最大能力
        // 负无穷表示花的前无论多少都不能通过
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Integer.MIN_VALUE;
                if (dp[i - 1][j] >= a[i]) {
                    // 不要当前怪兽，前i-1个怪兽获取的能力大于当前怪兽能力
                    dp[i][j] = dp[i - 1][j];
                }
                if (j - b[i] >= 0 && dp[i - 1][j - b[i]] != Integer.MIN_VALUE) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - b[i]] + a[i]);
                }
            }
        }
        int ans = -1;
        for (int j = 0; j <= m; j++) {
            if (dp[n][j] != Integer.MIN_VALUE) {
                ans = j;
                break;
            }
        }
        return ans;
    }

    private static int compute2() {
        int m = 0;
        for (int i = 1; i <= n; i++) {
            m += b[i];
        }
        // dp[i][j] 表示第i个怪兽花钱不超过j的情况下，获取的最大能力
        // 负无穷表示花的前无论多少都不能通过
        int[] dp = new int[m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= 0; j--) {
                int cur = Integer.MIN_VALUE;
                if (dp[j] >= a[i]) {
                    cur = dp[j];
                }
                dp[j] = Integer.MIN_VALUE;
                if (j - b[i] >= 0 && dp[j - b[i]] != Integer.MIN_VALUE) {
                    cur = Math.max(cur, dp[j - b[i]] + a[i]);
                }
                dp[j] = cur;
            }
        }
        int ans = -1;
        for (int j = 0; j <= m; j++) {
            if (dp[j] != Integer.MIN_VALUE) {
                ans = j;
                break;
            }
        }
        return ans;
    }

    private static int compute3() {
        int m = 0;
        for (int i = 1; i <= n; i++) {
            m += a[i];
        }
        // dp[i][j] 表示第i个怪兽花钱能力为j的情况下，花的最少钱
        // 正无穷表示花的前无论多少都不能通过
        int[][] dp = new int[n + 1][m + 1];
        for (int j = 1; j <= m; j++) {
            // dp[0][0]:0个怪兽0能力花钱为0
            // 0怪兽1.2.3...能力花钱为正无穷
            dp[0][j] = Integer.MAX_VALUE;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                if (j >= a[i] && dp[i - 1][j] != Integer.MAX_VALUE) {
                    // 不要当前怪兽，前i-1个怪兽获取的能力大于当前怪兽能力并且有效
                    dp[i][j] = dp[i - 1][j];
                }
                if (j - a[i] >= 0 && dp[i - 1][j - a[i]] != Integer.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - a[i]] + b[i]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int j = 0; j <= m; j++) {
            ans = Math.min(ans, dp[n][j]);
        }
        return ans;
    }

    private static int compute4() {
        int m = 0;
        for (int i = 1; i <= n; i++) {
            m += a[i];
        }
        // dp[i][j] 表示第i个怪兽花钱能力为j的情况下，花的最少钱
        // 正无穷表示花的前无论多少都不能通过
        int[] dp = new int[m + 1];
        for (int j = 1; j <= m; j++) {
            // dp[0][0]:0个怪兽0能力花钱为0
            // 0怪兽1.2.3...能力花钱为正无穷
            dp[j] = Integer.MAX_VALUE;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= 0; j--) {
                int cur = Integer.MAX_VALUE;;
                if (j >= a[i] && dp[j] != Integer.MAX_VALUE) {
                    // 不要当前怪兽，前i-1个怪兽获取的能力大于当前怪兽能力并且有效
                    cur = dp[j];
                }
                if (j - a[i] >= 0 && dp[j - a[i]] != Integer.MAX_VALUE) {
                    cur = Math.min(cur, dp[j - a[i]] + b[i]);
                }
                dp[j] = cur;
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int j = 0; j <= m; j++) {
            ans = Math.min(ans, dp[j]);
        }
        return ans;
    }
}
