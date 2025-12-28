package leetcode.study.leetcode001_200;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/7 20:17
 * https://leetcode.cn/problems/dungeon-game/
 */
public class Leetcode174CalculateMinimumHP {
    public static int n, m;
    public int calculateMinimumHP(int[][] arr) {
        n = arr.length;
        m = arr[0].length;
        return getAns(arr);
    }

    public static int dp(int[][] arr) {
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Integer.MAX_VALUE;            }
        }
        dp[n][m - 1] = dp[n - 1][m] = 1;
        for (int i = n - 1; i >= 0; i--) {
            for(int j = m - 1; j >= 0; j--) {
                int min = Math.min(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = Math.max(min - arr[i][j], 1);
            }
        }
        return dp[0][0];
    }

    private static Integer getInteger(int[][] arr) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        boolean[][] vis = new boolean[n][m];
        int ans = 0;
        heap.add(new int[] {0, 0, 0});
        while (!heap.isEmpty()) {
            int size = heap.size();
            for (int i = 0; i < size; i++) {
                int[] cur = heap.poll();
                int x = cur[0];
                int y = cur[1];
                int blood = cur[2];
                if (x == n - 1 && y == n - 1 && 1 - blood > 0) {
                    return Math.max(1, 1 - blood);
                }
                if (x + 1 < n) {
                    heap.add(new int[] {x + 1, y, blood + arr[x + 1][y]});
                }
                if (y + 1 < m) {
                    heap.add(new int[] {x, y + 1, blood + arr[x][y + 1]});
                }
            }
        }
        return null;
    }

    private static int getAns(int[][] arr) {
        int l = 1, r = 1000;
        int ans = 0;
//        int[][] dp = new int[n][m];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                dp[i][j] = -1;
//            }
//        }
        Map<String, Boolean> map = new HashMap<>();
        while (l <= r) {
            int mid = (l + r) / 2;
            if (arr[0][0] + mid > 0 && dfs(0, 0, arr, mid + arr[0][0], map)) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    public static boolean dfs(int i, int j, int[][] arr, int num, Map<String, Boolean> map) {
        String k = i + "#" + j + "#" + num;
        if (map.containsKey(k)) {
            return map.get(k);
        }
        if (i == n - 1 && j == m - 1) {
            return true;
        }
        if (num <= 0) {
            return false;
        }
        if (i < 0 || i >= n || j < 0 || j >= m) {
            return false;
        }
        boolean p1 = false;
        boolean p2 = false;
        if (i + 1 < n && num + arr[i + 1][j] > 0) {
            p1 = dfs(i + 1, j, arr, num + arr[i + 1][j], map);
        }
        if (j + 1 < m && num + arr[i][j  + 1] > 0) {
            p2 = dfs(i, j + 1, arr, num + arr[i][j + 1], map);
        }
        boolean res = p1 || p2;
        map.put(k, res);
        return res;
    }
}
