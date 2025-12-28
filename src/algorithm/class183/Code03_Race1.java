package algorithm.class183;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/22 16:08
 * // 路径权值和为k的最少边数，java版
 * // 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
 * // 给定数字k，要求找到路径权值和等于k的简单路径，并且边的数量最小
 * // 打印这个最小的边数，如果不存在路径权值和等于k的简单路径，那么打印-1
 * // 注意，本题给定的点的编号从0开始
 * // 1 <= n <= 2 * 10^5
 * // 0 <= 边权、k <= 10^6
 * // 测试链接 : https://www.luogu.com.cn/problem/P4149
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_Race1 {
    public static int MAXN = 200001;
    public static int MAXK = 1000001;
    public static int INF = 1000000001;
    public static int n, k;
    public static int[] head = new int[MAXN];
    public static int[] nxt = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cntg;

    public static boolean[] vis = new boolean[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] curDis = new int[MAXN];
    public static int[] curEdge = new int[MAXN];
    public static int cntc;

    public static int[] allDis = new int[MAXN];
    public static int cnta;

    public static int[] dp = new int[MAXK];

    public static int[][] stack = new int[MAXN][5];
    public static int stacksize, u, f, dis, edge, e;

    public static void push(int u, int f, int dis, int edge, int e) {
        stack[stacksize][0] = u;
        stack[stacksize][1] = f;
        stack[stacksize][2] = dis;
        stack[stacksize][3] = edge;
        stack[stacksize][4] = e;
        stacksize++;
    }

    public static void pop() {
        stacksize--;
        u = stack[stacksize][0];
        f = stack[stacksize][1];
        dis = stack[stacksize][2];
        edge = stack[stacksize][3];
        e = stack[stacksize][4];
    }

    public static void addEdge(int u, int v, int w) {
        nxt[++cntg] = head[u];
        to[cntg] = v;
        weight[cntg] = w;
        head[u] = cntg;
    }

    public static void getSize1(int u, int fa) {
        siz[u] = 1;
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa && !vis[v]) {
                getSize1(v, u);
                siz[u] += siz[v];
            }
        }
    }

    public static void getSize2(int cur, int fa) {
        stacksize = 0;
        push(cur, fa, 0, 0, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                siz[u] = 1;
                e = head[u];
            } else {
                e = nxt[e];
            }
            if (e != 0) {
                push(u, f, 0, 0, e);
                int v = to[e];
                if (v != f && !vis[v]) {
                    push(v, u, 0, 0, -1);
                }
            } else {
                for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
                    int v = to[ei];
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
            for(int e = head[u]; e > 0; e = nxt[e]) {
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

    public static void dfs1(int u, int fa, int dis, int edge) {
        if (dis > k) {
            return;
        }
        curDis[++cntc] = dis;
        curEdge[cntc] = edge;
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa && !vis[v]) {
                dfs1(v, u, dis + weight[e], edge + 1);
            }
        }
    }

    public static void dfs2(int cur, int fa, int pathDis, int pathEdge) {
        stacksize = 0;
        push(cur, fa, pathDis, pathEdge, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                if (dis > k) {
                    continue;
                    // 错误写法 return;
                }
                curDis[++cntc] = dis;
                curEdge[cntc] = edge;
                e = head[u];
            } else {
                e = nxt[e];
            }
            if (e != 0) {
                push(u, f, dis, edge, e);
                int v = to[e];
                if (v != f && !vis[v]) {
                    push(v, u, dis + weight[e], edge + 1, -1);
                }
            }
        }
    }

    public static int calc(int u) {
        int ans = INF;
        cnta = 0;
        dp[0] = 0;
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (!vis[v]) {
                cntc = 0;
                dfs2(v, u, weight[e], 1);
                for (int i = 1; i <= cntc; i++) {
                    ans = Math.min(ans, dp[k - curDis[i]] + curEdge[i]);
                }
                for (int i = 1; i <= cntc; i++) {
                    allDis[++cnta] = curDis[i];
                    dp[curDis[i]] = Math.min(dp[curDis[i]], curEdge[i]);
                }
            }
        }
        for (int i = 1; i <= cnta; i++) {
            dp[allDis[i]] = INF;
        }
        return ans;
    }

    public static int solve(int u) {
        vis[u] = true;
        int ans = calc(u);
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (!vis[v]) {
                ans = Math.min(ans, solve(getCentroid(v, u)));
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        k = in.nextInt();
        for (int i = 1, u, v, w; i < n; i++) {
            u = in.nextInt() + 1;
            v = in.nextInt() + 1;
            w = in.nextInt();
            addEdge(u, v, w);
            addEdge(v, u, w);
        }
        Arrays.fill(dp, INF);
        int centroid = getCentroid(1, 0);
        int ans = solve(centroid);
        out.println(ans == INF ? -1 : ans);
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
