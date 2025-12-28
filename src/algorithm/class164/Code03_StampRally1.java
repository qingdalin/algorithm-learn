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
 * @date: 2025/4/3 19:21
 * // 边的最大编号的最小值，java版
 * // 图里有n个点，m条无向边，边的编号1~m，没有边权，所有点都连通
 * // 一共有q条查询，查询的格式如下
 * // 查询 x y z : 从两个点x和y出发，希望经过的点数量等于z
 * //              每个点可以重复经过，但是重复经过只计算一次
 * //              经过边的最大编号，最小是多少
 * // 3 <= n、m、q <= 10^5
 * // 3 <= z <= n
 * // 测试链接 : https://www.luogu.com.cn/problem/AT_agc002_d
 * // 测试链接 : https://atcoder.jp/contests/agc002/tasks/agc002_d
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_StampRally1 {
    public static int MAXK = 200001;
    public static int MAXM = 100001;
    public static int MAXH = 20;
    public static int n, m, q;
    public static int[][] edge = new int[MAXM][3];

    public static int[] father = new int[MAXK];
    public static int[] head = new int[MAXK];
    public static int[] next = new int[MAXK];
    public static int[] to = new int[MAXK];
    public static int cntg = 0;
    public static int[] nodeKey = new int[MAXK];
    public static int cntu = 0;
    public static int[] leafSiz = new int[MAXK];
    public static int[][] stjump = new int[MAXK][MAXH];

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
        Arrays.sort(edge, 1, m + 1, (a, b) -> a[2] - b[2]);
        cntu = n;
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
        stjump[u][0] = fa;
        for(int p = 1; p < MAXH; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = head[u]; e > 0; e = next[e]) {
            dfs1(to[e], u);
        }
        if (u <= n) {
            leafSiz[u] = 1;
        } else {
            leafSiz[u] = 0;
        }
        for(int e = head[u]; e > 0; e = next[e]) {
            leafSiz[u] += leafSiz[to[e]];
        }
    }

    public static boolean check(int x, int y, int z, int limit) {
        for (int p = MAXH - 1; p >= 0; p--) {
            if (stjump[x][p] > 0 && nodeKey[stjump[x][p]] <= limit) {
                x = stjump[x][p];
            }
        }
        for (int p = MAXH - 1; p >= 0; p--) {
            if (stjump[y][p] > 0 && nodeKey[stjump[y][p]] <= limit) {
                y = stjump[y][p];
            }
        }
        if (x == y) {
            return leafSiz[x] >= z;
        } else {
            return (leafSiz[x] + leafSiz[y]) >= z;
        }
    }

    public static int query(int x, int y, int z) {
        int l = 1, r = m, mid, ans = -1;
        while (l <= r) {
            mid = (l + r) / 2;
            if (check(x, y, z, mid)) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); edge[i][0] = (int) in.nval;
            in.nextToken(); edge[i][1] = (int) in.nval;
            edge[i][2] = i;
        }
        kruskalRebuild();
        for (int i = 1; i <= cntu; i++) {
            if (i == father[i]) {
                dfs1(i, 0);

            }
        }
        in.nextToken(); q = (int) in.nval;
        for (int i = 1, x, y, z; i <= q; i++) {
            in.nextToken(); x = (int) in.nval;
            in.nextToken(); y = (int) in.nval;
            in.nextToken(); z = (int) in.nval;
            out.println(query(x, y, z));
        }
        out.flush();
        out.close();
        br.close();
    }
}
