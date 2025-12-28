package algorithm.class111segmenttree02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/6 20:52
 * https://www.luogu.com.cn/problem/CF438D
 * 提交失败，codeforces暂时不可用
 */
public class QueryModUpdate {
    public static int MAXN = 100001;
    public static int n, m;
    public static long[] arr = new long[MAXN];
    public static long[] sum = new long[MAXN << 2];
    public static long[] max = new long[MAXN << 2];
    public static void up(int i) {
        sum[i] = sum[i << 1] + sum[i << 1 | 1];
        max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = max[i] = arr[l];
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
    }

    public static long query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        } else {
            int mid = (l + r) >> 1;
            long ans = 0;
            if (jobl <= mid) {
                ans += query(jobl, jobr, l, mid, i << 1);
            }
            if (jobr > mid) {
                ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
            }
            return ans;
        }
    }
    public static void mod(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobv > max[i]) {
            return;
        }
        if (l == r) {
            sum[i] %= jobv;
            max[i] %= jobv;
        } else {
            int mid = (l + r) >> 1;
            if (jobl <= mid) {
                mod(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                mod(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }
    public static void update(int jobi, int jobv, int l, int r, int i) {
        if (l == r) {
            sum[i] = max[i] = jobv;
        } else {
            int mid = (l + r) >> 1;
            if (jobi <= mid) {
                update(jobi, jobv, l, mid, i << 1);
            } else {
                update(jobi, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
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
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                arr[i] = (long) in.nval;
            }
            build(1, n, 1);
            for (int i = 0, op, jobl, jobr, x, k; i < m; i++) {
                in.nextToken(); op = (int) in.nval;

                if (op == 1) {
                    in.nextToken(); jobl = (int) in.nval;
                    in.nextToken(); jobr = (int) in.nval;
                    System.out.println(query(jobl, jobr, 1, n, 1));
                } else if (op == 2){
                    in.nextToken(); jobl = (int) in.nval;
                    in.nextToken(); jobr = (int) in.nval;
                    in.nextToken(); x = (int) in.nval;
                    mod(jobl, jobr, x, 1, n, 1);
                } else {
                    in.nextToken(); k = (int) in.nval;
                    in.nextToken(); x = (int) in.nval;
                    update(k, x, 1, n, 1);
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
