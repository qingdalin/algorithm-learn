package algorithm.class173;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/6 8:27
 * // 王室联邦，java版
 * // 一共有n个城市，编号1~n，给定n-1条边，所有城市连成一棵树
 * // 给定数值b，请把树划分成若干个省，划分要求如下
 * // 每个省至少要有b个城市，最多有3 * b个城市，每个省必须有一个省会
 * // 省会可在省内也可在省外，一个城市可以是多个省的省会
 * // 一个省里，任何城市到达省会的路径上，除了省会之外的其他城市，必须都在省内
 * // 根据要求完成一种有效划分即可，先打印划分了多少个省，假设数量为k
 * // 然后打印n个数字，范围[1, k]，表示每个城市被划分给了哪个省
 * // 最后打印k个数字，表示每个省会的城市编号
 * // 1 <= n、b <= 10^3
 * // 测试链接 : https://www.luogu.com.cn/problem/P2325
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code07_Royal1 {
    public static int MAXN = 1001;
    public static int n, b;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg = 0;

    public static int[] capital = new int[MAXN];
    public static int[] belong = new int[MAXN];
    public static int cntb = 0;
    public static int[] stack = new int[MAXN];
    public static int siz = 0;

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void dfs(int u, int f) {
        int x = siz;
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs(v, u);
                if (siz - x >= b) {
                    capital[++cntb] = u;
                    while (siz != x) {
                        belong[stack[siz--]] = cntb;
                    }
                }
            }
        }
        stack[++siz] = u;
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        b = in.nextInt();
        for (int i = 1, u, v; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            addEdge(u, v);
            addEdge(v, u);
        }
        dfs(1, 0);
        if (cntb == 0) {
            capital[++cntb] = 1;
        }
        while (siz > 0) {
            belong[stack[siz--]] = cntb;
        }
        out.println(cntb);
        for (int i = 1; i <= n; i++) {
            out.print(belong[i] + " ");
        }
        out.println();
        for (int i = 1; i <= cntb ; i++) {
            out.print(capital[i] + " ");
        }
        out.println();
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
