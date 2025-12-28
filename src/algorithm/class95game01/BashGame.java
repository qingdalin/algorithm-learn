package algorithm.class95game01;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 15:55
 */
public class BashGame {
    public static int maxn = 500;
    public static String[][] dp = new String[maxn][maxn];
    public static String bashGame1(int n, int m) {
        return f(n, m, dp);
    }

    private static String f(int n, int m, String[][] dp) {
        if (n == 0) {
            return "后手";
        }
        if (dp[n][m] != null) {
            return dp[n][m];
        }
        String ans = "后手";
        for (int pick = 1; pick <= m; pick++) {
            if (f(n - pick, m, dp).equals("后手")) {
                // 后续过程返回的后手就是当前的先手
                ans = "先手";
                break;
            }
        }
        dp[n][m] = ans;
        return ans;
    }

    public static String bashGame2(int n, int m) {
        return n % (m + 1) != 0 ? "先手" : "后手";
    }

    public static void main(String[] args) {
        int v = 400;
        System.out.println("测试开始");
        for (int i = 0; i < 2000; i++) {
            int n = (int) (Math.random() * v + 1);
            int m = (int) (Math.random() * v + 1);
            String ans1 = bashGame1(n, m);
            String ans2 = bashGame2(n, m);
            if (!ans1.equals(ans2)) {
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }
}
