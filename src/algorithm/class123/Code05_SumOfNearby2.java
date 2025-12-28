package algorithm.class123;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/19 8:30
 * // 每个节点距离k以内的权值和(迭代版)
 * // 给定一棵n个点的树，每个点有点权
 * // 到达每个节点的距离不超过k的节点就有若干个
 * // 把这些节点权值加起来，就是该点不超过距离k的点权和
 * // 打印每个节点不超过距离k的点权和
 * // 注意k并不大
 * // 测试链接 : https://www.luogu.com.cn/problem/P3047
 * // 提交以下的code，提交时请把类名改成"Main"
 * // C++这么写能通过，java会因为递归层数太多而爆栈
 * // java能通过的写法参考本节课Code05_SumOfNearby2文件
 */
public class Code05_SumOfNearby2 {
    public static int MAXN = 100001;
    public static int MAXK = 21;
    public static int n, k;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cnt;
    // sum[u][i] : 以u为头的整颗子树，距离不超过i的点权和
    public static int[][] sum = new int[MAXN][MAXK];
    // dp[u][i] : 以u为根的整颗树，距离不超过i的点权和
    public static int[][] dp = new int[MAXN][MAXK];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        build();
        for (int j = 1, u, v; j <= n - 1; j++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            // 每个点到距离不超过0的点权和，就是自己的权值
            sum[i][0] = (int) in.nval;
        }
        dfs1(1);
        for (int i = 0; i <= k; i++) {
            dp[1][i] = sum[1][i];
        }
        dfs2(1);

        for (int i = 1; i <= n; i++) {
            int ans = 0;
            for (int j = 0; j <= k; j++) {
                ans += dp[i][j];
            }
            System.out.println(ans);
        }
        out.flush();
        out.close();
        br.close();
    }

    public static int[][] ufe = new int[MAXN][3];
    public static int u, f, e, stackSize;

    public static void push(int u, int f, int e) {
        ufe[stackSize][0] = u;
        ufe[stackSize][1] = f;
        ufe[stackSize][2] = e;
        stackSize++;
    }

    public static void pop() {
        stackSize--;
        u = ufe[stackSize][0];
        f = ufe[stackSize][1];
        e = ufe[stackSize][2];
    }

    private static void dfs2(int root) {
        stackSize = 0;
        push(root, 0, -1);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                int v = to[e];
                push(u, f, e);
                if (v != f) {
                    dp[v][0] = sum[v][0];
                    dp[v][1] = sum[v][1] + dp[u][0];
                    for (int i = 2; i <= k; i++) {
                        dp[v][i] = sum[v][i] + dp[u][i - 1] - sum[v][i - 2];
                    }
                    push(to[e], u, -1);
                }
            }
        }
    }

    private static void dfs1(int root) {
        stackSize = 0;
        push(root, 0, -1);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (to[e] != f) {
                    push(to[e], u, -1);
                }
            } else {
                for (int e = head[u], v; e != 0; e = next[e]) {
                    v = to[e];
                    if (v != f) {
                        for (int j = 1; j <= k; j++) {
                            sum[u][j] += sum[v][j - 1];
                        }
                    }
                }
            }
        }
    }

    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    private static void build() {
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
    }
}
