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
 * @date: 2024/8/24 14:56
 * https://www.luogu.com.cn/problem/P3374
 * 树状数组，单点增加，范围查询
 */
public class IndexTreeSingleAddIntervalQuery {
    public static int MAXN = 500001;

    public static int[] tree = new int[MAXN];

    public static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;
            for (int i = 1, v; i <= n; i++) {
                in.nextToken();
                v = (int) in.nval;
                add(i, v);
            }
            for (int i = 0, op, a, b; i < m; i++) {
                in.nextToken();
                op = (int) in.nval;
                in.nextToken();
                a = (int) in.nval;
                in.nextToken();
                b = (int) in.nval;
                if (op == 1) {
                    add(a, b);
                } else {
                    System.out.println(range(a, b));
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    // 最右侧的1
    public static int lowBit(int i) {
        return i & (-i);
    }

    public static void add(int i, int v) {
        while (i <= n) {
            tree[i] += v;
            i += lowBit(i);
        }
    }

    public static int range(int l, int r) {
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
}
