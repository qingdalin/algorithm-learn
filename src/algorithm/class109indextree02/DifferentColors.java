package algorithm.class109indextree02;

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
 * @date: 2024/8/25 13:43
 * https://www.luogu.com.cn/problem/P1972
 */
public class DifferentColors {
    public static int MAXN = 1000001;
    public static int[] arr = new int[MAXN];
    public static int[][] query = new int[MAXN][3];
    // 上升一元组
    public static int[] ans = new int[MAXN];

    public static int[] map = new int[MAXN];
    // 上升二元组
    public static int[] tree = new int[MAXN];

    public static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
            }
            in.nextToken();
            m = (int) in.nval;
            for (int i = 1, l, r; i <= m; i++) {
                in.nextToken();
                l = (int) in.nval;
                in.nextToken();
                r = (int) in.nval;
                query[i][0] = l;
                query[i][1] = r;
                query[i][2] = i;
            }
            compute();
            for (int i = 1; i <= m; i++) {
                System.out.println(ans[i]);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static void compute() {
        Arrays.sort(query, 1, m + 1, (a, b) -> a[1] - b[1]);
        for (int q = 1, s = 1, l, r, i; q <= m; q++) {
            r = query[q][1];
            for (;s <=  r; s++) {
                int color = arr[s];
                if (map[color] != 0) {
                    add(map[color], -1);
                }
                add(s, 1);
                map[color] = s;
            }
            l = query[q][0];
            i = query[q][2];
            ans[i] = range(l, r);
        }
    }

    private static int range(int l, int r) {
        return sum(r) - sum(l - 1);
    }

    private static int sum(int i) {
        int ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowBit(i);
        }
        return ans;
    }

    private static void add(int i, int v) {
        while (i <= n) {
            tree[i] += v;
            i += lowBit(i);
        }
    }

    private static int lowBit(int i) {
        return i & -i;
    }
}
