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
 * @date: 2025/8/24 9:47
 * // 小Z的袜子，java版
 * // 给定一个长度为n的数组arr，一共有m条查询，格式如下
 * // 查询 l r : arr[l..r]范围上，随机选不同位置的两个数，打印数值相同的概率
 * //            概率用分数的形式表达，并且约分到最简的形式
 * // 1 <= n、m、arr[i] <= 5 * 10^4
 * // 测试链接 : https://www.luogu.com.cn/problem/P1494
 * // 提交以下的code，提交时请把类名改成"Main"
 * // java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
 * // 想通过用C++实现，本节课Code03_SockFromZ2文件就是C++的实现
 * // 两个版本的逻辑完全一样，C++版本可以通过所有测试
 */
public class Code03_SockFromZ1 {
    public static int MAXN = 50001;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] bi = new int[MAXN];
    public static int[] cnt = new int[MAXN];
    public static int[][] query = new int[MAXN][3];
    public static long sum = 0;
    // 分子
    public static long[] ans1 = new long[MAXN];
    // 分母
    public static long[] ans2 = new long[MAXN];

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

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void del(int num) {
        sum -= (long) cnt[num] * cnt[num];
        cnt[num]--;
        sum += (long) cnt[num] * cnt[num];
    }

    public static void add(int num) {
        sum -= (long) cnt[num] * cnt[num];
        cnt[num]++;
        sum += (long) cnt[num] * cnt[num];
    }

    public static void prepare() {
        int blen = (int) Math.sqrt(n);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
//        Arrays.sort(query, 1, m + 1, new QueryCmp1());
        Arrays.sort(query, 1, m + 1, new QueryCmp2());
    }

    public static void compute() {
        int winl = 1, winr = 0;
        for (int i = 1, jobl, jobr, id; i <= m; i++) {
            jobl = query[i][0];
            jobr = query[i][1];
            id = query[i][2];
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
            if (jobl == jobr) {
                ans1[id] = 0;
                ans2[id] = 1;
            } else {
                // 错误写法 long k = jobl - jobr + 1;
                long k = jobr - jobl + 1;
                ans1[id] = sum - k;
                ans2[id] = k * (k - 1);
                long g = gcd(ans1[id], ans2[id]);
                ans1[id] /= g;
                ans2[id] /= g;
            }
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
        for (int i = 1; i <= m; i++) {
            query[i][0] = in.nextInt();
            query[i][1] = in.nextInt();
            query[i][2] = i;
        }
        prepare();
        compute();
        for (int i = 1; i <= m; i++) {
            out.println(ans1[i] + "/" + ans2[i]);
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
