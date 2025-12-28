package algorithm.class180;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/15 16:10
 * // 消耗战，java版
 * // 一共有n个节点，给定n-1条无向边，每条边有边权，所有节点组成一棵树
 * // 一共有q条查询，每条查询格式如下
 * // 查询 k a1 a2 ... ak : 给出了k个不同的关键节点，并且一定不包含1号节点
 * //                       你可以随意选择边进行切断，切断的代价就是边权
 * //                       目的是让所有关键点都无法到达1号节点，打印最小总代价
 * // 1 <= n、q <= 5 * 10^5
 * // 1 <= 所有查询给出的点的总数 <= 5 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P2495
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_War1 {
    public static int MAXN = 500001;
    public static int MAXP = 20;
    public static int n, q, k;

    public static int[] headg = new int[MAXN];
    public static int[] nextg = new int[MAXN << 1];
    public static int[] tog = new int[MAXN << 1];
    public static int[] weightg = new int[MAXN << 1];
    public static int cntg;

    public static int[] headv = new int[MAXN];
    public static int[] nextv = new int[MAXN];
    public static int[] tov = new int[MAXN];
    public static int[] weightv = new int[MAXN];
    public static int cntv;

    public static int[] dep = new int[MAXN];
    public static int[] dfn = new int[MAXN];
    public static int[][] stjump = new int[MAXN][MAXP];
    public static int[][] mindist = new int[MAXN][MAXP];
    public static int cntd;

    public static int[] arr = new int[MAXN];
    public static boolean[] isKey = new boolean[MAXN];

    public static int[] tmp = new int[MAXN << 1];
    public static int[] stk = new int[MAXN];
    // cost[u]表示虚树中，u下方的所有关键节点，都连不上u的话，最小总代价
    public static long[] cost = new long[MAXN];

    public static void addEdgeG(int u, int v, int w) {
        nextg[++cntg] = headg[u];
        tog[cntg] = v;
        weightg[cntg] = w;
        headg[u] = cntg;
    }

    public static void addEdgeV(int u, int v, int w) {
        nextv[++cntv] = headv[u];
        tov[cntv] = v;
        weightv[cntv] = w;
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

    public static void dfs(int u, int fa, int w) {
        dep[u] = dep[fa] + 1;
        dfn[u] = ++cntd;
        stjump[u][0] = fa;
        mindist[u][0] = w;
        for (int p = 1; p < MAXP; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
            mindist[u][p] = Math.min(mindist[u][p - 1], mindist[stjump[u][p - 1]][p - 1]);
        }
        for (int e = headg[u]; e > 0; e = nextg[e]) {
            if (tog[e] != fa) {
                dfs(tog[e], u, weightg[e]);
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
    // 已知u一定是v的祖先节点，返回u到v路径上的最小边权
    public static int getDist(int u, int v) {
        int dist = 1000000001;
        for(int p = MAXP - 1; p >= 0; p--) {
            if (dep[stjump[v][p]] >= dep[u]) {
                dist = Math.min(dist, mindist[v][p]);
                v = stjump[v][p];
            }
        }
        return dist;
    }

    public static int buildVirtualTree1() {
        sortByDfn(arr, 1, k);
        int len = 0;
        tmp[++len] = 1;
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
            int lca = getLca(tmp[i], tmp[i + 1]);
            addEdgeV(lca, tmp[i + 1], getDist(lca, tmp[i + 1]));
        }
        return tmp[1];
    }

    public static int buildVirtualTree2() {
        sortByDfn(arr, 1, k);
        cntv = 0;
        headv[1] = 0;
        int top = 0;
        stk[++top] = 1;
        for (int i = 1; i <= k; i++) {
            int x = arr[i];
            int y = stk[top];
            int lca = getLca(x, y);
            while (top > 1 && dfn[stk[top - 1]] >= dfn[lca]) {
                addEdgeV(stk[top - 1], stk[top], getDist(stk[top - 1], stk[top]));
                top--;
            }
            if (lca != stk[top]) {
                headv[lca] = 0;
                addEdgeV(lca, stk[top], getDist(lca, stk[top]));
                stk[top] = lca;
            }
            headv[x] = 0;
            stk[++top] = x;
        }
        while (top > 1) {
            addEdgeV(stk[top - 1], stk[top], getDist(stk[top - 1], stk[top]));
            top--;
        }
        return stk[1];
    }

    public static void dp(int u) {
        for(int e = headv[u]; e > 0; e = nextv[e]) {
            dp(tov[e]);
        }
        cost[u] = 0;
        for(int e = headv[u], v, w; e > 0; e = nextv[e]) {
            v = tov[e];
            w = weightv[e];
            if (isKey[v]) {
                cost[u] += w;
            } else {
                cost[u] += Math.min(cost[v], w);
            }
        }
    }

    public static long compute() {
        for (int i = 1; i <= k; i++) {
            isKey[arr[i]] = true;
        }
        int tree = buildVirtualTree1();
//        int tree = buildVirtualTree2();
        dp(tree);
        for (int i = 1; i <= k; i++) {
            isKey[arr[i]] = false;
        }
        return cost[1];
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1, u, v, w; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            w = in.nextInt();
            addEdgeG(u, v, w);
            addEdgeG(v, u, w);
        }
        dfs(1, 0, 0);
        q = in.nextInt();
        for (int i = 1; i <= q; i++) {
            k = in.nextInt();
            for (int j = 1; j <= k; j++) {
                arr[j] = in.nextInt();
            }
            compute();
            out.println(compute());
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
