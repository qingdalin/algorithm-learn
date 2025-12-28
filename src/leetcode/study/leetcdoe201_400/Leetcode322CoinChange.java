package leetcode.study.leetcdoe201_400;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 15:38
 * https://leetcode.cn/problems/coin-change/
 */
public class Leetcode322CoinChange {
    public static int coinChange(int[] arr, int target) {
        int max = target + 1;
        int[] dp = new int[max];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - arr[j]] + 1);
                }
            }
        }
        return dp[target] > target ? -1 : dp[target];
    }

    public static int coinChange2(int[] arr, int target) {
        if (target < 1) {
            return 0;
        }
        return dfs(arr, target, new int[target]);
    }

    private static int dfs(int[] arr, int target, int[] dp) {
        if (target < 0) {
            return -1;
        }
        if (target == 0) {
            return 0;
        }
        if (dp[target - 1] != 0) {
            return dp[target - 1];
        }
        int min = Integer.MAX_VALUE;
        for (int cur : arr) {
            int res = dfs(arr, target - cur, dp);
            if (res >= 0 && res < min) {
                min = res + 1;
            }
        }
        dp[target - 1] = min == Integer.MAX_VALUE ? -1 : min;
        return dp[target - 1];
    }


    public static int[][] dp = new int[13][10001];
    public static int coinChange1(int[] arr, int target) {
        int n = arr.length;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = -1;
            }
        }
        int ans = f(0, arr, target, 0, 0);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int f(int i, int[] arr, int target, int cnt, int sum) {
        if (i >= arr.length) {
            if (sum == target) {
                return cnt;
            }
            return Integer.MAX_VALUE;
        }
        int p1 = f(i + 1, arr, target, cnt, sum);
        int p2 = Integer.MAX_VALUE;
        for (int j = 1; j <= Math.ceil(target / arr[i]); j++) {
            if (sum + arr[i] * j <= target) {
                p2 = Math.min(p2, f(i + 1, arr, target, cnt + j, sum + arr[i] * j));
            }
        }
        int ans = Math.min(p2, p1);
        return ans;
    }

    public static void main(String[] args) {
        int n = 18;
        int[] arr = new int[] {1,2,5,10};
        System.out.println(coinChange(arr, n));
    }
}
