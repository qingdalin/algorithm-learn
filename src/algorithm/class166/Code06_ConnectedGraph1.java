package algorithm.class166;

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
 * @date: 2025/4/27 20:46
 * // 连通图，java版
 * // 一共有n个点，给定m条边，所有点一开始就连通在一起了
 * // 一共有k条操作，每条操作格式如下
 * // 操作 c ... : 操作涉及c条边，这些边的编号 ... 一共c个
 * //              假设删掉这些边，打印整张图是否联通
 * // 每条操作都是独立的，相互之间没有任何关系
 * // 1 <= n、k <= 10^5
 * // 1 <= m <= 2 * 10^5
 * // 1 <= c <= 4
 * // 测试链接 : https://www.luogu.com.cn/problem/P5227
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code06_ConnectedGraph1 {
    public static int MAXN = 100001;
    public static int MAXM = 200001;
    public static int MAXE = 400001;
    public static int MAXT = 10000001;
    public static int n, m, k;
    public static int[] u = new int[MAXN];
    public static int[] v = new int[MAXN];
    public static int[][] event = new int[MAXE][2];
    public static int ecnt = 0;

    public static boolean[] visited = new boolean[MAXM];

    public static int[] father = new int[MAXN];
    public static int[] siz = new int[MAXN];
    public static int[][] rollback = new int[MAXN][2];
    public static int opsize = 0;

    public static int[] head = new int[MAXN << 2];
    public static int[] next = new int[MAXT];
    public static int[] tox = new int[MAXT];
    public static int[] toy = new int[MAXT];
    public static int cnt = 0;
    public static boolean[] ans = new boolean[MAXN];

    public static void addEdge(int i, int x, int y) {
        next[++cnt] = head[i];
        tox[cnt] = x;
        toy[cnt] = y;
        head[i] = cnt;
    }

    public static int find(int i) {
        while (i != father[i]) {
            i = father[i];
        }
        return i;
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (siz[fx] < siz[fy]) {
            int tmp = fx;
            fx = fy;
            fy = tmp;
        }
        father[fy] = fx;
        siz[fx] += siz[fy];
        rollback[++opsize][0] = fx;
        rollback[opsize][1] = fy;
    }

    public static void undo() {
        int fx = rollback[opsize][0];
        int fy = rollback[opsize--][1];
        father[fy] = fy;
        siz[fx] -= siz[fy];
    }

    public static void add(int jobl, int jobr, int jobx, int joby, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            addEdge(i, jobx, joby);
        } else {
            int mid = (l + r) / 2;
            if (jobl <= mid) {
                add(jobl, jobr, jobx, joby, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobx, joby, mid + 1, r, i << 1 | 1);
            }
        }
    }

    public static void dfs(int l, int r, int i) {
        boolean check = false;
        int unionCnt = 0;
        for(int ei = head[i]; ei > 0; ei = next[ei]) {
            int fx = find(tox[ei]), fy = find(toy[ei]);
            if (fx != fy) {
                union(fx, fy);
                unionCnt++;
            }
            if (siz[find(fx)] == n) {
                check = true;
                break;
            }
        }
        if (check) {
            for (int j = l; j <= r; j++) {
                ans[j] = true;
            }
        } else {
            if (l == r) {
                ans[l] = false;
            } else {
                int mid = (l + r) / 2;
                dfs(l, mid, i << 1);
                dfs(mid + 1, r, i << 1 | 1);
            }
        }
        for (int j = 1; j <= unionCnt; j++) {
            undo();
        }
    }

    public static void prepare() {
        for (int i = 1; i <= n; i++) {
            father[i] = i;
            siz[i] = 1;
        }
        Arrays.sort(event, 1, ecnt + 1, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        int eid, t;
        for (int l = 1, r = 1; l <= ecnt; l = ++r) {
            eid = event[l][0];
            visited[eid] = true;
            while (r + 1 <= ecnt && event[r + 1][0] == eid) {
                r++;
            }
            t = 1;
            for (int i = l; i <= r; i++) {
                if (t <= event[i][1] - 1) {
                    add(t, event[i][1] - 1, u[eid], v[eid], 1, k, 1);
                }
                t = event[i][1] + 1;
            }
            if (t <= k) {
                add(t, k, u[eid], v[eid], 1, k, 1);
            }
        }
        for (int i = 1; i <= m; i++) {
            if (!visited[i]) {
                add(1, k, u[i], v[i], 1, k, 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); u[i] = (int) in.nval;
            in.nextToken(); v[i] = (int) in.nval;
        }
        in.nextToken(); k = (int) in.nval;
        for (int i = 1, c; i <= k; i++) {
            in.nextToken(); c = (int) in.nval;
            for (int j = 1; j <= c; j++) {
                in.nextToken(); event[++ecnt][0] = (int) in.nval;
                event[ecnt][1] = i;
            }
        }
        prepare();
        dfs(1, k, 1);
        for (int i = 1; i <= k; i++) {
            if (ans[i]) {
                out.println("Connected");
            } else {
                out.println("Disconnected");
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
