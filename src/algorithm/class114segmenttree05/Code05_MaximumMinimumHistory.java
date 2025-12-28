package algorithm.class114segmenttree05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/15 11:31
 * https://www.luogu.com.cn/problem/P6242
 */
public class Code05_MaximumMinimumHistory {
    public static int MAXN = 500001;
    public static long LOWEST = Long.MIN_VALUE;
    public static int[] arr = new int[MAXN];
    public static long[] sum = new long[MAXN << 2];
    // 最大值，只维持当前最大值，不维护懒更新
    public static long[] max = new long[MAXN << 2];
    // 次大值
    public static long[] sem = new long[MAXN << 2];
    public static int[] cnt = new int[MAXN << 2];
    // 懒更新维持增加的最大值
    public static long[] maxAdd = new long[MAXN << 2];
    // 懒更新，维持增加的其他位的数值
    public static long[] otherAdd = new long[MAXN << 2];

    // 历史最大值
    public static long[] maxHistory = new long[MAXN << 2];
    // 最大值达到过的最大提升幅度(懒更新信息)
    public static long[] maxAddTop = new long[MAXN << 2];
    // 除最大值以外的其他数字，达到过的最大提升幅度(懒更新信息)
    public static long[] otherAddTop = new long[MAXN << 2];

    public static void up(int i) {
        int l = i << 1;
        int r = i << 1 | 1;
        sum[i] = sum[l] + sum[r];
        max[i] = Math.max(max[l], max[r]);
        maxHistory[i] = Math.max(maxHistory[l], maxHistory[r]);
        if (max[l] > max[r]) {
            cnt[i] = cnt[l];
            sem[i] = Math.max(sem[l], max[r]);
        } else if (max[l] < max[r]) {
            cnt[i] = cnt[r];
            sem[i] = Math.max(max[l], sem[r]);
        } else {
            cnt[i] = cnt[l] + cnt[r];
            sem[i] = Math.max(sem[l], sem[r]);
        }
    }

    public static void down(int i, int ln, int rn) {
        int l = i << 1;
        int r = i << 1 | 1;
        long tmp = Math.max(max[l], max[r]);
        // 临时的最大值
        if (max[l] == tmp) {
            // 有最大值
            lazy(l, ln, maxAdd[i], otherAdd[i], maxAddTop[i], otherAddTop[i]);
        } else {
            lazy(l, ln, otherAdd[i], otherAdd[i], otherAddTop[i], otherAddTop[i]);
        }
        if (max[r] == tmp) {
            lazy(r, rn, maxAdd[i], otherAdd[i], maxAddTop[i], otherAddTop[i]);
        } else {
            lazy(r, rn, otherAdd[i], otherAdd[i], otherAddTop[i], otherAddTop[i]);
        }
        maxAdd[i] = otherAdd[i] = maxAddTop[i] = otherAddTop[i] = 0;
    }

    private static void lazy(int i, int n, long maxAddv, long otherAddv, long maxUpv, long otherUpv) {
        maxHistory[i] = Math.max(maxHistory[i], max[i] + maxUpv);
        maxAddTop[i] = Math.max(maxAddTop[i], maxAdd[i] + maxUpv);
        otherAddTop[i] = Math.max(otherAddTop[i], otherAdd[i] + otherUpv);
        sum[i] += maxAddv * cnt[i] + otherAddv * (n - cnt[i]);
        max[i] += maxAddv;
        sem[i] += sem[i] == LOWEST ? 0 : otherAddv;
        maxAdd[i] += maxAddv;
        otherAdd[i] += otherAddv;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = max[i] = maxHistory[i] = arr[l];
            cnt[i] = 1;
            sem[i] = LOWEST;
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        maxAdd[i] = otherAdd[i] = maxAddTop[i] = otherAddTop[i] = 0;
    }

    public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, r - l + 1, jobv, jobv, jobv, jobv);
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

    public static void setMin(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobv >= max[i]) {
            return;
        }
        if (jobl <= l && r <= jobr && sem[i] < jobv) {
            lazy(i, r - l + 1, jobv - max[i], 0, jobv - max[i], 0);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                setMin(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                setMin(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static long queryMax(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        }
        int mid = (l + r) >> 1;
        long ans = Long.MIN_VALUE;
        down(i, mid - l + 1, r - mid);
        if (jobl <= mid) {
            ans = Math.max(ans, queryMax(jobl, jobr, l, mid, i << 1));
        }
        if (jobr > mid) {
            ans = Math.max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
        }
        return ans;
    }

    public static long querySum(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        int mid = (l + r) >> 1;
        long ans = 0;
        down(i, mid - l + 1, r - mid);
        if (jobl <= mid) {
            ans += querySum(jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
        }
        return ans;
    }

    public static long historyMax(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return maxHistory[i];
        }
        int mid = (l + r) >> 1;
        long ans = Long.MIN_VALUE;
        down(i, mid - l + 1, r - mid);
        if (jobl <= mid) {
            ans = Math.max(ans, historyMax(jobl, jobr, l, mid, i << 1));
        }
        if (jobr > mid) {
            ans = Math.max(ans, historyMax(jobl, jobr, mid + 1, r, i << 1 | 1));
        }
        return ans;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int n = (int) in.nval;
        in.nextToken(); int m = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            arr[i] = (int) in.nval;
        }
        build(1, n, 1);
        for (int i = 1, op, jobl, jobr, jobv; i <= m; i++) {
            in.nextToken(); op = (int) in.nval;
            in.nextToken(); jobl = (int) in.nval;
            in.nextToken(); jobr = (int) in.nval;
            if (op == 1) {
                in.nextToken(); jobv = (int) in.nval;
                add(jobl, jobr, jobv, 1, n, 1);
            } else if (op == 2){
                in.nextToken(); jobv = (int) in.nval;
                setMin(jobl, jobr, jobv, 1, n, 1);
            } else if (op == 3) {
                System.out.println(querySum(jobl, jobr, 1, n, 1));
            } else if (op == 4) {
                System.out.println(queryMax(jobl, jobr, 1, n, 1));
            } else {
                System.out.println(historyMax(jobl, jobr, 1, n, 1));
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
