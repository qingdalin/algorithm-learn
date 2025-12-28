package algorithm.class119lca02;

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
 * @date: 2024/9/22 8:02
 * https://www.luogu.com.cn/problem/P4281
 */
public class Code01_EmergencyAssembly1 {
    public static int MAXN = 500001;
    public static int LIMIT = 19;

    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt, power;

    public static int[] deep = new int[MAXN];

    public static int[][] stjump = new int[MAXN][LIMIT];

    public static long cost;
    public static int togather;

    public static int log2(int n) {
        int ans = 0;
        while ((1 << ans) <= (n >> 1)) {
            ans++;
        }
        return ans;
    }

    public static void build(int n) {
        power = log2(n);
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
    }

    public static void add(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    public static void dfs(int u, int f) {
        deep[u] = deep[f] + 1;
        stjump[u][0] = f;
        for (int p = 1; p <= power; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
        }
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                dfs(to[e], u);
            }
        }
    }

    public static int lca(int a, int b) {
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
            return a;
        }
        for (int p = power; p >= 0; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        return stjump[a][0];
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int n = (int) in.nval;
        in.nextToken(); int m = (int) in.nval;
        build(n);
        for (int i = 1, u, v; i <= n - 1; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            add(u, v);
            add(v, u);
        }
        dfs(1, 0);
        for (int i = 0, a, b, c; i < m; i++) {
            in.nextToken(); a = (int) in.nval;
            in.nextToken(); b = (int) in.nval;
            in.nextToken(); c = (int) in.nval;
            compute(a, b, c);
            System.out.println(togather + " " + cost);
        }
        out.println();
        out.flush();
        out.close();
        br.close();
    }

    private static void compute(int a, int b, int c) {
        int h1 = lca(a, b), h2 = lca(a, c), h3 = lca(b, c);
        int high = h1 != h2 ? (deep[h1] < deep[h2] ? h1 : h2) : h1;
        int low = h1 != h2 ? (deep[h1] > deep[h2] ? h1 : h2) : h3;
        cost = (long)deep[a] + deep[b] + deep[c] - 2 * deep[high] - deep[low];
        togather = low;
    }
}
