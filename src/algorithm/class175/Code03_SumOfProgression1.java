package algorithm.class175;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/9 7:29
 * // 等差数列求和，java版
 * // 一共有t组测试，每组测试遵循同样的设定
 * // 给定一个长度为n的数组arr，接下来有q条查询，查询格式如下
 * // 查询 s d k : arr[s]作为第1项、arr[s + 1d]作为第2项、arr[s + 2d]作为第3项...
 * //             每项的值 * 项的编号，一共k项都累加起来，打印累加和
 * // 1 <= n <= 10^5
 * // 1 <= q <= 2 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/CF1921F
 * // 测试链接 : https://codeforces.com/problemset/problem/1921/F
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_SumOfProgression1 {
    public static int MAXN = 100001;
    public static int MAXB = 401;
    public static int n, m, blen, t;
    public static int[] arr = new int[MAXN];
    public static long[][] f = new long[MAXB][MAXN];
    public static long[][] g = new long[MAXB][MAXN];

    public static long query(int s, int d, int k) {
        long ans = 0;
        if (d <= blen) {
            ans = g[d][s];
            int next = s + d * k;
            if (next <= n) {
                ans -= (g[d][next] + k * f[d][next]);
            }
        } else {
            for (int i = 1; i <= k; i++) {
                ans += (long) arr[s + (i - 1) * d] * i;
            }
        }
        return ans;
    }

    public static void prepare() {
        blen = (int) Math.sqrt(n);
        for (int d = 1; d <= blen; d++) {
            for(int i = n; i >= 1; i--) {
                f[d][i] = arr[i] + (i + d > n ? 0 : f[d][i + d]);
            }
        }
        for (int d = 1; d <= blen; d++) {
            for(int i = n; i >= 1; i--) {
                g[d][i] = f[d][i] + (i + d > n ? 0 : g[d][i + d]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        t = in.nextInt();
        for (int test = 0; test < t; test++) {
            n = in.nextInt();
            m = in.nextInt();
            for (int i = 1; i <= n; i++) {
                arr[i] = in.nextInt();
            }
            prepare();
            int s, d, k;
            for (int i = 1; i <= m; i++) {
                s = in.nextInt();
                d = in.nextInt();
                k = in.nextInt();
                out.print(query(s, d, k) + " ");
            }
            out.println();
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
