package algorithm.class175;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/10 13:30
 * // 最少划分，java版
 * // 给定一个长度为n的数组arr，考虑如下问题的解
 * // 数组arr划分成若干段子数组，保证每段不同数字的种类 <= k，返回至少划分成几段
 * // 打印k = 1, 2, 3..n时，所有的答案
 * // 1 <= arr[i] <= n <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/CF786C
 * // 测试链接 : https://codeforces.com/problemset/problem/786/C
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code06_TillCollapse1 {
    public static int MAXN = 100001;
    public static int n, blen;
    public static int[] arr = new int[MAXN];
    public static boolean[] vis = new boolean[MAXN];
    public static int[] ans = new int[MAXN];

    public static int query(int limit) {
        int kind = 0, cnt = 0, start = 1;
        for (int i = 1; i <= n; i++) {
            if (!vis[arr[i]]) {
                kind++;
                if (kind > limit) {
                    cnt++;
                    for (int j = start; j < i; j++) {
                        vis[arr[j]] = false;
                    }
                    start = i;
                    kind = 1;
                }
                vis[arr[i]] = true;
            }
        }
        if (kind > 0) {
            cnt++;
            for (int j = start; j <= n; j++) {
                vis[arr[j]] = false;
            }
        }
        return cnt;
    }

    public static int jump(int l, int r,int curAns) {
        int find = l;
        while (l <= r) {
            int mid = (l + r) >> 1;
            int check = query(mid);
            if (check < curAns) {
                r = mid - 1;
            } else if (check > curAns) {
                l = mid + 1;
            } else {
                find = mid;
                l = mid + 1;
            }
        }
        return find + 1;
    }

    public static void compute() {
        for (int i = 1; i <= blen; i++) {
            ans[i] = query(i);
        }
        for (int i = blen + 1; i <= n; i = jump(i, n, ans[i])) {
            ans[i] = query(i);
        }
    }

    public static void prepare() {
        int log2n = 0;
        while ((1 << log2n) <= (n >> 1)) {
            log2n++;
        }
        blen = Math.max(1, (int) Math.sqrt(n * log2n));
        Arrays.fill(ans, 1, n + 1, -1);
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        prepare();
        compute();
        for (int i = 1; i <= n; i++) {
            if (ans[i] == -1) {
                ans[i] = ans[i - 1];
            }
            out.print(ans[i] + " ");
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
