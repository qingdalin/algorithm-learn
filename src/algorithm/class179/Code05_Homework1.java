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
 * @date: 2025/10/12 11:16
 * // 作业，java版
 * // 给定一个长度为n的数组arr，接下来有m条查询，格式如下
 * // 查询 l r a b : 打印arr[l..r]范围上的两个答案
 * //                答案1，数值范围在[a, b]的数字个数
 * //                答案2，数值范围在[a, b]的数字种数
 * // 1 <= 所有数据 <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P4396
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_Homework1 {
    public static int MAXN = 100001;
    public static int MAXV = 100000;
    public static int MAXB = 400;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXB];
    public static int[] br = new int[MAXB];
    public static int[][] query = new int[MAXN][5];

    public static int[] numCnt = new int[MAXN];
    public static int[] blockCnt = new int[MAXB];
    public static int[] blockKind = new int[MAXB];

    public static int[] ans1 = new int[MAXN];
    public static int[] ans2 = new int[MAXN];

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

    public static void add(int x) {
        numCnt[x]++;
        blockCnt[bi[x]]++;
        if (numCnt[x] == 1) {
            blockKind[bi[x]]++;
        }
    }

    public static void del(int x) {
        numCnt[x]--;
        blockCnt[bi[x]]--;
        if (numCnt[x] == 0) {
            blockKind[bi[x]]--;
        }
    }

    public static void setAns(int a, int b, int id) {
        if (bi[a] == bi[b]) {
            for (int i = a; i <= b; i++) {
                if (numCnt[i] > 0) {
                    ans1[id] += numCnt[i];
                    ans2[id]++;
                }
            }
        } else {
            for (int i = a; i <= br[bi[a]]; i++) {
                if (numCnt[i] > 0) {
                    ans1[id] += numCnt[i];
                    ans2[id]++;
                }
            }
            for (int i = bl[bi[b]]; i <= b; i++) {
                if (numCnt[i] > 0) {
                    ans1[id] += numCnt[i];
                    ans2[id]++;
                }
            }
            for (int i = bi[a] + 1; i <= bi[b] - 1; i++) {
                ans1[id] += blockCnt[i];
                ans2[id] += blockKind[i];
            }
        }
    }

    public static void compute() {
        int winl = 1, winr = 0;
        for (int i = 1, id, jobl, jobr, joba, jobb; i <= m; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
            joba = query[i][2];
            jobb = query[i][3];
            id = query[i][4];
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
            setAns(joba, jobb, id);
        }
    }

    public static void prepare() {
        int blen = (int) Math.sqrt(MAXV);
        int bnum = (MAXV + blen - 1) / blen;
        for (int i = 1; i <= MAXV; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= bnum; i++) {
            bl[i] = (i - 1) * blen + 1;
            br[i] = Math.min(MAXV, i * blen);
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
            query[i][2] = in.nextInt();
            query[i][3] = in.nextInt();
            query[i][4] = i;
        }
        prepare();
        compute();
        for (int i = 1; i <= m; i++) {
            out.println(ans1[i] + " " + ans2[i]);
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
