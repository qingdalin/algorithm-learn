package algorithm.class118lca01;

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
 * @date: 2024/9/21 10:43
 * https://www.luogu.com.cn/problem/P3379
 */
public class Code03_Tarjan2 {
    public static int MAXN = 500001;
    public static int[] stack = new int[MAXN];
    public static int[][] ufe = new int[MAXN][3];
    public static int stackSize, u, f, e;
    // 边的图
    public static int[] headEdge = new int[MAXN];
    public static int[] edgeNext = new int[MAXN << 1];
    public static int[] edgeTo = new int[MAXN << 1];
    public static int tcnt;

    // 问题的图
    public static int[] headQuery = new int[MAXN];
    public static int[] queryIndex = new int[MAXN << 1];
    public static int[] queryNext = new int[MAXN << 1];
    public static int[] queryTo = new int[MAXN << 1];
    public static int qcnt;

    public static int[] father = new int[MAXN];
    public static int[] ans = new int[MAXN];
    public static boolean[] visited = new boolean[MAXN];

    public static void build(int n) {
        tcnt = qcnt = 1;
        Arrays.fill(headEdge, 1, n + 1, 0);
        Arrays.fill(headQuery, 1, n + 1, 0);
        Arrays.fill(visited, 1, n + 1, false);
        for (int i = 1; i <= n; i++) {
            father[i] = i;
        }
    }
    public static void addEdge(int u, int v) {
        edgeNext[tcnt] = headEdge[u];
        edgeTo[tcnt] = v;
        headEdge[u] = tcnt++;
    }

    public static void addQuery(int u, int v, int i) {
        queryNext[qcnt] = headQuery[u];
        queryTo[qcnt] = v;
        queryIndex[qcnt] = i;
        headQuery[u] = qcnt++;
    }
    public static int find(int i) {
        int size = 0;
        while (i != father[i]) {
            stack[size++] = i;
            i = father[i];
        }
        while (size > 0) {
            father[stack[--size]] = i;
        }
        return i;
    }

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

//    public static int find(int x) {
//        if (x != father[x]) {
//            father[x] = find(father[x]);
//        }
//        return father[x];
//    }

    public static void tarjan(int root) {
        stackSize = 0;
        push(root, 0, -1);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                visited[u] = true;
                e = headEdge[u];
            } else {
                e = edgeNext[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (edgeTo[e] != f) {
                    push(edgeTo[e], u, -1);
                }
            } else {
                for (int e = headQuery[u], v; e != 0; e = queryNext[e]) {
                    v = queryTo[e];
                    if (visited[v]) {
                        ans[queryIndex[e]] = find(v);
                    }
                }
                father[u] = f;
            }
        }
    }
    public static void tarjan(int u, int f) {
        visited[u] = true;
        for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
            v = edgeTo[e];
            if (v != f) {
                tarjan(v, u);
                // 为了更好改迭代版本，这一行移动到108以后
                father[v] = u;
            }
        }
        for (int e = headQuery[u], v; e != 0; e = queryNext[e]) {
            v = queryTo[e];
            if (visited[v]) {
                ans[queryIndex[e]] = find(v);
            }
        }
        father[u] = f;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int n = (int) in.nval;
        in.nextToken(); int m = (int) in.nval;
        in.nextToken(); int root = (int) in.nval;
        build(n);
        for (int i = 1, u, v; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
        for (int i = 1, a, b; i <= m; i++) {
            in.nextToken(); a = (int) in.nval;
            in.nextToken(); b = (int) in.nval;
            addQuery(a, b, i);
            addQuery(b, a, i);
        }
        tarjan(root);
        for (int i = 1; i <= m; i++) {
            System.out.println(ans[i]);
        }
        out.flush();
        out.close();
        br.close();
    }
}
