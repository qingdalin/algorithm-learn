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
 * @date: 2025/3/14 19:27
 * // 重链剖分解决LCA查询，java版
 * // 一共有n个节点，给定n-1条边，节点连成一棵树，给定头节点编号root
 * // 一共有m条查询，每条查询给定a和b，打印a和b的最低公共祖先
 * // 请用树链剖分的方式实现
 * // 1 <= n、m <= 5 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P3379
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_LCA1 {
    public static int MAXN = 500001;
    public static int n,m,root;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt = 0;

    public static int[] dep = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] fa = new int[MAXN];
    public static int[] son = new int[MAXN];
    public static int[] top = new int[MAXN];

    public static void addEdge(int u, int v) {
        next[++cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt;
    }

    public static void dfs1(int u, int f) {
        dep[u] = dep[f] + 1;
        siz[u] = 1;
        fa[u] = f;
        for (int e = head[u]; e > 0; e = next[e]) {
            if (to[e] != f) {
                dfs1(to[e], u);
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
        if (son[u] == 0) {
            return;
        }
        dfs2(son[u], t);
        for (int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u] && son[u] != v) {
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
        push(root, 0, - 1);
        while (stackSize > 0) {
            pop();
            if (edge == -1) {
                dep[first] = dep[second] + 1;
                siz[first] = 1;
                fa[first] = second;
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
        push(root, root, -1);
        while (stackSize > 0) {
            pop();
            if (edge == -1) {
                top[first] = second;
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

    public static int lca(int a, int b) {
        while (top[a] != top[b]) {
            if (dep[top[a]] <= dep[top[b]]) {
                b = fa[top[b]];
            } else {
                a = fa[top[a]];
            }
        }
        return dep[a] <= dep[b] ? a : b;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); root = (int) in.nval;
        for (int i = 1, u, v; i < n; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
        // dfs1(root, 0);
        dfs3();
        // dfs2(root, root);
        dfs4();
        for (int i = 1, a, b; i <= m; i++) {
            in.nextToken(); a = (int) in.nval;
            in.nextToken(); b = (int) in.nval;
            out.println(lca(a, b));
        }
        out.flush();
        out.close();
        br.close();
    }
}
