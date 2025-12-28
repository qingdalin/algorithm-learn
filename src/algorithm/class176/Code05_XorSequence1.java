package algorithm.class176;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/24 13:11
 * // 异或序列，java版
 * // 给定一个长度为n的数组arr，给定一个数字k，一共有m条查询，格式如下
 * // 查询 l r : arr[l..r]范围上，有多少子数组的异或和为k，打印其数量
 * // 0 <= n、m、k、arr[i] <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P4462
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_XorSequence1 {
    public static int MAXN = 100001;
    public static int MAXS = 1 << 20;
    public static int n,m,k;
    public static int[] arr = new int[MAXN];
    public static int[] bi = new int[MAXN];
    public static int[][] query = new int[MAXN][3];
    // pre[i] == x，表示前i个数字的前缀异或和为x
    public static int[] pre = new int[MAXN];
    // num表示窗口内，异或和为k的子数组数量
    public static long num = 0;
    // cnt[x] = a，表示窗口内，前缀异或和x，一共有a个
    public static long[] cnt = new long[MAXS];
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

    public static void del(int x) {
        if (k != 0) {
            num -= cnt[x] * cnt[x ^ k];
        } else {
            num -= (cnt[x] * (cnt[x] - 1)) / 2;
        }
        cnt[x]--;
        if (k != 0) {
            num += cnt[x] * cnt[x ^ k];
        } else {
            num += (cnt[x] * (cnt[x] - 1)) / 2;
        }
    }

    public static void add(int x) {
        if (k != 0) {
            num -= cnt[x] * cnt[x ^ k];
        } else {
            num -= (cnt[x] * (cnt[x] - 1)) / 2;
        }
        cnt[x]++;
        if (k != 0) {
            num += cnt[x] * cnt[x ^ k];
        } else {
            num += (cnt[x] * (cnt[x] - 1)) / 2;
        }
    }

    public static void prepare() {
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] ^ arr[i];
        }
        int blen = (int) Math.sqrt(n);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        Arrays.sort(query, 1, m + 1, new QueryCmp());
    }

    public static void compute() {
        int winl = 1, winr = 0;
        for (int i = 1, jobl, jobr; i <= m; i++) {
            jobl = query[i][0] - 1;
            jobr = query[i][1];
            while (winl > jobl) {
                add(pre[--winl]);
            }
            while (winr < jobr) {
                add(pre[++winr]);
            }
            while (winl < jobl) {
                del(pre[winl++]);
            }
            while (winr > jobr) {
                del(pre[winr--]);
            }
            ans[query[i][2]] = num;
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
