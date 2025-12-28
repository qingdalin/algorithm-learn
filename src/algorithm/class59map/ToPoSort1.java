package algorithm.class59map;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-23 10:55
 * https://www.nowcoder.com/practice/88f7e156ca7d43a1a535f619cd3f495c
 * 拓扑排序
 */
public class ToPoSort1 {
    public static int MAXN = 100001;
    public static int MAXM = 2 * MAXN;
    public static int[] queue = new int[MAXM];
    public static int[] ans = new int[MAXM];
    public static int[] indegree = new int[MAXM];
    public static int l, r, cnt, n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (StreamTokenizer.TT_EOF != st.nextToken()) {
            Arrays.fill(indegree, 0);
            List<List<Integer>> graph = new ArrayList<>();
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }
            for (int i = 0; i < m; i++) {
                st.nextToken();
                int from = (int) st.nval;
                st.nextToken();
                int to = (int) st.nval;
                graph.get(from).add(to);
                indegree[to]++;
            }
            if (toPoSort(graph)) {
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

    private static boolean toPoSort(List<List<Integer>> graph) {
        l = r = cnt = 0;
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue[r++] = i;
            }

        }
        while (l < r) {
            int cur = queue[l++];
            ans[cnt++] = cur;
            for (Integer next : graph.get(cur)) {
                if (--indegree[next] == 0) {
                    queue[r++] = next;
                }
            }
        }
        return cnt == n;
    }
}
