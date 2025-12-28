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
 * @date: 2025/3/16 8:55
 * // 月下毛景树，java版
 * // 一共有n个节点，节点编号从1到n，所有节点连成一棵树
 * // 给定n-1条边，边的编号从1到n-1，每条边给定初始边权
 * // 会进行若干次操作，每条操作的类型是如下4种类型中的一种
 * // 操作 Change x v  : 第x条边的边权改成v
 * // 操作 Cover x y v : x号点到y号点的路径上，所有边权改成v
 * // 操作 Add x y v   : x号点到y号点的路径上，所有边权增加v
 * // 操作 Max x y     : x号点到y号点的路径上，打印最大的边权
 * // 1 <= n <= 10^5
 * // 任何时候的边权 <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P4315
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_UnderMoon1 {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[][] arr = new int[MAXN][3];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg = 0;

    public static int[] fa = new int[MAXN];
    public static int[] dep = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] top = new int[MAXN];
    public static int[] son = new int[MAXN];
    public static int[] dfn = new int[MAXN];
    public static int cntd = 0;

    public static int[] max = new int[MAXN << 2];
    public static int[] addTage = new int[MAXN << 2];
    public static boolean[] update = new boolean[MAXN << 2];
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
        max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
    }

    public static void addLazy(int i, int v) {
        max[i] += v;
        addTage[i] += v;
    }

    public static void updateLazy(int i, int v) {
        max[i] = v;
        addTage[i] = 0;
        change[i] = v;
        update[i] = true;
    }

    public static void down(int i) {
        if (update[i]) {
            updateLazy(i << 1, change[i]);
            updateLazy(i << 1 | 1, change[i]);
            update[i] = false;
        }
        if (addTage[i] != 0) {
            addLazy(i << 1, addTage[i]);
            addLazy(i << 1 | 1, addTage[i]);
            addTage[i] = 0;
        }
    }

    public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            updateLazy(i, jobv);
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

    public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            addLazy(i, jobv);
        } else {
            int mid = (l + r) / 2;
            down(i);
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static int query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        }
        int ans = Integer.MIN_VALUE;
        int mid = (l + r) / 2;
        down(i);
        if (jobl <= mid) {
            ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
        }
        if (jobr > mid) {
            ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
        }
        return ans;
    }

    public static void edgeUpdate(int ei, int val) {
        int x = arr[ei][0];
        int y = arr[ei][1];
        int down = Math.max(dfn[x], dfn[y]);
        update(down, down, val, 1, n, 1);
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
        update(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), v, 1, n, 1);
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

    public static int pathMax(int x, int y) {
        int ans = Integer.MIN_VALUE;
        while (top[x] != top[y]) {
            if (dep[top[x]] <= dep[top[y]]) {
                ans = Math.max(ans, query(dfn[top[y]], dfn[y], 1, n, 1));
                y = fa[top[y]];
            } else {
                ans = Math.max(ans, query(dfn[top[x]], dfn[x], 1, n, 1));
                x = fa[top[x]];
            }
        }
        ans = Math.max(ans, query(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), 1, n, 1));
        return ans;
    }

    public static void prepare() {
        for (int i = 1; i < n; i++) {
            addEdge(arr[i][0], arr[i][1]);
            addEdge(arr[i][1], arr[i][0]);
        }
        dfs1(1, 0);
        dfs2(1, 1);
        for (int i = 1; i < n; i++) {
            edgeUpdate(i, arr[i][2]);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 1; i < n; i++) {
            in.nextToken(); arr[i][0] = (int) in.nval;
            in.nextToken(); arr[i][1] = (int) in.nval;
            in.nextToken(); arr[i][2] = (int) in.nval;
        }
        prepare();
        // in.nextToken(); m = (int) in.nval;
        String op;
        int x, y, v;
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            op = in.sval;
            if (op.equals("Stop")) {
                break;
            }
            in.nextToken(); x = (int) in.nval;
            in.nextToken(); y = (int) in.nval;
            if (op.equals("Change")) {
                edgeUpdate(x, y);
            } else if (op.equals("Cover")) {
                in.nextToken(); v = (int) in.nval;
                pathUpdate(x, y, v);
            } else if (op.equals("Add")) {
                in.nextToken(); v = (int) in.nval;
                pathAdd(x, y, v);
            } else {
                out.println(pathMax(x, y));
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}
