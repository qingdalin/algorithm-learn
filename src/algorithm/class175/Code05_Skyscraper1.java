package algorithm.class175;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/10 9:37
 * // 雅加达的摩天楼，java版
 * // 有n个大楼，编号0~n-1，有m个狗子，编号0~m-1
 * // 每只狗子有两个参数，idx表示狗子的初始大楼，jump表示狗子的跳跃能力
 * // 狗子在i位置，可以来到 i - jump 或 i + jump，向左向右自由跳跃，但不能越界
 * // 0号狗子有消息希望传给1号狗子，所有狗子都可帮忙，返回至少传送几次，无法送达打印-1
 * // 1 <= n、m <= 30000
 * // 测试链接 : https://www.luogu.com.cn/problem/P3645
 * // 测试链接 : https://uoj.ac/problem/111
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_Skyscraper1 {
    static class Node{
        int idx, jump, time;

        public Node(int idx, int jump, int time) {
            this.idx = idx;
            this.jump = jump;
            this.time = time;
        }
    }

    public static int MAXN = 30001;
    public static int n, m;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN];
    public static int[] to = new int[MAXN];
    public static int cnt = 0;
    public static Deque<Node> que = new ArrayDeque<>();
    public static BitSet[] vis = new BitSet[MAXN];

    public static void add(int idx, int jump) {
        next[++cnt] = head[idx];
        to[cnt] = jump;
        head[idx] = cnt;
    }

    public static void trigger(int idx, int time) {
        for(int e = head[idx], jump; e > 0; e = next[e]) {
            jump = to[e];
            if (!vis[idx].get(jump)) {
                vis[idx].set(jump);
                que.addLast(new Node(idx, jump, time));
            }
        }
        head[idx] = 0;
    }

    public static void extend(int idx, int jump, int time) {
        trigger(idx, time);
        if (!vis[idx].get(jump)) {
            vis[idx].set(jump);
            que.addLast(new Node(idx, jump, time));
        }
    }

    public static int bfs(int s, int t) {
        if (s == t) {
            return 0;
        }
        for (int i = 0; i < MAXN; i++) {
            vis[i] = new BitSet();
        }
        trigger(s, 0);
        while (!que.isEmpty()) {
            Node cur = que.pollFirst();
            int idx = cur.idx;
            int jump = cur.jump;
            int time = cur.time;
            if (idx + jump == t || idx - jump == t) {
                return time + 1;
            }
            if (idx - jump >= 0) {
                extend(idx - jump, jump, time + 1);
            }
            if (idx + jump < n) {
                extend(idx + jump, jump, time + 1);
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        int s = in.nextInt();
        int sjump = in.nextInt();
        int t = in.nextInt();
        int tjump = in.nextInt();
        add(s, sjump);
        add(t, tjump);
        for (int i = 2, idx, jump; i < m; i++) {
            idx = in.nextInt();
            jump = in.nextInt();
            add(idx, jump);
        }
        out.println(bfs(s, t));
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
