package algorithm.class183;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/22 14:44
 * // 聪聪可可，java版
 * // 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
 * // 如果点对(u, v)的简单路径权值和能被3整除，则称这个点对是合法的
 * // 认为点对(x, x)的简单路径权值和是0，并且认为(x, y)和(y, x)是不同的点对
 * // 打印(合法点对的数量 / 点对的总数量)的最简分数形式
 * // 1 <= n、边权 <= 2 * 10^4
 * // 测试链接 : https://www.luogu.com.cn/problem/P2634
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_Ratio1 {
    public static int MAXN = 20001;
    public static int n;
    public static int[] head = new int[MAXN];
    public static int[] nxt = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cntg;

    public static boolean[] vis = new boolean[MAXN];
    public static int[] siz = new int[MAXN];
    // cur[v]，表示往下走的当前路径中，路径权值和 % 3 == v的路径有多少条
    // all[v]，表示往下走的所有路径中，路径权值和 % 3 == v的路径有多少条
    public static int[] all = new int[3];
    public static int[] cur = new int[3];

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void addEdge(int u, int v, int w) {
        nxt[++cntg] = head[u];
        to[cntg] = v;
        weight[cntg] = w;
        head[u] = cntg;
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

    public static void dfs(int u, int fa, int dis) {
        cur[dis % 3]++;
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (v != fa && !vis[v]) {
                dfs(v, u, dis + weight[e]);
            }
        }
    }

    public static int calc(int u) {
        int ans = 1;
        all[0] = all[1] = all[2] = 0;
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            int w = weight[e];
            if (!vis[v]) {
                cur[0] = cur[1] = cur[2] = 0;
                dfs(v, u, w);
                ans += (all[0] * cur[0] + all[1] * cur[2] + all[2] * cur[1] + cur[0]) * 2;
                all[0] += cur[0];
                all[1] += cur[1];
                all[2] += cur[2];
            }
        }
        return ans;
    }

    public static int solve(int u) {
        int ans = 0;
        vis[u] = true;
        ans += calc(u);
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (!vis[v]) {
                ans += solve(getCentroid(v, u));
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1, u, v, w; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            w = in.nextInt();
            addEdge(u, v, w);
            addEdge(v, u, w);
        }
        int centroid = getCentroid(1, 0);
        int a = solve(centroid);
        int b = n * n;
        int c = gcd(a, b);
        a /= c;
        b /= c;
        out.println(a + "/" + b);
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
