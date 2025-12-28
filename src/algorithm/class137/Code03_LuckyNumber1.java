package algorithm.class137;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/6 20:38
 * // 幸运数字(递归版)
 * // 一共有n个点，编号1~n，由n-1条边连成一棵树，每个点上有数字
 * // 一共有q条查询，每次返回a到b的路径上，可以随意选择数字，能得到的最大异或和
 * // 1 <= n <= 2 * 10^4
 * // 1 <= q <= 2 * 10^5
 * // 0 <= 点上的数字 <= 2^60
 * // 测试链接 : https://www.luogu.com.cn/problem/P3292
 * // 提交以下的code，提交时请把类名改成"Main"
 * // C++这么写能通过，java会因为递归层数太多而爆栈
 * // java能通过的写法参考本节课Code03_LuckyNumber2文件
 */
public class Code03_LuckyNumber1 {
    public static int MAXN = 20002;
    public static int BIT = 60;
    public static int LIMIT = 16;

    public static long[] arr = new long[MAXN];

    // 链式前向星
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN << 1];
    public static int[] to = new int[MAXN << 1];
    public static int cnt;
    // 树上倍增
    public static int[] deep = new int[MAXN];
    public static int[][] stjump = new int[MAXN][LIMIT];
    public static int power;
    // bases[i][j]表示：
    // 头节点到i节点路径上的数字，建立异或空间线性基，其中j位的线性基是哪个数字
    public static long[][] bases = new long[MAXN][BIT + 1];
    public static long[] basis = new long[BIT + 1];
    // levels[i][j]表示：
    // 头节点到i节点路径上的数字，建立异或空间线性基，其中j位的线性基来自哪一层
    public static int[][] levels = new int[MAXN][BIT + 1];
    public static int n, q;
    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio();
        n = io.nextInt();
        q = io.nextInt();
        prepare();
        for (int i = 1; i <= n; i++) {
            arr[i] = io.nextLong();
        }
        for (int i = 1; i <= n - 1; i++) {
            int u = io.nextInt();
            int v = io.nextInt();
            addEdge(u, v);
            addEdge(v, u);
        }
        dfs(1, 0);
        for (int i = 0; i < q; i++) {
            int x = io.nextInt();
            int y = io.nextInt();
            io.println(query(x, y));
        }
        io.flush();
        io.close();
    }

    private static long query(int x, int y) {
        int lca = lca(x, y);
        long[] basisx = bases[x];
        int[] levelx = levels[x];
        long[] basisy = bases[y];
        int[] levely = levels[y];
        Arrays.fill(basis, 0);
        for (int i = BIT; i >= 0; i--) {
            long num = basisx[i];
            if (levelx[i] >= deep[lca] && num != 0) {
                basis[i] = num;
            }
        }
        for (int i = BIT; i >= 0; i--) {
            long num = basisy[i];
            if (levely[i] >= deep[lca] && num != 0) {
                for (int j = i; j >= 0; j--) {
                    if (num >> j == 1) {
                        if (basis[j] == 0) {
                            basis[j] = num;
                            break;
                        }
                        num ^= basis[j];
                    }
                }
            }
        }
        long ans = 0;
        for (int i = BIT; i >= 0; i--) {
            ans = Math.max(ans, ans ^ basis[i]);
        }
        return ans;
    }

    private static int lca(int x, int y) {
        if (deep[x] < deep[y]) {
            int tmp = x;
            x = y;
            y = tmp;
        }
        for (int p = power; p >= 0; p--) {
            if (deep[stjump[x][p]] >= deep[y]) {
                x = stjump[x][p];
            }
        }
        if (x == y) {
            return x;
        }
        for (int p = power; p >= 0; p--) {
            if (stjump[x][p] != stjump[y][p]) {
                x = stjump[x][p];
                y = stjump[y][p];
            }
        }
        return stjump[x][0];
    }

    private static void dfs(int u, int f) {
        deep[u] = deep[f] + 1;
        stjump[u][0] = f;
        for (int p = 1; p <= power; p++) {
            stjump[u][p] = stjump[stjump[u][p - 1]][p- 1];
        }
        for (int i = 0; i <= BIT; i++) {
            bases[u][i] = bases[f][i];
            levels[u][i] = levels[f][i];
        }
        insertReplace(arr[u], deep[u], bases[u], levels[u]);
        for (int e = head[u]; e != 0; e = next[e]) {
            if (to[e] != f) {
                dfs(to[e], u);
            }
        }
    }

    private static boolean insertReplace(long curv, int curl, long[] basis, int[] level) {
        for (int i = BIT; i >= 0; i--) {
            if (curv >> i == 1) {
                if (basis[i] == 0) {
                    basis[i] = curv;
                    level[i] = curl;
                    return true;
                }
                if (curl > level[i]) {
                    long tmp1 = curv;
                    curv = basis[i];
                    basis[i] = tmp1;
                    int tmp2 = curl;
                    curl = level[i];
                    level[i] = tmp2;
                }
                curv ^= basis[i];
            }
        }
        return false;
    }

    private static void prepare() {
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
        power = log2();
    }

    private static int log2() {
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

    // Kattio类IO效率很好，但还是不如StreamTokenizer
    // 只有StreamTokenizer无法正确处理时，才考虑使用这个类
    // 参考链接 : https://oi-wiki.org/lang/java-pro/
    public static class Kattio extends PrintWriter {
        private BufferedReader r;
        private StringTokenizer st;

        public Kattio() {
            this(System.in, System.out);
        }

        public Kattio(InputStream i, OutputStream o) {
            super(o);
            r = new BufferedReader(new InputStreamReader(i));
        }

        public Kattio(String intput, String output) throws IOException {
            super(output);
            r = new BufferedReader(new FileReader(intput));
        }

        public String next() {
            try {
                while (st == null || !st.hasMoreTokens())
                    st = new StringTokenizer(r.readLine());
                return st.nextToken();
            } catch (Exception e) {
            }
            return null;
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}
