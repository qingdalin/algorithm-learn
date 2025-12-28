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
 * @date: 2024/9/11 20:49
 * https://www.luogu.com.cn/problem/P1503
 */
public class HouseDestruction {
    public static int MAXN = 50001;
    public static int[] pre = new int[MAXN << 2];
    public static int[] suf = new int[MAXN << 2];
    public static int[] stack = new int[MAXN];

    public static void up(int l, int r, int i) {
        pre[i] = pre[i << 1];
        suf[i] = suf[i << 1 | 1];
        int mid = (l + r) / 2;
        int ln = mid - l + 1;
        int rn = r - mid;
        if (pre[i << 1] == ln) {
            // 左侧的前缀完全贯穿
            pre[i] += pre[i << 1 | 1];
        }
        if (suf[i << 1 | 1] == rn) {
            // 右侧的后缀完全贯穿
            suf[i] += suf[i << 1];
        }
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            pre[i] = suf[i] = 1;
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(l, r, i);
        }
    }

    public static void update(int jobi, int jobv, int l, int r, int i) {
        if (l == r) {
            pre[i] = suf[i] = jobv;
        } else {
            int mid = (l + r) >> 1;
            if (jobi <= mid) {
                update(jobi, jobv, l, mid, i << 1);
            } else {
                update(jobi, jobv, mid + 1, r, i << 1 | 1);
            }
            up(l, r, i);
        }
    }

    // 递归过程的潜台词，jobi一定不超过l-r范围
    public static int query(int jobi, int l, int r, int i) {
        if (l == r) {
            return pre[i];
        } else {
            int mid = (l + r) >> 1;
            if (jobi <= mid) {
                if (jobi >= mid - suf[i << 1] + 1) {
                    // 如果左侧后缀包住了jobi，直接返回，左侧后缀加上右侧前缀
                    return suf[i << 1] + pre[i << 1 | 1];
                } else {
                    // 否则往左递归
                    return query(jobi, l, mid, i << 1);
                }
            } else {
                if (mid + pre[i << 1 | 1] >= jobi) {
                    return suf[i << 1] + pre[i << 1 | 1];
                } else {
                    return query(jobi, mid + 1, r, i << 1 | 1);
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int m = (int) in.nval;
            build(1, n, 1);
            String op;
            int size = 0;
            for (int i = 1; i <= m; i++) {
                in.nextToken();
                op = in.sval;
                if (op.equals("D")) {
                    in.nextToken();
                    int x = (int) in.nval;
                    update(x, 0, 1, n, 1);
                    stack[size++] = x;
                } else if (op.equals("R")) {
                    update(stack[--size], 1, 1, n, 1);
                } else {
                    in.nextToken();
                    int x = (int) in.nval;
                    System.out.println(query(x, 1, n, 1));
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
