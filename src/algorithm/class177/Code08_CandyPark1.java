package algorithm.class177;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/13 13:59
 * // 糖果公园，java版
 * // 一共有n个公园，给定n-1条边，所有公园连成一棵树，c[i]为i号公园的糖果型号
 * // 一共有m种糖果，v[y]表示y号糖果的美味指数，给定长度为n的数组w，用于计算愉悦值
 * // 假设游客当前遇到了y号糖果，并且是第x次遇到，那么愉悦值会增加 v[y] * w[x]
 * // 随着游客遇到各种各样的糖果，愉悦值会不断上升，接下来有q条操作，操作类型如下
 * // 操作 0 x y : 第x号公园的糖果型号改成y
 * // 操作 1 x y : 游客从点x出发走过简单路径到达y，依次遇到每个公园的糖果，打印最终的愉悦值
 * // 1 <= n、m、q <= 10^5
 * // 1 <= v[i]、w[i] <= 10^6
 * // 测试链接 : https://www.luogu.com.cn/problem/P4074
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code08_CandyPark1 {
    public static int MAXN = 100001;
    public static int MAXH = 20;
    public static int n, m, q;
    public static int[] v = new int[MAXN];
    public static int[] w = new int[MAXN];
    public static int[] c = new int[MAXN];
    public static int[][] query = new int[MAXN][5];
    public static int[][] update = new int[MAXN][2];
    public static int cntq, cntu;

    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg;

    public static int[] dep = new int[MAXN];
    public static int[] seg = new int[MAXN << 1];
    public static int[] st = new int[MAXN];
    public static int[] ed = new int[MAXN];
    public static int[][] stjump = new int[MAXN][MAXH];
    public static int cntd;

    public static int[] bi = new int[MAXN << 1];
    public static int[] cnt = new int[MAXN];
    public static boolean[] vis = new boolean[MAXN];
    public static long happy;
    public static long[] ans = new long[MAXN];

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void dfs1(int u, int fa) {
        dep[u] = dep[fa] + 1;
        stjump[u][0] = fa;
        seg[++cntd] = u;
        st[u] = cntd;
        for (int p = 1; p < MAXH; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa) {
                dfs1(v, u);
            }
        }
        seg[++cntd] = u;
        ed[u] = cntd;
    }

    public static int[][] ufe = new int[MAXN][3];
    public static int u,f,e,stacksize;

    public static void push(int u, int f, int e) {
        ufe[stacksize][0] = u;
        ufe[stacksize][1] = f;
        ufe[stacksize][2] = e;
        stacksize++;
    }

    public static void pop() {
        stacksize--;
        u = ufe[stacksize][0];
        f = ufe[stacksize][1];
        e = ufe[stacksize][2];
    }

    public static void dfs2() {
        stacksize = 0;
        push(1, 0, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                dep[u] = dep[f] + 1;
                stjump[u][0] = f;
                seg[++cntd] = u;
                st[u] = cntd;
                for (int p = 1; p < MAXH; p++) {
                    stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
                }
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (to[e] != f) {
                    push(to[e], u, -1);
                }
            } else {
                seg[++cntd] = u;
                ed[u] = cntd;
            }
        }
    }

    public static int lca(int a, int b) {
        if (dep[a] < dep[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        for(int p = MAXH - 1; p >= 0; p--) {
            if (dep[stjump[a][p]] >= dep[b]) {
                a = stjump[a][p];
            }
        }
        if (a == b) {
            return a;
        }
        for(int p = MAXH - 1; p >= 0; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        return stjump[a][0];
    }

    public static class QueryCmp implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            if (bi[a[1]] != bi[b[1]]) {
                return bi[a[1]] - bi[b[1]];
            }
            return a[2] - b[2];
        }
    }

    public static void invert(int node) {
        int candy = c[node];
        if (vis[node]) {
            happy -= (long) v[candy] * w[cnt[candy]--];
        } else {
            happy += (long) v[candy] * w[++cnt[candy]];
        }
        vis[node] = !vis[node];
    }

    public static void moveTime(int tim) {
        int pos = update[tim][0];
        int oldVal = c[pos];
        int newVal = update[tim][1];
        if (vis[pos]) {
            invert(pos);
            c[pos] = newVal;
            update[tim][1] = oldVal;
            invert(pos);
        } else {
            c[pos] = newVal;
            update[tim][1] = oldVal;
        }
    }

    public static void compute() {
        int winl = 1, winr = 0, wint = 0;
        for (int i = 1, jobl, jobr, jobt, lca, id; i <= cntq; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
            jobt = query[i][2];
            lca = query[i][3];
            id = query[i][4];
            while (winl > jobl) {
                invert(seg[--winl]);
            }
            while (winr < jobr) {
                invert(seg[++winr]);
            }
            while (winl < jobl) {
                invert(seg[winl++]);
            }
            while (winr > jobr) {
                invert(seg[winr--]);
            }
            while (wint < jobt) {
                moveTime(++wint);
            }
            while (wint > jobt) {
                moveTime(wint--);
            }
            if (lca > 0) {
                invert(lca);
            }
            ans[id] = happy;
            if (lca > 0) {
                invert(lca);
            }
        }
    }

    public static void prepare() {
        int blen = Math.max(1, (int) Math.pow(cntd, 2.0 / 3));
        for (int i = 1; i <= cntd; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        Arrays.sort(query, 1, cntq + 1, new QueryCmp());
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        q = in.nextInt();
        for (int i = 1; i <= m; i++) {
            v[i] = in.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
        }
        for (int i = 1, u, v; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            addEdge(u, v);
            addEdge(v, u);
        }
        for (int i = 1; i <= n; i++) {
            c[i] = in.nextInt();
        }
//        dfs1(1, 0);
        dfs2();
        for (int i = 1, y, x, lca, op; i <= q; i++) {
            op = in.nextInt();
            x = in.nextInt();
            y = in.nextInt();
            if (op == 0) {
                cntu++;
                update[cntu][0] = x;
                update[cntu][1] = y;
            } else {
                if (st[y] < st[x]) {
                    int tmp = x;
                    x = y;
                    y = tmp;
                }
                lca = lca(x, y);
                if (x == lca) {
                    query[++cntq][0] = st[x];
                    query[cntq][1] = st[y];
                    query[cntq][2] = cntu;
                    query[cntq][3] = 0;
                    query[cntq][4] = cntq;
                } else {
                    query[++cntq][0] = ed[x];
                    query[cntq][1] = st[y];
                    query[cntq][2] = cntu;
                    query[cntq][3] = lca;
                    query[cntq][4] = cntq;
                }
            }
        }
        prepare();
        compute();
        for (int i = 1; i <= cntq; i++) {
            out.println(ans[i]);
        }
        out.flush();
        out.close();
    }

    // 读写工具类
    static class FastReader {
        final private int BUFFER_SIZE = 1 << 16;
        private final InputStream in;
        private final byte[] buffer;
        private int ptr, len;

        FastReader() {
            in = System.in;
            buffer = new byte[BUFFER_SIZE];
            ptr = len = 0;
        }

        private boolean hasNextByte() throws IOException {
            if (ptr < len)
                return true;
            ptr = 0;
            len = in.read(buffer);
            return len > 0;
        }

        private byte readByte() throws IOException {
            if (!hasNextByte())
                return -1;
            return buffer[ptr++];
        }

        // 读取下一个ASCII码范围在[32,126]的字符
        char nextChar() throws IOException {
            byte c;
            do {
                c = readByte();
                if (c == -1)
                    return 0;
            } while (c < 32 || c > 126);
            return (char) c;
        }

        String nextString() throws IOException {
            byte b = readByte();
            while (isWhitespace(b))
                b = readByte();
            StringBuilder sb = new StringBuilder(16);
            while (!isWhitespace(b) && b != -1) {
                sb.append((char) b);
                b = readByte();
            }
            return sb.toString();
        }

        int nextInt() throws IOException {
            int num = 0, sign = 1;
            byte b = readByte();
            while (isWhitespace(b))
                b = readByte();
            if (b == '-') {
                sign = -1;
                b = readByte();
            }
            while (!isWhitespace(b) && b != -1) {
                num = num * 10 + (b - '0');
                b = readByte();
            }
            return sign * num;
        }

        private boolean isWhitespace(byte b) {
            return b == ' ' || b == '\n' || b == '\r' || b == '\t';
        }
    }
}
