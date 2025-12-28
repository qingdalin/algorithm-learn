package algorithm.class111segmenttree02;

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
 * @date: 2024/9/6 21:54
 * https://www.luogu.com.cn/problem/P3740
 */
public class Posters1 {
    public static int MAXN = 10000001;
    public static int n, m;
    public static int MAXM = 1001;
    public static int[] pl = new int[MAXM];
    public static int[] pr = new int[MAXM];
    public static int[] num = new int[MAXM << 2];
    // posters[i] != 0 表示有统一的海报
    // posters[i] == 0 表示没有海报或者编号没有统一
    public static int[] posters = new int[MAXM << 4];
    // 判断最后的海报是否收集，一张海报只计算一次
    public static boolean[] visited = new boolean[MAXM];

    public static void down(int i) {
        if (posters[i] != 0) {
            posters[i << 1] = posters[i];
            posters[i << 1 | 1] = posters[i];
            posters[i] = 0;
        }
    }

    public static void build(int l, int r, int i) {
        if (l < r) {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
        }
        posters[i] = 0;
    }

    public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            posters[i] = jobv;
        } else {
            down(i);
            int mid = (l + r) >> 1;
            if (jobl <= mid) {
                update(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
        }
    }

    public static int query (int jobl, int jobr, int l, int r, int i) {
        if (l == r) {
            if (posters[i] != 0 && !visited[posters[i]]) {
                visited[posters[i]] = true;
                return 1;
            } else {
                return 0;
            }
        } else {
            down(i);
            int ans = 0;
            int mid = (l + r) >> 1;
            if (jobl <= mid) {
                ans += query(jobl, jobr, l, mid, i << 1);
            }
            if (jobr > mid) {
                ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
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
            int size = 0;
            num[++size] = n;
            for (int i = 1; i <= m; i++) {
                in.nextToken(); pl[i] = (int) in.nval;
                in.nextToken(); pr[i] = (int) in.nval;
                num[++size] = pl[i];
                num[++size] = pr[i];
            }
            size = collect(size);
            build(1, size, 1);
            for (int i = 1, jobl, jobr; i <= m; i++) {
                jobl = rank(1, size, pl[i]);
                jobr = rank(1, size, pr[i]);
                update(jobl, jobr, i, 1, size, 1);
            }
            System.out.println(query(1, rank(1, size, n), 1, size, 1));
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int rank(int l, int r, int v) {
        int m = 0;
        int ans = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (num[m] >= v) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    private static int collect(int m) {
        Arrays.sort(num, 1, m + 1);
        int size = 1;
        // 数字去重
        for (int i = 2; i <= m; i++) {
            if (num[i - 1] != num[i]) {
                num[++size] = num[i];
            }
        }
        int cnt = size;
        for (int i = 2; i <= size; i++) {
            if (num[i - 1] + 1 < num[i]) {
                num[++cnt] = num[i - 1] + 1;
            }
        }
        Arrays.sort(num, 1, cnt + 1);
        return cnt;
    }
}
