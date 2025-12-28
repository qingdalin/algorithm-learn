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
 * @date: 2024/9/7 8:13
 * https://www.luogu.com.cn/problem/P3870
 */
public class Switch {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] light = new int[MAXN << 2];
    public static boolean[] reverse = new boolean[MAXN << 2];

    public static void up(int i) {
        light[i] = light[i << 1] + light[i << 1 | 1];
    }

    public static void down(int i, int ln, int rn) {
        if (reverse[i]) {
            lazy(i << 1, ln);
            lazy(i << 1 | 1, rn);
            reverse[i] = false;
        }
    }

    private static void lazy(int i, int n) {
        light[i] = n - light[i];
        reverse[i] = !reverse[i];
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            light[i] = 0;
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        reverse[i] = false;
    }

    public static void reverse(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                reverse(jobl, jobr, l, mid, i << 1);
            }
            if (jobr > mid) {
                reverse(jobl, jobr, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static int query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return light[i];
        }
        int ans = 0;
        int mid = (l + r) >> 1;
        down(i, mid - l + 1, r - mid);
        if (jobl <= mid) {
            ans += query(jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;
            build(1, n, 1);
            for (int i = 1, op, jobl, jobr; i <= m; i++) {
                in.nextToken(); op = (int) in.nval;
                in.nextToken(); jobl = (int) in.nval;
                in.nextToken(); jobr = (int) in.nval;
                if (op == 0) {
                    reverse(jobl, jobr, 1, n, 1);
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
