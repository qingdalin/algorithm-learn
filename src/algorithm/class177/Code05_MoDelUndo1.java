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
 * @date: 2025/9/13 9:58
 * // 只删回滚莫队入门题，java版
 * // 本题最优解为主席树，讲解158，题目2，已经讲述
 * // 给定一个长度为n的数组arr，一共有m条查询，格式如下
 * // 查询 l r : 打印arr[l..r]内没有出现过的最小自然数，注意0是自然数
 * // 0 <= n、m、arr[i] <= 2 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P4137
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_MoDelUndo1 {
    public static int MAXN = 200001;
    public static int MAXB = 501;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[][] query = new int[MAXN][3];
    public static int bnum, blen;
    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXB];
    public static int[] cnt = new int[MAXN];
    public static int mex;
    public static int[] ans = new int[MAXN];

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
        if (--cnt[num] == 0) {
            mex = Math.min(mex, num);
        }
    }

    public static void add(int num) {
        cnt[num]++;
    }

    public static void compute() {
        for (int i = 1; i <= n; i++) {
            cnt[arr[i]]++;
        }
        mex = 0;
        while (cnt[mex] != 0) {
            mex++;
        }
        int winl = 1, winr = n;
        for (int block = 1, qi = 1; qi <= m && block <= bnum; block++) {
            while (winl < bl[block]) {
                del(arr[winl++]);
            }
            int beforeJob = mex;
            for (int jobl, jobr, id; qi <= m && bi[query[qi][0]] == block; qi++) {
                jobl = query[qi][0];
                jobr = query[qi][1];
                id = query[qi][2];
                while (winr > jobr) {
                    del(arr[winr--]);
                }
                int backup = mex;
                while (winl < jobl) {
                    del(arr[winl++]);
                }
                ans[id] = mex;
                mex = backup;
                while (winl > bl[block]) {
                    add(arr[--winl]);
                }
            }
            while (winr < n) {
                add(arr[++winr]);
            }
            mex = beforeJob;
        }
    }

    public static void prepare() {
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
