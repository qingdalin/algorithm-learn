package algorithm.class180;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/15 19:54
 * // 树上病毒传播，java版
 * // 一共有n个城市，有n-1条无向边，所有城市组成一棵树，一共有q条查询，每条查询数据如下
 * // 首先给定k种病毒，每种病毒有初次感染的城市start[i]，还有传播速度speed[i]
 * // 然后给定m个关键城市，打印每个城市被第几号病毒感染，病毒传播的规则如下
 * // 病毒的传播按轮次进行，每一轮病毒1先传播，然后是病毒2 .. 直到病毒k，下一轮又从病毒1开始
 * // 如果第i种病毒已经感染了城市x，当自己传播时，想要感染城市y的条件如下
 * // 城市x到城市y的路径包含的边数<=speed[i]，城市x到城市y的路径上，除了x所有城市都未被感染
 * // 一旦城市被某种病毒感染就永久保持，不会再被其他病毒感染，传播一直持续，直到所有城市都被感染
 * // 1 <= n、q、所有查询病毒总数、所有查询关键城市总数 <= 2 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/CF1320E
 * // 测试链接 : https://codeforces.com/problemset/problem/1320/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_TreelandAndViruses1 {
    static class Node {
        int id, dist, time, virus;

        public Node(int id, int dist, int time, int virus) {
            this.id = id;
            this.dist = dist;
            this.time = time;
            this.virus = virus;
        }
    }

    static class NodeCmp implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.time != o2.time) {
                return o1.time - o2.time;
            }
            return o1.virus - o2.virus;
        }
    }

    public static int MAXN = 200001;
    public static int MAXP = 20;
    public static int n, q, k, m;

    public static int[] headg = new int[MAXN];
    public static int[] nextg = new int[MAXN << 1];
    public static int[] tog = new int[MAXN << 1];
    public static int cntg;

    public static int[] headv = new int[MAXN];
    public static int[] nextv = new int[MAXN << 1];
    public static int[] tov = new int[MAXN << 1];
    public static int cntv;

    public static int[] dep = new int[MAXN];
    public static int[] dfn = new int[MAXN];
    public static int[][] stjump = new int[MAXN][MAXP];
    public static int cntd = 0;

    public static int[] start = new int[MAXN];
    public static int[] speed = new int[MAXN];
    public static int[] query = new int[MAXN];

    public static int[] arr = new int[MAXN << 1];
    public static int[] tmp = new int[MAXN << 2];
    public static int len;

    public static PriorityQueue<Node> heap = new PriorityQueue<>(new NodeCmp());
    public static boolean[] vis = new boolean[MAXN];
    public static int[] minTime = new int[MAXN];
    public static int[] findVirus = new int[MAXN];

    public static void addEdgeG(int u, int v) {
        nextg[++cntg] = headg[u];
        tog[cntg] = v;
        headg[u] = cntg;
    }

    public static void addEdgeV(int u, int v) {
        nextv[++cntv] = headv[u];
        tov[cntv] = v;
        headv[u] = cntv;
    }

    public static void sortByDfn(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int i = l, j = r;
        int pivot = nums[(l + r) >> 1];
        while (i <= j) {
            while (dfn[nums[i]] < dfn[pivot]) {
                i++;
            }
            while (dfn[nums[j]] > dfn[pivot]) {
                j--;
            }
            if (i <= j) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                i++;
                j--;
            }
        }
        sortByDfn(nums, l, j);
        sortByDfn(nums, i, r);
    }

    public static void dfs(int u, int fa) {
        dep[u] = dep[fa] + 1;
        dfn[u] = ++cntd;
        stjump[u][0] = fa;
        for (int p = 1; p < MAXP; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = headg[u]; e > 0; e = nextg[e]) {
            int v = tog[e];
            if (v != fa) {
                dfs(v, u);
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

    public static int buildVirtualTree() {
        int tot = 0;
        for (int i = 1; i <= k; i++) {
            arr[++tot] = start[i];
        }
        for (int i = 1; i <= m; i++) {
            arr[++tot] = query[i];
        }
        sortByDfn(arr, 1, tot);
        len = 0;
        for (int i = 1; i < tot; i++) {
            tmp[++len] = arr[i];
            tmp[++len] = getLca(arr[i], arr[i + 1]);
        }
        tmp[++len] = arr[tot];
        sortByDfn(tmp, 1, len);
        int unique = 1;
        for (int i = 2; i <= len; i++) {
            if (tmp[unique] != tmp[i]) {
                tmp[++unique] = tmp[i];
            }
        }
        cntv = 0;
        for (int i = 1; i <= unique; i++) {
            headv[tmp[i]] = 0;
        }
        for (int i = 1; i < unique; i++) {
            int lca = getLca(tmp[i], tmp[i + 1]);
            addEdgeV(lca, tmp[i + 1]);
            addEdgeV(tmp[i + 1], lca);
        }
        len = unique;
        return tmp[1];
    }

    public static void dijkstra() {
        for (int i = 1; i <= len; i++) {
            int u = tmp[i];
            minTime[u] = n + 1;
            findVirus[u] = k + 1;
            vis[u] = false;
        }
        for (int i = 1; i <= k; i++) {
            int s = start[i];
            minTime[s] = 0;
            findVirus[s] = i;
            heap.add(new Node(s, 0, 0, i));
        }
        while (!heap.isEmpty()) {
            Node cur = heap.poll();
            int u = cur.id;
            int udist = cur.dist;
            int uvirus = cur.virus;
            if (!vis[u]) {
                vis[u] = true;
                for(int e = headv[u]; e > 0; e = nextv[e]) {
                    int v = tov[e];
                    if (!vis[v]) {
                        int vdist = udist + Math.abs(dep[v] - dep[u]);
                        int vtime = (vdist + speed[uvirus] - 1) / speed[uvirus];
                        if (vtime < minTime[v] || (vtime == minTime[v] && uvirus < findVirus[v])) {
                            minTime[v] = vtime;
                            findVirus[v] = uvirus;
                            heap.add(new Node(v, vdist, vtime, uvirus));
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1, x, y; i < n; i++) {
            x = in.nextInt();
            y = in.nextInt();
            addEdgeG(x, y);
            addEdgeG(y, x);
        }
        dfs(1, 0);
        q = in.nextInt();
        for (int i = 1; i <= q; i++) {
            k = in.nextInt();
            m = in.nextInt();
            for (int j = 1; j <= k; j++) {
                start[j] = in.nextInt();
                speed[j] = in.nextInt();
            }
            for (int j = 1; j <= m; j++) {
                query[j] = in.nextInt();
            }
            buildVirtualTree();
            dijkstra();
            for (int j = 1; j <= m; j++) {
                out.print(findVirus[query[j]] + " ");
            }
            out.println();
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
