package algorithm.class180;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/15 15:03
 * // 大工程，java版
 * // 一共有n个节点，给定n-1条无向边，所有节点组成一棵树
 * // 如果在节点a和节点b之间建立新通道，那么代价是两个节点在树上的距离
 * // 一共有q条查询，每条查询格式如下
 * // 查询 k a1 a2 ... ak : 给出了k个不同的节点，任意两个节点之间都会建立新通道
 * //                       打印新通道的代价和、新通道中代价最小的值、新通道中代价最大的值
 * // 1 <= n <= 10^6
 * // 1 <= q <= 5 * 10^4
 * // 1 <= 所有查询给出的点的总数 <= 2 * n
 * // 测试链接 : https://www.luogu.com.cn/problem/P4103
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_BigProject1 {
    public static int MAXN = 1000001;
    public static int MAXP = 20;
    public static long INF = 1L << 60;
    public static int n, q, k;

    public static int[] headg = new int[MAXN];
    public static int[] nextg = new int[MAXN << 1];
    public static int[] tog = new int[MAXN << 1];
    public static int cntg;

    public static int[] headv = new int[MAXN];
    public static int[] nextv = new int[MAXN];
    public static int[] tov = new int[MAXN];
    public static int cntv;

    public static int[] dep = new int[MAXN];
    public static int[] dfn = new int[MAXN];
    public static int[][] stjump = new int[MAXN][MAXP];
    public static int cntd;

    public static int[] arr = new int[MAXN];
    public static boolean[] isKey = new boolean[MAXN];

    public static int[] tmp = new int[MAXN << 1];
    public static int[] stk = new int[MAXN];
    // siz[u]表示子树u上，关键点的数量
    // sum[u]表示子树u上，所有关键点到u的总距离
    // near[u]表示子树u上，到u最近关键点的距离
    // far[u]表示子树u上，到u最远关键点的距离
    public static int[] siz = new int[MAXN];
    public static long[] sum = new long[MAXN];
    public static long[] near = new long[MAXN];
    public static long[] far = new long[MAXN];

    public static long costSum, costMax, costMin;

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

    public static void dfs1(int u, int fa) {
        dep[u] = dep[fa] + 1;
        dfn[u] = ++cntd;
        stjump[u][0] = fa;
        for (int p = 1; p < MAXP; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for(int e = headg[u]; e > 0; e = nextg[e]) {
            if (tog[e] != fa) {
                dfs1(tog[e], u);
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
                dfn[u] = ++cntd;
                stjump[u][0] = f;
                for (int p = 1; p < MAXP; p++) {
                    stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
                }
                e = headg[u];
            } else {
                e = nextg[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (tog[e] != f) {
                    push(tog[e], u, -1);
                }
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

    public static int buildVirtualTree1() {
        sortByDfn(arr, 1, k);
        int len = 0;
        for (int i = 1; i < k; i++) {
            tmp[++len] = arr[i];
            tmp[++len] = getLca(arr[i], arr[i + 1]);
        }
        tmp[++len] = arr[k];
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
            addEdgeV(getLca(tmp[i], tmp[i + 1]), tmp[i + 1]);
        }
        return tmp[1];
    }

    public static int buildVirtualTree2() {
        sortByDfn(arr, 1, k);
        cntv = 0;
        headv[arr[1]] = 0;
        int top = 0;
        stk[++top] = arr[1];
        for (int i = 2; i <= k; i++) {
            int x = arr[i];
            int y = stk[top];
            int lca = getLca(x, y);
            while (top > 1 && dfn[stk[top - 1]] >= dfn[lca]) {
                addEdgeV(stk[top - 1], stk[top]);
                top--;
            }
            if (lca != stk[top]) {
                headv[lca] = 0;
                addEdgeV(lca, stk[top]);
                stk[top] = lca;
            }
            headv[x] = 0;
            stk[++top] = x;
        }
        while (top > 1) {
            addEdgeV(stk[top - 1], stk[top]);
            top--;
        }
        return stk[1];
    }

    public static void dp1(int u) {
        siz[u] = isKey[u] ? 1 : 0;
        sum[u] = 0;
        if (isKey[u]) {
            near[u] = far[u] = 0;
        } else {
            near[u] = INF;
            far[u] = -INF;
        }
        for(int e = headv[u]; e > 0; e = nextv[e]) {
            dp1(tov[e]);
        }
        for(int e = headv[u]; e > 0; e = nextv[e]) {
            int v = tov[e];
            long len = dep[v] - dep[u];
            costSum += (sum[u] + siz[u] * len) * siz[v] + sum[v] * siz[u];
            siz[u] += siz[v];
            sum[u] += sum[v] + len * siz[v];
            costMin = Math.min(costMin, near[u] + near[v] + len);
            costMax = Math.max(costMax, far[u] + far[v] + len);
            near[u] = Math.min(near[u], near[v] + len);
            far[u] = Math.max(far[u], far[v] + len);
        }
    }

    public static void dp2(int tree) {
        stacksize = 0;
        push(tree, 0, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                siz[u] = isKey[u] ? 1 : 0;
                sum[u] = 0;
                if (isKey[u]) {
                    near[u] = far[u] = 0;
                } else {
                    near[u] = INF;
                    far[u] = -INF;
                }
                e = headv[u];
            } else {
                e = nextv[e];
            }
            if (e != 0) {
                push(u, 0, e);
                push(tov[e], 0, -1);
            } else {
                for(int ei = headv[u]; ei > 0; ei = nextv[ei]) {
                    int v = tov[ei];
                    long len = dep[v] - dep[u];
                    costSum += (sum[u] + siz[u] * len) * siz[v] + sum[v] * siz[u];
                    siz[u] += siz[v];
                    sum[u] += sum[v] + len * siz[v];
                    costMin = Math.min(costMin, near[u] + near[v] + len);
                    costMax = Math.max(costMax, far[u] + far[v] + len);
                    near[u] = Math.min(near[u], near[v] + len);
                    far[u] = Math.max(far[u], far[v] + len);
                }
            }
        }
    }

    public static void compute() {
        for (int i = 1; i <= k; i++) {
            isKey[arr[i]] = true;
        }
        costSum = 0;
        costMax = -INF;
        costMin = INF;
        int tree = buildVirtualTree1();
        dp1(tree);
//        int tree = buildVirtualTree2();
//        dp2(tree);
        for (int i = 1; i <= k; i++) {
            isKey[arr[i]] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1, u, v; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            addEdgeG(u, v);
            addEdgeG(v, u);
        }
        dfs1(1, 0);
//        dfs2();
        q = in.nextInt();
        for (int i = 1; i <= q; i++) {
            k = in.nextInt();
            for (int j = 1; j <= k; j++) {
                arr[j] = in.nextInt();
            }
            compute();
            out.println(costSum + " " + costMin + " " + costMax);
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
