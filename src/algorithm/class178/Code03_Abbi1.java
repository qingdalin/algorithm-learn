package algorithm.class178;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/14 14:06
 * // 区间Abbi值，java版
 * // 给定一个长度为n的数组arr，区间Abbi值的定义如下
 * // 如果arr[l..r]包含数字v，并且v是第k小，那么这个数字的Abbi值 = v * k
 * // 区间Abbi值 = 区间内所有数字Abbi值的累加和
 * // 比如[1, 2, 2, 3]的Abbi值 = 1 * 1 + 2 * 2 + 2 * 2 + 3 * 4 = 21
 * // 一共有m条查询，格式为 l r : 打印arr[l..r]的区间Abbi值
 * // 1 <= n、m <= 5 * 10^5
 * // 1 <= arr[i] <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P5501
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_Abbi1 {
    public static int MAXN = 500001;
    public static int MAXV = 100000;
    public static int MAXB = 401;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static long[] preSum = new long[MAXN];

    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXB];
    public static int[] br = new int[MAXB];

    public static int[][] query = new int[MAXN][3];
    public static int[] headq = new int[MAXN];
    public static int[] nextq = new int[MAXN << 1];
    public static int[] ql = new int[MAXN << 1];
    public static int[] qr = new int[MAXN << 1];
    public static int[] qop = new int[MAXN << 1];
    public static int[] qid = new int[MAXN << 1];
    public static int cntq;

    public static long[] treeCnt = new long[MAXV + 1];
    public static long[] treeSum = new long[MAXV + 1];
    public static long[] pre = new long[MAXN];

    // 值域分块，统计<x的个数
    public static int[] blockLessCnt = new int[MAXB];
    public static int[] numLessCnt = new int[MAXN];
    // 值域分块，统计>x的前缀和
    public static long[] blockMoreSum = new long[MAXB];
    public static long[] numMoreSum = new long[MAXN];
    public static long[] ans = new long[MAXN];

    public static class QueryCmp implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            return a[1] - b[1];
        }
    }

    public static void addOffline(int x, int l, int r, int op, int id) {
        nextq[++cntq] = headq[x];
        headq[x] = cntq;
        ql[cntq] = l;
        qr[cntq] = r;
        qop[cntq] = op;
        qid[cntq] = id;
    }

    public static int lowbit(int i) {
        return i & -i;
    }

    public static void add(long[] tree, int i, int v) {
        while (i <= MAXV) {
            tree[i] += v;
            i += lowbit(i);
        }
    }

    public static long sum(long[] tree, int i) {
        long ret = 0;
        while (i > 0) {
            ret += tree[i];
            i -= lowbit(i);
        }
        return ret;
    }

    public static void addVal(int val) {
        for (int b = bi[val] + 1; b <= bi[MAXV]; b++) {
            blockLessCnt[b]++;
        }
        for (int i = val + 1; i <= br[bi[val]]; i++) {
            numLessCnt[i]++;
        }
        for (int b = 1; b <= bi[val] - 1; b++) {
            blockMoreSum[b] += val;
        }
        for (int i = bl[bi[val]]; i < val; i++) {
            numMoreSum[i] += val;
        }
    }

    public static int lessCnt(int val) {
        return blockLessCnt[bi[val]] + numLessCnt[val];
    }

    public static long moreSum(int val) {
        return blockMoreSum[bi[val]] + numMoreSum[val];
    }

    public static void prepare() {
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + arr[i];
        }
        int blen = (int) Math.sqrt(MAXV);
        int bnum = (MAXV + blen - 1) / blen;
        for (int i = 1; i <= MAXV; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= bnum; i++) {
            bl[i] = (i - 1) * blen + 1;
            br[i] = Math.min(i * blen, MAXV);
        }
        Arrays.sort(query, 1, m + 1, new QueryCmp());
    }

    public static void compute() {
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + sum(treeCnt, arr[i] - 1) * arr[i] + sum(treeSum, MAXV) - sum(treeSum, arr[i]);
            add(treeCnt, arr[i], 1);
            add(treeSum, arr[i], arr[i]);
        }
        int winl = 1, winr = 0;
        for (int i = 1, jobl, jobr, id; i <= m; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
            id = query[i][2];
            if (winr < jobr) {
                addOffline(winl - 1, winr + 1, jobr, -1, id);
                ans[id] += pre[jobr] - pre[winr];
            }
            if (winr > jobr) {
                addOffline(winl - 1, jobr + 1, winr, 1, id);
                ans[id] -= pre[winr] - pre[jobr];
            }
            winr = jobr;
            if (winl > jobl) {
                addOffline(winr, jobl, winl - 1, 1, id);
                ans[id] -= pre[winl - 1] - pre[jobl - 1];
            }
            if (winl < jobl) {
                addOffline(winr, winl, jobl - 1, -1, id);
                ans[id] += pre[jobl - 1] - pre[winl - 1];
            }
            winl = jobl;
        }
        long tmp;
        for (int x = 0; x <= n; x++) {
            if (x >= 1) {
                addVal(arr[x]);
            }
            for(int q = headq[x]; q > 0; q = nextq[q]) {
                int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
                for (int j = l; j <= r; j++) {
                    tmp = (long) arr[j] * lessCnt(arr[j]) + moreSum(arr[j]);
                    if (op == 1) {
                        ans[id] += tmp;
                    } else {
                        ans[id] -= tmp;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        for (int i = 1; i <= m; i++) {
            query[i][0] = in.nextInt();
            query[i][1] = in.nextInt();
            query[i][2] = i;
        }
        prepare();
        compute();
        for (int i = 2; i <= m; i++) {
            ans[query[i][2]] += ans[query[i - 1][2]];
        }
        for (int i = 1; i <= m; i++) {
            ans[query[i][2]] += preSum[query[i][1]] - preSum[query[i][0] - 1];
        }
        for (int i = 1; i <= m; i++) {
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
