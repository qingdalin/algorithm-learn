package algorithm.class126;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/27 11:55
 * // 节点最多经过两次的tsp问题
 * // 给定有n个地点，用m条边无向边连接，每条边有权值
 * // 你可以任选一点出发，目标是经过所有的点，最终不必回到出发点
 * // 并且每个点最多可以到达两次
 * // 返回总路程最小是多少
 * // 1 <= n <= 10
 * // 1 <= m <= 100
 * // 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=3001
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
public class Code03_TspTwice {
    public static int MAXN = 10;
    public static int MAXS = (int) Math.pow(3, MAXN);
    public static int n, m, maxs, size;
    public static int[][] grid = new int[MAXN][MAXN];
    public static int[][] dp = new int[MAXN][MAXS];
    public static int[] complete = new int[1 << MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken(); m = (int) in.nval;
            build();
            for (int i = 1, u, v, w; i <= m; i++) {
                in.nextToken(); u = (int) in.nval - 1;
                in.nextToken(); v = (int) in.nval - 1;
                in.nextToken(); w = (int) in.nval;
                if (w < grid[u][v]) {
                    grid[u][v] = grid[v][u] = w;
                }
            }
            int ans = compute();
            System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
        }
        out.flush();
        out.close();
        br.close();
    }

    private static int compute() {
        int ans = Integer.MAX_VALUE;
        for (int k = 0; k < size; k++) {
            for (int i = 0, bit = 1; i < n; i++, bit *= 3) {
                ans = Math.min(ans, f(i, complete[k] - bit));
            }
        }
        return ans;
    }

    // 最后来到的是i，前边的状态是s（不含i）消耗的最小路程是多少
    private static int f(int i, int s) {
        if (s == 0) {
            // s == 0,说明i是出发点，直径返回0
            return 0;
        }
        if (dp[i][s] != -1) {
            return dp[i][s];
        }
        int ans = Integer.MAX_VALUE;
        for (int j = 0, bit = 1, pre; j < n; j++, bit *= 3) {
            if (s / bit % 3 > 0) {
                // s / bit % 3 > 0 说明可以从前方城市到当前城市
                pre = f(j, s - bit);
                if (pre != Integer.MAX_VALUE && grid[j][i] != Integer.MAX_VALUE) {
                    ans = Math.min(ans, pre + grid[j][i]);
                }
            }
        }
        dp[i][s] = ans;
        return ans;
    }

    private static void build() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.MAX_VALUE;
            }
        }
        maxs = (int) Math.pow(3, n);
        for (int i = 0; i < n; i++) {
            for (int s = 0; s < maxs; s++) {
                dp[i][s] = -1;
            }
        }
        size = 0;
        dfs(0, 1, 0);
    }

    private static void dfs(int i, int bit, int s) {
        if (i == n) {
            complete[size++] = s;
        } else {
            dfs(i + 1, bit * 3, s + bit);
            dfs(i + 1, bit * 3, s + bit * 2);
        }
    }
}
