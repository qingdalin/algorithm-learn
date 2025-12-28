package algorithm.class183;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/23 10:15
 * // 消息传递，java版
 * // 一共有n个节点，给定n-1条边，每条边的权值为1，所有节点组成一棵树
 * // 一共有m条查询，格式 x k : 打印有多少点与x的距离恰好为k
 * // 1 <= n、m <= 10^5
 * // 0 <= k < n
 * // 测试链接 : https://www.luogu.com.cn/problem/P6626
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code07_Message1 {
    public static int MAXN = 100001;
    public static int t, n, m;

    public static int[] headg = new int[MAXN];
    public static int[] nextg = new int[MAXN << 1];
    public static int[] tog = new int[MAXN << 1];
    public static int cntg;

    public static int[] headq = new int[MAXN];
    public static int[] nextq = new int[MAXN];
    public static int[] dis = new int[MAXN];
    public static int[] qid = new int[MAXN];
    public static int cntq;

    public static boolean[] vis = new boolean[MAXN];
    public static int[] siz = new int[MAXN];
    // nodeCnt[i] = j，表示从u往下走i条边，一共能找到j个点
    public static int[] nodeCnt = new int[MAXN];
    public static int maxEdge;

    public static int[] needArr = new int[MAXN];
    public static int[] qidArr = new int[MAXN];
    public static int cnta;

    public static int[] ans = new int[MAXN];

    public static int[][] stack = new int[MAXN][4];
    public static int stacksize, u, f, edge, e;

    public static void push(int u, int f, int edge, int e) {
        stack[stacksize][0] = u;
        stack[stacksize][1] = f;
        stack[stacksize][2] = edge;
        stack[stacksize][3] = e;
        stacksize++;
    }

    public static void pop() {
        stacksize--;
        u = stack[stacksize][0];
        f = stack[stacksize][1];
        edge = stack[stacksize][2];
        e = stack[stacksize][3];
    }

    public static void addEdge(int u, int v) {
        nextg[++cntg] = headg[u];
        tog[cntg] = v;
        headg[u] = cntg;
    }

    public static void addQuery(int u, int k, int id) {
        nextq[++cntq] = headq[u];
        dis[cntq] = k;
        qid[cntq] = id;
        headq[u] = cntq;
    }

    public static void getSize1(int u, int fa) {
        siz[u] = 1;
        for(int e = headg[u]; e > 0; e = nextg[e]) {
            int v = tog[e];
            if (v != fa && !vis[v]) {
                getSize1(v, u);
                siz[u] += siz[v];
            }
        }
    }

    public static void getSize2(int cur, int fa) {
        stacksize = 0;
        push(cur, fa, 0, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                siz[u] = 1;
                e = headg[u];
            } else {
                e = nextg[e];
            }
            if (e != 0) {
                push(u, f, 0,  e);
                int v = tog[e];
                if (v != f && !vis[v]) {
                    push(v, u, 0, -1);
                }
            } else {
                for(int ei = headg[u]; ei > 0; ei = nextg[ei]) {
                    int v = tog[ei];
                    if (v != f && !vis[v]) {
                        siz[u] += siz[v];
                    }
                }
            }
        }
    }

    public static int getCentroid(int u, int fa) {
//        getSize1(u, fa);
        getSize2(u, fa);
        int half = siz[u] >> 1;
        boolean find = false;
        while (!find) {
            find = true;
            for (int e = headg[u]; e > 0; e = nextg[e]) {
                int v = tog[e];
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

    public static void dfs1(int u, int fa, int edge) {
        nodeCnt[edge]++;
        maxEdge = Math.max(maxEdge, edge);
        for(int e = headq[u]; e > 0; e = nextq[e]) {
            if (dis[e] >= edge) {
                needArr[++cnta] = dis[e] - edge;
                qidArr[cnta] = qid[e];
            }
        }
        for(int e = headg[u]; e > 0; e = nextg[e]) {
            int v = tog[e];
            if (v != fa && !vis[v]) {
                dfs1(v, u, edge + 1);
            }
        }
    }

    public static void dfs2(int cur, int fa, int edg) {
        stacksize = 0;
        push(cur, fa, edg, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                nodeCnt[edge]++;
                maxEdge = Math.max(maxEdge, edge);
                for (int e = headq[u]; e > 0; e = nextq[e]) {
                    if (dis[e] >= edge) {
                        needArr[++cnta] = dis[e] - edge;
                        qidArr[cnta] = qid[e];
                    }
                }
                e = headg[u];
            } else {
                e = nextg[e];
            }
            if (e != 0) {
                push(u, f, edge, e);
                int v = tog[e];
                if (v != f && !vis[v]) {
                    push(v, u, edge + 1, -1);
                }
            }
        }
    }

    public static void calc(int u, int edge, int effect) {
        cnta = 0;
        maxEdge = 0;
//        dfs1(u, 0, edge);
        dfs2(u, 0, edge);
        for (int i = 1; i <= cnta; i++) {
            ans[qidArr[i]] += nodeCnt[needArr[i]] * effect;
        }
        for (int v = 0; v <= maxEdge; v++) {
            nodeCnt[v] = 0;
        }
    }

    public static void solve(int u) {
        vis[u] = true;
        calc(u, 0, 1);
        for (int e = headg[u]; e > 0; e = nextg[e]) {
            int v = tog[e];
            if (!vis[v]) {
                calc(v, 1, -1);
                solve(getCentroid(v, u));
            }
        }
    }

    public static void prepare() {
        cntg = cntq = 0;
        for (int i = 1; i <= n; i++) {
            headg[i] = 0;
            headq[i] = 0;
            vis[i] = false;
        }
        for (int i = 1; i <= m; i++) {
            ans[i] = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        t = in.nextInt();
        for (int c = 0; c < t; c++) {
            n = in.nextInt();
            m = in.nextInt();
            prepare();
            for (int i = 1, u, v; i < n; i++) {
                u = in.nextInt();
                v = in.nextInt();
                addEdge(u, v);
                addEdge(v, u);
            }
            for (int i = 1, x, k; i <= m; i++) {
                x = in.nextInt();
                k = in.nextInt();
                addQuery(x, k, i);
            }
            solve(getCentroid(1, 0));
            for (int i = 1; i <= m; i++) {
                out.println(ans[i]);
            }
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
