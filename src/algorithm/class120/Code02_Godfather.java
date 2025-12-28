package algorithm.class120;

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
 * @date: 2024/9/27 20:21
 * // 教父
 * // 一共有n个节点，编号1~n，有n-1条边形成一棵树
 * // 返回所有重心点
 * // 树的重心第二种求解方式
 * // 以某个节点为根时，每颗子树的节点数不超过总节点数的一半，那么这个节点是重心
 * // 测试链接 : http://poj.org/problem?id=3107
 * // 提交以下的code，提交时请把类名改成"Main"，可以直接通过
 */
public class Code02_Godfather {
    public static int MAXN = 50001;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt;
    public static int n;
    public static int[] size = new int[MAXN];
    public static int[] maxsub = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        build();
        for (int i = 1, u, v; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v);
            addEdge(v, u);

        }
        dfs(1, 0);
        int m = 0;
        int[] center = new int[2];
        for (int i = 1; i <= n; i++) {
            if (maxsub[i] <= n / 2) {
                center[m++] = i;
            }
        }
        if (m == 1) {
            System.out.println(center[0]);
        } else {
            System.out.println(center[0] + " " + center[1]);
        }
        out.flush();
        out.close();
        br.close();
    }

    private static void dfs(int u, int f) {
        size[u] = 1;
        maxsub[u] = 0;
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                dfs(v, u);
                size[u] += size[v];
                maxsub[u] = Math.max(maxsub[u], size[v]);
            }
        }
        maxsub[u] = Math.max(maxsub[u], n - size[u]);
    }

    private static void build() {
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
    }

    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }
}
