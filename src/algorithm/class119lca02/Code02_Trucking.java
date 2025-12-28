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
 * @date: 2024/9/22 9:55
 * https://www.luogu.com.cn/problem/P1967
 */
public class Code02_Trucking {
    public static int MAXN = 10001;
    public static int MAXM = 50001;
    public static int LIMIT = 15;
    public static int cnt, power;

    public static int[][] edge = new int[MAXM][3];
    public static int[] father = new int[MAXN];
    public static boolean[] visited = new boolean[MAXN];

    // 最大生成树
    public static int[] head = new int[MAXM];
    public static int[] next = new int[MAXM << 1];
    public static int[] to = new int[MAXM << 1];
    public static int[] wight = new int[MAXM << 1];

    public static int[] deep = new int[MAXN];

    public static int[][] stjump = new int[MAXN][LIMIT];
    public static int[][] stmin = new int[MAXN][LIMIT];

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
        for (int i = 1; i <= n; i++) {
            father[i] = i;
        }
        Arrays.fill(visited, 1, n + 1, false);
        Arrays.fill(head, 1, n + 1, 0);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int n = (int) in.nval;
        in.nextToken(); int m = (int) in.nval;
        for (int i = 1, u, v, w; i <= m; i++) {
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            in.nextToken(); w = (int) in.nval;
            edge[i][0] = u;
            edge[i][1] = v;
            edge[i][2] = w;
        }
        build(n);
        kruskral(m);
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                dfs(i, 0, 0);
            }
        }
        in.nextToken();
        int q = (int) in.nval;
        for (int i = 0, a, b; i < q; i++) {
            in.nextToken(); a = (int) in.nval;
            in.nextToken(); b = (int) in.nval;
            System.out.println(lca(a, b));
        }
        out.println();
        out.flush();
        out.close();
        br.close();
    }

    private static int lca(int a, int b) {
        if (find(a) != find(b)) {
            return -1;
        }
        if (deep[a] < deep[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        int ans = Integer.MAX_VALUE;
        for (int p = power; p >= 0; p--) {
            if (deep[stjump[a][p]] >= deep[b]) {
                ans = Math.min(ans, stmin[a][p]);
                a = stjump[a][p];
            }
        }
        if (a == b) {
            return ans;
        }
        for (int p = power; p >= 0; p--) {
            if (stjump[a][p] != stjump[b][p]) {
                ans = Math.min(ans, Math.min(stmin[a][p], stmin[b][p]));
                a = stjump[a][p];
                b = stjump[b][p];
            }
        }
        ans = Math.min(ans, Math.min(stmin[a][0], stmin[b][0]));
        return ans;
    }

    private static void dfs(int u, int f, int w) {
        visited[u] = true;
        if (f == 0) {
            deep[u] = 1;
            stjump[u][0] = u;
            stmin[u][0] = Integer.MAX_VALUE;
        } else {
            deep[u] = deep[f] + 1;
            stjump[u][0] = f;
            stmin[u][0] = w;
        }
        for (int p = 1; p <= power; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
            stmin[u][p] = Math.min(stmin[u][p - 1], stmin[stjump[u][p - 1]][p - 1]);
        }
        for (int e = head[u]; e != 0; e = next[e]) {
            if (!visited[to[e]]) {
                dfs(to[e], u, wight[e]);
            }
        }
    }

    private static void kruskral(int m) {
        Arrays.sort(edge, 1, m + 1, (a, b) -> b[2] - a[2]);
        for (int i = 1, a, b; i <= m; i++) {
            a = edge[i][0];
            b = edge[i][1];
            int fa = find(a);
            int fb = find(b);
            if (fa != fb) {
                father[fa] = fb;
                addEdge(a, b, edge[i][2]);
                addEdge(b, a, edge[i][2]);
            }
        }
    }

    private static void addEdge(int u, int v, int w) {
        next[cnt] = head[u];
        to[cnt] = v;
        wight[cnt] = w;
        head[u] = cnt++;
    }

    private static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }
}
