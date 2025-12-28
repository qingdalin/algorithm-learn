package algorithm.class113segmenttree04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/9 20:50
 * https://www.luogu.com.cn/problem/P2572
 */
public class SequenceOperate {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] sum = new int[MAXN << 2];
    public static int[] len1 = new int[MAXN << 2];
    public static int[] pre1 = new int[MAXN << 2];
    public static int[] suf1 = new int[MAXN << 2];
    public static int[] len0 = new int[MAXN << 2];
    public static int[] pre0 = new int[MAXN << 2];
    public static int[] suf0 = new int[MAXN << 2];
    public static int[] change = new int[MAXN << 2];
    public static boolean[] update = new boolean[MAXN << 2];
    public static boolean[] reverse = new boolean[MAXN << 2];

    public static void up(int i, int ln, int rn) {
        int l = i << 1;
        int r = i << 1 | 1;
        sum[i] = sum[l] + sum[r];
        len0[i] = Math.max(Math.max(len0[l], len0[r]), suf0[l] + pre0[r]);
        pre0[i] = len0[l] < ln ? pre0[l] : pre0[l] + pre0[r];
        suf0[i] = len0[r] < rn ? suf0[r] : suf0[l] + suf0[r];
        len1[i] = Math.max(Math.max(len1[l], len1[r]), suf1[l] + pre1[r]);
        pre1[i] = len1[l] < ln ? pre1[l] : pre1[l] + pre1[r];
        suf1[i] = len1[r] < rn ? suf1[r] : suf1[l] + suf1[r];
    }

    public static void down(int i, int ln, int rn) {
        if (update[i]) {
            updateLazy(i << 1, change[i], ln);
            updateLazy(i << 1 | 1, change[i], rn);
            update[i] = false;
        }
        if (reverse[i]) {
            reverseLazy(i << 1, ln);
            reverseLazy(i << 1 | 1, rn);
            reverse[i] = false;
        }
    }

    private static void reverseLazy(int i, int n) {
        sum[i] = n - sum[i];
        int tmp;
        tmp = len1[i]; len1[i] = len0[i]; len0[i] = tmp;
        tmp = pre1[i]; pre1[i] = pre0[i]; pre0[i] = tmp;
        tmp = suf1[i]; suf1[i] = suf0[i]; suf0[i] = tmp;
        reverse[i] = !reverse[i];
    }

    private static void updateLazy(int i, int v, int n) {
        sum[i] = v * n;
        len0[i] = pre0[i] = suf0[i] = v == 0 ? n : 0;
        len1[i] = pre1[i] = suf1[i] = v == 1 ? n : 0;
        change[i] = v;
        update[i] = true;
        reverse[i] = false;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = arr[l];
            len1[i] = pre1[i] = suf1[i] = arr[l] == 1 ? 1 : 0;
            len0[i] = pre0[i] = suf0[i] = arr[l] == 0 ? 1 : 0;
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i, mid - l + 1, r - mid);
        }
        change[i] = 0;
        update[i] = false;
        reverse[i] = false;
    }

    public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l  && r <= jobr) {
            updateLazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            int ln = mid - l + 1;
            int rn = r - mid;
            down(i, ln, rn);
            if (jobl <= mid) {
                update(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i, ln, rn);
        }
    }

    public static void reverse(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            reverseLazy(i, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            int ln = mid - l + 1;
            int rn = r - mid;
            down(i, ln, rn);
            if (jobl <= mid) {
                reverse(jobl, jobr, l, mid, i << 1);
            }
            if (jobr > mid) {
                reverse(jobl, jobr, mid + 1, r, i << 1 | 1);
            }
            up(i, ln, rn);
        }
    }

    public static int querySum(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        int ans = 0;
        int mid = (l + r) >> 1;
        down(i, mid - l + 1, r - mid);
        if (jobl <= mid) {
            ans += querySum(jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
        }
        return ans;
    }

    public static int[] queryLongest(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return new int[] {len1[i], pre1[i], suf1[i]};
        }
        int mid = (l + r) >> 1;
        int ln = mid - l + 1;
        int rn = r - mid;
        down(i, ln, rn);
        if (jobr <= mid) {
            return queryLongest(jobl, jobr, l, mid, i << 1);
        }
        if (jobl > mid) {
            return queryLongest(jobl, jobr, mid + 1, r, i << 1 | 1);
        }

        int[] l3 = queryLongest(jobl, jobr, l, mid, i << 1);
        int[] r3 = queryLongest(jobl, jobr, mid + 1, r, i << 1 | 1);
        int len = Math.max(Math.max(l3[0], r3[0]), l3[2] + r3[1]);
        int pre = l3[0] < mid - Math.max(l, jobl) + 1 ? l3[1] : l3[1] + r3[1];
        int suf = r3[0] < Math.min(r, jobr) - mid ? r3[2] : r3[2] + l3[2];
        return new int[] {len, pre, suf};
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
                arr[i] = (int) in.nval;
            }
            build(1, n, 1);
            for (int i = 1, op, jobl, jobr; i <= m; i++) {
                in.nextToken(); op = (int) in.nval;
                in.nextToken(); jobl = (int) in.nval + 1;
                in.nextToken(); jobr = (int) in.nval + 1;
                if (op == 0) {
                    update(jobl, jobr, 0, 1, n,1);
                } else if (op == 1){
                    update(jobl, jobr, 1, 1, n,1);
                } else if (op == 2) {
                    reverse(jobl, jobr, 1, n, 1);
                } else if (op == 3) {
                    System.out.println(querySum(jobl, jobr, 1, n, 1));
                } else {
                    System.out.println(queryLongest(jobl, jobr, 1, n, 1)[0]);
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
