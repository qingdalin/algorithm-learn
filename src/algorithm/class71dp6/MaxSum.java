package algorithm.class71dp6;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-01 9:27
 * 被7整除的子序列最大累加和
 */
public class MaxSum {
    public static int maxSum1(int[] nums) {
        // 除以7余数为0的最大累加和
        return f(nums, 0, 0);
    }

    public static int f(int[] nums, int i, int s) {
        if (i == nums.length) {
            return s % 7 == 0 ? s : 0;
        }
        return Math.max(f(nums, i + 1, s), f(nums, i + 1, s + nums[i]));
    }

    public static int maxSum2(int[] nums) {
        int n = nums.length;
        // dp[i][j]:表示前i个数的累加和余数为j的最大累加和是多少
        // dp[i][j] = dp[i - 1][j],不要当前数余j
        // 当前数的余数为cur
        // dp[i][j] = dp[i -1][(j + 7 - cur) % 7]
        int[][] dp = new int[n + 1][7];
        // 没有数的时候余数为0的累加和是0
        dp[0][0] = 0;
        for (int j = 1; j < 7; j++) {
            dp[0][j] = -1;
        }
        for (int i = 1, x, cur; i <= n; i++) {
            x = nums[i - 1];
            cur = x % 7;
            for (int j = 0, need; j < 7; j++) {
                dp[i][j] = dp[i - 1][j];
                need = (j + 7 - cur) % 7;
                if (dp[i - 1][need] != -1) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][need] + x);
                }
            }
        }
        return dp[n][0];
    }

    public static void main(String[] args) {
        int n = 20;
        int v = 300;
        int t = 10000;
        System.out.println("开始测试");
        for (int i = 0; i < t; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] nums = randomArray(len, v);
            int ans1 = maxSum1(nums);
            int ans2 = maxSum2(nums);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("结束测试");
    }

    private static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * v);
        }
        return ans;
    }
}
