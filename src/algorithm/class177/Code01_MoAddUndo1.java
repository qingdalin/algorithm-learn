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
 * @date: 2025/9/3 20:24
 * // 只增回滚莫队入门题，java版
 * // 给定一个长度为n的数组arr，下面定义重要度的概念
 * // 如果一段范围上，数字x出现c次，那么这个数字的重要度为x * c
 * // 范围上的最大重要度，就是该范围上，每种数字的重要度，取最大值
 * // 一共有m条查询，格式 l r : 打印arr[l..r]范围上的最大重要度
 * // 1 <= n、m <= 10^5
 * // 1 <= arr[i] <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/AT_joisc2014_c
 * // 测试链接 : https://atcoder.jp/contests/joisc2014/tasks/joisc2014_c
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_MoAddUndo1 {
    public static int MAXN = 100001;
    public static int MAXB = 401;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] sorted = new int[MAXN];

    public static int[] cnt = new int[MAXN];
    public static int cntv;
    public static int[][] query = new int[MAXN][3];

    public static int blen, bnum;
    public static int[] bi = new int[MAXN];
    public static int[] br = new int[MAXB];
    public static long[] ans = new long[MAXN];
    public static long curAns;

    public static class QueryCmp implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            if (bi[a[0]] != bi[b[0]]) {
                return bi[a[0]] - bi[b[0]];
            }
            return a[1] - b[1];
        }
    }

    public static int kth(int num) {
        int l = 1, r = cntv, ans = 0, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (sorted[mid] >= num) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    public static long force(int l, int r) {
        for (int i = l; i <= r; i++) {
            cnt[arr[i]]++;
        }
        long ret = 0;
        for (int i = l; i <= r; i++) {
            ret = Math.max(ret, (long) cnt[arr[i]] * sorted[arr[i]]);
        }
        for (int i = l; i <= r; i++) {
            cnt[arr[i]]--;
        }
        return ret;
    }

    public static void add(int num) {
        cnt[num]++;
        curAns = Math.max(curAns, (long) cnt[num] * sorted[num]);
    }

    public static void del(int num) {
        cnt[num]--;
    }

    public static void compute() {
        for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
            curAns = 0;
            Arrays.fill(cnt, 1, cntv + 1, 0);
            int winl = br[block] + 1, winr = br[block];
            for (int jobl, jobr, id; qi <= m && bi[query[qi][0]] == block; qi++) {
                jobl = query[qi][0];
                jobr = query[qi][1];
                id = query[qi][2];
                if (jobr <= br[block]) {
                    ans[id] = force(jobl, jobr);
                } else {
                    while (winr < jobr) {
                        add(arr[++winr]);
                    }
                    long backup = curAns;
                    while (winl > jobl) {
                        add(arr[--winl]);
                    }
                    ans[id] = curAns;
                    curAns = backup;
                    while (winl <= br[block]) {
                        del(arr[winl++]);
                    }
                }
            }
        }
    }

    public static void prepare() {
        for (int i = 1; i <= n; i++) {
            sorted[i] = arr[i];
        }
        Arrays.sort(sorted, 1, n + 1);
        cntv = 1;
        for (int i = 2; i <= n; i++) {
            if (sorted[cntv] != sorted[i]) {
                sorted[++cntv] = sorted[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            arr[i] = kth(arr[i]);
        }
        blen = (int) Math.sqrt(n);
        bnum = (n + blen - 1) / blen;
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= bnum; i++) {
            br[i] = Math.min(i * blen, n);
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
