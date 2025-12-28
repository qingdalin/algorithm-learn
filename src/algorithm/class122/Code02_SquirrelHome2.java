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
 * @date: 2024/10/12 20:00
 * // 松鼠的新家(迭代版)
 * // 有n个节点形成一棵树
 * // 给定一个由点编号组成的数组，表示松鼠依次要去往的地点
 * // 松鼠每走到一个节点都必须拿一个糖果，否则松鼠就停止前进
 * // 松鼠来到最后一个地点时不需要吃糖果
 * // 打印每个节点上至少准备多少糖果才能让松鼠依次走完数组里的节点
 * // 测试链接 : https://www.luogu.com.cn/problem/P3258
 * // 提交以下的code，提交时请把类名改成"Main"
 * // C++这么写能通过，java会因为递归层数太多而爆栈
 * // java能通过的写法参考本节课Code02_SquirrelHome2文件
 */
public class Code02_SquirrelHome2 {
    public static int MAXN = 300001;
    public static int n;
    // 链式前向星建图
    public static int[] headEdge = new int[MAXN];
    public static int[] edgeNext = new int[MAXN << 1];
    public static int[] edgeTo = new int[MAXN << 1];
    public static int tcnt;

    // 每个节点的糖果数量
    public static int[] num = new int[MAXN];
    // 经过的节点
    public static int[] travel = new int[MAXN];

    // tarjan算法
    public static int[] headQuery = new int[MAXN];
    public static int[] queryNext = new int[MAXN << 1];
    public static int[] queryTo = new int[MAXN << 1];
    public static int[] queryIndex = new int[MAXN << 1];
    public static int qcnt;
    public static boolean[] visited = new boolean[MAXN];
    public static int[] unionFind = new int[MAXN];
    // 每个节点的父亲节点
    public static int[] father = new int[MAXN];
    // ans数组是tarjan算法的输出结果，记录每次旅行两端点的最低公共祖先
    public static int[] ans = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        build();
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            travel[i] = (int) in.nval;
        }
        for (int i = 1, u, v; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
        for (int i = 1, u, v; i <= n - 1; i++) {
            u = travel[i];
            v = travel[i + 1];
            addQuery(u, v, i);
            addQuery(v, u, i);
        }
        compute();
        for (int i = 1; i <= n; i++) {
            System.out.println(num[i]);
        }
        out.flush();
        out.close();
        br.close();
    }

    private static void compute() {
        tarjan(1);
        for (int i = 1, u, v, lca, lcaFather; i <= n - 1; i++) {
            u = travel[i];
            v = travel[i + 1];
            lca = ans[i];
            lcaFather = father[lca];
            num[u]++;
            num[v]++;
            num[lca]--;
            num[lcaFather]--;
        }
        dfs(1);
        for (int i = 2; i <= n; i++) {
            num[travel[i]]--;
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
    private static void dfs(int root) {
        stackSize = 0;
        push(root, 0, -1);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
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
                for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
                    v = edgeTo[e];
                    if (v != f) {
                        num[u] += num[v];
                    }
                }
            }
        }
    }

    private static void tarjan(int root) {
        stackSize = 0;
        push(root, 0, -1);
        while (stackSize > 0) {
            pop();
            visited[u] = true;
            if (e == -1) {
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
                unionFind[u] = f;
            }
        }
    }

    public static int[] stack = new int[MAXN];
    private static int find(int i) {
        int size = 0;
        while (i != unionFind[i]) {
            stack[size++] = i;
            i = unionFind[i];
        }
        while (size > 0) {
            unionFind[stack[--size]] = i;
        }
        return i;
    }
//    private static int find(int i) {
//        if (i != unionFind[i]) {
//            unionFind[i] = find(unionFind[i]);
//        }
//        return unionFind[i];
//    }

    private static void addQuery(int u, int v, int i) {
        queryNext[qcnt] = headQuery[u];
        queryTo[qcnt] = v;
        queryIndex[qcnt] = i;
        headQuery[u] = qcnt++;
    }

    private static void addEdge(int u, int v) {
        edgeNext[tcnt] = headEdge[u];
        edgeTo[tcnt] = v;
        headEdge[u] = tcnt++;
    }

    private static void build() {
        tcnt = qcnt = 1;
        Arrays.fill(num, 1, n + 1, 0);
        Arrays.fill(headEdge, 1, n + 1, 0);
        Arrays.fill(headQuery, 1, n + 1, 0);
        Arrays.fill(visited, 1, n + 1, false);
        for (int i = 1; i <= n; i++) {
            unionFind[i] = i;
        }
    }
}
