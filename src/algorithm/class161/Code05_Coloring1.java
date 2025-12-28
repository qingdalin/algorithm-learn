package algorithm.class161;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/15 11:13
 * // 染色，java版
 * // 一共有n个节点，给定n-1条边，节点连成一棵树，每个节点给定初始颜色值
 * // 连续相同颜色被认为是一段，变化了就认为是另一段
 * // 比如，112221，有三个颜色段，分别为 11、222、1
 * // 一共有m条操作，每种操作是如下2种类型中的一种
 * // 操作 C x y z : x到y的路径上，每个节点的颜色都改为z
 * // 操作 Q x y   : x到y的路径上，打印有几个颜色段
 * // 1 <= n、m <= 10^5
 * // 1 <= 任何时候的颜色值 <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P2486
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_Coloring1 {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg = 0;

    public static int[] dep = new int[MAXN];
    public static int[] fa = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] son = new int[MAXN];
    public static int[] top = new int[MAXN];
    public static int[] dfn = new int[MAXN];
    public static int[] seg = new int[MAXN];
    public static int cntd = 0;

    public static int[] sum = new int[MAXN << 2];
    public static int[] lcolor = new int[MAXN << 2];
    public static int[] rcolor = new int[MAXN << 2];
    public static int[] change = new int[MAXN << 2];

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void dfs1(int u, int f) {
        dep[u] = dep[f] + 1;
        siz[u] = 1;
        fa[u] = f;
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
        seg[cntd] = u;
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
                for (int e = head[first], v; e > 0; e = next[e]) {
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
                seg[cntd] = first;
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
        if (rcolor[i << 1] == lcolor[i << 1 | 1]) {
            sum[i]--;
        }
        lcolor[i] = lcolor[i << 1];
        rcolor[i] = rcolor[i << 1 | 1];
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = 1;
            lcolor[i] = rcolor[i] = arr[seg[l]];
        } else {
            int mid = (l + r) / 2;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
    }

    public static void lazy(int i, int v) {
        sum[i] = 1;
        lcolor[i] = v;
        rcolor[i] = v;
        change[i] = v;
    }

    public static void down(int i) {
        if (change[i] != 0) {
            lazy(i << 1, change[i]);
            lazy(i << 1 | 1, change[i]);
            change[i] = 0;
        }
    }

    public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv);
        } else {
            int mid = (l + r) / 2;
            down(i);
            if (jobl <= mid) {
                update(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static int query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        down(i);
        int mid = (l + r) / 2;
        if (jobr <= mid) {
            return query(jobl, jobr, l, mid, i << 1);
        } else if (jobl > mid) {
            return query(jobl, jobr, mid + 1, r, i << 1 | 1);
        } else {
            int ans = query(jobl, jobr, l, mid, i << 1) + query(jobl, jobr, mid + 1, r, i << 1 | 1);
            if (rcolor[i << 1] == lcolor[i << 1 | 1]) {
                ans--;
            }
            return ans;
        }
    }

    public static int pointColor(int jobi, int l, int r, int i) {
        if (l == r) {
            // 错误写法 return sum[i];
            return lcolor[i];
        }
        down(i);
        int mid = (l + r) / 2;
        if (jobi <= mid) {
            return pointColor(jobi, l, mid, i << 1);
        } else {
            return pointColor(jobi, mid + 1, r, i << 1 | 1);
        }
    }

    public static void pathUpdate(int x, int y, int v) {
        while (top[x] != top[y]) {
            if (dep[top[x]] <= dep[top[y]]) {
                update(dfn[top[y]], dfn[y], v, 1, n, 1);
                y = fa[top[y]];
            } else {
                update(dfn[top[x]], dfn[x], v, 1, n, 1);
                x = fa[top[x]];
            }
        }
        update(Math.min(dfn[x], dfn[y]), Math.max(dfn[x], dfn[y]), v, 1, n, 1);
    }

    public static int pathColors(int x, int y) {
        int ans = 0, sonc, fac;
        while (top[x] != top[y]) {
            if (dep[top[x]] <= dep[top[y]]) {
                ans += query(dfn[top[y]], dfn[y], 1, n, 1);
                sonc = pointColor(dfn[top[y]], 1, n, 1);
                fac = pointColor(dfn[fa[top[y]]], 1, n, 1);
                y = fa[top[y]];
            } else {
                ans += query(dfn[top[x]], dfn[x], 1, n, 1);
                sonc = pointColor(dfn[top[x]], 1, n, 1);
                fac = pointColor(dfn[fa[top[x]]], 1, n, 1);
                x = fa[top[x]];
            }
            if (sonc == fac) {
                ans--;
            }
        }
        ans += query(Math.min(dfn[x], dfn[y]), Math.max(dfn[x], dfn[y]), 1, n, 1);
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); arr[i] = (int) in.nval;
        }
        for (int i = 1, u, v; i < n; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
//         dfs1(1, 0);
        dfs3();
//        dfs2(1, 1);
        dfs4();
        build(1, n, 1);
        String op;
        for (int i = 1, x, y, v; i <= m; i++) {
            in.nextToken(); op = in.sval;
            in.nextToken(); x = (int) in.nval;
            in.nextToken(); y = (int) in.nval;
            if (op.equals("C")) {
                in.nextToken(); v = (int) in.nval;
                pathUpdate(x, y, v);
            } else {
                out.println(pathColors(x, y));
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}
