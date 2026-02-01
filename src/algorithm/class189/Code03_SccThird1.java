package algorithm.class189;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/31 16:54
 * // 强连通分量模版题3，java版
 * // 一共有n个节点，给定m条边，边的格式 a b t
 * // 如果t为1，表示a到b的单向边，如果t为2，表示a到b的双向边
 * // 找到图中最大的强连通分量，先打印大小，然后打印包含的节点，编号从小到大输出
 * // 如果有多个最大的强连通分量，打印字典序最小的结果
 * // 1 <= n <= 5 * 10^3
 * // 0 <= m <= 5 * 10^4
 * // 测试链接 : https://www.luogu.com.cn/problem/P1726
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_SccThird1 {
    public static int MAXN = 5001;
    public static int MAXM = 50001;
    public static int n, m;

    public static int[] head = new int[MAXN];
    public static int[] nxt = new int[MAXM];
    public static int[] to = new int[MAXM];
    public static int cntg;

    public static int[] sta = new int[MAXN];
    public static int top;

    public static int[] dfn = new int[MAXN];
    public static int[] low = new int[MAXN];
    public static int cntd;

    public static int[] belong = new int[MAXN];
    public static int[] sccSize = new int[MAXN];
    public static int sccCnt;

    public static int[][] stack = new int[MAXN][3];
    public static int u, status, e, stacksize;

    public static void push(int u, int status, int e) {
        stack[stacksize][0] = u;
        stack[stacksize][1] = status;
        stack[stacksize][2] = e;
        stacksize++;
    }

    public static void pop() {
        stacksize--;
        u = stack[stacksize][0];
        status = stack[stacksize][1];
        e = stack[stacksize][2];
    }

    public static void addEdge(int u, int v) {
        nxt[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void tarjan1(int u) {
        dfn[u] = low[u] = ++cntd;
        sta[++top] = u;
        for (int e = head[u]; e > 0; e = nxt[e]) {
            int v = to[e];
            if (dfn[v] == 0) {
                tarjan1(v);
                low[u] = Math.min(low[u], low[v]);
            } else {
                if (belong[v] == 0) {
                    low[u] = Math.min(low[u], dfn[v]);
                }
            }
        }
        if (dfn[u] == low[u]) {
            sccCnt++;
            int pop;
            do {
                pop = sta[top--];
                belong[pop] = sccCnt;
                sccSize[sccCnt]++;
            } while (pop != u);
        }
    }
    // 迭代版
    // u表示当前节点
    // e表示u当前处理的边，如果e == 0，说明所有边都处理完了
    // status的具体说明如下
    //     如果status == -1，表示u没有遍历过任何儿子
    //     如果status == 0，表示u遍历到儿子v，然后发现dfn[v] == 0
    //         并且执行完了tarjan(v)，对应递归版for循环中的第一个分支
    //     如果status == 1，表示u遍历到儿子v，然后发现dfn[v] != 0
    //         对应递归版for循环中的第二个分支
    public static void tarjan2(int node) {
        stacksize = 0;
        push(node, -1, -1);
        int v;
        while (stacksize > 0) {
            pop();
            if (status == -1) {
                dfn[u] = low[u] = ++cntd;
                sta[++top] = u;
                e = head[u];
            } else {
                v = to[e];
                if (status == 0) {
                    low[u] = Math.min(low[u], low[v]);
                }
                if (status == 1 && belong[v] == 0) {
                    low[u] = Math.min(low[u], dfn[v]);
                }
                e = nxt[e];
            }
            if (e != 0) {
                v = to[e];
                if (dfn[v] == 0) {
                    push(u, 0, e);
                    push(v, -1, -1);
                } else {
                    push(u, 1, e);
                }
            } else {
                if (dfn[u] == low[u]) {
                    sccCnt++;
                    int pop;
                    do {
                        pop = sta[top--];
                        belong[pop] = sccCnt;
                        sccSize[sccCnt]++;
                    } while (pop != u);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1, u, v, t; i <= m; i++) {
            u = in.nextInt();
            v = in.nextInt();
            t = in.nextInt();
            if (t == 1) {
                addEdge(u, v);
            } else {
                addEdge(u, v);
                addEdge(v, u);
            }
        }
        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
//                 tarjan1(i);
                tarjan2(i);
            }
        }
        int largest = 0;
        for (int i = 1; i <= sccCnt; i++) {
            largest = Math.max(largest, sccSize[i]);
        }
        out.println(largest);
        for (int i = 1; i <= n; i++) {
            if (sccSize[belong[i]] == largest) {
                int scc = belong[i];
                for (int j = i; j <= n; j++) {
                    if (belong[j] == scc) {
                        out.print(j + " ");
                    }
                }
                break;
            }
        }
        out.flush();
        out.close();
    }

    // 读写工具类
    static class FastReader {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastReader(InputStream in) {
            this.in = in;
        }

        private int readByte() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0)
                    return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = readByte();
            } while (c <= ' ' && c != -1);
            boolean neg = false;
            if (c == '-') {
                neg = true;
                c = readByte();
            }
            int val = 0;
            while (c > ' ' && c != -1) {
                val = val * 10 + (c - '0');
                c = readByte();
            }
            return neg ? -val : val;
        }
    }
}
