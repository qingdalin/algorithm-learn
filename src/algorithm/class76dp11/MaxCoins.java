package algorithm.class76dp11;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-22 14:35
 * 有 n 个气球，编号为0 到 n - 1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
 *
 * 现在要求你戳破所有的气球。戳破第 i 个气球，你可以获得 nums[i - 1] * nums[i] * nums[i + 1] 枚硬币。
 * 这里的 i - 1 和 i + 1 代表和 i 相邻的两个气球的序号。如果 i - 1或 i + 1 超出了数组的边界，那么就当它是一个数字为 1 的气球。
 *
 * 求所能获得硬币的最大数量。
 * https://leetcode.cn/problems/burst-balloons/description/
 */
public class MaxCoins {
    public int maxCoins1(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 2];
        // 前后补一个1
        arr[0] = 1;
        arr[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            arr[i] = nums[i - 1];
        }
        int[][] dp = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = -1;
            }
        }
        return f1(arr, 1, n, dp);
    }

    // r + 1的气球一定没打爆
    // l - 1的气球一定没打爆
    // 尝试每个气球最后打爆的最大硬币
    private int f1(int[] arr, int l, int r, int[][] dp) {
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        int ans = 0;
        if (l == r) {
            // 只有一个气球了
            ans = arr[l - 1] * arr[l] * arr[r + 1];
        } else {
            ans = Math.max(
                arr[l - 1] * arr[l] * arr[r + 1] + f1(arr, l + 1, r, dp), // 先打爆l位置的气球，加上l+1到r上的最大值
                arr[l - 1] * arr[r] * arr[r + 1] + f1(arr, l, r - 1, dp));// 先打爆r位置的气球，加上l到r-1上的最大值
            // l和r位置已经决策了，从l+1尝试到r-1
            for (int k = l + 1; k < r; k++) {
                ans = Math.max(ans, arr[l - 1] * arr[k] * arr[r + 1] + f1(arr, l, k - 1, dp) + f1(arr, k + 1 , r, dp));
            }
        }
        dp[l][r] = ans;
        return ans;
    }

    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 2];
        // 前后补一个1
        arr[0] = 1;
        arr[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            arr[i] = nums[i - 1];
        }
        int[][] dp = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            dp[i][i] = arr[i - 1] * arr[i] * arr[i + 1];
        }
        for (int l = n - 1, ans; l >= 1; l--) {
            for (int r = l + 1; r <= n; r++) {
                ans = Math.max(arr[l - 1] * arr[l] * arr[r + 1] + dp[l + 1][r],
                    arr[l - 1] * arr[r] * arr[r + 1] + dp[l][r - 1]);
                for (int k = l + 1; k < r; k++) {
                    ans = Math.max(ans, arr[l - 1] * arr[k] * arr[r + 1] + dp[l][k - 1] + dp[k + 1][r]);
                }
                dp[l][r] = ans;
            }
        }
        return dp[1][n];
    }
}
