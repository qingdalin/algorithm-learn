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
 * @date: 2025/4/2 20:28
 * // youyou的军训，java版
 * // 图里有n个点，m条无向边，每条边给定不同的边权，图里可能有若干个连通的部分
 * // 一共有q条操作，每条操作都是如下的三种类型中的一种
 * // 操作 1 x   : 限制变量limit，把limit的值改成x
 * // 操作 2 x   : 点x不能走过任何边权小于limit的边，打印此时x所在的连通区域大小
 * // 操作 3 x y : 第x条边的边权修改为y，题目保证修改之后，第x条边的边权排名不变
 * // 1 <= n、m、q <= 4 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P9638
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_Training1 {
    public static int MAXK = 800001;
    public static int MAXM = 800001;
    public static int MAXH = 20;
    public static int n, m, q;
    public static int[][] edge = new int[MAXM][4];
    public static int[] edgeToTree = new int[MAXM];
    public static int[] father = new int[MAXK];
    public static int[] stack = new int[MAXK];
    public static int[] head = new int[MAXK];
    public static int[] next = new int[MAXK];
    public static int[] to = new int[MAXK];
    public static int cntg = 0;
    public static int[] nodeKey = new int[MAXK];
    public static int cntu = 0;
    public static int[][] stjump = new int[MAXK][MAXH];
    public static int[] leafsiz = new int[MAXK];

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

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void kruskalRebuild() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
        Arrays.sort(edge, 1, m + 1, (a, b) -> b[2] - a[2]);
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
                edgeToTree[edge[i][3]] = cntu;
            }
        }
    }

    public static void dfs1(int u, int fa) {
        stjump[u][0] = fa;
        for (int p = 1; p < MAXH; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = head[u]; e > 0; e = next[e]) {
            dfs1(to[e], u);
        }
        if (u <= n) {
            leafsiz[u] = 1;
        } else {
            leafsiz[u] = 0;
        }
        for(int e = head[u]; e > 0; e = next[e]) {
            leafsiz[u] += leafsiz[to[e]];
        }
    }

    public static int[][] ufe = new int[MAXK][3];
    public static int stackSize, u, f, e;

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

    public static void dfs2(int cur, int fa) {
        stackSize = 0;
        push(cur, fa, -1);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                stjump[u][0] = f;
                for (int p = 1; p < MAXH; p++) {
                    stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
                }
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e);
                push(to[e], u, -1);
            } else {
                if (u <= n) {
                    leafsiz[u] = 1;
                } else {
                    leafsiz[u] = 0;
                }
                for(int e = head[u]; e > 0; e = next[e]) {
                    leafsiz[u] += leafsiz[to[e]];
                }
            }
        }
    }

    public static int query(int u, int limit) {
        for (int p = MAXH - 1; p >= 0; p--) {
            // 错误写法 if (stjump[u][p] > 0 && nodeKey[stjump[u][p]] >= limit) {
            if (stjump[u][p] > 0 && nodeKey[stjump[u][p]] >= limit) {
                u = stjump[u][p];
            }
        }
        return leafsiz[u];
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); q = (int) in.nval;
        for (int i = 1, u, v, w; i <= m; i++) {
            in.nextToken(); edge[i][0] = (int) in.nval;
            in.nextToken(); edge[i][1] = (int) in.nval;
            in.nextToken(); edge[i][2] = (int) in.nval;
            edge[i][3] = i;
        }
        kruskalRebuild();
        for (int i = 1; i <= cntu; i++) {
            if (i == father[i]) {
//                dfs1(i, 0);
                dfs2(i, 0);
            }
        }
        int limit = 0, op;
        for (int i = 1, x, y; i <= q; i++) {
            in.nextToken(); op = (int) in.nval;
            if (op == 1) {
                in.nextToken(); limit = (int) in.nval;
            } else if (op == 2) {
                in.nextToken(); x = (int) in.nval;
                out.println(query(x, limit));
            } else {
                in.nextToken(); x = (int) in.nval;
                in.nextToken(); y = (int) in.nval;
                if (edgeToTree[x] != 0) {
                    nodeKey[edgeToTree[x]] = y;
                }
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}
