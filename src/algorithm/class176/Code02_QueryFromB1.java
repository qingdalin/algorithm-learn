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
 * @date: 2025/8/24 9:18
 * // 小B的询问，java版
 * // 给定一个长度为n的数组arr，所有数字在[1..k]范围上
 * // 定义f(i) = i这种数的出现次数的平方
 * // 一共有m条查询，格式如下
 * // 查询 l r : arr[l..r]范围上，打印 f(1) + f(2) + .. + f(k) 的值
 * // 1 <= n、m、k <= 5 * 10^4
 * // 测试链接 : https://www.luogu.com.cn/problem/P2709
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_QueryFromB1 {
    public static int MAXN = 50001;
    public static int n, m, k;
    public static int[] arr = new int[MAXN];
    public static int[][] query = new int[MAXN][3];
    public static int[] bi = new int[MAXN];
    public static int[] cnt = new int[MAXN];
    public static int sum = 0;
    public static int[] ans = new int[MAXN];

    public static class QueryCmp1 implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            return a[1] - b[1];
        }
    }

    public static class QueryCmp2 implements Comparator<int[]> {
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

    public static void del(int num) {
        sum -= cnt[num] * cnt[num];
        cnt[num]--;
        sum += cnt[num] * cnt[num];
    }

    public static void add(int num) {
        sum -= cnt[num] * cnt[num];
        cnt[num]++;
        sum += cnt[num] * cnt[num];
    }

    public static void prepare() {
        int blen = (int) Math.sqrt(n);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        Arrays.sort(query, 1, m + 1, new QueryCmp1());
//        Arrays.sort(query, 1, m + 1, new QueryCmp2());
    }

    public static void compute() {
        int winl = 1, winr = 0;
        for (int i = 1, jobl, jobr; i <= m; i++) {
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
            ans[query[i][2]] = sum;
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
