package algorithm.class173;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/5 9:16
 * // 磁力块，java版
 * // 磁块有五个属性值，x、y、m、p、range，磁块在(x, y)位置、质量为m、磁力为p、吸引半径range
 * // 磁块A可以把磁块B吸到磁块A的位置，需要满足如下的条件
 * // A与B的距离不大于A的吸引半径，并且B的质量不大于A的磁力
 * // 你有一个初始磁块，给定初始磁块的4个属性值(不给质量，因为没用)，你永远在初始磁块的位置
 * // 接下来给定n个磁块各自的5个属性值，你可以用初始磁块，吸过来其中的磁块
 * // 吸过来的磁块可以被你随意使用，返回你最多能吸过来多少磁块
 * // 1 <= n <= 3 * 10^5    -10^9 <= x、y <= +10^9    1 <= m、p、range <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P10590
 * // 测试链接 : https://codeforces.com/problemset/problem/198/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 * // 为了java的实现能通过，不把数据封装成一个磁块对象，然后去排序
 * // 手写了双指针快排优化常数时间，一般不需要这么做，正式比赛不卡常
 */
public class Code03_Magnet1 {
    public static int MAXN = 250001;
    public static int MAXB = 501;
    public static int n;
    public static int[] x = new int[MAXN];
    public static int[] y = new int[MAXN];
    public static int[] m = new int[MAXN];
    public static int[] p = new int[MAXN];
    public static long[] range = new long[MAXN];
    public static long[] dist = new long[MAXN];

    public static int blen, bnum;
    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXB];
    public static int[] br = new int[MAXB];
    public static int[] maxm = new int[MAXB];
    public static int[] que = new int[MAXN];
    public static boolean[] vis = new boolean[MAXN];

    public static void swap(int i, int j) {
        int tmp1;
        tmp1 = x[i]; x[i] = x[j]; x[j] = tmp1;
        tmp1 = y[i]; y[i] = y[j]; y[j] = tmp1;
        tmp1 = m[i]; m[i] = m[j]; m[j] = tmp1;
        tmp1 = p[i]; p[i] = p[j]; p[j] = tmp1;
        long tmp2;
        tmp2 = range[i]; range[i] = range[j]; range[j] = tmp2;
        tmp2 = dist[i]; dist[i] = dist[j]; dist[j] = tmp2;
    }

    public static void swapByM(int l, int r) {
        if (l >= r) {
            return;
        }
        int pivot = m[(l + r) >>> 1];
        int i = l, j = r;
        while (i <= j) {
            while (m[i] < pivot) {
                i++;
            }
            while (m[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(i++, j--);
            }
        }
        swapByM(l, j);
        swapByM(i, r);
    }

    public static void swapByDist(int l, int r) {
        if (l >= r) {
            return;
        }
        long pivot = dist[(l + r) >>> 1];
        int i = l, j = r;
        while (i <= j) {
            while (dist[i] < pivot) {
                i++;
            }
            while (dist[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(i++, j--);
            }
        }
        swapByDist(l, j);
        swapByDist(i, r);
    }

    public static int bfs() {
        int l = 1, r = 1, ans = 0;
        que[r++] = 0;
        vis[0] = true;
        while (l < r) {
            int cur = que[l++];
            for (int b = 1; b <= bnum; b++) {
                if (maxm[b] <= p[cur]) {
                    while (bl[b] <= br[b] && dist[bl[b]] <= range[cur]) {
                        int i = bl[b];
                        if (!vis[i]) {
                            vis[i] = true;
                            que[r++] = i;
                            ans++;
                        }
                        bl[b]++;
                    }
                } else {
                    for (int j = bl[b]; j <= br[b]; j++) {
                        if (dist[j] <= range[cur] && m[j] <= p[cur] && !vis[j]) {
                            vis[j] = true;
                            que[r++] = j;
                            ans++;
                        }
                    }
                    break;
                }
            }
        }
        return ans;
    }

    public static void prepare() {
        blen = (int) Math.sqrt(n);
        bnum = (n + blen - 1) / blen;
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= bnum; i++) {
            bl[i] = (i - 1) * blen + 1;
            br[i] = Math.min(i * blen, n);
        }
        swapByM(1, n);
        for (int i = 1; i <= bnum; i++) {
            maxm[i] = m[br[i]];
            swapByDist(bl[i], br[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        x[0] = in.nextInt();
        y[0] = in.nextInt();
        p[0] = in.nextInt();
        range[0] = in.nextInt();
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            x[i] = in.nextInt();
            y[i] = in.nextInt();
            m[i] = in.nextInt();
            p[i] = in.nextInt();
            range[i] = in.nextInt();
        }
        for (int i = 0; i <= n; i++) {
            range[i] *= range[i];
            long dx = x[i] - x[0];
            long dy = y[i] - y[0];
            dist[i] = dx * dx + dy * dy;
        }
        prepare();
        out.println(bfs());
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

