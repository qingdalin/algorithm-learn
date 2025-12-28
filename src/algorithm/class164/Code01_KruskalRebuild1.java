package algorithm.class164;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/2 19:50
 * // Kruskal重构树模版题，java版
 * // 图里有n个点，m条无向边，每条边给定边权，图里可能有若干个连通的部分
 * // 一共有q条查询，每条查询都是如下的格式
 * // 查询 x y : 点x和点y希望连通起来，其中的最大边权希望尽量小，打印这个值
 * //            如果怎样都无法联通，打印"impossible"
 * // 1 <= n <= 10^5
 * // 1 <= m <= 3 * 10^5
 * // 1 <= q <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P2245
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_KruskalRebuild1 {
    public static int MAXK = 200001;
    public static int MAXM = 300001;
    public static int MAXH = 20;
    public static int n, m, q;
    public static int[][] edge = new int[MAXM][3];
    public static int[] father = new int[MAXK];
    public static int[] head = new int[MAXK];
    public static int[] next = new int[MAXK];
    public static int[] to = new int[MAXK];
    public static int cntg = 0;
    public static int[] nodeKey = new int[MAXK];
    public static int cntu = 0;

    public static int[] dep = new int[MAXK];
    public static int[][] stjump = new int[MAXK][MAXH];

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void kruskalRebuild() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
        Arrays.sort(edge, 1, m + 1, (a, b) -> a[2] - b[2]);
        cntu = n;
        for (int i = 1, fx, fy; i <= m; i++) {
            fx = find(edge[i][0]);
            fy = find(edge[i][1]);
            if (fx != fy) {
                father[fx] = father[fy] = ++cntu;
                father[cntu] = cntu;
                nodeKey[cntu] = edge[i][2];
                addEdge(cntu, fx);
                addEdge(cntu, fy);
            }
        }
    }

    public static void dfs1(int u, int fa) {
        dep[u] = dep[fa] + 1;
        stjump[u][0] = fa;
        for (int p = 1; p < MAXH; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = head[u]; e > 0; e = next[e]) {
            dfs1(to[e], u);
        }
    }

    public static int[][] ufe = new int[MAXK][3];
    public static int stackSize, u, f, e;

    public static void push(int u, int f, int e) {
        ufe[stackSize][0] = u;
        ufe[stackSize][1] = f;
        ufe[stackSize][2] = e;
        stackSize++;
    }

    public static void pop() {
        stackSize--;
        u = ufe[stackSize][0];
        f = ufe[stackSize][1];
        e = ufe[stackSize][2];
    }

    public static void dfs2(int cur, int fa) {
        stackSize = 0;
        push(cur, fa, -1);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                dep[u] = dep[f] + 1;
                stjump[u][0] = f;
                for (int p = 1; p < MAXH; p++) {
                    stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
                }
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e);
                push(to[e], u, -1);
            }
        }
    }

    public static int lca(int a, int b) {
        if (dep[a] < dep[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        for (int p = MAXH - 1; p >= 0 ; p--) {
            if (dep[stjump[a][p]] >= dep[b]) {
                a = stjump[a][p];
            }
        }
        if (a == b) {
            return a;
        }
        for (int p = MAXH - 1; p >= 0 ; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        return stjump[a][0];
    }

//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StreamTokenizer in = new StreamTokenizer(br);
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
//        in.nextToken(); n = (int) in.nval;
//        in.nextToken(); m = (int) in.nval;
//        for (int i = 1, u, v, w; i <= m; i++) {
//            in.nextToken(); edge[i][0] = (int) in.nval;
//            in.nextToken(); edge[i][1] = (int) in.nval;
//            in.nextToken(); edge[i][2] = (int) in.nval;
//        }
//        kruskalRebuild();
//        for (int i = 1; i <= cntu; i++) {
//            if (i == father[i]) {
////                dfs1(i, 0);
//                dfs2(i, 0);
//            }
//        }
//        in.nextToken(); q = (int) in.nval;
//        for (int i = 1, x, y; i <= q; i++) {
//            in.nextToken(); x = (int) in.nval;
//            in.nextToken(); y = (int) in.nval;
//            if (find(x) != find(y)) {
//                out.println("impossible");
//            } else {
//                out.println(nodeKey[lca(x, y)]);
//            }
//
//        }
//        out.flush();
//        out.close();
//        br.close();
//    }

    public static void main(String[] args) {
        FastIO io = new FastIO(System.in, System.out);
        n = io.nextInt();
        m = io.nextInt();
        for (int i = 1; i <= m; i++) {
            edge[i][0] = io.nextInt();
            edge[i][1] = io.nextInt();
            edge[i][2] = io.nextInt();
        }
        kruskalRebuild();
        for (int i = 1; i <= cntu; i++) {
            if (i == father[i]) {
                dfs2(i, 0);
            }
        }
        q = io.nextInt();
        for (int i = 1, x, y; i <= q; i++) {
            x = io.nextInt();
            y = io.nextInt();
            if (find(x) != find(y)) {
                io.write("impossible\n");
            } else {
                io.writelnInt(nodeKey[lca(x, y)]);
            }
        }
        io.flush();
    }

    // 读写工具类
    static class FastIO {
        private final InputStream is;
        private final OutputStream os;
        private final byte[] inbuf = new byte[1 << 16];
        private int lenbuf = 0;
        private int ptrbuf = 0;
        private final StringBuilder outBuf = new StringBuilder();

        public FastIO(InputStream is, OutputStream os) {
            this.is = is;
            this.os = os;
        }

        private int readByte() {
            if (ptrbuf >= lenbuf) {
                ptrbuf = 0;
                try {
                    lenbuf = is.read(inbuf);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (lenbuf == -1) {
                    return -1;
                }
            }
            return inbuf[ptrbuf++] & 0xff;
        }

        private int skip() {
            int b;
            while ((b = readByte()) != -1) {
                if (b > ' ') {
                    return b;
                }
            }
            return -1;
        }

        public int nextInt() {
            int b = skip();
            if (b == -1) {
                throw new RuntimeException("No more integers (EOF)");
            }
            boolean negative = false;
            if (b == '-') {
                negative = true;
                b = readByte();
            }
            int val = 0;
            while (b >= '0' && b <= '9') {
                val = val * 10 + (b - '0');
                b = readByte();
            }
            return negative ? -val : val;
        }

        public void write(String s) {
            outBuf.append(s);
        }

        public void writeInt(int x) {
            outBuf.append(x);
        }

        public void writelnInt(int x) {
            outBuf.append(x).append('\n');
        }

        public void flush() {
            try {
                os.write(outBuf.toString().getBytes());
                os.flush();
                outBuf.setLength(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
