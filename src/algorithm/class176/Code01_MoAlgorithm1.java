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
 * @date: 2025/8/23 21:18
 * // 普通莫队入门题，java版
 * // 给定一个长度为n的数组arr，一共有q条查询，格式如下
 * // 查询 l r : 打印arr[l..r]范围上有几种不同的数字
 * // 1 <= n <= 3 * 10^4
 * // 1 <= arr[i] <= 10^6
 * // 1 <= q <= 2 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/SP3267
 * // 测试链接 : https://www.spoj.com/problems/DQUERY/
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
// TODO: 2025/8/23 没有注册成功spoj
public class Code01_MoAlgorithm1 {
    public static int MAXN = 30001;
    public static int MAXV = 1000001;
    public static int MAXQ = 200001;
    public static int n, q;
    public static int[] arr = new int[MAXN];
    public static int[] bi = new int[MAXN];
    public static int[][] query = new int[MAXQ][3];
    public static int kind, blen;
    public static int[] cnt = new int[MAXV];
    public static int[] ans = new int[MAXQ];

    public static class QueryCmp1 implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            // 先根据jobl的块序号，从小到大
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            // 其次根据jobr从小到大
            return a[1] - b[1];
        }
    }

    public static class QueryCmp2 implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            // 先根据jobl的块序号，从小到大
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            // 如果jobl块是奇数，jobr从小到大,块是偶数，jobr从大到小
            if ((bi[a[0]] & 1) == 1) {
                return a[1] - b[1];
            } else {
                return b[1] - a[1];
            }
        }
    }

    public static void del(int num) {
        if (--cnt[num] == 0) {
            kind--;
        }
    }

    public static void add(int num) {
        if (++cnt[num] == 1) {
            kind++;
        }
    }

    public static void prepare() {
        blen = (int) Math.sqrt(n);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
//        Arrays.sort(query, 1, q + 1, new QueryCmp1());
        Arrays.sort(query, 1, q + 1, new QueryCmp2());
    }

    public static void compute() {
        int winl = 1, winr = 0;
        kind = 0;
        for (int i = 1, jobl, jobr; i <= q; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
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
            ans[query[i][2]] = kind;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        q = in.nextInt();
        for (int i = 1; i <= q; i++) {
            query[i][0] = in.nextInt();
            query[i][1] = in.nextInt();
            query[i][2] = i;
        }
        prepare();
        compute();
        for (int i = 1; i <= q; i++) {
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
