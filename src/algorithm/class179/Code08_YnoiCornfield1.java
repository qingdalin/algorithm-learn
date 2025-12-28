package algorithm.class179;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/10/15 19:52
 * // 由乃的玉米田，java版
 * // 给定一个长度为n的数组arr，接下来有m条查询，查询格式如下
 * // 查询 1 l r x : 打印arr[l..r]范围上能否选出两个数，减的结果为x
 * // 查询 2 l r x : 打印arr[l..r]范围上能否选出两个数，加的结果为x
 * // 查询 3 l r x : 打印arr[l..r]范围上能否选出两个数，乘的结果为x
 * // 查询 4 l r x : 打印arr[l..r]范围上能否选出两个数，除的结果为x，并且没有余数
 * // 选出的这两个数可以是同一个位置的数，答案如果为是，打印 "yuno"，否则打印 "yumi"
 * // 1 <= 所有数据 <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P5355
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code08_YnoiCornfield1 {
    static class BitSet {
        int len;
        long[] status;

        public BitSet(int size) {
            len = (size + 63) >> 6;
            status = new long[len];
        }

        public void setOne(int bit) {
            status[bit >> 6] |= 1L << (bit & 63);
        }

        public void SetZero(int bit) {
            status[bit >> 6] &= ~(1L << (bit & 63));
        }

        public boolean getStatus(int bit) {
            return ((status[bit >> 6] >> (bit & 63)) & 1L) != 0;
        }

        // 检查 自己 & other右移k位 是否有1存在
        public boolean andOtherMoveRight(BitSet other, int move) {
            int ws = move >> 6;
            int bs = move & 63;
            for (int i = 0; i < len; i++) {
                int src = i + ws;
                if (src >= len) {
                    break;
                }
                long shifted = other.status[src] >>> bs;
                if (bs != 0 && src + 1 < len) {
                    shifted |= other.status[src + 1] << (64 - bs);
                }
                if ((status[i] & shifted) != 0L) {
                    return true;
                }
            }
            return false;
        }
    }

    public static int MAXN = 100001;
    public static int MAXV = 100000;
    public static int MAXB = 400;
    public static int n, m, blen;
    public static int[] arr = new int[MAXN];
    public static int[] bi = new int[MAXN];
    public static int[][] query = new int[MAXN][5];
    public static int cntq;

    public static int[] headq = new int[MAXB];
    public static int[] nextq = new int[MAXN];
    public static int[] ql = new int[MAXN];
    public static int[] qr = new int[MAXN];
    public static int[] qid = new int[MAXN];
    public static int cnts;

    public static int[] cnt = new int[MAXN];
    public static BitSet bisSet1 = new BitSet(MAXN);
    public static BitSet bisSet2 = new BitSet(MAXN);
    public static int[] pre = new int[MAXN];
    public static int[] dp = new int[MAXN];
    public static boolean[] ans = new boolean[MAXN];

    public static void addSpecial(int x, int l, int r, int id) {
        nextq[++cnts] = headq[x];
        ql[cnts] = l;
        qr[cnts] = r;
        qid[cnts] = id;
        headq[x] = cnts;
    }

    public static class QueryCmp implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            if ((bi[a[0]] & 1) == 1) {
                return a[1] - b[1];
            } else {
                return b[1] - a[1];
            }
        }
    }

    public static void add(int x) {
        cnt[x]++;
        if (cnt[x] == 1) {
            bisSet1.setOne(x);
            bisSet2.setOne(MAXV - x);
        }
    }

    public static void del(int x) {
        cnt[x]--;
        if (cnt[x] == 0) {
            bisSet1.SetZero(x);
            bisSet2.SetZero(MAXV - x);
        }
    }

    public static boolean calc(int op, int x) {
        if (op == 1) {
            return bisSet1.andOtherMoveRight(bisSet1, x);
        } else if (op == 2) {
            return bisSet1.andOtherMoveRight(bisSet2, MAXV - x);
        } else if (op == 3) {
            for (int f = 1; f * f <= x; f++) {
                if (x % f == 0 && bisSet1.getStatus(f) && bisSet1.getStatus(x / f)) {
                    return true;
                }
            }
            return false;
        } else {
            if (x >= 1) {
                for (int i = 1; i * x <= MAXV; i++) {
                    if (bisSet1.getStatus(i) && bisSet1.getStatus(i * x)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static void compute() {
        int winl = 1, winr = 0;
        for (int i = 1, jobl, jobr, jobx, id, op; i <= cntq; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
            jobx = query[i][2];
            op = query[i][3];
            id = query[i][4];
            while (winl > jobl) {
                add(arr[--winl]);
            }
            while (winr < jobr) {
                add(arr[++winr]);
            }
            while (winl < jobl) {
                del(arr[winl++]);
            }
            while (winr > jobr) {
                del(arr[winr--]);
            }
            ans[id] = calc(op, jobx);
        }
    }

    public static void special() {
        for (int x = 1; x < blen; x++) {
            if (headq[x] != 0) {
                Arrays.fill(pre, 0);
                Arrays.fill(dp, 0);
                for (int i = 1; i <= n; i++) {
                    int v = arr[i];
                    pre[v] = i;
                    dp[i] = dp[i - 1];
                    if (v * x <= MAXV) {
                        dp[i] = Math.max(dp[i], pre[v * x]);
                    }
                    if (v % x == 0) {
                        dp[i] = Math.max(dp[i], pre[v / x]);
                    }
                }
                for(int q = headq[x]; q > 0; q = nextq[q]) {
                    int l = ql[q];
                    int r = qr[q];
                    int id = qid[q];
                    ans[id] = l <= dp[r];
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        blen = (int) Math.sqrt(MAXV);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        for (int i = 1, op, l, r, x; i <= m; i++) {
            op = in.nextInt();
            l = in.nextInt();
            r = in.nextInt();
            x = in.nextInt();
            if (op == 4 && x < blen) {
                addSpecial(x, l, r, i);
            } else {
                query[++cntq][0] = l;
                query[cntq][1] = r;
                query[cntq][2] = x;
                query[cntq][3] = op;
                query[cntq][4] = i;
            }
        }
        Arrays.sort(query, 1, cntq + 1, new QueryCmp());
        compute();
        special();
        for (int i = 1; i <= m; i++) {
            if (ans[i]) {
                out.println("yuno");
            } else {
                out.println("yumi");
            }
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
