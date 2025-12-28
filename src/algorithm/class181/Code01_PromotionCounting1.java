package algorithm.class181;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/16 14:12
 * // 晋升者计数，java版
 * // 一共有n个人，给定每个人的能力值arr[i]，所有人组成一棵树，代表公司的组织
 * // 1号人是整个公司的老板，从2号人开始，给定每个人的上司编号fa[i]
 * // 打印第i号人为头的子树中，有多少个人的能力值 > 第i号人的能力值，一共n条打印
 * // 1 <= n <= 10^5
 * // 1 <= arr[i] <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P3605
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_PromotionCounting1 {
    public static int MAXN = 100001;
    public static int MAXT = MAXN * 40;
    public static int n;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg;

    public static int[] arr = new int[MAXN];
    public static int[] sorted = new int[MAXN];
    public static int cntv;

    public static int[] root = new int[MAXN];
    public static int[] ls = new int[MAXT];
    public static int[] rs = new int[MAXT];
    public static int[] siz = new int[MAXT];
    public static int cntt;

    public static int[] ans = new int[MAXN];

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static int kth(int num) {
        int left = 1, right = cntv, mid, ret = 0;
        while (left <= right) {
            mid = (left + right) >> 1;
            if (sorted[mid] >= num) {
                ret = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ret;
    }

    public static void up(int i) {
        siz[i] = siz[ls[i]] + siz[rs[i]];
    }

    public static int add(int jobi, int l, int r, int i) {
        int rt = i;
        if (rt == 0) {
            rt = ++cntt;
        }
        if (l == r) {
            siz[rt]++;
        } else {
            int mid = (l + r) / 2;
            if (jobi <= mid) {
                ls[rt] = add(jobi, l, mid, ls[rt]);
            } else {
                rs[rt] = add(jobi, mid + 1, r, rs[rt]);
            }
            up(rt);
        }
        return rt;
    }

    public static int merge(int l, int r, int t1, int t2) {
        if (t1 == 0 || t2 == 0) {
            return t1 + t2;
        }
        if (l == r) {
            siz[t1] += siz[t2];
        } else {
            int mid = (l + r) / 2;
            ls[t1] = merge(l, mid, ls[t1], ls[t2]);
            rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
            up(t1);
        }
        return t1;
    }

    public static int query(int jobl, int jobr, int l, int r, int i) {
        if (jobl > jobr || i == 0) {
            return 0;
        }
        if (jobl <= l && r <= jobr) {
            return siz[i];
        }
        int mid = (l + r) / 2;
        int ret = 0;
        if (jobl <= mid) {
            ret += query(jobl, jobr, l, mid, ls[i]);
        }
        if (jobr > mid) {
            ret += query(jobl, jobr, mid + 1, r, rs[i]);
        }
        return ret;
    }

    public static void calc1(int u, int fa) {
        for (int e = head[u]; e > 0; e = next[e]) {
            int v = to[e];
            if (v != fa) {
                calc1(v, u);
                root[u] = merge(1, cntv, root[u], root[v]);
            }
        }
        ans[u] = query(arr[u] + 1, cntv, 1, cntv, root[u]);
    }

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

    public static void calc2() {
        stacksize = 0;
        push(1, 0, -1);
        while (stacksize > 0) {
            pop();
            if (e == -1) {
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (to[e] != f) {
                    push(to[e], u, -1);
                }
            } else {
                for(int ei = head[u]; ei > 0; ei = next[ei]) {
                    int v = to[ei];
                    if (v != f) {
                        root[u] = merge(1, cntv, root[u], root[v]);
                    }
                }
                ans[u] = query(arr[u] + 1, cntv, 1, cntv, root[u]);
            }
        }
    }

    public static void compute() {
        for (int i = 1; i <= n; i++) {
            sorted[i] = arr[i];
        }
        Arrays.sort(sorted, 1, n + 1);
        cntv = 1;
        for (int i = 2; i <= n; i++) {
            if (sorted[cntv] != sorted[i]) {
                sorted[++cntv] = sorted[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            arr[i] = kth(arr[i]);
        }
        for (int i = 1; i <= n; i++) {
            root[i] = add(arr[i], 1, cntv, root[i]);
        }
//        calc1(1, 0);
        calc2();
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        for (int i = 2, fa; i <= n; i++) {
            fa = in.nextInt();
            addEdge(fa, i);
            addEdge(i, fa);
        }
        compute();
        for (int i = 1; i <= n; i++) {
            out.println(ans[i]);
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
