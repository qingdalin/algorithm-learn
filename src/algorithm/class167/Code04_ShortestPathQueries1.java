package algorithm.class167;

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
 * @date: 2025/5/10 11:31
 * // 异或最短路，java版
 * // 一共有n个节点，m条边，每条边有边权
 * // 接下来有q条操作，每种操作是如下三种类型中的一种
 * // 操作 1 x y d : 原图中加入，点x到点y，权值为d的边
 * // 操作 2 x y   : 原图中删除，点x到点y的边
 * // 操作 3 x y   : 点x到点y，所有路随便走，沿途边权都异或起来，打印能取得的异或最小值
 * // 保证x < y，并且任意操作后，图连通、无重边、无自环，所有操作均合法
 * // 1 <= n、m、q <= 2 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/CF938G
 * // 测试链接 : https://codeforces.com/problemset/problem/938/G
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_ShortestPathQueries1 {
    public static int MAXN = 200001;
    public static int MAXT = 5000001;
    public static int BIT = 29;
    public static int n, m, q;
    public static int[][] event = new int[MAXN << 1][4];
    public static int eventCnt;
    public static int[] op = new int[MAXN];
    public static int[] x = new int[MAXN];
    public static int[] y = new int[MAXN];
    public static int[] d = new int[MAXN];

    public static int[] basic = new int[BIT + 1];
    public static int[] inspos = new int[BIT + 1];
    public static int basiz = 0;

    public static int[] father = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] eor = new int[MAXN];
    public static int[][] rollback = new int[MAXN][2];
    public static int opsize = 0;

    public static int[] head = new int[MAXN << 2];
    public static int[] next = new int[MAXT];
    public static int[] tox = new int[MAXT];
    public static int[] toy = new int[MAXT];
    public static int[] tow = new int[MAXT];
    public static int cnt = 0;

    public static int[] ans = new int[MAXN];

    public static boolean insert(int num) {
        for (int i = BIT; i >= 0; i--) {
            // 错误写法 if (num >> i == 1) {
            if (num >> i == 1) {
                if (basic[i] == 0) {
                    basic[i] = num;
                    inspos[basiz++] = i;
                    return true;
                }
                num ^= basic[i];
            }
        }
        return false;
    }

    public static int minEor(int num) {
        for (int i = BIT; i >= 0; i--) {
            num = Math.min(num, num ^ basic[i]);
        }
        return num;
    }

    public static void cancel(int oldsize) {
        while (basiz > oldsize) {
            basic[inspos[--basiz]] = 0;
        }
    }

    public static int find(int i) {
        while (i != father[i]) {
            i = father[i];
        }
        return i;
    }

    public static int getEor(int i) {
        int ans = 0;
        while (i != father[i]) {
            ans ^= eor[i];
            i = father[i];
        }
        return ans;
    }

    // 可撤销并查集的合并，增加a和b之间，权值为w的边
    // 集合合并的过程中，还要更新eor数组
    // 更新eor的方式，参考讲解156，带权并查集
    public static boolean union(int u, int v, int w) {
        int fu = find(u);
        int fv = find(v);
        // 错误写法 w = getEor(fu) ^ getEor(fv) ^ w;
        w = getEor(u) ^ getEor(v) ^ w;
        if (fu == fv) {
            insert(w);
            return false;
        }
        if (siz[fu] < siz[fv]) {
            int tmp = fu;
            fu = fv;
            fv = tmp;
        }
        father[fv] = fu;
        siz[fu] += siz[fv];
        eor[fv] = w;
        rollback[++opsize][0] = fu;
        rollback[opsize][1] = fv;
        return true;
    }

    public static void undo() {
        int fx = rollback[opsize][0];
        int fy = rollback[opsize--][1];
        father[fy] = fy;
        eor[fy] = 0;
        siz[fx] -= siz[fy];
    }

    public static void addEdge(int i, int x, int y, int w) {
        next[++cnt] = head[i];
        tox[cnt] = x;
        toy[cnt] = y;
        tow[cnt] = w;
        head[i] = cnt;
    }

    public static void add(int jobl, int jobr, int jobx, int joby, int jobw, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            addEdge(i, jobx, joby, jobw);
        } else {
            int mid = (l + r) / 2;
            if (jobl <= mid) {
                add(jobl, jobr, jobx, joby, jobw, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobx, joby, jobw, mid + 1, r, i << 1 | 1);
            }
        }
    }

    public static void dfs(int l, int r, int i) {
        int oldsize = basiz;
        int unionCnt = 0;
        for (int e = head[i], x, y, w; e > 0; e = next[e]) {
            if (union(tox[e], toy[e], tow[e])) {
                unionCnt++;
            }
        }
        if (l == r) {
            if (op[l] == 3) {
                ans[l] = minEor(getEor(x[l]) ^ getEor(y[l]));
            }
        } else {
            int mid = (l + r) / 2;
            dfs(l, mid, i << 1);
            dfs(mid + 1, r, i << 1 | 1);
        }
        cancel(oldsize);
        for (int j = 1; j <= unionCnt; j++) {
            undo();
        }
    }

    public static void prepare() {
        for (int i = 1; i <= n; i++) {
            father[i] = i;
            siz[i] = 1;
        }
        Arrays.sort(event, 1, eventCnt + 1,
            (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]);
        int start, end, x, y, d;
        // 错误写法 for (int l = 1, r = 1; l <= eventCnt; l++) {
        for (int l = 1, r = 1; l <= eventCnt; l = ++r) {
            x = event[l][0];
            y = event[l][1];
            while (r + 1 <= eventCnt && event[r + 1][0] == x && event[r + 1][1] == y) {
                r++;
            }
            // 错误写法 for (int i = 1; i <= r; i += 2) {
            for (int i = l; i <= r; i += 2) {
                start = event[i][2];
                end = i + 1 <= r ? event[i + 1][2] - 1 : q;
                d = event[i][3];
                add(start, end, x, y, d, 0, q, 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); event[i][0] = (int) in.nval;
            in.nextToken(); event[i][1] = (int) in.nval;
            // 错误写法 event[i][2] = i;
            event[i][2] = 0;
            in.nextToken(); event[i][3] = (int) in.nval;
        }
        eventCnt = m;
        in.nextToken(); q = (int) in.nval;
        for (int i = 1; i <= q; i++) {
            in.nextToken(); op[i] = (int) in.nval;
            in.nextToken(); x[i] = (int) in.nval;
            in.nextToken(); y[i] = (int) in.nval;
            if (op[i] == 1) {
                in.nextToken(); d[i] = (int) in.nval;
            }
            if (op[i] != 3) {
                event[++eventCnt][0] = x[i];
                event[eventCnt][1] = y[i];
                event[eventCnt][2] = i;
                event[eventCnt][3] = d[i];
            }
        }
        prepare();
        dfs(0, q, 1);
        for (int i = 1; i <= q; i++) {
            if (op[i] == 3) {
                out.println(ans[i]);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
