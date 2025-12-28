package algorithm.class184;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/6 14:01
 * // 最大中位数路径，java版
 * // 一共有n个节点，给定n-1条边，每条边给定边权，所有节点组成一棵树
 * // 一条简单路径上，收集所有边权组成序列，其中的 下中位数 作为路径的权
 * // 边数在[limitl, limitr]范围的所有路径中，找到最大权的路径
 * // 如果有多条路径，找到其中一个方案即可，打印两个端点
 * // 1 <= n <= 10^5    0 <= 边权 <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/CF150E
 * // 测试链接 : https://codeforces.com/problemset/problem/150/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_Freezing1 {
    public static int MAXN = 100001;
    public static int INF = 1000000001;
    public static int n, limitl, limitr, cntw;

    public static int[][] arr = new int[MAXN][3];

    public static int[] head = new int[MAXN];
    public static int[] nxt = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cntg;

    public static boolean[] vis = new boolean[MAXN];
    public static int[] siz = new int[MAXN];

    public static int[][] edgeArr = new int[MAXN][2];
    public static int cnte;

    public static int[] preVal = new int[MAXN];
    public static int[] preNode = new int[MAXN];
    public static int preLen;

    public static int[] curVal = new int[MAXN];
    public static int[] curNode = new int[MAXN];
    public static int curLen;

    public static int[] que = new int[MAXN];
    public static int ans, ansu, ansv;

    public static void addEdge(int u, int v, int w) {
        nxt[++cntg] = head[u];
        to[cntg] = v;
        weight[cntg] = w;
        head[u] = cntg;
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

    public static void dfs(int u, int fa, int edge, int sum, int limit) {
        curLen = Math.max(curLen, edge);
        if (sum > curVal[edge]) {
            curVal[edge] = sum;
            curNode[edge] = u;
        }
        for(int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            int w = weight[e];
            if (v != fa && !vis[v]) {
                dfs(v, u, edge + 1, sum + (w >= limit ? 1 : -1), limit);
            }
        }
    }

    public static boolean check(int u, int limit) {
        preVal[0] = 0;
        preNode[0] = u;
        preLen = 0;
        for (int k = 1; k <= cnte; k++) {
            int v = to[edgeArr[k][0]];
            int w = weight[edgeArr[k][0]];
            for (int i = 1; i <= siz[v]; i++) {
                curVal[i] = -INF;
            }
            curLen = 0;
            dfs(v, u, 1, w >= limit ? 1 : -1, limit);
            int ql = 1, qr = 0;
            for(int i = Math.min(preLen, limitr); i >= limitl; i--) {
                while (ql <= qr && preVal[que[qr]] <= preVal[i]) {
                    qr--;
                }
                que[++qr] = i;
            }
            int down = limitr, up = limitl;
            for (int i = 1; i <= curLen; i++) {
                up--;
                if (up >= 0 && up <= preLen) {
                    while (ql <= qr && preVal[que[qr]] <= preVal[up]) {
                        qr--;
                    }
                    que[++qr] = up;
                }
                if (ql <= qr && que[ql] == down) {
                    ql++;
                }
                down--;
                if (ql <= qr && preVal[que[ql]] + curVal[i] >= 0) {
                    if (limit > ans) {
                        ans = limit;
                        ansu = curNode[i];
                        ansv = preNode[que[ql]];
                    }
                    return true;
                }
            }
            for (int i = 1; i <= curLen; i++) {
                if (i > preLen || curVal[i] > preVal[i]) {
                    preVal[i] = curVal[i];
                    preNode[i] = curNode[i];
                }
            }
            preLen = Math.max(preLen, curLen);
        }
        return false;
    }

    public static void calc(int u) {
        getSize(u, 0);
        cnte = 0;
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (!vis[v]) {
                edgeArr[++cnte][0] = e;
                edgeArr[cnte][1] = siz[v];
            }
        }
        Arrays.sort(edgeArr, 1, cnte + 1, (a, b) -> a[1] - b[1]);
        int l = 1, r = cntw, mid;
        while (l <= r) {
            mid = (l + r) >> 1;
            if (check(u, mid)) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
            if (r <= ans) {
                break;
            }
        }
    }

    public static void solve(int u) {
        vis[u] = true;
        calc(u);
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (!vis[v]) {
                solve(getCentroid(v, u));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        limitl = in.nextInt();
        limitr = in.nextInt();
        for (int i = 1; i < n; i++) {
            arr[i][0] = in.nextInt();
            arr[i][1] = in.nextInt();
            arr[i][2] = in.nextInt();
        }
        Arrays.sort(arr, 1, n, (a, b) -> a[2] - b[2]);
        cntw = 0;
        for (int i = 1; i < n; i++) {
            if (i == 1 || arr[i - 1][2] != arr[i][2]) {
                cntw++;
            }
            addEdge(arr[i][0], arr[i][1], cntw);
            addEdge(arr[i][1], arr[i][0], cntw);
        }
        solve(getCentroid(1, 0));
        out.println(ansu + " " + ansv);
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
