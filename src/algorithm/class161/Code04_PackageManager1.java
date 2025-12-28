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
 * @date: 2025/3/15 10:14
 * // 软件包管理器，java版
 * // 一共有n个软件，编号0~n-1，0号软件不依赖任何软件，其他每个软件都仅依赖一个软件
 * // 依赖关系由数组形式给出，题目保证不会出现循环依赖
 * // 一开始所有软件都是没有安装的，如果a依赖b，那么安装a需要安装b，同时卸载b需要卸载a
 * // 一共有m条操作，每种操作是如下2种类型中的一种
 * // 操作 install x    : 安装x，如果x已经安装打印0，否则打印有多少个软件的状态需要改变
 * // 操作 uninstall x  : 卸载x，如果x没有安装打印0，否则打印有多少个软件的状态需要改变
 * // 1 <= n、m <= 10^6
 * // 测试链接 : https://www.luogu.com.cn/problem/P2146
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_PackageManager1 {
    public static int MAXN = 100001;
    public static int n, m;
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
    public static int cntd = 0;

    public static int[] sum = new int[MAXN << 2];
    public static int[] change = new int[MAXN << 2];
    public static boolean[] update = new boolean[MAXN << 2];

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void dfs1(int u, int f) {
        fa[u] = f;
        dep[u] = dep[f] + 1;
        siz[u] = 1;
        for (int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs1(v, u);
            }
        }
        for (int e = head[u], v; e > 0; e = next[e]) {
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
        for (int e = head[u], v; e > 0; e = next[e]) {
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
            // edge == -1，表示第一次来到当前节点，并且先处理重儿子
            if (edge == -1) {
                top[first] = second;
                dfn[first] = ++cntd;
                // 错误写法 seg[t] = cntd;
                if (son[first] == 0) {
                    continue;
                }
                push(first, second, -2);
                push(son[first], second, -1);
                continue;
            } else if (edge == -2) {
                // edge == -2，表示处理完当前节点的重儿子，回到了当前节点
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
        sum[i] = n * v;
        change[i] = v;
        update[i] = true;
    }

    public static void down(int i, int ln, int rn) {
        if (update[i]) {
            lazy(i << 1, change[i], ln);
            lazy(i << 1 | 1, change[i], rn);
            update[i] = false;
        }
    }

    public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) / 2;
            down(i, mid - l + 1, r - mid);
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
        int mid = (l + r) / 2;
        down(i, mid - l + 1, r - mid);
        int ans = 0;
        if (jobl <= mid) {
            ans += query(jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
        }
        return ans;
    }

    public static void pathUpdate(int x, int v) {
        int y = 1;
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

    public static int install(int x) {
        int pre = sum[1];
        pathUpdate(x, 1);
        int post = sum[1];
        return Math.abs(pre - post);
    }

    public static int uninstall(int x) {
        int pre = sum[1];
        update(dfn[x], dfn[x] + siz[x] - 1, 0, 1, n, 1);
        int post = sum[1];
        return Math.abs(pre - post);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int v = 2, u; v <= n; v++) {
            in.nextToken(); u = (int) in.nval + 1;
            addEdge(u, v);
        }
//        dfs1(1, 0);
        dfs3();
//        dfs2(1, 1);
        dfs4();
        String op;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1, x; i <= m; i++) {
            in.nextToken(); op = in.sval;
            // 错误写法 in.nextToken(); x = (int) in.nval;
            in.nextToken(); x = (int) in.nval + 1;
            if (op.equals("install")) {
                out.println(install(x));
            } else {
                out.println(uninstall(x));
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}
