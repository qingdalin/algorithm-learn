package algorithm.class115segmenttree06;

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
 * @date: 2024/9/16 11:41
 * https://www.luogu.com.cn/problem/P1856
 */
public class Code04_PerimeterSum1 {
    public static int MAXN = 10001;
    // 矩形的四个点
    public static int[][] rec = new int[MAXN][4];
    // 扫描线
    // x1 -> y2,y4, +1
    // x3 -> y2,y4, -1
    public static int[][] line = new int[MAXN][4];
    // y坐标离散化处理
    public static int[] vsort = new int[MAXN];
    // 线段树总从长度
    public static int[] length = new int[MAXN << 2];
    // 线段树，被覆盖的y长度
    public static int[] cover = new int[MAXN << 2];
    // 被覆盖的次数
    public static int[] times = new int[MAXN << 2];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken(); rec[i][0] = (int) in.nval;
                in.nextToken(); rec[i][1] = (int) in.nval;
                in.nextToken(); rec[i][2] = (int) in.nval;
                in.nextToken(); rec[i][3] = (int) in.nval;
            }
            System.out.println(compute(n));
        }

        out.flush();
        out.close();
        bf.close();
    }

    private static long compute(int n) {
        return sancX(n) + scanY(n);
    }

    private static long scanY(int n) {
        for (int i = 1, j = 1 + n, x1, y1, x2, y2; i <= n; i++, j++) {
            x1 = rec[i][0]; y1 = rec[i][1]; x2 = rec[i][2]; y2 = rec[i][3];
            vsort[i] = x1; vsort[j] = x2;
            line[i][0] = y1; line[i][1] = x1; line[i][2] = x2; line[i][3] = 1;
            line[j][0] = y2; line[j][1] = x1; line[j][2] = x2; line[j][3] = -1;
        }
        long ans = sacen(n);
        return ans;
    }

    private static long sancX(int n) {
        for (int i = 1, j = 1 + n, x1, y1, x2, y2; i <= n; i++, j++) {
            x1 = rec[i][0]; y1 = rec[i][1]; x2 = rec[i][2]; y2 = rec[i][3];
            vsort[i] = y1; vsort[j] = y2;
            line[i][0] = x1; line[i][1] = y1; line[i][2] = y2; line[i][3] = 1;
            line[j][0] = x2; line[j][1] = y1; line[j][2] = y2; line[j][3] = -1;
        }
        long ans = sacen(n);
        return ans;
    }

    private static long sacen(int n) {
        n = n * 2;
        int m = prepare(n);
        build(1, m, 1);
        Arrays.sort(line, 1, n + 1, (a, b) -> a[0] - b[0]);
        long ans = 0;
        for (int i = 1, pre = 0; i <= n; i++) {
            pre = cover[1];
            // 更新线段树，找到jobl， jobr（需要减一）
            add(rank(m, line[i][1]), rank(m, line[i][2]) - 1, line[i][3], 1, m, 1);
            ans += Math.abs(cover[1] - pre);
        }
        return ans;
    }

    private static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            times[i] += jobv;
        } else {
            int mid = (l + r) >> 1;
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
        }
        up(i);
    }

    private static void up(int i) {
        if (times[i] > 0) {
            cover[i] = length[i];
        } else {
            cover[i] = cover[i << 1] + cover[i << 1 | 1];
        }
    }

    private static int rank(int m, int v) {
        int l = 1, r = m, mid = 0;
        int ans = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (vsort[mid] >= v) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    private static void build(int l, int r, int i) {
        if (l < r) {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
        }
        length[i] = vsort[r + 1] - vsort[l];
        cover[i] = 0;
        times[i] = 0;
    }

    private static int prepare(int n) {
        Arrays.sort(vsort, 1, n + 1);
        int m = 1;
        for (int i = 2; i <= n; i++) {
            if (vsort[m] != vsort[i]) {
                vsort[++m] = vsort[i];
            }
        }
        vsort[m + 1] = vsort[m];
        return m;
    }
}
