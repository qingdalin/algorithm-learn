package algorithm.class110segmenttree01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/1 9:19
 * https://www.luogu.com.cn/problem/P1253
 */
public class SegmentTreeAddUpdateMax {
    public static int MAXN = 1000001;
    public static int n, q;
    public static long[] arr = new long[MAXN];
    public static long[] max = new long[MAXN << 2];
    public static long[] add = new long[MAXN << 2];
    public static long[] change = new long[MAXN << 2];
    public static boolean[] update = new boolean[MAXN << 2];

    public static void up(int i) {
        max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
    }

    public static void down(int i) {
        if (update[i]) {
            updateLazy(i << 1, change[i]);
            updateLazy(i << 1 | 1, change[i]);
            update[i] = false;
        }
        if (add[i] != 0) {
            addLazy(i << 1, add[i]);
            addLazy(i << 1 | 1, add[i]);
            add[i] = 0;
        }
    }

    private static void addLazy(int i, long v) {
        max[i] += v;
        add[i] += v;
    }

    private static void updateLazy(int i, long v) {
        max[i] = v;
        add[i] = 0;
        change[i] = v;
        update[i] = true;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            max[i] = arr[l];
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        add[i] = 0;
        change[i] = 0;
        update[i] = false;
    }

    public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            addLazy(i, jobv);
        } else {
            down(i);
            int mid = (l + r) >> 1;
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static void update(int jobl, int jobr, long jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            updateLazy(i, jobv);
        } else {
            down(i);
            int mid = (l + r) >> 1;
            if (jobl <= mid) {
                update(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static long query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        } else {
            down(i);
            int mid = (l + r) >> 1;
            long ans = Long.MIN_VALUE;
            if (jobl <= mid) {
                ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
            }
            if (jobr > mid) {
                ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
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
            q = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                arr[i] = (long) in.nval;
            }
            build(1, n, 1);
            long jobv;
            for (int i = 0, op, jobl, jobr; i < q; i++) {
                in.nextToken();
                op = (int) in.nval;
                in.nextToken();
                jobl= (int) in.nval;
                in.nextToken();
                jobr = (int) in.nval;
                if (op == 1) {
                    in.nextToken(); jobv = (long) in.nval;
                    update(jobl, jobr, jobv, 1, n, 1);
                } else if (op == 2){
                    in.nextToken(); jobv = (long) in.nval;
                    add(jobl, jobr, jobv, 1, n, 1);
                } else {
                    System.out.println(query(jobl, jobr, 1, n, 1));
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
