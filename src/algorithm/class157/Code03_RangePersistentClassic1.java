package algorithm.class157;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/1 12:54
 * // 范围修改的可持久化线段树，经典的方式，java版
 * // 给定一个长度为n的数组arr，下标1~n，时间戳t=0，arr认为是0版本的数组
 * // 一共有m条操作，每条操作为如下四种类型中的一种
 * // C x y z : 当前时间戳t版本的数组，[x..y]范围每个数字增加z，得到t+1版本数组，并且t++
 * // Q x y   : 当前时间戳t版本的数组，打印[x..y]范围累加和
 * // H x y z : z版本的数组，打印[x..y]范围的累加和
 * // B x     : 当前时间戳t设置成x
 * // 1 <= n、m <= 10^5
 * // -10^9 <= arr[i] <= +10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/SP11470
 * // 测试链接 : https://www.spoj.com/problems/TTM/
 * // 提交以下的code，提交时请把类名改成"Main"
 * // java实现的逻辑一定是正确的，但是通过不了
 * // 因为这道题根据C++的运行时间，制定通过标准，根本没考虑java的用户
 * // 想通过用C++实现，本节课Code03_RangePersistentClassic2文件就是C++的实现
 * // 两个版本的逻辑完全一样，C++版本可以通过所有测试
 */
// todo 未提交答案
public class Code03_RangePersistentClassic1 {
    public static int MAXN = 100001;
    public static int MAXT = MAXN * 70;
    public static int n, m, t = 0, cnt = 0;
    public static int[] arr = new int[MAXN];
    public static int[] root = new int[MAXN];
    public static int[] left = new int[MAXT];
    public static int[] right = new int[MAXT];
    public static long[] sum = new long[MAXT];
    public static long[] add = new long[MAXT];

    public static int clone(int i) {
        int rt = ++cnt;
        left[rt] = left[i];
        right[rt] = right[i];
        sum[rt] = sum[i];
        add[rt] = add[i];
        return rt;
    }

    public static void up(int i) {
        sum[i] = sum[left[i]] + sum[right[i]];
    }

    public static void lazy(int i, long v, int n) {
        sum[i] += n * v;
        add[i] += v;
    }

    public static void down(int i, int ln, int rn) {
        if (add[i] != 0) {
            left[i] = clone(left[i]);
            right[i] = clone(right[i]);
            lazy(left[i], add[i], ln);
            lazy(right[i], add[i], rn);
            add[i] = 0;
        }
    }

    public static int build(int l, int r) {
        int rt = ++cnt;
        add[rt] = 0;
        if (l == r) {
            // 错误写法sum[rt] = add[l];
            sum[rt] = arr[l];
        } else {
            int mid = (l + r) >> 1;
            left[rt] = build(l, mid);
            right[rt] = build(mid + 1, r);
            up(rt);
        }
        return rt;
    }

    public static int add(int jobl, int jobr, int jobv, int l, int r, int i) {
        int rt = clone(i);
        if (jobl <= l && r <= jobr) {
            lazy(rt, jobv, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(rt, mid - l + 1, r - mid);
            if (jobl <= mid) {
                left[rt] = add(jobl, jobr, jobv, l, mid, left[rt]);
            }
            if (mid < jobr) {
                right[rt] = add(jobl, jobr, jobv, mid + 1, r, right[rt]);
            }
            up(rt);
        }
        return rt;
    }

    public static long query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        long ans = 0;
        int mid = (l + r) >> 1;
        down(i, mid - l + 1, r - mid);
        if (jobl <= mid) {
            ans += query(jobl, jobr, l, mid, left[i]);
        }
        if (mid < jobr) {
            ans += query(jobl, jobr, mid + 1, r, right[i]);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); arr[i] = (int) in.nval;
        }
        root[0] = build(1, n);
        int x, y, z;
        String op;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); op = in.sval;
            if (op.equals("C")) {
                in.nextToken(); x = (int) in.nval;
                in.nextToken(); y = (int) in.nval;
                in.nextToken(); z = (int) in.nval;
                root[t + 1] = add(x, y, z, 1, n, root[t]);
                t++;
            } else if (op.equals("Q")) {
                in.nextToken(); x = (int) in.nval;
                in.nextToken(); y = (int) in.nval;
                out.println(query(x, y, 1, n, root[t]));
            } else if (op.equals("H")) {
                in.nextToken(); x = (int) in.nval;
                in.nextToken(); y = (int) in.nval;
                in.nextToken(); z = (int) in.nval;
                out.println(query(x, y, 1, n, root[z]));
            } else {
                in.nextToken(); x = (int) in.nval;
                t = x;
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
