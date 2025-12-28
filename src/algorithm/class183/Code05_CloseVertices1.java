package algorithm.class183;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/23 8:49
 * // 相近点对的数量，java版
 * // 一共有n个节点，所有节点组成一棵树，1号节点是树头
 * // 从2号点开始，给定每个点的父节点编号、与父节点之间无向边的边权
 * // 给定两个整数limitl、limitw，要求两点之间的简单路径满足如下关系
 * // 路径权值和 <= limitw、路径边数 <= limitl，求出这样的点对数量
 * // 本题规定(x, x)不是点对，(x, y)和(y, x)认为是同一个点对
 * // 1 <= limitl <= n <= 10^5    0 <= limitw <= 10^9    0 <= 边权 <= 10^4
 * // 测试链接 : https://www.luogu.com.cn/problem/CF293E
 * // 测试链接 : https://codeforces.com/problemset/problem/293/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_CloseVertices1 {
    public static int MAXN = 100002;
    public static int n, limitl, limitw;
    public static int[] head = new int[MAXN];
    public static int[] nxt = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cntg;

    public static boolean[] vis = new boolean[MAXN];
    public static int[] siz = new int[MAXN];

    public static int[] disArr = new int[MAXN];
    public static int[] edgeArr = new int[MAXN];
    public static int cnta;

    public static int[] tree = new int[MAXN];

    public static void sort(int l, int r) {
        if (l >= r) {
            return;
        }
        int i = l, j = r, pivot = disArr[(l + r) >> 1], tmp;
        while (i <= j) {
            while (disArr[i] < pivot) {
                i++;
            }
            while (disArr[j] > pivot) {
                j--;
            }
            if (i <= j) {
                tmp = disArr[i];
                disArr[i] = disArr[j];
                disArr[j] = tmp;
                tmp = edgeArr[i];
                edgeArr[i] = edgeArr[j];
                edgeArr[j] = tmp;
                i++;
                j--;
            }
        }
        sort(l, j);
        sort(i, r);
    }

    public static void addEdge(int u, int v, int w) {
        nxt[++cntg] = head[u];
        to[cntg] = v;
        weight[cntg] = w;
        head[u] = cntg;
    }

    public static int lowbit(int i) {
        return i & -i;
    }

    public static void add(int i, int v) {
        i++;
        while (i <= limitl + 1) {
            tree[i] += v;
            i += lowbit(i);
        }
    }

    public static int sum(int i) {
        i++;
        int ret = 0;
        while (i > 0) {
            ret += tree[i];
            i -= lowbit(i);
        }
        return ret;
    }

    public static void getSize(int u, int fa) {
        siz[u] = 1;
        for(int e = head[u]; e > 0; e = nxt[e]) {
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

    public static void dfs(int u, int fa, int dis, int edge) {
        if (dis > limitw || edge > limitl) {
            return;
        }
        disArr[++cnta] = dis;
        edgeArr[cnta] = edge;
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa && !vis[v]) {
                dfs(v, u, dis + weight[e], edge + 1);
            }
        }
    }

    public static long calc(int u, int dis, int edge) {
        cnta = 0;
        dfs(u, 0, dis, edge);
        sort(1, cnta);
        for (int i = 1; i <= cnta; i++) {
            add(edgeArr[i], 1);
        }
        long ans = 0;
        for (int l = 1, r = cnta; l <= r;) {
            if (disArr[l] + disArr[r] <= limitw) {
                add(edgeArr[l], -1);
                ans += sum(limitl - edgeArr[l]);
                l++;
            } else {
                add(edgeArr[r], -1);
                r--;
            }
        }
        return ans;
    }

    public static long solve(int u) {
        vis[u] = true;
        long ans = calc(u, 0, 0);
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (!vis[v]) {
                ans -= calc(v, weight[e], 1);
                ans += solve(getCentroid(v, u));
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        limitl = in.nextInt();
        limitw = in.nextInt();
        for (int i = 2, fa, w; i <= n; i++) {
            fa = in.nextInt();
            w = in.nextInt();
            addEdge(fa, i, w);
            addEdge(i, fa, w);
        }
        long ans = solve(getCentroid(1, 0));
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
