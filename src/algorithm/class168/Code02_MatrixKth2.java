package algorithm.class168;

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
 * @date: 2025/5/17 11:49
 * // 矩阵内第k小，第二种写法，java版
 * // 给定一个n * n的矩阵，接下来有q条查询，格式如下
 * // 查询 a b c d k : 左上角(a, b)，右下角(c, d)，打印该区域中第k小的数
 * // 1 <= n <= 500
 * // 1 <= q <= 6 * 10^4
 * // 0 <= 矩阵中的数字 <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P1527
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_MatrixKth2 {
    public static int MAXN = 501;
    public static int MAXQ = 60001;
    public static int n, q;
    public static int[][] xyv = new int[MAXN * MAXN][3];
    public static int cntv = 0;
    public static int used = 0;
    public static int[] qid = new int[MAXQ];
    public static int[] a = new int[MAXQ];
    public static int[] b = new int[MAXQ];
    public static int[] c = new int[MAXQ];
    public static int[] d = new int[MAXQ];
    public static int[] k = new int[MAXQ];
    public static int[][] tree = new int[MAXN][MAXN];
    public static int[] lset = new int[MAXQ];
    public static int[] rset = new int[MAXQ];
    public static int[] ans = new int[MAXQ];

    public static int lowbit(int i) {
        return i & -i;
    }

    public static void add(int x, int y, int v) {
        for (int i = x; i <= n; i += lowbit(i)) {
            for (int j = y; j <= n; j += lowbit(j)) {
                tree[i][j] += v;
            }
        }
    }

    public static int sum(int x, int y) {
        int ret = 0;
        for(int i = x; i > 0; i -= lowbit(i)) {
            for(int j = y; j > 0; j -= lowbit(j)) {
                ret += tree[i][j];
            }
        }
        return ret;
    }

    public static int query(int a, int b, int c, int d) {
        return sum(c, d) - sum(c, b - 1) - sum(a - 1, d) + sum(a - 1, b - 1);
    }

    public static void compute(int ql, int qr, int vl, int vr) {
        if (ql > qr) {
            return;
        }
        if (vl == vr) {
            for (int i = ql; i <= qr; i++) {
                ans[qid[i]] = xyv[vl][2];
            }
        } else {
            int mid = (vl + vr) / 2;
            while (used < mid) {
                ++used;
                add(xyv[used][0], xyv[used][1], 1);
            }
            while (used > mid) {
                add(xyv[used][0], xyv[used][1], -1);
                used--;
            }
            int lsiz = 0, rsiz = 0;
            for (int i = ql; i <= qr; i++) {
                int id = qid[i];
                int satisfy = query(a[id], b[id], c[id], d[id]);
                if (satisfy >= k[id]) {
                    lset[++lsiz] = id;
                } else {
                    rset[++rsiz] = id;
                }
            }
            for (int i = 1; i <= lsiz; i++) {
                qid[ql + i - 1] = lset[i];
            }
            for (int i = 1; i <= rsiz; i++) {
                qid[ql + lsiz + i - 1] = rset[i];
            }
            compute(ql, ql + lsiz - 1, vl, mid);
            compute(ql + lsiz, qr, mid + 1, vr);
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); q = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                xyv[++cntv][0] = i;
                xyv[cntv][1] = j;
                in.nextToken(); xyv[cntv][2] = (int) in.nval;
            }
        }
        for (int i = 1; i <= q; i++) {
            qid[i] = i;
            in.nextToken(); a[i] = (int) in.nval;
            in.nextToken(); b[i] = (int) in.nval;
            in.nextToken(); c[i] = (int) in.nval;
            in.nextToken(); d[i] = (int) in.nval;
            in.nextToken(); k[i] = (int) in.nval;
        }
        Arrays.sort(xyv, 1, cntv + 1, (a, b) -> a[2] - b[2]);
        compute(1, q, 1, cntv);
        for (int i = 1; i <= q; i++) {
            out.println(ans[i]);
        }
        out.flush();
        out.close();
        bf.close();
    }
}
