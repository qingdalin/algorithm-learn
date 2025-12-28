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
 * @date: 2024/10/15 20:13
 * // 最大深度和(迭代版)
 * // 给定一棵n个点的树，找到一个节点，使得以这个节点为根时，到达所有节点的深度之和最大
 * // 如果有多个节点满足要求，返回节点编号最小的
 * // 测试链接 : https://www.luogu.com.cn/problem/P3478
 * // 提交以下的code，提交时请把类名改成"Main"
 * // C++这么写能通过，java会因为递归层数太多而爆栈
 * // java能通过的写法参考本节课Code01_MaximizeSumOfDeeps2文件
 */
public class Code01_MaximizeSumOfDeeps2 {
    public static int MAXN = 1000001;
    public static int n;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt;
    public static int[] size = new int[MAXN];
    public static long[] sum = new long[MAXN];
    public static long[] dp = new long[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        build();
        for (int i = 1, u, v; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
        dfs1(1);
        dp[1] = sum[1];
        dfs2(1);
        long max = Long.MIN_VALUE;
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (dp[i] > max) {
                max = dp[i];
                ans = i;
            }
        }
        System.out.println(ans);
        out.flush();
        out.close();
        br.close();
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
                push(u, f, e);
                int v = to[e];
                if (v != f) {
                    dp[v] = dp[u] - size[v] + n - size[v];
                    push(v, u, -1);
                }
            }
        }
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
                size[u] = 1;
                sum[u] = 0;
                for (int e = head[u], v; e != 0; e = next[e]) {
                    v = to[e];
                    if (v != f) {
                        size[u] += size[v];
                        sum[u] += sum[v] + size[v];
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
