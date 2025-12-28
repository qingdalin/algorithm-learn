package algorithm.class121;

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
 * @date: 2024/9/29 20:19
 * // 树的直径模版(两遍dfs)
 * // 给定一棵树，边权可能为负，求直径长度
 * // 测试链接 : https://www.luogu.com.cn/problem/U81904
 * // 提交以下的code，提交时请把类名改成"Main"
 * // 会有无法通过的用例，因为树上有负边
 * 树形dp方式, 使用全局直径变量diameter代替ans数组
 */
public class Code01_Diameter3 {
    public static int MAXN = 500001;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cnt;
    public static int n;
    public static int diameter;
    // 必须从u节点开始，到下方节点距离的最大长度
    public static int[] dist = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        build();
        for (int i = 1, u, v, w; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            in.nextToken(); w = (int) in.nval;
            addEdge(u, v, w);
            addEdge(v, u, w);
        }
        dp(1, 0);
        System.out.println(diameter);
        out.flush();
        out.close();
        br.close();
    }

    private static void dp(int u, int f) {
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                dp(to[e], u);
            }
        }
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                diameter = Math.max(diameter, dist[u] + dist[v] + weight[e]);
                dist[u] = Math.max(dist[u], dist[v] + weight[e]);
            }
        }
    }


    private static void addEdge(int u, int v, int w) {
        next[cnt] = head[u];
        to[cnt] = v;
        weight[cnt] = w;
        head[u] = cnt++;
    }

    private static void build() {
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
        Arrays.fill(dist, 1, n + 1, 0);
        diameter = Integer.MIN_VALUE;
    }
}
