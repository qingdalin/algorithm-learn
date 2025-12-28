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
 * @date: 2025/9/13 17:50
 * // 莫队二次离线入门题，java版
 * // 给定一个长度为n的数组arr，给定一个非负整数k，下面给出k1二元组的定义
 * // 位置二元组(i, j)，i和j必须是不同的，并且 arr[i]异或arr[j] 的二进制状态里有k个1
 * // 当i != j时，(i, j)和(j, i)认为是相同的二元组
 * // 一共有m条查询，格式为 l r : 打印arr[l..r]范围上，有多少k1二元组
 * // 1 <= n、m <= 10^5
 * // 0 <= arr[i]、k < 16384(2的14次方)
 * // 测试链接 : https://www.luogu.com.cn/problem/P4887
 * // 提交以下的code，提交时请把类名改成"Main"
 * // java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
 * // 想通过用C++实现，本节课Code01_MoOfflineTwice2文件就是C++的实现
 * // 两个版本的逻辑完全一样，C++版本可以通过所有测试
 */
public class Code01_MoOfflineTwice1 {
    public static int MAXN = 100002;
    public static int MAXV = 1 << 14;
    public static int n, m, k;
    public static int[] arr = new int[MAXN];
    public static int[] bi = new int[MAXN];
    public static int[] kOneArr = new int[MAXV];
    public static int cntk;

    public static int[][] query = new int[MAXN][3];

    // 离线任务，x、l、r、op、id
    // 位置x的任务列表用链式前向星表示
    // headl[x]，x在l~r左侧的离线任务列表
    // headr[x]，x在l~r右侧的离线任务列表
    public static int[] headl = new int[MAXN];
    public static int[] headr = new int[MAXN];
    public static int[] nextq = new int[MAXN << 1];
    public static int[] ql = new int[MAXN << 1];
    public static int[] qr = new int[MAXN << 1];
    public static int[] qop = new int[MAXN << 1];
    public static int[] qid = new int[MAXN << 1];
    public static int cntq;
    // cnt[v] : 当前的数字v作为第二个数，之前出现的数字作为第一个数，产生多少k1二元组
    public static int[] cnt = new int[MAXN];
    public static long[] pre = new long[MAXN];
    public static long[] suf = new long[MAXN];
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

    public static int lowbit(int i) {
        return i & -i;
    }

    public static int countOne(int num) {
        int ret = 0;
        while (num > 0) {
            ret++;
            num -= lowbit(num);
        }
        return ret;
    }

    public static void addLeftOffline(int x, int l, int r, int op, int id) {
        nextq[++cntq] = headl[x];
        headl[x] = cntq;
        ql[cntq] = l;
        qr[cntq] = r;
        qop[cntq] = op;
        qid[cntq] = id;
    }

    public static void addRightOffline(int x, int l, int r, int op, int id) {
        nextq[++cntq] = headr[x];
        headr[x] = cntq;
        ql[cntq] = l;
        qr[cntq] = r;
        qop[cntq] = op;
        qid[cntq] = id;
    }

    public static void prepare() {
        int blen = (int) Math.sqrt(n);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        Arrays.sort(query, 1, m + 1, new QueryCmp());
        for (int v = 0; v < MAXV; v++) {
            if (countOne(v) == k) {
                kOneArr[++cntk] = v;
            }
        }
    }

    public static void compute() {
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + cnt[arr[i]];
            for (int j = 1; j <= cntk; j++) {
                cnt[arr[i] ^ kOneArr[j]]++;
            }
        }
        Arrays.fill(cnt, 0);
        for(int i = n; i >= 1; i--) {
            suf[i] = suf[i + 1] + cnt[arr[i]];
            for (int j = 1; j <= cntk; j++) {
                cnt[arr[i] ^ kOneArr[j]]++;
            }
        }
        int winl = 1, winr = 0;
        for (int i = 1, jobl, jobr, id; i <= m; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
            id = query[i][2];
            if (winr < jobr) {
                addLeftOffline(winl - 1, winr + 1, jobr, -1, id);
                ans[id] += pre[jobr] - pre[winr];
            }
            if (winr > jobr) {
                addLeftOffline(winl - 1, jobr + 1, winr, 1, id);
                ans[id] -= pre[winr] - pre[jobr];
            }
            winr = jobr;
            if (winl > jobl) {
                addRightOffline(winr + 1, jobl, winl - 1, -1, id);
                ans[id] += suf[jobl] - suf[winl];
            }
            if (winl < jobl) {
                addRightOffline(winr + 1, winl, jobl - 1, 1, id);
                ans[id] -= suf[winl] - suf[jobl];
            }
            winl = jobl;
        }
        Arrays.fill(cnt, 0);
        for (int x = 0; x <= n; x++) {
            if (x >= 1) {
                for (int j = 1; j <= cntk; j++) {
                    cnt[arr[x] ^ kOneArr[j]]++;
                }
            }
            for(int q = headl[x]; q > 0; q = nextq[q]) {
                int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
                for (int j = l; j <= r; j++) {
                    ans[id] += (long) op * cnt[arr[j]];
                }
            }
        }
        Arrays.fill(cnt, 0);
        for (int x = n + 1; x >= 1; x--) {
            if (x <= n) {
                for (int j = 1; j <= cntk; j++) {
                    cnt[arr[x] ^ kOneArr[j]]++;
                }
            }
            for(int q = headr[x]; q > 0; q = nextq[q]) {
                int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
                for (int j = l; j <= r; j++) {
                    ans[id] += (long) op * cnt[arr[j]];
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        k = in.nextInt();
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
