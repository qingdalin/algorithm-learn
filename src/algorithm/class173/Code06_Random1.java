package algorithm.class173;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/5 16:11
 * // 树上分块模版题，随机撒点，java版
 * // 一共有n个节点，每个节点有点权，给定n-1条边，所有节点连成一棵树
 * // 接下来有m条操作，每条操作都要打印两个答案，描述如下
 * // 操作 k x1 y1 x2 y2 .. (一共k个点对)
 * // 每个点对(x, y)，在树上都有从x到y的路径，那么k个点对就有k条路径
 * // 先打印k条路径上不同点权的数量，再打印点权集合中没有出现的最小非负数(mex)
 * // 1 <= n、点对总数 <= 10^5    点权 <= 30000
 * // 题目要求强制在线，具体规则可以打开测试链接查看
 * // 测试链接 : https://www.luogu.com.cn/problem/P3603
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code06_Random1 {
    public static int MAXN = 100001;
    public static int MAXB = 401;
    public static int MAXV = 30001;
    public static int MAXP = 17;
    public static int n, m, f, k;
    public static int[] arr = new int[MAXN];

    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg = 0;

    public static int[] dep = new int[MAXN];
    public static int[][] stjump = new int[MAXN][MAXP];

    public static int blen, bnum;
    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXB];
    public static int[] br = new int[MAXB];
    // 随机撒点
    // markNum表示关键点数量
    public static int markNum;
    // vis表示是否是关键点
    public static boolean[] vis = new boolean[MAXN];
    // markNode[k] = i 表示第k个关键点是编号为i的节点
    public static int[] markNode = new int[MAXB];
    // kthMark[i] = k 表示i号节点是第k个关键点，kthMark[i] = 0 表示i号节点是非关键点
    public static int[] kthMark = new int[MAXN];
    // up[i] = j，表示i号节点是关键点，它往上跳到最近的关键点是j号节点
    public static int[] up = new int[MAXN];
    // downSet[k]的含义，路径是[第k个的关键点 .. 最近的上方关键点)，沿途所有节点值组成的位图
    public static BieSet[] downSet = new BieSet[MAXB];

    public static BieSet ans = new BieSet();

    static class BieSet {
        int len;
        int[] set;

        public BieSet() {
            len = (MAXV + 31) / 32;
            set = new int[len];
        }

        public void clear() {
            for (int i = 0; i < len; i++) {
                set[i] = 0;
            }
        }

        public void setOne(int v) {
            set[v / 32] |= 1 << (v % 32);
        }

        public void or(BieSet obj) {
            for (int i = 0; i < len; i++) {
                ans.set[i] |= obj.set[i];
            }
        }

        public int getOne() {
            int ans = 0;
            for (int x : set) {
                ans += Integer.bitCount(x);
            }
            return ans;
        }

        public int getMex() {
            for (int i = 0, inv; i < len; i++) {
                inv = ~set[i];
                if (inv != 0) {
                    return i * 32 + Integer.numberOfTrailingZeros(inv);
                }
            }
            return -1;
        }
    }

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void dfs1(int u, int f) {
        dep[u] = dep[f] + 1;
        stjump[u][0] = f;
        for (int p = 1; p < MAXP; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs1(v, u);
            }
        }
    }

    public static int[][] ufe = new int[MAXN][3];
    public static int stacksize, cur, fath, edge;

    public static void push(int u, int f, int e) {
        ufe[stacksize][0] = u;
        ufe[stacksize][1] = f;
        ufe[stacksize][2] = e;
        stacksize++;
    }

    public static void pop() {
        stacksize--;
        cur = ufe[stacksize][0];
        fath = ufe[stacksize][1];
        edge = ufe[stacksize][2];
    }

    public static void dfs2() {
        stacksize = 0;
        push(1, 0, -1);
        while (stacksize > 0) {
            pop();
            if (edge == -1) {
                dep[cur] = dep[fath] + 1;
                stjump[cur][0] = fath;
                for (int p = 1; p < MAXP; p++) {
                    stjump[cur][p] = stjump[stjump[cur][p - 1]][p - 1];
                }
                edge = head[cur];
            } else {
                edge = next[edge];
            }
            if (edge != 0) {
                push(cur, fath, edge);
                if (to[edge] != fath) {
                    push(to[edge], cur, -1);
                }
            }
        }
    }

    public static int lca(int a, int b) {
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

    public static void query(int x, int xylca) {
        while (kthMark[x] == 0 && x != xylca) {
            ans.setOne(arr[x]);
            x= stjump[x][0];
        }
        while (up[x] > 0 && dep[up[x]] > dep[xylca]) {
            ans.or(downSet[kthMark[x]]);
            x = up[x];
        }
        while (x != xylca) {
            ans.setOne(arr[x]);
            x= stjump[x][0];
        }
    }

    public static void updateAns(int x, int y) {
        int xylca = lca(x, y);
        query(x, xylca);
        query(y, xylca);
        ans.setOne(arr[xylca]);
    }

    public static void prepare() {
//        dfs1(1, 0);
        dfs2();
        int len = (int) Math.sqrt(n * 10);
        markNum = (n + len - 1) / len;
        for (int b = 1, pick; b <= markNum; b++) {
            do {
                pick = (int) (Math.random() * n) + 1;
            } while (vis[pick]);
            vis[pick] = true;
            markNode[b] = pick;
            kthMark[pick] = b;
        }
        for (int b = 1, cur; b <= markNum; b++) {
            downSet[b] = new BieSet();
            downSet[b].setOne(arr[markNode[b]]);
            cur = stjump[markNode[b]][0];
            while (cur != 0) {
                if (kthMark[cur] > 0) {
                    up[markNode[b]] = cur;
                    break;
                } else {
                    downSet[b].setOne(arr[cur]);
                    cur = stjump[cur][0];
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        f = in.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        for (int i = 1, u, v; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            addEdge(u, v);
            addEdge(v, u);
        }
        prepare();
        for (int i = 1, x, y, lastAns = 0; i <= m; i++) {
            ans.clear();
            k = in.nextInt();
            for (int j = 1; j <= k; j++) {
                x = in.nextInt();
                y = in.nextInt();
                if (f > 0) {
                    x = lastAns ^ x;
                    y = lastAns ^ y;
                }
                updateAns(x, y);
            }
            int ans1 = ans.getOne();
            int ans2 = ans.getMex();
            out.println(ans1 + " " + ans2);
            lastAns = ans1 + ans2;
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
