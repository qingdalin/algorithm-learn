package algorithm.class181;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/16 15:37
 * // 雨天的尾巴，java版
 * // 一共有n个节点，给定n-1条边，所有节点组成一棵树
 * // 给定m条路径，格式 x y v，表示x到y路径上的每个点都收到一个数字v
 * // 打印第i号点上，收到次数最多的数字，如果不止一种，打印值最小的数字
 * // 如果某节点没有收到过数字，打印0
 * // 一共n条打印
 * // 1 <= n、m、v <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P4556
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_RainyDayTail1 {
    public static int MAXN = 100001;
    public static int MAXV = 100000;
    public static int MAXT = MAXN * 50;
    public static int MAXP = 20;
    public static int n, m;

    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg;

    public static int[] root = new int[MAXN];
    public static int[] ls = new int[MAXT];
    public static int[] rs = new int[MAXT];
    public static int[] maxCnt = new int[MAXT];
    public static int cntt;

    public static int[] dep = new int[MAXN];
    public static int[][] stjump = new int[MAXN][MAXP];

    public static int[] ans = new int[MAXN];

    public static int[][] ufe = new int[MAXN][3];
    public static int stacksize, u, f, e;

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

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void dfs1(int u, int fa) {
        dep[u] = dep[fa] + 1;
        stjump[u][0] = fa;
        for (int p = 1; p < MAXP; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for (int e = head[u]; e > 0; e = next[e]) {
            if (to[e] != fa) {
                dfs1(to[e], u);
            }
        }
    }

    public static void dfs2() {
        stacksize = 0;
        push(1, 0, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                dep[u] = dep[f] + 1;
                stjump[u][0] = f;
                for (int p = 1; p < MAXP; p++) {
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
            }
        }
    }

    public static int getLca(int a, int b) {
        if (dep[a] < dep[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        for(int p = MAXP - 1; p >= 0; p--) {
            if (dep[stjump[a][p]] >= dep[b]) {
                a = stjump[a][p];
            }
        }
        if (a == b) {
            return a;
        }
        for(int p = MAXP - 1; p >= 0; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        return stjump[a][0];
    }

    public static void up(int i) {
        maxCnt[i] = Math.max(maxCnt[ls[i]], maxCnt[rs[i]]);
    }

    public static int add(int jobi, int jobv, int l, int r, int i) {
        int rt = i;
        if (rt == 0) {
            rt = ++cntt;
        }
        if (l == r) {
            maxCnt[rt] += jobv;
        } else {
            int mid = (l + r) / 2;
            if (jobi <= mid) {
                ls[rt] = add(jobi, jobv, l, mid, ls[rt]);
            } else {
                rs[rt] = add(jobi, jobv, mid + 1, r, rs[rt]);
            }
            up(rt);
        }
        return rt;
    }

    public static int merge(int l, int r, int t1, int t2) {
        if (t1 == 0 || t2 == 0) {
            return t1 + t2;
        }
        if (l == r) {
            maxCnt[t1] += maxCnt[t2];
        } else {
            int mid = (l + r) / 2;
            ls[t1] = merge(l, mid, ls[t1], ls[t2]);
            rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
            up(t1);
        }
        return t1;
    }

    public static int query(int l, int r, int i) {
        if (l == r) {
            return l;
        }
        int mid = (l + r) / 2;
        if (maxCnt[ls[i]] >= maxCnt[rs[i]]) {
            return query(l, mid, ls[i]);
        } else {
            return query(mid + 1, r, rs[i]);
        }
    }

    public static void calc1(int u, int fa) {
        for (int e = head[u]; e > 0; e = next[e]) {
            int v = to[e];
            if (v != fa) {
                calc1(v, u);
                root[u] = merge(1, MAXV, root[u], root[v]);
            }
        }
        if (maxCnt[root[u]] == 0) {
            ans[u] = 0;
        } else {
            ans[u] = query(1, MAXV, root[u]);
        }
    }

    public static void calc2() {
        stacksize = 0;
        push(1, 0, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
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
                for (int ei = head[u]; ei > 0; ei = next[ei]) {
                    int v = to[ei];
                    if (v != f) {
                        root[u] = merge(1, MAXV, root[u], root[v]);
                    }
                }
                if (maxCnt[root[u]] == 0) {
                    ans[u] = 0;
                } else {
                    ans[u] = query(1, MAXV, root[u]);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1, u, v; i < n; i++) {
           u = in.nextInt();
           v = in.nextInt();
           addEdge(u, v);
           addEdge(v, u);
        }
        dfs1(1, 0);
//        dfs2();
        for (int i = 1, x, y, v, lca, lcafa; i <= m; i++) {
            x = in.nextInt();
            y = in.nextInt();
            v = in.nextInt();
            lca = getLca(x, y);
            lcafa = stjump[lca][0];
            root[x] = add(v, 1, 1, MAXV, root[x]);
            root[y] = add(v, 1, 1, MAXV, root[y]);
            root[lca] = add(v, -1, 1, MAXV, root[lca]);
            root[lcafa] = add(v, -1, 1, MAXV, root[lcafa]);
        }
        calc1(1, 0);
//        calc2();
        for (int i = 1; i <= n; i++) {
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
