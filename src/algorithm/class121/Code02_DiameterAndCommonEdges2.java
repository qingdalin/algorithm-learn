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
 * @date: 2024/9/30 20:28
 * // 所有直径的公共部分(迭代版)
 * // 给定一棵树，边权都为正
 * // 打印直径长度、所有直径的公共部分有几条边
 * // 测试链接 : https://www.luogu.com.cn/problem/P3304
 * // 提交以下的code，提交时请把类名改成"Main"
 * // C++这么写能通过，java会因为递归层数太多而爆栈
 * // java能通过的写法参考本节课Code02_DiameterAndCommonEdges2文件
 */
public class Code02_DiameterAndCommonEdges2 {
    public static int MAXN = 200001;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cnt;
    public static int n;
    public static int start;
    public static int end;
    // 直径
    public static long diameter;
    // 头节点开始，到每个下方节点距离
    public static long[] dist = new long[MAXN];
    // 上一个节点
    public static int[] last = new int[MAXN];
    // 直径上的边，走过记录一下
    public static boolean[] diameterPath = new boolean[MAXN];
    public static int commonEdge;
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
        compute();
        System.out.println(diameter);
        System.out.println(commonEdge);
        out.flush();
        out.close();
        br.close();
    }

    private static void compute() {
        road();
        for (int i = end; i != 0; i = last[i]) {
            // 将已经走过的直径设置true
            diameterPath[i] = true;
        }
        int l = start;
        int r = end;
        long maxDist = 0;
        for (int i = last[end]; i != start; i = last[i]) {
            maxDist = maxDistanceExceptDiameter(i);
            if (maxDist == diameter - dist[i]) {
                r = i;
            }
            if (maxDist == dist[i] && l == start) {
                l = i;
            }
        }
        if (l == r) {
            commonEdge = 0;
        } else {
            commonEdge = 1;
            for (int e = last[r]; e != l; e = last[e]) {
                commonEdge++;
            }
        }
    }

    public static int[][] ufe = new int[MAXN][3];
    public static long[] distStack = new long[MAXN];
    public static int stackSize;
    public static int u, f, e;
    public static long c;

    public static void push(int u, int f, int e, long c) {
        ufe[stackSize][0] = u;
        ufe[stackSize][1] = f;
        ufe[stackSize][2] = e;
        distStack[stackSize] = c;
        stackSize++;
    }

    public static void pop() {
        stackSize--;
        u = ufe[stackSize][0];
        f = ufe[stackSize][1];
        e = ufe[stackSize][2];
        c = distStack[stackSize];
    }

    private static long maxDistanceExceptDiameter(int root) {
        stackSize = 0;
        push(root, 0, -1, 0);
        long ans = c;
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e, c);
                if (!diameterPath[to[e]] && to[e] != f) {
                    push(to[e], u, -1, c + weight[e]);
                }
            } else {
                ans = Math.max(ans, c);
            }
        }
        return ans;
    }

    private static void road() {
        dfs(1);
        start = 1;
        for (int i = 2; i <= n; i++) {
            if (dist[i] > dist[start]) {
                start = i;
            }
        }
        dfs(start);
        end = 1;
        for (int i = 2; i <= n; i++) {
            if (dist[i] > dist[end]) {
                end = i;
            }
        }
        diameter = dist[end];
    }

    private static void dfs(int root) {
        stackSize = 0;
        push(root, 0, -1, 0);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                last[u] = f;
                dist[u] = c;
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e, c);
                if (to[e] != f) {
                    push(to[e], u, -1, c + weight[e]);
                }
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
        Arrays.fill(diameterPath, 1, n + 1, false);
    }
}
