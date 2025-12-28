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
 * @date: 2025/4/6 16:36
 * // 边权上限内第k大点权，java版
 * // 图里有n个点，m条无向边，点有点权，边有边权，图里可能有若干个连通的部分
 * // 一共有q条查询，查询格式如下
 * // 查询 u x k : 从点u开始，只能走过权值<=x的边
 * //              所有能到达的点中，打印第k大点权，如果不存在打印-1
 * // 1 <= n <= 10^5
 * // 0 <= m、q <= 5 * 10^5
 * // 1 <= 点权、边权 <= 10^9
 * // 本题要求强制在线，具体规定请打开测试链接查看
 * // 测试链接 : https://www.luogu.com.cn/problem/P7834
 * // 提交以下的code，提交时请把类名改成"Main"
 * // java实现的逻辑一定是正确的，但是通过不了
 * // 因为这道题根据C++的运行空间，制定通过标准，根本没考虑java的用户
 * // 想通过用C++实现，本节课Code07_Peaks2文件就是C++的实现
 * // 两个版本的逻辑完全一样，C++版本可以通过所有测试
 */
public class Code07_Peaks1 {
    public static int MAXN = 100001;
    public static int MAXK = 200001;
    public static int MAXM = 500001;
    public static int MAXH = 20;
    public static int MAXT = MAXN * 40;
    public static int n, m, q, s;
    public static int[] node = new int[MAXN];
    public static int[] sorted = new int[MAXN];
    public static int[][] edge = new int[MAXM][3];

    public static int[] father = new int[MAXK];
    public static int[] head = new int[MAXK];
    public static int[] next = new int[MAXK];
    public static int[] to = new int[MAXK];
    public static int cntg = 0;
    public static int[] nodeKey = new int[MAXK];
    public static int cntu = 0;

    public static int[][] stjump = new int[MAXK][MAXH];
    public static int[] leafsiz = new int[MAXK];
    public static int[] leafseg = new int[MAXK];
    public static int[] leafDfnMin = new int[MAXK];
    public static int cntd = 0;

    public static int[] root = new int[MAXN];
    public static int[] ls = new int[MAXT];
    public static int[] rs = new int[MAXT];
    public static int[] numcnt = new int[MAXT];
    public static int cntt = 0;

    public static int kth(int num) {
        int l = 1, r = s, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (sorted[mid] == num) {
                return mid;
            } else if (sorted[mid] > num) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    public static void prepare() {
        for (int i = 1; i <= n; i++) {
            sorted[i] = node[i];
        }
        Arrays.sort(sorted, 1, n + 1);
        s = 1;
        for (int i = 2; i <= n; i++) {
            if (sorted[s] != sorted[i]) {
                sorted[++s] = sorted[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            node[i] = kth(node[i]);
        }
    }

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
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

    public static void dfs(int u, int fa) {
        stjump[u][0] = fa;
        for (int p = 1; p < MAXH; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = head[u]; e > 0; e = next[e]) {
            dfs(to[e], u);
        }
        if (u <= n) {
            leafsiz[u] = 1;
            leafDfnMin[u] = ++cntd;
            leafseg[cntd] = u;
        } else {
            leafsiz[u] = 0;
            leafDfnMin[u] = n + 1;
        }
        for(int e = head[u]; e > 0; e = next[e]) {
            leafsiz[u] += leafsiz[to[e]];
            leafDfnMin[u] = Math.min(leafDfnMin[u], leafDfnMin[to[e]]);
        }
    }

    public static int build(int l, int r) {
        int rt = ++cntt;
        numcnt[rt] = 0;
        if (l < r) {
            int mid = (l + r) / 2;
            ls[rt] = build(l, mid);
            rs[rt] = build(mid + 1, r);
        }
        return rt;
    }

    public static int insert(int jobi, int l, int r, int i) {
        int rt = ++cntt;
        ls[rt] = ls[i];
        rs[rt] = rs[i];
        numcnt[rt] = numcnt[i] + 1;
        if (l < r) {
            int mid = (l + r) / 2;
            if (jobi <= mid) {
                ls[rt] = insert(jobi, l, mid, ls[rt]);
            } else {
                rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
            }
        }
        return rt;
    }

    public static int query(int jobk, int l, int r, int pre, int post) {
        if (l == r) {
            return l;
        }
        int rsize = numcnt[rs[post]] - numcnt[rs[pre]];
        int mid = (l + r) / 2;
        if (rsize >= jobk) {
            return query(jobk, mid + 1, r, rs[pre], rs[post]);
        } else {
            // 错误写法 return query(jobk, l, mid, ls[pre], ls[post]);
            return query(jobk - rsize, l, mid, ls[pre], ls[post]);
        }
    }

    public static int kthMax(int u, int x, int k) {
        for(int p = MAXH - 1; p >= 0; p--) {
            if (stjump[u][p] > 0 && nodeKey[stjump[u][p]] <= x) {
                u = stjump[u][p];
            }
        }
        if (leafsiz[u] < k) {
            return 0;
        }
        int idx = query(k, 1, s, root[leafDfnMin[u] - 1], root[leafDfnMin[u] + leafsiz[u] - 1]);
        return sorted[idx];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); q = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); node[i] = (int) in.nval;
        }
        for (int i = 1; i <= m; i++) {
            in.nextToken(); edge[i][0] = (int) in.nval;
            in.nextToken(); edge[i][1] = (int) in.nval;
            in.nextToken(); edge[i][2] = (int) in.nval;
        }
        prepare();
        kruskalRebuild();
        for (int i = 1; i <= cntu; i++) {
            if (i == father[i]) {
                dfs(i, 0);
            }
        }
        root[0] = build(1, s);
        for (int i = 1; i <= n; i++) {
            root[i] = insert(node[leafseg[i]], 1, s, root[i - 1]);
        }
        for (int i = 1, u, x, k, lastAns = 0; i <= q; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); x = (int) in.nval;
            in.nextToken(); k = (int) in.nval;
            u = (u ^ lastAns) % n + 1;
            k = (k ^ lastAns) % n + 1;
            x = x ^ lastAns;
            lastAns = kthMax(u, x, k);
            if (lastAns == 0) {
                out.println(-1);
            } else {
                out.println(lastAns);
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}
