package algorithm.class173;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/5 7:54
 * // 序列，java版
 * // 给定一个长度为n的数组arr，初始时刻认为是第0秒
 * // 接下来发生m条操作，第i条操作发生在第i秒，操作类型如下
 * // 操作 1 l r v : arr[l..r]范围上每个数加v，v可能是负数
 * // 操作 2 x v   : 不包括当前这一秒，查询过去多少秒内，arr[x] >= v
 * // 2 <= n、m <= 10^5
 * // -10^9 <= 数组中的值 <= +10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P3863
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_Sequence1 {
    public static int MAXN = 100001;
    public static int MAXB = 501;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    // op == 1 表示修改事件、位置x、时刻t、修改效果v、空缺
    // op == 2 表示查询事件、位置x、时刻t、查询标准v、问题编号q
    public static int[][] event = new int[MAXN << 2][5];
    public static int cnte = 0;
    public static int cntq = 0;
    public static long[] tim = new long[MAXN];
    public static long[] sortv = new long[MAXN];
    public static int blen, bnum;
    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXB];
    public static int[] br = new int[MAXB];
    public static long[] lazy = new long[MAXB];
    public static int[] ans = new int[MAXN];

    public static void innerAdd(int l, int r, int v) {
        for (int i = l; i <= r; i++) {
            tim[i] += v;
        }
        for (int i = bl[bi[l]]; i <= br[bi[l]]; i++) {
            sortv[i] = tim[i];
        }
        Arrays.sort(sortv, bl[bi[l]], br[bi[l]] + 1);
    }

    public static void add(int l, int r, int v) {
        if (l > r) {
            return;
        }
        if (bi[l] == bi[r]) {
            innerAdd(l, r, v);
        } else {
            innerAdd(l, br[bi[l]], v);
            innerAdd(bl[bi[r]], r, v);
            for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
                lazy[i] += v;
            }
        }
    }

    public static int innerQuery(int l, int r, long v) {
        v -= lazy[bi[l]];
        int ans = 0;
        for (int i = l; i <= r; i++) {
            if (tim[i] >= v) {
                ans++;
            }
        }
        return ans;
    }

    public static int getCnt(int i, long v) {
        // 少写了
        v -= lazy[i];
        int l = bl[i], r = br[i], pos = br[i] + 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (sortv[mid] >= v) {
                pos = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return br[i] - pos + 1;
    }

    public static int query(int l, int r, long v) {
        if (l > r) {
            return 0;
        }
        int ans = 0;
        if (bi[l] == bi[r]) {
            ans = innerQuery(l, r, v);
        } else {
            ans += innerQuery(l, br[bi[l]], v);
            ans += innerQuery(bl[bi[r]], r, v);
            for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
                ans += getCnt(i, v);
            }
        }
        return ans;
    }

    public static void addChange(int x, int t, int v) {
        event[++cnte][0] = 1;
        event[cnte][1] = x;
        event[cnte][2] = t;
        event[cnte][3] = v;
    }

    public static void addQuery(int x, int t, int v) {
        event[++cnte][0] = 2;
        event[cnte][1] = x;
        event[cnte][2] = t;
        event[cnte][3] = v;
        event[cnte][4] = ++cntq;
    }

    public static void prepare() {
        blen = (int) Math.sqrt(m);
        bnum = (m + blen - 1) / blen;
        for (int i = 1; i <= m; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= bnum; i++) {
            bl[i] = (i - 1) * blen + 1;
            br[i] = Math.min(i * blen, m);
        }
        // 所有事件根据位置x排序，位置一样的事件，修改事件先执行，查询事件后执行
        Arrays.sort(event, 1, cnte + 1, (a, b) -> a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]);
    }


    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        m++;
        for (int t = 2, l, r, x, v, op; t <= m; t++) {
            op = in.nextInt();
            if (op == 1) {
                l = in.nextInt();
                r = in.nextInt();
                v = in.nextInt();
                addChange(l, t, v);
                addChange(r + 1, t, -v);
            } else {
                x = in.nextInt();
                v = in.nextInt();
                addQuery(x, t, v);
            }
        }
        prepare();
        for (int i = 1, op, x, t, v, q; i <= cnte; i++) {
            op = event[i][0];
            x = event[i][1];
            t = event[i][2];
            v = event[i][3];
            q = event[i][4];
            if (op == 1) {
                add(t, m, v);
            } else {
                ans[q] = query(1, t - 1, v - arr[x]);
            }
        }
        for (int i = 1; i <= cntq; i++) {
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
