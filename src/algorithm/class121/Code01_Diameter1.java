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
 */
public class Code01_Diameter1 {
    public static int MAXN = 500001;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cnt;
    public static int n;
    public static int start;
    public static int end;
    // 直径
    public static int diameter;
    // 头节点开始，到每个下方节点距离
    public static int[] dist = new int[MAXN];
    // 上一个节点
    public static int[] last = new int[MAXN];
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
        road();
        System.out.println(diameter);
        out.flush();
        out.close();
        br.close();
    }

    private static void road() {
        dfs(1, 0, 0);
        start = 1;
        for (int i = 2; i <= n; i++) {
            if (dist[i] > dist[start]) {
                start = i;
            }
        }
        dfs(start, 0, 0);
        end = 1;
        for (int i = 2; i <= n; i++) {
            if (dist[i] > dist[end]) {
                end = i;
            }
        }
        diameter = dist[end];
    }

    private static void dfs(int u, int f, int w) {
        dist[u] = dist[f] + w;
        last[u] = f;
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                dfs(to[e], u, weight[e]);
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
    }
}
