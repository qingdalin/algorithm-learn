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
 * @date: 2024/9/28 7:45
 * // 删增边使其重心唯一
 * // 一共有n个节点，编号1~n，有n-1条边形成一棵树
 * // 现在希望重心是唯一的节点，调整的方式是先删除一条边、然后增加一条边
 * // 如果树上只有一个重心，需要删掉连接重心的任意一条边，再把这条边加上(否则无法通过已经实测)
 * // 如果树上有两个重心，调整的方式是先删除一条边、然后增加一条边，使重心是唯一的
 * // 如果方案有多种，打印其中一种即可
 * // 比如先删除节点3和节点4之间的边，再增加节点4和节点7之间的边，那么打印:
 * // "3 4"
 * // "4 7"
 * // 测试链接 : https://www.luogu.com.cn/problem/CF1406C
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
public class Code04_LinkCutCentroids {
    public static int MAXN = 100001;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt;
    public static int n;
    public static int[] size = new int[MAXN];
    // 最大的子树大小
    public static int[] maxsub = new int[MAXN];
    // 重心，最多两个
    public static int[] center = new int[2];
    // 任意的一个叶子节点和父亲
    public static int anyLeaf, anyFather;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int testCase = (int) in.nval;
        for (int t = 0; t < testCase; t++) {
            in.nextToken(); n = (int) in.nval;
            build();
            for (int i = 1, u, v; i <= n - 1; i++) {
                in.nextToken(); u = (int) in.nval;
                in.nextToken(); v = (int) in.nval;
                addEdge(u, v);
                addEdge(v, u);

            }
            int m = compute();
            if (m == 1) {
                System.out.println(center[0] + " " + to[head[center[0]]]);
                System.out.println(center[0] + " " + to[head[center[0]]]);
            } else {
                System.out.println(anyFather + " " + anyLeaf);
                System.out.println(center[0] + " " + anyLeaf);
            }
        }
        out.flush();
        out.close();
        br.close();
    }

    private static int compute() {
        dfs(1, 0);
        int m =  0;
        for (int i = 1; i <= n; i++) {
            if (maxsub[i] <= (n / 2)) {
                center[m++] = i;
            }
        }
        if (m == 2) {
            find(center[1], center[0]);
        }
        return m;
    }

    private static void find(int u, int f) {
        for (int e = head[u], v; e != 0; e = next[e]) {
            v = to[e];
            if (v != f) {
                find(v, u);
                return;
            }
        }
        anyLeaf = u;
        anyFather = f;
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

    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    private static void build() {
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
    }
}
