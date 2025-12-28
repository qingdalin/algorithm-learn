package algorithm.class177;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/13 10:57
 * // 秃子酋长，java版
 * // 给定一个长度为n的数组arr，一共有m条查询，格式如下
 * // 查询 l r : 打印arr[l..r]范围上，如果所有数排序后，
 * //            相邻的数在原序列中的位置的差的绝对值之和
 * // 注意arr很特殊，1~n这些数字在arr中都只出现1次
 * // 1 <= n、m <= 5 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P8078
 * // 提交以下的code，提交时请把类名改成"Main"
 * // java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
 * // 想通过用C++实现，本节课Code06_BaldChief2文件就是C++的实现
 * // 两个版本的逻辑完全一样，C++版本可以通过所有测试
 */
public class Code06_BaldChief1 {
    public static int MAXN = 500001;
    public static int MAXB = 801;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] pos = new int[MAXN];
    public static int[][] query = new int[MAXN][3];

    public static int blen, bnum;
    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXN];

    public static int[] next = new int[MAXN + 1];
    public static int[] last = new int[MAXN + 1];

    public static long sum;
    public static long[] ans = new long[MAXN];

    public static class QueryCmp implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            return b[1] - a[1];
        }
    }

    public static void del(int num) {
        int less = last[num], more = next[num];
        if (less != 0) {
            sum -= Math.abs(pos[num] - pos[less]);
        }
        if (more != n + 1) {
            sum -= Math.abs(pos[more] - pos[num]);
        }
        if (less != 0 && more != n + 1) {

            sum += Math.abs(pos[more] - pos[less]);
        }
        next[less] = more;
        last[more] = less;
    }

    public static void add(int num) {
        next[last[num]] = num;
        last[next[num]] = num;
    }

    public static void compute() {
        for (int v = 1; v <= n; v++) {
            last[v] = v - 1;
            next[v] = v + 1;
        }
        next[0] = 1;
        last[n + 1] = n;
        for (int v = 2; v <= n; v++) {
            sum += Math.abs(pos[v] - pos[v - 1]);
        }
        int winl = 1, winr = n;
        for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
            while (winl < bl[block]) {
                del(arr[winl++]);
            }
            long beforeJob = sum;
            for (int jobl, jobr, id; qi <= m && bi[query[qi][0]] == block; qi++) {
                jobl = query[qi][0];
                jobr = query[qi][1];
                id = query[qi][2];
                while (winr > jobr) {
                    del(arr[winr--]);
                }
                long backup = sum;
                while (winl < jobl) {
                    del(arr[winl++]);
                }
                ans[id] = sum;
                sum = backup;
                while (winl > bl[block]) {
                    add(arr[--winl]);
                }
            }
            while (winr < n) {
                add(arr[++winr]);
            }
            sum = beforeJob;
        }
    }

    public static void prepare() {
        for (int i = 1; i <= n; i++) {
            pos[arr[i]] = i;
        }
        blen = (int) Math.sqrt(n);
        bnum = (n + blen - 1) / blen;
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= bnum; i++) {
            bl[i] = (i - 1) * blen + 1;
        }
        Arrays.sort(query, 1, m + 1, new QueryCmp());
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
