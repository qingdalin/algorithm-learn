package algorithm.class76dp11;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-22 13:46
 * 有一根长度为 n 个单位的木棍，棍上从 0 到 n 标记了若干位置。例如，长度为 6 的棍子可以标记如下：
 * 给你一个整数数组 cuts ，其中 cuts[i] 表示你需要将棍子切开的位置。
 *
 * 你可以按顺序完成切割，也可以根据需要更改切割的顺序。
 *
 * 每次切割的成本都是当前要切割的棍子的长度，切棍子的总成本是历次切割成本的总和。
 * 对棍子进行切割将会把一根木棍分成两根较小的木棍（这两根木棍的长度和就是切割前木棍的长度）。请参阅第一个示例以获得更直观的解释。
 *
 * 返回切棍子的 最小总成本 。
 * https://leetcode.cn/problems/minimum-cost-to-cut-a-stick/description/
 */
public class MinCost {
    public int minCost1(int n, int[] cuts) {
        int m = cuts.length;
        int[] arr = new int[m + 2];
        Arrays.sort(cuts);
        arr[0] = 0;
        for (int i = 1; i <= m; i++) {
            arr[i] = cuts[i - 1];
        }
        arr[m + 1] = n;
        int[][] dp = new int[m + 2][m + 2];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = -1;
            }
        }
        return f1(arr, 1, m, dp);
    }

    private int f1(int[] arr, int l, int r, int[][] dp) {
        if (l > r) {
            return 0;
        }
        if (l == r) {
            return arr[r + 1] - arr[l - 1];
        }
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        int ans = Integer.MAX_VALUE;
        for (int m = l; m <= r; m++) {
            ans = Math.min(ans, f1(arr, l, m - 1, dp) + f1(arr, m + 1, r, dp));
        }
        // 最后加上第一下棍子总长
        ans += (arr[r + 1] - arr[l - 1]);
        dp[l][r] = ans;
        return ans;
    }

    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
        int[] arr = new int[m + 2];
        Arrays.sort(cuts);
        arr[0] = 0;
        for (int i = 1; i <= m; i++) {
            arr[i] = cuts[i - 1];
        }
        arr[m + 1] = n;
        int[][] dp = new int[m + 2][m + 2];
        for (int i = 1; i <= m; i++) {
            dp[i][i] = arr[i + 1] - arr[i - 1];
        }
        for (int l = m - 1, next; l >= 1; l--) {
            for (int r = l +1; r <= m; r++) {
                next = Integer.MAX_VALUE;
                for (int k = l; k <= r; k++) {
                    next = Math.min(next, dp[l][k - 1] + dp[k + 1][r]);
                }
                dp[l][r] = next + arr[r + 1] - arr[l - 1];
            }
        }
        return dp[1][m];
    }
}
