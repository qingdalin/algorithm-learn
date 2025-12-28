package algorithm.class122;

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
 * @date: 2024/10/13 9:42
 * // 运输计划(递归版)
 * // 有n个节点，给定n-1条边使其连接成一棵树，每条边有正数边权
 * // 给定很多运输计划，每个运输计划(a,b)表示从a去往b
 * // 每个运输计划的代价就是沿途边权和，运输计划之间完全互不干扰
 * // 你只能选择一条边，将其边权变成0
 * // 你的目的是让所有运输计划代价的最大值尽量小
 * // 返回所有运输计划代价的最大值最小能是多少
 * // 测试链接 : https://www.luogu.com.cn/problem/P2680
 * // 提交以下的code，提交时请把类名改成"Main"
 * // C++这么写能通过，java会因为递归层数太多而爆栈
 * // java能通过的写法参考本节课Code05_TransportPlan2文件
 */
public class Code05_TransportPlan1 {
    public static int MAXN = 300001;
    public static int MAXM = 300001;
    public static int n, m, maxCost;
    public static int[] num = new int[MAXN];
    public static int[] headEdge = new int[MAXN];
    public static int[] edgeNext = new int[MAXN << 1];
    public static int[] edgeTo = new int[MAXN << 1];
    public static int[] edgeWeight = new int[MAXN << 1];
    public static int tcnt;
    public static int[] headQuery = new int[MAXN];
    public static int[] queryNext = new int[MAXM << 1];
    public static int[] queryTo = new int[MAXM << 1];
    public static int[] queryIndex = new int[MAXM << 1];
    public static int[] lca = new int[MAXM];
    public static int[] cost = new int[MAXM];
    public static int[] quesu = new int[MAXM];
    public static int[] quesv = new int[MAXM];
    public static int[] distance = new int[MAXN];
    public static int[] unionfind = new int[MAXN];
    public static boolean[] visited = new boolean[MAXN];
    public static int qcnt;
    public static int atLeast; // 至少减去多少的权值
    public static int beyond; // 超过的边数
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        build();
        for (int i = 1, u, v, w; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            in.nextToken(); w = (int) in.nval;
            addEdge(u, v, w);
            addEdge(v, u, w);
        }
        for (int i = 1, u, v; i <= m; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            quesu[i] = u;
            quesv[i] = v;
            addQuery(u, v, i);
            addQuery(v, u, i);
        }
        System.out.println(compute());
        out.flush();
        out.close();
        br.close();
    }

    private static int compute() {
        tarjan(1, 0, 0);
        int l = 0, r = maxCost, mid;
        int ans = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (f(mid)) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    private static boolean f(int limit) {
        atLeast = maxCost - limit;
        beyond = 0;
        Arrays.fill(num, 1, n + 1, 0);
        for (int i = 1, v; i <= m; i++) {
            if (cost[i] > limit) {
                num[quesu[i]]++;
                num[quesv[i]]++;
                num[lca[i]] -= 2;
                beyond++;
            }
        }
        return beyond == 0 || dfs(1, 0, 0);
    }

    // 边数正好相等且权值大于atLeast返回true
    private static boolean dfs(int u, int f, int w) {
        for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
            v = edgeTo[e];
            if (v != f) {
                if (dfs(v, u, edgeWeight[e])) {
                    return true;
                }
            }
        }
        for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
            v = edgeTo[e];
            if (v != f) {
                num[u] += num[v];
            }
        }
        return num[u] == beyond && w >= atLeast;
    }

    private static void tarjan(int u, int f, int w) {
        visited[u] = true;
        distance[u] = distance[f] + w;
        for (int e = headEdge[u]; e != 0; e = edgeNext[e]) {
            if (edgeTo[e] != f) {
                tarjan(edgeTo[e], u, edgeWeight[e]);
            }
        }
        for (int e = headQuery[u], v; e != 0; e = queryNext[e]) {
            v = queryTo[e];
            if (visited[v]) {
                int i = queryIndex[e];
                lca[i] = find(v);
                cost[i] = distance[u] + distance[v] - 2 * distance[lca[i]];
                maxCost = Math.max(maxCost, cost[i]);
            }
        }
        unionfind[u] = f;
    }

    private static int find(int i) {
        if (i != unionfind[i]) {
            unionfind[i] = find(unionfind[i]);
        }
        return unionfind[i];
    }

    private static void addQuery(int u, int v, int i) {
        queryNext[qcnt] = headQuery[u];
        queryTo[qcnt] = v;
        queryIndex[qcnt] = i;
        headQuery[u] = qcnt++;
    }

    private static void addEdge(int u, int v, int w) {
        edgeNext[tcnt] = headEdge[u];
        edgeTo[tcnt] = v;
        edgeWeight[tcnt] = w;
        headEdge[u] = tcnt++;
    }

    private static void build() {
        qcnt = tcnt = 1;
        maxCost = 0;
        Arrays.fill(headEdge, 1, n + 1, 0);
        Arrays.fill(headQuery, 1, n + 1, 0);
        Arrays.fill(visited, 1, n + 1, false);
        for (int i = 1; i <= n; i++) {
            unionfind[i] = i;
        }
    }
}
