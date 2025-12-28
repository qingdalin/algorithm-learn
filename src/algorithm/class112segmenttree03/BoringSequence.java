package algorithm.class112segmenttree03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/7 9:45
 * https://www.luogu.com.cn/problem/P1438
 * 维护差分数列的线段树信息
 */
public class BoringSequence {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] diff = new int[MAXN];
    public static long[] sum = new long[MAXN << 2];
    public static long[] add = new long[MAXN << 2];

    public static void up(int i) {
        sum[i] = sum[i << 1] + sum[i << 1 | 1];
    }

    public static void down(int i, int ln, int rn) {
        if (add[i] != 0) {
            lazy(i << 1, ln, add[i]);
            lazy(i << 1 | 1, rn, add[i]);
            add[i] = 0;
        }
    }

    private static void lazy(int i, int n, long v) {
        sum[i] += n * v;
        add[i] += v;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = diff[l];
        } else {
            int mid =(l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        add[i] = 0;
    }

    public static void add(int jobl,  int jobr, long jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, r - l + 1, jobv);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static long query(int jobl, int jobi, int l, int r, int i) {
        if (jobl <= l && r <= jobi) {
            return sum[i];
        } else {
            long ans = 0;
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                ans += query(jobl, jobi, l, mid, i << 1);
            }
            if (jobi > mid) {
                ans += query(jobl, jobi, mid + 1, r, i << 1 | 1);
            }
            return ans;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;
            for (int i = 1, pre = 0, cur = 0; i <= n; i++) {
                in.nextToken();
                cur = (int) in.nval;
                diff[i] = cur - pre;
                pre = cur;
            }
            build(1, n, 1);
            for (int i = 1, op, jobl, jobr; i <= m; i++) {
                in.nextToken(); op = (int) in.nval;
                if (op == 1) {
                    in.nextToken(); jobl = (int) in.nval;
                    in.nextToken(); jobr = (int) in.nval;
                    in.nextToken(); long k = (long) in.nval;
                    in.nextToken(); long d = (long) in.nval;
                    // 先将l位置加一个首项
                    add(jobl, jobl, k, 1, n, 1);
                    // 再将jobl+1 -- jobr 位置每一项加一个d
                    if (jobl + 1 <= jobr) {
                        add(jobl + 1, jobr, d, 1, n, 1);
                    }
                    // 最后将jobr + 1位置减去末项
                    long e = k + d * (jobr - jobl);
                    if (jobr <  n) {
                        add(jobr + 1, jobr + 1, -e, 1, n, 1);
                    }
                } else {
                    in.nextToken(); int p = (int) in.nval;
                    System.out.println(query(1, p, 1, n, 1));
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
