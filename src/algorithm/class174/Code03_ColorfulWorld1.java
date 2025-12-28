package algorithm.class174;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/19 14:39
 * // 五彩斑斓的世界，java版
 * // 给定一个长度为n的数组arr，一共有m条操作，每条操作类型如下
 * // 操作 1 l r x : arr[l..r]范围上，所有大于x的数减去x
 * // 操作 2 l r x : arr[l..r]范围上，查询x出现的次数
 * // 1 <= n <= 10^6
 * // 1 <= m <= 5 * 10^5
 * // 0 <= arr[i]、x <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P4117
 * // 提交以下的code，提交时请把类名改成"Main"
 * // java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
 * // 想通过用C++实现，本节课Code03_ColorfulWorld2文件就是C++的实现
 * // 两个版本的逻辑完全一样，C++版本可以通过所有测试
 */
public class Code03_ColorfulWorld1 {
    public static int MAXN = 1000001;
    public static int MAXM = 500001;
    public static int MAXV = 100002;
    public static int n, m;
    public static int blen, bnum;
    public static int[] arr = new int[MAXN];
    public static int[] op = new int[MAXM];
    public static int[] ql = new int[MAXM];
    public static int[] qr = new int[MAXM];
    public static int[] qx = new int[MAXM];
    public static int maxv, lazy;
    public static int[] fa = new int[MAXV];
    public static int[] pre0 = new int[MAXN];
    public static int[] cntv = new int[MAXV];
    public static int[] ans = new int[MAXM];

    public static int find(int i) {
        if (i != fa[i]) {
            fa[i] = find(fa[i]);
        }
        return fa[i];
    }

    public static void change(int x, int y) {
        fa[x] = y;
    }

    // 修改保留在值域并查集，把修改写入arr[l..r]
    public static void down(int l, int r) {
        for (int i = l; i <= r; i++) {
            arr[i] = find(arr[i]);
        }
    }

    public static void update(int qi, int l, int r) {
        int jobl = ql[qi], jobr = qr[qi], jobx = qx[qi];
        if (jobx >= maxv - lazy || jobl > r || jobr < l) {
            return;
        }
        if (jobl <= l && r <= jobr) {
            if ((jobx << 1) <= maxv - lazy) {
                // x小于maxv的一半
                for (int v = lazy + 1; v <= lazy + jobx; v++) {
                    cntv[v + jobx] += cntv[v];
                    cntv[v] = 0;
                    change(v, v + jobx);
                }
                lazy += jobx;
            } else {
                for (int v = lazy + 1 + jobx; v <= maxv; v++) {
                    cntv[v - jobx] += cntv[v];
                    cntv[v] = 0;
                    change(v, v - jobx);
                }
                for (int v = maxv; v >= 0; v--) {
                    if (cntv[v] != 0) {
                        maxv = v;
                        break;
                    }
                }
            }
        } else {
            down(l, r);
            for (int i = Math.max(l, jobl); i <= Math.min(r, jobr); i++) {
                if (arr[i] - lazy > jobx) {
                    cntv[arr[i]]--;
                    arr[i] -= jobx;
                    cntv[arr[i]]++;
                }
            }
            for (int v = maxv; v >= 0; v--) {
                if (cntv[v] != 0) {
                    maxv = v;
                    break;
                }
            }
        }
    }

    public static void query(int qi, int l, int r) {
        int jobl = ql[qi], jobr = qr[qi], jobx = qx[qi];
        if (jobx == 0 || jobx > maxv - lazy || jobl > r || jobr < l) {
            return;
        }
        if (jobl <= l && r <= jobr) {
            ans[qi] += cntv[jobx + lazy];
        } else {
            down(l, r);
            for (int i = Math.max(l, jobl); i <= Math.min(r, jobr); i++) {
                if (arr[i] - lazy == jobx) {
                    ans[qi]++;
                }
            }
        }
    }

    public static void compute(int l, int r) {
        Arrays.fill(cntv, 0);
        maxv = lazy = 0;
        for (int i = l; i <= r; i++) {
            maxv = Math.max(maxv, arr[i]);
            cntv[arr[i]]++;
        }
        for (int i = 0; i <= maxv; i++) {
            fa[i] = i;
        }
        for (int i = 1; i <= m; i++) {
            if (op[i] == 1) {
                update(i, l, r);
            } else {
                query(i, l, r);
            }
        }
    }

    public static void prepare() {
        blen = (int) Math.sqrt(n);
        bnum = (n + blen - 1) / blen;
        for (int i = 1; i <= n; i++) {
            pre0[i] = pre0[i - 1] + (arr[i] == 0 ? 1 : 0);
        }
        for (int i = 1; i <= m; i++) {
            if (op[i] == 2 && qx[i] == 0) {
                ans[i] = pre0[qr[i]] - pre0[ql[i] - 1];
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
            op[i] = in.nextInt();
            ql[i] = in.nextInt();
            qr[i] = in.nextInt();
            qx[i] = in.nextInt();
        }
        prepare();
        for (int i = 1, l, r; i <= bnum; i++) {
            l = (i - 1) * blen + 1;
            r = Math.min(i * blen, n);
            compute(l, r);
        }
        for (int i = 1; i <= m; i++) {
            if (op[i] == 2) {
                out.println(ans[i]);
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
