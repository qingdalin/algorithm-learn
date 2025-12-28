package algorithm.class120;

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
 * @date: 2024/9/25 20:28
 * // 平衡行为
 * // 一共有n个节点，编号1~n，有n-1条边形成一棵树
 * // 返回重心点，返回重心点最大子树的节点数
 * // 树的重心第一种求解方式
 * // 以某个节点为根时，最大子树的节点数最少，那么这个节点是重心
 * // 测试链接 : http://poj.org/problem?id=1655
 * // 提交以下的code，提交时请把类名改成"Main"，可以直接通过
 */
public class Code01_BalancingAct {
    public static int MAXN = 20001;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt;
    public static int best, center, n;
    public static int[] size = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int T = (int) in.nval;
        for (int t = 0; t < T; t++) {
            in.nextToken(); n = (int) in.nval;
            build();
            for (int i = 1, u, v; i <= n - 1; i++) {
                in.nextToken(); u = (int) in.nval;
                in.nextToken(); v = (int) in.nval;
                addEdge(u, v);
                addEdge(v, u);
            }
            dfs(1, 0);
            System.out.println(center + " " + best);
        }
        out.flush();
        out.close();
        br.close();
    }

    private static void dfs(int u, int f) {
        size[u] = 1;
        // 子树的最大节点个数
        int maxSub = 0;
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs(v, u);
                size[u] += size[v];
                maxSub = Math.max(maxSub, size[v]);
            }
        }
        // 另外一颗子树等于n - 这颗子树大小
        maxSub = Math.max(maxSub, n - size[u]);
        if (maxSub < best || (maxSub == best && u < center)) {
            // 如果子树比最小的还要好，更新，或者有两个重心，收集小的编号
            best = maxSub;
            center = u;
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
        best = Integer.MAX_VALUE;
    }
}
