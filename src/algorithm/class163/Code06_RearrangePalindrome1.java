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
 * @date: 2025/3/29 19:59
 * // 最长重排回文路径，java版
 * // 一共有n个节点，编号1~n，给定n-1条边，所有节点连成一棵树，1号节点为树头
 * // 每条边上都有一个字符，字符范围[a~v]，字符一共22种，重排回文路径的定义如下
 * // 节点a到节点b的路径，如果所有边的字符收集起来，能重新排列成回文串，该路径是重排回文路径
 * // 打印1~n每个节点为头的子树中，最长重排回文路径的长度
 * // 1 <= n <= 5 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/CF741D
 * // 测试链接 : https://codeforces.com/problemset/problem/741/D
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code06_RearrangePalindrome1 {
    public static int MAXN = 500001;
    public static int MAXV = 22;
    public static int n;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN];
    public static int[] to = new int[MAXN];
    public static int[] weight = new int[MAXN];
    public static int cnt = 0;
    public static int[] siz = new int[MAXN];
    public static int[] son = new int[MAXN];
    public static int[] dep = new int[MAXN];
    public static int[] eor = new int[MAXN];

    public static int[] maxdep = new int[1 << MAXV];
    public static int[] ans = new int[MAXN];

    public static void addEdge(int u, int v, int w) {
        next[++cnt] = head[u];
        to[cnt] = v;
        weight[cnt] = w;
        head[u] = cnt;
    }

    public static void dfs1(int u, int d, int x) {
        siz[u] = 1;
        dep[u] = d;
        eor[u] = x;
        for(int e = head[u], v; e > 0; e = next[e]) {
            dfs1(to[e], d + 1, x ^ (1 << weight[e]));
        }
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            siz[u] += siz[v];
            if (son[u] == 0 || siz[son[u]] < siz[v]) {
                son[u] = v;
            }
        }
    }

    public static void effect(int u) {
        maxdep[eor[u]] = Math.max(maxdep[eor[u]], dep[u]);
        for(int e = head[u]; e > 0; e = next[e]) {
            effect(to[e]);
        }
    }

    public static void cancel(int u) {
        maxdep[eor[u]] = 0;
        for(int e = head[u]; e > 0; e = next[e]) {
            cancel(to[e]);
        }
    }

    public static void ansFromLight(int light, int u) {
        if (maxdep[eor[light]] != 0) {
            ans[u] = Math.max(ans[u], maxdep[eor[light]] + dep[light] - 2 * dep[u]);
        }
        for (int i = 0; i < MAXV; i++) {
            if (maxdep[eor[light] ^ (1 << i)] != 0) {
                ans[u] = Math.max(ans[u], maxdep[eor[light] ^ (1 << i)] + dep[light] - 2 * dep[u]);
            }
        }
        // 错误写法 for(int e = head[u]; e > 0; e = next[e]) {
        for(int e = head[light]; e > 0; e = next[e]) {
            ansFromLight(to[e], u);
        }
    }

    public static void dfs2(int u, int keep) {
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (son[u] != v) {
                dfs2(v, 0);
            }
        }
        if (son[u] != 0) {
            dfs2(son[u], 1);
        }
        // 每一个儿子的子树，里得到的答案
        for(int e = head[u]; e > 0; e = next[e]) {
            ans[u] = Math.max(ans[u], ans[to[e]]);
        }
        // 选择当前节点，再选择重儿子树上的任意一点，得到的答案
        // 枚举所有可能得到的异或值
        if (maxdep[eor[u]] != 0) {
            ans[u] = Math.max(ans[u], maxdep[eor[u]] - dep[u]);
        }
        for (int i = 0; i < MAXV; i++) {
            if (maxdep[eor[u] ^ (1 << i)] != 0) {
                // ans[u] = Math.max(ans[u], maxdep[eor[u] ^ (1 << i)] + dep[u] - 2 * dep[u]);
                ans[u] = Math.max(ans[u], maxdep[eor[u] ^ (1 << i)] - dep[u]);
            }
        }
        // 当前点的异或值，更新最大深度信息
        maxdep[eor[u]] = Math.max(maxdep[eor[u]], dep[u]);
        // 选择遍历过的部分里的任意一点，再选择当前遍历到的子树里的任意一点，得到的答案
        for(int e = head[u], v; e > 0; e = next[e]) {
            v = to[e];
            if (v != son[u]) {
                ansFromLight(v, u);
                effect(v);
            }
        }
        if (keep == 0) {
            cancel(u);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 2, fth, edg; i <= n; i++) {
//            String[] s = br.readLine().split(" ");
//            fth = Integer.parseInt(s[0]);
//            edg = s[1].charAt(0) - 'a';
            in.nextToken(); fth = (int) in.nval;
            in.nextToken(); edg = in.sval.charAt(0) - 'a';
            addEdge(fth, i, edg);
        }
        dfs1(1, 1, 0);
        dfs2(1, 0);
        for (int i = 1; i <= n; i++) {
            out.print(ans[i] + " ");
        }
        out.flush();
        out.close();
        br.close();
    }
}
