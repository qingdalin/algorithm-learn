package leetcode.leetcodeweek.test2026.test504;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/31 10:10
 * https://leetcode.cn/contest/weekly-contest-504/problems/maximum-number-of-items-from-sale-i/description/
 */
public class Code504_02 {
    public static int MAXN = 1001;
    public static int MAXM = 1501;
    public static Map<Integer, Integer> cnt = new HashMap<>();
    public static int[][] dp = new int[MAXN][MAXM];
    public static int maximumSaleItems(int[][] items, int budget) {
        int[] f = new int[budget + 1];
        int minPrice = Integer.MAX_VALUE;
        for (int[] p : items) {
            int factor = p[0], price = p[1];
            minPrice = Math.min(price, minPrice);
            int cnt = 0;
            for (int[] q : items) {
                if (q[0] % factor == 0) {
                    cnt++;
                }
            }
            for (int j = budget; j >= price; j--) {
                f[j] = Math.max(f[j], f[j - price] + cnt);
            }
        }
        int ans = 0;
        for (int i = 0; i <= budget; i++) {
            ans = Math.max(ans, f[i] + (budget - i) / minPrice);
        }
        return ans;
    }

    private static int f(int[][] arr, int i, int budget) {
        if (i >= arr.length) {
            return 0;
        }
        if (budget < arr[i][1] || budget <= 0) {
            return 0;
        }
        if (dp[i][budget] != -1) {
            return dp[i][budget];
        }
        int p1 = f(arr, i + 1, budget);
        int p2 = Integer.MIN_VALUE;
        if (budget - arr[i][1] >= 0) {
            p2 = cnt.getOrDefault(i, 0);
            Integer val = cnt.remove(i);
            p2 += f(arr, i, budget - arr[i][1]) + 1;
            if (val != null) {
                cnt.put(i, val);
            }
        }
        int ans = Math.max(p1, p2);
        dp[i][budget] = ans;
        return ans;
    }

    private static void build(int[][] items, int budget) {
        cnt.clear();
        int n = items.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < budget + 1; j++) {
                dp[i][j] = -1;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                if (items[j][0] % items[i][0] == 0) {
                    cnt.merge(i, 1, Integer::sum);
                }
            }
        }
    }

    public static int maximumSaleItems1(int[][] items, int budget) {
        build(items, budget);
        int ans = f(items, 0, budget);
        return ans;
    }

    public static void main(String[] args) {
        int[][] arr = {
            {2,4},
            {3,2},
            {4,1},
            {6,4},
            {12,4}

//            {6,2},
//            {2,6},
//            {3,4},
        };
        int limit = 8;
        System.out.println(maximumSaleItems(arr, limit));
    }
}
