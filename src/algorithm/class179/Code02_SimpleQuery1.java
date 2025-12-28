package algorithm.class179;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/10/10 21:00
 * // 简单的询问，java版
 * // 给定一个长度为n的数组arr，下标从1到n
 * // 函数get(l, r, x) = arr[l..r]范围上，数组x出现的次数
 * // 接下来有q条查询，格式如下
 * // 查询 l1 r1 l2 r2 : 每种x都算，打印 get(l1, r1, x) * get(l2, r2, x) 的累加和
 * // 1 <= n、q <= 5 * 10^4
 * // 1 <= arr[i] <= n
 * // 测试链接 : https://www.luogu.com.cn/problem/P5268
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_SimpleQuery1 {
    public static int MAXN = 50001;
    public static int n, q;
    public static int[] arr = new int[MAXN];
    public static int[] bi = new int[MAXN];
    // 查询任务，siz1、siz2、op、id
    public static int[][] query = new int[MAXN << 2][4];
    public static int cntq = 0;
    // cnt1 : arr[1..siz1]范围内每种数字出现的次数
    // cnt2 : arr[1..siz2]范围内每种数字出现的次数
    public static int[] cnt1 = new int[MAXN];
    public static int[] cnt2 = new int[MAXN];
    public static long curAns = 0;
    public static long[] ans = new long[MAXN];

    public static class QueryCmp implements Comparator<int[]> {
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

    public static void addQuery(int size1, int size2, int op, int id) {
        query[++cntq][0] = size1;
        query[cntq][1] = size2;
        query[cntq][2] = op;
        query[cntq][3] = id;
    }

    // win1和win2不代表一段区间，而是代表两个独立区域各自去覆盖arr
    // win1 = 0，win2 = 0，表示两个覆盖区域一开始都没有数字
    // job1和job2也不代表区间，而是代表两个区域各自要覆盖多大
    public static void compute() {
        int win1 = 0, win2 = 0;
        for (int i = 1, jobl, jobr, op, id; i <= cntq; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
            op = query[i][2];
            id = query[i][3];
            while (win1 < jobl) {
                win1++;
                cnt1[arr[win1]]++;
                curAns += cnt2[arr[win1]];
            }
            while (win1 > jobl) {
                cnt1[arr[win1]]--;
                curAns -= cnt2[arr[win1]];
                win1--;
            }
            while (win2 < jobr) {
                win2++;
                cnt2[arr[win2]]++;
                curAns += cnt1[arr[win2]];
            }
            while (win2 > jobr) {
                cnt2[arr[win2]]--;
                curAns -= cnt1[arr[win2]];
                win2--;
            }
            ans[id] += curAns * op;
        }
    }

    public static void prepare() {
        int blen = (int) Math.sqrt(n);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= cntq; i++) {
            if (query[i][0] > query[i][1]) {
                int tmp = query[i][0];
                query[i][0] = query[i][1];
                query[i][1] = tmp;
            }
        }
        Arrays.sort(query, 1, cntq + 1, new QueryCmp());
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        q = in.nextInt();
        for (int i = 1, l1, r1, l2, r2; i <= q; i++) {
            l1 = in.nextInt();
            r1 = in.nextInt();
            l2 = in.nextInt();
            r2 = in.nextInt();
            // a-l1, b-r1, c-l2, d-r2
            // (b - a) * (d - c)
            // bd - bc - ad + ac
            addQuery(r1, r2, 1, i);
            addQuery(r1, l2 - 1, -1, i);
            addQuery(l1 - 1, r2, -1, i);
            addQuery(l1 - 1, l2 - 1, 1, i);
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
