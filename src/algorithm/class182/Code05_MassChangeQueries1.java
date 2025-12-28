package algorithm.class182;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/22 11:41
 * // 值全改的操作，java版
 * // 给定一个长度为n的数组arr，接下来有q条操作，格式如下
 * // 操作 l r x y : arr[l..r]范围上，所有数字x改成数字y
 * // 所有操作做完之后，从左到右打印arr中的值
 * // 1 <= n、q <= 2 * 10^5
 * // 1 <= arr[i]、x、y <= 100
 * // 测试链接 : https://www.luogu.com.cn/problem/CF911G
 * // 测试链接 : https://codeforces.com/problemset/problem/911/G
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_MassChangeQueries1 {
    public static int MAXN = 200001;
    public static int MAXT = MAXN * 10;
    public static int MAXV = 100;
    public static int n, q;
    public static int[] arr = new int[MAXN];
    public static int[] root = new int[MAXV + 1];
    public static int[] ls = new int[MAXT];
    public static int[] rs = new int[MAXT];
    public static boolean[] status = new boolean[MAXT];
    public static int[] pool = new int[MAXT];
    public static int top;

    public static void prepare() {
        top = 0;
        for (int i = 1; i < MAXT; i++) {
            pool[++top] = i;
        }
    }

    public static int newNode() {
        return pool[top--];
    }

    public static void del(int i) {
        pool[++top] = i;
        ls[i] = 0;
        rs[i] = 0;
        status[i] = false;
    }

    public static void up(int i) {
        status[i] = status[ls[i]] | status[rs[i]];
    }

    public static int insert(int jobi, int l, int r, int i) {
        int rt = i;
        if (rt == 0) {
            rt = newNode();
        }
        if (l == r) {
            status[rt] = true;
        } else {
            int mid = (l + r) / 2;
            if (jobi <= mid) {
                ls[rt] = insert(jobi, l, mid, ls[rt]);
            } else {
                rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
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
            status[t1] |= status[t2];
        } else {
            int mid = (l + r) / 2;
            ls[t1] = merge(l, mid, ls[t1], ls[t2]);
            rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
            up(t1);
        }
        del(t2);
        return t1;
    }

    public static int tree1, tree2;

    public static void split(int jobl, int jobr, int l, int r, int t1) {
        if (t1 == 0) {
            tree1 = t1;
            tree2 = 0;
            return;
        }
        if (jobl <= l && r <= jobr) {
            tree1 = 0;
            tree2 = t1;
            return;
        }
        int t2 = newNode();
        int mid = (l + r) / 2;
        if (jobl <= mid) {
            split(jobl, jobr, l, mid, ls[t1]);
            ls[t1] = tree1;
            ls[t2] = tree2;
        }
        if (jobr > mid) {
            split(jobl, jobr, mid + 1, r, rs[t1]);
            rs[t1] = tree1;
            rs[t2] = tree2;
        }
        up(t1);
        up(t2);
        tree1 = t1;
        tree2 = t2;
    }

    public static void dfs(int val, int l, int r, int i) {
        if (i == 0 || !status[i]) {
            return;
        }
        if (l == r) {
            arr[l] = val;
        } else {
            int mid = (l + r) / 2;
            dfs(val, l, mid, ls[i]);
            dfs(val, mid + 1, r, rs[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        prepare();
        n = in.nextInt();
        for (int i = 1, x; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            root[arr[i]] = insert(i, 1, n, root[arr[i]]);
        }
        q = in.nextInt();
        for (int i = 1, l, r, x, y; i <= q; i++) {
            l = in.nextInt();
            r = in.nextInt();
            x = in.nextInt();
            y = in.nextInt();
            split(l, r, 1, n, root[x]);
            root[x] = tree1;
            root[y] = merge(1, n, root[y], tree2);
        }
        for (int v = 1; v <= MAXV; v++) {
            dfs(v, 1, n, root[v]);
        }
        for (int i = 1; i <= n; i++) {
            out.print(arr[i] + " ");
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
