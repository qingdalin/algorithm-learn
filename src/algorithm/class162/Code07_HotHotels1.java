package algorithm.class162;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/16 15:50
 * // 火热旅馆，java版
 * // 一共有n个节点，给定n-1条边，所有节点连成一棵树
 * // 三个点构成一个点组(a, b, c)，打乱顺序认为是同一个点组
 * // 求树上有多少点组，内部任意两个节点之间的距离是一样的
 * // 1 <= n <= 10^5
 * // 答案一定在long类型范围内
 * // 测试链接 : https://www.luogu.com.cn/problem/P5904
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code07_HotHotels1 {
    public static int MAXN = 100001;
    public static int n;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cntg = 0;

    public static int[] fa = new int[MAXN];
    public static int[] len = new int[MAXN];
    public static int[] son = new int[MAXN];
    public static int cntd = 0;
    // 每个点在动态规划表f中的开始位置，就是dfn序
    public static int[] fid = new int[MAXN];
    // 每个点在动态规划表g中的开始位置，课上讲的设计
    public static int[] gid = new int[MAXN];
    // 动态规划表f，f[父][i]依赖f[子][i-1]
    public static long[] f = new long[MAXN];
    // 动态规划表g，g[父][i]依赖g[子][i+1]
    public static long[] g = new long[MAXN << 1];
    public static long ans = 0;

    public static void addEdge(int u, int v) {
        next[++cntg] = head[u];
        to[cntg] = v;
        head[u] = cntg;
    }

    public static void setf(int u, int i, long v) {
        f[fid[u] + i] = v;
    }

    public static long getf(int u, int i) {
        return f[fid[u] + i];
    }

    public static void setg(int u, int i, long v) {
        g[gid[u] + i] = v;
    }

    public static long getg(int u, int i) {
        return g[gid[u] + i];
    }

    public static void dfs1(int u, int f) {
        fa[u] = f;
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs1(v, u);
            }
        }
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                if (son[u] == 0 || len[son[u]] < len[v]) {
                    son[u] = v;
                }
            }
        }
        len[u] = len[son[u]] + 1;
    }

    public static void dfs2(int u, int top) {
        // ++在前或者再后都可以
        fid[u] = ++cntd;
        if (son[u] == 0) {
            gid[u] = 2 * fid[top];
            return;
        }
        dfs2(son[u], top);
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u] && v != son[u]) {
                dfs2(v, v);
            }
        }
        gid[u] = gid[son[u]] + 1;
    }

    public static void dfs3(int u) {
        setf(u, 0, 1);
        if (son[u] == 0) {
            return;
        }
        dfs3(son[u]);
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u] && v != son[u]) {
                dfs3(v);
            }
        }
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u] && v != son[u]) {
                for (int i = 0; i <= len[v]; i++) {
                    // 情况2，u树上，选择三个点，u没被选中，但跨u选点
                    if (i - 1 >= 0 && i < len[u]) {
                        // 情况二的分一：之前子树选两个，当前子树选一个
                        ans += getg(u, i) * getf(v, i - 1);
                    }
                    // 情况二的分一：之前子树选1个，当前子树选2个
                    if (i + 1 < len[v] && i > 0) {
                        ans += getf(u, i) * getg(v, i + 1);
                    }
                }
                for (int i = 0; i <= len[v]; i++) {
                    if (i + 1 < len[v]) {
                        // 情况二的分一：等距只来自v
                        setg(u, i, getg(u, i) + getg(v, i + 1));
                    }
                    if (i - 1 >= 0) {
                        // 等距来自两侧
                        setg(u, i, getg(u, i) + getf(u, i) * getf(v, i - 1));
                    }
                }
                for (int i = 0; i <= len[v]; i++) {
                    // 错误写法， 判断不能缺失
                    if (i - 1 >= 0) {
                        setf(u, i, getf(u, i) + getf(v, i - 1));
                    }
                }
            }
        }
        // 情况1，u树上，选择三个点，u被选中
        ans += getg(u, 0);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 1, u, v; i < n; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);
        }
         dfs1(1, 0);
         dfs2(1, 1);
        dfs3(1);
//         dfs2(1, 0);
//        dfs4();
        out.println(ans);
        out.flush();
        out.close();
        br.close();
    }
}
