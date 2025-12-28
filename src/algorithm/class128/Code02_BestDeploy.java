package algorithm.class128;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/2 11:20
 * // 最好的部署
 * // 一共有n台机器，编号1 ~ n，所有机器排成一排
 * // 你只能一台一台的部署机器，你可以决定部署的顺序，最终所有机器都要部署
 * // 给定三个数组no[]、one[]、both[]
 * // no[i] : 如果i号机器部署时，相邻没有机器部署，此时能获得的收益
 * // one[i] : 如果i号机器部署时，相邻有一台机器部署，此时能获得的收益
 * // both[i] : 如果i号机器部署时，相邻有两台机器部署，此时能获得的收益
 * // 第1号机器、第n号机器当然不会有两台相邻的机器
 * // 返回部署的最大收益
 * // 1 <= n <= 10^5
 * // 0 <= no[i]、one[i]、both[i]
 * // 来自真实大厂笔试，对数器验证
 */
public class Code02_BestDeploy {
    public static int MAXN = 1001;
    public static int[] no = new int[MAXN];
    public static int[] one = new int[MAXN];
    public static int[] both = new int[MAXN];
    public static int n;
    // 区间dp
    // f(l, r) 潜台词是l-1和r+1的位置没有部署
    public static int best1() {
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = -1;
            }
        }
        return f1(1, n, dp);
    }

    private static int f1(int l, int r, int[][] dp) {
        if (l == r) {
            return no[l];
        }
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        // l位置最后部署，先部署l+1...r:f(l+1, r) + one[l]
        int ans = f1(l + 1, r, dp) + one[l];
        // r位置最后部署，先部署l...r-1:f(l, r-1) + one[r]
        ans = Math.max(ans, f1(l, r - 1, dp) + one[r]);
        // l+1...r-1
        for (int i = l+1; i < r; i++) {
            // l..i-1   i  i+1...r
            // i位置最后部署:f(l,i-1) + f(i+1,r) + both[i]
            ans = Math.max(ans, f1(l, i - 1, dp) + f1(i + 1, r, dp) + both[i]);
        }
        dp[l][r] = ans;
        return ans;
    }

    // f(i,p)
    // p==0，i的前一位没有部署；
    //       1.i不部署，f(i+1,0)+one[i]
    //       1.i部署，f(i+1,1)+no[i]
    // p==1，i的前一位部署；
    //       1.i不部署，f(i+1,0)+both[i]
    //       1.i部署，f(i+1,1)+one[i]
    public static int best2() {
        int[][] dp = new int[n + 1][2];
        // dp[i][0]:表示第i位前一位没有部署的情况下，部署i获取的最大收益
        // dp[i][1]:表示第i位前一位已经部署的情况下，部署i获取的最大收益
        dp[n][0] = no[n];
        dp[n][1] = one[n];
        for (int i = n - 1; i >= 1; i--) {
            dp[i][0] = Math.max(dp[i + 1][0] + one[i], dp[i + 1][1] + no[i]);
            dp[i][1] = Math.max(dp[i + 1][0] + both[i], dp[i + 1][1] + one[i]);
        }
        return dp[1][0];
    }

    public static void main(String[] args) {
        int maxn = 100;
        int maxv = 100;
        int test = 10000;
        System.out.println("测试开始");
        for (int t = 0; t < test; t++) {
            n = (int) (Math.random() * maxn) + 1;
            randomArr(n, maxv);
            int ans1 = best1();
            int ans2 = best2();
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }

    private static void randomArr(int n, int maxv) {
        for (int i = 1; i <= n; i++) {
            no[i] = (int) (Math.random() * maxv);
            one[i] = (int) (Math.random() * maxv);
            both[i] = (int) (Math.random() * maxv);
        }
    }
}
