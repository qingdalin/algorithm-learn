package algorithm.class163;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/24 20:22
 * // 树上启发式合并模版题，java版
 * // 一共有n个节点，编号1~n，给定n-1条边，所有节点连成一棵树，1号节点为树头
 * // 每个节点给定一种颜色值，一共有m条查询，每条查询给定参数x
 * // 每条查询打印x为头的子树上，一共有多少种不同的颜色
 * // 1 <= n、m、颜色值 <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/U41492
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_DsuOnTree2 {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt = 0;

    public static int[] fa = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[] son = new int[MAXN];

    public static int[] colorCnt = new int[MAXN];
    public static int[] ans = new int[MAXN];
    public static int diffColor = 0;

    public static void addEdge(int u, int v) {
        next[++cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt;
    }

    public static void dfs1(int u, int f) {
        fa[u] = f;
        siz[u] = 1;
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs1(v, u);
            }
        }
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                siz[u] += siz[v];
                if (son[u] == 0 || siz[son[u]] < siz[v]) {
                    son[u] = v;
                }
            }
        }
    }

    public static void effect(int u) {
        // 错误写法 if (++colorCnt[u] == 1) {
        if (++colorCnt[arr[u]] == 1) {
            diffColor++;
        }
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u]) {
                effect(v);
            }
        }
    }

    public static void cancel(int u) {
        // 出现任何颜色直接置为0
        colorCnt[arr[u]] = 0;
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u]) {
                cancel(v);
            }
        }
    }

    public static void dfs2(int u, int keep) {
        // 遍历轻儿子的子树，统计子树的答案，然后取消贡献
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u] && v != son[u]) {
                dfs2(v, 0);
            }
        }
        // 遍历重儿子的子树，统计子树的答案，然后保留贡献
        if (son[u] != 0) {
            dfs2(son[u], 1);
        }
        // 当前节点贡献信息
        if (++colorCnt[arr[u]] == 1) {
            diffColor++;
        }
        // 遍历轻儿子的子树，重新贡献一遍
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != fa[u] && v != son[u]) {
                effect(v);
            }
        }
        // 记录子树u的答案
        ans[u] = diffColor;
        if (keep == 0) {
            // 直接把全局的不同颜色数量重置为0
            diffColor = 0;
            cancel(u);
        }
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
        for (int i = 1; i <= n; i++) {
            in.nextToken(); arr[i] = (int) in.nval;
        }
        dfs1(1, 0);
        dfs2(1, 0);
        in.nextToken(); m = (int) in.nval;
        for (int i = 1, cur; i <= m; i++) {
            in.nextToken(); cur = (int) in.nval;
            out.println(ans[cur]);
        }
        out.flush();
        out.close();
        br.close();
    }
}
