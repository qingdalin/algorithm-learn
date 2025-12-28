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
 * @date: 2024/8/24 16:12
 * 树状数组，范围查询，范围添加 即线段树
 * https://www.luogu.com.cn/problem/P3372
 */
public class IndexTreeIntervalQueryIntervalAdd {
    public static int MAXN = 100002;

    // 维护差分数组的树状数组信息
    public static long[] tree1 = new long[MAXN];
    public static long[] tree2 = new long[MAXN];

    public static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;
            long cur;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                cur = (long) in.nval;
                add(i, i, cur);
            }
            for (int i = 0, op, a, b, v; i < m; i++) {
                in.nextToken();
                op = (int) in.nval;
                in.nextToken();
                a = (int) in.nval;
                in.nextToken();
                b = (int) in.nval;
                if (op == 1) {
                    in.nextToken();
                    cur = (long) in.nval;
                    add(a, b, cur);
                } else {
                    System.out.println(sum(a, b));
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long sum(int l, int r) {
        return sum(r, tree1) * r - sum(r, tree2) - sum(l - 1, tree1) * (l - 1) + sum(l - 1, tree2);
    }

    private static long sum(int i, long[] tree) {
        long ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowBit(i);
        }
        return ans;
    }

    private static void add(int l, int r, long v) {
        add(l, tree1, v);
        add(r + 1, tree1, -v);
        add(l, tree2, (l - 1) * v);
        add(r + 1, tree2, -(r * v));
    }

    private static void add(int i, long[] tree, long v) {
        while (i <= n) {
            tree[i] += v;
            i += lowBit(i);
        }
    }

    private static int lowBit(int i) {
        return i & -i;
    }
}
