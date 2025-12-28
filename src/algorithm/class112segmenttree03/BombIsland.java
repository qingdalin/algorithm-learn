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
 * @date: 2024/9/7 8:49
 * https://www.luogu.com.cn/problem/P2184
 */
public class BombIsland {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[] bombStart = new int[MAXN << 2];
    public static int[] bombEnd = new int[MAXN << 2];

    public static void up(int i) {
        bombStart[i] = bombStart[i << 1] + bombStart[i << 1 | 1];
        bombEnd[i] = bombEnd[i << 1] + bombEnd[i << 1 | 1];
    }

    public static void build(int l, int r, int i) {
        if (l < r) {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        bombStart[i] = 0;
        bombEnd[i] = 0;
    }

    // 维护一段范围开始的数量和结束的数量
    public static void add(int jobt, int jobi, int l, int r, int i) {
        if (l == r) {
            if (jobt == 0) {
                // 开始位置
                bombStart[i]++;
            } else {
                bombEnd[i]++;
            }
        } else {
            int mid = (l + r) >> 1;
            if (jobi <= mid) {
                add(jobt, jobi, l, mid, i << 1);
            } else {
                add(jobt, jobi, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }
    // 如果求13-27范围地雷数量，先求1-27开始的地雷数量，在求1-12地雷结尾的数量，最后相减
    public static int query(int jobt, int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            if (jobt == 0) {
                return bombStart[i];
            } else {
                return bombEnd[i];
            }
        }
        int ans = 0;
        int mid = (l + r) >> 1;
        if (jobl <= mid) {
            ans += query(jobt, jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += query(jobt, jobl, jobr, mid + 1, r, i << 1 | 1);
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
                if (op == 1) {
                    add(0, jobl, 1, n, 1);
                    add(1, jobr, 1, n, 1);
                } else {
                    int s = query(0, 1, jobr, 1, n, 1);
                    // 如果s==1，开头前边没有最左的位置，
                    int e = s == 1 ? 0 : query(1, 1, jobl - 1, 1, n, 1);
                    System.out.println(s - e);
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
