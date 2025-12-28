package algorithm.class164;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/3 20:18
 * // 归程，java版
 * // 图里有n个点，m条无向边，每条边给定长度l和海拔a，所有点都连通
 * // 一共有q条查询，查询格式如下
 * // 查询 x y : 海拔 > y的边，走过没有代价
 * //            海拔 <= y的边，走过的代价为边的长度
 * //            从点x出发到达1号点，打印最小的代价
 * // 1 <= n <= 2 * 10^5
 * // 1 <= m、q <= 4 * 10^5
 * // 本题要求强制在线，具体规定请打开测试链接查看
 * // 测试链接 : https://www.luogu.com.cn/problem/P4768
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_Journey1 {
    public static int MAXN = 200001;
    public static int MAXK = 400001;
    public static int MAXM = 400001;
    public static int MAXH = 20;
    public static int INF = 2000000001;
    public static int t, n, m, q, k, s;
    public static int[][] edge = new int[MAXM][4];

    public static int[] headg = new int[MAXN];
    // 边的数量是MAXM << 1
    // 错误写法 public static int[] nextg = new int[MAXN << 1];
    public static int[] nextg = new int[MAXM << 1];
    public static int[] tog = new int[MAXM << 1];
    public static int[] weightg = new int[MAXM << 1];
    public static int cntg = 0;

    public static int[] dist = new int[MAXN];
    public static boolean[] visit = new boolean[MAXN];
    public static PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);

    public static int[] father = new int[MAXK];
    public static int[] stack = new int[MAXK];

    public static int[] headk = new int[MAXK];
    public static int[] nextk = new int[MAXK];
    public static int[] tok = new int[MAXK];
    public static int cntk = 0;
    public static int[] nodeKey = new int[MAXK];
    public static int cntu = 0;

    public static int[] mindist = new int[MAXK];
    public static int[][] stjump = new int[MAXK][MAXH];

    public static void clear() {
        cntg = cntk = 0;
        Arrays.fill(headg, 1, n + 1, 0);
        Arrays.fill(headk, 1, 2 * n, 0);
    }

    public static int find(int i) {
        int size = 0;
        while (i != father[i]) {
            stack[size++] = i;
            i = father[i];
        }
        while (size > 0) {
            father[stack[--size]] = i;
        }
        return i;
    }

    public static void addEdgeG(int u, int v, int w) {
        nextg[++cntg] = headg[u];
        tog[cntg] = v;
        weightg[cntg] = w;
        headg[u] = cntg;
    }

    public static void dijkstra() {
        for (int i = 1; i <= m; i++) {
            addEdgeG(edge[i][0], edge[i][1], edge[i][2]);
            addEdgeG(edge[i][1], edge[i][0], edge[i][2]);
        }
        Arrays.fill(dist, 1, n + 1, INF);
        Arrays.fill(visit, 1, n + 1, false);
        heap.add(new int[] {1, 0});
        dist[1] = 0;
        int[] cur;
        int x, v;
        while (!heap.isEmpty()) {
            cur = heap.poll();
            x = cur[0];
            v = cur[1];
            if (visit[x]) {
                continue;
            }
            visit[x] = true;
            for(int e = headg[x], y, w; e > 0; e = nextg[e]) {
                y = tog[e];
                w = weightg[e];
                if (!visit[y] && dist[y] > v + w) {
                    dist[y] = v + w;
                    heap.add(new int[] {y, dist[y]});
                }
            }
        }
    }

    public static void addEdgeK(int u, int v) {
        nextk[++cntk] = headk[u];
        tok[cntk] = v;
        // 错误写法 headk[cntk] = u;
        headk[u] = cntk;
    }

    public static void kruskalRebuild() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
        Arrays.sort(edge, 1, m + 1, (a, b) -> b[3] - a[3]);
        cntu = n;
        for (int i = 1, fx, fy; i <= m; i++) {
            fx = find(edge[i][0]);
            fy = find(edge[i][1]);
            if (fx != fy) {
                father[fx] = father[fy] = ++cntu;
                father[cntu] = cntu;
                nodeKey[cntu] = edge[i][3];
                addEdgeK(cntu, fx);
                addEdgeK(cntu, fy);
            }
        }
    }

    public static void dfs1(int u, int fa) {
        stjump[u][0] = fa;
        for (int p = 1; p < MAXH; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = headk[u]; e > 0; e = nextk[e]) {
            dfs1(tok[e], u);
        }
        if (u <= n) {
            mindist[u] = dist[u];
        } else {
            mindist[u] = INF;
        }
        for(int e = headk[u]; e > 0; e = nextk[e]) {
            mindist[u] = Math.min(mindist[u], mindist[tok[e]]);
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
                stjump[u][0] = f;
                for (int p = 1; p < MAXH; p++) {
                    stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
                }
                e = headk[u];
            } else {
                e = nextk[e];
            }
            if (e != 0) {
                push(u, f, e);
                push(tok[e], u, -1);
            } else {
                if (u <= n) {
                    mindist[u] = dist[u];
                } else {
                    mindist[u] = INF;
                }
                for(int ei = headk[u]; ei > 0; ei = nextk[ei]) {
                    mindist[u] = Math.min(mindist[u], mindist[tok[ei]]);
                }
            }
        }
    }

    public static int query(int v, int line) {
        for (int p = MAXH - 1; p >= 0; p--) {
            if (stjump[v][p] > 0 && nodeKey[stjump[v][p]] > line) {
                v = stjump[v][p];
            }
        }
        return mindist[v];
    }

//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StreamTokenizer in = new StreamTokenizer(br);
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
//        in.nextToken(); t = (int) in.nval;
//        for (int test = 0; test < t; test++) {
//            in.nextToken(); n = (int) in.nval;
//            in.nextToken(); m = (int) in.nval;
//            clear();
//            for (int i = 1; i <= m; i++) {
//                in.nextToken(); edge[i][0] = (int) in.nval;
//                in.nextToken(); edge[i][1] = (int) in.nval;
//                in.nextToken(); edge[i][2] = (int) in.nval;
//                in.nextToken(); edge[i][3] = (int) in.nval;
//            }
//            dijkstra();
//            kruskalRebuild();
//            // dfs1(cntu, 0);
//            dfs2(cntu, 0);
//            in.nextToken(); q = (int) in.nval;
//            in.nextToken(); k = (int) in.nval;
//            in.nextToken(); s = (int) in.nval;
//            for (int i = 1, v, line, lastAns = 0; i <= q; i++) {
//                in.nextToken(); v = (int) in.nval;
//                v = (v + k * lastAns - 1) % n + 1;
//                in.nextToken(); line = (int) in.nval;
//                line = (line + k * lastAns) % (s + 1);
//                lastAns = query(v, line);
//                out.println(lastAns);
//            }
//        }
//        out.flush();
//        out.close();
//        br.close();
//    }

    public static void main(String[] args) {
        FastIO io = new FastIO(System.in, System.out);
        t = io.nextInt();
        for (int test = 1; test <= t; test++) {
            n = io.nextInt();
            m = io.nextInt();
            clear();
            for (int i = 1; i <= m; i++) {
                edge[i][0] = io.nextInt();
                edge[i][1] = io.nextInt();
                edge[i][2] = io.nextInt();
                edge[i][3] = io.nextInt();
            }
            dijkstra();
            kruskalRebuild();
            dfs2(cntu, 0);
            q = io.nextInt();
            k = io.nextInt();
            s = io.nextInt();
            for (int i = 1, x, y, lastAns = 0; i <= q; i++) {
                x = (io.nextInt() + k * lastAns - 1) % n + 1;
                y = (io.nextInt() + k * lastAns) % (s + 1);
                lastAns = query(x, y);
                io.writelnInt(lastAns);
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
