package algorithm.class175;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/8 20:15
 * // 数组查询，java版
 * // 给定一个长度为n的数组arr，接下来有q条查询，查询格式如下
 * // 查询 p k : p 不断变成 p + arr[p] + k，直到 p > n 停止，打印操作次数
 * // 1 <= n、q <= 10 ^ 5
 * // 1 <= arr[i] <= n
 * // 测试链接 : https://www.luogu.com.cn/problem/CF797E
 * // 测试链接 : https://codeforces.com/problemset/problem/797/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_ArrayQueries1 {
    public static int MAXN = 100001;
    public static int MAXB = 401;
    public static int n, m, blen;
    public static int[] arr = new int[MAXN];
    public static int[][] dp = new int[MAXN][MAXB];

    public static int query(int p, int k) {
        if (k <= blen) {
            return dp[p][k];
        }
        int ans = 0;
        while (p <= n) {
            ans++;
            p += arr[p] + k;
        }
        return ans;
    }

    public static void prepare() {
        blen = (int) Math.sqrt(n);
        for (int p = n; p >= 1; p--) {
            for (int k = 1; k <= blen; k++) {
                dp[p][k] = 1 + (p + arr[p] + k > n ? 0 : dp[p + arr[p] + k][k]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        m = in.nextInt();
        prepare();
        int p, k;
        for (int i = 1; i <= m; i++) {
            p = in.nextInt();
            k = in.nextInt();
            out.println(query(p, k));
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
