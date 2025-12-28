package algorithm.class123;

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
 * @date: 2024/10/16 20:24
 * // 翻转道路数量最少的首都
 * // 给定一棵n个点的树，但是给定的每条边都是有向的
 * // 需要选择某个城市为首都，要求首都一定可以去往任何一个城市
 * // 这样一来，可能需要翻转一些边的方向才能做到，现在想翻转道路的数量尽量少
 * // 打印最少翻转几条道路就可以拥有首都
 * // 如果有若干点做首都时，翻转道路的数量都是最少的，那么打印这些点
 * // 测试链接 : https://www.luogu.com.cn/problem/CF219D
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
// TODO 链接不可用
public class Code03_ChooseCapital {
    public static int MAXN = 200001;
    public static int n;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int[] weight = new int[MAXN << 1];
    public static int cnt;
    public static int[] reverse = new int[MAXN];
    public static int[] dp = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        build();
        for (int i = 1, u, v; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            addEdge(u, v, 0);
            addEdge(v, u, 1);
        }
        dfs1(1, 0);
        dp[1] = reverse[1];
        dfs2(1, 0);
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (min > dp[i]) {
                min = dp[i];
            }
        }
        System.out.println(min);
        for (int i = 1; i <= n; i++) {
            if (min == dp[i]) {
                System.out.print(i + " ");
            }
        }
        out.flush();
        out.close();
        br.close();
    }

    private static void dfs2(int u, int f) {
        for (int e = head[u], v, w; e != 0; e = next[e]) {
            v = to[e];
            w = weight[e];
            if (v != f) {
                if (w == 0) {
                    // u -> v
                    dp[v] = dp[u] + 1;
                } else {
                    dp[v] = dp[u] - 1;
                }
                dfs2(v, u);
            }
        }
    }

    private static void dfs1(int u, int f) {
        for (int e = head[u], v, w; e != 0; e = next[e]) {
            v = to[e];
            w = weight[e];
            if (v != f) {
                dfs1(v, u);
                reverse[u] += reverse[v] + w;
            }
        }
    }

    private static void addEdge(int u, int v, int w) {
        next[cnt] = head[u];
        to[cnt] = v;
        weight[cnt] = w;
        head[u] = cnt++;
    }

    private static void build() {
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
    }
}
