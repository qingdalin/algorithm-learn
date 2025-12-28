package algorithm.class114segmenttree05;

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
 * @date: 2024/9/14 21:16
 * https://www.luogu.com.cn/problem/P2781
 * 开点线段树
 */
public class Code01_DynamicSegmentTree {
    public static int LIMIT = 80001;
    public static int cnt;
    public static int[] left = new int[LIMIT];
    public static int[] right = new int[LIMIT];
    public static long[] sum = new long[LIMIT];
    public static long[] add = new long[LIMIT];
    public static void up(int i, int l, int r) {
        sum[i] = sum[l] + sum[r];
    }

    public static void down(int i, int ln, int rn) {
        if (add[i] != 0) {
            // 需要左树进行创建
            if (left[i] == 0) {
                left[i] = ++cnt;
            }
            if (right[i] == 0) {
                right[i] = ++cnt;
            }
            lazy(left[i], add[i], ln);
            lazy(right[i], add[i], rn);
            add[i] = 0;
        }
    }

    private static void lazy(int i, long v, int n) {
        sum[i] += n * v;
        add[i] += v;
    }

    public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                if (left[i] == 0) {
                    left[i] = ++cnt;
                }
                add(jobl, jobr, jobv, l, mid, left[i]);
            }
            if (jobr > mid) {
                if (right[i] == 0) {
                    right[i] = ++cnt;
                }
                add(jobl, jobr, jobv, mid + 1, r, right[i]);
            }
            up(i, left[i], right[i]);
        }
    }

    public static long query(int jobl ,int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        long ans = 0;
        int mid = (l + r) >> 1;
        down(i, mid - l + 1, r - mid);
        if (jobl <= mid && left[i] != 0) {
            ans += query(jobl, jobr, l, mid, left[i]);
        }
        if (jobr > mid && right[i] != 0) {
            ans += query(jobl, jobr, mid + 1, r, right[i]);
        }
        return ans;
    }

    public static void clear() {
        Arrays.fill(left, 1, cnt + 1, 0);
        Arrays.fill(right, 1, cnt + 1, 0);
        Arrays.fill(sum, 1, cnt + 1, 0);
        Arrays.fill(add, 1, cnt + 1, 0);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        int n = (int) in.nval;
        in.nextToken();
        int m = (int) in.nval;
        cnt = 1;
        long jobv;
        for (int i = 1, op, jobl, jobr; i <= m; i++) {
            in.nextToken(); op = (int) in.nval;
            in.nextToken(); jobl = (int) in.nval;
            in.nextToken(); jobr = (int) in.nval;
            if (op == 1) {
                in.nextToken(); jobv = (long) in.nval;
                add(jobl, jobr, jobv, 1, n, 1);
            } else {
                System.out.println(query(jobl, jobr, 1, n, 1));
            }
        }
        clear();
        out.flush();
        out.close();
        bf.close();
    }
}
