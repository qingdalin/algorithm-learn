package algorithm.class108indextree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/24 17:41
 * https://www.luogu.com.cn/problem/P4514
 * 二维差分范围增加和查找
 */
public class TwoDimensionIntervalAddIntervalQuery {
    public static int MAXN = 2050;
    public static int MAXM = 2050;

    // 维护差分数组的树状数组信息
    public static int[][] tree1 = new int[MAXN][MAXM];
    public static int[][] tree2 = new int[MAXN][MAXM];
    public static int[][] tree3 = new int[MAXN][MAXM];
    public static int[][] tree4 = new int[MAXN][MAXM];

    public static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String op;
        int a, b, c, d, v;
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            op = in.sval;
            if (op.equals("X")) {
                in.nextToken();
                n = (int) in.nval;
                in.nextToken();
                m = (int) in.nval;
            } else if (op.equals("L")) {
                in.nextToken(); a = (int) in.nval;
                in.nextToken(); b = (int) in.nval;
                in.nextToken(); c = (int) in.nval;
                in.nextToken(); d = (int) in.nval;
                in.nextToken(); v = (int) in.nval;
                add(a, b, c, d, v);
            } else {
                in.nextToken(); a = (int) in.nval;
                in.nextToken(); b = (int) in.nval;
                in.nextToken(); c = (int) in.nval;
                in.nextToken(); d = (int) in.nval;
                System.out.println(range(a, b, c, d));
            }


        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int range(int a, int b, int c, int d) {
        return sum(c, d) - sum(c, b - 1) - sum(a - 1, d) + sum(a - 1, b - 1);
    }

    private static int sum(int x, int y) {
        int ans = 0;
        for (int i = x; i > 0; i -= lowBit(i)) {
            for (int j = y; j > 0; j -= lowBit(j)) {
                ans += (x + 1) * (y + 1) * tree1[i][j] - (y + 1) * tree2[i][j] - (x + 1) * tree3[i][j] + tree4[i][j];
            }
        }
        return ans;
    }

    private static void add(int a, int b, int c, int d, int v) {
        add(a, b, v);
        add(a, d + 1, -v);
        add(c + 1, b, -v);
        add(c + 1, d + 1, v);
    }

    private static void add(int x, int y, int v) {
        int v1 = v;
        int v2 = v * x;
        int v3 = v * y;
        int v4 = v * y * x;
        for (int i = x; i <= n; i += lowBit(i)) {
            for (int j = y; j <= m; j += lowBit(j)) {
                tree1[i][j] += v1;
                tree2[i][j] += v2;
                tree3[i][j] += v3;
                tree4[i][j] += v4;
            }
        }
    }

    private static int lowBit(int i) {
        return i & -i;
    }
}
