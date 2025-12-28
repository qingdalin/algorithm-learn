package algorithm.class122;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/11 19:59
 * // 树上点差分模版(迭代版)
 * // 有n个节点形成一棵树，一开始所有点权都是0
 * // 给定很多操作，每个操作(a,b)表示从a到b路径上所有点的点权增加1
 * // 所有操作完成后，返回树上的最大点权
 * // 测试链接 : https://www.luogu.com.cn/problem/P3128
 * // 提交以下的code，提交时请把类名改成"Main"
 * // C++这么写能通过，java会因为递归层数太多而爆栈
 * // java能通过的写法参考本节课Code01_MaxFlow2文件
 */
public class Code01_MaxFlow2 {
    public static int MAXN = 50001;
    public static int LIMIT = 16;
    public static int power;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt;
    public static int n, k;
    // 必须从u节点开始，到下方节点距离的最大长度
    public static int[] deep = new int[MAXN];
    public static int[][] stjump = new int[MAXN][LIMIT];
    public static int[] num = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        build();
        for (int i = 1, u, v; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
        dfs1(1);
        for (int i = 0, u, v, lca, lcaFather; i < k; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            lca = lca(u, v);
            lcaFather = stjump[lca][0];
            num[u]++;
            num[v]++;
            num[lca]--;
            num[lcaFather]--;
        }
        dfs2(1);
        int max = 0;
        for (int i = 1; i <= n; i++) {
            max = Math.max(max, num[i]);
        }
        System.out.println(max);
        out.flush();
        out.close();
        br.close();
    }

    public static int[][] ufe = new int[MAXN][3];
    public static int u, f, e, stackSize;

    public static void push(int u, int f, int e) {
        ufe[stackSize][0] = u;
        ufe[stackSize][1] = f;
        ufe[stackSize][2] = e;
        stackSize++;
    }

    public static void pop() {
        stackSize--;
        u = ufe[stackSize][0];
        f = ufe[stackSize][1];
        e = ufe[stackSize][2];
    }

    private static void dfs2(int root) {
        stackSize = 0;
        push(root, 0, -1);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (to[e] != f) {
                    push(to[e], u, -1);
                }
            } else {
                for (int e = head[u], v; e != 0; e = next[e]) {
                    v = to[e];
                    if (v != f) {
                        num[u] += num[v];
                    }
                }
            }
        }
    }

    private static int lca(int a, int b) {
        if (deep[a] < deep[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        for (int p = power; p >= 0; p--) {
            if (deep[stjump[a][p]] >= deep[b]) {
                a = stjump[a][p];
            }
        }
        if (a == b) {
            return b;
        }
        for (int p = power; p >= 0; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        return stjump[a][0];
    }

    private static void dfs1(int root) {
        stackSize = 0;
        push(root, 0, - 1);
        while (stackSize > 0) {
            pop();
            if (e == -1) {
                deep[u] = deep[f] + 1;
                stjump[u][0] = f;
                for (int p = 1; p <= power; p++) {
                    stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
                }
                e = head[u];
            } else {
                e = next[e];
            }
            if (e != 0) {
                push(u, f, e);
                if (to[e] != f) {
                    push(to[e], u, -1);
                }
            }
        }
    }

    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    private static void build() {
        cnt = 1;
        power = log2();
        Arrays.fill(head, 1, n + 1, 0);
        Arrays.fill(num, 1, n + 1, 0);
    }

    private static int log2() {
        int ans = 0;
        while ((1 << ans) <= (n >> 1)) {
            ans++;
        }
        return ans;
    }
}
