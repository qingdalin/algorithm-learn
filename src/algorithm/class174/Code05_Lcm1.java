package algorithm.class174;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/20 9:28
 * // 最小公倍数，java版
 * // 有n个点组成的无向图，依次给出m条无向边，每条边都有边权，并且边权很特殊
 * // u v a b : u到v的边，边权 = 2的a次方 * 3的b次方
 * // 接下来有q条查询，每条查询的格式如下
 * // u v a b : 从u出发可以随意选择边到达v，打印是否存在一条路径，满足如下条件
 * //           路径上所有边权的最小公倍数 = 2的a次方 * 3的b次方
 * // 1 <= n、q <= 5 * 10^4
 * // 1 <= m <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P3247
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_Lcm1 {
    public static int MAXN = 50001;
    public static int MAXM = 100001;
    public static int MAXQ = 50001;
    public static int n, m, q;
    public static int blen, bnum;
    public static int[] eu = new int[MAXM];
    public static int[] ev = new int[MAXM];
    public static int[] ea = new int[MAXM];
    public static int[] eb = new int[MAXM];

    public static int[] qu = new int[MAXQ];
    public static int[] qv = new int[MAXQ];
    public static int[] qa = new int[MAXQ];
    public static int[] qb = new int[MAXQ];

    public static int[] edge = new int[MAXM];
    public static int[] query = new int[MAXQ];
    public static int[] cur = new int[MAXQ];
    public static int cursize;

    public static int[] fa = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] maxa = new int[MAXN];
    public static int[] maxb = new int[MAXN];
    public static int[][] rollback = new int[MAXM][5];
    public static int opsize;

    public static boolean[] ans = new boolean[MAXQ];

    public static void sort(int[] idx, int[] val, int l, int r) {
        if (l >= r) {
            return;
        }
        int i = l, j = r, pivot = val[idx[(l + r) >> 1]], tmp;
        while (i <= j) {
            while (val[idx[i]] < pivot) {
                i++;
            }
            while (val[idx[j]] > pivot) {
                j--;
            }
            if (i <= j) {
               tmp = idx[i];
               idx[i] = idx[j];
               idx[j] = tmp;
               i++;
               j--;
            }
        }
        sort(idx, val, l, j);
        sort(idx, val, i, r);
    }

    public static void build() {
        for (int i = 1; i <= n; i++) {
            fa[i] = i;
            siz[i] = 1;
            maxa[i] = -1;
            maxb[i] = -1;
        }
    }

    public static int find(int i) {
        while (i != fa[i]) {
            i = fa[i];
        }
        return fa[i];
    }

    public static void union(int x, int y, int a, int b) {
        int fx = find(x);
        int fy = find(y);
        if (siz[fx] < siz[fy]) {
            int tmp = fx;
            fx = fy;
            fy = tmp;
        }
        rollback[++opsize][0] = fx;
        rollback[opsize][1] = fy;
        rollback[opsize][2] = siz[fx];
        rollback[opsize][3] = maxa[fx];
        // 错误写法 rollback[opsize][4] = maxa[fx];
        rollback[opsize][4] = maxb[fx];
        if (fx != fy) {
            fa[fy] = fx;
            siz[fx] += siz[fy];
        }
        maxa[fx] = Math.max(Math.max(maxa[fx], maxa[fy]), a);
        maxb[fx] = Math.max(Math.max(maxb[fx], maxb[fy]), b);
    }

    public static void undo() {
        for (int fx, fy; opsize > 0; opsize--) {
            fx = rollback[opsize][0];
            fy = rollback[opsize][1];
            fa[fy] = fy;
            siz[fx] = rollback[opsize][2];
            maxa[fx] = rollback[opsize][3];
            maxb[fx] = rollback[opsize][4];
        }
    }

    public static boolean check(int x, int y, int a, int b) {
        int fx = find(x);
        int fy = find(y);
        return fx == fy && maxa[fx] == a && maxb[fx] == b;
    }

    public static void compute(int l, int r) {
        // 重要剪枝
        // 保证每条查询只在一个边的序列块中处理
        cursize = 0;
        for (int i = 1; i <= q; i++) {
            if (ea[edge[l]] <= qa[query[i]] && (r + 1 > m || qa[query[i]] < ea[edge[r + 1]])) {
                cur[++cursize] = query[i];
            }
        }
        if (cursize > 0) {
            build();
            // 本题直接排序能通过，就不写归并了
            sort(edge, eb, 1, l - 1);
            for (int i = 1, j = 1; i <= cursize; i++) {
                while (j < l && eb[edge[j]] <= qb[cur[i]]) {
                    union(eu[edge[j]], ev[edge[j]], ea[edge[j]], eb[edge[j]]);
                    j++;
                }
                opsize = 0;
                for (int k = l; k <= r; k++) {
                    if (ea[edge[k]] <= qa[cur[i]] && eb[edge[k]] <= qb[cur[i]]) {
                        union(eu[edge[k]], ev[edge[k]], ea[edge[k]], eb[edge[k]]);
                    }
                }
                ans[cur[i]] = check(qu[cur[i]], qv[cur[i]], qa[cur[i]], qb[cur[i]]);
                undo();
            }
        }
    }

    public static void prepare() {
        int log2n = 0;
        while ((1 << log2n) <= (n >> 1)) {
            log2n++;
        }
        blen = Math.max(1, (int) Math.sqrt(m * log2n));
        bnum = (m + blen - 1) / blen;
        sort(edge, ea, 1, m);
        sort(query, qb, 1, q);
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= m; i++) {
            eu[i] = in.nextInt();
            ev[i] = in.nextInt();
            ea[i] = in.nextInt();
            eb[i] = in.nextInt();
            edge[i] = i;
        }
        q = in.nextInt();
        for (int i = 1; i <= q; i++) {
            qu[i] = in.nextInt();
            qv[i] = in.nextInt();
            qa[i] = in.nextInt();
            qb[i] = in.nextInt();
            query[i] = i;
        }
        prepare();
        for (int i = 1, l, r; i <= bnum; i++) {
            l = (i - 1) * blen + 1;
            r = Math.min(i * blen, m);
            compute(l, r);
        }
        for (int i = 1; i <= q; i++) {
            out.println(ans[i] ? "Yes" : "No");
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
