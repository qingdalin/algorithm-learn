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
 * @date: 2025/4/28 20:13
 * // 给边涂色，java版
 * // 一共有n个点，给定m条无向边，一开始每条边无颜色，一共有k种颜色
 * // 合法状态的定义为，仅保留染成k种颜色中的任何一种颜色的边，图都是一张二分图
 * // 一共有q条操作，每条操作格式如下
 * // 操作 e c : 第e条边，现在要涂成c颜色
 * //           如果执行此操作之后，整张图还是合法状态，那么执行并打印"YES"
 * //           如果执行此操作之后，整张图不再是合法状态，那么不执行并打印"NO"
 * // 1 <= n、m、q <= 5 * 10^5    1 <= k <= 50
 * // 测试链接 : https://www.luogu.com.cn/problem/CF576E
 * // 测试链接 : https://codeforces.com/problemset/problem/576/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code07_PaintingEdges1 {
    public static int MAXN = 500001;
    public static int MAXK = 51;
    public static int MAXT = 10000001;
    public static int n, m, k, q;
    public static int[] u = new int[MAXN];
    public static int[] v = new int[MAXN];

    public static int[] e = new int[MAXN];
    public static int[] c = new int[MAXN];
    public static int[] post = new int[MAXN];

    public static int[][] father = new int[MAXK][MAXN << 1];
    public static int[][] siz = new int[MAXK][MAXN << 1];
    public static int[][] rollback = new int[MAXN << 1][3];
    public static int opsize = 0;

    public static int[] head = new int[MAXN << 2];
    public static int[] next = new int[MAXT];
    public static int[] qid = new int[MAXT];
    public static int cnt = 0;
    // lastColor[i] : 第i号边上次成功涂上的颜色
    public static int[] lastColor = new int[MAXN];
    public static boolean[] ans = new boolean[MAXN];

    public static void addEdge(int i, int qi) {
        next[++cnt] = head[i];
        qid[cnt] = qi;
        head[i] = cnt;
    }

    public static int find(int color, int i) {
        while (i != father[color][i]) {
            i = father[color][i];
        }
        return i;
    }

    public static void union(int color, int x, int y) {
        int fx = find(color, x);
        int fy = find(color, y);
        if (siz[color][fx] < siz[color][fy]) {
            int tmp = fx;
            fx = fy;
            fy = tmp;
        }
        father[color][fy] = fx;
        siz[color][fx] += siz[color][fy];
        rollback[++opsize][0] = color;
        rollback[opsize][1] = fx;
        rollback[opsize][2] = fy;
    }

    public static void undo() {
        int color = rollback[opsize][0];
        int fx = rollback[opsize][1];
        int fy = rollback[opsize--][2];
        father[color][fy] = fy;
        siz[color][fx] -= siz[color][fy];
    }

    public static void add(int jobl, int jobr, int jobq, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            addEdge(i, jobq);
        } else {
            int mid = (l + r) / 2;
            if (jobl <= mid) {
                add(jobl, jobr, jobq, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobq, mid + 1, r, i << 1 | 1);
            }
        }
    }

    public static void dfs(int l, int r, int i) {
        int unionCnt = 0;
        int color, x, y, xn, yn, fx, fy, fxn, fyn;
        for(int ei = head[i]; ei > 0; ei = next[ei]) {
            color = c[qid[ei]];
            x = u[e[qid[ei]]];
            y = v[e[qid[ei]]];
            xn = x + n;
            yn = y + n;
            fx = find(color, x);
            fy = find(color, y);
            fxn = find(color, xn);
            fyn = find(color, yn);
            // 错误写法
//            if (fx != fy) {
//                union(color, fx, fy);
//                unionCnt++;
//            }
//            if (fxn != fyn) {
//                union(color, fxn, fyn);
//                unionCnt++;
//            }
            if (fx != fyn) {
                union(color, fx, fyn);
                unionCnt++;
            }
            if (fy != fxn) {
                union(color, fy, fxn);
                unionCnt++;
            }
        }
        if (l == r) {
            if (find(c[l], u[e[l]]) == find(c[l], v[e[l]])) {
                ans[l] = false;
                c[l] = lastColor[e[l]];
            } else {
                ans[l] = true;
                // 错误写法
                // lastColor[l] = c[l];
                lastColor[e[l]] = c[l];
            }
        } else {
            int mid = (l + r) / 2;
            dfs(l, mid, i << 1);
            dfs(mid + 1, r, i << 1 | 1);
        }
        for (int j = 1; j <= unionCnt; j++) {
            undo();
        }
    }

    public static void prepare() {
        for (int color = 1; color <= k; color++) {
            for (int i = 1; i <= n; i++) {
                father[color][i] = i;
                father[color][i + n] = i + n;
                siz[color][i] = 1;
                siz[color][i + n] = 1;
            }
        }
        for (int i = 1; i <= m; i++) {
            post[i] = q;
        }
        for (int i = q; i >= 1; i--) {
            if (i + 1 <= post[e[i]]) {
                add(i + 1, post[e[i]], i, 1, q, 1);
            }
            post[e[i]] = i;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        in.nextToken(); q = (int) in.nval;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); u[i] = (int) in.nval;
            in.nextToken(); v[i] = (int) in.nval;
        }
        for (int i = 1; i <= q; i++) {
            in.nextToken(); e[i] = (int) in.nval;
            in.nextToken(); c[i] = (int) in.nval;
        }
        prepare();
        dfs(1, q, 1);
        for (int i = 1; i <= q; i++) {
            if (ans[i]) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
