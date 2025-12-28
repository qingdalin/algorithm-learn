package algorithm.class102ac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/11 10:07
 * https://www.luogu.com.cn/problem/P5357
 */
public class ACAM {
    public static int MAXN = 200001;
    public static int MAXS = 200001;

    // 结尾的编号
    public static int[] end = new int[MAXN];
    // ac自动机
    public static int[][] tree = new int[MAXS][26];
    public static int[] fail = new int[MAXS];
    public static int cnt = 0;

    public static int n;
    public static int[] times = new int[MAXS];
    public static int[] box = new int[MAXS];

    public static int[] head = new int[MAXS];
    public static int[] next = new int[MAXS];
    public static int[] to = new int[MAXS];
    public static int edge = 0;

    // 递归内存溢出
    public static boolean[] visited = new boolean[MAXS];

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(bf.readLine());
//        st.nextToken();
//        n = (int) st.nval;
        for (int i = 1; i <= n; i++) {
            insert(i, bf.readLine());
        }
        setFail();
        char[] s = bf.readLine().toCharArray();
        for (int i = 0, u = 0; i < s.length; i++) {
            // 每个节点出现的次数++
            u = tree[u][s[i] - 'a'];
            times[u]++;
        }
        for (int i = 1; i <= cnt; i++) {
            // 链式前向星建立反图
            addEdge(fail[i], i);
        }
        f2(0);
        for (int i = 1; i <= n; i++) {
            System.out.println(times[end[i]]);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static void f2(int u) {
        int r = 0;
        box[r++] = u;
        int cur;
        while (r > 0) {
            cur = box[r - 1];
            if (!visited[cur]) {
                visited[cur] = true;
                for (int i = head[cur]; i > 0; i = next[i]) {
                    box[r++] = to[i];
                }
            } else {
                r--;
                for (int i = head[cur]; i > 0; i = next[i]) {
                    times[cur] += times[to[i]];
                }
            }
        }
    }

    private static void f1(int u) {
        for (int i = head[u]; i > 0; i = next[i]) {
            f1(to[i]);
            times[u] += times[to[i]];
        }
    }

    private static void addEdge(int u, int v) {
        next[++edge] = head[u];
        to[edge] = v;
        head[u] = edge;
    }

    private static void setFail() {
        int l = 0, r = 0;
        for (int i = 0; i <= 25; i++) {
            if (tree[0][i] > 0) {
                box[r++] = tree[0][i];
            }
        }
        while (l < r) {
            int u = box[l++];
            for (int i = 0; i <= 25; i++) {
                if (tree[u][i] == 0) {
                    tree[u][i] = tree[fail[u]][i];
                } else {
                    fail[tree[u][i]] = tree[fail[u]][i];
                    box[r++] = tree[u][i];
                }
            }
        }
    }

    private static void insert(int i, String word) {
        char[] s = word.toCharArray();
        int u = 0;
        for (int j = 0, c = 0; j < s.length; j++) {
            c = s[j] - 'a';
            if (tree[u][c] == 0) {
                tree[u][c] = ++cnt;
            }
            u = tree[u][c];
        }
        end[i] = u;
    }
}
