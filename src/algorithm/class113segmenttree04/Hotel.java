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
 * @date: 2024/9/13 19:59
 * https://www.luogu.com.cn/problem/P2894
 */
public class Hotel {
    public static int MAXN = 50001;
    public static int[] len = new int[MAXN << 2];
    // 空房的前缀长度
    public static int[] pre = new int[MAXN << 2];
    public static int[] suf = new int[MAXN << 2];
    public static int[] change = new int[MAXN << 2];
    public static boolean[] update = new boolean[MAXN << 2];

    public static void up(int i, int ln, int rn) {
        int l = i << 1;
        int r = i << 1 | 1;
        len[i] = Math.max(Math.max(len[l], len[r]), suf[l] + pre[r]);
        pre[i] = len[l] < ln ? pre[l] : pre[l] + pre[r];
        suf[i] = len[r] < rn ? suf[r] : suf[l] + suf[r];
    }

    public static void down(int i, int ln, int rn) {
        if (update[i]) {
            lazy(i << 1, change[i], ln);
            lazy(i << 1 | 1, change[i], rn);
            update[i] = false;
        }
    }

    private static void lazy(int i, int v, int n) {
        len[i] = pre[i] = suf[i] = v == 0 ? n : 0;
        change[i] = v;
        update[i] = true;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            // 初始化都是空房
            len[i] = pre[i] = suf[i] = 1;
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i, mid - l + 1, r - mid);
        }
        update[i] = false;
        //change[i] = 0;
    }

    public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                update(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i, mid - l + 1, r - mid);
        }
    }

    // 调用递归的潜台词，l-r范围的空房长度大于等于x
    public static int queryLeft(int x, int l, int r, int i) {
        if (l == r) {
            // 说明x == 1
            return l;
        } else {
            int mid = (l + r) >> 1;
            // 这一步必须下发
            down(i, mid - l + 1, r - mid);
            // 返回尽量小的房间号，如果左侧满足返回左侧
            if (len[i << 1] >= x) {
                return queryLeft(x, l, mid, i << 1);
            }
            // 如果左侧加右侧满足，直接返回
            if (suf[i << 1] + pre[i << 1 | 1] >= x) {
                return mid - suf[i << 1] + 1;
            }
            // 最后是右侧
            return queryLeft(x, mid + 1, r, i << 1 | 1);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        int n = (int) in.nval;
        build(1, n, 1);
        in.nextToken();
        int m = (int) in.nval;
        for (int i = 1, op, x, y, left; i <= m; i++) {
            in.nextToken();
            op = (int) in.nval;
            if (op == 1) {
                in.nextToken(); x = (int) in.nval;
                if (len[1] < x) {
                    // 最长的连续空房都不能满足，返回0
                    left = 0;
                } else {
                    left = queryLeft(x, 1, n, 1);
                    update(left, left + x - 1, 1, 1, n, 1);
                }
                System.out.println(left);
            } else {
                in.nextToken(); x = (int) in.nval;
                in.nextToken(); y = (int) in.nval;
                // 这里的y + x - 1和n取最小值
                update(x, Math.min(y + x - 1, n), 0, 1, n, 1);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
