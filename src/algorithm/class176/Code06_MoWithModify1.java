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
 * @date: 2025/8/24 14:26
 * // 带修莫队入门题，java版
 * // 给定一个长度为n的数组arr，一共有m条操作，操作格式如下
 * // 操作 Q l r     : 打印arr[l..r]范围上有几种不同的数
 * // 操作 R pos val : 把arr[pos]的值设置成val
 * // 1 <= n、m <= 2 * 10^5
 * // 1 <= arr[i]、val <= 10^6
 * // 测试链接 : https://www.luogu.com.cn/problem/P1903
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code06_MoWithModify1 {
    public static int MAXN = 200001;
    public static int MAXV = 1000001;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] bi = new int[MAXN];
    public static int[][] query = new int[MAXN][4];
    public static int[][] update = new int[MAXN][2];
    public static int cntq, cntu;
    public static int[] cnt = new int[MAXV];
    public static int kind = 0;
    public static int[] ans = new int[MAXN];

    public static class QueryCmp implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            // 先按照jobl的块排序
            // 再按照jobr的块排序
            // 最后按照jobt的排序
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            if (bi[a[1]] != bi[b[1]]) {
                return bi[a[1]] - bi[b[1]];
            }
            return a[2] - b[2];
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

    public static void moveTime(int jobl, int jobr, int tim) {
        int pos = update[tim][0];
        int val = update[tim][1];
        if (jobl <= pos && pos <= jobr) {
            del(arr[pos]);
            add(val);
        }
        int tmp = arr[pos];
        arr[pos] = val;
        update[tim][1] = tmp;
    }

    public static void prepare() {
        int blen = (int) Math.pow(n, 2.0 / 3);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        Arrays.sort(query, 1, cntq + 1, new QueryCmp());
    }

    public static void compute() {
        int winl = 1, winr = 0, wint = 0;
        for (int i = 1, jobl, jobr, jobt, id; i <= cntq; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
            jobt = query[i][2];
            id = query[i][3];
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
            while (wint < jobt) {
                moveTime(jobl, jobr, ++wint);
            }
            while (wint > jobt) {
                moveTime(jobl, jobr, wint--);
            }
            ans[id] = kind;
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
        char op;
        for (int i = 1; i <= m; i++) {
            op = in.nextChar();
            if (op == 'Q') {
                cntq++;
                query[cntq][0] = in.nextInt();
                query[cntq][1] = in.nextInt();
                query[cntq][2] = cntu;
                query[cntq][3] = cntq;
            } else {
                cntu++;
                update[cntu][0] = in.nextInt();
                update[cntu][1] = in.nextInt();
            }
        }
        prepare();
        compute();
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
