package leetcode.leetcodeweek.test2026.test492;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/8 13:47
 * https://leetcode.cn/problems/minimum-cost-to-partition-a-binary-string/
 */
public class Code492_04 {
    public static long minCost(String s, int encCost, int flatCost) {
        int n = s.length();
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + (s.charAt(i) - '0');
        }
        return dfs(0, n, sum, encCost, flatCost);
    }

    private static long dfs(int l, int r, int[] sum, int encCost, int flatCost) {
        // 不拆分
        int x = sum[r] - sum[l];
        long res = x > 0 ? (long) (r - l) * x * encCost : flatCost;
        // 拆分
        if ((r - l) % 2 == 0) {
            int m = (l + r) / 2;
            res = Math.min(res, dfs(l, m, sum, encCost, flatCost) + dfs(m, r, sum, encCost, flatCost));
        }
        return res;
    }
}
