package algorithm.class164;

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
 * @date: 2025/4/4 10:06
 * // 加边直到连通，java版
 * // 图里有n个点，m条无向边，点的编号1~n，边的编号1~m，所有点都连通
 * // 一共有q条查询，每条查询格式如下
 * // 查询 l r : 至少要加完编号前多少的边，才能使得[l, r]中的所有点连通
 * // 1 <= n <= 10^5
 * // 1 <= m、q <= 2 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/CF1706E
 * // 测试链接 : https://codeforces.com/problemset/problem/1706/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_UntilConnect1 {
    public static int MAXN = 100001;
    public static int MAXK = 200001;
    public static int MAXM = 200001;
    public static int MAXH = 20;
    public static int t, n, m, q;
    public static int[][] edge = new int[MAXM][3];

    public static int[] father = new int[MAXK];
    public static int[] head = new int[MAXK];
    public static int[] next = new int[MAXK];
    public static int[] to = new int[MAXK];
    public static int cntg = 0;
    public static int[] nodeKey = new int[MAXK];
    public static int cntu = 0;

    public static int[] dep = new int[MAXK];
    public static int[] dfn = new int[MAXK];
    public static int[] seg = new int[MAXK];
    public static int[][] stjump = new int[MAXK][MAXH];
    public static int cntd = 0;

    public static int[] log2 = new int[MAXN];
    public static int[][] stmin = new int[MAXN][MAXH];
    public static int[][] stmax = new int[MAXN][MAXH];

    public static void clear() {
        cntg = cntd = 0;
        Arrays.fill(head, 1, 2 * n, 0);
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void kruskalRebuild() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
        cntu = n;
        Arrays.sort(edge, 1, m + 1, (a, b) -> a[2] - b[2]);
        for (int i = 1, fx, fy; i <= m; i++) {
            fx = find(edge[i][0]);
            fy = find(edge[i][1]);
            if (fx != fy) {
                father[fx] = father[fy] = ++cntu;
                father[cntu] = cntu;
                nodeKey[cntu] = edge[i][2];
                addEdge(cntu, fx);
                addEdge(cntu, fy);
            }
        }
    }

    public static void dfs1(int u, int fa) {
        dep[u] = dep[fa] + 1;
        dfn[u] = ++cntd;
        seg[cntd] = u;
        stjump[u][0] = fa;
        for (int p = 1; p < MAXH; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = head[u]; e > 0; e = next[e]) {
            dfs1(to[e], u);
        }
    }

    public static void buildst() {
        log2[0] = -1;
        for (int i = 1; i <= n; i++) {
            log2[i] = log2[i >> 1] + 1;
            // 错误写法 stmax[i][0] = i;
            stmax[i][0] = dfn[i];
            // 错误写法 stmin[i][0] = i;
            stmin[i][0] = dfn[i];
        }
        for (int p = 1; p <= log2[n]; p++) {
            // 错误写法 for (int i = 1; i + 1 < (p - 1) - 1 <= n; i++) {
            for (int i = 1; i + (1 << p) - 1 <= n; i++) {
                stmax[i][p] = Math.max(stmax[i][p - 1], stmax[i + (1 << (p - 1))][p - 1]);
                stmin[i][p] = Math.min(stmin[i][p - 1], stmin[i + (1 << (p - 1))][p - 1]);
            }
        }
    }

    public static int dfnmin(int l, int r) {
        int p = log2[r - l + 1];
        return Math.min(stmin[l][p], stmin[r - (1 << p) + 1][p]);
    }

    public static int dfnmax(int l, int r) {
        int p = log2[r - l + 1];
        // 错误写法 return Math.min(stmax[l][p], stmax[r - (1 << p) + 1][p]);
        return Math.max(stmax[l][p], stmax[r - (1 << p) + 1][p]);
    }

    public static int lca(int a, int b) {
        if (dep[a] < dep[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        for (int p = MAXH - 1; p >= 0; p--) {
            if (dep[stjump[a][p]] >= dep[b]) {
                a = stjump[a][p];
            }
        }
        if (a == b) {
            return a;
        }
        for (int p = MAXH - 1; p >= 0; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        return stjump[a][0];
    }

    public static int query(int l, int r) {
        int x = seg[dfnmin(l, r)];
        int y = seg[dfnmax(l, r)];
        return nodeKey[lca(x, y)];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        t = (int) in.nval;
        for (int test = 0; test < t; test++) {
            in.nextToken();
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;
            in.nextToken();
            q = (int) in.nval;
            clear();
            for (int i = 1; i <= m; i++) {
                in.nextToken();
                edge[i][0] = (int) in.nval;
                in.nextToken();
                edge[i][1] = (int) in.nval;
                edge[i][2] = i;
            }
            kruskalRebuild();
            dfs1(cntu, 0);
            buildst();
            for (int i = 1, l, r; i <= q; i++) {
                in.nextToken();
                l = (int) in.nval;
                in.nextToken();
                r = (int) in.nval;
                if (l == r) {
                    out.print("0 ");
                } else {
                    out.print(query(l, r) + " ");
                }
            }
            out.println();
        }
        out.flush();
        out.close();
        br.close();
    }
}
