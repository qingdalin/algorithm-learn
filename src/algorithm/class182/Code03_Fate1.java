package algorithm.class182;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/22 9:50
 * // 命运，java版
 * // 一共有n个节点，给定n-1条边，所有节点组成一棵树，规定1号节点是树头
 * // 给定m个点对，每个点对(x, y)，x是y的祖先节点，路径由从上到下的边组成
 * // 树上的每条边都要涂上白色或者黑色，完全由你决定
 * // 但是请保证每个点对的路径中，至少有一条黑色的边存在
 * // 打印给树涂色的方法数，答案对 998244353 取模
 * // 1 <= n、m <= 5 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P6773
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_Fate1 {
    public static int MAXN = 500001;
    public static int MAXT = MAXN * 40;
    public static int MOD = 998244353;
    public static int n, m;
    public static int[] head = new int[MAXN];
    public static int[] nxt = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg;

    public static int[] root = new int[MAXN];
    public static int[] ls = new int[MAXT];
    public static int[] rs = new int[MAXT];
    public static long[] sum = new long[MAXT];
    public static long[] mulLazy = new long[MAXT];
    public static int cntt;

    public static int[] dep = new int[MAXN];
    public static int[] maxdep = new int[MAXN];

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
        nxt[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void up(int i) {
        sum[i] = (sum[ls[i]] + sum[rs[i]]) % MOD;
    }

    public static void lazy(int i, long v) {
        if (i != 0) {
            sum[i] = sum[i] * v % MOD;
            mulLazy[i] = mulLazy[i] * v % MOD;
        }
    }

    public static void down(int i) {
        if (mulLazy[i] != 1) {
            lazy(ls[i], mulLazy[i]);
            lazy(rs[i], mulLazy[i]);
            mulLazy[i] = 1;
        }
    }

    public static int insert(int jobi, int l, int r, int i) {
        int rt = i;
        if (rt == 0) {
            rt = ++cntt;
            mulLazy[rt] = 1;
        }
        if (l == r) {
            sum[rt] = 1;
        } else {
            down(rt);
            int mid = (l + r) / 2;
            if (jobi <= mid) {
                ls[rt] = insert(jobi, l, mid, ls[rt]);
            } else {
                rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
            }
            up(rt);
        }
        return rt;
    }

    public static long query(int jobl, int jobr, int l, int r, int i) {
        if (i == 0) {
            return 0;
        }
        if (jobl <= l && r <= jobr) {
            return sum[i] % MOD;
        }
        down(i);
        long ans = 0;
        int mid = (l + r) / 2;
        if (jobl <= mid) {
            ans = query(jobl, jobr, l, mid, ls[i]);
        }
        if (jobr > mid) {
            ans = (ans + query(jobl, jobr, mid + 1, r, rs[i])) % MOD;
        }
        return ans;
    }

    public static int merge(int l, int r, int t1, int t2, long sum1, long sum2) {
        if (t1 == 0 || t2 == 0) {
            if (t1 != 0) {
                lazy(t1, sum2);
            }
            if (t2 != 0) {
                lazy(t2, sum1);
            }
            return t1 + t2;
        }
        if (l == r) {
            // sum[t1] = ((sum[t1] * (sum2 + sum[t2])) % MOD + (sum[t2] * sum1) % MOD) % MOD;
            sum[t1] = ((sum[t1] * (sum2 + sum[t2])) % MOD + (sum[t2] * sum1) % MOD) % MOD;
        } else {
            down(t1);
            down(t2);
            int mid = (l + r) / 2;
            long tmp1 = sum[ls[t1]];
            long tmp2 = sum[ls[t2]];
            ls[t1] = merge(l, mid, ls[t1], ls[t2], sum1, sum2);
            rs[t1] = merge(mid + 1, r, rs[t1], rs[t2], sum1 + tmp1, sum2 + tmp2);
            up(t1);
        }
        return t1;
    }

    public static void dfs1(int u, int fa) {
        dep[u] = dep[fa] + 1;
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa) {
                dfs1(v, u);
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
                e = head[u];
            } else {
                e = nxt[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (to[e] != f) {
                    push(to[e], u, -1);
                }
            }
        }
    }

    public static void dp1(int u, int fa) {
        root[u] = insert(maxdep[u], 0, n, root[u]);
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa) {
                dp1(v, u);
                root[u] = merge(0, n, root[u], root[v], 0,
                    query(0, dep[u], 0, n, root[v]));
            }
        }
    }

    public static void dp2() {
        stacksize = 0;
        push(1, 0, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                root[u] = insert(maxdep[u], 0, n, root[u]);
                e = head[u];
            } else {
                e = nxt[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (to[e] != f) {
                    push(to[e], u, -1);
                }
            } else {
                for(int ei = head[u]; ei > 0; ei = nxt[ei]) {
                    int v = to[ei];
                    if (v != f) {
                        root[u] = merge(0, n, root[u], root[v], 0,
                            query(0, dep[u], 0, n, root[v]));
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1, u, v; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            addEdge(u, v);
            addEdge(v, u);
        }
        dfs1(1, 0);
//        dfs2();
        m = in.nextInt();
        for (int i = 1, x, y; i <= m; i++) {
            x = in.nextInt();
            y = in.nextInt();
            maxdep[y] = Math.max(maxdep[y], dep[x]);
        }
        dp1(1, 0);
//        dp2();
        long ans = query(0, 0, 0, n, root[1]) % MOD;
        out.println(ans);
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
