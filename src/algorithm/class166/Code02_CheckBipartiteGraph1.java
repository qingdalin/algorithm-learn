package algorithm.class166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/26 19:52
 * // 判断二分图，java版
 * // 一共有n个节点，时刻的范围0~k，一共有m条操作，每条操作含义如下
 * // 操作 x y l r : 点x到点y之间连一条边，该边在l时刻出现，在r时刻消失
 * // 分别打印1时刻以内、2时刻以内..k时刻以内，图是不是二分图
 * // 注意i时刻以内是0~i-1时间段的意思
 * // 1 <= n、k <= 10^5    1 <= m <= 2 * 10^5
 * // 1 <= x、y <= n       0 <= l、r <= k
 * // 测试链接 : https://www.luogu.com.cn/problem/P5787
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_CheckBipartiteGraph1 {
    public static int MAXN = 100001;
    public static int MAXT = 3000001;
    public static int n, m, k;
    public static int[] father = new int[MAXN << 1];
    public static int[] siz = new int[MAXN << 1];
    public static int[][] rollback = new int[MAXN << 1][2];
    public static int opsize = 0;

    public static boolean[] ans = new boolean[MAXN];

    public static int[] head = new int[MAXN << 2];
    public static int[] next = new int[MAXT];
    public static int[] tox = new int[MAXT];
    public static int[] toy = new int[MAXT];
    public static int cnt = 0;

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
        boolean check = true;
        int unionCnt = 0;
        for(int ei = head[i]; ei > 0; ei = next[ei]) {
            int x = tox[ei], y = toy[ei], fx = find(x), fy = find(y);
            if (fx == fy) {
                check = false;
                break;
            } else {
                union(x, y + n);
                union(y, x + n);
                unionCnt += 2;
            }
        }
        if (check) {
            if (l == r) {
                ans[l] = true;
            } else {
                int mid = (l + r) / 2;
                dfs(l, mid, i << 1);
                dfs(mid + 1, r, i << 1 | 1);
            }
        } else {
            for (int j = l; j <= r; j++) {
                ans[j] = false;
            }
        }
        for (int j = 1; j <= unionCnt; j++) {
            undo();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        for (int i = 1; i <= 2 * n; i++) {
            father[i] = i;
            siz[i] = 1;
        }
        for (int i = 1, x, y, l, r; i <= m; i++) {
            in.nextToken(); x = (int) in.nval;
            in.nextToken(); y = (int) in.nval;
            in.nextToken(); l = (int) in.nval;
            in.nextToken(); r = (int) in.nval;
            add(l + 1, r, x, y, 1, k, 1);
        }
        dfs(1, k, 1);
        for (int i = 1; i <= k; i++) {
            if (ans[i]) {
                out.println("Yes");
            } else {
                out.println("No");
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
