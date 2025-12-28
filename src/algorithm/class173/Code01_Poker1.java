package algorithm.class173;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/4 20:25
 * // 由乃打扑克，java版
 * // 给定一个长度为n的数组arr，接下来有m条操作，操作类型如下
 * // 操作 1 l r v : 查询arr[l..r]范围上，第v小的数
 * // 操作 2 l r v : arr[l..r]范围上每个数加v，v可能是负数
 * // 1 <= n、m <= 10^5
 * // -2 * 10^4 <= 数组中的值 <= +2 * 10^4
 * // 测试链接 : https://www.luogu.com.cn/problem/P5356
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_Poker1 {
    public static int MAXN = 100001;
    public static int MAXB = 1001;
    public static int n;
    public static int m;
    public static int[] arr = new int[MAXN];
    public static int[] sortv = new int[MAXN];

    public static int blen, bnum;
    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXB];
    public static int[] br = new int[MAXB];
    public static int[] lazy = new int[MAXB];

    public static void innerAdd(int l, int r, int v) {
        for (int i = l; i <= r; i++) {
            arr[i] += v;
        }
        // for (int i = bl[bi[l]]; i <= br[bi[r]]; i++) {
        for (int i = bl[bi[l]]; i <= br[bi[l]]; i++) {
            sortv[i] = arr[i];
        }
        // Arrays.sort(sortv, bl[bi[l]], br[bi[r]] + 1);
        Arrays.sort(sortv, bl[bi[l]], br[bi[l]] + 1);
    }


    public static void add(int l, int r, int v) {
        if (bi[l] == bi[r]) {
            innerAdd(l, r, v);
        } else {
            innerAdd(l, br[bi[l]], v);
            innerAdd(bl[bi[r]], r, v);
            for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
                lazy[i] += v;
            }
        }
    }

    public static int getMin(int l, int r) {
        int lb = bi[l], rb = bi[r], ans = 100000000;
        if (lb == rb) {
            for (int i = l; i <= r; i++) {
                ans = Math.min(ans, arr[i] + lazy[lb]);
            }
        } else {
            for (int i = l; i <= br[lb]; i++) {
                ans = Math.min(ans, arr[i] + lazy[lb]);
            }
            for (int i = bl[rb]; i <= r; i++) {
                ans = Math.min(ans, arr[i] + lazy[rb]);
            }
            for (int i = lb + 1; i <= rb - 1; i++) {
                ans = Math.min(ans, sortv[bl[i]] + lazy[i]);
            }
        }
        return ans;
    }


    public static int getMax(int l, int r) {
        int lb = bi[l], rb = bi[r], ans = -100000000;
        if (lb == rb) {
            for (int i = l; i <= r; i++) {
                ans = Math.max(ans, arr[i] + lazy[lb]);
            }
        } else {
            for (int i = l; i <= br[lb]; i++) {
                ans = Math.max(ans, arr[i] + lazy[lb]);
            }
            for (int i = bl[rb]; i <= r; i++) {
                ans = Math.max(ans, arr[i] + lazy[rb]);
            }
            for (int i = lb + 1; i <= rb - 1; i++) {
                ans = Math.max(ans, sortv[br[i]] + lazy[i]);
            }
        }
        return ans;
    }

    // i块内小于等于v的个数
    public static int blockCnt(int i, int v) {
        v -= lazy[i];
        int l = bl[i], r = br[i], find = l;
        if (sortv[l] > v) {
            return 0;
        }
        if (sortv[r] <= v) {
            return r - l + 1;
        }
        while (l <= r) {
            int m = (l + r) / 2;
            if (sortv[m] <= v) {
                find = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        // return find - l + 1;
        return find - bl[i] + 1;
    }

    public static int getCnt(int l, int r, int v) {
        int lb = bi[l], rb = bi[r], ans = 0;
        if (lb == rb) {
            for (int i = l; i <= r; i++) {
                if (arr[i] + lazy[lb] <= v) {
                    ans++;
                }
            }
        } else {
            for (int i = l; i <= br[lb]; i++) {
                if (arr[i] + lazy[lb] <= v) {
                    ans++;
                }
            }
            for (int i = bl[rb]; i <= r; i++) {
                if (arr[i] + lazy[rb] <= v) {
                    ans++;
                }
            }
            for (int i = lb + 1; i <= rb - 1; i++) {
                ans += blockCnt(i, v);
            }
        }
        return ans;
    }

    public static int query(int l, int r, int k) {
        if (k < 1 || k > r - l + 1) {
            return -1;
        }
        int maxv = getMax(l, r);
        int minv = getMin(l, r);
        int midv;
        int ans = 0;
        while (minv <= maxv) {
            midv = (minv + maxv) / 2;
            if (getCnt(l, r, midv) >= k) {
                ans = midv;
                maxv = midv - 1;
            } else {
                minv = midv + 1;
            }
        }
        return ans;
    }

    public static void prepare() {
        // 减少块的长度，增加块的数量，卡常数时间
        blen = (int) Math.sqrt(n / 2);
        // blen = (int) Math.sqrt(n);
        bnum = (n + blen - 1) / blen;
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= bnum; i++) {
            bl[i] = (i - 1) * blen + 1;
            br[i] = Math.min(i * blen, n);
        }
        for (int i = 1; i <= n; i++) {
            sortv[i] = arr[i];
        }
        for (int i = 1; i <= bnum; i++) {
            Arrays.sort(sortv, bl[i], br[i] + 1);
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
        prepare();
        for (int i = 1, l, r, k, op; i <= m; i++) {
            op = in.nextInt();
            l = in.nextInt();
            r = in.nextInt();
            k = in.nextInt();
            if (op == 1) {
                int ans = query(l, r, k);
                out.println(ans);
            } else {
                add(l, r, k);
            }
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
