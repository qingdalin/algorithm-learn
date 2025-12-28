package algorithm.class162;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/15 20:13
 * // 边权转化为点权的模版题，java版
 * // 一共有n个节点，给定n-1条边，节点连成一棵树，初始时所有边的权值为0
 * // 一共有m条操作，每条操作是如下2种类型中的一种
 * // 操作 P x y : x到y的路径上，每条边的权值增加1
 * // 操作 Q x y : x和y保证是直接连接的，查询他们之间的边权
 * // 1 <= n、m <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P3038
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_GrassPlanting1 {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg = 0;
    public static int[] fa = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] dep = new int[MAXN];
    public static int[] son = new int[MAXN];
    public static int[] top = new int[MAXN];
    public static int[] dfn = new int[MAXN];
    public static int cntd = 0;
    public static int[] sum = new int[MAXN << 2];
    public static int[] addTag = new int[MAXN << 2];

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void dfs1(int u, int f) {
        dep[u] = dep[f] + 1;
        fa[u] = f;
        siz[u] = 1;
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs1(v, u);
            }
        }
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                siz[u] += siz[v];
                if (son[u] == 0 || siz[son[u]] < siz[v]) {
                    son[u] = v;
                }
            }
        }
    }

    public static void dfs2(int u, int t) {
        top[u] = t;
        dfn[u] = ++cntd;
        if (son[u] == 0) {
            return;
        }
        dfs2(son[u], t);
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u] && v != son[u]) {
                dfs2(v, v);
            }
        }
    }

    public static int[][] fse = new int[MAXN][3];
    public static int stackSize, first, second, edge;

    public static void push(int fir, int sec, int edg) {
        fse[stackSize][0] = fir;
        fse[stackSize][1] = sec;
        fse[stackSize][2] = edg;
        stackSize++;
    }

    public static void pop() {
        stackSize--;
        first = fse[stackSize][0];
        second = fse[stackSize][1];
        edge = fse[stackSize][2];
    }

    public static void dfs3() {
        stackSize = 0;
        push(1, 0, -1);
        while (stackSize > 0) {
            pop();
            if (edge == -1) {
                fa[first] = second;
                siz[first] = 1;
                dep[first] = dep[second] + 1;
                edge = head[first];
            } else {
                edge = next[edge];
            }
            if (edge != 0) {
                push(first, second, edge);
                if (to[edge] != second) {
                    push(to[edge], first, -1);
                }
            } else {
                for(int e = head[first], v; e > 0; e = next[e]) {
                    v = to[e];
                    if (v != second) {
                        siz[first] += siz[v];
                        if (son[first] == 0 || siz[son[first]] < siz[v]) {
                            son[first] = v;
                        }
                    }
                }
            }
        }
    }

    public static void dfs4() {
        stackSize = 0;
        push(1, 1, -1);
        while (stackSize > 0) {
            pop();
            if (edge == -1) {
                top[first] = second;
                dfn[first] = ++cntd;
                if (son[first] == 0) {
                    continue;
                }
                push(first, second, -2);
                push(son[first], second, -1);
                continue;
            } else if (edge == -2) {
                edge = head[first];
            } else {
                edge = next[edge];
            }
            if (edge != 0) {
                push(first, second, edge);
                if (to[edge] != fa[first] && to[edge] != son[first]) {
                    push(to[edge], to[edge], -1);
                }
            }
        }
    }

    public static void up(int i) {
        sum[i] = sum[i << 1] + sum[i << 1 | 1];
    }

    public static void lazy(int i, int v, int n) {
        sum[i] += n * v;
        addTag[i] += v;
    }

    public static void down(int i, int ln, int rn) {
        if (addTag[i] != 0) {
            lazy(i << 1, addTag[i], ln);
            lazy(i << 1 | 1, addTag[i], rn);
            addTag[i] = 0;
        }
    }

    public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) / 2;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static int query(int jobi, int l, int r, int i) {
        if (l == r) {
            return sum[i];
        }
        int mid = (l + r) / 2;
        down(i, mid - l + 1, r - mid);
        if (jobi <= mid) {
            return query(jobi, l, mid, i << 1);
        } else {
            return query(jobi, mid + 1, r, i << 1 | 1);
        }
    }

    public static void pathAdd(int x, int y, int v) {
        while (top[x] != top[y]) {
            if (dep[top[x]] <= dep[top[y]]) {
                add(dfn[top[y]], dfn[y], v, 1, n, 1);
                y = fa[top[y]];
            } else {
                add(dfn[top[x]], dfn[x], v, 1, n, 1);
                x = fa[top[x]];
            }
        }
        add(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), v, 1, n, 1);
    }

    public static int pathQuery(int x, int y) {
        int down = Math.max(dfn[x], dfn[y]);
        return query(down, 1, n, 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1, u, v; i < n; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
//         dfs1(1, 0);
        dfs3();
//         dfs2(1, 1);
        dfs4();
        String op;
        for (int i = 1, x, y, v; i <= m; i++) {
            in.nextToken(); op = in.sval;
            in.nextToken(); x = (int) in.nval;
            in.nextToken(); y = (int) in.nval;
            if (op.equals("P")) {
                pathAdd(x, y, 1);
            } else {
                out.println(pathQuery(x, y));
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}
