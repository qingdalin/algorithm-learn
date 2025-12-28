package algorithm.class59map;

import java.io.*;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-23 11:22
 */
public class ToPoSort2 {
    public static int MAXN = 100001;
    public static int MAXM = 2 * MAXN;

    public static int[] head = new int[MAXM];
    public static int[] next = new int[MAXM];
    public static int[] to = new int[MAXM];
    public static int[] queue = new int[MAXM];
    public static int[] ans = new int[MAXM];
    public static int[] indegree = new int[MAXM];
    public static int l, r, cnt, n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (StreamTokenizer.TT_EOF != st.nextToken()) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            build();
            for (int i = 0; i < m; i++) {
                st.nextToken();
                int from = (int) st.nval;
                st.nextToken();
                int to = (int) st.nval;
                addEdge(from, to);
                indegree[to]++;
            }
            if (toPoSort()) {
                for (int i = 0; i < n - 1; i++) {
                    out.print(ans[i] + " ");
                }
                out.println(ans[n - 1]);
            } else {
                out.println(-1);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    public static void addEdge(int u, int v) {
        // u -> v 权重w
        // 下一条边是老头部边
        next[cnt] = head[u];
        // 去往的点
        to[cnt] = v;
        // 权重
        // 新头部边是cnt
        head[u] = cnt++;
    }

    public static void build() {
        cnt = 1;
        Arrays.fill(head, 0, n + 1, 0);
        Arrays.fill(indegree, 0, n + 1, 0);
    }

    private static boolean toPoSort() {
        l = r = 0;
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue[r++] = i;
            }
        }
        int fill = 0;
        while (l < r) {
            int cur = queue[l++];
            ans[fill++] = cur;
            for (int e1 = head[cur]; e1 != 0; e1 = next[e1]) {
                if (--indegree[to[e1]] == 0) {
                    queue[r++] = to[e1];
                }
            }

        }
        return fill == n;
    }
}
