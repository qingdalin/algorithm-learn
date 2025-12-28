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
 * @date: 2024/9/24 20:30
 * // 检查树上两节点间的路径是否是回文
 * // 一颗树上有n个节点，编号1~n
 * // 给定长度为n的数组parent, parent[i]表示节点i的父节点编号
 * // 给定长度为n的数组s, s[i]表示节点i上是什么字符
 * // 从节点a到节点b经过节点最少的路，叫做a和b的路径
 * // 一共有m条查询，每条查询(a,b)，a和b的路径字符串是否是回文
 * // 是回文打印"YES"，不是回文打印"NO"
 * // 1 <= n <= 10^5
 * // 1 <= m <= 10^5
 * // parent[1] = 0，即整棵树的头节点一定是1号节点
 * // 每个节点上的字符一定是小写字母a~z
 */
public class Code05_PathPalindrome {
    public static int MAXN = 100001;
    public static int LIMIT = 17;
    public static int power;

    // 将字符串转为数字
    // a -> 1
    // b -> 2
    // z -> 26
    public static int[] s = new int[MAXN];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt;

    public static int[] deep = new int[MAXN];
    public static int[][] jump = new int[MAXN][LIMIT];

    public static long k = 499;
    public static long[] kpower = new long[MAXN];
    public static long[][] stup = new long[MAXN][LIMIT];
    public static long[][] stdown = new long[MAXN][LIMIT];

    public static void build(int n) {
        power = log2(n);
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
        kpower[0] = 1;
        for (int i = 1; i <= n; i++) {
            kpower[i] = kpower[i - 1] * k;
        }
    }

    private static int log2(int n) {
        int ans = 0;
        while ((1 << ans) <= (n >> 1)) {
            ans++;
        }
        return ans;
    }

    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    private static void dfs(int u, int f) {
        deep[u] = deep[f] + 1;
        jump[u][0] = f;
        stup[u][0] = stdown[u][0] = s[f];
        for (int p = 1, v; p <= power; p++) {
            v = jump[u][p - 1];
            jump[u][p] = jump[v][p - 1];
            stup[u][p] = stup[u][p - 1] * kpower[1 << (p - 1)] + stup[v][p - 1];
            stdown[u][p] = stdown[v][p - 1] * kpower[1 << (p - 1)] + stdown[u][p - 1];
        }
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                dfs(to[e], u);
            }
        }
    }
    public static boolean isPalindrome(int a, int b) {
        int lca = lca(a, b);
        long hash1 = hash(a, lca, b);
        long hash2 = hash(b, lca, a);
        return hash1 == hash2;
    }

    private static long hash(int from, int lca, int to) {
        long up = s[from];
        for (int p = power; p >= 0; p--) {
            if (deep[jump[from][p]] >= deep[lca]) {
                up = up * kpower[1 << p] + stup[from][p];
                from = jump[from][p];
            }
        }
        if (from == to) {
            return up;
        }
        long down = s[to];
        int h = 1;
        for (int p = power; p >= 0; p--) {
            if (deep[jump[to][p]] > deep[lca]) {
                down = stdown[to][p] * kpower[h] + down;
                to = jump[to][p];
                h += 1 << p;
            }
        }
        return up * kpower[h] + down;
    }

    private static int lca(int a, int b) {
        if (deep[a] < deep[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        for (int p = power; p >= 0; p--) {
            if (deep[jump[a][p]] >= deep[b]) {
                a = jump[a][p];
            }
        }
        if (a == b) {
            return a;
        }
        for (int p = power; p >= 0; p--) {
            if (jump[a][p] != jump[b][p]) {
                a = jump[a][p];
                b = jump[b][p];
            }
        }
        return jump[a][0];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int n = (int) in.nval;
        build(n);
        String str = br.readLine();
        int si = 1;
        for (char c : str.toCharArray()) {
            s[si++] = c - 'a' + 1;
        }
        for (int i = 1, parent; i <= n; i++) {
            in.nextToken();
            parent = (int) in.nval;
            addEdge(parent, i);
            addEdge(i, parent);
        }
        dfs(1, 0);
        in.nextToken(); int q = (int) in.nval;
        for (int i = 0, a, b; i < q; i++) {
            in.nextToken(); a = (int) in.nval;
            in.nextToken(); b = (int) in.nval;
            System.out.println(isPalindrome(a, b) ? "YES" : "NO");
        }
        out.println();
        out.flush();
        out.close();
        br.close();
    }


}
