package algorithm.class185;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/12 20:45
 * // Atm的树，java版
 * // 树上有n个点，给定n-1条边，每条边有边权
 * // 现在关心，从节点x出发到达其他点的距离中，第k小的距离
 * // 注意，节点x到自己的距离0，不参与距离评比
 * // 给定正数k，打印每个节点的第k小距离，一共n条打印
 * // 1 <= n <= 15000
 * // 1 <= k <= 5000
 * // 1 <= 边权 <= 10
 * // 测试链接 : https://www.luogu.com.cn/problem/P10604
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_AtmTree1 {
    public static int MAXN = 20001;
    public static int MAXH = 18;
    public static int MAXT = 1000001;
    public static int n, k, sumw;

    public static int[] head = new int[MAXN];
    public static int[] nxt = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cntg;

    public static int[] dep = new int[MAXN];
    public static int[] dist = new int[MAXN];
    public static int[][] stjump = new int[MAXN][MAXH];

    public static boolean[] vis = new boolean[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] centfa = new int[MAXN];


    public static int[] addTree = new int[MAXN];
    public static int[] minusTree = new int[MAXN];
    public static int[] ls = new int[MAXT];
    public static int[] rs = new int[MAXT];
    public static int[] sum = new int[MAXT];
    public static int cntt;

    public static void addEdge(int u, int v, int w) {
        nxt[++cntg] = head[u];
        to[cntg] = v;
        weight[cntg] = w;
        head[u] = cntg;
    }

    public static void dfs(int u, int fa, int dis) {
        dep[u] = dep[fa] + 1;
        dist[u] = dis;
        stjump[u][0] = fa;
        for (int p = 1; p < MAXH; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa) {
                dfs(v, u, dis + weight[e]);
            }
        }
    }

    public static int getLca(int a, int b) {
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

    public static int getDist(int x, int y) {
        return dist[x] + dist[y] - (dist[getLca(x, y)] << 1);
    }

    public static void getSize(int u, int fa) {
        siz[u] = 1;
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa && !vis[v]) {
                getSize(v, u);
                siz[u] += siz[v];
            }
        }
    }

    public static int getCentroid(int u, int fa) {
        getSize(u, fa);
        int half = siz[u] >> 1;
        boolean find = false;
        while (!find) {
            find = true;
            for (int e = head[u]; e > 0; e = nxt[e]) {
                int v = to[e];
                if (v != fa && !vis[v] && siz[v] > half) {
                    fa = u;
                    u = v;
                    find = false;
                    break;
                }
            }
        }
        return u;
    }

    public static void centroidTree(int u, int fa) {
        centfa[u] = fa;
        vis[u] = true;
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (!vis[v]) {
                centroidTree(getCentroid(v, u), u);
            }
        }
    }

    public static int add(int jobi, int jobv, int l, int r, int i) {
        if (i == 0) {
            i = ++cntt;
        }
        if (l == r) {
            sum[i] += jobv;
        } else {
            int mid = (l + r) >> 1;
            if (jobi <= mid) {
                ls[i] = add(jobi, jobv, l, mid, ls[i]);
            } else {
                rs[i] = add(jobi, jobv, mid + 1, r, rs[i]);
            }
            sum[i] = sum[ls[i]] + sum[rs[i]];
        }
        return i;
    }

    public static int query(int jobl, int jobr, int l, int r, int i) {
        if (i == 0) {
            return 0;
        }
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        int mid = (l + r) >> 1;
        int ans = 0;
        if (jobl <= mid) {
            ans += query(jobl, jobr, l, mid, ls[i]);
        }
        if (jobr > mid) {
            ans += query(jobl, jobr, mid + 1, r, rs[i]);
        }
        return ans;
    }

    public static void add(int x, int v) {
        addTree[x] = add(0, v, 0, sumw, addTree[x]);
        for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
            int dist = getDist(x, fa);
            addTree[fa] = add(dist, v, 0, sumw, addTree[fa]);
            minusTree[cur] = add(dist, v, 0, sumw, minusTree[cur]);
        }
    }

    public static int query(int x, int limit) {
        int ans = query(0, limit, 0, sumw, addTree[x]);
        for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
            int dist = getDist(x, fa);
            if (limit - dist >= 0) {
                ans += query(0, limit - dist, 0, sumw, addTree[fa]);
                ans -= query(0, limit - dist, 0, sumw, minusTree[cur]);
            }
        }
        return ans;
    }

    public static int compute(int x) {
        int ans = 0;
        int l = 0, r = sumw, mid;
        while (l <= r) {
            mid = (l + r) >> 1;
            if (query(x, mid) >= k) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        k = in.nextInt() + 1;
        for (int i = 1, u, v, w; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            w = in.nextInt();
            addEdge(u, v, w);
            addEdge(v, u, w);
            sumw += w;
        }
        dfs(1, 0, 0);
        centroidTree(getCentroid(1, 0), 0);
        for (int i = 1; i <= n; i++) {
            add(i, 1);
        }
        for (int i = 1; i <= n; i++) {
            out.println(compute(i));
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
